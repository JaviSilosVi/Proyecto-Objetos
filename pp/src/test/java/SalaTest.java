import org.junit.jupiter.api.Test; 
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class SalaTest {
    @Test public void testAgregarYObtenerAsiento() { 
        Sala sala = new Sala("Sala 1"); 
        Asiento asiento = new Asiento("F12", "VIP", 85.50);
    }

    @Test
    public void testGenerarDistribucionAsientos() {
        Sala sala = new Sala("Sala VIP");
        
        // vamos a Generar 5 filas, con 10 asientos cada una, tipo "VIP", precio 100.0
        sala.generarAsientos(5, 10, "VIP", 100.0);
        
        // 5 x 10, deben ser 50 asientos
        assertEquals(50, sala.getTotalAsientos());
        
        Asiento primerAsiento = sala.obtenerAsiento("A1");
        assertNotNull(primerAsiento);
        
        Asiento ultimoAsiento = sala.obtenerAsiento("E10");
        assertNotNull(ultimoAsiento);
    }
    
    @Test
    public void testAgregaFuncionSinConflicto(){
        Sala sala = new Sala("Sala VIP");
        Funcion funcion1 = new Funcion("2026-20-05", 14, 30, null);

        boolean FuncionAgregada = sala.agregarFuncion(funcion1);
        assertTrue(FuncionAgregada);
    }
    @Test
    public void testAgregaFuncionConConflicto(){
        Sala sala = new Sala("Sala VIP");
        Funcion funcion1 = new Funcion("2026-20-05", 14, 30, null);
        Funcion funcion2 = new Funcion("2026-20-05", 14, 30, null);

        // La primera se agrega sin problema 
        assertTrue(sala.agregarFuncion(funcion1));
        
        // La segunda no se agrega por conflicto de horarios
        assertFalse(sala.agregarFuncion(funcion2));
    }

    @Test
    public void testCancelarFuncion(){
        Sala sala = new Sala("Sala VIP");
        Funcion funcion1 = new Funcion("2026-20-05", 14, 30, null);
        sala.agregarFuncion(funcion1);
        assertTrue(sala.cancelarFuncion("2026-20-05", 14));
    }

    @Test
    public void testEvitarConflictoHorario() throws ConflictoHorarioException {
        Sala sala = new Sala("Sala VIP"); 
        Pelicula peli = new Pelicula("Godzilla", "132 min", "B", "Ciencia Ficción", "Director", new ArrayList<>(), "Monstruos");
        
        Funcion funcion1 = new Funcion("2026-05-25", 18, 30, peli);
        Funcion funcion2 = new Funcion("2026-05-25", 18, 30, peli); 
        
        sala.programarFuncion(funcion1); 
        
        assertThrows(ConflictoHorarioException.class, ()->{sala.programarFuncion(funcion2);});
    }
}

