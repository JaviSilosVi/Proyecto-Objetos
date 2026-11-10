import java.util.Map;
import java.util.HashMap;

/**
 * Representa una sucursal física de cine.
 * Gestiona un conjunto de salas donde se proyectan las películas.
 * 
 * @author Alejandro Mora Tapia, Chino Campos Yeshua Ismael y Salazar Rioú Pablo de Jesús
 * @version 1.0
 */

public class Cine {
    
    private String nombreCine;
    private Map<String, Sala> salas;

        /**
     * Constructor de la clase Cine.
     * Inicializa el nombre del cine y crea la estructura vacía para almacenar las salas.
     * 
     * @param nombreCine El nombre asignado a la sucursal del cine.
     */

    public Cine(String nombreCine) {
        this.nombreCine = nombreCine;
        this.salas = new HashMap<>();
    }

        /**
     * Obtiene el nombre de este cine.
     * 
     * @return El nombre de la sucursal.
     */
    public String getNombreCine() {
        return nombreCine;
    }
    

        /**
     * Agrega una nueva sala al diccionario de salas de este cine.
     * 
     * @param idSala El identificador único de la sala.
     * @param nuevaSala El objeto Sala a registrar.
     */
     public void agregarSala(String idSala, Sala nuevaSala) {
        this.salas.put(idSala, nuevaSala);
    }

        /**
     * Busca y devuelve una sala específica dentro del cine.
     * 
     * @param idSala El identificador de la sala a buscar.
     * @return El objeto Sala si se encuentra, o null en caso contrario.
     */
    public Sala obtenerSala(String idSala) {
        return this.salas.get(idSala);
    }

    /**
     * Elimina una sala del registro del cine.
     * 
     * @param idSala El identificador de la sala a eliminar.
     * @return true si la sala fue eliminada con éxito, false si no existía.
     */

    public boolean eliminarSala(String idSala) {
        if (this.salas.containsKey(idSala)) {
            this.salas.remove(idSala);
            return true;
        }
        return false;
    }


        /**
     * Modifica los datos de una sala existente, reemplazándola por una actualizada.
     * 
     * @param idActual El identificador actual de la sala.
     * @param nuevoId El nuevo identificador de la sala.
     * @param salaActualizada El nuevo objeto Sala con los datos actualizados.
     * @return true si la modificación fue exitosa, false si la sala original no existía.
     */
    public boolean modificarSala(String idActual, String nuevoId, Sala salaActualizada) {
        if (this.salas.containsKey(idActual)) {
            this.salas.remove(idActual);
            this.salas.put(nuevoId, salaActualizada);
            return true;
        }
        return false;
    }

        /**
     * Delega la programación de una nueva función a una sala específica del cine.
     * 
     * @param idSala El identificador de la sala donde se proyectará la función.
     * @param nuevaFuncion El objeto Funcion a programar.
     * @return true si la función fue programada exitosamente, false si la sala no existe.
     * @throws ConflictoHorarioException Si el horario de la nueva función choca con una ya existente en la sala.
     */
    public boolean programarFuncion(String idSala, Funcion nuevaFuncion) throws ConflictoHorarioException {
        Sala sala = this.salas.get(idSala);
        if(sala != null) {
            return sala.programarFuncion(nuevaFuncion); // lo pasamos a la Sala
        }
        return false; 
    }

        /**
     * Delega la cancelación de una función a una sala específica.
     * 
     * @param idSala El identificador de la sala.
     * @param funcionACancelar El objeto Funcion a eliminar.
     * @return true si la cancelación fue exitosa.
     */

    public boolean cancelarFuncion(String idSala, Funcion funcionACancelar) {
    Sala sala = this.salas.get(idSala);
    if(sala != null) {
        return sala.cancelarFuncion(funcionACancelar); // La sala hace el .remove()
    }
    return false;
    }

        /**
     * Delega la modificación de una función existente en una de sus salas.
     * 
     * @param idSala El identificador de la sala.
     * @param funcionActual La función original que se desea modificar.
     * @param funcionModificada La nueva función con los datos actualizados.
     * @return true si la modificación fue exitosa.
     * @throws ConflictoHorarioException Si el nuevo horario genera un empalme en la sala.
     */

    public boolean modificarFuncion(String idSala, Funcion funcionActual, Funcion funcionModificada) throws ConflictoHorarioException {
        Sala sala = this.salas.get(idSala);
        if(sala != null) {
            return sala.modificarFuncion(funcionActual, funcionModificada);
        }
        return false;
    }

        /**
     * Obtiene el diccionario completo de las salas pertenecientes a este cine.
     * 
     * @return Un mapa que asocia el ID de cada sala con su objeto Sala.
     */
    public Map<String, Sala> getSalas() {
        return this.salas;
    }

}