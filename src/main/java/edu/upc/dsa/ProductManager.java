package edu.upc.dsa;

import edu.upc.dsa.exceptions.PasswordNotMatchException;
import edu.upc.dsa.exceptions.UsuarioNotFoundException;
import edu.upc.dsa.exceptions.UsuarioYaExisteException;
import edu.upc.dsa.models.*;

import java.util.List;

public interface ProductManager {

    // Registrar usuario
    public Usuario addUsuario(Usuario u) throws UsuarioYaExisteException;
    public Usuario addUsuario(String nombreUsu, String password);
    public Usuario comprobarUsuario(String nombreUsu);
    // Registrar usuario
    // Login
    public Usuario getUsuario(String nombreUsu) throws UsuarioNotFoundException;
    public Usuario loginUsuario(String nombreUsu, String password) throws PasswordNotMatchException;
    // Login




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
    public int sizeUsuarios();

}
