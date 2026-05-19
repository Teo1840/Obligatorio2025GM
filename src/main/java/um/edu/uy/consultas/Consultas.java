package um.edu.uy.consultas;

import um.edu.uy.entidades.*;
import um.edu.uy.exeptions.*;
import um.edu.uy.tads.*;

import java.util.Calendar;

public class Consultas {

    // Consulta 1: top 5 películas más calificadas agrupadas por idioma original
    public static void top5PeliculasMasCalificadasPorIdioma(MyList<Integer, Pelicula> peliculas, MyList<Integer, Evaluacion> evaluaciones) {
        long inicio = System.currentTimeMillis();

        // Contar calificaciones por película
        MyHash<Integer, Integer> cuentaPorPelicula = new MyHashImpl<>();
        for (int i = 0; i < evaluaciones.getSize(); i++) {
            int id = evaluaciones.getNumber(i).getIdPelicula();
            if (cuentaPorPelicula.pertenece(id)) {
                try { cuentaPorPelicula.replace(id, id, cuentaPorPelicula.get(id) + 1); }
                catch (ElementoNoExiste e) {}
            } else {
                cuentaPorPelicula.insert(id, 1);
            }
        }

        // Agrupar películas por idioma original: idioma → lista de (movieId → count)
        MyHash<String, MyList<Integer, Integer>> peliculasPorIdioma = new MyHashImpl<>();
        for (int i = 0; i < peliculas.getSize(); i++) {
            Pelicula p = peliculas.getNumber(i);
            String idioma = p.getIdiomaOriginal();
            if (idioma == null || idioma.isEmpty()) continue;
            if (!peliculasPorIdioma.pertenece(idioma)) {
                peliculasPorIdioma.insert(idioma, new MyList<>());
            }
            int count = 0;
            try { count = cuentaPorPelicula.get(p.getId()); } catch (ElementoNoExiste e) {}
            try { peliculasPorIdioma.get(idioma).insert(p.getId(), count); } catch (ElementoNoExiste e) {}
        }

        // Para cada idioma, ordenar y mostrar top 5
        for (int l = 0; l < peliculasPorIdioma.getSize(); l++) {
            String idioma = peliculasPorIdioma.getKey(l);
            MyList<Integer, Integer> lista = peliculasPorIdioma.getValue(l);
            int limit = Math.min(5, lista.getSize());

            for (int i = 0; i < limit; i++) {
                int maxIdx = i;
                for (int j = i + 1; j < lista.getSize(); j++) {
                    if (lista.getNumber(j) > lista.getNumber(maxIdx)) maxIdx = j;
                }
                lista.swap(i, maxIdx);
            }

            System.out.println("Idioma: " + idioma);
            for (int i = 0; i < limit; i++) {
                int movieId = lista.getKeyNumber(i);
                Pelicula p = peliculas.get(movieId);
                String titulo = (p != null) ? p.getTitulo_ingles() : "Desconocido";
                System.out.printf("  %d, %s, %d%n", movieId, titulo, lista.getNumber(i));
            }
        }

        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecucion de la consulta: " + (fin - inicio) + " ms");
    }

    // Consulta 2: top 10 películas con mejor calificación promedio
    public static void top10PeliculasMejorCalificadas(MyList<Integer, Pelicula> peliculas, MyList<Integer, Evaluacion> evaluaciones) {
        long inicio = System.currentTimeMillis();

        MyHash<Integer, Double[]> acumulados = new MyHashImpl<>();
        for (int i = 0; i < peliculas.getSize(); i++) {
            acumulados.insert(peliculas.getNumber(i).getId(), new Double[]{0.0, 0.0});
        }
        for (int j = 0; j < evaluaciones.getSize(); j++) {
            Evaluacion e = evaluaciones.getNumber(j);
            if (!acumulados.pertenece(e.getIdPelicula())) continue;
            try {
                Double[] datos = acumulados.get(e.getIdPelicula());
                datos[0] += e.getPuntacion();
                datos[1]++;
            } catch (ElementoNoExiste ex) {}
        }

        MyList<Integer, Double> promedios = new MyList<>();
        for (int i = 0; i < acumulados.getSize(); i++) {
            Double[] datos = acumulados.getValue(i);
            if (datos[1] > 0) promedios.insert(acumulados.getKey(i), datos[0] / datos[1]);
        }

        int limit = Math.min(10, promedios.getSize());
        for (int i = 0; i < limit; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < promedios.getSize(); j++) {
                if (promedios.getNumber(j) > promedios.getNumber(maxIdx)) maxIdx = j;
            }
            promedios.swap(i, maxIdx);
        }

