import java.util.HashMap;
import java.util.Map;

/**
 * Servicio con Responsabilidad Única (SRP):
 * Maneja exclusivamente las altas, bajas, modificaciones y consultas de cines.
 */
public class GestorCines {
    private Map<String, Cine> cines;

    public GestorCines() {
        this.cines = new HashMap<>();
    }

    public Map<String, Cine> getCines() {
        return this.cines;
    }

    public void setCines(Map<String, Cine> cines) {
        this.cines = cines;
    }

    public void agregaCine(Cine cineNuevo) {
        if (cineNuevo != null) {
            this.cines.put(cineNuevo.getNombreCine(), cineNuevo);
        }
    }

    public Cine obtenerCine(String nombreCine) throws EntidadNoEncontradaException {
        if (nombreCine == null || nombreCine.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cine no puede estar vacío.");
        }
        if (this.cines == null) {
            throw new EntidadNoEncontradaException("El catálogo de cines está vacío.");
        }
        String busqueda = nombreCine.trim().toLowerCase();

        for (Cine c : this.cines.values()) {
            if (c.getNombreCine().equalsIgnoreCase(busqueda)) {
                return c;
            }
        }
        for (Cine c : this.cines.values()) {
            if (c.getNombreCine().toLowerCase().contains(busqueda)) {
                return c;
            }
        }
        throw new EntidadNoEncontradaException("El cine '" + nombreCine + "' no existe en el sistema.");
    }

    public boolean eliminarCine(String nombreCine) throws EntidadNoEncontradaException {
        Cine cine = obtenerCine(nombreCine);
        this.cines.remove(cine.getNombreCine());
        return true;
    }

    public boolean modificarCine(String nombreActual, Cine cineActualizado) throws EntidadNoEncontradaException {
        Cine cine = obtenerCine(nombreActual);
        if (cineActualizado == null) {
            throw new IllegalArgumentException("El cine actualizado no puede ser nulo.");
        }
        this.cines.remove(cine.getNombreCine());
        this.cines.put(cineActualizado.getNombreCine(), cineActualizado);
        return true;
    }
}
