import java.util.List;

public class Administrador extends Usuario {
    private String pinAcceso;

    public Administrador(String nombre, String pinAcceso) {
        super(nombre);
        this.pinAcceso = pinAcceso;
    }

    public String getPinAcceso() {
        return pinAcceso;
    }

    public void setPinAcceso(String pinAcceso) {
        this.pinAcceso = pinAcceso;
    }

    public boolean validarPin(String pinIngresado) {
        if (this.pinAcceso != null) {
            if (this.pinAcceso.equals(pinIngresado)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
