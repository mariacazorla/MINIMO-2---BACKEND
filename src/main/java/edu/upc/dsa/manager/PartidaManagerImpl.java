package edu.upc.dsa.manager;

import edu.upc.dsa.exceptions.partida.PartidaYaExisteException;
import edu.upc.dsa.models.Partida;
import edu.upc.dsa.models.Usuario;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class PartidaManagerImpl implements PartidaManager{

    private static PartidaManager instance;
    final static Logger logger = Logger.getLogger(PartidaManagerImpl.class);

    private PartidaManagerImpl() {}

    // Patron singleton
    public static PartidaManager getInstance() {
        if (instance == null) instance = new PartidaManagerImpl();
        return instance;
    }

    @Override
    public Partida createPartida(Partida p) {
        logger.info("Nueva partida " + p);
        String id_partida = p.getId_partida();
        Partida comprobar = comprobarPartida(id_partida);
        if (comprobar != null){
            logger.error("Partida con " + id_partida + " ya existe");
            throw new PartidaYaExisteException("Partida con " + id_partida + " ya existe");
        }
        try {
            Usuario usuario = UsuarioManagerImpl.getInstance().getUsuario(p.getId_usuario());
            logger.info("Todo: " +usuario);
            //Necesito la funcion de getPartida para continuar
            logger.info("mass  "+usuario);
        } catch (Exception e) {
            logger.error("No se pudo encontrar el usuario: " + p.getId_usuario(), e);
        }
        return p;
    }

    @Override
    public Partida createPartida(String id_partida, String id_usuario, Integer vidas, Integer monedas, Integer puntuacion) {
        return this.createPartida(new Partida(id_partida, id_usuario, vidas, monedas, puntuacion));
    }

    @Override
    public Partida comprobarPartida(String id_partida) {
        return null;
    }

    @Override
    public Partida getPartida(String usuario) {
        return null;
    }

    @Override
    public Partida addMonedas(String usuario) {
        return null;
    }

    @Override
    public Partida comprarProducto(String id_producto) {
        return null;
    }

    @Override
    public void clear() {
        //this.partidas.clear();
    }

    @Override
    public int sizePartidas(Partida p) {
        return 0;
    }
}
