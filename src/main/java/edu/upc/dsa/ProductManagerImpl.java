package edu.upc.dsa;

import edu.upc.dsa.exceptions.PasswordNotMatchException;
import edu.upc.dsa.exceptions.UsuarioNotFoundException;
import edu.upc.dsa.exceptions.UsuarioYaExisteException;
import edu.upc.dsa.models.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProductManagerImpl implements ProductManager{

    private static ProductManager instance; // singleton
    protected List<Usuario> usuarios;


    List<Producto> productos;
    List<Producto> secciones;
    Tienda tienda;

    final static Logger logger = Logger.getLogger(ProductManagerImpl.class);

    private ProductManagerImpl() {
        this.usuarios = new LinkedList<>();
        this.tienda = new Tienda("Tienda del juego");
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
    public void addProductoASeccion(String nombreSeccion, Producto producto) {
        for (Seccion s : this.tienda.getSecciones()) {
            if (s.getNombre().equalsIgnoreCase(nombreSeccion)) {
                s.agregarProducto(producto);
                logger.info("Producto añadido a la sección " + nombreSeccion + ": " + producto);
                return;
            }
        }

        // Si la sección no existe, se puede crear automáticamente (opcional):
        Seccion nueva = new Seccion(nombreSeccion);
        nueva.agregarProducto(producto);
        this.tienda.agregarSeccion(nueva);
        logger.info("Sección no encontrada. Se ha creado la sección " + nombreSeccion + " y se ha añadido el producto: " + producto);
    }

    //TIENDA
    @Override
    public Producto buscarProductoPorId(String idProducto) {
        for (Seccion seccion : tienda.getSecciones()) {
            for (Producto p : seccion.getProductos()) {
                if (p.getId().equals(idProducto)) return p;
            }
        }
        return null;
    }

    @Override
    public List<Producto> buscarProductosPorNombre(String nombre) {
        List<Producto> resultados = new ArrayList<>();
        for (Seccion seccion : tienda.getSecciones()) {
            for (Producto p : seccion.getProductos()) {
                if (p.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                    resultados.add(p);
                }
            }
        }
        return resultados;
    }

    @Override
    public boolean comprarProducto(String idProducto, String nombreUsuario) {
        Usuario usuario = comprobarUsuario(nombreUsuario);
        if (usuario == null) return false;
        Producto producto = buscarProductoPorId(idProducto);
        if (producto == null) return false;

        logger.info(nombreUsuario + " ha comprado el producto " + producto.getNombre());
        return true;
    }


    @Override
    public List<Producto> listarProductosPorSeccion(String nombreSeccion) {
        for (Seccion s : tienda.getSecciones()) {
            if (s.getNombre().equalsIgnoreCase(nombreSeccion)) {
                return s.getProductos();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public List<Producto> listarProductos() {
        List<Producto> productos = new ArrayList<>();
        for (Seccion seccion : tienda.getSecciones()) {
            productos.addAll(seccion.getProductos());
        }
        return productos;
    }


    /*@Override
    public void clear() {
        this.usuarios.clear();
    }*/

    @Override
    public void clear() {
        this.usuarios.clear();
        if (this.tienda != null) {
            this.tienda.getSecciones().clear();  // <-- Limpia secciones y por tanto productos
        }
    }


    @Override
    public int sizeUsuarios() {
        int users = this.usuarios.size();
        logger.info(users + " usuarios");
        return users;
    }


}

/*@Override
    public List<Seccion> listarSecciones() {
        return this.tienda.getSecciones();
    }*/
