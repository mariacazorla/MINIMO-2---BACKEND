window.onload = function () {
  formulario();
}

function formulario() {
  async function enviarFormulario(event, tipo) {
    event.preventDefault();
    const form = event.target;
    const usuario = form.username.value.trim();
    const password = form.password.value.trim();
    const msgDiv = document.getElementById("mensaje");

    // Validación de campos vacíos
    if (!usuario || !password) {
      msgDiv.innerText = "Por favor completa todos los campos.";
      msgDiv.className = "text-danger";
      return;
    }

    const datos = {
      nombreUsu: usuario,
      password: password
    };

    // Determina si es un login o un registro
    const ruta = tipo === "login" ? "login" : "register";

    // Detecta si estamos en el emulador o en la PC
    const isEmulator = /Android/i.test(navigator.userAgent);

    // Usar la IP correcta según el caso
    const baseUrl = isEmulator ? "http://10.0.2.2:8080/dsaApp" : "http://localhost:8080/dsaApp";

    try {
      const res = await fetch(`${baseUrl}/usuarios/${ruta}`, {
        method: "POST",
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(datos)
      });

      const data = await res.json();

      if (res.ok) {
        msgDiv.innerText = data.mensaje || (tipo === "login" ? "Inicio de sesión exitoso." : "Registro exitoso.");
        msgDiv.className = "text-success";

        if (tipo === "login") {
          localStorage.setItem("token", data.token);
          setTimeout(() => {
            window.location.href = "tienda.html";
          }, 1000);
        }
      } else {
        msgDiv.innerText = data.mensaje || data.error || "Error al procesar la solicitud.";
        msgDiv.className = "text-danger";
      }
    } catch (error) {
      msgDiv.innerText = "Error de conexión con el servidor.";
      msgDiv.className = "text-danger";
    }
  }

  // Event listeners para los formularios
  document.getElementById("loginForm").addEventListener("submit", e => enviarFormulario(e, "login"));
  document.getElementById("registerForm").addEventListener("submit", e => enviarFormulario(e, "register"));
}

