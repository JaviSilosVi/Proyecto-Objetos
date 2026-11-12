import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CineTest {

    // METODO CRITICO 7: Cine.obtenerSala()

    @Test
    public void testObtenerSala_CaminoFeliz() throws EntidadNoEncontradaException {
        Cine cine = new Cine("Cinepolis Test");
        Sala sala = new Sala("Sala 1", 5, 5, 80.0);
        cine.agregaSala("Sala 1", sala);

        Sala encontrada = cine.obtenerSala("Sala 1");
        assertNotNull(encontrada);
        assertEquals("Sala 1", encontrada.getIdSala());
    }

    @Test
    public void testObtenerSala_CaminoTriste() {
        Cine cine = new Cine("Cinepolis Test");

        // Sala que no existe -> EntidadNoEncontradaException
        assertThrows(EntidadNoEncontradaException.class, () -> {
            cine.obtenerSala("Sala 99");
        });
    }
}
