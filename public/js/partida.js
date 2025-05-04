// Detecta si estamos en el emulador o en la PC
const isEmulator = /Android/i.test(navigator.userAgent);

// Usar la IP correcta según el caso
const BASE_URL = isEmulator ? "http://10.0.2.2:8080/dsaApp" : "http://localhost:8080/dsaApp";

const token = localStorage.getItem("token");
// Función para hacer peticiones AJAX con token
function ajaxConToken(opciones) {
  opciones.headers = opciones.headers || {};
  opciones.headers["Authorization"] = "Bearer " + token;
  return $.ajax(opciones);
}

function crearPartida(){
  ajaxConToken({
    url: BASE_URL + "/partidas",
    type: "POST",
    success: function () {
      mostrarPartidas();
    },
    error: function (xhr, status, error) {
      console.error("Error:", xhr.responseText);
      console.error("Estado:", status);
      console.error("Detalles del error:", error);
      mostrarMensaje("Error al crear partidas. Por favor, intente más tarde.", "error");
    }
  });
}

function mostrarPartidas() {
    ajaxConToken({
        url: BASE_URL + "/partidas",
        type: "GET",
        success: function (partidas) {
          if (!partidas || partidas.length === 0) {
            console.log("No hay partidas.");
            filasPartidas(partidas);
          } else {
            filasPartidas(partidas);
          }
        },
        error: function (xhr, status, error) {
          console.error("Error:", xhr.responseText);
          console.error("Estado:", status);
          console.error("Detalles del error:", error);
          mostrarMensaje("Error al cargar partidas. Por favor, intente más tarde.", "error");
        }
      });
}
function filasPartidas(partidas){
  console.log(partidas);
  const contenedor = $("#contenedor-partidas");
  contenedor.empty();

  partidas.forEach((partida, i) => {
    contenedor.append(`
      <div class="col-12 col-md-6 col-lg-4">
        <div class="card h-100">
          <div class="card-body d-flex flex-column justify-content-between">
            <div>
              <h5 class="card-title">Partida #${i + 1}</h5>
              <p class="card-text"><strong>Vidas:</strong> ${partida.vidas}</p>
              <p class="card-text"><strong>Monedas:</strong> ${partida.monedas}</p>
              <p class="card-text"><strong>Puntuación:</strong> ${partida.puntuacion}</p>
            </div>
            <div class="mt-3 text-end">
              <button class="btn btn-danger btn-sm eliminar-btn" data-id="${partida.id_partida}">Eliminar</button>
            </div>
          </div>
        </div>
      </div>
    `);
  });
}

function eliminarPartida(id){
  ajaxConToken({
    url: BASE_URL + "/partidas/" + id,
    type: "DELETE",
    success: function () {
      mostrarPartidas();
    },
    error: function (xhr, status, error) {
      console.error("Error:", xhr.responseText);
      console.error("Estado:", status);
      console.error("Detalles del error:", error);
      mostrarMensaje("Error al eliminar la partida. Por favor, intente más tarde.", "error");
    }
  });
}

mostrarPartidas();

document.getElementById("crear").addEventListener("click", function () {
  crearPartida();
});
document.getElementById("volver").addEventListener("click", function () {
window.location.href = "menu.html";
});

$(document).on("click", ".eliminar-btn", function () {
  const id = $(this).data("id");
  const confirmado = window.confirm("¿Estás seguro de que deseas eliminar esta partida?");
  if (confirmado) {
    eliminarPartida(id);
  }
});