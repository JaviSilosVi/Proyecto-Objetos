import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class FuncionTest {
    
    @Test public void CorrectoFuncionamientoConstructor()
    {

        Pelicula peliculaPrueba = new Pelicula("Kong", "167 min", "B", "Ciencia Ficción", "Michael Dougherty", null, "peleas");

        Funcion funcion = new Funcion("2026-20-05", 14, 30, peliculaPrueba);

        assertEquals("2026-20-05", funcion.getFecha());
        assertEquals(14, funcion.getHorarioHrs());
        assertEquals(30, funcion.getHorarioMin());

        assertNotNull(funcion.getPeliculaProyecta());
        assertEquals("Kong", funcion.getPeliculaProyecta().getTitulo());

    }

    @Test
    public void testEqualsFuncionesIguales() {
        Pelicula peli = new Pelicula("Kong", "167 min", "B", "Sci-Fi", "Director", new ArrayList<>(), "Sinopsis");
        
        // Creamos dos objetos distintos en memoria, pero con los mismos atributos de fecha y hora
        Funcion f1 = new Funcion("2026-05-25", 14, 30, peli);
        Funcion f2 = new Funcion("2026-05-25", 14, 30, peli);

        // Debe devolver true porque el equals compara contenido (fecha y hora), no referencia en memoria
        assertEquals(f1, f2);
    }

    @Test
    public void testEqualsFuncionesDiferentes() {
        Pelicula peli = new Pelicula("Kong", "167 min", "B", "Sci-Fi", "Director", new ArrayList<>(), "Sinopsis");
        
        Funcion f1 = new Funcion("2026-05-25", 14, 30, peli);
        Funcion f2 = new Funcion("2026-05-26", 18, 00, peli); // Diferente fecha y hora

        assertNotEquals(f1, f2);
    }
}
