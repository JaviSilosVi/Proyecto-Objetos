import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class AdministradorTest{

    @Test
    public void testResgistraPelicula(){
        Sistema sistema = new Sistema(); 
        Administrador admin = new Administrador();

        ArrayList<String> actores = new ArrayList<>();
        actores.add("Kyle Chandler");
        actores.add("Millie Bobby Brown");
        Pelicula pelicula = new Pelicula("Godzilla", "132 minutos", "B", "Ciencia ficcion", "Michael Dougherty", actores, "Pelea entre Kaijus");

        admin.registraPelicula(pelicula, sistema); // Aqui ya delegamos la orden al sistema
        Pelicula peliculaGuardada = admin.obtenerPelicula("Godzilla", sistema);

        assertNotNull(peliculaGuardada);
        assertEquals("Godzilla", peliculaGuardada.getTitulo());
    }

    @Test
    public void testModificaPelicula() throws EntidadNoEncontradaException {
        Sistema sistema = new Sistema();
        Administrador admin = new Administrador();
        ArrayList<String> actores = new ArrayList<>();
        actores.add("Kyle Chandler");
        actores.add("Millie Bobby Brown");
        Pelicula pelicula1 = new Pelicula ("Kong", "132 minutos", "B", "Ciencia ficcion", "Michael Dougherty", actores, "Pelea entre Kaijus");
        Pelicula pelicula2 = new Pelicula ("Godzilla", "132 minutos", "B", "Ciencia ficcion", "Michael Dougherty", actores, "Pelea entre Kaijus");

        admin.registraPelicula(pelicula1, sistema);
        assertNotNull(admin.obtenerPelicula("Kong", sistema));

        boolean modificacion = admin.modificaPelicula("Kong", pelicula2, sistema);
        assertTrue(modificacion);
        assertNotNull(admin.obtenerPelicula("Godzilla", sistema));
    }
    @Test
    public void testModificaPeliculaInexistente() {
        Sistema sistema = new Sistema();
        Administrador admin = new Administrador();
        
        // Creamos una película para intentar modificarla
        Pelicula peliFantasma = new Pelicula("Avatar", "160 minutos", "B", "Ciencia ficcion", "James Cameron", new ArrayList<>(), "Aliens azules");
        
        // Comprobamos que salta el error si intentamos modificar un título que no existe
        assertThrows(EntidadNoEncontradaException.class, () -> {
            admin.modificaPelicula("Shrek 5", peliFantasma, sistema);
        });
    }

    @Test
    public void testEliminarPelicula() throws EntidadNoEncontradaException {
        Sistema sistema = new Sistema();
        Administrador admin = new Administrador();
        ArrayList<String> actores = new ArrayList<>();
        actores.add("Kyle Chandler");
        actores.add("Millie Bobby Brown");
        Pelicula pelicula1 = new Pelicula ("Kong", "132 minutos", "B", "Ciencia ficcion", "Michael Dougherty", actores, "Pelea entre Kaijus");
        
        admin.registraPelicula(pelicula1, sistema);
    
        boolean eliminacion = admin.eliminarPelicula("Kong", sistema);
        assertTrue(eliminacion);
        assertNull(admin.obtenerPelicula("Kong", sistema));
    }

    @Test
    public void testEliminarPeliculaInexistente() {
        Sistema sistema = new Sistema();
        Administrador admin = new Administrador();
        
        assertThrows(EntidadNoEncontradaException.class, () -> {
            admin.eliminarPelicula("Shrek 5", sistema);
        });
    }


    @Test 
    public void testProgramarFuncion() throws ConflictoHorarioException {
        Sistema sistema = new Sistema();
        Administrador admin = new Administrador();

        Cine cine = new Cine("Cine el Dorado");
        Sala sala = new Sala("Sala 1");

        cine.agregarSala("Sala 1", sala);
        sistema.agregarCine(cine);

        Funcion funcion = new Funcion("2026-20-05", 14, 30, null);
        
        assertTrue(admin.programarFuncion("Cine el Dorado", "Sala 1", funcion, sistema));
    }

    @Test
    public void testEliminarFuncion() throws ConflictoHorarioException {
        Sistema sistema = new Sistema();
        Administrador admin = new Administrador();

        Cine cine = new Cine("Cine plaza el Paseo");
        Sala sala = new Sala("Sala 2");

        cine.agregarSala("Sala 2", sala);
        sistema.agregarCine(cine);

        Funcion funcion = new Funcion("2026-20-05", 14, 30, null);
        admin.programarFuncion("Cine plaza el Paseo", "Sala 2", funcion, sistema);

        // le pasamos directamente el objeto 'funcion'
        boolean eliminacionExitosa = admin.eliminarFuncion("Cine plaza el Paseo", "Sala 2", funcion, sistema);
        assertTrue(eliminacionExitosa);
        assertFalse(sala.getProgramacion().contains(funcion)); // Aqui nos debe de dar vacio.
    }

    @Test
    public void testModificarFuncion() throws ConflictoHorarioException {
        Sistema sistema = new Sistema();
        Administrador admin = new Administrador();

        Cine cine = new Cine("Cine plaza Sendero");
        Sala sala = new Sala("Sala 3");

        cine.agregarSala("Sala 3", sala);
        sistema.agregarCine(cine);

        Funcion funcionActual = new Funcion("2026-20-05", 14, 30, null);
        admin.programarFuncion("Cine plaza Sendero", "Sala 3", funcionActual, sistema);

        Funcion funcionModificada = new Funcion("2026-20-05", 16, 30, null);
        
        boolean modificacionExitosa = admin.modificarFuncion("Cine plaza Sendero", "Sala 3", funcionActual, funcionModificada, sistema);
        assertTrue(modificacionExitosa);
    }
}