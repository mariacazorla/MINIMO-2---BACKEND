// Detecta si estamos en el emulador o en la PC
const isEmulator = /Android/i.test(navigator.userAgent);
// Usar la IP correcta según el caso
const BASE_URL = isEmulator ? "http://10.0.2.2:8080/dsaApp/tienda" : "http://localhost:8080/dsaApp/tienda";

const token = localStorage.getItem("token");
// Función para hacer peticiones AJAX con token
function ajaxConToken(opciones) {
  opciones.headers = opciones.headers || {};
  opciones.headers["Authorization"] = "Bearer " + token;
  return $.ajax(opciones);
}
// Cargar secciones
function cargarSecciones() {
  ajaxConToken({
    url: BASE_URL + "/secciones",
    type: "GET",
    success: function (secciones) {
      console.log("Secciones cargadas:", secciones);
      let seccionesDiv = $('#secciones');
      seccionesDiv.empty();
      if (secciones && secciones.length > 0) {
        secciones.forEach(s => {
          let btn = $('<button class="seccion-btn"></button>').text(s.nombre);
          btn.on('click', function () {
            cargarProductosPorSeccion(s.nombre);
          });
          seccionesDiv.append(btn);
        });
        // Cargar la primera sección por defecto
        if (secciones.length > 0) {
          cargarProductosPorSeccion(secciones[0].nombre);
        }
      } else {
        // Si no hay secciones, mostrar todos los productos
        cargarTodosProductos();
      }
    },
    error: function (xhr, status, error) {
      console.error("Error al cargar secciones:", xhr.responseText);
      // Si falla la carga de secciones, intentamos cargar todos los productos
      cargarTodosProductos();
      mostrarMensaje("No se pudieron cargar las secciones. Mostrando todos los productos disponibles.", "error");
    }
  });
}
// Cargar todos los productos
function cargarTodosProductos() {
  ajaxConToken({
    url: BASE_URL + "/productos",
    type: "GET",
    success: function (productos) {
      console.log("Todos los productos:", productos);
      mostrarProductos(productos);
    },
    error: function (xhr, status, error) {
      console.error("Error al cargar todos los productos:", xhr.responseText);
      mostrarMensaje("Error al cargar productos. Por favor, intente más tarde.", "error");
    }
  });
}
// Cargar productos por sección
function cargarProductosPorSeccion(nombreSeccion) {
  ajaxConToken({
    url: BASE_URL + "/productos/seccion/" + nombreSeccion,
    type: "GET",
    success: function (productos) {
      console.log("Productos en sección:", nombreSeccion, productos);
      mostrarProductos(productos);
    },
    error: function (xhr, status, error) {
      console.error("Error al cargar productos de la sección:", xhr.responseText);
      mostrarMensaje("Error al cargar productos de la sección: " + nombreSeccion, "error");
    }
  });
}
// Mostrar productos en la interfaz
function mostrarProductos(productos) {
  let productosDiv = $('#productos');
  productosDiv.empty();
  if (productos && productos.length > 0) {
    productos.forEach(p => {
      let productoCard = $(`
        <div class="producto-card">
          <h5>${p.nombre}</h5>
          <p>ID: ${p.id}</p>
          <p>Precio: ${p.precio} monedas</p>
          <button class="comprar-btn" data-id="${p.id}">Comprar</button>
        </div>
      `);
      // Agregar evento al botón de comprar
      productoCard.find('.comprar-btn').on('click', function() {
        let idProducto = $(this).data('id');
        comprarProducto(idProducto);
      });
      productosDiv.append(productoCard);
    });
  } else {
    productosDiv.html('<p>No hay productos disponibles en esta sección.</p>');
  }
}
// Comprar un producto
function comprarProducto(idProducto) {
  ajaxConToken({
    url: BASE_URL + "/productos/comprar",
    type: "POST",
    contentType: "application/json",
    data: JSON.stringify({
      idProducto: idProducto,
      nombreUsuario: usuarioActual
    }),
    success: function (response) {
      console.log("Producto comprado:", response);
      mostrarMensaje("¡Producto comprado con éxito!", "success");
    },
    error: function (xhr, status, error) {
      console.error("Error al comprar producto:", xhr.responseText);
      mostrarMensaje("Error al comprar el producto. " + JSON.parse(xhr.responseText).error, "error");
    }
  });
}
// Mostrar mensaje de éxito o error
function mostrarMensaje(texto, tipo) {
  let mensajeDiv = $('#mensaje');
  mensajeDiv.text(texto);
  mensajeDiv.removeClass('success error').addClass(tipo);
  mensajeDiv.show();
  // Ocultar el mensaje después de 3 segundos
  setTimeout(function() {
    mensajeDiv.hide();
  }, 3000);
}
$(document).ready(function () {
  cargarSecciones();
});
  