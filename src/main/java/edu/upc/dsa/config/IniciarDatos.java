package edu.upc.dsa.config;

import edu.upc.dsa.manager.TiendaManager;
import edu.upc.dsa.manager.TiendaManagerImpl;
import edu.upc.dsa.manager.UsuarioManager;
import edu.upc.dsa.manager.UsuarioManagerImpl;
import edu.upc.dsa.models.Producto;

public class IniciarDatos {

    public static void init() {
        // Obtener instancias de los managers
        UsuarioManager um = UsuarioManagerImpl.getInstance();
        TiendaManager tm = TiendaManagerImpl.getInstance();

        // Cargar datos de usuarios si no hay usuarios
        if (um.sizeUsuarios() == 0) {
            um.addUsuario("Paco", "1234");
            um.addUsuario("Ana", "1234");
            um.addUsuario("Miguel", "1234");
            um.addUsuario("Lopez", "1234");
        }

        // Cargar datos de productos si no hay productos
        if (tm.listarProductos().isEmpty()) {
            Producto p1 = new Producto("1", "Espada mágica", 50);
            Producto p2 = new Producto("2", "Poción de vida", 25);
            Producto p3 = new Producto("3", "Traje ninja", 40);

            // Añadir productos a las secciones
            tm.addProductoASeccion("skins", p1);
            tm.addProductoASeccion("vidas", p2);
            tm.addProductoASeccion("skins", p3);
        }
    }

}
