package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class Partida {

    private String id_partida;
    //id_usuario es el nombre del usuario
    private String id_usuario;
    private Integer vidas;
    private Integer monedas;
    private Integer puntuacion;
    private List<Producto> objetos;

    public Partida() {
        this.setId_partida(RandomUtils.getId());
        this.objetos = new ArrayList<>();
    }
    public Partida(String id_partida, String id_usuario, Integer vidas, Integer monedas, Integer puntuacion) {
        this(); // Llama al constructor sin parámetros (asigna un id aleatorio).
        if (id_partida != null) this.setId_partida(id_partida); // Si se proporciona un id, lo sobrescribe.
        this.setId_usuario(id_usuario);
        this.setVidas(vidas);
        this.setMonedas(monedas);
        this.setPuntuacion(puntuacion);
        this.objetos = new ArrayList<>();
    }

    public String getId_partida() { return this.id_partida; }
    public void setId_partida(String id_partida) { this.id_partida = id_partida; }
    public String getId_usuario() { return this.id_usuario; }
    public void setId_usuario(String id_usuario) { this.id_usuario = id_usuario; }
    public Integer getVidas() { return this.vidas; }
    public void setVidas(Integer vidas){ this.vidas = vidas; }
    public Integer getMonedas() { return this.monedas; }
    public void setMonedas(Integer monedas) { this.monedas = monedas; }
    public Integer getPuntuacion() { return this.puntuacion; }
    public void setPuntuacion(Integer puntuacion) { this.puntuacion = puntuacion; }

    @Override
    public String toString() {
        return "Partida [id_partida="+id_partida+", id_usuario="+id_usuario+", vidas="+vidas+", monedas="+monedas+", puntuacion="+puntuacion+", objetos="+objetos+"]";
    }

}
