import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Sistema {
    private Map<String, Cine> cines;
    private Map<String, Pelicula> catalogoPeliculas;
    private List<Boleto> boletosVendidos;

    public Sistema() {
        this.cines = new HashMap<>();
        this.catalogoPeliculas = new HashMap<>();
        this.boletosVendidos = new ArrayList<>();
    }

    public boolean inicia() {
        System.out.println("Sistema de Gestion de Cines iniciado con exito.");
        return true;
    }

    public Map<String, Cine> getCines() {
        return cines;
    }

    public void setCines(Map<String, Cine> cines) {
        this.cines = cines;
    }

    public Map<String, Pelicula> getCatalogoPeliculas() {
        return catalogoPeliculas;
    }

    public void setCatalogoPeliculas(Map<String, Pelicula> catalogoPeliculas) {
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
            this.cines.put(cineNuevo.getNombreCine(), cineNuevo);
        }
    }

    public Cine obtenerCine(String nombreCine) {
        if (this.cines != null) {
            return this.cines.get(nombreCine);
        }
        return null;
    }

    public boolean eliminarCine(String nombreCine) {
        if (this.cines != null && this.cines.containsKey(nombreCine)) {
            this.cines.remove(nombreCine);
            return true;
        }
        return false;
    }

    public boolean modificarCine(String nombreActual, Cine cineActualizado) {
        if (this.eliminarCine(nombreActual)) {
            if (cineActualizado != null) {
                this.cines.put(cineActualizado.getNombreCine(), cineActualizado);
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
            this.catalogoPeliculas.put(nuevaPelicula.getTitulo(), nuevaPelicula);
        }
    }

    public Pelicula obtenerPelicula(String titulo) {
        if (this.catalogoPeliculas != null) {
            return this.catalogoPeliculas.get(titulo);
        }
        return null;
    }

    public boolean eliminarPelicula(String titulo) {
        if (this.catalogoPeliculas != null && this.catalogoPeliculas.containsKey(titulo)) {
            this.catalogoPeliculas.remove(titulo);
            return true;
        }
        return false;
    }

    public boolean modificarPelicula(String tituloActual, Pelicula modificacion) {
        if (this.eliminarPelicula(tituloActual)) {
            if (modificacion != null) {
                this.catalogoPeliculas.put(modificacion.getTitulo(), modificacion);
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
        if (this.catalogoPeliculas != null) {
            for (Pelicula p : this.catalogoPeliculas.values()) {
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
        if (this.catalogoPeliculas != null) {
            for (Pelicula p : this.catalogoPeliculas.values()) {
                if (p.getClasificacion().equalsIgnoreCase(clasificacion)) {
                    encontradas.add(p);
                }
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
