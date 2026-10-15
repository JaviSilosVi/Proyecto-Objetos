import java.util.ArrayList;
import java.util.List;

public class Cine {
    private String nombreCine;
    private List<Sala> salas;

    public Cine(String nombreCine) {
        this.nombreCine = nombreCine;
        this.salas = new ArrayList<>();
    }

    public String getNombreCine() {
        return nombreCine;
    }

    public void setNombreCine(String nombreCine) {
        this.nombreCine = nombreCine;
    }

    public List<Sala> getSalas() {
        return salas;
    }

    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }
}
