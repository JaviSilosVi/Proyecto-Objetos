import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Sistema.
 * Metodos criticos:
 *   - obtenerPelicula(String) - throws EntidadNoEncontradaException
 *   - eliminarCine(String)    - throws EntidadNoEncontradaException
 *
 * Cubre camino feliz y camino triste para cada metodo.
 */
public class SistemaTest {

    private Sistema sistema;

    /** Preparacion: se ejecuta antes de CADA prueba. */
    @BeforeEach
    public void setUp() {
        sistema = new Sistema();

        // Registrar datos de prueba
        Cine cine = new Cine("Cinepolis Centro");
        sistema.agregaCine(cine);

        Pelicula pelicula = new Pelicula("Avengers", "2:30", "B",
                "Accion", "Russo Brothers", new java.util.ArrayList<>(), "Superheroes");
        sistema.agregarPelicula(pelicula);
    }

    // =========================================================
    // METODO CRITICO 3: obtenerPelicula()
    // =========================================================

    /** CAMINO FELIZ: Buscar una pelicula que SÍ existe. */
    @Test
    public void testObtenerPelicula_Existente_CaminoFeliz() throws EntidadNoEncontradaException {
        // When
        Pelicula resultado = sistema.obtenerPelicula("Avengers");

        // Then
        assertNotNull(resultado, "La pelicula debe ser encontrada");
        assertEquals("Avengers", resultado.getTitulo(), "El titulo debe coincidir");
    }

    /** CAMINO TRISTE: Buscar pelicula que NO existe -> EntidadNoEncontradaException. */
    @Test
    public void testObtenerPelicula_NoExistente_CaminoTriste() {
        // When & Then
        assertThrows(EntidadNoEncontradaException.class, () -> {
            sistema.obtenerPelicula("PeliculaQueNoExiste");
        }, "Debe lanzar EntidadNoEncontradaException si la pelicula no existe");
    }

    /** CAMINO TRISTE: Buscar pelicula con titulo null -> IllegalArgumentException. */
    @Test
    public void testObtenerPelicula_TituloNull_CaminoTriste() {
        assertThrows(IllegalArgumentException.class, () -> {
            sistema.obtenerPelicula(null);
        }, "Debe lanzar IllegalArgumentException si el titulo es null");
    }

    // =========================================================
    // METODO CRITICO 4: eliminarCine()
    // =========================================================

    /** CAMINO FELIZ: Eliminar un cine que SÍ existe. */
    @Test
    public void testEliminarCine_Existente_CaminoFeliz() throws EntidadNoEncontradaException {
        // When
        boolean resultado = sistema.eliminarCine("Cinepolis Centro");

        // Then
        assertTrue(resultado, "La eliminacion debe retornar true");
        assertEquals(0, sistema.getCines().size(), "El sistema no debe tener cines despues de eliminar");
    }

    /** CAMINO TRISTE: Eliminar cine que NO existe -> EntidadNoEncontradaException. */
    @Test
    public void testEliminarCine_NoExistente_CaminoTriste() {
        // When & Then
        assertThrows(EntidadNoEncontradaException.class, () -> {
            sistema.eliminarCine("CineQueNoExiste");
        }, "Debe lanzar EntidadNoEncontradaException si el cine no existe");
    }

    // =========================================================
    // METODO adicional: obtenerCine()
    // =========================================================

    /** CAMINO FELIZ: Buscar un cine existente. */
    @Test
    public void testObtenerCine_Existente_CaminoFeliz() throws EntidadNoEncontradaException {
        // When
        Cine cine = sistema.obtenerCine("Cinepolis Centro");

        // Then
        assertNotNull(cine);
        assertEquals("Cinepolis Centro", cine.getNombreCine());
    }

    /** CAMINO TRISTE: Buscar cine inexistente -> EntidadNoEncontradaException. */
    @Test
    public void testObtenerCine_NoExistente_CaminoTriste() {
        assertThrows(EntidadNoEncontradaException.class, () -> {
            sistema.obtenerCine("CineFantasma");
        }, "Debe lanzar EntidadNoEncontradaException si el cine no existe");
    }
}
