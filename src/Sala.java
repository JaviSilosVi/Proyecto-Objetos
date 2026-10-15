import java.util.ArrayList;
import java.util.List;

public class Sala {
    private String idSala;
    private int filas;
    private int asientosPorFila;
    private double precioBase;
    private List<Asiento> asientos;
    private List<Funcion> programacion;

    public Sala(String idSala) {
        this.idSala = idSala;
        this.filas = 0;
        this.asientosPorFila = 0;
        this.precioBase = 0.0;
        this.asientos = new ArrayList<>();
        this.programacion = new ArrayList<>();
    }

    public Sala(String idSala, int filas, int asientosPorFila, double precioBase) {
        this.idSala = idSala;
        this.filas = filas;
        this.asientosPorFila = asientosPorFila;
        this.precioBase = precioBase;
        this.asientos = new ArrayList<>();
        this.programacion = new ArrayList<>();
    }

    public String getIdSala() {
        return idSala;
    }

    public void setIdSala(String idSala) {
        this.idSala = idSala;
    }

    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public int getAsientosPorFila() {
        return asientosPorFila;
    }

    public void setAsientosPorFila(int asientosPorFila) {
        this.asientosPorFila = asientosPorFila;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public String getTipoSala() {
        return "TRADICIONAL";
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
    }

    public List<Funcion> getProgramacion() {
        return programacion;
    }

    public void setProgramacion(List<Funcion> programacion) {
        this.programacion = programacion;
    }

    public int getTotalAsientos() {
        if (this.asientos != null) {
            return this.asientos.size();
        } else {
            return 0;
        }
    }
}
