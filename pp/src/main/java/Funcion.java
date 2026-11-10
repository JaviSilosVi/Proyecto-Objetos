import java.util.HashMap;
import java.util.Map;

/**
 * Representa una función o proyección programada en una sala de cine.
 * Almacena la fecha, horario, la película proyectada y el estado de los asientos para esa función.
 * 
 * @author Alejandro Mora Tapia, Chino Campos Yeshua Ismael y Salazar Rioú Pablo de Jesús
 * @version 1.0
 */

public class Funcion {
    private String fecha;
    private int horarioHrs;
    private int horarioMin;
    private Pelicula peliculaProyecta;
    private Map<String, Asiento> asientos;

        
        /**
     * Constructor de la clase Funcion.
     * Inicializa y asigna los valores de fecha, horario y la película a proyectar.
     * 
     * @param fecha La fecha de la función (ej. "2026-05-25").
     * @param horarioHrs La hora de la función en formato de 24 horas.
     * @param horarioMin Los minutos exactos de la función.
     * @param peliculaProyecta El objeto Pelicula que se mostrará en esta función.
     */

    public Funcion(String fecha, int horarioHrs, int horarioMin, Pelicula peliculaProyecta){
        this.fecha = fecha;
        this.horarioHrs = horarioHrs;
        this.horarioMin = horarioMin;
        this.peliculaProyecta = peliculaProyecta;
        this.asientos = new HashMap<>();
    }

    /**
     * Asigna la película que se proyectará en esta función específica.
     * 
     * @param peliculaProyecta El objeto Pelicula a asignar.
     */
    public void asignarPeliculas(Pelicula peliculaProyecta){
        this.peliculaProyecta = peliculaProyecta;
    }
    
        /**
     * Obtiene la fecha programada de la función.
     * 
     * @return La fecha de la función en formato de texto.
     */
    public String getFecha(){
        return this.fecha;
    }

    /**
     * Obtiene el horario de la función en formato de 24 horas.
     * 
     * @return Las horas programadas para el inicio de la función.
     */
    public int getHorarioHrs(){
        return this.horarioHrs;
    }
        /**
     * Obtiene los minutos de la hora programada para la función.
     * 
     * @return Los minutos de la función.
     */

    public int getHorarioMin(){
        return this.horarioMin;
    }

        /**
     * Obtiene la película asociada a esta función específica.
     * 
     * @return El objeto Pelicula que se proyectará.
     */

    public Pelicula getPeliculaProyecta(){
        return this.peliculaProyecta;
    }

        /**
     * Obtiene el diccionario completo de asientos específicos para esta función.
     * 
     * @return Un mapa con los identificadores de asientos y sus objetos Asiento asociados.
     */
    public Map<String, Asiento> getAsientos() {
        return this.asientos;
    }

        /**
     * Inicializa los asientos de la función copiando la estructura de asientos físicos de la sala.
     * 
     * @param plantillaAsientos El mapa de asientos originales de la Sala para duplicarlos en la función.
     */
    public void inicializarAsientos(Map<String, Asiento> plantillaAsientos) {
        this.asientos = new HashMap<>();
            for (Asiento plantilla : plantillaAsientos.values()) {
            this.asientos.put(plantilla.getIdentificador(), new Asiento(plantilla.getIdentificador(),
                plantilla.getTipoAsiento(),
                plantilla.getPrecio()));
            }
    }

        /**
     * Busca y devuelve un asiento específico de esta función mediante su identificador.
     * 
     * @param idAsiento El código alfanumérico del asiento (ej. "A1").
     * @return El objeto Asiento si existe, o null en caso contrario.
     */
    public Asiento obtenerAsiento(String idAsiento) {
        if (this.asientos == null) return null;
        return this.asientos.get(idAsiento);
    }

        /**
     * Compara si dos funciones son iguales basándose exactamente en su fecha y hora.
     * 
     * @param obj El objeto con el cual se comparará esta función.
     * @return true si ambas funciones tienen la misma fecha y hora, false en caso contrario.
     */

    @Override
    public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Funcion otraFuncion = (Funcion) obj;
    // aqui dos funciones son iguales si se proyectan en la misma fecha y hora
    return this.fecha.equals(otraFuncion.getFecha()) &&
           this.horarioHrs == otraFuncion.getHorarioHrs() &&
           this.horarioMin == otraFuncion.getHorarioMin();
}
}
