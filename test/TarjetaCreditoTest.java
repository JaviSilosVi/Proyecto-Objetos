import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para TarjetaCredito.
 * Metodo critico: procesarPago(double) - throws IllegalArgumentException
 */
public class TarjetaCreditoTest {

    // =========================================================
    // METODO CRITICO 6: procesarPago()
    // =========================================================

    /** CAMINO FELIZ: Procesar pago con monto valido y tarjeta valida. */
    @Test
    public void testProcesarPago_MontoValido_CaminoFeliz() {
        // Given
        TarjetaCredito tarjeta = new TarjetaCredito("1234567890123456");

        // When
        boolean resultado = tarjeta.procesarPago(150.0);

        // Then
        assertTrue(resultado, "El pago debe ser aprobado con datos validos");
    }

    /** CAMINO TRISTE: Monto negativo -> IllegalArgumentException. */
    @Test
    public void testProcesarPago_MontoNegativo_CaminoTriste() {
        // Given
        TarjetaCredito tarjeta = new TarjetaCredito("1234567890123456");

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            tarjeta.procesarPago(-50.0);
        }, "Debe lanzar IllegalArgumentException con monto negativo");
    }

    /** CAMINO TRISTE: Monto cero -> IllegalArgumentException. */
    @Test
    public void testProcesarPago_MontoCero_CaminoTriste() {
        TarjetaCredito tarjeta = new TarjetaCredito("1234567890123456");

        assertThrows(IllegalArgumentException.class, () -> {
            tarjeta.procesarPago(0.0);
        }, "Debe lanzar IllegalArgumentException con monto cero");
    }

    /** CAMINO TRISTE: Numero de tarjeta invalido (menos de 10 digitos) -> IllegalArgumentException. */
    @Test
    public void testProcesarPago_TarjetaInvalida_CaminoTriste() {
        // Given: tarjeta con numero demasiado corto
        TarjetaCredito tarjeta = new TarjetaCredito("123");

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            tarjeta.procesarPago(100.0);
        }, "Debe lanzar IllegalArgumentException con numero de tarjeta invalido");
    }
}
