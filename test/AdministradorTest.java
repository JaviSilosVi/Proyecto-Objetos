import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para Administrador.
 * Metodo critico: validarPin(String) - throws IllegalArgumentException
 * Metodo critico: calcularTotal() en Boleto - throws IllegalStateException
 */
public class AdministradorTest {

    // =========================================================
    // METODO CRITICO 8: validarPin()
    // =========================================================

    /** CAMINO FELIZ: PIN correcto retorna true. */
    @Test
    public void testValidarPin_Correcto_CaminoFeliz() {
        // Given
        Administrador admin = new Administrador("Carlos", "1234");

        // When
        boolean resultado = admin.validarPin("1234");

        // Then
        assertTrue(resultado, "El PIN correcto debe retornar true");
    }

    /** CAMINO TRISTE: PIN incorrecto retorna false (sin excepcion). */
    @Test
    public void testValidarPin_Incorrecto_CaminoTriste() {
        // Given
        Administrador admin = new Administrador("Carlos", "1234");

        // When
        boolean resultado = admin.validarPin("9999");

        // Then
        assertFalse(resultado, "El PIN incorrecto debe retornar false");
    }

    /** CAMINO TRISTE: PIN null -> IllegalArgumentException. */
    @Test
    public void testValidarPin_Null_CaminoTriste() {
        // Given
        Administrador admin = new Administrador("Carlos", "1234");

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            admin.validarPin(null);
        }, "Debe lanzar IllegalArgumentException si el PIN es null");
    }

    /** CAMINO TRISTE: PIN vacio -> IllegalArgumentException. */
    @Test
    public void testValidarPin_Vacio_CaminoTriste() {
        // Given
        Administrador admin = new Administrador("Carlos", "1234");

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            admin.validarPin("");
        }, "Debe lanzar IllegalArgumentException si el PIN esta vacio");
    }
}
