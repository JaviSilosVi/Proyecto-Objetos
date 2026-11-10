/**
 * Representa el método de pago a través de una transferencia interbancaria directa.
 * 
 * @author Alejandro Mora Tapia, Chino Campos Yeshua Ismael y Salazar Rioú Pablo de Jesús
 * @version 1.0
 */

public class TransferenciaElectronica implements MetodoDePago {
    private int cuentaBancaria;

        /**
     * Constructor de la clase TransferenciaElectronica.
     * 
     * @param cuentaBancaria El número de cuenta bancaria o CLABE de origen.
     */

    public TransferenciaElectronica(int cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

        /**
     * Autoriza y procesa el traspaso de fondos desde la cuenta bancaria.
     * 
     * @param monto El dinero a transferir.
     * @return true si la transferencia se completó con éxito.
     */

    @Override
    public boolean procesarPago(double monto) {
        System.out.println("Procesando transferencia de $" + monto + " desde la cuenta bancaria: " + cuentaBancaria);
        return true;
    }
}