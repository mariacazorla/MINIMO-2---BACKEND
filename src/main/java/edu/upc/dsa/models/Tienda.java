package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.List;

public class Tienda {
    private List<Seccion> secciones;

    public Tienda(String tiendaDelJuego) {
        this.secciones = new ArrayList<>();
    }

    public void agregarSeccion(Seccion seccion) {
        secciones.add(seccion);
    }

    public List<Seccion> getSecciones() {
        return secciones;
    }

    public Seccion buscarSeccionPorNombre(String nombre) {
        for (Seccion s : secciones) {
            if (s.getNombre().equalsIgnoreCase(nombre)) return s;
        }
        return null;
    }

    public Producto buscarProductoEnTienda(String idProducto) {
        for (Seccion s : secciones) {
            Producto p = s.buscarProductoPorId(idProducto);
            if (p != null) return p;
        }
        return null;
    }
}
