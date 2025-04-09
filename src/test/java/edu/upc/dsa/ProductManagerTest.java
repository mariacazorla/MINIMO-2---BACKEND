package edu.upc.dsa;

import edu.upc.dsa.exceptions.PasswordNotMatchException;
import edu.upc.dsa.exceptions.UsuarioNotFoundException;
import edu.upc.dsa.exceptions.UsuarioYaExisteException;
import edu.upc.dsa.models.Producto;
import edu.upc.dsa.models.Usuario;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ProductManagerTest {
    final static Logger logger = Logger.getLogger(ProductManagerTest.class);
    ProductManager pm;

    @Before
    public void setUp() {
        this.pm = ProductManagerImpl.getInstance();
        this.pm.addUsuario("Paco", "1234");
        this.pm.addUsuario("Lopez", "1234");
        this.pm.addUsuario("Ana", "1234");

        // Añadir productos a las secciones usando el método correcto
        Producto p1 = new Producto("1", "Espada mágica", 50);
        Producto p2 = new Producto("2", "Poción de vida", 25);
        Producto p3 = new Producto("3", "Traje ninja", 40);

        this.pm.addProductoASeccion("skins", p1);
        this.pm.addProductoASeccion("vidas", p2);
        this.pm.addProductoASeccion("skins", p3);

        // Añadir usuario que hace compra
        this.pm.addUsuario("Miguel", "1234");
    }

    @After
    public void tearDown() {
        this.pm.clear();
    }

    @Test
    public void addUsuarioTest() throws Exception {
        assertEquals(4, pm.sizeUsuarios()); // Incluye "Miguel"
        this.pm.addUsuario("John", "1234");
        assertEquals(5, pm.sizeUsuarios());
        Assert.assertThrows(UsuarioYaExisteException.class, () ->
                this.pm.addUsuario("John", "1234"));
    }

    @Test
    public void getUsuarioTest() throws Exception {
        assertEquals(4, pm.sizeUsuarios());
        Usuario u = this.pm.getUsuario("Paco");
        assertEquals("Paco", u.getNombreUsu());
        assertEquals("1234", u.getPassword());

        Assert.assertThrows(UsuarioNotFoundException.class, () ->
                this.pm.getUsuario("UUUUUU"));
    }

    @Test
    public void loginUsuarioTest() throws Exception {
        assertEquals(4, pm.sizeUsuarios());
        Usuario u = this.pm.loginUsuario("Paco", "1234");
        assertEquals("Paco", u.getNombreUsu());
        assertEquals("1234", u.getPassword());

        Assert.assertThrows(PasswordNotMatchException.class, () ->
                this.pm.loginUsuario("Paco", "PPPPPP"));
    }

    //TIENDA
    @Test
    public void testBuscarProductoPorId() {
        Producto p = pm.buscarProductoPorId("1");
        assertNotNull(p);
        assertEquals("Espada mágica", p.getNombre());
    }

    @Test
    public void testBuscarProductosPorNombre() {
        List<Producto> resultados = pm.buscarProductosPorNombre("poción");
        assertEquals(1, resultados.size());
        assertEquals("Poción de vida", resultados.get(0).getNombre());
    }

    @Test
    public void testComprarProducto() {
        boolean comprado = pm.comprarProducto("1", "Miguel");
        assertTrue(comprado);
    }

    @Test
    public void testListarProductosPorSeccion() {
        List<Producto> productos = pm.listarProductosPorSeccion("skins");
        assertEquals(2, productos.size());
    }

    @Test
    public void testListarProductos() {
        List<Producto> todos = pm.listarProductos();
        assertEquals(3, todos.size());
    }
}
