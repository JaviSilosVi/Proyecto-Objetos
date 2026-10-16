import java.util.Iterator;
import java.util.List;

public class Cliente extends Usuario {

    public Cliente(String nombre) {
        super(nombre);
    }

    public Boleto comprarBoleto(Funcion funcion, String idSala, List<Asiento> seleccion, MetodoDePago metodoPago) {
        if (funcion == null || seleccion == null || seleccion.isEmpty() || metodoPago == null) {
            System.out.println("Datos de compra incompletos.");
            return null;
        }

        // Calcular total recorriendo con iterador
        double total = 0.0;
        Iterator<Asiento> itPrecio = seleccion.iterator();
        while (itPrecio.hasNext()) {
            Asiento a = itPrecio.next();
            total = total + a.getPrecio();
        }

        boolean pagoAprobado = metodoPago.procesarPago(total);
        if (!pagoAprobado) {
            System.out.println("El pago no pudo ser procesado.");
            return null;
        }

        // marcar asientos como vendidos
        Iterator<Asiento> itVender = seleccion.iterator();
        while (itVender.hasNext()) {
            Asiento a = itVender.next();
            a.vender();
        }

        // Crear y retona el boleto
        Boleto nuevoBoleto = new Boleto(funcion.getPeliculaProyectada(), idSala, funcion, seleccion, metodoPago);
        nuevoBoleto.setTotalPagado(total);
        System.out.println("Compra exitosa para el cliente " + this.nombre);
        return nuevoBoleto;
    }
}
