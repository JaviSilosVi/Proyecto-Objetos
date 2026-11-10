import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaypalTest {

    @Test
    public void testProcesarPagoPaypal() {
        Paypal pagoPaypal = new Paypal("cliente@correo.com");
        
        boolean resultado = pagoPaypal.procesarPago(150.50);
        
        assertTrue(resultado, "El cobro mediante PayPal debió ser exitoso");
    }
}