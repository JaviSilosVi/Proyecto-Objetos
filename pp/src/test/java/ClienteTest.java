import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class ClienteTest {

    @Test
    public void testEvitarVentaAsientoDuplicado() throws AsientoOcupadoException {
        
        Cliente cliente1 = new Cliente("Juan Perez");
        Cliente cliente2 = new Cliente("Maria Lopez");

        
        Pelicula peli = new Pelicula("Spider-Man", "120 min", "B", "Acción", "Director", new ArrayList<>(), "Sinopsis");
        Funcion funcion = new Funcion("2026-05-25", 16, 0, peli);
        
        // creamos un asiento en estado DISPONIBLE y lo metemos a la lista
        Asiento asientoVIP = new Asiento("VIP", "DISPONIBLE", 150.0);
        ArrayList<Asiento> asientosAComprar = new ArrayList<>();
        asientosAComprar.add(asientoVIP);

        // Simulamos la tarjeta
        MetodoDePago tarjeta = new TarjetaCredito("1234567890123456");

        
        // En el primer intento hacemos que Juan compre el asiento. Debería ser exitoso y generrle un Boleto.
        Boleto boletoJuan = cliente1.comprarBoleto(funcion, "Sala 1", asientosAComprar, tarjeta);
        assertNotNull(boletoJuan);

        // Ahora hacemos que Maria intente comprar exactamente la misma lista con el MISMO asiento.
        // Como el estado del asiento ya se venvio, el metodo debe devolver null.
        assertThrows(AsientoOcupadoException.class, ()-> {cliente2.comprarBoleto(funcion, "Sala 1", asientosAComprar, tarjeta);});
    }
}