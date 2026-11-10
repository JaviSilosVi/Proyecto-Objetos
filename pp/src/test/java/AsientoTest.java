import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class AsientoTest {

    @Test
    public void testConstructorYEstadoInicial() {
        
        Asiento asiento = new Asiento("F12", "VIP", 85.50);

        assertEquals("DISPONIBLE", asiento.getEstadoAsiento());
        assertEquals("F12", asiento.getIdentificador());
    }

     @Test
    public void testReservarAsiento() {
        Asiento asiento = new Asiento("F12", "VIP", 85.50);
        asiento.reservar();
        assertEquals("RESERVADO", asiento.getEstadoAsiento());
    }

    @Test public void testVentaDeAsiento() throws AsientoOcupadoException { 
        Asiento asiento = new Asiento("F12", "VIP", 85.50);

        boolean ventaExitosa = asiento.vender();
        assertTrue(ventaExitosa);
        assertEquals("VENDIDO", asiento.getEstadoAsiento());
    }

     @Test public void testVentaDeAsientoYRestriccion() throws AsientoOcupadoException { 
        Asiento asiento = new Asiento("F12", "VIP", 85.50);
        
        boolean ventaExitosa = asiento.vender();
        assertTrue(ventaExitosa);
        assertEquals("VENDIDO", asiento.getEstadoAsiento());
        
        assertThrows(AsientoOcupadoException.class, ()->{asiento.vender();});
    }

    @Test
    public void testReservarAsientoYRestriccionDeVenta() {
        Asiento asiento = new Asiento("A1", "Normal", 50.0);

        boolean reservaExitosa = asiento.reservar();
        assertTrue(reservaExitosa);
        assertEquals("RESERVADO", asiento.getEstadoAsiento());

        //Vamos a valorar que un asiento que ya esta reservado, no puede volver a resrrvar
        boolean segundaReserva = asiento.reservar();
        assertFalse(segundaReserva);

        // Y aqui validamos que no puede haber venta de asientos reservados
        assertThrows(AsientoOcupadoException.class, ()-> { asiento.vender();});
    }
}