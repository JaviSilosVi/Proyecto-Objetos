public class Asiento {
    private String identificador;
    private String tipoAsiento;
    private String estadoAsiento;
    private double precio;

    public Asiento(String identificador, String tipoAsiento, double precio) {
        this.identificador = identificador;
        this.tipoAsiento = tipoAsiento;
        this.estadoAsiento = "DISPONIBLE";
        this.precio = precio;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getTipoAsiento() {
        return tipoAsiento;
    }

    public void setTipoAsiento(String tipoAsiento) {
        this.tipoAsiento = tipoAsiento;
    }

    public String getEstadoAsiento() {
        return estadoAsiento;
    }

    public void setEstadoAsiento(String estadoAsiento) {
        this.estadoAsiento = estadoAsiento;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean reservar() {
        return true;
    }

    public boolean vender() {
        return true;
    }
}
