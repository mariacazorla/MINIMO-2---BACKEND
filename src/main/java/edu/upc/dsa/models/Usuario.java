package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nombreUsu;
    private String password;
    private List<Partida> partidas;

    // Nueva lista de insignias
    private List<Insignia> insignias;

    public Usuario() {
        partidas = new ArrayList<>();
        insignias = new ArrayList<>();
    }

    public Usuario(String nombreUsu, String password) {
        this(); // inicializa partidas e insignias
        setNombreUsu(nombreUsu);
        setPassword(password);
    }

    public String getNombreUsu() { return nombreUsu; }
    public void setNombreUsu(String nombreUsu) { this.nombreUsu = nombreUsu; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Partida> getPartidas() { return partidas; }
    public void setPartidas(List<Partida> partidas) { this.partidas = partidas; }

    // Getters y setters para insignias
    public List<Insignia> getInsignias() { return insignias; }
    public void setInsignias(List<Insignia> insignias) { this.insignias = insignias; }

    @Override
    public String toString() {
        return "Usuario [nombreUsu=" + nombreUsu +
                ", partidas=" + partidas +
                ", insignias=" + insignias + "]";
    }
}
