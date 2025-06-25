package um.edu.uy.entidades;

import java.util.ArrayList;

public class Saga {
    private Integer id;
    private String name;
    private String poster_path;
    private String backdrop_path;
    private ArrayList<Pelicula> lista_pelicuas;

    // Constructor vacío requerido por Gson
    public Saga() {}

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }

    //public double calcularIngresos() {
    //    double suma = 0;
    //    for (int i = 0; i < this.lista_pelicuas.size(); i++) {
    //        suma+= this.lista_pelicuas.get(i).getIngreso();
    //    }
    //    return suma;
    //}
}