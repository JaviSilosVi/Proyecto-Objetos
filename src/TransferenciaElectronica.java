public class TransferenciaElectronica implements MetodoDePago {
    private String cuentaBancaria;

    public TransferenciaElectronica(String cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

    public String getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(String cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

    @Override
    public boolean procesarPago(double monto) {
        return true;
    }
}
