import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para Boleto.
 * Metodo critico: calcularTotal() - throws IllegalStateException
 */
public class BoletoTest {

    private Pelicula crearPelicula() {
        return new Pelicula("Test Movie", "2:00", "A", "Accion",
                "Director", new ArrayList<>(), "Sinopsis");
    }

    // =========================================================
    // METODO CRITICO 7: calcularTotal()
    // =========================================================

    /** CAMINO FELIZ: Boleto con asientos validos calcula el total correctamente. */
    @Test
    public void testCalcularTotal_ConAsientos_CaminoFeliz() throws AsientoOcupadoException {
        // Given
        Asiento a1 = new Asiento("A1", "TRADICIONAL", 80.0);
        Asiento a2 = new Asiento("A2", "TRADICIONAL", 80.0);
        a1.vender();
        a2.vender();

        List<Asiento> asientos = new ArrayList<>();
        asientos.add(a1);
        asientos.add(a2);

        Funcion funcion = new Funcion("2026-12-01", 18, 0, crearPelicula());
        Boleto boleto = new Boleto(crearPelicula(), "Sala 1", funcion, asientos, new Paypal("test@test.com", "token123"));

        // When
        double total = boleto.calcularTotal();

        // Then
        assertEquals(160.0, total, 0.01, "El total debe ser la suma de los precios de los asientos (80+80=160)");
    }

    /** CAMINO TRISTE: Boleto sin asientos -> IllegalStateException. */
    @Test
    public void testCalcularTotal_SinAsientos_CaminoTriste() {
        // Given: boleto con lista de asientos vacia
        List<Asiento> asientosVacios = new ArrayList<>();
        Funcion funcion = new Funcion("2026-12-01", 18, 0, crearPelicula());
        Boleto boleto = new Boleto(crearPelicula(), "Sala 1", funcion, asientosVacios, new Paypal("test@test.com", "token123"));

        // When & Then
        assertThrows(IllegalStateException.class, () -> {
            boleto.calcularTotal();
        }, "Debe lanzar IllegalStateException si no hay asientos asignados");
    }

    /** CAMINO TRISTE: Boleto con lista null -> IllegalStateException. */
    @Test
    public void testCalcularTotal_AsientosNull_CaminoTriste() {
        // Given
        Funcion funcion = new Funcion("2026-12-01", 18, 0, crearPelicula());
        Boleto boleto = new Boleto(crearPelicula(), "Sala 1", funcion, null, new Paypal("test@test.com", "token123"));

        // When & Then
        assertThrows(IllegalStateException.class, () -> {
            boleto.calcularTotal();
        }, "Debe lanzar IllegalStateException si los asientos son null");
    }
}
