import java.util.ArrayList;
import java.util.List;

/**
 * Servicio con Responsabilidad Única (SRP):
 * Gestiona la venta de boletos, el historial de boletos vendidos y el cálculo de ingresos.
 */
public class GestorVentas {
    private List<Boleto> boletosVendidos;

    public GestorVentas() {
        this.boletosVendidos = new ArrayList<>();
    }

    public Boleto realizarVenta(Cliente cliente, Funcion funcion, String idSala,
                                List<Asiento> seleccion, MetodoDePago metodoPago)
                                throws AsientoOcupadoException {
        if (funcion == null || seleccion == null || seleccion.isEmpty() || metodoPago == null) {
            System.out.println("Datos de compra incompletos.");
            return null;
        }

        // 1. Validar que los asientos esten disponibles antes de cobrar
        double total = 0.0;
        for (int i = 0; i < seleccion.size(); i++) {
            Asiento a = seleccion.get(i);
            if (!a.getEstadoAsiento().equalsIgnoreCase("DISPONIBLE")) {
                throw new AsientoOcupadoException("El asiento " + a.getIdentificador() + " ya no está disponible.");
            }
            total = total + a.getPrecio();
        }

        // 2. Procesar pago con la interfaz MetodoDePago (OCP / DIP)
        boolean pagoAprobado = metodoPago.procesarPago(total);
        if (!pagoAprobado) {
            System.out.println("El pago no pudo ser procesado.");
            return null;
        }

        // 3. Marcar asientos como vendidos
        for (int i = 0; i < seleccion.size(); i++) {
            seleccion.get(i).vender();
        }

        // 4. Crear boleto y guardarlo en el historial
        Boleto nuevoBoleto = new Boleto(funcion.getPeliculaProyectada(), idSala, funcion, seleccion, metodoPago);
        nuevoBoleto.setTotalPagado(total);
        registrarBoleto(nuevoBoleto);

        if (cliente != null) {
            System.out.println("Compra exitosa para el cliente " + cliente.getNombre());
        }
        return nuevoBoleto;
    }

    public void registrarBoleto(Boleto boleto) {
        if (boleto != null) {
            this.boletosVendidos.add(boleto);
        }
    }

    public double calcularIngresosTotales() {
        double total = 0.0;
        for (int i = 0; i < this.boletosVendidos.size(); i++) {
            total = total + this.boletosVendidos.get(i).getTotalPagado();
        }
        return total;
    }

    public List<Boleto> getBoletosVendidos() {
        return this.boletosVendidos;
    }

    public void setBoletosVendidos(List<Boleto> boletosVendidos) {
        this.boletosVendidos = boletosVendidos;
    }
}
