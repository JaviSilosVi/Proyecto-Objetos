import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class SalaTest {

    // METODO CRITICO 2: Sala.agregarFuncion()

    // 1. Camino Feliz: Agregar funcion en horario libre
    @Test
    public void testAgregarFuncion_CaminoFeliz() throws ConflictoHorarioException {
        Sala sala = new Sala("Sala 1", 5, 5, 80.0);
        Pelicula peli = new Pelicula("Avatar", "2:40", "B", "Accion", "James Cameron", new ArrayList<>(), "Pandora");
        Funcion f1 = new Funcion("2026-12-01", 18, 0, peli);

        boolean agregada = sala.agregarFuncion(f1);
        assertTrue(agregada);
        assertEquals(1, sala.getProgramacion().size());
    }

    // 2. Camino Triste: Agregar dos funciones con el mismo horario lanza ConflictoHorarioException
    @Test
    public void testAgregarFuncion_CaminoTriste() throws ConflictoHorarioException {
        Sala sala = new Sala("Sala 1", 5, 5, 80.0);
        Pelicula peli = new Pelicula("Avatar", "2:40", "B", "Accion", "James Cameron", new ArrayList<>(), "Pandora");
        Funcion f1 = new Funcion("2026-12-01", 18, 0, peli);
        Funcion f2 = new Funcion("2026-12-01", 18, 0, peli); // Mismo horario

        sala.agregarFuncion(f1);

        assertThrows(ConflictoHorarioException.class, () -> {
            sala.agregarFuncion(f2);
        });
    }
}
