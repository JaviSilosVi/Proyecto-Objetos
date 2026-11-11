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

    public Sala obtenerSala(String idSala) throws EntidadNoEncontradaException {
        if (this.salas == null || !this.salas.containsKey(idSala)) {
            throw new EntidadNoEncontradaException("La sala '" + idSala + "' no existe en el cine " + this.nombreCine);
        }
        return this.salas.get(idSala);
    }

    public boolean eliminaSala(String idSala) throws EntidadNoEncontradaException {
        if (this.salas == null || !this.salas.containsKey(idSala)) {
            throw new EntidadNoEncontradaException("No se puede eliminar: La sala '" + idSala + "' no existe en el cine " + this.nombreCine);
        }
        this.salas.remove(idSala);
        return true;
    }

    public boolean modificaSala(String idActual, String nuevoId, Sala salaActualizada) throws EntidadNoEncontradaException {
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

    public boolean programarFuncion(String idSala, Funcion nuevaFuncion) throws EntidadNoEncontradaException, ConflictoHorarioException {
        Sala sala = this.obtenerSala(idSala);
        return sala.programarFuncion(nuevaFuncion);
    }

    public boolean cancelarFuncion(String idSala, Funcion funcionACancelar) throws EntidadNoEncontradaException {
        Sala sala = this.obtenerSala(idSala);
        return sala.cancelarFuncion(funcionACancelar);
    }

    public boolean modificarFuncion(String idSala, Funcion funcionActual, Funcion funcionModificada) throws EntidadNoEncontradaException, ConflictoHorarioException {
        Sala sala = this.obtenerSala(idSala);
        return sala.modificarFuncion(funcionActual, funcionModificada);
    }
}
