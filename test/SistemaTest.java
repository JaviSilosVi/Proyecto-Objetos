import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class SistemaTest {

    // METODO CRITICO 3: Sistema.obtenerPelicula()

    @Test
    public void testObtenerPelicula_CaminoFeliz() throws EntidadNoEncontradaException {
        Sistema sistema = new Sistema();
        Pelicula p = new Pelicula("Batman", "2:10", "B", "Accion", "Matt Reeves", new ArrayList<>(), "Gotham");
        sistema.agregarPelicula(p);

        Pelicula resultado = sistema.obtenerPelicula("Batman");
        assertNotNull(resultado);
        assertEquals("Batman", resultado.getTitulo());
    }

    @Test
    public void testObtenerPelicula_CaminoTriste() {
        Sistema sistema = new Sistema();

        assertThrows(EntidadNoEncontradaException.class, () -> {
            sistema.obtenerPelicula("PeliculaFantasma");
        });
    }

    // METODO CRITICO 6: Sistema.eliminarCine()

    @Test
    public void testEliminarCine_CaminoFeliz() throws EntidadNoEncontradaException {
        Sistema sistema = new Sistema();
        sistema.agregaCine(new Cine("Cinepolis Tangamanga"));

        boolean eliminado = sistema.eliminarCine("Cinepolis Tangamanga");
        assertTrue(eliminado);
        assertEquals(0, sistema.getCines().size());
    }

    @Test
    public void testEliminarCine_CaminoTriste() {
        Sistema sistema = new Sistema();

        assertThrows(EntidadNoEncontradaException.class, () -> {
            sistema.eliminarCine("CineQueNoExiste");
        });
    }
}
