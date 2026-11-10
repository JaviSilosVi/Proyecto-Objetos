public class Administrador {

    /**
 * Representa a un administrador del sistema.
 * Tiene permisos para gestionar películas, cines, salas y funciones dentro de la plataforma.
 * 
 * @author Alejandro Mora Tapia, Chino Campos Yeshua Ismael y Salazar Rioú Pablo de Jesús
 * @version 1.0
 */

    /**
    * Constructor por defecto de la clase Administrador.
    */
    public Administrador() {
        // El administrador ya no almacena la base de datos
    }

    /**
     * Registra una nueva película en el catálogo del sistema.
     * 
     * @param nuevaPelicula El objeto Pelicula a registrar.
     * @param sistemaActivo El sistema donde se guardará la película.
     */

    public void registraPelicula(Pelicula nuevaPelicula, Sistema sistemaActivo){
        sistemaActivo.agregarPelicula(nuevaPelicula);
    }

        /**
     * Modifica los datos de una película existente en el sistema.
     * 
     * @param tituloActual El título actual de la película a modificar.
     * @param modificacion El objeto Pelicula con los datos actualizados.
     * @param sistemaActivo El sistema donde se realizará la modificación.
     * @return true si la modificación fue exitosa.
     * @throws EntidadNoEncontradaException Si la película no existe en el catálogo.
     */
    public boolean modificaPelicula(String tituloActual, Pelicula modificacion, Sistema sistemaActivo) throws EntidadNoEncontradaException {
        return sistemaActivo.modificarPelicula(tituloActual, modificacion);
    }

        /**
     * Elimina una película del catálogo del sistema.
     * 
     * @param tituloElimina El título de la película a eliminar.
     * @param sistemaActivo El sistema de donde se eliminará.
     * @return true si fue eliminada correctamente.
     * @throws EntidadNoEncontradaException Si la película no existe en el catálogo.
     */

    public boolean eliminarPelicula(String tituloElimina, Sistema sistemaActivo) throws EntidadNoEncontradaException {
        return sistemaActivo.eliminarPelicula(tituloElimina);
    }

        /**
     * Obtiene una película del catálogo buscando por su título.
     * 
     * @param titulo El título de la película a buscar.
     * @param sistemaActivo El sistema donde se realizará la búsqueda.
     * @return El objeto Pelicula si se encuentra, o null en caso contrario.
     */

    public Pelicula obtenerPelicula(String titulo, Sistema sistemaActivo) {
        return sistemaActivo.obtenerPelicula(titulo);
    }

        /**
     * Programa una nueva función en una sala específica de un cine.
     * 
     * @param idCine El nombre del cine.
     * @param idSala El ID de la sala donde se proyectará.
     * @param nuevaFuncion El objeto Funcion a programar.
     * @param sistemaActivo El sistema activo.
     * @return true si la función fue programada con éxito.
     * @throws ConflictoHorarioException Si ya existe una función en el mismo horario.
     */

    public boolean programarFuncion(String idCine, String idSala, Funcion nuevaFuncion, Sistema sistemaActivo) throws ConflictoHorarioException {
        return sistemaActivo.programarFuncion(idCine, idSala, nuevaFuncion);
    }

        /**
     * Cancela o elimina una función previamente programada.
     * 
     * @param idCine El nombre del cine.
     * @param idSala El ID de la sala.
     * @param funcionACancelar El objeto Funcion a cancelar.
     * @param sistemaActivo El sistema activo.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
    

    public boolean eliminarFuncion(String idCine, String idSala, Funcion funcionACancelar, Sistema sistemaActivo) {
        return sistemaActivo.cancelarFuncion(idCine, idSala, funcionACancelar);
    }

        /**
     * Modifica una función existente sustituyéndola por una nueva.
     * 
     * @param idCine El nombre del cine.
     * @param idSala El ID de la sala.
     * @param funcionActual La función que se desea modificar.
     * @param funcionModificada La nueva función con los datos actualizados.
     * @param sistemaActivo El sistema activo.
     * @return true si la modificación fue exitosa.
     * @throws ConflictoHorarioException Si el nuevo horario genera un empalme de funciones.
     */

    public boolean modificarFuncion(String idCine, String idSala, Funcion funcionActual, Funcion funcionModificada, Sistema sistemaActivo) throws ConflictoHorarioException {
        return sistemaActivo.modificarFuncion(idCine, idSala, funcionActual, funcionModificada);
    }


}