package um.edu.uy;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.consultas.Consultas;
import um.edu.uy.entidades.*;
import um.edu.uy.exeptions.ElementoNoExiste;
import um.edu.uy.tads.*;

import java.io.FileReader;
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
        MyList<Integer, Paises_Produccion> lista_paises = new MyList<>();
        MyList<Integer, Productora> lista_productoras = new MyList<>();
        MyList<Integer, Actor> lista_actores = new MyList<>();
        MyList<Integer, Equipo_Produccion> lista_equipo = new MyList<>();

        Scanner entrada = new Scanner(System.in);
        String opcion = "0";
        while (Integer.parseInt(opcion) != 3) {
            System.out.println("Bienvenido a UMovie");
            System.out.println("MENU PRINCIPAL:");
            System.out.println("Seleccione la opcion que desee:");
            System.out.println("1. Carga de Datos");
            System.out.println("2. Ejecutar Consultas");
            System.out.println("3. Salir");
            opcion = entrada.nextLine();
            if (Integer.parseInt(opcion) == 1) {
                long inicio = System.currentTimeMillis();
                String[] fila;
                CSVReader readerPeliculas = new CSVReader(new FileReader("src/main/resources/DATASETS v2/movies_metadata.csv"));
                fila = readerPeliculas.readNext(); // saltar encabezado
                while ((fila = readerPeliculas.readNext()) != null) {

                    // Validación mínima
                    if (fila[0].isEmpty() || fila[2].isEmpty() || fila[3].isEmpty() || fila[5].isEmpty()
                            || fila[10].isEmpty() || fila[11].isEmpty() || fila[15].isEmpty()
                            || fila[18].isEmpty()) {
                        continue; // saltar películas incompletas
                    }

                    try {
                        Pelicula temp = new Pelicula(fila[0], fila[1], fila[2], fila[3],
                                fila[4], fila[5], fila[6], fila[7], fila[8], fila[9],
                                fila[10], fila[11], fila[12], fila[13], fila[14],
                                fila[15], fila[16], fila[17], fila[18]);

                        int id = Integer.parseInt(fila[5]);
                        lista_peliculas.insert(id, temp);

                        if (temp.getSaga() != null) {
                            lista_sagas.insert(temp.getSaga().getId(), temp.getSaga());
                        }

                        for (int j = 0; j < temp.getLista_lenguajes_hablados().getSize(); j++) {
                            lista_lenguajes.insert(
                                    temp.getLista_lenguajes_hablados().getNumber(j).getIso_639_1(),
                                    temp.getLista_lenguajes_hablados().getNumber(j)
                            );
                        }
                    } catch (Exception e) {
                        // System.out.println("Error procesando película: " + e.getMessage());
                    }
                }
                System.out.println("Cargaron " + lista_peliculas.getSize() + " peliculas!");

                CSVReader readerCreditos = new CSVReader(new FileReader("src/main/resources/DATASETS v2/credits.csv"));
                fila = readerCreditos.readNext();
                for (Integer i = 0; i < 5000; i++) {
                    fila = readerCreditos.readNext();
                    Creditos temp = new Creditos(fila[0], fila[1], fila[2]);
                    lista_creditos.insert(Integer.parseInt(fila[2]), temp);
                }
                System.out.println("Cargaron " + lista_creditos.getSize() + " creditos!");

                CSVReader readerEvaluaciones = new CSVReader(new FileReader("src/main/resources/DATASETS v2/ratings_1mm.csv"));
                fila = readerEvaluaciones.readNext();
                for (Integer i = 0; i < 61787; i++) {
                    fila = readerEvaluaciones.readNext();
                    Evaluacion temp = new Evaluacion(fila[0], fila[1], fila[2], fila[3]);
                    lista_evaluaciones.insert(Integer.parseInt(fila[3]), temp);
                }
                System.out.println("Cargaron " + lista_evaluaciones.getSize() + " evaluaciones!");

                long fin = System.currentTimeMillis();
                System.out.println("Tiempo de ejecución de la carga: " + (fin - inicio) + " ms");

                System.out.println("Enter para continuar.");
                opcion = entrada.nextLine();
            }
            if (Integer.parseInt(opcion) == 2) {

                System.out.println("Seleccione la opcion que desee:");
                System.out.println("1. Top 5 de las mejores peliculas con mas calificaciones por idioma.");
                System.out.println("2. Top 10 de las peliculas que mejor calificacion media tienen por parte de los usuarios.");
                System.out.println("3. Top 5 de las colecciones que mas ingresos generaron.");
                System.out.println("4. Top 10 de los directores que mejor calificacion tienen.");
                System.out.println("5. Actor con mas calificaciones recibidas en cada mes del año.");
                System.out.println("6. Usuarios con mas calificaciones por genero");
                System.out.println("7. Salir");
                opcion = entrada.nextLine();
                Consultas consultas = new Consultas();
                if (Integer.parseInt(opcion) == 1) {
                    consultas.top5PeliculasMasCalificadasPorIdioma(lista_peliculas, lista_evaluaciones);
                } else if (Integer.parseInt(opcion) == 2) {
                    consultas.top10PeliculasMejorCalificadas(lista_peliculas, lista_evaluaciones);
                } else if (Integer.parseInt(opcion) == 3) {
                    consultas.top5ColeccionesMasIngresos(lista_peliculas);
                } else if (Integer.parseInt(opcion) == 4) {
                    consultas.top10DirectoresMejorCalificados(lista_creditos, lista_evaluaciones);
                } else if (Integer.parseInt(opcion) == 5) {
                    MyHash<Integer, Creditos> hash_creditos = new MyHashImpl<>();
                    for (int i = 0; i < lista_creditos.getSize(); i++) {
                        Creditos c = lista_creditos.getNumber(i);
                        hash_creditos.insert(c.getId_pelicula(), c);
                    }
                    consultas.actorConMasCalificacionesPorMes(lista_evaluaciones, hash_creditos);
                } else if (Integer.parseInt(opcion) == 6) {
                    consultas.topUsuariosPorGenero(lista_generos);
                } else if (Integer.parseInt(opcion) == 7) {
                    System.out.println("Saliendo...");
                }
            }
        }
    }
}


