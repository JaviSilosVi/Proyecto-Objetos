//El profesor dijo que teniamos que crear una clase Sistema o apk para gestionar todos los cines

import java.util.ArrayList;
import java.util.HashMap; // El hashMap sera mucho mas eficiente, por eso lo refactorizo 
import java.util.List;
import java.util.Map;

/**
 * Representa el núcleo principal de la aplicación.
 * Gestiona de manera centralizada el catálogo de películas, la infraestructura 
 * de cines y el historial de boletos vendidos.
 * 
 * @author Alejandro Mora Tapia, Chino Campos Yeshua Ismael y Salazar Rioú Pablo de Jesús
 * @version 1.0
 */

public class Sistema {

        /**
     * Inicializa los componentes de arranque del sistema.
     * 
     * @return true si el sistema arrancó correctamente.
     */

    public boolean inicia() {
        System.out.println("Sistema iniciado");
        return true;
    }

    // Usaremos este atributo para guardar todos los cines del pais
    private Map<String, Cine> cines;
    private Map<String, Pelicula> catalogoPeliculas;
    private List<Boleto> boletosVendidos;

        /**
     * Constructor de la clase Sistema.
     * Crea las estructuras de datos vacías para almacenar cines, películas y boletos.
     */
    public Sistema() {
        this.cines = new HashMap<>();
        this.catalogoPeliculas = new HashMap<>();
        this.boletosVendidos = new ArrayList<>();
    }

        /**
     * Registra una nueva película en el catálogo global del sistema.
     * 
     * @param nuevaPelicula El objeto Pelicula a añadir.
     */
    public void agregarPelicula(Pelicula nuevaPelicula) {
        this.catalogoPeliculas.put(nuevaPelicula.getTitulo(), nuevaPelicula);
    }

        /**
     * Modifica los datos de una película previamente registrada.
     * 
     * @param tituloActual El título de la película que se desea modificar.
     * @param modificacion El nuevo objeto Pelicula con la información actualizada.
     * @return true si la modificación fue exitosa.
     * @throws EntidadNoEncontradaException Si la película no existe en el catálogo.
     */

    public boolean modificarPelicula(String tituloActual, Pelicula modificacion) throws EntidadNoEncontradaException {
        if(this.catalogoPeliculas.containsKey(tituloActual)){
            this.catalogoPeliculas.remove(tituloActual);
            this.catalogoPeliculas.put(modificacion.getTitulo(), modificacion);
            return true;
        } else {
            throw new EntidadNoEncontradaException("La pelicula con el titulo " + tituloActual + " no se encontro para modificar");
        }
    }

        /**
     * Elimina una película del catálogo global del sistema.
     * 
     * @param titulo El título de la película a eliminar.
     * @return true si se eliminó correctamente.
     * @throws EntidadNoEncontradaException Si la película no se encuentra registrada.
     */
    public boolean eliminarPelicula(String titulo) throws EntidadNoEncontradaException {
        if(this.catalogoPeliculas.containsKey(titulo)){
            this.catalogoPeliculas.remove(titulo);
            return true;
        }else {
            throw new EntidadNoEncontradaException("La pelicula con el titulo " + titulo + " no se encuentra en el catalogo.");
        }
    }

        /**
     * Busca una película específica dentro del catálogo.
     * 
     * @param titulo El título exacto de la película.
     * @return El objeto Pelicula si existe, o null en caso contrario.
     */

    public Pelicula obtenerPelicula(String titulo) {
        return this.catalogoPeliculas.get(titulo);
    }

        /**
     * Delega la programación de una función a una sala de un cine específico.
     * 
     * @param nombreCine El nombre del cine.
     * @param idSala El identificador de la sala.
     * @param nuevaFuncion La función a programar.
     * @return true si la operación fue exitosa.
     * @throws ConflictoHorarioException Si se detecta un empalme de horarios en la sala.
     */

    public boolean programarFuncion(String idCine, String idSala, Funcion nuevaFuncion) throws ConflictoHorarioException {
        Cine cine = this.cines.get(idCine);
        if(cine != null) {
            return cine.programarFuncion(idSala, nuevaFuncion); // La pasamos al Cine
        }
        return false; // si el cine no existe pues falso.
    }

        /**
     * Delega la cancelación de una función a la sala correspondiente.
     * 
     * @param nombreCine El nombre del cine.
     * @param idSala El identificador de la sala.
     * @param funcionACancelar La función a eliminar.
     * @return true si la cancelación fue exitosa, false si no se encontró.
     */

    public boolean eliminarFuncion(String nombreCine, String idSala, String fecha, int horarioHrs){
        Cine cine = this.cines.get(nombreCine);
        if(cine != null){
            Sala sala = cine.obtenerSala(idSala);
            if(sala != null){
                return sala.cancelarFuncion(fecha, horarioHrs);
            }
        }
        return false;
    }

