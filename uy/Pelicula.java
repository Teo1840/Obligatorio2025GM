package um.edu.uy;

import um.edu.uy.tads.MyList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import java.time.LocalDate;
import java.util.List;

public class Pelicula {
    private Boolean adulta;
    private Saga saga;
    private Integer presupuesto;
    private MyList<Integer, Genero> lista_generos;
    private String pagina_web;
    private Integer id;
    private String id_imdb;
    private String idiomaOriginal;
    private String tituloOriginal;
    private String resumen;
    private MyList<Integer, Productora> lista_productoras;
    private MyList<String, Paises_Produccion> lista_paises;
    private LocalDate fecha_estreno;
    private Double ingreso;
    private Double duracion;
    private MyList<String, Lenguajes> lista_lenguajes_hablados;
    private String estado;
    private String eslogan;
    private String titulo_ingles;

    public Pelicula(String adulta, String sagaJson, String presupuesto, String generoJson,
                    String pagina_web, String id, String id_imdb, String idiomaOriginal,
                    String tituloOriginal, String resumen, String companias_produccion,
                    String paises_produccion, String fecha_estreno, String ingreso,
                    String duracion, String lenguajes_hablados, String estado,
                    String eslogan, String titulo_ingles) {

        this.adulta = Boolean.valueOf(adulta);
        this.presupuesto = parseIntSafe(presupuesto);
        this.pagina_web = pagina_web;
        this.id = parseIntSafe(id);
        this.id_imdb = id_imdb;
        this.idiomaOriginal = idiomaOriginal;
        this.tituloOriginal = tituloOriginal;
        this.resumen = resumen;
        this.fecha_estreno = LocalDate.parse(fecha_estreno);
        this.ingreso = parseDoubleSafe(ingreso);
        this.duracion = parseDoubleSafe(duracion);
        this.estado = estado;
        this.eslogan = eslogan;
        this.titulo_ingles = titulo_ingles;
        this.lista_generos = new MyList<>();
        this.lista_productoras = new MyList<>();
        this.lista_paises = new MyList<>();
        this.lista_lenguajes_hablados = new MyList<>();


        // --- Parsear JSON de saga (objeto único) ---
        try {
            String limpio = limpiarJson(sagaJson, false);
            if (limpio.equals("{}")) {
                this.saga = null;
            } else {
                Type tipoSaga = new TypeToken<Saga>() {
                }.getType();
                Gson gson = new Gson();
                this.saga = gson.fromJson(limpio, tipoSaga);
            }
        } catch (JsonSyntaxException | IllegalStateException e) {
            System.out.println("⚠️ Error al parsear saga para película " + id + ": " + e.getMessage());
            this.saga = null;
        }

        // --- Parsear JSON de género (lista, tomar primero) ---
        try {
            String limpio = limpiarJson(generoJson, true);
            Type tipoListaGenero = new TypeToken<List<Genero>>() {
            }.getType();
            Gson gson = new Gson();
            List<Genero> generos = gson.fromJson(limpio, tipoListaGenero);
            if (generos != null && !generos.isEmpty()) {
                for (Genero g : generos) {
                    if (g != null) {
                        this.lista_generos.insert(g.getId(), g);
                    }
                }
            }
        } catch (JsonSyntaxException | IllegalStateException e) {
            System.out.println("⚠️ Error al parsear género para película " + id + ": " + e.getMessage());
        }
        // --- Lenguajes hablados (lista completa) ---
        try {
            String limpio = limpiarJson(lenguajes_hablados, true);
            Gson gson = new Gson();
            List<Lenguajes> lenguajes = gson.fromJson(limpio, new TypeToken<List<Lenguajes>>() {
            }.getType());
            if (lenguajes != null) {
                for (Lenguajes l : lenguajes) {
                    if (l != null && l.getIso_639_1() != null)
                        lista_lenguajes_hablados.insert(l.getIso_639_1(), l);
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error al parsear lenguajes: " + e.getMessage());
        }
        // --- Productoras (compañías de producción) ---
        try {
            String limpio = limpiarJson(companias_produccion, true);
            Gson gson = new Gson();
            List<Productora> productoras = gson.fromJson(limpio, new TypeToken<List<Productora>>() {
            }.getType());
            if (productoras != null) {
                for (Productora p : productoras) {
                    if (p != null) {
                        lista_productoras.insert(p.getId(), p);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error al parsear productoras: " + e.getMessage());
        }
        // --- Países de producción ---
        try {
            String limpio = limpiarJson(paises_produccion, true);
            Gson gson = new Gson();
            List<Paises_Produccion> paises = gson.fromJson(limpio, new TypeToken<List<Paises_Produccion>>() {
            }.getType());
            if (paises != null) {
                for (Paises_Produccion p : paises) {
                    if (p != null && p.getIso_3166_1() != null)
                        lista_paises.insert(p.getIso_3166_1(), p);
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error al parsear países: " + e.getMessage());
        }
    }
    //Funciones auxiliares
    private Integer parseIntSafe(String s) {
        try {
            return (s == null || s.trim().isEmpty()) ? 0 : Integer.parseInt(s.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private Double parseDoubleSafe(String s) {
        try {
            return (s == null || s.trim().isEmpty()) ? 0.0 : Double.parseDouble(s.trim());
        } catch (Exception e) {
            return 0.0;
        }
    }
    private LocalDate parseDateSafe(String s) {
        try {
            return (s == null || s.trim().isEmpty()) ? null : LocalDate.parse(s);
        } catch (Exception e) {
            System.out.println("⚠️ Error al parsear fecha: " + e.getMessage());
            return null;
        }
    }

    // isArray: true si esperás una lista JSON (como género), false si es un objeto (como saga)
    private String limpiarJson(String json, boolean isArray) {
        if (json == null || json.trim().isEmpty() || json.trim().equals("null")) {
            return isArray ? "[]" : "{}";
        }
        return json.trim();
    }

    // --- Getters y setters ---

    public Boolean getAdulta() {
        return adulta;
    }

    public void setAdulta(Boolean adulta) {
        this.adulta = adulta;
    }

    public int getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(int presupuesto) {
        this.presupuesto = presupuesto;
    }

    public String getPagina_web() {
        return pagina_web;
    }

    public void setPagina_web(String pagina_web) {
        this.pagina_web = pagina_web;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_imdb() {
        return id_imdb;
    }

    public void setId_imdb(String id_imdb) {
        this.id_imdb = id_imdb;
    }

    public String getIdiomaOriginal() {
        return idiomaOriginal;
    }

    public void setIdiomaOriginal(String idiomaOriginal) {
        this.idiomaOriginal = idiomaOriginal;
    }

    public String getTituloOriginal() {
        return tituloOriginal;
    }

    public void setTituloOriginal(String tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public Double getIngreso() {
        return ingreso;
    }

    public void setIngreso(Double ingreso) {
        this.ingreso = ingreso;
    }

    public Double getDuracion() {
        return duracion;
    }

    public void setDuracion(Double duracion) {
        this.duracion = duracion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEslogan() {
        return eslogan;
    }

    public void setEslogan(String eslogan) {
        this.eslogan = eslogan;
    }

    public String getTitulo_ingles() {
        return titulo_ingles;
    }

    public void setTitulo_ingles(String titulo_ingles) {
        this.titulo_ingles = titulo_ingles;
    }

    public Saga getSaga() {
        return saga;
    }

    public MyList<Integer, Genero> getLista_generos() {
        return lista_generos;
    }

    public void setLista_generos(MyList<Integer, Genero> lista_generos) {
        this.lista_generos = lista_generos;
    }

    public MyList<String, Lenguajes> getLista_lenguajes_hablados() {
        return lista_lenguajes_hablados;
    }

    public void setLista_lenguajes_hablados(MyList<String, Lenguajes> lista_lenguajes_hablados) {
        this.lista_lenguajes_hablados = lista_lenguajes_hablados;
    }

    public MyList<String, Paises_Produccion> getLista_paises() {
        return lista_paises;
    }

    public void setLista_paises(MyList<String, Paises_Produccion> lista_paises) {
        this.lista_paises = lista_paises;
    }

    public MyList<Integer, Productora> getLista_productoras() {
        return lista_productoras;
    }

    public void setLista_productoras(MyList<Integer, Productora> lista_productoras) {
        this.lista_productoras = lista_productoras;
    }
}
