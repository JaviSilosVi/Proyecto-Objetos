import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class GestorArchivosTest {

    @Test
    public void testGuardarYCargarPeliculas() throws FormatoArchivoException {
        GestorArchivos gestor = new GestorArchivos();
        Map<String, Pelicula> catalogoPrueba = new HashMap<>();
        
        //Creamos una pelicula para despues cargarla en el archivo
        Pelicula peliOriginal = new Pelicula("Interstellar", "169 min", "B", "Ciencia Ficcion", "Christopher Nolan", new ArrayList<>(), "Viaje al espacio.");
        catalogoPrueba.put(peliOriginal.getTitulo(), peliOriginal);

        gestor.guardarPeliculas(catalogoPrueba);
        
        File archivo = new File("peliculas.txt");
        assertTrue(archivo.exists(), "El archivo peliculas.txt no se creo");

        // Simulamos un sistema completamente nuevo y vacío
        Sistema sistemaPrueba = new Sistema();
        
        // Le pedimos al gestor que lea el .txt y llene este nuevo sistema
        gestor.cargarPeliculas(sistemaPrueba);

        // Buscamos la película en el sistema nuevo para ver si revivió correctamente
        Pelicula peliCargada = sistemaPrueba.obtenerPelicula("Interstellar");

        assertNotNull(peliCargada, "La pelicula no se cargo desde el archivo");
        assertEquals("Interstellar", peliCargada.getTitulo());
        assertEquals("Christopher Nolan", peliCargada.getDirector());
        assertEquals("Viaje al espacio.", peliCargada.getSinopsis());
    }
}
