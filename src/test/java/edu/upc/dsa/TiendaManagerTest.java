package edu.upc.dsa;

import edu.upc.dsa.manager.TiendaManager;
import edu.upc.dsa.manager.TiendaManagerImpl;
import edu.upc.dsa.manager.UsuarioManager;
import edu.upc.dsa.manager.UsuarioManagerImpl;
import edu.upc.dsa.models.Producto;
import edu.upc.dsa.util.IniciarDatosTests;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TiendaManagerTest {

    final static Logger logger = Logger.getLogger(TiendaManagerTest.class);
    TiendaManager tm;
    UsuarioManager um;

    @Before
    public void setUp() {
        this.tm = TiendaManagerImpl.getInstance();
        this.um = UsuarioManagerImpl.getInstance();
        this.tm.clear(); // Nos aseguramos que no haya datos anteriores
        this.um.clear();

        IniciarDatosTests.initUsuarios(this.um);
        IniciarDatosTests.initProductos(this.tm);
    }

    @After
    public void tearDown() {
        this.tm.clear();
        this.um.clear();
    }

    @Test
    public void testBuscarProductoPorId() {
        Producto p = tm.buscarProductoPorId("1");
        assertNotNull(p);
        assertEquals("Espada mágica", p.getNombre());
    }

    @Test
    public void testBuscarProductosPorNombre() {
        List<Producto> resultados = tm.buscarProductosPorNombre("poción");
        assertEquals(1, resultados.size());
        assertEquals("Poción de vida", resultados.get(0).getNombre());
    }

    @Test
    public void testComprarProducto() {
        boolean comprado = tm.comprarProducto("1", "Miguel");
        assertTrue(comprado);
    }

    @Test
    public void testListarProductosPorSeccion() {
        List<Producto> productos = tm.listarProductosPorSeccion("skins");
        assertEquals(2, productos.size());
    }

    @Test
    public void testListarProductos() {
        List<Producto> todos = tm.listarProductos();
        assertEquals(3, todos.size());
    }

}
