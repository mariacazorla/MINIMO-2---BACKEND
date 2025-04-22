package edu.upc.dsa.manager;

import edu.upc.dsa.models.Producto;
import edu.upc.dsa.models.Seccion;

import java.util.List;

public interface TiendaManager {

    //TIENDA
    // Para que el suaurio compre
    boolean comprarProducto(String idProducto, String nombreUsuario);

    // Métodos de consulta para la interfaz
    List<Producto> listarProductos();                       // Todos los productos
    List<Producto> listarProductosPorSeccion(String nombreSeccion); // Productos de una sección concreta
    Producto buscarProductoPorId(String idProducto);        // Detalle de un producto
    List<Producto> buscarProductosPorNombre(String nombre); // Búsqueda por nombre (pensado para un buscador)
    void addProductoASeccion(String nombreSeccion, Producto producto);
    List<Seccion> getSecciones();

    public void clear();

}
