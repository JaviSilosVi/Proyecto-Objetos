import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdministradorTest {

    // METODO CRITICO 8: Administrador.validarPin()

    @Test
    public void testValidarPin_CaminoFeliz() {
        Administrador admin = new Administrador("Carlos", "1234");
        assertTrue(admin.validarPin("1234"));
        assertFalse(admin.validarPin("9999"));
    }

    @Test
    public void testValidarPin_CaminoTriste() {
        Administrador admin = new Administrador("Carlos", "1234");

        // PIN nulo -> IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            admin.validarPin(null);
        });
    }
}
