package edu.upc.dsa.models;

// Esta clase debe estar en el mismo paquete (edu.upc.dsa.models o services si prefieres)
public class CompraRequest {
    private String idProducto;
    private String nombreUsuario;

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}

