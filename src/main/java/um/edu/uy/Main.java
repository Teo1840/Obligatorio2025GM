package um.edu.uy;

import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.consultas.Carga;
import um.edu.uy.consultas.Consultas;
import um.edu.uy.entidades.*;
import um.edu.uy.tads.*;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, CsvValidationException {

        MyList<Integer, Pelicula> lista_peliculas = new MyList<>();
        MyList<Integer, Creditos> lista_creditos = new MyList<>();
        MyList<Integer, Saga> lista_sagas = new MyList<>();
        MyList<String, Lenguajes> lista_lenguajes = new MyList<>();
        MyHash<Integer, Genero> lista_generos = new MyHashImpl<>();
        MyList<Integer, Evaluacion> lista_evaluaciones = new MyList<>();

        Scanner entrada = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 3) {
            System.out.println("\nBienvenido a UMovie");
            System.out.println("MENU PRINCIPAL:");
            System.out.println("1. Carga de Datos");
            System.out.println("2. Ejecutar Consultas");
            System.out.println("3. Salir");

            try {
                opcion = Integer.parseInt(entrada.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Opcion invalida. Ingrese un numero.");
                continue;
            }

            if (opcion == 1) {
                Carga.carga(lista_peliculas, lista_creditos, lista_sagas, lista_lenguajes, lista_generos, lista_evaluaciones);

            } else if (opcion == 2) {
                System.out.println("\nSeleccione la opcion que desee:");
                System.out.println("1. Top 5 de las mejores peliculas con mas calificaciones por idioma.");
                System.out.println("2. Top 10 de las peliculas que mejor calificacion media tienen por parte de los usuarios.");
                System.out.println("3. Top 5 de las colecciones que mas ingresos generaron.");
                System.out.println("4. Top 10 de los directores que mejor calificacion tienen.");
                System.out.println("5. Actor con mas calificaciones recibidas en cada mes del año.");
                System.out.println("6. Usuarios con mas calificaciones por genero.");
                System.out.println("7. Volver");

                int subOpcion;
                try {
                    subOpcion = Integer.parseInt(entrada.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Opcion invalida.");
                    continue;
                }

                switch (subOpcion) {
                    case 1 -> Consultas.top5PeliculasMasCalificadasPorIdioma(lista_peliculas, lista_evaluaciones);
                    case 2 -> Consultas.top10PeliculasMejorCalificadas(lista_peliculas, lista_evaluaciones);
                    case 3 -> Consultas.top5ColeccionesMasIngresos(lista_peliculas);
                    case 4 -> Consultas.top10DirectoresMejorCalificados(lista_creditos, lista_evaluaciones);
                    case 5 -> {
                        MyHash<Integer, Creditos> hash_creditos = new MyHashImpl<>();
                        for (int i = 0; i < lista_creditos.getSize(); i++) {
                            Creditos c = lista_creditos.getNumber(i);
                            hash_creditos.insert(c.getId_pelicula(), c);
                        }
                        Consultas.actorConMasCalificacionesPorMes(lista_evaluaciones, hash_creditos);
                    }
                    case 6 -> Consultas.topUsuariosPorGenero(lista_generos);
                    case 7 -> System.out.println("Volviendo al menu principal...");
                    default -> System.out.println("Opcion invalida.");
                }

            } else if (opcion != 3) {
                System.out.println("Opcion invalida.");
            }
        }

        System.out.println("Hasta luego!");
        entrada.close();
    }
}
