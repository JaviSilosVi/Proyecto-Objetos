import java.util.ArrayList;
import java.util.Iterator;
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

    public void agregaAsiento(Asiento asiento) {
        if (asiento != null) {
            this.asientos.add(asiento);
        }
    }

    public Asiento obtenerAsiento(String idAsiento) {
        Iterator<Asiento> iterador = this.asientos.iterator();
        while (iterador.hasNext()) {
            Asiento asientoActual = iterador.next();
            if (asientoActual.getIdentificador().equalsIgnoreCase(idAsiento)) {
                return asientoActual;
            }
        }
        return null;
    }

    public void generarAsientos(int filas, int asientoPorFila, String tipo, double precio) {
        this.filas = filas;
        this.asientosPorFila = asientoPorFila;
        this.precioBase = precio;
        this.asientos.clear();

        for (int i = 0; i < filas; i++) {
            char letraFila = (char) ('A' + i);
            for (int j = 1; j <= asientoPorFila; j++) {
                String idAsiento = letraFila + String.valueOf(j);
                Asiento nuevoAsiento = new Asiento(idAsiento, tipo, precio);
                this.asientos.add(nuevoAsiento);
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
        nuevaFuncion.inicializarAsientos();
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
        Iterator<Asiento> iterador = this.asientos.iterator();
        while (iterador.hasNext()) {
            Asiento a = iterador.next();
            System.out.print("[" + a.getIdentificador() + " - " + a.getEstadoAsiento() + "] ");
        }
        System.out.println();
    }
}
