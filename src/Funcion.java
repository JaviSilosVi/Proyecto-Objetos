import java.util.ArrayList;
import java.util.List;

public class Funcion {
    private String fecha;
    private int horarioHrs;
    private int horarioMin;
    private Pelicula peliculaProyectada;
    private Sala sala;
    private List<Asiento> asientos;

    public Funcion(String fecha, int horarioHrs, int horarioMin, Pelicula peliculaProyectada) {
        this.fecha = fecha;
        this.horarioHrs = horarioHrs;
        this.horarioMin = horarioMin;
        this.peliculaProyectada = peliculaProyectada;
        this.asientos = new ArrayList<>();
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getHorarioHrs() {
        return horarioHrs;
    }

    public void setHorarioHrs(int horarioHrs) {
        this.horarioHrs = horarioHrs;
    }

    public int getHorarioMin() {
        return horarioMin;
    }

    public void setHorarioMin(int horarioMin) {
        this.horarioMin = horarioMin;
    }

    public Pelicula getPeliculaProyectada() {
        return peliculaProyectada;
    }

    public void setPeliculaProyectada(Pelicula peliculaProyectada) {
        this.peliculaProyectada = peliculaProyectada;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
    }

    public void asignarPeliculas(Pelicula peliculaProyectada) {
        this.peliculaProyectada = peliculaProyectada;
    }
}
