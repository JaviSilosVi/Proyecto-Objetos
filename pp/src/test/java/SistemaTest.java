import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SistemaTest {

    @Test public void testAgregarYObtenerCine() { 
        Sistema sistema = new Sistema(); 
        Cine cine = new Cine("Cinepolis Plaza");

        sistema.agregarCine(cine);

        Cine cineEncontrado = sistema.obtenerCine("Cinepolis Plaza");

        assertNotNull(cineEncontrado);
        assertEquals("Cinepolis Plaza", cineEncontrado.getNombreCine());
    
    }

    @Test
    public void testEliminarCine() {
        Sistema sistema = new Sistema();
        Cine cine = new Cine("Cine Centro");
        sistema.agregarCine(cine);

        boolean eliminado = sistema.eliminarCine("Cine Centro");
        assertTrue(eliminado);
        
        assertNull(sistema.obtenerCine("Cine Centro"));
    }

    @Test
    public void testModificarCine() {
        Sistema sistema = new Sistema();
        Cine cineAntiguo = new Cine("Cine Viejo");
        sistema.agregarCine(cineAntiguo);

        Cine cineNuevo = new Cine("Cine Nuevo");
        
        //Validamos la modificacion correctaa
        boolean modificado = sistema.modificarCine("Cine Viejo", cineNuevo);
        assertTrue(modificado);
        
        assertNull(sistema.obtenerCine("Cine Viejo"));
        assertNotNull(sistema.obtenerCine("Cine Nuevo"));
    }

    @Test 
    public void testProgramarFuncion() throws ConflictoHorarioException {
        Sistema sistema = new Sistema();
        Cine cine = new Cine("Cine el Dorado");
        Sala sala = new Sala("Sala 1");

        cine.agregarSala("Sala 1", sala);
        sistema.agregarCine(cine);

        Funcion funcion = new Funcion("2026-20-05", 14, 30, null);
        
        assertTrue(sistema.programarFuncion("Cine el Dorado", "Sala 1", funcion));
    }


}
