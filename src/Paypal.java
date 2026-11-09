public class Paypal implements MetodoDePago {
    private String correo;

    public Paypal(String correo) {
        this.correo = correo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public boolean procesarPago(double monto) {
        if (monto > 0) {
            if (this.correo != null) {
                if (this.correo.contains("@")) {
                    System.out.println("Pago de $" + monto + " aprobado con la cuenta Paypal: " + this.correo);
                    return true;
                } else {
                    System.out.println("Correo de Paypal invalido.");
                    return false;
                }
            } else {
                System.out.println("no se especifico el correo");
                return false;
            }
        } else {
            System.out.println("El monto a pagar debe ser mayor a 0 pesos ");
            return false;
        }
    }
}
