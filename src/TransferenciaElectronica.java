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
        if (monto > 0) {
            if (this.cuentaBancaria != null) {
                if (this.cuentaBancaria.length() >= 8) {
                    System.out.println("Pago de $" + monto + " aprobado mediante transferencia a cuenta " + this.cuentaBancaria);
                    return true;
                } else {
                    System.out.println("Numero de cuenta bancaria invalido");
                    return false;
                }
            } else {
                System.out.println("no se ingreso la cuenta bancaria ");
                return false;
            }
        } else {
            System.out.println("El monto a pagar debe ser mayor a 0 pesos");
            return false;
        }
    }
}
