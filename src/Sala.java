import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class Sala {
    private Map<String, Asiento> asientos;
    private List<Funcion> programacion;
    private String idSala;
    private int filas;
    private int asientosPorFila;
    private String tipoSala;
    private double precioBase;

    public Sala(String idSala) {
        this.idSala = idSala;
        this.filas = 0;
        this.asientosPorFila = 0;
        this.tipoSala = "TRADICIONAL";
        this.precioBase = 0.0;
        this.asientos = new HashMap<>();
        this.programacion = new ArrayList<>();
    }

    public Sala(String idSala, int filas, int asientosPorFila, double precioBase) {
        this.idSala = idSala;
        this.filas = filas;
        this.asientosPorFila = asientosPorFila;
        this.tipoSala = "TRADICIONAL";
        this.precioBase = precioBase;
        this.asientos = new HashMap<>();
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

    public String getTipoSala() {
        return tipoSala;
    }

    public void setTipoSala(String tipoSala) {
        this.tipoSala = tipoSala;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public Map<String, Asiento> getAsientos() {
        return asientos;
    }

    public void setAsientos(Map<String, Asiento> asientos) {
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

    public void agregaAsiento(Asiento asiento) {
        if (asiento != null) {
            this.asientos.put(asiento.getIdentificador(), asiento);
        }
    }

    public Asiento obtenerAsiento(String idAsiento) {
        if (this.asientos != null) {
            return this.asientos.get(idAsiento);
        }
        return null;
    }

    public void generarAsientos(int filas, int asientoPorFila, String tipo, double precio) {
        this.filas = filas;
        this.asientosPorFila = asientoPorFila;
        this.tipoSala = tipo;
        this.precioBase = precio;
        this.asientos.clear();

        for (int i = 0; i < filas; i++) {
            char letraFila = (char) ('A' + i);
            for (int j = 1; j <= asientoPorFila; j++) {
                String idAsiento = letraFila + String.valueOf(j);
                Asiento nuevoAsiento = new Asiento(idAsiento, tipo, precio);
                this.asientos.put(idAsiento, nuevoAsiento);
            }
        }
    }

    public boolean agregarFuncion(Funcion nuevaFuncion) {
        if (nuevaFuncion == null) {
            return false;
        }

        Iterator<Funcion> iterador = this.programacion.iterator();
        while (iterador.hasNext()) {
            Funcion funcionActual = iterador.next();
            if (funcionActual.getFecha().equals(nuevaFuncion.getFecha())) {
                if (funcionActual.getHorarioHrs() == nuevaFuncion.getHorarioHrs()) {
                    System.out.println("Conflicto de horario: ya hay una funcion programada a esa hora.");
                    return false;
                }
            }
        }

        nuevaFuncion.setSala(this);
        nuevaFuncion.inicializarAsientos(this.asientos);
        this.programacion.add(nuevaFuncion);
        return true;
    }

    public boolean programarFuncion(Funcion nuevaFuncion) {
        return this.agregarFuncion(nuevaFuncion);
    }

    public boolean cancelarFuncion(String fecha, int horarioHrs) {
        Iterator<Funcion> iterador = this.programacion.iterator();
        while (iterador.hasNext()) {
            Funcion funcionActual = iterador.next();
            if (funcionActual.getFecha().equals(fecha)) {
                if (funcionActual.getHorarioHrs() == horarioHrs) {
                    iterador.remove();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean cancelarFuncion(Funcion funcionACancelar) {
        if (funcionACancelar != null) {
            return this.cancelarFuncion(funcionACancelar.getFecha(), funcionACancelar.getHorarioHrs());
        } else {
            return false;
        }
    }

    public boolean modificarFuncion(Funcion funcionActual, Funcion funcionModificada) {
        if (this.cancelarFuncion(funcionActual)) {
            return this.agregarFuncion(funcionModificada);
        } else {
            return false;
        }
    }

    public void mostrarAsientos() {
        System.out.println("=== Asientos de la sala " + this.idSala + " ===");
        if (this.asientos != null) {
            for (Asiento a : this.asientos.values()) {
                System.out.print("[" + a.getIdentificador() + " - " + a.getEstadoAsiento() + "] ");
            }
        }
        System.out.println();
    }
}
