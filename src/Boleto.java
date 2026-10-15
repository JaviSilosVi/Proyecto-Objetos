import java.util.ArrayList;
import java.util.List;

public class Boleto {
    private Pelicula informacionPelicula;
    private String informacionSala;
    private Funcion informacionFuncion;
    private List<Asiento> asientosAsignados;
    private double totalPagado;
    private MetodoDePago metodoPago;

    public Boleto(Pelicula informacionPelicula, String informacionSala, Funcion informacionFuncion, List<Asiento> asientosAsignados, MetodoDePago metodoPago) {
        this.informacionPelicula = informacionPelicula;
        this.informacionSala = informacionSala;
        this.informacionFuncion = informacionFuncion;
        if (asientosAsignados != null) {
         this.asientosAsignados = asientosAsignados;
       } else {
        this.asientosAsignados = new ArrayList<>();
       }
        this.metodoPago = metodoPago;
        this.totalPagado = 0.0;
    }

    public Pelicula getInformacionPelicula() {
        return informacionPelicula;
    }

    public void setInformacionPelicula(Pelicula informacionPelicula) {
        this.informacionPelicula = informacionPelicula;
    }

    public String getInformacionSala() {
        return informacionSala;
    }

    public void setInformacionSala(String informacionSala) {
        this.informacionSala = informacionSala;
    }

    public Funcion getInformacionFuncion() {
        return informacionFuncion;
    }

    public void setInformacionFuncion(Funcion informacionFuncion) {
        this.informacionFuncion = informacionFuncion;
    }

    public List<Asiento> getAsientosAsignados() {
        return asientosAsignados;
    }

    public void setAsientosAsignados(List<Asiento> asientosAsignados) {
        this.asientosAsignados = asientosAsignados;
    }

    public double getTotalPagado() {
        return totalPagado;
    }

    public void setTotalPagado(double totalPagado) {
        this.totalPagado = totalPagado;
    }

    public MetodoDePago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoDePago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public double calcularTotal() {
        return this.totalPagado;
    }

    public void imprimirBoleto() {
    }
}
