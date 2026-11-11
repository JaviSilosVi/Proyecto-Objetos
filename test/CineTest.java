import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Cine.
 * Metodo critico: obtenerSala(String) - throws EntidadNoEncontradaException
 *
 * Cubre camino feliz y camino triste.
 */
public class CineTest {

    private Cine cine;

    @BeforeEach
    public void setUp() {
        cine = new Cine("Cinepolis Test");
        Sala sala = new Sala("Sala 1", 5, 10, 80.0);
        cine.agregaSala("Sala 1", sala);
    }

    // =========================================================
    // METODO CRITICO 5: obtenerSala()
    // =========================================================

    /** CAMINO FELIZ: Obtener una sala que SÍ existe. */
    @Test
    public void testObtenerSala_Existente_CaminoFeliz() throws EntidadNoEncontradaException {
        // When
        Sala sala = cine.obtenerSala("Sala 1");

        // Then
        assertNotNull(sala, "La sala debe ser encontrada");
        assertEquals("Sala 1", sala.getIdSala(), "El ID de sala debe coincidir");
    }

    /** CAMINO TRISTE: Obtener sala que NO existe -> EntidadNoEncontradaException. */
    @Test
    public void testObtenerSala_NoExistente_CaminoTriste() {
        // When & Then
        assertThrows(EntidadNoEncontradaException.class, () -> {
            cine.obtenerSala("Sala 99");
        }, "Debe lanzar EntidadNoEncontradaException si la sala no existe");
    }

    /** CAMINO FELIZ: Agregar sala y verificar que queda registrada. */
    @Test
    public void testAgregarSala_CaminoFeliz() {
        // Given
        Sala nuevaSala = new Sala("Sala 2", 8, 12, 100.0);

        // When
        cine.agregaSala("Sala 2", nuevaSala);

        // Then
        assertEquals(2, cine.getSalas().size(), "El cine debe tener 2 salas");
    }

    /** CAMINO TRISTE: Eliminar sala inexistente -> EntidadNoEncontradaException. */
    @Test
    public void testEliminarSala_NoExistente_CaminoTriste() {
        assertThrows(EntidadNoEncontradaException.class, () -> {
            cine.eliminaSala("SalaFantasma");
        }, "Debe lanzar EntidadNoEncontradaException al eliminar sala inexistente");
    }
}
