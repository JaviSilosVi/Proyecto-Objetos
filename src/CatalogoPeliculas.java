import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servicio con Responsabilidad Única (SRP):
 * Administra el mapa de películas y concentra los métodos de filtrado y búsqueda.
 */
public class CatalogoPeliculas {
    private Map<String, Pelicula> catalogo;

    public CatalogoPeliculas() {
        this.catalogo = new HashMap<>();
    }

    public Map<String, Pelicula> getCatalogo() {
        return this.catalogo;
    }

    public void setCatalogo(Map<String, Pelicula> catalogo) {
        this.catalogo = catalogo;
    }

    public void agregarPelicula(Pelicula nuevaPelicula) {
        if (nuevaPelicula != null) {
            this.catalogo.put(nuevaPelicula.getTitulo(), nuevaPelicula);
        }
    }

    public Pelicula obtenerPelicula(String titulo) throws EntidadNoEncontradaException {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título de la película no puede estar vacío.");
        }
        if (this.catalogo == null) {
            throw new EntidadNoEncontradaException("El catálogo de películas está vacío.");
        }
        String busqueda = titulo.trim().toLowerCase();

        for (Pelicula p : this.catalogo.values()) {
            if (p.getTitulo().equalsIgnoreCase(busqueda)) {
                return p;
            }
        }
        for (Pelicula p : this.catalogo.values()) {
            if (p.getTitulo().toLowerCase().contains(busqueda)) {
                return p;
            }
        }
        throw new EntidadNoEncontradaException("La película '" + titulo + "' no fue encontrada en el catálogo.");
    }

    public boolean eliminarPelicula(String titulo) throws EntidadNoEncontradaException {
        Pelicula p = obtenerPelicula(titulo);
        this.catalogo.remove(p.getTitulo());
        return true;
    }

    public boolean modificarPelicula(String tituloActual, Pelicula modificacion) throws EntidadNoEncontradaException {
        Pelicula p = obtenerPelicula(tituloActual);
        if (modificacion == null) {
            throw new IllegalArgumentException("La película modificada no puede ser nula.");
        }
        this.catalogo.remove(p.getTitulo());
        this.catalogo.put(modificacion.getTitulo(), modificacion);
        return true;
    }

    public List<Pelicula> buscarPeliculasPorActor(String actorBuscado) {
        List<Pelicula> encontradas = new ArrayList<>();
        if (this.catalogo != null) {
            for (Pelicula p : this.catalogo.values()) {
                if (p.getActores() != null) {
                    for (String actor : p.getActores()) {
                        if (actor.toLowerCase().contains(actorBuscado.toLowerCase())) {
                            encontradas.add(p);
                            break;
                        }
                    }
                }
            }
        }
        return encontradas;
    }

    public List<Pelicula> buscarPeliculasPorClasificacion(String clasificacion) {
        List<Pelicula> encontradas = new ArrayList<>();
        if (this.catalogo != null) {
            for (Pelicula p : this.catalogo.values()) {
                if (p.getClasificacion().equalsIgnoreCase(clasificacion)) {
                    encontradas.add(p);
                }
            }
        }
        return encontradas;
    }
}
