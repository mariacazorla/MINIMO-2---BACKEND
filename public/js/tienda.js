const isEmulator = /Android/i.test(navigator.userAgent);
const BASE_URL = isEmulator ? "http://10.0.2.2:8080/dsaApp" : "http://localhost:8080/dsaApp";
const token = localStorage.getItem("token");
const idPartida = new URLSearchParams(window.location.search).get("id_partida");

function ajaxConToken(opciones) {
  opciones.headers = opciones.headers || {};
  opciones.headers["Authorization"] = "Bearer " + token;
  return $.ajax(opciones);
}

$(document).ready(function () {
    cargarCategorias();
    cargarCarrito();
    cargarMonedas(); // Nueva llamada para cargar las monedas
  
    $("#btnCarrito").click(() => {
      $("#modalCarrito").modal("show");
    });
  
    $("#comprarBtn").click(() => {
      ajaxConToken({
        url: `${BASE_URL}/carrito/comprar/${idPartida}`,
        method: "POST",
      }).done(() => {
        alert("¡Compra realizada!");
        cargarCarrito();
        cargarMonedas();
      }).fail((xhr) => {
        alert(JSON.parse(xhr.responseText).error);
      });
    });
});
  
function cargarMonedas() {
  ajaxConToken({
    url: `${BASE_URL}/partidas/monedas/${idPartida}`,
    method: "GET",
    success: function (data) {
      // Mostrar las monedas disponibles en la tienda
      $("#monedasCantidad").text(data.monedas + " monedas");
    },
    error: function (xhr) {
      console.error("Error al obtener las monedas:", xhr.responseText);
    }
  });
}
  

function cargarCategorias() {
    ajaxConToken({
      url: `${BASE_URL}/tienda/categorias`,
      method: "GET",
      success: function (categorias) {
        // Agrega el botón "Todos"
        $("#categorias").append(`<button class="btn btn-outline-primary me-2 mb-2 categoria-btn" data-cat="">Todos</button>`);
  
        // Agrega los botones de categorías
        categorias.forEach(cat => {
          $("#categorias").append(`<button class="btn btn-outline-primary me-2 mb-2 categoria-btn" data-cat="${cat}">${cat}</button>`);
        });
  
        // Asigna el evento click a todos los botones
        $(".categoria-btn").click(function () {
          const categoria = $(this).data("cat");
          cargarProductos(categoria);
        });
  
        // Mostrar todos los productos por defecto
        cargarProductos();
      },
      error: function (xhr) {
        console.error("Error al cargar categorías:", xhr.responseText);
      }
    });
}

function cargarProductos(categoria = "") {
  const url = categoria ? `${BASE_URL}/tienda/categorias/${categoria}` : `${BASE_URL}/tienda/productos`;
  ajaxConToken({
    url: url,
    method: "GET",
    success: function (productos) {
      $("#productos").empty();
      productos.forEach(p => {
        $("#productos").append(`
          <div class="col-sm-6 col-md-4 col-lg-3">
            <div class="card h-100">
              <div class="card-body">
                <h5 class="card-title">${p.nombre}</h5>
                <p class="card-text">Precio: ${p.precio} monedas</p>
                <p class="card-text"><span class="badge bg-secondary">${p.categoria}</span></p>
                <button class="btn btn-primary agregar-btn" data-id="${p.id_objeto}">Agregar</button>
              </div>
            </div>
          </div>`);
      });

      $(".agregar-btn").click(function () {
        const id = $(this).data("id");
        ajaxConToken({
          url: `${BASE_URL}/carrito/${id}`,
          method: "POST",
        }).done(() => {
          cargarCarrito();
        });
      });
    },
    error: function (xhr) {
      console.error("Error al cargar productos:", xhr.responseText);
    }
  });
}

function cargarCarrito() {
  ajaxConToken({
    url: `${BASE_URL}/carrito`,
    method: "GET"
  }).done(objetos => {
    const tbody = $("#tablaCarrito").empty();
    let total = 0;
    objetos.forEach(o => {
      total += o.precio;
      tbody.append(`
        <tr>
          <td>${o.nombre}</td>
          <td>${o.precio}</td>
          <td>${o.categoria}</td>
          <td><button class="btn btn-danger btn-sm quitar-btn" data-id="${o.id_objeto}">🗑</button></td>
        </tr>`);
    });
    $("#totalCarrito").text(total);

    $(".quitar-btn").click(function () {
      const id = $(this).data("id");
      ajaxConToken({
        url: `${BASE_URL}/carrito/${id}`,
        method: "DELETE"
      }).done(() => {
        cargarCarrito();
      });
    });
  });
}

