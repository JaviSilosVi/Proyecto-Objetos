import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Asiento.
 * Metodo critico: vender() - throws AsientoOcupadoException
 *
 * Cubre:
 *  - Camino feliz (Happy Path): vender asiento disponible exitosamente.
 *  - Camino triste (Unhappy Path): intentar vender un asiento ya vendido -> AsientoOcupadoException.
 */
public class AsientoTest {

    // =========================================================
    // METODO 1: vender()
    // =========================================================

    /** CAMINO FELIZ: Un asiento disponible se puede vender sin error. */
    @Test
    public void testVenderAsientoDisponible_CaminoFeliz() throws AsientoOcupadoException {
        // Given (preparacion)
        Asiento asiento = new Asiento("A1", "TRADICIONAL", 80.0);

        // When (accion)
        boolean resultado = asiento.vender();

        // Then (verificacion)
        assertTrue(resultado, "La venta debe retornar true");
        assertEquals("VENDIDO", asiento.getEstadoAsiento(), "El estado debe cambiar a VENDIDO");
    }

    /** CAMINO TRISTE: Vender un asiento que ya fue vendido -> AsientoOcupadoException. */
    @Test
    public void testVenderAsientoYaVendido_CaminoTriste() throws AsientoOcupadoException {
        // Given
        Asiento asiento = new Asiento("B2", "VIP", 150.0);
        asiento.vender(); // Primera venta exitosa

        // When & Then: La segunda venta debe lanzar AsientoOcupadoException
        assertThrows(AsientoOcupadoException.class, () -> {
            asiento.vender();
        }, "Debe lanzar AsientoOcupadoException al intentar vender un asiento ya vendido");
    }

    // =========================================================
    // METODO adicional: reservar() - sin excepcion, camino feliz
    // =========================================================

    /** CAMINO FELIZ: Reservar un asiento disponible. */
    @Test
    public void testReservarAsientoDisponible_CaminoFeliz() {
        // Given
        Asiento asiento = new Asiento("C3", "TRADICIONAL", 80.0);

        // When
        boolean resultado = asiento.reservar();

        // Then
        assertTrue(resultado, "La reserva debe retornar true");
        assertEquals("RESERVADO", asiento.getEstadoAsiento(), "El estado debe ser RESERVADO");
    }

    /** CAMINO TRISTE: Reservar un asiento ya reservado devuelve false (sin excepcion). */
    @Test
    public void testReservarAsientoYaReservado_CaminoTriste() {
        // Given
        Asiento asiento = new Asiento("D4", "TRADICIONAL", 80.0);
        asiento.reservar(); // Primera reserva

        // When
        boolean segundaReserva = asiento.reservar();

        // Then
        assertFalse(segundaReserva, "La segunda reserva debe retornar false");
    }

    /** CAMINO TRISTE: Vender un asiento RESERVADO debe lanzar AsientoOcupadoException. */
    @Test
    public void testVenderAsientoReservado_CaminoTriste() {
        // Given
        Asiento asiento = new Asiento("E5", "VIP", 150.0);
        asiento.reservar();

        // When & Then
        assertThrows(AsientoOcupadoException.class, () -> {
            asiento.vender();
        }, "No se debe poder vender un asiento RESERVADO");
    }
}
