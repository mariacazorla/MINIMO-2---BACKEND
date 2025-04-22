package edu.upc.dsa.manager;

import edu.upc.dsa.exceptions.PasswordNotMatchException;
import edu.upc.dsa.exceptions.UsuarioNotFoundException;
import edu.upc.dsa.exceptions.UsuarioYaExisteException;
import edu.upc.dsa.models.Usuario;

public interface UsuarioManager {

    // Registrar usuario
    public Usuario addUsuario(Usuario u) throws UsuarioYaExisteException;
    public Usuario addUsuario(String nombreUsu, String password);
    public Usuario comprobarUsuario(String nombreUsu);
    // Registrar usuario
    // Login
    public Usuario getUsuario(String nombreUsu) throws UsuarioNotFoundException;
    public Usuario loginUsuario(String nombreUsu, String password) throws PasswordNotMatchException;
    // Login

    public void clear();
    public int sizeUsuarios();

}
