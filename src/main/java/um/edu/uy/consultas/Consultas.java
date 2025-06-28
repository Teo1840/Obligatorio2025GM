package um.edu.uy.consultas;

import um.edu.uy.entidades.*;
import um.edu.uy.exeptions.*;
import um.edu.uy.tads.*;
import um.edu.uy.tads.MyHash;

import java.util.Calendar;


public class Consultas {
    //Consulta 1 <-<--<-<-<-<<--<<-<-<<<<<-<-<-<--<--<<--<<--<-<-<-<-<-<-<-<-<-<-<-<-<-<-<<-<--<-<-<-<<--<<-<-<<<<<-<-<-<--<--<<--<<--<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<--<-<-<-<-<-<-<-<-<
    public static void top5PeliculasMasCalificadasPorIdioma(MyList<Integer, Pelicula> peliculas, MyList<Integer, Evaluacion> evaluaciones) {
        long inicio = System.currentTimeMillis();

        MyList<Integer, Integer> contadorCalificaciones = new MyList<>();
        for (int i = 0; i < peliculas.getSize(); i++) {
            int id = peliculas.getNumber(i).getId();
            contadorCalificaciones.insert(id, 0);
        }

        for (int i = 0; i < evaluaciones.getSize(); i++) {
            int idPelicula = evaluaciones.getNumber(i).getIdPelicula();
            try {
                int actual = contadorCalificaciones.getNode(idPelicula).getData();
                contadorCalificaciones.getNode(idPelicula).setData(actual + 1);
            } catch (ElementoNoExiste error) {
            }
        }

        class PeliculaIdioma {
            int id;
            String titulo;
            int total;
            String idioma;

            public PeliculaIdioma(int id, String titulo, int total, String idioma) {
                this.id = id;
                this.titulo = titulo;
                this.total = total;
                this.idioma = idioma;
            }
        }

        MyList<Integer, PeliculaIdioma> resumen = new MyList<>();

        for (int i = 0; i < contadorCalificaciones.getSize(); i++) {
            int id = contadorCalificaciones.getKeyNumber(i);
            int total = contadorCalificaciones.getNumber(i);
            try {
                Pelicula peli = peliculas.getNode(id).getData();
                String idioma = "?";
                if (peli.getLista_lenguajes_hablados() != null && peli.getLista_lenguajes_hablados().getSize() > 0) {
                    idioma = peli.getLista_lenguajes_hablados().getNumber(0).getName();
                }
                resumen.insert(id, new PeliculaIdioma(id, peli.getTitulo_ingles(), total, idioma));
            } catch (ElementoNoExiste e) {
                resumen.insert(id, new PeliculaIdioma(id, "Desconocido", total, "?"));
            }
        }

        for (int i = 0; i < Math.min(5, resumen.getSize()); i++) {
            int maxIndex = i;
            for (int j = i + 1; j < resumen.getSize(); j++) {
                if (resumen.getNumber(j).total > resumen.getNumber(maxIndex).total) {
                    maxIndex = j;
                }
            }
            if (i != maxIndex) {
                PeliculaIdioma temp = resumen.getNumber(i);
                resumen.replace(resumen.getKeyNumber(i), resumen.getKeyNumber(maxIndex), resumen.getNumber(maxIndex));
                resumen.replace(resumen.getKeyNumber(maxIndex), resumen.getKeyNumber(i), temp);
            }
        }

        for (int i = 0; i < Math.min(5, resumen.getSize()); i++) {
            PeliculaIdioma p = resumen.getNumber(i);
            System.out.printf("%d, %s, %d, %s%n", p.id, p.titulo, p.total, p.idioma);
        }

        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución de la consulta: " + (fin - inicio) + " ms");
    }

