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

    public void registraPelicula(Pelicula nuevaPelicula, Sistema sistemaActivo) {
        if (sistemaActivo != null) {
            sistemaActivo.agregarPelicula(nuevaPelicula);
        }
    }

    public boolean modificaPelicula(String tituloActual, Pelicula modificacion, Sistema sistemaActivo) {
        if (sistemaActivo != null) {
            return sistemaActivo.modificarPelicula(tituloActual, modificacion);
        } else {
            return false;
        }
    }

    public boolean eliminarPelicula(String tituloElimina, Sistema sistemaActivo) {
        if (sistemaActivo != null) {
            return sistemaActivo.eliminarPelicula(tituloElimina);
        } else {
            return false;
        }
    }

    public Pelicula obtenerPelicula(String titulo, Sistema sistemaActivo) {
        if (sistemaActivo != null) {
            return sistemaActivo.obtenerPelicula(titulo);
        } else {
            return null;
        }
    }

    public boolean programarFuncion(String idCine, String idSala, Funcion nuevaFuncion, Sistema sistemaActivo) {
        if (sistemaActivo != null) {
            return sistemaActivo.programarFuncion(idCine, idSala, nuevaFuncion);
        } else {
            return false;
        }
    }

    public boolean eliminarFuncion(String idCine, String idSala, Funcion funcionACancelar, Sistema sistemaActivo) {
        if (sistemaActivo != null) {
            return sistemaActivo.cancelarFuncion(idCine, idSala, funcionACancelar);
        } else {
            return false;
        }
    }

    public boolean modificarFuncion(String idCine, String idSala, Funcion funcionActual, Funcion funcionModificada, Sistema sistemaActivo) {
        if (sistemaActivo != null) {
            return sistemaActivo.modificarFuncion(idCine, idSala, funcionActual.getFecha(), funcionActual.getHorarioHrs(), funcionModificada);
        } else {
            return false;
        }
    }
}
