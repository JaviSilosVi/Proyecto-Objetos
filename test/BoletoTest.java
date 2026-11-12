import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BoletoTest {

    // METODO CRITICO 4: Boleto.calcularTotal()

    @Test
    public void testCalcularTotal_CaminoFeliz() throws AsientoOcupadoException {
        Pelicula p = new Pelicula("Toy Story", "1:30", "A", "Animacion", "Director", new ArrayList<>(), "Sinopsis");
        Funcion f = new Funcion("2026-11-20", 15, 0, p);

        Asiento a1 = new Asiento("A1", "TRADICIONAL", 80.0);
        Asiento a2 = new Asiento("A2", "TRADICIONAL", 80.0);
        a1.vender();
        a2.vender();

        List<Asiento> lista = new ArrayList<>();
        lista.add(a1);
        lista.add(a2);

        Boleto boleto = new Boleto(p, "Sala 1", f, lista, new Paypal("test@cine.com"));
        double total = boleto.calcularTotal();

        assertEquals(160.0, total, 0.01);
    }

    @Test
    public void testCalcularTotal_CaminoTriste() {
        Pelicula p = new Pelicula("Toy Story", "1:30", "A", "Animacion", "Director", new ArrayList<>(), "Sinopsis");
        Funcion f = new Funcion("2026-11-20", 15, 0, p);

        // Sin asientos asignados -> el constructor o calcularTotal() debe lanzar IllegalStateException
        assertThrows(IllegalStateException.class, () -> {
            new Boleto(p, "Sala 1", f, new ArrayList<>(), new Paypal("test@cine.com"));
        });
    }
}
