package um.edu.uy.entidades;

public class Genero implements Comparable<Genero> {
    private int id;
    private String name;
    private int topUserId;
    private int topUserEvaluaciones;
    private int evaluacionesTotales;

    public Genero() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTopUserId() {
        return topUserId;
    }

    public void setTopUserId(int topUserId) {
        this.topUserId = topUserId;
    }

    public int getTopUserEvaluaciones() {
        return topUserEvaluaciones;
    }

    public void setTopUserEvaluaciones(int topUserEvaluaciones) {
        this.topUserEvaluaciones = topUserEvaluaciones;
    }

    public int getEvaluacionesTotales() {
        return evaluacionesTotales;
    }

    public void setEvaluacionesTotales(int evaluacionesTotales) {
        this.evaluacionesTotales = evaluacionesTotales;
    }

    @Override
    public int compareTo(Genero otro) {
        return Integer.compare(this.evaluacionesTotales, otro.evaluacionesTotales);
    }
}


//    private MyList<Integer, Genero> lista_generos;
//
//    public GenerosCargados(String jsonGeneros) {
//        this.lista_generos = new MyList<>();
//        Gson gson = new Gson();
//
//        try {
//            String limpio = limpiarJson(jsonGeneros);
//            Type tipoLista = new TypeToken<List<Genero>>() {}.getType();
//            List<Genero> generos = gson.fromJson(limpio, tipoLista);
//
//            if (generos != null) {
//                for (Genero g : generos) {
//                    if (g != null) {
//                        lista_generos.insert(g.getId(), g);
//                    }
//                }
//            }
//        } catch (JsonSyntaxException e) {
//            System.out.println("⚠️ Error al parsear géneros: " + e.getMessage());
//        }
//    }
//
//    private String limpiarJson(String json) {
//        if (json == null || json.trim().isEmpty() || json.trim().equals("null")) {
//            return "[]";
//        }
//        return json.trim();
//    }
//
//    public MyList<Integer, Genero> getListaGeneros() {
//        return lista_generos;
//    }
//}
