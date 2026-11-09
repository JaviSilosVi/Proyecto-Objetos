/**
 * Representa la abstracción de un usuario genérico en el sistema.
 * Sirve como clase base para heredar atributos comunes como el nombre.
 * 
 * @author Alejandro Mora Tapia, Chino Campos Yeshua Ismael y Salazar Rioú Pablo de Jesús
 * @version 1.0
 */

public abstract class Usuario {
    private String nombre;

        /**
     * Constructor de la clase Usuario.
     * 
     * @param nombre El nombre completo del usuario.
     */

    public Usuario(String nombre) {
        this.nombre = nombre;
    }

        /**
     * Obtiene el nombre del usuario.
     * 
     * @return El nombre asignado.
     */

    public String getNombre() {
        return this.nombre;
    }
}