    //Consulta 2 <-<--<-<-<-<<--<<-<-<<<<<-<-<-<--<--<<--<<--<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<--<-<-<-<<--<<-<-<<<<<-<-<-<--<--<<--<<--<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<--<-<-<-<-<-<-<-<-<
    public static void top10PeliculasMejorCalificadas(MyList<Integer, Pelicula> peliculas, MyList<Integer, Evaluacion> evaluaciones) {
        long inicio = System.currentTimeMillis();

        MyList<Integer, Double[]> acumulados = new MyList<>();
        for (int i = 0; i < peliculas.getSize(); i++) {
            Pelicula p = peliculas.getNumber(i);
            acumulados.insert(p.getId(), new Double[]{0.0, 0.0});
        }
        for (int j = 0; j < evaluaciones.getSize(); j++) {
            Evaluacion e = evaluaciones.getNumber(j);
            try {
                Double[] datos = acumulados.getNode(e.getIdPelicula()).getData();
                datos[0] += e.getPuntacion();
                datos[1]++;
            } catch (ElementoNoExiste ex) {}
        }

        MyList<Integer, Double> promedios = new MyList<>();
        for (int i = 0; i < acumulados.getSize(); i++) {
            int id = acumulados.getKeyNumber(i);
            Double[] datos = acumulados.getNumber(i);
            if (datos[1] > 0) {
                double promedio = datos[0] / datos[1];
                promedios.insert(id, promedio);
            }
        }

        for (int i = 0; i < Math.min(10, promedios.getSize()); i++) {
            int max = i;
            for (int j = i + 1; j < promedios.getSize(); j++) {
                if (promedios.getNumber(j) > promedios.getNumber(max)) {
                    max = j;
                }
            }
            if (i != max) {
                double tempVal = promedios.getNumber(i);
                int tempKey = promedios.getKeyNumber(i);
                promedios.replace(promedios.getKeyNumber(i), promedios.getKeyNumber(max), promedios.getNumber(max));
                promedios.replace(promedios.getKeyNumber(max), tempKey, tempVal);
            }
        }

        for (int i = 0; i < Math.min(10, promedios.getSize()); i++) {
            int id = promedios.getKeyNumber(i);
            double promedio = promedios.getNumber(i);
            try {
                Pelicula p = peliculas.getNode(id).getData();
                System.out.printf("%d, %s, %.2f%n", id, p.getTitulo_ingles(), promedio);
            } catch (ElementoNoExiste e) {
                System.out.printf("%d, Título desconocido, %.2f%n", id, promedio);
            }
        }

        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución de la consulta: " + (fin - inicio) + " ms");
    }

