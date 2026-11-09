/**
 * Define el contrato estándar para cualquier método de pago que 
 * se integre en el sistema de venta de boletos.
 * 
 * @author Alejandro Mora Tapia, Chino Campos Yeshua Ismael y Salazar Rioú Pablo de Jesús
 * @version 1.0
 */

public interface MetodoDePago {

        /**
     * Procesa el cobro de un monto específico.
     * 
     * @param monto La cantidad total a cobrar.
     * @return true si el pago fue aprobado, false si fue rechazado.
     */

    boolean procesarPago(double monto);
}