const isEmulator = /Android/i.test(navigator.userAgent);
const BASE_URL = isEmulator ? "http://10.0.2.2:8080/dsaApp" : "http://localhost:8080/dsaApp";
const token = localStorage.getItem("token");
const idPartida = new URLSearchParams(window.location.search).get("id_partida");

// Función ajax con token para hacer peticiones
function ajaxConToken(opciones) {
    opciones.headers = opciones.headers || {};
    opciones.headers["Authorization"] = "Bearer " + token;
    return $.ajax(opciones);
}

// Función para cargar el inventario de la partida
function cargarInventario() {
    ajaxConToken({
        url: `${BASE_URL}/partidas/${idPartida}`, // Ruta para obtener una partida específica
        type: 'GET',
        success: function(data) {
            const inventario = data.inventario;
            const inventarioList = $('#inventario-list');
            inventarioList.empty(); // Limpiar lista previa
            inventario.forEach(objeto => {
                // Verificar si no existe descripción
                const descripcion = objeto.descripcion || "Descripción no disponible"; // Valor predeterminado
                // Verificar si no existe imagen
                const imagen = objeto.imagenUrl ? `<img src="${objeto.imagenUrl}" class="card-img-top" alt="${objeto.nombre}">` : "<div class='no-image'>Imagen no disponible</div>";

                inventarioList.append(`
                    <div class="col-12 col-md-4 mb-4">
                        <div class="card">
                            ${imagen} <!-- Mostrar imagen o mensaje de "Imagen no disponible" -->
                            <div class="card-body">
                                <h5 class="card-title">${objeto.nombre}</h5>
                                <p class="card-text">${descripcion}</p>
                                <p class="card-text"><strong>Precio:</strong> ${objeto.precio} monedas</p>
                            </div>
                        </div>
                    </div>
                `);
            });
        },
        error: function() {
            alert("Error al cargar el inventario.");
        }
    });
}

// Llamada a la función para cargar el inventario
cargarInventario();
