import java.util.HashMap;
import java.util.Map;

public class Cine {
    private String nombreCine;
    private Map<String, Sala> salas;

    public Cine(String nombreCine) {
        this.nombreCine = nombreCine;
        this.salas = new HashMap<>();
    }

    public String getNombreCine() {
        return nombreCine;
    }

    public void setNombreCine(String nombreCine) {
        this.nombreCine = nombreCine;
    }

    public Map<String, Sala> getSalas() {
        return salas;
    }

    public void setSalas(Map<String, Sala> salas) {
        this.salas = salas;
    }

    public void agregaSala(String idSala, Sala nuevaSala) {
        if (nuevaSala != null) {
            nuevaSala.setIdSala(idSala);
            this.salas.put(idSala, nuevaSala);
        }
    }

    public Sala obtenerSala(String idSala) {
        if (this.salas != null) {
            return this.salas.get(idSala);
        }
        return null;
    }

    public boolean eliminaSala(String idSala) {
        if (this.salas != null && this.salas.containsKey(idSala)) {
            this.salas.remove(idSala);
            return true;
        }
        return false;
    }

    public boolean modificaSala(String idActual, String nuevoId, Sala salaActualizada) {
        if (this.eliminaSala(idActual)) {
            if (salaActualizada != null) {
                salaActualizada.setIdSala(nuevoId);
                this.salas.put(nuevoId, salaActualizada);
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
