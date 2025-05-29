package edu.upc.dsa.services;

import edu.upc.dsa.config.IniciarDatos;
import edu.upc.dsa.exceptions.usuario.PasswordNotMatchException;
import edu.upc.dsa.exceptions.usuario.UsuarioNotFoundException;
import edu.upc.dsa.exceptions.usuario.UsuarioYaExisteException;
import edu.upc.dsa.manager.UsuarioManager;
import edu.upc.dsa.manager.UsuarioManagerImpl;
import edu.upc.dsa.models.Insignia;
import edu.upc.dsa.models.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

@Api(value = "/usuarios", description = "Servicio de usuarios")
@Path("/usuarios")
public class UsuarioService {

    final static Logger logger = Logger.getLogger(UsuarioService.class);
    private UsuarioManager um = UsuarioManagerImpl.getInstance();
    private static final String secretKey = "d42a2373271508dae325e933cddcfe13d504512272bdb6e89123f2e80717ad9d";

    public UsuarioService() {
        this.um = UsuarioManagerImpl.getInstance();
        IniciarDatos.init(); // Carga datos de prueba (incluye algunas insignias)
    }

    // Método para generar el token JWT
    private String generateToken(Usuario u) {
        return Jwts.builder()
                .setSubject(u.getNombreUsu())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hora
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    @POST
    @ApiOperation(value = "Crea al usuario", notes = "Registro de nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuario registrado correctamente", response = Usuario.class),
            @ApiResponse(code = 500, message = "Usuario ya existe")
    })
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newUsuario(Usuario u) {
        try {
            this.um.addUsuario(u);
            return Response.status(201)
                    .entity("{\"mensaje\":\"Usuario registrado correctamente\"}")
                    .build();
        } catch (UsuarioYaExisteException e) {
            return Response.status(500)
                    .entity("{\"error\":\"Usuario ya existe\"}")
                    .build();
        }
    }

    @POST
    @ApiOperation(value = "Login usuario", notes = "Inicio de sesión de un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Login exitoso", response = Usuario.class),
            @ApiResponse(code = 404, message = "Usuario no encontrado"),
            @ApiResponse(code = 401, message = "Contraseña incorrecta")
    })
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUsuario(Usuario u) {
        try {
            Usuario usuarioLogueado = this.um.loginUsuario(u.getNombreUsu(), u.getPassword());
            String token = generateToken(usuarioLogueado);
            return Response.status(201)
                    .entity("{\"token\":\"" + token + "\"}")
                    .build();
        } catch (UsuarioNotFoundException e) {
            return Response.status(404)
                    .entity("{\"error\":\"Usuario no encontrado\"}")
                    .build();
        } catch (PasswordNotMatchException e) {
            return Response.status(401)
                    .entity("{\"error\":\"Contraseña incorrecta\"}")
                    .build();
        }
    }

    @GET
    @ApiOperation(value = "Obtiene las insignias de un usuario", notes = "Devuelve la lista de insignias asociadas al usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de insignias", response = Insignia.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Usuario no encontrado")
    })
    @Path("/{nombreUsu}/insignias")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInsigniasUsuario(@PathParam("nombreUsu") String nombreUsu) {
        try {
            List<Insignia> insignias = um.getInsignias(nombreUsu);
            return Response.ok(insignias).build();
        } catch (Exception e) {
            return Response.status(404)
                    .entity("{\"error\":\"Usuario no encontrado\"}")
                    .build();
        }
    }
}
