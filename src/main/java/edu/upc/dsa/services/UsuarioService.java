package edu.upc.dsa.services;

import edu.upc.dsa.config.IniciarDatos;
import edu.upc.dsa.exceptions.PasswordNotMatchException;
import edu.upc.dsa.exceptions.UsuarioNotFoundException;
import edu.upc.dsa.exceptions.UsuarioYaExisteException;
import edu.upc.dsa.manager.UsuarioManager;
import edu.upc.dsa.manager.UsuarioManagerImpl;
import edu.upc.dsa.models.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api(value = "/usuarios", description = "Servicio de usuarios")
@Path("/usuarios")
public class UsuarioService {

    final static Logger logger = Logger.getLogger(UsuarioService.class); // Log4j
    private UsuarioManager um = UsuarioManagerImpl.getInstance();

    public UsuarioService() {
        this.um = UsuarioManagerImpl.getInstance();
        IniciarDatos.init(); // Cargar datos de prueba solo una vez

    }

    @POST
    @ApiOperation(value = "Crea al usuario", notes = "Registro de nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuario registrado correctamente", response = Usuario.class),
            @ApiResponse(code = 500, message = "Usuario ya existe")
    })

    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newUsuario(Usuario u) {
        try {
            this.um.addUsuario(u);
            return Response.status(201).entity("{\"mensaje\":\"Usuario registrado correctamente\"}").build();
        } catch (UsuarioYaExisteException e) {
            return Response.status(500).entity("{\"error\":\"Usuario ya existe\"}").build();
        }

    }

    //

    @POST
    @ApiOperation(value = "Login usuario", notes = "Inicio de sesión de un usuario")
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
            Usuario UsuarioLogueado = this.um.loginUsuario(u.getNombreUsu(), u.getPassword());
            return Response.status(201).entity(UsuarioLogueado).build();
        } catch (UsuarioNotFoundException e) {
            return Response.status(404).entity("{\"error\":\"Usuario no encontrado\"}").build();
        } catch (PasswordNotMatchException e) {
            return Response.status(401).entity("{\"error\":\"Contraseña incorrecta\"}").build();
        }
    }

}
