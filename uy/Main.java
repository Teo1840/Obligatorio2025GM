package um.edu.uy;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.tads.MyList;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException, CsvValidationException {
        MyList<Integer, Pelicula> lista_peliculas = new MyList();
        MyList<Integer, Creditos> lista_creditos = new MyList();

        String[] fila;
        CSVReader reader1 = new CSVReader(new FileReader("src/main/resources/DATASETS v2/movies_metadata.csv"));
        fila = reader1.readNext();
        System.out.println(String.join(" | ", fila));
        for (Integer i=0; i<10; i++) {
            fila = reader1.readNext();
            Pelicula temp = new Pelicula(fila[0], fila[1], fila[2], fila[3], fila[4], fila[5], fila[6], fila[7], fila[8], fila[9], fila[10], fila[11], fila[12], fila[13], fila[14], fila[15], fila[16], fila[17], fila[18]);
            lista_peliculas.insert(lista_peliculas.getSize(),temp);
        }

        CSVReader reader2 = new CSVReader(new FileReader("src/main/resources/DATASETS v2/credits.csv"));
        fila = reader2.readNext();
        System.out.println(String.join(" | ", fila));
        for (Integer i=0; i<1; i++) {
            fila = reader2.readNext();
            System.out.println(fila[0]);
        }

        System.out.println("TERMINO EL TEST");

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
