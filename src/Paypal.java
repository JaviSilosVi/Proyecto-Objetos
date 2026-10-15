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
        return true;
    }
}
