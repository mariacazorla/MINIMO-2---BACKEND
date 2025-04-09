window.onload = function() {
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

  try {
    const res = await fetch(`http://localhost:8080/dsaApp/usuarios/${tipo === "login" ? "login" : ""}`, {
      method: "POST",
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(datos)
    });
  
    const data = await res.json();
  
    if (res.ok) {
      msgDiv.innerText = data.mensaje || "Inicio de sesión exitoso.";
      msgDiv.className = "text-success";
      setTimeout(() => {
        window.location.href = "tienda.html";
      }, 1000); // Puedes ajustar este tiempo si quieres que redirija más rápido
    } else {
      msgDiv.innerText = data.mensaje || data.error || "Error al iniciar sesión.";
      msgDiv.className = "text-danger";
    }
  } catch (error) {
    msgDiv.innerText = "Error de conexión con el servidor.";
    msgDiv.className = "text-danger";
  }
}

document.getElementById("loginForm").addEventListener("submit", e => enviarFormulario(e, "login"));
document.getElementById("registerForm").addEventListener("submit", e => enviarFormulario(e, "register"));
}
