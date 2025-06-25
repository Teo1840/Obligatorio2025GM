package um.edu.uy.entidades;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import um.edu.uy.tads.MyList;

import java.lang.reflect.Type;
import java.util.List;

public class Creditos {
    private int id_pelicula;
    private MyList<Integer, Actor> lista_actores;
    private MyList<Integer, Equipo_Produccion> lista_equipo_produccion;

    public Creditos(String actoresJson, String equipoJson, String id_pelicula) {
        this.id_pelicula = Integer.parseInt(id_pelicula);
        this.lista_actores = new MyList<>();
        this.lista_equipo_produccion = new MyList<>();

        Gson gson = new Gson();

        // --- Procesamiento de actores ---
        try {
            String actoresLimpio = limpiarJson(actoresJson);
            Type tipoListaActores = new TypeToken<List<Actor>>() {}.getType();
            List<Actor> actores = gson.fromJson(actoresLimpio, tipoListaActores);
            if (actores != null) {
                for (Actor a : actores) {
                    if (a != null) {
                        this.lista_actores.insert(a.getId(), a);
                    }
                }
            }
        } catch (JsonSyntaxException e) {
            System.out.println("⚠️ Error parseando actores para película " + id_pelicula + ": " + e.getMessage());
        }

        // --- Procesamiento de equipo ---
        try {
            String equipoLimpio = limpiarJson(equipoJson);
            Type tipoListaEquipo = new TypeToken<List<Equipo_Produccion>>() {}.getType();
            List<Equipo_Produccion> equipo = gson.fromJson(equipoLimpio, tipoListaEquipo);

            if (equipo != null) {
                for (Equipo_Produccion ep : equipo) {
                    if (ep != null) { // && ep.getId() != null
                        lista_equipo_produccion.insert(ep.getId(), ep);
                    }
                }
            }
        } catch (JsonSyntaxException e) {
            System.out.println("⚠️ Error parseando equipo para película " + id_pelicula + ": " + e.getMessage());
        }
    }

    private String limpiarJson(String json) {
        if (json == null || json.trim().isEmpty() || json.trim().equals("null")) {
            return "[]";
        }
        return json.trim();
    }

    public int getId_pelicula() {
        return id_pelicula;
    }

    public void setId_pelicula(int id_pelicula) {
        this.id_pelicula = id_pelicula;
    }

    public MyList<Integer, Actor> getLista_actores() {
        return lista_actores;
    }

    public void setLista_actores(MyList<Integer, Actor> lista_actores) {
        this.lista_actores = lista_actores;
    }

    public MyList<Integer, Equipo_Produccion> getLista_equipo_produccion() {
        return lista_equipo_produccion;
    }

    public void setLista_equipo_produccion(MyList<Integer, Equipo_Produccion> lista_equipo_produccion) {
        this.lista_equipo_produccion = lista_equipo_produccion;
    }
}


/*package um.edu.uy;


import um.edu.uy.tads.MyList;

public class Creditos {
    private int id_pelicula;
    private MyList<Integer, Actor> lista_actores;
    private MyList<Integer, Equipo_Produccion> lista_equipo_produccion;

    public Creditos(String stringActores, String lista_equipo_produccion, String id_pelicula) throws CsvValidationException, IOException {
        this.id_pelicula = Integer.parseInt(id_pelicula);
        this.lista_actores = new MyList<>();




        String a = stringActores.substring(1, stringActores.length() - 1); // saca [ y ]
        a = a.replace("'", "\"");
        a = a.replace('"credit_id": ', "\"");
        String[] actoresSeparados = a.split("\\}, \\{");
        System.out.println(actoresSeparados[1]);

        CSVReader readerActores = new CSVReader(new StringReader(actoresSeparados[1]));
        String[] filaActor;

        filaActor=readerActores.readNext();
        System.out.println(filaActor[0]);
        //System.out.println(filaActor[1]);
        //System.out.println(filaActor[2]);
        //System.out.println(filaActor[3]);
        //System.out.println(filaActor[4]);
        //System.out.println(filaActor[5]);
        //System.out.println(filaActor[6]);
        //System.out.println(filaActor[7]);
        // this.lista_actores.insert(Integer.parseInt(filaActor[4]), new Actor(filaActor[0],filaActor[1],filaActor[2],filaActor[3],filaActor[4],filaActor[5],filaActor[6],filaActor[7]));

        this.lista_equipo_produccion = new MyList<>();
    }
}


//listsa abajo


//public class Creditos {
//    private int id_pelicula;
//    private MyList<Integer, Actor> lista_actores;
//    private MyList<Integer, Equipo_Produccion> lista_equipo;
//
//    public Creditos(String actoresComoTexto, String equipoComoTexto, String idPeli) throws CsvValidationException, IOException {
//        this.id_pelicula = Integer.parseInt(idPeli);
//        this.lista_actores = new MyList<>();
//        this.lista_equipo = new MyList<>();
//
//        // Parsear actores
//        CSVReader readerActores = new CSVReader(new StringReader(actoresComoTexto));
//        String[] filaActor;
//        readerActores.readNext(); // saltar encabezado si lo tiene
//        while ((filaActor = readerActores.readNext()) != null) {
//            Actor actor = new Actor(...); // usar los campos de filaActor
//            lista_actores.insert(Integer.parseInt(actor.getId()), actor);
//        }
//
//        // Parsear equipo
//        CSVReader readerEquipo = new CSVReader(new StringReader(equipoComoTexto));
//        String[] filaEquipo;
//        readerEquipo.readNext();
//        while ((filaEquipo = readerEquipo.readNext()) != null) {
//            Equipo_Produccion ep = new Equipo_Produccion(...); // usar los campos
//            lista_equipo.insert(...);
//        }
//    }
//}



//String entrada = "[{'cast_id': 1, 'character': 'James Bond', 'credit_id': '...', ...}]";
//entrada = entrada.trim().substring(1, entrada.length() - 1); // saca [ y ]

//entrada = entrada.replace("'", "\"");
//String[] actoresRaw = entrada.split("\\}, \\{");
//"{"cast_id": 1, "character": "James Bond", ...}"


//for (String actorTexto : actoresRaw) {
//    actorTexto = actorTexto.replace("{", "").replace("}", "");
//
//    String[] campos = actorTexto.split(", \""); // separa por cada atributo
//    for (String campo : campos) {
//        String[] partes = campo.split("\": ");
//        if (partes.length < 2) continue;
//
//        String clave = partes[0].replace("\"", "").trim();
//        String valor = partes[1].replace("\"", "").trim();
//
//        System.out.println("Clave: " + clave + " | Valor: " + valor);
//    }

 */