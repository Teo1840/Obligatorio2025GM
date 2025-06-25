package um.edu.uy.entidades;

public class Genero {
    private int id;
    private String name;

    public Genero() {

    }

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
