package edu.upc.dsa.models;

public class Usuario {
    private String nombreUsu;
    private String password;

    public Usuario() {}
    public Usuario(String nombreUsu, String password) {
        this.setNombreUsu(nombreUsu);
        this.setPassword(password);
    }

    public String getNombreUsu() { return this.nombreUsu; }
    public void setNombreUsu(String nombreUsu){ this.nombreUsu = nombreUsu; }
    public String getPassword() { return this.password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "Usuario [nombreUsu="+nombreUsu+", password="+password+"]";
    }
}
