import java.util.ArrayList;
import java.util.List;

public class Sistema {
    private List<Cine> cines;
    private List<Pelicula> catalogoPeliculas;
    private List<Boleto> boletosVendidos;

    public Sistema() {
        this.cines = new ArrayList<>();
        this.catalogoPeliculas = new ArrayList<>();
        this.boletosVendidos = new ArrayList<>();
    }

    public List<Cine> getCines() {
        return cines;
    }

    public void setCines(List<Cine> cines) {
        this.cines = cines;
    }

    public List<Pelicula> getCatalogoPeliculas() {
        return catalogoPeliculas;
    }

    public void setCatalogoPeliculas(List<Pelicula> catalogoPeliculas) {
        this.catalogoPeliculas = catalogoPeliculas;
    }

    public List<Boleto> getBoletosVendidos() {
        return boletosVendidos;
    }

    public void setBoletosVendidos(List<Boleto> boletosVendidos) {
        this.boletosVendidos = boletosVendidos;
    }
}
