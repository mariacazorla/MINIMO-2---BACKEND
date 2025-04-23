package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.List;
 //SECCIONES DE LA TIENDA. Por ejemplo: skin, vidas, armas, etc.
public class Seccion {
    private String nombre;
    private List<Producto> productos;

    public Seccion(String nombre) {
        this.nombre = nombre;
        this.productos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public Producto buscarProductoPorId(String id) {
        for (Producto p : productos) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }

     @Override
     public String toString() {
         return "Seccion [nombre="+nombre+" , List<Producto>="+ productos +"]";
     }
}

