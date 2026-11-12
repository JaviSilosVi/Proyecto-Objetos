public class TarjetaCredito implements MetodoDePago {
    private String numeroTarjeta;

    public TarjetaCredito(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    @Override
    public boolean procesarPago(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a pagar debe ser mayor a 0 pesos. Monto recibido: " + monto);
        }
        if (this.numeroTarjeta == null || this.numeroTarjeta.trim().length() < 16) {
            throw new IllegalArgumentException("Número de tarjeta inválido para procesar la transacción.");
        }
        System.out.println("Pago de $" + monto + " aprobado con Tarjeta terminación " + this.numeroTarjeta.substring(this.numeroTarjeta.length() - 4));
        return true;
    }
}
