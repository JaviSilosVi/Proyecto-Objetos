import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransferenciaElectronicaTest {

    @Test
    public void testProcesarPagoTransferencia() {
        TransferenciaElectronica transferencia = new TransferenciaElectronica(987654321);
        
        boolean resultado = transferencia.procesarPago(500.00);
        
        assertTrue(resultado, "La transferencia electrónica se proceso correctamente");
    }
}