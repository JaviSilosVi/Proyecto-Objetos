import java.util.ArrayList;
import java.util.Iterator;
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

    public void agregaSala(String idSala, Sala nuevaSala) {
        if (nuevaSala != null) {
            nuevaSala.setIdSala(idSala);
            this.salas.add(nuevaSala);
        }
    }

    public Sala obtenerSala(String idSala) {
        Iterator<Sala> iterador = this.salas.iterator();
        while (iterador.hasNext()) {
            Sala salaActual = iterador.next();
            if (salaActual.getIdSala().equalsIgnoreCase(idSala)) {
                return salaActual;
            }
        }
        return null;
    }

    public boolean eliminaSala(String idSala) {
        Iterator<Sala> iterador = this.salas.iterator();
        while (iterador.hasNext()) {
            Sala salaActual = iterador.next();
            if (salaActual.getIdSala().equalsIgnoreCase(idSala)) {
                iterador.remove();
                return true;
            }
        }
        return false;
    }

    public boolean modificaSala(String idActual, String nuevoId, Sala salaActualizada) {
        if (this.eliminaSala(idActual)) {
            if (salaActualizada != null) {
                salaActualizada.setIdSala(nuevoId);
                this.salas.add(salaActualizada);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean programarFuncion(String idSala, Funcion nuevaFuncion) {
        Sala sala = this.obtenerSala(idSala);
        if (sala != null) {
            return sala.programarFuncion(nuevaFuncion);
        } else {
            System.out.println("No se encontro la sala " + idSala + " en el cine " + this.nombreCine);
            return false;
        }
    }

    public boolean cancelarFuncion(String idSala, Funcion funcionACancelar) {
        Sala sala = this.obtenerSala(idSala);
        if (sala != null) {
            return sala.cancelarFuncion(funcionACancelar);
        } else {
            return false;
        }
    }

    public boolean modificarFuncion(String idSala, Funcion funcionActual, Funcion funcionModificada) {
        Sala sala = this.obtenerSala(idSala);
        if (sala != null) {
            return sala.modificarFuncion(funcionActual, funcionModificada);
        } else {
            return false;
        }
    }
}