        for (int i = 0; i < limit; i++) {
            int id = promedios.getKeyNumber(i);
            Pelicula p = peliculas.get(id);
            String titulo = (p != null) ? p.getTitulo_ingles() : "Desconocido";
            System.out.printf("%d, %s, %.2f%n", id, titulo, promedios.getNumber(i));
        }

        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecucion de la consulta: " + (fin - inicio) + " ms");
    }

    // Consulta 3: top 5 colecciones/sagas con más ingresos totales
    public static void top5ColeccionesMasIngresos(MyList<Integer, Pelicula> peliculas) {
        long inicio = System.currentTimeMillis();

        // saga id → [ingresoTotal, cantPeliculas]
        MyHash<Integer, Double[]> colecciones = new MyHashImpl<>();
        for (int i = 0; i < peliculas.getSize(); i++) {
            Pelicula p = peliculas.getNumber(i);
            if (p.getSaga() == null) continue;
            int id = p.getSaga().getId();
            if (colecciones.pertenece(id)) {
                try {
                    Double[] data = colecciones.get(id);
                    data[0] += p.getIngreso();
                    data[1]++;
                } catch (ElementoNoExiste e) {}
            } else {
                colecciones.insert(id, new Double[]{p.getIngreso(), 1.0});
            }
        }

        MyList<Integer, Double> ingresos = new MyList<>();
        for (int i = 0; i < colecciones.getSize(); i++) {
            ingresos.insert(colecciones.getKey(i), colecciones.getValue(i)[0]);
        }

        int limit = Math.min(5, ingresos.getSize());
        for (int i = 0; i < limit; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < ingresos.getSize(); j++) {
                if (ingresos.getNumber(j) > ingresos.getNumber(maxIdx)) maxIdx = j;
            }
            ingresos.swap(i, maxIdx);
        }

        for (int i = 0; i < limit; i++) {
            int id = ingresos.getKeyNumber(i);
            String tituloSaga = "Coleccion desconocida";
            StringBuilder ids = new StringBuilder();
            for (int j = 0; j < peliculas.getSize(); j++) {
                Pelicula p = peliculas.getNumber(j);
                if (p.getSaga() != null && p.getSaga().getId() == id) {
                    if (tituloSaga.equals("Coleccion desconocida")) tituloSaga = p.getSaga().getName();
                    if (ids.length() > 0) ids.append("|");
                    ids.append(p.getId());
                }
            }
            double cant = 0;
            try { cant = colecciones.get(id)[1]; } catch (ElementoNoExiste e) {}
            System.out.printf("%d, %s, %.0f, %s, %.2f%n", id, tituloSaga, cant, ids, ingresos.getNumber(i));
        }

        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecucion de la consulta: " + (fin - inicio) + " ms");
    }

    // Consulta 4: top 10 directores con mejor calificación promedio
    public static void top10DirectoresMejorCalificados(MyList<Integer, Creditos> creditos, MyList<Integer, Evaluacion> evaluaciones) {
        long inicio = System.currentTimeMillis();

        MyHash<Integer, String> peliculaADirector = new MyHashImpl<>();
        for (int i = 0; i < creditos.getSize(); i++) {
            Creditos c = creditos.getNumber(i);
            MyList<Integer, Equipo_Produccion> equipo = c.getLista_equipo_produccion();
            for (int j = 0; j < equipo.getSize(); j++) {
                Equipo_Produccion ep = equipo.getNumber(j);
                if (ep.getJob() != null && ep.getJob().equalsIgnoreCase("Director")) {
                    peliculaADirector.insert(c.getId_pelicula(), ep.getName());
                    break;
                }
            }
        }

        // director → [sumaCalificaciones, cantidad]
        MyHash<String, Double[]> acumulador = new MyHashImpl<>();
        for (int i = 0; i < evaluaciones.getSize(); i++) {
            Evaluacion e = evaluaciones.getNumber(i);
            if (!peliculaADirector.pertenece(e.getIdPelicula())) continue;
            try {
                String director = peliculaADirector.get(e.getIdPelicula());
                if (acumulador.pertenece(director)) {
                    Double[] datos = acumulador.get(director);
                    datos[0] += e.getPuntacion();
                    datos[1]++;
                } else {
                    acumulador.insert(director, new Double[]{e.getPuntacion(), 1.0});
                }
            } catch (ElementoNoExiste ex) {}
        }

        MyList<String, Double> promedios = new MyList<>();
        for (int i = 0; i < acumulador.getSize(); i++) {
            Double[] datos = acumulador.getValue(i);
            if (datos[1] > 0) promedios.insert(acumulador.getKey(i), datos[0] / datos[1]);
        }

        int limit = Math.min(10, promedios.getSize());
        for (int i = 0; i < limit; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < promedios.getSize(); j++) {
                if (promedios.getNumber(j) > promedios.getNumber(maxIdx)) maxIdx = j;
            }
            promedios.swap(i, maxIdx);
        }

        for (int i = 0; i < limit; i++) {
            System.out.printf("%s, %.2f%n", promedios.getKeyNumber(i), promedios.getNumber(i));
        }

        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecucion de la consulta: " + (fin - inicio) + " ms");
    }

    // Consulta 5: actor con más calificaciones recibidas en cada mes del año
    public static void actorConMasCalificacionesPorMes(MyList<Integer, Evaluacion> evaluaciones, MyHash<Integer, Creditos> lista_creditos) {
        long inicio = System.currentTimeMillis();

        // mes → (actor → cantidad)
        MyHash<Integer, MyHash<String, Integer>> contador = new MyHashImpl<>();

        for (int i = 0; i < evaluaciones.getSize(); i++) {
            Evaluacion e = evaluaciones.getNumber(i);
            if (!lista_creditos.pertenece(e.getIdPelicula())) continue;

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis((long) e.getTimestamp() * 1000);
            int mes = cal.get(Calendar.MONTH);

            try {
                Creditos c = lista_creditos.get(e.getIdPelicula());
                if (c.getLista_actores() == null) continue;
                for (int j = 0; j < c.getLista_actores().getSize(); j++) {
                    String nombreActor = c.getLista_actores().getNumber(j).getName();
                    if (!contador.pertenece(mes)) contador.insert(mes, new MyHashImpl<>());
                    MyHash<String, Integer> actoresMes;
                    try { actoresMes = contador.get(mes); } catch (ElementoNoExiste ex) { continue; }
                    if (actoresMes.pertenece(nombreActor)) {
                        try { actoresMes.replace(nombreActor, nombreActor, actoresMes.get(nombreActor) + 1); }
                        catch (ElementoNoExiste ex) {}
                    } else {
                        actoresMes.insert(nombreActor, 1);
                    }
                }
            } catch (ElementoNoExiste ex) {}
        }

        for (int mes = 0; mes < 12; mes++) {
            if (!contador.pertenece(mes)) continue;
            try {
                MyHash<String, Integer> actores = contador.get(mes);
                String topActor = null;
                int maxCal = 0;
                for (int i = 0; i < actores.getSize(); i++) {
                    int cal = actores.getValue(i);
                    if (cal > maxCal) { topActor = actores.getKey(i); maxCal = cal; }
                }
                if (topActor != null) System.out.printf("%d, %s, %d%n", mes + 1, topActor, maxCal);
            } catch (ElementoNoExiste ex) {}
        }

        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecucion de la consulta: " + (fin - inicio) + " ms");
    }

    // Consulta 6: usuario con más calificaciones por género
    public static void topUsuariosPorGenero(MyHash<Integer, Genero> generos) {
        long tiempoInicio = System.currentTimeMillis();

        if (generos.getSize() == 0) {
            System.out.println("No hay generos cargados.");
            return;
        }

        HeapImpl<Genero> heap = new HeapImpl<>(generos.getSize(), true);
        for (int i = 0; i < generos.getSize(); i++) {
            Genero g = generos.getValue(i);
            if (heap.size() < 10) {
                heap.insert(g);
            } else if (g.getEvaluacionesTotales() > heap.get().getEvaluacionesTotales()) {
                heap.delete();
                heap.insert(g);
            }
        }

        MyList<Genero, Genero> top = new MyList<>();
        while (heap.size() > 0) {
            Genero g = heap.delete();
            top.insert(g, g);
        }
        for (int i = 0; i < top.getSize(); i++) {
            Genero g = top.getNumber(i);
            System.out.println(g.getTopUserId() + ", " + g.getName() + ", " + g.getTopUserEvaluaciones());
        }

        long tiempoFin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecucion de la consulta: " + (tiempoFin - tiempoInicio) + " ms");
    }
}
