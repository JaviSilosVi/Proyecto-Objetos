import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AsientoTest {

    // METODO CRITICO 1: Asiento.vender()

    // 1. Camino Feliz: Asiento disponible se vende exitosamente
    @Test
    public void testVender_CaminoFeliz() throws AsientoOcupadoException {
        Asiento asiento = new Asiento("A1", "TRADICIONAL", 80.0);
        boolean resultado = asiento.vender();
        assertTrue(resultado);
        assertEquals("VENDIDO", asiento.getEstadoAsiento());
    }

    // 2. Camino Triste: Intentar vender un asiento ya vendido lanza AsientoOcupadoException
    @Test
    public void testVender_CaminoTriste() throws AsientoOcupadoException {
        Asiento asiento = new Asiento("A1", "TRADICIONAL", 80.0);
        asiento.vender(); // Primera venta OK

        assertThrows(AsientoOcupadoException.class, () -> {
            asiento.vender(); // Segunda venta debe fallar
        });
    }
}
