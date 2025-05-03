package edu.upc.dsa.manager;

import edu.upc.dsa.exceptions.usuario.PasswordNotMatchException;
import edu.upc.dsa.exceptions.usuario.UsuarioNotFoundException;
import edu.upc.dsa.exceptions.usuario.UsuarioYaExisteException;
import edu.upc.dsa.models.Usuario;

import java.util.List;

public interface UsuarioManager {

    // Registrar usuario
    Usuario addUsuario(Usuario u) throws UsuarioYaExisteException;
    Usuario addUsuario(String nombreUsu, String password);
    Usuario comprobarUsuario(String nombreUsu);
    // Registrar usuario
    // Login
    Usuario getUsuario(String nombreUsu) throws UsuarioNotFoundException;
    Usuario loginUsuario(String nombreUsu, String password) throws PasswordNotMatchException;
    // Login

    List<Usuario> getAllUsuarios();

    void clear();
    int sizeUsuarios();

}
