import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class Sistema {
    private Map<String, Cine> cines;
    private Map<String, Pelicula> catalogoPeliculas;
    private List<Boleto> boletosVendidos;
    private Map<String, Administrador> administradores;

    public Sistema() {
        this.cines = new HashMap<>();
        this.catalogoPeliculas = new HashMap<>();
        this.boletosVendidos = new ArrayList<>();
        this.administradores = new HashMap<>();
    }

    public boolean inicia() {
        System.out.println("Sistema de Gestion de Cines iniciado con exito.");
        return true;
    }

    public Map<String, Administrador> getAdministradores() {
        return administradores;
    }

    public void registrarAdministrador(Administrador admin) {
        if (admin != null) {
            this.administradores.put(admin.getNombre().toLowerCase(), admin);
        }
    }

    public Administrador autenticarAdmin(String nombre, String pin) {
        if (nombre == null || pin == null) return null;
        Administrador admin = this.administradores.get(nombre.trim().toLowerCase());
        if (admin != null && admin.validarPin(pin.trim())) {
            return admin;
        }
        return null;
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

    public Cine obtenerCine(String nombreCine) throws EntidadNoEncontradaException {
        if (nombreCine == null || nombreCine.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cine no puede estar vacío.");
        }
        if (this.cines == null) {
            throw new EntidadNoEncontradaException("El catálogo de cines está vacío.");
        }
        String busqueda = nombreCine.trim().toLowerCase();

        // 1. Coincidencia exacta
        for (Cine c : this.cines.values()) {
            if (c.getNombreCine().equalsIgnoreCase(busqueda)) {
                return c;
            }
        }
        // 2. Coincidencia parcial
        for (Cine c : this.cines.values()) {
            if (c.getNombreCine().toLowerCase().contains(busqueda)) {
                return c;
            }
        }
        throw new EntidadNoEncontradaException("El cine '" + nombreCine + "' no existe en el sistema.");
    }

    public boolean eliminarCine(String nombreCine) throws EntidadNoEncontradaException {
        Cine cine = obtenerCine(nombreCine); // Si no existe, lanza EntidadNoEncontradaException
        this.cines.remove(cine.getNombreCine());
        return true;
    }

    public boolean modificarCine(String nombreActual, Cine cineActualizado) throws EntidadNoEncontradaException {
        Cine cine = obtenerCine(nombreActual);
        if (cineActualizado == null) {
            throw new IllegalArgumentException("El cine actualizado no puede ser nulo.");
        }
        this.cines.remove(cine.getNombreCine());
        this.cines.put(cineActualizado.getNombreCine(), cineActualizado);
        return true;
    }

    public void agregarPelicula(Pelicula nuevaPelicula) {
        if (nuevaPelicula != null) {
            this.catalogoPeliculas.put(nuevaPelicula.getTitulo(), nuevaPelicula);
        }
    }

    public Pelicula obtenerPelicula(String titulo) throws EntidadNoEncontradaException {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título de la película no puede estar vacío.");
        }
        if (this.catalogoPeliculas == null) {
            throw new EntidadNoEncontradaException("El catálogo de películas está vacío.");
        }
        String busqueda = titulo.trim().toLowerCase();

        for (Pelicula p : this.catalogoPeliculas.values()) {
            if (p.getTitulo().equalsIgnoreCase(busqueda)) {
                return p;
            }
        }
        for (Pelicula p : this.catalogoPeliculas.values()) {
            if (p.getTitulo().toLowerCase().contains(busqueda)) {
                return p;
            }
        }
        throw new EntidadNoEncontradaException("La película '" + titulo + "' no fue encontrada en el catálogo.");
    }

    public boolean eliminarPelicula(String titulo) throws EntidadNoEncontradaException {
        Pelicula p = obtenerPelicula(titulo); // Lanza EntidadNoEncontradaException si no existe
        this.catalogoPeliculas.remove(p.getTitulo());
        return true;
    }

    public boolean modificarPelicula(String tituloActual, Pelicula modificacion) throws EntidadNoEncontradaException {
        Pelicula p = obtenerPelicula(tituloActual);
        if (modificacion == null) {
            throw new IllegalArgumentException("La película modificada no puede ser nula.");
        }
        this.catalogoPeliculas.remove(p.getTitulo());
        this.catalogoPeliculas.put(modificacion.getTitulo(), modificacion);
        return true;
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
