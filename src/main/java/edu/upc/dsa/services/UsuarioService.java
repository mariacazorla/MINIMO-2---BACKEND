package edu.upc.dsa.services;

import edu.upc.dsa.ProductManager;
import edu.upc.dsa.ProductManagerImpl;
import edu.upc.dsa.exceptions.PasswordNotMatchException;
import edu.upc.dsa.exceptions.UsuarioNotFoundException;
import edu.upc.dsa.exceptions.UsuarioYaExisteException;
import edu.upc.dsa.models.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/usuarios", description = "Endpoint to usuarios Service")
@Path("/usuarios")
public class UsuarioService {

    final static Logger logger = Logger.getLogger(UsuarioService.class); // Log4j
    private ProductManager pm; // fm es una instancia del FlightManager (implementado como Singleton).

    public UsuarioService() {
        this.pm = ProductManagerImpl.getInstance();
        if (pm.sizeUsuarios()==0){
            this.pm.addUsuario("Paco", "1234");
            this.pm.addUsuario("Lopez", "1234");
            this.pm.addUsuario("Ana", "1234");
            this.pm.addUsuario("Miguel", "1234");
        }

        Producto p1 = new Producto("1", "Espada mágica", 50);
        Producto p2 = new Producto("2", "Poción de vida", 25);
        Producto p3 = new Producto("3", "Traje ninja", 40);

        this.pm.addProductoASeccion("skins", p1);
        this.pm.addProductoASeccion("vidas", p2);
        this.pm.addProductoASeccion("skins", p3);

    }

    @POST
    @ApiOperation(value = "Crea al usuario", notes = "nose")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuario registrado correctamente", response = Usuario.class),
            @ApiResponse(code = 500, message = "Usuario ya existe")
    })

    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newUsuario(Usuario u) {
        try {
            this.pm.addUsuario(u);
            return Response.status(201).entity("{\"mensaje\":\"Usuario registrado correctamente\"}").build();
        } catch (UsuarioYaExisteException e) {
            return Response.status(500).entity("{\"error\":\"Usuario ya existe\"}").build();
        }

    }

    //

    @POST
    @ApiOperation(value = "Login usuario", notes = "nose")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Usuario.class),
            @ApiResponse(code = 404, message = "Usuario no encontrado"),
            @ApiResponse(code = 401, message = "Contraseña incorrecta")
    })
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUsuario(Usuario u){
        try {
            Usuario UsuarioLogueado = this.pm.loginUsuario(u.getNombreUsu(), u.getPassword());
            return Response.status(201).entity(UsuarioLogueado).build();
        } catch (UsuarioNotFoundException e) {
            return Response.status(404).entity("{\"error\":\"Usuario no encontrado\"}").build();
        } catch (PasswordNotMatchException e) {
            return Response.status(401).entity("{\"error\":\"Contraseña incorrecta\"}").build();
        }
    }

    @GET
    @ApiOperation(value = "Listar todos los productos", notes = "Devuelve todos los productos disponibles en la tienda")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Producto.class, responseContainer = "List")
    })
    @Path("/productos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProductos() {
        return Response.status(200).entity(this.pm.listarProductos()).build();
    }

    @GET
    @ApiOperation(value = "Buscar productos por nombre", notes = "Devuelve productos que contienen el nombre buscado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Producto.class, responseContainer = "List")
    })
    @Path("/productos/buscar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarProductosPorNombre(@QueryParam("nombre") String nombre) {
        return Response.status(200).entity(this.pm.buscarProductosPorNombre(nombre)).build();
    }

    @GET
    @ApiOperation(value = "Buscar producto por ID", notes = "Devuelve un producto con ID específico")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Producto.class),
            @ApiResponse(code = 404, message = "Producto no encontrado")
    })
    @Path("/productos/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarProductoPorId(@PathParam("id") String idProducto) {
        Producto p = this.pm.buscarProductoPorId(idProducto);
        if (p != null)
            return Response.status(200).entity(p).build();
        else
            return Response.status(404).entity("{\"error\":\"Producto no encontrado\"}").build();
    }

    @GET
    @ApiOperation(value = "Listar productos de una sección", notes = "Devuelve los productos de una sección específica")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Producto.class, responseContainer = "List")
    })
    @Path("/productos/seccion/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarProductosPorSeccion(@PathParam("nombre") String nombreSeccion) {
        return Response.status(200).entity(this.pm.listarProductosPorSeccion(nombreSeccion)).build();
    }

    @POST
    @ApiOperation(value = "Comprar un producto", notes = "Realiza una compra de un producto por parte de un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Compra realizada con éxito"),
            @ApiResponse(code = 404, message = "Usuario o producto no encontrado")
    })
    @Path("/productos/comprar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response comprarProducto(CompraRequest request) {
        boolean comprado = this.pm.comprarProducto(request.getIdProducto(), request.getNombreUsuario());
        if (comprado)
            return Response.status(200).entity("{\"mensaje\":\"Producto comprado con éxito\"}").build();
        else
            return Response.status(404).entity("{\"error\":\"Usuario o producto no encontrado\"}").build();
    }
    @GET
    @ApiOperation(value = "Listar todas las secciones", notes = "Devuelve todas las secciones de la tienda")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Seccion.class, responseContainer = "List")
    })
    @Path("/secciones")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSecciones() {
        return Response.status(200).entity(((ProductManagerImpl)this.pm).getSecciones()).build();
    }


}
