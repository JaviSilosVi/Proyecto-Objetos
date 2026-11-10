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

    public Cine obtenerCine(String nombreCine) {
        if (this.cines == null || nombreCine == null) return null;
        String busqueda = nombreCine.trim().toLowerCase();

        // 1. Coincidencia exacta
        for (Cine c : this.cines.values()) {
            if (c.getNombreCine().equalsIgnoreCase(busqueda)) {
                return c;
            }
        }
        // 2. Coincidencia parcial (por si escribe solo "Cinepolis")
        for (Cine c : this.cines.values()) {
            if (c.getNombreCine().toLowerCase().contains(busqueda)) {
                return c;
            }
        }
        return null;
    }

    public boolean eliminarCine(String nombreCine) {
        Cine cine = obtenerCine(nombreCine);
        if (cine != null) {
            this.cines.remove(cine.getNombreCine());
            return true;
        }
        return false;
    }

    public boolean modificarCine(String nombreActual, Cine cineActualizado) {
        Cine cine = obtenerCine(nombreActual);
        if (cine != null && cineActualizado != null) {
            this.cines.remove(cine.getNombreCine());
            this.cines.put(cineActualizado.getNombreCine(), cineActualizado);
            return true;
        }
        return false;
    }

    public void agregarPelicula(Pelicula nuevaPelicula) {
        if (nuevaPelicula != null) {
            this.catalogoPeliculas.put(nuevaPelicula.getTitulo(), nuevaPelicula);
        }
    }

    public Pelicula obtenerPelicula(String titulo) {
        if (this.catalogoPeliculas == null || titulo == null) return null;
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
        return null;
    }

    public boolean eliminarPelicula(String titulo) {
        Pelicula p = obtenerPelicula(titulo);
        if (p != null) {
            this.catalogoPeliculas.remove(p.getTitulo());
            return true;
        }
        return false;
    }

    public boolean modificarPelicula(String tituloActual, Pelicula modificacion) {
        Pelicula p = obtenerPelicula(tituloActual);
        if (p != null && modificacion != null) {
            this.catalogoPeliculas.remove(p.getTitulo());
            this.catalogoPeliculas.put(modificacion.getTitulo(), modificacion);
            return true;
        }
        return false;
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
