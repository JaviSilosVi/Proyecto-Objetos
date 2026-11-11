import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Sala.
 * Metodo critico: agregarFuncion(Funcion) - throws ConflictoHorarioException
 *
 * Cubre:
 *  - Camino feliz: agregar funcion en horario libre -> exito.
 *  - Camino triste: agregar funcion con mismo horario -> ConflictoHorarioException.
 *  - Camino triste: agregar funcion null -> IllegalArgumentException.
 */
public class SalaTest {

    // Metodo helper para crear una pelicula de prueba
    private Pelicula crearPeliculaPrueba() {
        return new Pelicula("Pelicula Test", "2:00", "A", "Accion", "Director Test",
                new java.util.ArrayList<>(), "Sinopsis test");
    }

    // =========================================================
    // METODO CRITICO: agregarFuncion()
    // =========================================================

    /** CAMINO FELIZ: Agregar una funcion en un horario libre. */
    @Test
    public void testAgregarFuncion_HorarioLibre_CaminoFeliz() throws ConflictoHorarioException {
        // Given
        Sala sala = new Sala("Sala 1", 5, 10, 80.0);
        Funcion funcion = new Funcion("2026-12-01", 18, 0, crearPeliculaPrueba());

        // When
        boolean resultado = sala.agregarFuncion(funcion);

        // Then
        assertTrue(resultado, "La funcion debe agregarse exitosamente");
        assertEquals(1, sala.getProgramacion().size(), "La sala debe tener 1 funcion programada");
    }

    /** CAMINO FELIZ: Agregar dos funciones en horarios DISTINTOS. */
    @Test
    public void testAgregarDos_FuncionesHorariosDiferentes_CaminoFeliz() throws ConflictoHorarioException {
        // Given
        Sala sala = new Sala("Sala 2", 5, 10, 80.0);
        Funcion f1 = new Funcion("2026-12-01", 15, 0, crearPeliculaPrueba());
        Funcion f2 = new Funcion("2026-12-01", 18, 0, crearPeliculaPrueba());

        // When
        sala.agregarFuncion(f1);
        sala.agregarFuncion(f2);

        // Then
        assertEquals(2, sala.getProgramacion().size(), "Deben haber 2 funciones programadas en horarios distintos");
    }

    /** CAMINO TRISTE: Agregar dos funciones con el MISMO horario -> ConflictoHorarioException. */
    @Test
    public void testAgregarFuncion_ConflictoHorario_CaminoTriste() throws ConflictoHorarioException {
        // Given
        Sala sala = new Sala("Sala 3", 5, 10, 80.0);
        Funcion f1 = new Funcion("2026-12-01", 18, 0, crearPeliculaPrueba());
        Funcion f2 = new Funcion("2026-12-01", 18, 0, crearPeliculaPrueba()); // Mismo horario!
        sala.agregarFuncion(f1); // Primera funcion OK

        // When & Then
        assertThrows(ConflictoHorarioException.class, () -> {
            sala.agregarFuncion(f2); // Segunda funcion con mismo horario -> excepcion
        }, "Debe lanzar ConflictoHorarioException al agregar funcion en horario ocupado");
    }

    /** CAMINO TRISTE: Agregar funcion null -> IllegalArgumentException. */
    @Test
    public void testAgregarFuncionNull_CaminoTriste() {
        // Given
        Sala sala = new Sala("Sala 4", 5, 10, 80.0);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            sala.agregarFuncion(null);
        }, "Debe lanzar IllegalArgumentException al agregar una funcion nula");
    }
}
