package um.edu.uy;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.consultas.*;
import um.edu.uy.entidades.*;
import um.edu.uy.exeptions.ElementoNoExiste;
import um.edu.uy.tads.*;

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
                Carga.carga();

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


