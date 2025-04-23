package edu.upc.dsa.services;

import edu.upc.dsa.manager.TiendaManager;
import edu.upc.dsa.manager.TiendaManagerImpl;
import edu.upc.dsa.models.CompraRequest;
import edu.upc.dsa.models.Producto;
import edu.upc.dsa.models.Seccion;
import edu.upc.dsa.config.IniciarDatos;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api(value = "/tienda", description = "Servicio de tienda")
@Path("/tienda")
public class TiendaService {

    final static Logger logger = Logger.getLogger(TiendaService.class);
    private final TiendaManager tm;

    public TiendaService() {
        this.tm = TiendaManagerImpl.getInstance();
        IniciarDatos.init(); // Asegurarse de que los datos están cargados
    }

    @GET
    @ApiOperation(value = "Listar todos los productos", notes = "Productos disponibles en la tienda")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Producto.class, responseContainer = "List")
    })
    @Path("/productos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProductos() {
        return Response.status(200).entity(this.tm.listarProductos()).build();
    }

    @GET
    @ApiOperation(value = "Buscar productos por nombre", notes = "Devuelve productos que contienen el nombre buscado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Producto.class, responseContainer = "List")
    })
    @Path("/productos/buscar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarProductosPorNombre(@QueryParam("nombre") String nombre) {
        return Response.status(200).entity(this.tm.buscarProductosPorNombre(nombre)).build();
    }

    @GET
    @ApiOperation(value = "Buscar producto por ID", notes = "Producto específico por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Producto.class),
            @ApiResponse(code = 404, message = "Producto no encontrado")
    })
    @Path("/productos/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarProductoPorId(@PathParam("id") String idProducto) {
        Producto p = this.tm.buscarProductoPorId(idProducto);
        if (p != null)
            return Response.status(200).entity(p).build();
        else
            return Response.status(404).entity("{\"error\":\"Producto no encontrado\"}").build();
    }

    @GET
    @ApiOperation(value = "Listar productos de una sección", notes = "Productos de una sección específica")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Producto.class, responseContainer = "List")
    })
    @Path("/productos/seccion/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarProductosPorSeccion(@PathParam("nombre") String nombreSeccion) {
        return Response.status(200).entity(this.tm.listarProductosPorSeccion(nombreSeccion)).build();
    }

    @POST
    @ApiOperation(value = "Añadir producto a una sección", notes = "Crea un nuevo producto en la sección especificada")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Producto añadido correctamente"),
            @ApiResponse(code = 400, message = "Faltan datos o sección inválida")
    })
    @Path("/productos/seccion/{seccion}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProductoASeccion(@PathParam("seccion") String nombreSeccion, Producto producto) {
        if (producto.getId() == null || producto.getNombre() == null || producto.getPrecio() <= 0) {
            return Response.status(400).entity("{\"error\":\"Datos del producto incompletos o inválidos\"}").build();
        }
        this.tm.addProductoASeccion(nombreSeccion, producto);
        return Response.status(201).entity("{\"mensaje\":\"Producto añadido correctamente\"}").build();
    }

    @POST
    @ApiOperation(value = "Comprar un producto", notes = "Compra un producto específico")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Compra realizada"),
            @ApiResponse(code = 404, message = "Usuario o producto no encontrado")
    })
    @Path("/productos/comprar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response comprarProducto(CompraRequest request) {
        boolean comprado = this.tm.comprarProducto(request.getIdProducto(), request.getNombreUsuario());
        if (comprado)
            return Response.status(200).entity("{\"mensaje\":\"Producto comprado con éxito\"}").build();
        else
            return Response.status(404).entity("{\"error\":\"Usuario o producto no encontrado\"}").build();
    }

    @GET
    @ApiOperation(value = "Listar todas las secciones", notes = "Devuelve todas las secciones disponibles")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Seccion.class, responseContainer = "List")
    })
    @Path("/secciones")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSecciones() {
        return Response.status(200).entity(this.tm.getSecciones()).build();
    }

    @DELETE
    @ApiOperation(value = "Eliminar producto de una sección", notes = "Elimina un producto de la sección especificada por su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Producto eliminado correctamente"),
            @ApiResponse(code = 404, message = "Producto no encontrado")
    })
    @Path("/productos/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarProducto(@PathParam("id") String idProducto) {
        Producto producto = this.tm.buscarProductoPorId(idProducto);
        if (producto != null) {
            this.tm.eliminarProducto(idProducto);
            return Response.status(200).entity("{\"mensaje\":\"Producto eliminado correctamente\"}").build();
        } else {
            return Response.status(404).entity("{\"error\":\"Producto no encontrado\"}").build();
        }
    }

}
