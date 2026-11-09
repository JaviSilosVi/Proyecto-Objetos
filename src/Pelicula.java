import java.util.ArrayList;
import java.util.List;

public class Pelicula {
    private String titulo;
    private String duracion;
    private String clasificacion;
    private String genero;
    private String director;
    private List<String> actores;
    private String sinopsis;

    public Pelicula(String titulo, String duracion, String clasificacion, String genero, String director, List<String> actores, String sinopsis) {
        this.titulo = titulo;
        this.duracion = duracion;
        this.clasificacion = clasificacion;
        this.genero = genero;
        this.director = director;
        if (actores != null) {
            this.actores = actores;
        } else {
            this.actores = new ArrayList<>();
        }
        this.sinopsis = sinopsis;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public List<String> getActores() {
        return actores;
    }

    public void setActores(List<String> actores) {
        this.actores = actores;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }
}
