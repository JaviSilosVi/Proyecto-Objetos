import java.util.ArrayList;
import java.util.List;

/**
 * Representa una película en el catálogo del cine.
 * Almacena la información descriptiva de la cinta, incluyendo 
 * su duración, clasificación, género y actores principales.
 * 
 * @author Alejandro Mora Tapia, Chino Campos Yeshua Ismael y Salazar Rioú Pablo de Jesús
 * @version 1.0
 */

public class Pelicula {
    private String titulo;
    private String duracion;
    private String clasificacion;
    private String genero;
    private String director;
    private ArrayList<String> actores;
    private String sinopsis;

    /**
     * Constructor de la clase Pelicula.
     * Inicializa y asigna los valores para cada uno de los atributos de la película.
     * 
     * @param titulo El título oficial de la película.
     * @param duracion La duración en formato de texto (ej. "120 min").
     * @param clasificacion La clasificación por edades.
     * @param genero El género cinematográfico.
     * @param director El nombre del director.
     * @param actores Lista de actores principales.
     * @param sinopsis Resumen de la trama.
     */

    public Pelicula( String titulo, String duracion, String clasificacion, String genero, String director, ArrayList<String> actores, String sinopsis ){
        this.titulo = titulo;
        this.duracion = duracion;
        this.clasificacion = clasificacion;
        this.genero = genero;
        this.director = director;
        this.actores = actores;
        this.sinopsis = sinopsis;
    }

        /**
     * Obtiene el título de la película.
     * @return El título.
     */

    public String getTitulo(){
        return this.titulo;
    }

        /**
     * Obtiene la duración de la película.
     * @return La duración.
     */
    public String getDuracion(){
        return this.duracion;
    }

        /**
     * Obtiene la clasificación por edades de la película.
     * @return La clasificación.
     */

    public String getClasificacion()
    {
        return this.clasificacion;
    }

        /**
     * Obtiene el género de la película.
     * @return El género.
     */
    public String getGenero(){
        return this.genero;
    }

        /**
     * Obtiene el nombre del director.
     * @return El director.
     */

    public String getDirector(){
        return this.director;
    }

        /**
     * Obtiene la lista de actores principales.
     * @return Lista de actores.
     */
    public List<String> getActores(){
        return this.actores;
    }

        /**
     * Obtiene la sinopsis de la película.
     * @return La sinopsis.
     */
    public String getSinopsis()
    {
        return this.sinopsis;
    }

    //Setters

        /**
     * Actualiza el título de la película.
     * @param titulo El nuevo título.
     */
    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

        /**
     * Actualiza la duración de la película.
     * @param duracion La nueva duración.
     */

    public void setDuracion(String duracion){
        this.duracion = duracion;
    }

        /**
     * Actualiza la clasificación de la película.
     * @param clasificacion La nueva clasificación.
     */
    public void setClasificacion(String clasificacion){
        this.clasificacion = clasificacion;
    }

        /**
     * Actualiza el género de la película.
     * @param genero El nuevo género.
     */
    public void setGenero(String genero){
        this.genero = genero;
    }

        /**
     * Actualiza el director de la película.
     * @param diretor El nuevo director.
     */
    public void setDirector(String diretor){
        this.director= diretor;
    }

        /**
     * Actualiza la lista de actores.
     * @param actores La nueva lista de actores.
     */
    public void setActores(ArrayList<String> actores){
        this.actores = actores;
    }

        /**
     * Actualiza la sinopsis de la película.
     * @param sinopsis La nueva sinopsis.
     */
    public void setSinopsis(String sinopsis){
        this.sinopsis = sinopsis;
    }
}
