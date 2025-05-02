package edu.upc.dsa.util;

import edu.upc.dsa.manager.TiendaManager;
import edu.upc.dsa.manager.UsuarioManager;
import edu.upc.dsa.models.Producto;

public class IniciarDatosTests {
    public static void initUsuarios(UsuarioManager um) {
        um.addUsuario("Paco", "1234");
        um.addUsuario("Ana", "1234");
        um.addUsuario("Miguel", "1234");
        um.addUsuario("Diego", "1234");
    }

    public static void initProductos(TiendaManager tm) {
        tm.addProductoASeccion("skins", new Producto("1", "Espada mágica", 50));
        tm.addProductoASeccion("vidas", new Producto("2", "Poción de vida", 25));
        tm.addProductoASeccion("skins", new Producto("3", "Traje ninja", 40));
    }
}
