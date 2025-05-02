package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nombreUsu;
    private String password;
    private List<Partida> partidas;

    public Usuario() {
        this.partidas = new ArrayList<>();
    }
    public Usuario(String nombreUsu, String password) {
        this.setNombreUsu(nombreUsu);
        this.setPassword(password);
        this.partidas = new ArrayList<>();
    }

    public String getNombreUsu() { return this.nombreUsu; }
    public void setNombreUsu(String nombreUsu){ this.nombreUsu = nombreUsu; }
    public String getPassword() { return this.password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "Usuario [nombreUsu="+nombreUsu+", partidas="+partidas+"]";
    }
}
