package edu.upc.dsa.config;

import io.jsonwebtoken.Jwts;
import javax.ws.rs.container.*;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@PreMatching
public class JWTAuthFilter implements ContainerRequestFilter {

    private static final String secretKey = "d42a2373271508dae325e933cddcfe13d504512272bdb6e89123f2e80717ad9d"; // Clave secreta

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Obtener el encabezado "Authorization"
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Token no proporcionado o inválido\"}")
                    .build());
            return;
        }

        // Extraer el token del encabezado
        String token = authorizationHeader.substring(7);

        // Validar el token
        try {
            // Usamos `Jwts.parserBuilder()` para la validación de los JWT en la versión 0.12.3
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token); // Parsear y validar el token

        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Token inválido\"}")
                    .build());
        }
    }
}