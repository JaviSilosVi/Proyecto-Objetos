/**
 * Representa un asiento físico dentro de una sala de cine.
 * Almacena su identificador, tipo, precio y estado actual.
 * 
 * @author Alejandro Mora Tapia, Chino Campos Yeshua Ismael y Salazar Rioú Pablo de Jesús
 * @version 1.0
 */

public class Asiento {
    
    private String identificador; // ejemplo "F12"
    private String tipoAsiento;   // "NORMAL", "VIP", etc
    private String estadoAsiento; // "DISPONIBLE", "VENDIDO"
    private double precio;

    
        /**
     * Constructor de la clase Asiento.
     * Por defecto, un asiento recién creado se inicializa con el estado "DISPONIBLE".
     * 
     * @param identificador El código alfanumérico del asiento (ej. "F12").
     * @param tipoAsiento La categoría del asiento ("NORMAL", "VIP", etc.).
     * @param precio El costo del asiento.
     */

    public Asiento(String identificador, String tipoAsiento, double precio) {
        this.identificador = identificador;
        this.tipoAsiento = tipoAsiento;
        this.precio = precio;
        this.estadoAsiento = "DISPONIBLE"; 
    }

        /**
     * Obtiene el estado actual de disponibilidad del asiento.
     * 
     * @return El estado del asiento ("DISPONIBLE", "RESERVADO" o "VENDIDO").
     */

    public String getEstadoAsiento (){
        return estadoAsiento;
    }

        /**
     * Obtiene el identificador del asiento en la sala.
     * 
     * @return El código alfanumérico del asiento.
     */

    public String getIdentificador (){
        return identificador;
    }

        /**
     * Cambia el estado del asiento a "RESERVADO" si se encuentra disponible.
     * 
     * @return true si la reserva fue exitosa, false en caso contrario.
     */

    public boolean reservar() {
        if (this.estadoAsiento.equals("DISPONIBLE")) {
            this.estadoAsiento = "RESERVADO";
            return true;
        } else {
            return false;
        }
    }

        /**
     * Concreta la venta del asiento cambiando su estado a "VENDIDO".
     * 
     * @return true si la venta se realizó exitosamente.
     * @throws AsientoOcupadoException Si el asiento ya no se encuentra en estado "DISPONIBLE".
     */

    public boolean vender() throws AsientoOcupadoException { 
        if (this.estadoAsiento.equals("DISPONIBLE")) { 
            this.estadoAsiento = "VENDIDO"; 
            return true; } 
        else { 
            throw new AsientoOcupadoException("El asiento " + this.identificador + " ya ha sido vendido");
        } 
    }

        /**
     * Obtiene el precio individual del asiento.
     * 
     * @return El precio asignado.
     */

    public double getPrecio() {
        return this.precio; 
    }

        /**
     * Obtiene la categoría a la que pertenece el asiento.
     * 
     * @return El tipo de asiento.
     */

    public String getTipoAsiento() {
        return tipoAsiento;
 }

  }
