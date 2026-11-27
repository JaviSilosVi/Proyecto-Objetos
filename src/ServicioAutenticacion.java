import java.util.HashMap;
import java.util.Map;

/**
 * Servicio con Responsabilidad Única (SRP):
 * Gestiona exclusivamente el registro de administradores y la verificación de PINs.
 */
public class ServicioAutenticacion {
    private Map<String, Administrador> administradores;

    public ServicioAutenticacion() {
        this.administradores = new HashMap<>();
    }

    public void registrarAdministrador(Administrador admin) {
        if (admin != null) {
            this.administradores.put(admin.getNombre().toLowerCase(), admin);
        }
    }

    public Administrador autenticarAdmin(String nombre, String pin) {
        if (nombre == null || pin == null) {
            return null;
        }
        Administrador admin = this.administradores.get(nombre.trim().toLowerCase());
        if (admin != null && admin.validarPin(pin.trim())) {
            return admin;
        }
        return null;
    }

    public Map<String, Administrador> getAdministradores() {
        return this.administradores;
    }
}
