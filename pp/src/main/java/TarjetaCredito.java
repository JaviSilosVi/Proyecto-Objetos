/**
 * Representa el método de pago mediante una tarjeta de crédito física o digital.
 * 
 * @author Alejandro Mora Tapia, Chino Campos Yeshua Ismael y Salazar Rioú Pablo de Jesús
 * @version 1.0
 */

public class TarjetaCredito implements MetodoDePago {
    private String numeroTarjeta;

        /**
     * Constructor de la clase TarjetaCredito.
     * 
     * @param numeroTarjeta El número de la tarjeta (16 dígitos).
     */


    public TarjetaCredito(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

        /**
     * Valida la tarjeta y ejecuta el cargo correspondiente.
     * 
     * @param monto La cantidad a cobrar a la tarjeta.
     * @return true si el cargo es aceptado.
     */

    @Override
    public boolean procesarPago(double monto) {
        // Validamos que al menos tenga 4 números
        if(numeroTarjeta.length() >= 4) {
            System.out.println("Procesando cobro de $" + monto + " a la tarjeta terminada en " + numeroTarjeta.substring(numeroTarjeta.length() - 4));
            return true; 
        } else {
            System.out.println("Error: Tarjeta invalida, procesando cobro generico de $" + monto);
            return true;
        }
    }
}