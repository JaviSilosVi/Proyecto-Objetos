import java.util.HashMap;
import java.util.Map;

public class Funcion {
    private String fecha;
    private int horarioHrs;
    private int horarioMin;
    private Pelicula peliculaProyectada;
    private Sala sala;
    private Map<String, Asiento> asientos;

    public Funcion(String fecha, int horarioHrs, int horarioMin, Pelicula peliculaProyectada) {
        this.fecha = fecha;
        this.horarioHrs = horarioHrs;
        this.horarioMin = horarioMin;
        this.peliculaProyectada = peliculaProyectada;
        this.asientos = new HashMap<>();
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

    public Map<String, Asiento> getAsientos() {
        return asientos;
    }

    public void setAsientos(Map<String, Asiento> asientos) {
        this.asientos = asientos;
    }

    public void asignarPeliculas(Pelicula peliculaProyectada) {
        this.peliculaProyectada = peliculaProyectada;
    }

    public void inicializarAsientos(Map<String, Asiento> plantillaAsientos) {
        this.asientos.clear();
        if (plantillaAsientos != null) {
            for (Asiento plantilla : plantillaAsientos.values()) {
                Asiento nuevo = new Asiento(plantilla.getIdentificador(), plantilla.getTipoAsiento(), plantilla.getPrecio());
                this.asientos.put(nuevo.getIdentificador(), nuevo);
            }
        }
    }

    public Asiento obtenerAsiento(String idAsiento) {
        if (this.asientos != null) {
            return this.asientos.get(idAsiento);
        }
        return null;
    }
}
