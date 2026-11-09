import java.util.ArrayList;
import java.util.Iterator;
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

    public boolean inicia() {
        System.out.println("Sistema de Gestion de Cines iniciado con exito.");
        return true;
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

    public void agregaCine(Cine cineNuevo) {
        if (cineNuevo != null) {
            this.cines.add(cineNuevo);
        }
    }

    public Cine obtenerCine(String nombreCine) {
        Iterator<Cine> iterador = this.cines.iterator();
        while (iterador.hasNext()) {
            Cine cineActual = iterador.next();
            if (cineActual.getNombreCine().equalsIgnoreCase(nombreCine)) {
                return cineActual;
            }
        }
        return null;
    }

    public boolean eliminarCine(String nombreCine) {
        Iterator<Cine> iterador = this.cines.iterator();
        while (iterador.hasNext()) {
            Cine cineActual = iterador.next();
            if (cineActual.getNombreCine().equalsIgnoreCase(nombreCine)) {
                iterador.remove();
                return true;
            }
        }
        return false;
    }

    public boolean modificarCine(String nombreActual, Cine cineActualizado) {
        if (this.eliminarCine(nombreActual)) {
            if (cineActualizado != null) {
                this.cines.add(cineActualizado);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void agregarPelicula(Pelicula nuevaPelicula) {
        if (nuevaPelicula != null) {
            this.catalogoPeliculas.add(nuevaPelicula);
        }
    }

    public Pelicula obtenerPelicula(String titulo) {
        Iterator<Pelicula> iterador = this.catalogoPeliculas.iterator();
        while (iterador.hasNext()) {
            Pelicula peliculaActual = iterador.next();
            if (peliculaActual.getTitulo().equalsIgnoreCase(titulo)) {
                return peliculaActual;
            }
        }
        return null;
    }

    public boolean eliminarPelicula(String titulo) {
        Iterator<Pelicula> iterador = this.catalogoPeliculas.iterator();
        while (iterador.hasNext()) {
            Pelicula peliculaActual = iterador.next();
            if (peliculaActual.getTitulo().equalsIgnoreCase(titulo)) {
                iterador.remove();
                return true;
            }
        }
        return false;
    }

    public boolean modificarPelicula(String tituloActual, Pelicula modificacion) {
        if (this.eliminarPelicula(tituloActual)) {
            if (modificacion != null) {
                this.catalogoPeliculas.add(modificacion);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public List<Pelicula> buscarPeliculasPorActor(String actorBuscado) {
        List<Pelicula> encontradas = new ArrayList<>();
        Iterator<Pelicula> iteradorPeliculas = this.catalogoPeliculas.iterator();
        while (iteradorPeliculas.hasNext()) {
            Pelicula p = iteradorPeliculas.next();
            if (p.getActores() != null) {
                Iterator<String> iteradorActores = p.getActores().iterator();
                while (iteradorActores.hasNext()) {
                    String actor = iteradorActores.next();
                    if (actor.toLowerCase().contains(actorBuscado.toLowerCase())) {
                        encontradas.add(p);
                        break;
                    }
                }
            }
        }
        return encontradas;
    }

    public List<Pelicula> buscarPeliculasPorClasificacion(String clasificacion) {
        List<Pelicula> encontradas = new ArrayList<>();
        Iterator<Pelicula> iterador = this.catalogoPeliculas.iterator();
        while (iterador.hasNext()) {
            Pelicula p = iterador.next();
            if (p.getClasificacion().equalsIgnoreCase(clasificacion)) {
                encontradas.add(p);
            }
        }
        return encontradas;
    }

    public boolean programarFuncion(String idCine, String idSala, Funcion nuevaFuncion) {
        Cine cine = this.obtenerCine(idCine);
        if (cine != null) {
            return cine.programarFuncion(idSala, nuevaFuncion);
        } else {
            System.out.println("Cine no encontrado: " + idCine);
            return false;
        }
    }

    public boolean eliminarFuncion(String nombreCine, String idSala, String fecha, int horarioHrs) {
        Cine cine = this.obtenerCine(nombreCine);
        if (cine != null) {
            Sala sala = cine.obtenerSala(idSala);
            if (sala != null) {
                return sala.cancelarFuncion(fecha, horarioHrs);
            }
        }
        return false;
    }

    public boolean modificarFuncion(String nombreCine, String idSala, String fecha, int horarioHrs, Funcion nuevaFuncion) {
        Cine cine = this.obtenerCine(nombreCine);
        if (cine != null) {
            Sala sala = cine.obtenerSala(idSala);
            if (sala != null) {
                if (sala.cancelarFuncion(fecha, horarioHrs)) {
                    return sala.programarFuncion(nuevaFuncion);
                }
            }
        }
        return false;
    }

    public boolean cancelarFuncion(String idCine, String idSala, Funcion funcionACancelar) {
        Cine cine = this.obtenerCine(idCine);
        if (cine != null) {
            return cine.cancelarFuncion(idSala, funcionACancelar);
        } else {
            return false;
        }
    }

    public void registrarBoleto(Boleto boleto) {
        if (boleto != null) {
            this.boletosVendidos.add(boleto);
        }
    }
}
