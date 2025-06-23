package um.edu.uy;

public class Evaluacion {
    private String idUsuario;
    private int idPelicula;
    private Double puntacion;
    private int Timestamp;

    public Evaluacion(String idUsuario, int idPelicula, Double puntacion, int Timestamp){
        this.idUsuario = idUsuario;
        this.idPelicula = idPelicula;
        this.puntacion = puntacion;
        this.Timestamp = Timestamp;
    }
}
