import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class BoletoTest {

    /**
     * Verifica que el cálculo total del boleto sume correctamente 
     * los precios individuales de asientos normales y VIP.
     */
    @Test
    public void testCalcularTotalBoleto() {
        
        Pelicula peliPrueba = new Pelicula("Inception", "160 min", "B", "Sci-Fi", "Nolan", new ArrayList<>(), "Viaje en sueños");
        Funcion funcionPrueba = new Funcion("2026-05-25", 20, 0, peliPrueba);
        
        ArrayList<Asiento> asientosComprados = new ArrayList<>();
        asientosComprados.add(new Asiento("A1", "NORMAL", 50.0));
        asientosComprados.add(new Asiento("A2", "VIP", 150.0));

        
        Boleto boleto = new Boleto(peliPrueba, "Sala 1", funcionPrueba, asientosComprados);

        assertEquals(200.0, boleto.getTotalPagado());
    }
}