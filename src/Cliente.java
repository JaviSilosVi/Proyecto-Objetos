import java.util.List;

public class Cliente extends Usuario {

    public Cliente(String nombre) {
        super(nombre);
    }

    /**
     * Mantiene compatibilidad con el UML original delegando la venta a GestorVentas.
     */
    public Boleto comprarBoleto(Funcion funcion, String idSala, List<Asiento> seleccion, MetodoDePago metodoPago)
            throws AsientoOcupadoException {
        GestorVentas gestor = new GestorVentas();
        return gestor.realizarVenta(this, funcion, idSala, seleccion, metodoPago);
    }

    /**
     * Permite comprar usando directamente el GestorVentas del sistema para registrar el historial.
     */
    public Boleto comprarBoleto(Funcion funcion, String idSala, List<Asiento> seleccion,
                                MetodoDePago metodoPago, GestorVentas gestorVentas)
            throws AsientoOcupadoException {
        return gestorVentas.realizarVenta(this, funcion, idSala, seleccion, metodoPago);
    }
}
