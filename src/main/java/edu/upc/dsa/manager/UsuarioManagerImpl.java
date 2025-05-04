package edu.upc.dsa.manager;

import edu.upc.dsa.exceptions.usuario.PasswordNotMatchException;
import edu.upc.dsa.exceptions.usuario.UsuarioNotFoundException;
import edu.upc.dsa.exceptions.usuario.UsuarioYaExisteException;
import edu.upc.dsa.models.*;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.util.LinkedList;
import java.util.List;

public class UsuarioManagerImpl implements UsuarioManager{

    private static UsuarioManager instance;
    protected List<Usuario> usuarios;
    final static Logger logger = Logger.getLogger(UsuarioManagerImpl.class);

    private UsuarioManagerImpl() {
        this.usuarios = new LinkedList<>();
    }

    // Patron singleton
    public static UsuarioManager getInstance() {
        if (instance == null) instance = new UsuarioManagerImpl();
        return instance;
    }

    @Override
    public Usuario addUsuario(Usuario u) throws UsuarioYaExisteException {
        //logger.info("Nuevo usuario " + u);
        String nombreUsu = u.getNombreUsu();
        Usuario comprobar = comprobarUsuario(nombreUsu);
        if (comprobar != null){
            logger.error("Usuario con " + nombreUsu + " ya existe");
            throw new UsuarioYaExisteException("Usuario con " + nombreUsu + " ya existe");
        }

        // Cifrado de la contraseña
        String hashedPassword = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());
        u.setPassword(hashedPassword);

        this.usuarios.add(u);
        //logger.info("Usuario añadido: " + u);
        logger.info("Usuarios: " +this.usuarios);
        return u;
    }

    @Override
    public Usuario addUsuario(String nombreUsu, String password) {
        return this.addUsuario(new Usuario(nombreUsu, password));
    }

    @Override
    public Usuario comprobarUsuario(String nombreUsu) {
        //logger.info("getUsuario("+ nombreUsu +")");
        for (Usuario u: this.usuarios) {
            if (u.getNombreUsu().equals(nombreUsu)) {
                logger.info("getUsuario("+ nombreUsu +"): "+u);
                return u;
            }
        }
        //logger.info(nombreUsu+ " no encontrado ");
        return null;
    }

    @Override
    public Usuario getUsuario(String nombreUsu) throws UsuarioNotFoundException {
        Usuario u = comprobarUsuario(nombreUsu);
        if (u == null) throw new UsuarioNotFoundException(nombreUsu + " no encontrado");
        return u;
    }

    @Override
    public Usuario loginUsuario(String nombreUsu, String password) throws PasswordNotMatchException {
        Usuario u = getUsuario(nombreUsu);
        if (u == null || !BCrypt.checkpw(password, u.getPassword())) {
            throw new PasswordNotMatchException("Credenciales incorrectas");
        }
        logger.info("Login exitoso para: " + nombreUsu);
        return u;
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return this.usuarios;
    }

    @Override
    public int sizeUsuarios() {
        return this.usuarios.size();
    }

    @Override
    public void clear() {
        this.usuarios.clear();
    }

}
