package um.edu.uy;

import java.util.ArrayList;

public class Saga {
    private String id;
    private String nombre;
    private String poster;
    private String fondo;
    private ArrayList<Pelicula> lista_pelicuas;

    public Saga(String id, String nombre, String poster, String fondo, ArrayList<Pelicula> lista_pelicuas) {
        this.id = id;
        this.nombre = nombre;
        this.poster = poster;
        this.fondo = fondo;
        this.lista_pelicuas = lista_pelicuas;


    }

    public double calcularIngresos() {
        double suma = 0;
        for (int i = 0; i < this.lista_pelicuas.size(); i++) {
            suma+= this.lista_pelicuas.get(i).getIngreso();
        }
        return suma;
    }
}
