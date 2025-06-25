package um.edu.uy;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.entidades.*;
import um.edu.uy.tads.MyList;
import um.edu.uy.tads.MyTree;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException, CsvValidationException {
        MyList<Integer, Pelicula> lista_peliculas = new MyList();
        MyList<Integer, Creditos> lista_creditos = new MyList();
        MyList<Integer, Saga> lista_sagas = new MyList();
        MyTree<String, Lenguajes> lista_lenguajes = new MyTree();
        MyList<Integer, Genero> lista_generos = new MyList();
        MyList<Integer, Evaluacion> lista_evaluaciones = new MyList();
        MyList<Integer, Paises_Produccion> lista_paises = new MyList();
        MyList<Integer, Productora> lista_productoras = new MyList();
        MyList<Integer, Actor> lista_actores = new MyList();
        MyList<Integer, Equipo_Produccion> lista_equipo = new MyList();

        String[] fila;
        CSVReader reader1 = new CSVReader(new FileReader("src/main/resources/DATASETS v2/movies_metadata.csv"));
        fila = reader1.readNext();
        for (Integer i=0; i<10; i++) {
            fila = reader1.readNext();
            Pelicula temp = new Pelicula(fila[0], fila[1], fila[2], fila[3],
                    fila[4], fila[5], fila[6], fila[7], fila[8], fila[9],
                    fila[10], fila[11], fila[12], fila[13], fila[14],
                    fila[15], fila[16], fila[17], fila[18]);
            lista_peliculas.insert(Integer.parseInt(fila[5]), temp);
            //System.out.println(fila[15]);
            //System.out.println(fila[18]);
            if (temp.getSaga() != null) {
                lista_sagas.insert(temp.getSaga().getId(), temp.getSaga());
            }
            for (Integer j=0; j<(temp.getLista_lenguajes_hablados().getSize()); j++) {
                lista_lenguajes.insert(temp.getLista_lenguajes_hablados().getNumber(j).getIso_639_1(), temp.getLista_lenguajes_hablados().getNumber(j));
            }

        }

        CSVReader reader2 = new CSVReader(new FileReader("src/main/resources/DATASETS v2/credits.csv"));
        fila = reader2.readNext();
        for (Integer i=0; i<1; i++) {
            fila = reader2.readNext();
            Creditos temp = new Creditos(fila[0],fila[1],fila[2]);
            lista_creditos.insert(Integer.parseInt(fila[2]),temp);
        }

        CSVReader readerEvaluaciones = new CSVReader(new FileReader("src/main/resources/DATASETS v2/ratings_1mm.csv"));
        fila = readerEvaluaciones.readNext();
        for (Integer i=0; i<10; i++) {
            fila = readerEvaluaciones.readNext();
            Evaluacion temp = new Evaluacion(fila[0],fila[1],fila[2],fila[3]);
            lista_evaluaciones.insert(Integer.parseInt(fila[3]),temp);
        }
        System.out.println("ok");

        Scanner entrada = new Scanner(System.in);
        String opcion = entrada.nextLine();
        while (Integer.parseInt(opcion) != 3) {
            System.out.print("Bienvenido a UMovie");
            System.out.print("MENU PRINCIPAL:");
            System.out.print("Seleccione la opción que desee:");
            System.out.print("1. Carga de Datos");
            System.out.print("2. Ejecutar Consultas");
            System.out.print("3. Salir");
            if (Integer.parseInt(opcion) == 1) {
                //subir datos
            }
            if (Integer.parseInt(opcion) == 2) {
                System.out.print("Seleccione la opción que desee:");
                System.out.print("1. Top 5 de las mejores películas con más calificaciones por idioma.");
                System.out.print("2. Top 10 de las películas que mejor calificación media tienen por parte de los usuarios.");
                System.out.print("3. Top 5 de las colecciones que más ingresos generaron.");
                System.out.print("4. Top 10 de los directores que mejor calificación tienen.");
                System.out.print("5. Actor con más calificaciones recibidas en cada mes del año.");
                System.out.print("6. Usuarios con más calificaciones por género");
                System.out.print("7. Salir");
            }
        }
    }
}
