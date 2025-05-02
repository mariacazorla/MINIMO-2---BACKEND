package edu.upc.dsa.manager;

import edu.upc.dsa.models.Partida;

public interface PartidaManager {

    Partida createPartida(Partida p);
    Partida createPartida(String id_partida, String id_usuario, Integer vidas, Integer monedas, Integer puntuacion);
    //id_usuario es el nombre del usuario
    Partida comprobarPartida(String id_partida);
    Partida getPartida(String id_usuario);
    Partida addMonedas(String id_usuario);
    Partida comprarProducto(String id_producto);

    //Por ahora no
    //Partida addPuntuacion(String usuario);

    void clear();
    int sizePartidas(Partida p);
}
