import java.util.List;

public class Cliente extends Usuario {

    public Cliente(String nombre) {
        super(nombre);
    }

    public Boleto comprarBoleto(Funcion funcion, String idSala, List<Asiento> seleccion, MetodoDePago metodoPago) {
        return null;
    }
}
