import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TarjetaCreditoTest {

    // METODO CRITICO 5: TarjetaCredito.procesarPago()

    @Test
    public void testProcesarPago_CaminoFeliz() {
        TarjetaCredito tc = new TarjetaCredito("1234567890123456");
        boolean resultado = tc.procesarPago(150.0);
        assertTrue(resultado);
    }

    @Test
    public void testProcesarPago_CaminoTriste() {
        TarjetaCredito tc = new TarjetaCredito("1234567890123456");

        // Monto negativo -> IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            tc.procesarPago(-50.0);
        });
    }
}