    // Consulta 3 <-<--<-<-<-<<--<<-<-<<<<<-<-<-<--<--<<--<<--<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<<-<--<-<-<-<<--<<-<-<<<<<-<-<-<--<--<<--<<--<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<--<-<-<-<-<-<-<-<-<
    public static void top5ColeccionesMasIngresos(MyList<Integer, Pelicula> peliculas) {
        long inicio = System.currentTimeMillis();

        MyHash<Integer, Double[]> colecciones = new MyHashImpl<>();

        for (int i = 0; i < peliculas.getSize(); i++) {
            Pelicula p = peliculas.getNumber(i);
            if (p.getSaga() != null) {
                int id = p.getSaga().getId();
                try {
                    Double[] data = colecciones.get(id);
                    data[0] += p.getIngreso();
                    data[1]++;
                    colecciones.replace(id, id, data);
                } catch (ElementoNoExiste e) {
                    colecciones.insert(id, new Double[]{p.getIngreso(), 1.0});
                }
            }
        }

        MyList<Integer, Double> ingresos = new MyList<>();
        for (int i = 0; i < colecciones.getSize(); i++) {
            int id = colecciones.getKey(i);
            Double[] data = colecciones.getValue(i);
            ingresos.insert(id, data[0]);
        }

        for (int i = 0; i < Math.min(5, ingresos.getSize()); i++) {
            int max = i;
            for (int j = i + 1; j < ingresos.getSize(); j++) {
                if (ingresos.getNumber(j) > ingresos.getNumber(max)) {
                    max = j;
                }
            }
            if (i != max) {
                double tempVal = ingresos.getNumber(i);
                int tempKey = ingresos.getKeyNumber(i);
                ingresos.replace(ingresos.getKeyNumber(i), ingresos.getKeyNumber(max), ingresos.getNumber(max));
                ingresos.replace(ingresos.getKeyNumber(max), tempKey, tempVal);
            }
        }

        for (int i = 0; i < Math.min(5, ingresos.getSize()); i++) {
            int id = ingresos.getKeyNumber(i);
            double ingresoTotal = ingresos.getNumber(i);
            Double cantidadPeliculas = colecciones.get(id)[1];
            String tituloColeccion = "Colección desconocida";
            MyList<Integer, Integer> idsPeliculas = new MyList<>();

            for (int j = 0; j < peliculas.getSize(); j++) {
                Pelicula p = peliculas.getNumber(j);
                if (p.getSaga() != null && p.getSaga().getId() == id) {
                    if (tituloColeccion.equals("Colección desconocida")) {
                        tituloColeccion = p.getSaga().getName();
                    }
                    idsPeliculas.insert(p.getId(), p.getId());
                }
            }

            System.out.printf("%d, %s, %.0f, %s, %.2f%n", id, tituloColeccion, cantidadPeliculas, idsPeliculas.toString(), ingresoTotal);
        }

        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución de la consulta: " + (fin - inicio) + " ms");
    }
    // Consulta 4: Top 10 directores con mejor calificación promedio   <-<--<-<-<-<<--<<-<-<<<<<-<-<-<--<--<<--<<--<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<--<-<-<-<<--<<-<-<<<<<-<-<-<--<--<<--<<--<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<--<-<-<-<-<-<-
    public static void top10DirectoresMejorCalificados(MyList<Integer, Creditos> creditos, MyList<Integer, Evaluacion> evaluaciones) {
        long inicio = System.currentTimeMillis();

        MyHash<String, Double[]> acumulador = new MyHashImpl<>(); // clave: nombreDirector, valor[0]: suma calificaciones, valor[1]: cantidad

        MyHash<Integer, String> peliculaADirector = new MyHashImpl<>();

        for (int i = 0; i < creditos.getSize(); i++) {
            Creditos c = creditos.getNumber(i);
            MyList<Integer, Equipo_Produccion> equipo = c.getLista_equipo_produccion();
            for (int j = 0; j < equipo.getSize(); j++) {
                Equipo_Produccion ep = equipo.getNumber(j);
                if (ep.getJob() != null && ep.getJob().equalsIgnoreCase("Director")) {
                    String nombre = ep.getName();
                    peliculaADirector.insert(c.getId_pelicula(), nombre);
                    break; // solo un director por película
                }
            }
        }

        for (int i = 0; i < evaluaciones.getSize(); i++) {
            Evaluacion e = evaluaciones.getNumber(i);
            try {
                String director = peliculaADirector.get(e.getIdPelicula());
                Double[] datos = acumulador.pertenece(director) ? acumulador.get(director) : new Double[]{0.0, 0.0};
                datos[0] += e.getPuntacion();
                datos[1]++;
                acumulador.replace(director, director, datos);
            } catch (ElementoNoExiste ex) {
                // sin director, ignorar
            }
        }

        MyList<String, Double> promedios = new MyList<>();
        for (int i = 0; i < acumulador.getSize(); i++) {
            String nombre = acumulador.getKey(i);
            Double[] datos = acumulador.getValue(i);
            if (datos[1] > 0) {
                promedios.insert(nombre, datos[0] / datos[1]);
            }
        }

        for (int i = 0; i < Math.min(10, promedios.getSize()); i++) {
            int max = i;
            for (int j = i + 1; j < promedios.getSize(); j++) {
                if (promedios.getNumber(j) > promedios.getNumber(max)) {
                    max = j;
                }
            }
            if (i != max) {
                double tempVal = promedios.getNumber(i);
                String tempKey = promedios.getKeyNumber(i);
                promedios.replace(promedios.getKeyNumber(i), promedios.getKeyNumber(max), promedios.getNumber(max));
                promedios.replace(promedios.getKeyNumber(max), tempKey, tempVal);
            }
        }

        for (int i = 0; i < Math.min(10, promedios.getSize()); i++) {
            System.out.printf("%s, %.2f\n", promedios.getKeyNumber(i), promedios.getNumber(i));
        }

        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución de la consulta: " + (fin - inicio) + " ms");
    }


