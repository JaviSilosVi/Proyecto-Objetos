import java.util.ArrayList;

/**
 * Representa a un cliente del sistema de cine.
 * Un cliente hereda de Usuario y tiene la capacidad de comprar boletos.
 * 
 * @author Alejandro Mora Tapia, Chino Campos Yeshua Ismael y Salazar Rioú Pablo de Jesús
 * @version 1.0
 */


public class Cliente extends Usuario {

        /**
     * Constructor de la clase Cliente.
     * 
     * @param nombre El nombre del cliente.
     */

    public Cliente(String nombre) {
        super(nombre); 
    }

        /**
     * Realiza el proceso de compra de un boleto, validando asientos y procesando el pago.
     * 
     * @param funcion La función para la cual se comprará el boleto.
     * @param idSala El identificador de la sala donde se proyecta la función.
     * @param seleccion La lista de asientos que el cliente desea comprar.
     * @param metodoPago El método de pago elegido (Tarjeta, Paypal, Transferencia).
     * @return El objeto Boleto generado si el pago es exitoso, o null si el pago es rechazado.
     * @throws AsientoOcupadoException Si alguno de los asientos seleccionados ya fue vendido previamente.
     */
    public Boleto comprarBoleto(Funcion funcion, String idSala, ArrayList<Asiento> seleccion, MetodoDePago metodoPago) throws AsientoOcupadoException{
        double montoACobrar = 0;

        // Aqui validamos disponibilidad y bloqueamos los asientos
        for (Asiento asiento : seleccion) {
            asiento.vender();
            montoACobrar += asiento.getPrecio();
        }

        
        if (metodoPago.procesarPago(montoACobrar)) {
            
            // aqui generamos y damos el comprobante
            Boleto nuevoBoleto = new Boleto(funcion.getPeliculaProyecta(), idSala, funcion, seleccion);
            System.out.println("¡Compra exitosa, " + this.getNombre() + "!");
            return nuevoBoleto;
        }

        System.out.println("Error: El pago fue rechazado.");
        return null; 
    }
}