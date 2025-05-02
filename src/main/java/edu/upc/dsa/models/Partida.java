package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.List;

public class Partida {

    private Integer vidas;
    private Integer monedas;
    private Integer puntuacion;
    private List<Producto> objetos;

    public Partida() {
        this.objetos = new ArrayList<>();
    }
    public Partida(Integer vidas, Integer monedas, Integer puntuacion) {
        this.setVidas(vidas);
        this.setMonedas(monedas);
        this.setPuntuacion(puntuacion);
        this.objetos = new ArrayList<>();
    }

    public Integer getVidas() { return this.vidas; }
    public void setVidas(Integer vidas){ this.vidas = vidas; }
    public Integer getMonedas() { return this.monedas; }
    public void setMonedas(Integer monedas) { this.monedas = monedas; }
    public Integer getPuntuacion() { return this.puntuacion; }
    public void setPuntuacion(Integer puntuacion) { this.puntuacion = puntuacion; }

    @Override
    public String toString() {
        return "Usuario [vidas="+vidas+"]";
    }
    
}
