import java.util.List;
import java.util.Map;

/**
 * Fachada (Facade) que coordina los submódulos del sistema:
 * ServicioAutenticacion, GestorCines, CatalogoPeliculas y GestorVentas.
 */
public class Sistema {
    private ServicioAutenticacion servicioAuth;
    private GestorCines gestorCines;
    private CatalogoPeliculas catalogoPeliculas;
    private GestorVentas gestorVentas;

    public Sistema() {
        this.servicioAuth = new ServicioAutenticacion();
        this.gestorCines = new GestorCines();
        this.catalogoPeliculas = new CatalogoPeliculas();
        this.gestorVentas = new GestorVentas();
    }

    public boolean inicia() {
        System.out.println("Sistema de Gestion de Cines iniciado con exito.");
        return true;
    }

    // --- Submódulos ---
    public ServicioAutenticacion getServicioAuth() {
        return this.servicioAuth;
    }

    public GestorCines getGestorCines() {
        return this.gestorCines;
    }

    public CatalogoPeliculas getModuloPeliculas() {
        return this.catalogoPeliculas;
    }

    public GestorVentas getGestorVentas() {
        return this.gestorVentas;
    }

    // --- Delegación a ServicioAutenticacion ---
    public Map<String, Administrador> getAdministradores() {
        return this.servicioAuth.getAdministradores();
    }

    public void registrarAdministrador(Administrador admin) {
        this.servicioAuth.registrarAdministrador(admin);
    }

    public Administrador autenticarAdmin(String nombre, String pin) {
        return this.servicioAuth.autenticarAdmin(nombre, pin);
    }

    // --- Delegación a GestorCines ---
    public Map<String, Cine> getCines() {
        return this.gestorCines.getCines();
    }

    public void setCines(Map<String, Cine> cines) {
        this.gestorCines.setCines(cines);
    }

    public void agregaCine(Cine cineNuevo) {
        this.gestorCines.agregaCine(cineNuevo);
    }

    public Cine obtenerCine(String nombreCine) throws EntidadNoEncontradaException {
        return this.gestorCines.obtenerCine(nombreCine);
    }

    public boolean eliminarCine(String nombreCine) throws EntidadNoEncontradaException {
        return this.gestorCines.eliminarCine(nombreCine);
    }

    public boolean modificarCine(String nombreActual, Cine cineActualizado) throws EntidadNoEncontradaException {
        return this.gestorCines.modificarCine(nombreActual, cineActualizado);
    }

    // --- Delegación a CatalogoPeliculas ---
    public Map<String, Pelicula> getCatalogoPeliculas() {
        return this.catalogoPeliculas.getCatalogo();
    }

    public void setCatalogoPeliculas(Map<String, Pelicula> catalogoPeliculas) {
        this.catalogoPeliculas.setCatalogo(catalogoPeliculas);
    }

    public void agregarPelicula(Pelicula nuevaPelicula) {
        this.catalogoPeliculas.agregarPelicula(nuevaPelicula);
    }

    public Pelicula obtenerPelicula(String titulo) throws EntidadNoEncontradaException {
        return this.catalogoPeliculas.obtenerPelicula(titulo);
    }

    public boolean eliminarPelicula(String titulo) throws EntidadNoEncontradaException {
        return this.catalogoPeliculas.eliminarPelicula(titulo);
    }

    public boolean modificarPelicula(String tituloActual, Pelicula modificacion) throws EntidadNoEncontradaException {
        return this.catalogoPeliculas.modificarPelicula(tituloActual, modificacion);
    }

    public List<Pelicula> buscarPeliculasPorActor(String actorBuscado) {
        return this.catalogoPeliculas.buscarPeliculasPorActor(actorBuscado);
    }

    public List<Pelicula> buscarPeliculasPorClasificacion(String clasificacion) {
        return this.catalogoPeliculas.buscarPeliculasPorClasificacion(clasificacion);
    }

    // --- Delegación de Funciones en Salas ---
    public boolean programarFuncion(String idCine, String idSala, Funcion nuevaFuncion)
            throws EntidadNoEncontradaException, ConflictoHorarioException {
        Cine cine = this.obtenerCine(idCine);
        return cine.programarFuncion(idSala, nuevaFuncion);
    }

    public boolean eliminarFuncion(String nombreCine, String idSala, String fecha, int horarioHrs)
            throws EntidadNoEncontradaException {
        Cine cine = this.obtenerCine(nombreCine);
        Sala sala = cine.obtenerSala(idSala);
        return sala.cancelarFuncion(fecha, horarioHrs);
    }

    public boolean modificarFuncion(String nombreCine, String idSala, String fecha, int horarioHrs, Funcion nuevaFuncion)
            throws EntidadNoEncontradaException, ConflictoHorarioException {
        Cine cine = this.obtenerCine(nombreCine);
        Sala sala = cine.obtenerSala(idSala);
        if (sala.cancelarFuncion(fecha, horarioHrs)) {
            return sala.programarFuncion(nuevaFuncion);
        }
        return false;
    }

    public boolean cancelarFuncion(String idCine, String idSala, Funcion funcionACancelar)
            throws EntidadNoEncontradaException {
        Cine cine = this.obtenerCine(idCine);
        return cine.cancelarFuncion(idSala, funcionACancelar);
    }

    // --- Delegación a GestorVentas ---
    public List<Boleto> getBoletosVendidos() {
        return this.gestorVentas.getBoletosVendidos();
    }

    public void setBoletosVendidos(List<Boleto> boletosVendidos) {
        this.gestorVentas.setBoletosVendidos(boletosVendidos);
    }

    public void registrarBoleto(Boleto boleto) {
        this.gestorVentas.registrarBoleto(boleto);
    }
}