        /**
     * Sustituye una función programada por una nueva con datos actualizados.
     * 
     * @param nombreCine El nombre del cine.
     * @param idSala El identificador de la sala.
     * @param funcionActual La función que se va a modificar.
     * @param funcionModificada La nueva función con los cambios.
     * @return true si se modificó correctamente.
     * @throws ConflictoHorarioException Si el nuevo horario choca con otra proyección.
     */

    public boolean modificarFuncion(String nombreCine, String idSala, String fecha, int horarioHrs, Funcion nuevaFuncion){
        Cine cine = this.cines.get(nombreCine);
        if (cine != null){
            Sala sala = cine.obtenerSala(idSala);
            if(sala != null){
                return sala.cancelarFuncion(fecha, horarioHrs) && sala.agregarFuncion(nuevaFuncion);
            }
        }
    
        return false;
    }

        /**
     * Filtra el catálogo de películas buscando coincidencias por actor.
     * 
     * @param actorBuscado El nombre del actor.
     * @return Una lista de películas en las que participa dicho actor.
     */

    public List<Pelicula> buscarPeliculasPorActor(String actorBuscado) {
        List<Pelicula> encontradas = new ArrayList<>();
        for (Pelicula p : this.catalogoPeliculas.values()) {
            if (p.getActores().contains(actorBuscado)) {
                encontradas.add(p);
            }
        }
        return encontradas;
    }

        /**
     * Filtra el catálogo de películas buscando coincidencias por clasificación de edad.
     * 
     * @param clasificacion La clasificación deseada (ej. "B", "C").
     * @return Una lista de películas que coinciden con el filtro.
     */
    
    public List<Pelicula> buscarPeliculasPorClasificacion(String clasificacion) {
        List<Pelicula> encontradas = new ArrayList<>();
        for (Pelicula p : this.catalogoPeliculas.values()) {
            if (p.getClasificacion().equals(clasificacion)) {
                encontradas.add(p);
            }
        }
        return encontradas;
    }

        /**
     * Registra una nueva sucursal de cine en el sistema.
     * 
     * @param cineNuevo El objeto Cine a añadir.
     */

    public void agregarCine(Cine nuevoCine) {
        this.cines.put(nuevoCine.getNombreCine(), nuevoCine);
    }

        /**
     * Busca un cine registrado mediante su nombre.
     * 
     * @param nombreCine El nombre de la sucursal.
     * @return El objeto Cine si existe, o null en caso contrario.
     */

    public Cine obtenerCine(String nombreCine) {
        return this.cines.get(nombreCine);
    }

        /**
     * Da de baja una sucursal de cine del sistema.
     * 
     * @param nombreCine El nombre del cine a eliminar.
     * @return true si fue eliminado correctamente.
     */

    public boolean eliminarCine(String nombreCine) {
        if (this.cines.containsKey(nombreCine)) {
            this.cines.remove(nombreCine);
            return true;
        }
        return false;
    }

        /**
     * Actualiza la información de una sucursal de cine registrada.
     * 
     * @param nombreActual El nombre actual del cine.
     * @param cineActualizado El nuevo objeto Cine con los datos modificados.
     * @return true si la modificación fue exitosa.
     */

    public boolean modificarCine(String nombreActual, Cine cineActualizado) {
        if (this.cines.containsKey(nombreActual)) {
            this.cines.remove(nombreActual);
            this.cines.put(cineActualizado.getNombreCine(), cineActualizado);
            return true;
        }
        return false;
    }

    public boolean cancelarFuncion(String idCine, String idSala, Funcion funcionACancelar) {
    Cine cine = this.cines.get(idCine);
    if(cine != null) {
        return cine.cancelarFuncion(idSala, funcionACancelar);
    }
    return false;
    }

    public boolean modificarFuncion(String idCine, String idSala, Funcion funcionActual, Funcion funcionModificada) throws ConflictoHorarioException {
        Cine cine = this.cines.get(idCine);
        if(cine != null) {
            return cine.modificarFuncion(idSala, funcionActual, funcionModificada);
        }
        return false;
    }

        /**
     * Obtiene el registro completo de películas disponibles.
     * 
     * @return Un mapa con los títulos de las películas y sus objetos asociados.
     */

    public Map<String, Pelicula> getCatalogoPeliculas() {
        return this.catalogoPeliculas;
    }

        /**
     * Obtiene el registro completo de los cines operados por el sistema.
     * 
     * @return Un mapa con los nombres de los cines y sus respectivos objetos.
     */

    public Map<String, Cine> getCines() {
        return this.cines;
    }

        /**
     * Guarda un boleto en el historial histórico de ventas del sistema.
     * 
     * @param boleto El comprobante de venta a registrar.
     */

    public void registrarBoleto(Boleto boleto) {
        if (boleto != null) {
            this.boletosVendidos.add(boleto);
        }
    }

        /**
     * Obtiene el listado completo de boletos vendidos hasta el momento.
     * 
     * @return Una lista de objetos Boleto.
     */

    public List<Boleto> getBoletosVendidos() {
        return this.boletosVendidos;
    }
}