    // Consulta 5 <-<--<-<-<-<<--<<-<-<<<<<-<-<-<--<--<<--<<--<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<--<-<-<-<-<-<-<-<-<<-<--<-<-<-<<--<<-<-<<<<<-<-<-<--<--<<--<<--<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<--<-<-<-<-<-
    public static void actorConMasCalificacionesPorMes(MyList<Integer, Evaluacion> evaluaciones, MyHash<Integer, Creditos> lista_creditos) {
        long inicio = System.currentTimeMillis();

        MyHash<Integer, MyHash<String, Integer>> contador = new MyHashImpl<>();

        for (int i = 0; i < evaluaciones.getSize(); i++) {
            Evaluacion e = evaluaciones.getNumber(i);
            int timestamp = e.getTimestamp();

            // Convertir timestamp a mes (0 = enero, 11 = diciembre)
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis((long) timestamp * 1000);
            int mes = calendar.get(Calendar.MONTH);

            try {
                Creditos c = lista_creditos.get(e.getIdPelicula());
                if (c.getLista_actores() != null) {
                    for (int j = 0; j < c.getLista_actores().getSize(); j++) {
                        String nombreActor = c.getLista_actores().getNumber(j).getName();

                        // Obtener sub-hash del mes
                        MyHash<String, Integer> actoresMes;
                        try {
                            actoresMes = contador.get(mes);
                        } catch (ElementoNoExiste ex) {
                            actoresMes = new MyHashImpl<>();
                            contador.insert(mes, actoresMes);
                        }

                        // Sumar calificación
                        int cantidad = actoresMes.pertenece(nombreActor) ? actoresMes.get(nombreActor) : 0;
                        actoresMes.replace(nombreActor, nombreActor, cantidad + 1);
                    }
                }
            } catch (ElementoNoExiste ignored) {}
        }

        // Imprimir resultados
        for (int mes = 0; mes < 12; mes++) {
            try {
                MyHash<String, Integer> actores = contador.get(mes);
                String topActor = null;
                int maxCalificaciones = 0;

                for (int i = 0; i < actores.getSize(); i++) {
                    String actor = actores.getKey(i);
                    int calificaciones = actores.getValue(i);
                    if (calificaciones > maxCalificaciones) {
                        topActor = actor;
                        maxCalificaciones = calificaciones;
                    }
                }

                if (topActor != null) {
                    System.out.printf("%d, %s, ?, %d%n", mes + 1, topActor, maxCalificaciones); // mes+1 para mostrar 1–12
                }

            } catch (ElementoNoExiste ignored) {}
        }

        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución de la consulta: " + (fin - inicio) + " ms");
    }

    // Consulta 6 <-<--<-<-<-<<--<<-<-<<<<<-<-<-<--<--<<--<<--<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<--<-<-<-<-<--<-<-<-<<--<<-<-<<<<<-<-<-<--<--<<--<<--<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<--<-<-<-<-<-<-<-<-<
    public static void topUsuariosPorGenero(MyHash<Integer, Genero> generos) {
        long tiempoInicio = System.currentTimeMillis();

        HeapImpl<Genero> heapGeneros = new HeapImpl<>(generos.getSize(), true);

        MyList<Integer, Integer> generoIds = new MyList<>();

        for (int i = 0; i < generos.getSize(); i++) {
            int clave = generos.getKey(i);
            generoIds.insert(clave, clave);
        }

        for (int i = 0; i < generoIds.getSize(); i++) {
            Genero genero = generos.get(generoIds.getNumber(i));
            if (heapGeneros.size() < 10) {
                heapGeneros.insert(genero);
            } else if (genero.getEvaluacionesTotales() > heapGeneros.get().getEvaluacionesTotales()) {
                heapGeneros.delete();
                heapGeneros.insert(genero);
            }
        }

        MyList<Genero, Genero> top10Generos = new MyList<>();
        while (heapGeneros.size() > 0) {
            Genero g = heapGeneros.delete();
            top10Generos.insert(g, g);
        }
        for (int i = 0; i < top10Generos.getSize(); i++) {
            Genero genero = top10Generos.getNumber(i);
            System.out.println(genero.getTopUserId() + ", " +
                    genero.getName() + ", " +
                    genero.getTopUserEvaluaciones());
        }

        long tiempoFin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución de la consulta: " + (tiempoFin - tiempoInicio) + " ms");
    }

}
// Crear lista auxiliar con idioma
class PeliculaIdioma {
    int id;
    String titulo;
    int total;
    String idioma;

    public PeliculaIdioma(int id, String titulo, int total, String idioma) {
        this.id = id;
        this.titulo = titulo;
        this.total = total;
        this.idioma = idioma;
    }
}