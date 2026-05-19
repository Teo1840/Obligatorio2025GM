package um.edu.uy.consultas;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.entidades.*;
import um.edu.uy.tads.*;

import java.io.FileReader;
import java.io.IOException;

public class Carga {
    public static void carga(
            MyList<Integer, Pelicula> lista_peliculas,
            MyList<Integer, Creditos> lista_creditos,
            MyList<Integer, Saga> lista_sagas,
            MyList<String, Lenguajes> lista_lenguajes,
            MyHash<Integer, Genero> lista_generos,
            MyList<Integer, Evaluacion> lista_evaluaciones) {

        long inicio = System.currentTimeMillis();
        String[] fila;
        try {
            // ================== PELÍCULAS ==================
            CSVReader readerPeliculas = new CSVReader(new FileReader("src/main/resources/DATASETS v2/movies_metadata.csv"));
            readerPeliculas.readNext(); // saltar encabezado
            while ((fila = readerPeliculas.readNext()) != null) {
                if (fila[0].isEmpty() || fila[2].isEmpty() || fila[3].isEmpty() || fila[5].isEmpty()
                        || fila[10].isEmpty() || fila[11].isEmpty() || fila[15].isEmpty() || fila[18].isEmpty()) {
                    continue;
                }
                try {
                    Pelicula temp = new Pelicula(fila[0], fila[1], fila[2], fila[3], fila[4], fila[5], fila[6],
                            fila[7], fila[8], fila[9], fila[10], fila[11], fila[12], fila[13],
                            fila[14], fila[15], fila[16], fila[17], fila[18]);
                    int id = Integer.parseInt(fila[5]);
                    lista_peliculas.insert(id, temp);

                    if (temp.getSaga() != null) {
                        lista_sagas.insert(temp.getSaga().getId(), temp.getSaga());
                    }
                    for (int j = 0; j < temp.getLista_lenguajes_hablados().getSize(); j++) {
                        lista_lenguajes.insert(
                                temp.getLista_lenguajes_hablados().getNumber(j).getIso_639_1(),
                                temp.getLista_lenguajes_hablados().getNumber(j));
                    }
                    for (int j = 0; j < temp.getLista_generos().getSize(); j++) {
                        Genero g = temp.getLista_generos().getNumber(j);
                        if (g != null && !lista_generos.pertenece(g.getId())) {
                            lista_generos.insert(g.getId(), g);
                        }
                    }
                } catch (Exception e) {
                    // saltar filas malformadas
                }
            }
            System.out.println("Cargaron " + lista_peliculas.getSize() + " peliculas!");

            // ================== CRÉDITOS ==================
            CSVReader readerCreditos = new CSVReader(new FileReader("src/main/resources/DATASETS v2/credits.csv"));
            readerCreditos.readNext(); // saltar encabezado
            while ((fila = readerCreditos.readNext()) != null) {
                try {
                    Creditos temp = new Creditos(fila[0], fila[1], fila[2]);
                    lista_creditos.insert(Integer.parseInt(fila[2]), temp);
                } catch (Exception e) {
                    // saltar filas malformadas
                }
            }
            System.out.println("Cargaron " + lista_creditos.getSize() + " creditos!");

            // ================== EVALUACIONES ==================
            CSVReader readerEvaluaciones = new CSVReader(new FileReader("src/main/resources/DATASETS v2/ratings_1mm.csv"));
            readerEvaluaciones.readNext(); // saltar encabezado
            while ((fila = readerEvaluaciones.readNext()) != null) {
                try {
                    Evaluacion temp = new Evaluacion(fila[0], fila[1], fila[2], fila[3]);
                    lista_evaluaciones.insert(Integer.parseInt(fila[1]), temp);
                } catch (Exception e) {
                    // saltar filas malformadas
                }
            }
            System.out.println("Cargaron " + lista_evaluaciones.getSize() + " evaluaciones!");

        } catch (IOException | CsvValidationException e) {
            System.out.println("Error general en la carga: " + e.getMessage());
        }
        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecucion de la carga: " + (fin - inicio) + " ms");
    }
}
