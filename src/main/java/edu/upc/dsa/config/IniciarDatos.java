package edu.upc.dsa.config;

import edu.upc.dsa.manager.TiendaManager;
import edu.upc.dsa.manager.TiendaManagerImpl;
import edu.upc.dsa.manager.UsuarioManager;
import edu.upc.dsa.manager.UsuarioManagerImpl;
import edu.upc.dsa.models.CategoriaObjeto;
import edu.upc.dsa.models.Insignia;

public class IniciarDatos {

    private static final String BASE_URL = "http://10.0.2.2:8080";
    public static void init() {
        UsuarioManager um = UsuarioManagerImpl.getInstance();
        TiendaManager tm = TiendaManagerImpl.getInstance();

        // Cargar datos de usuarios si no hay usuarios
        if (um.sizeUsuarios() == 0) {
            um.addUsuario("Paco", "1234");
            um.addUsuario("Ana", "1234");
            um.addUsuario("Miguel", "1234");
            um.addUsuario("Diego", "1234");
        }

        // Cargar productos de la tienda si no hay ninguno
        if (tm.sizeProductos() == 0 ) {
            tm.addProducto("1","Espada", 30, "/img/espada.jpg", "Una espada", CategoriaObjeto.ARMAS);
            tm.addProducto("2", "Armadura", 50, "/img/armadura.png", "Una armadura", CategoriaObjeto.ARMADURAS);
            tm.addProducto("3", "Poción", 20, "/img/pocion.png", "Una pocion", CategoriaObjeto.POCIONES);
        }

        // Cargar insignias de prueba en memoria (si aún no existen)
        if (um.getInsignias("Paco").isEmpty()) {
            um.addInsigniaToUser("Paco", new Insignia("Maestro del universo", BASE_URL +"/img/maestro.png"));
            um.addInsigniaToUser("Paco", new Insignia("Becario enfurismado", BASE_URL +"/img/becario.png"));
        }
        if (um.getInsignias("Ana").isEmpty()) {
            um.addInsigniaToUser("Ana", new Insignia("Dependienta estrella", BASE_URL +"/img/dependienta.png"));
        }
    }
}
