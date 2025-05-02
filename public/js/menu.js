// Detecta si estamos en el emulador o en la PC
const isEmulator = /Android/i.test(navigator.userAgent);

// Usar la IP correcta según el caso
const BASE_URL = isEmulator ? "http://10.0.2.2:8080/dsaApp" : "http://localhost:8080/dsaApp";


// Función para hacer peticiones AJAX con token
function ajaxConToken(opciones) {
  opciones.headers = opciones.headers || {};
  opciones.headers["Authorization"] = "Bearer " + token;
  return $.ajax(opciones);
}
document.getElementById("tienda").addEventListener("click", function () {
    window.location.href = "tienda.html";
});

document.getElementById("volver").addEventListener("click", function () {
    localStorage.removeItem("token"); // Elimina el token
    alert("Sesión cerrada correctamente.");
    window.location.href = "/";
});