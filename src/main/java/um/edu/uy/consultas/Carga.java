import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Carga {
    public static void carga() {
        long inicio = System.currentTimeMillis();
        String[] fila;
        try {
            // ================== PELÍCULAS ==================
            CSVReader readerPeliculas = new CSVReader(new FileReader("src/main/resources/DATASETS v2/movies_metadata.csv"));
            readerPeliculas.readNext(); // saltar encabezado
            while ((fila = readerPeliculas.readNext()) != null) {
                if (fila[0].isEmpty() || fila[2].isEmpty() || fila[3].isEmpty() || fila[5].isEmpty()
                    || fila[10].isEmpty() || fila[11].isEmpty() || fila[15].isEmpty()|| fila[18].isEmpty()) {
                    continue; // saltar películas incompletas (Validación mínima)
                }
                try {
                    Pelicula temp = new Pelicula(fila[0], fila[1], fila[2], fila[3],fila[4], fila[5],fila[6],
                                                fila[7], fila[8], fila[9],fila[10], fila[11],fila[12],fila[13],
                                                fila[14],fila[15], fila[16], fila[17], fila[18]);
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
            // ================== CRÉDITOS ==================
            CSVReader readerCreditos = new CSVReader(new FileReader("src/main/resources/DATASETS v2/credits.csv"));
            readerCreditos.readNext();
            for (Integer i = 0; i < 5000; i++) {
                fila = readerCreditos.readNext();
                Creditos temp = new Creditos(fila[0], fila[1], fila[2]);
                lista_creditos.insert(Integer.parseInt(fila[2]), temp);
            }
            System.out.println("Cargaron " + lista_creditos.getSize() + " creditos!");
            // ================== EVALUACIONES ==================
            CSVReader readerEvaluaciones = new CSVReader(new FileReader("src/main/resources/DATASETS v2/ratings_1mm.csv"));
            fila = readerEvaluaciones.readNext();
            for (Integer i = 0; i < 61787; i++) {
                fila = readerEvaluaciones.readNext();
                Evaluacion temp = new Evaluacion(fila[0], fila[1], fila[2], fila[3]);
                lista_evaluaciones.insert(Integer.parseInt(fila[3]), temp);
            }
            System.out.println("Cargaron " + lista_evaluaciones.getSize() + " evaluaciones!");
        } catch (Exception e) {
            System.out.println("Error general en la carga: " + e.getMessage());
        }
        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución de la carga: " + (fin - inicio) + " ms");
    }
}