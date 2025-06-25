package um.edu.uy.entidades;

public class Evaluacion {
    private String idUsuario;
    private Integer idPelicula;
    private Double puntacion;
    private Integer Timestamp;

    public Evaluacion(String idUsuario, String idPelicula, String puntacion, String Timestamp){
        this.idUsuario = idUsuario;
        this.idPelicula = parseIntSafe(idPelicula);
        this.puntacion = parseDoubleSafe(puntacion);
        this.Timestamp = parseIntSafe(Timestamp);
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

    public String getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
    public Integer getIdPelicula() {
        return idPelicula;
    }
    public void setIdPelicula(Integer idPelicula) {
        this.idPelicula = idPelicula;
    }
    public Double getPuntacion() {
        return puntacion;
    }
    public void setPuntacion(Double puntacion) {
        this.puntacion = puntacion;
    }


}
