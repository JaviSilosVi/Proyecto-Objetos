import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class PeliculaTest {
    
    //Prueba 1: COmnstructor y getters
    @Test
    public void correctoFuncionamientoConstructor()
    {
        ArrayList<String> actores = new ArrayList<>();
        actores.add("Kyle Chandler");
        actores.add("Millie Bobby Brown");
        Pelicula pelicula = new Pelicula("Godzilla", "132 minutos", "B", "Ciencia ficcion", "Michael Dougherty", actores, "Pelea entre Kaijus");

        assertEquals("Godzilla",pelicula.getTitulo());
        assertEquals("132 minutos", pelicula.getDuracion());
        assertEquals("B", pelicula.getClasificacion());
        assertEquals("Ciencia ficcion", pelicula.getGenero());
        assertEquals("Michael Dougherty", pelicula.getDirector());
        assertEquals(2, pelicula.getActores().size());
        assertTrue(pelicula.getActores().contains("Kyle Chandler"));
        assertTrue(pelicula.getActores().contains("Millie Bobby Brown"));
        assertEquals("Pelea entre Kaijus",pelicula.getSinopsis());
    }
}
