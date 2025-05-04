package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class Objeto {
    private String id_objeto;
    private String nombre;
    private int precio;
    private CategoriaObjeto categoria;
    //private String foto; // ruta de foto, pensando en BBDD
    //private String descripcion;
    //private int stock;

    public Objeto(){
        setId_objeto(RandomUtils.getId());
    }

    public Objeto(String id_objeto, String nombre, int precio, CategoriaObjeto categoria) {
        this(); // Llama al constructor sin parámetros (asigna un id aleatorio).
        if (id_objeto != null) setId_objeto(id_objeto);
        setNombre(nombre);
        setPrecio(precio);
        setCategoria(categoria);
    }

    public String getId_objeto() { return id_objeto; }
    public void setId_objeto(String id_objeto) { this.id_objeto = id_objeto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getPrecio() { return precio; }
    public void setPrecio(int precio) { this.precio = precio; }

    public CategoriaObjeto getCategoria() { return categoria; }
    public void setCategoria(CategoriaObjeto categoria){ this.categoria = categoria; }

    @Override
    public String toString() {
        return "Objeto [id_objeto=" + id_objeto + ", nombre=" + nombre + ", precio=" + precio + ", categoria=" + categoria + "]";
    }
}
