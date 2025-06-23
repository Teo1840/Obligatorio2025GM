package um.edu.uy;

import um.edu.uy.tads.MyList;

import java.time.LocalDate;

public class Pelicula {
    private Boolean adulta;
    private Saga saga;
    private Integer presupuesto;
    private Genero genero;
    private String pagina_web;
    private Integer id;
    private String id_imdb;
    private String idiomaOriginal;
    private String tituloOriginal;
    private String resumen;
    //lista de companias de produccion
    //lista de paises de produccion
    private LocalDate fecha_estreno;
    private Double ingreso;
    private Double duracion;
    private MyList lista_lenguajes_hablados;
    private String estado;
    private String eslogan;
    private String titulo_ingles;

    public Pelicula(String adulta, String saga, String presupuesto, String genero, String pagina_web, String id, String id_imdb, String idiomaOriginal, String tituloOriginal, String resumen, String companias_produccion, String paises_produccion, String fecha_estreno, String ingreso, String duracion, String lenguajes_hablados,String estado, String eslogan, String titulo_ingles) {
        this.adulta = Boolean.valueOf(adulta);
        this.saga = null; //new Saga(saga);
        this.presupuesto = Integer.valueOf(presupuesto);
        this.genero = null;
        this.pagina_web = pagina_web;
        this.id = Integer.valueOf(id);
        this.id_imdb = id_imdb;
        this.idiomaOriginal = idiomaOriginal;
        this.tituloOriginal = tituloOriginal;
        this.resumen = resumen;
        this.fecha_estreno = LocalDate.parse(fecha_estreno);
        this.ingreso = Double.valueOf(ingreso);
        this.duracion = Double.valueOf(duracion);
        this.estado = estado;
        this.eslogan = eslogan;
        this.titulo_ingles = titulo_ingles;
    }

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

    public LocalDate getFecha_estreno() {
        return fecha_estreno;
    }

    public void setFecha_estreno(LocalDate fecha_estreno) {
        this.fecha_estreno = fecha_estreno;
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
}
