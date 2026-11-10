import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TarjetaCreditoTest {

    @Test
    public void testProcesarPagoTarjeta() {
        TarjetaCredito tarjeta = new TarjetaCredito("4152313498765432");
        
        boolean resultado = tarjeta.procesarPago(200.00);
        
        assertTrue(resultado, "El pago con tarjeta de crédito fue exitoso");
    }
}