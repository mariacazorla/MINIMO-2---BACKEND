package edu.upc.dsa.config;

import edu.upc.dsa.manager.UsuarioManager;
import edu.upc.dsa.manager.UsuarioManagerImpl;

public class IniciarDatos {

    public static void init() {
        // Obtener instancias de los managers
        UsuarioManager um = UsuarioManagerImpl.getInstance();

        // Cargar datos de usuarios si no hay usuarios
        if (um.sizeUsuarios() == 0) {
            um.addUsuario("Paco", "1234");
            um.addUsuario("Ana", "1234");
            um.addUsuario("Miguel", "1234");
            um.addUsuario("Diego", "1234");
        }
    }

}
