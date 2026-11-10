import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CineTest {

    @Test public void testAgregarYObtenerSala() {
         Cine cine = new Cine("Cine Centro"); 
         Sala salaVIP = new Sala("Sala 1");

         cine.agregarSala("Sala 1", salaVIP);
        
        Sala salaEncontrada = cine.obtenerSala("Sala 1");
        assertNotNull(salaEncontrada);
        assertEquals("Cine Centro", cine.getNombreCine());
    }

    @Test
    public void testEliminarSala() {
        Cine cine = new Cine("Cine Centro");
        Sala sala = new Sala("Sala 1");
        cine.agregarSala("Sala 1", sala);

        boolean eliminada = cine.eliminarSala("Sala 1");

        assertTrue(eliminada);
        assertNull(cine.obtenerSala("Sala 1"));
    }

    @Test
    public void testModificarSala() {
        Cine cine = new Cine("Cine Centro");
        Sala salaAntigua = new Sala("Sala 1");
        cine.agregarSala("Sala 1", salaAntigua);

        Sala salaNueva = new Sala("Sala VIP");
        
        boolean modificada = cine.modificarSala("Sala 1", "Sala VIP", salaNueva);
        assertTrue(modificada);
        
        assertNull(cine.obtenerSala("Sala 1"));
        assertNotNull(cine.obtenerSala("Sala VIP"));
    }
}
