    /**
     * Representa el método de pago a través de una cuenta de PayPal.
     * 
     * @author Alejandro Mora Tapia, Chino Campos Yeshua Ismael y Salazar Rioú Pablo de Jesús
     * @version 1.0
     */

public class Paypal implements MetodoDePago {
    private String correo;

        /**
     * Constructor de la clase Paypal.
     * 
     * @param correo El correo electrónico asociado a la cuenta de PayPal.
     */

    public Paypal(String correo) {
        this.correo = correo;
    }

        /**
     * Ejecuta la transacción de cobro utilizando la plataforma PayPal.
     * 
     * @param monto El total a cobrar.
     * @return true si la transacción fue exitosa.
     */

    @Override
    public boolean procesarPago(double monto) {
        System.out.println("Procesando pago de $" + monto + " a través de PayPal (Cuenta: " + correo + ")");
        return true; 
    }
}