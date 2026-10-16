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
        if (monto > 0) {
            if (this.numeroTarjeta != null) {
                if (this.numeroTarjeta.length() >= 10) {
                    System.out.println("Pago de $" + monto + " aprobado con Tarjeta terminacion " + this.numeroTarjeta.substring(this.numeroTarjeta.length() - 4));
                    return true;
                } else {
                    System.out.println("numero de tarjeta invalido ");
                    return false;
                }
            } else {
                System.out.println("no se ingreso el numero de tarjeta");
                return false;
            }
        } else {
            System.out.println("El monto a pagar debe ser mayor a 0 pesos");
            return false;
        }
    }
}
