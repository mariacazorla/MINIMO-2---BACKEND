package edu.upc.dsa;

import edu.upc.dsa.exceptions.PasswordNotMatchException;
import edu.upc.dsa.exceptions.UsuarioNotFoundException;
import edu.upc.dsa.exceptions.UsuarioYaExisteException;
import edu.upc.dsa.models.Usuario;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class ProductManagerImpl implements ProductManager{

    private static ProductManager instance; // singleton
    protected List<Usuario> usuarios;
    final static Logger logger = Logger.getLogger(ProductManagerImpl.class);

    private ProductManagerImpl() {
        this.usuarios = new LinkedList<>();
    }

    // Patron singleton
    public static ProductManager getInstance() {
        if (instance==null) instance = new ProductManagerImpl();
        return instance;
    }

    @Override
    public Usuario addUsuario(Usuario u) throws UsuarioYaExisteException {
        logger.info("Nuevo usuario " + u);
        String NombreUsu = u.getNombreUsu();
        Usuario comprobar = comprobarUsuario(u.getNombreUsu());
        if (comprobar != null){

            logger.error("Usuario con "+NombreUsu + " ya existe");
            throw new UsuarioYaExisteException("Usuario con "+NombreUsu + " ya existe");
        }
        this.usuarios.add(u);
        logger.info("Nuevo usuario añadido : "+u);
        logger.info("Usuarios: " +this.usuarios);
        return u;

    }

    @Override
    public Usuario addUsuario(String nombreUsu, String password) {
        return this.addUsuario(new Usuario(nombreUsu, password));
    }

    @Override
    public Usuario comprobarUsuario(String nombreUsu) {
        logger.info("getUsuario("+ nombreUsu +")");
        for (Usuario u: this.usuarios) {
            if (u.getNombreUsu().equals(nombreUsu)) {
                logger.info("getUsuario("+ nombreUsu +"): "+u);
                return u;
            }
        }

        logger.warn(nombreUsu+ " no encontrado ");
        return null;
    }

    @Override
    public Usuario getUsuario(String nombreUsu) throws UsuarioNotFoundException {
        logger.info("getUsuario("+ nombreUsu +")");
        for (Usuario u: this.usuarios) {
            if (u.getNombreUsu().equals(nombreUsu)) {
                logger.info("getUsuario("+ nombreUsu +"): "+u);
                return u;
            }
        }

        logger.warn(nombreUsu+ " no encontrado ");
        throw new UsuarioNotFoundException(nombreUsu+ " no encontrado ");
    }

    @Override
    public Usuario loginUsuario(String nombreUsu, String password) throws PasswordNotMatchException {
        Usuario u = getUsuario(nombreUsu);
        if(u.getPassword().equals(password)){
            logger.info("Login exitoso");
            return u;
        }
        logger.warn(password+ "no coincide");
        throw new PasswordNotMatchException(password+ "no coincide");
    }

    @Override
    public void clear() {
        this.usuarios.clear();
    }

    @Override
    public int sizeUsuarios() {
        int users = this.usuarios.size();
        logger.info(users + " usuarios");
        return users;
    }
}
