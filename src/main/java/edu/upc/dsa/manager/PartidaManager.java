package edu.upc.dsa.manager;

import edu.upc.dsa.models.Partida;

import java.util.List;

public interface PartidaManager {

    //Funciona como crear partida
    //id_usuario es el nombre del usuario
    Partida addPartida(Partida p);
    Partida addPartida(String id_partida, String id_usuario, Integer vidas, Integer monedas, Integer puntuacion);

    //Obtener todas las partidas de un usuario
    List<Partida> getPartidas(String id_usuario);

    void deletePartida(String id_usuario, String id_partida);

    Partida addMonedas(String id_usuario);
    Partida comprarProducto(String id_producto);

    //Por ahora no
    //Partida addPuntuacion(String usuario);

    //Elimina todas las partidas de todos los usuarios
    void clear();
    //solo de un usuario
    int sizePartidas(String id_usuario);
}
