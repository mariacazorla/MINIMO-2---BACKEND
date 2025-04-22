package edu.upc.dsa.manager;

import edu.upc.dsa.models.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TiendaManagerImpl implements TiendaManager {

    private static TiendaManager instance;
    private List<Seccion> secciones;
    final static Logger logger = Logger.getLogger(TiendaManagerImpl.class);

    private TiendaManagerImpl() {
        this.secciones = new ArrayList<>();
    }

    // Patron singleton
    public static TiendaManager getInstance() {
        if (instance == null) instance = new TiendaManagerImpl();
        return instance;
    }

    @Override
    public void addProductoASeccion(String nombreSeccion, Producto producto) {
        for (Seccion s : this.secciones) {
            if (s.getNombre().equalsIgnoreCase(nombreSeccion)) {
                s.agregarProducto(producto);
                logger.info("Producto añadido a la sección " + nombreSeccion + ": " + producto);
                return;
            }
        }
        // Si no existe, crear la sección
        Seccion nueva = new Seccion(nombreSeccion);
        nueva.agregarProducto(producto);
        this.secciones.add(nueva);
        logger.info("Sección no encontrada. Se ha creado la sección " + nombreSeccion + " y se ha añadido el producto: " + producto);
    }

    @Override
    public Producto buscarProductoPorId(String idProducto) {
        for (Seccion seccion : secciones) {
            for (Producto p : seccion.getProductos()) {
                if (p.getId().equals(idProducto)) return p;
            }
        }
        return null;
    }

    @Override
    public List<Producto> buscarProductosPorNombre(String nombre) {
        List<Producto> resultados = new ArrayList<>();
        for (Seccion seccion : secciones) {
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
        //No deberia ser necesario con jwt
        //Usuario usuario = comprobarUsuario(nombreUsuario);
        //if (usuario == null) return false;
        Producto producto = buscarProductoPorId(idProducto);
        if (producto == null) return false;

        logger.info(nombreUsuario + " ha comprado el producto " + producto.getNombre());
        return true;
    }

    @Override
    public List<Producto> listarProductosPorSeccion(String nombreSeccion) {
        for (Seccion s : secciones) {
            if (s.getNombre().equalsIgnoreCase(nombreSeccion)) {
                return s.getProductos();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public List<Producto> listarProductos() {
        List<Producto> productos = new ArrayList<>();
        for (Seccion seccion : secciones) {
            productos.addAll(seccion.getProductos());
        }
        return productos;
    }

    @Override
    public List<Seccion> getSecciones() {
        return this.secciones;
    }

    @Override
    public void clear() {
        this.secciones.clear();
    }

}
