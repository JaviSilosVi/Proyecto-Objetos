import java.util.List;

/**
 * Clase auxiliar con Responsabilidad Única (SRP):
 * Se encarga únicamente de dar formato visual de texto a un Boleto,
 * separando la presentación de los datos del boleto.
 */
public class FormateadorBoleto {

    public static String generarTextoBoleto(Boleto boleto) {
        if (boleto == null) {
            return "";
        }

        String texto = "   Boletos A Comprar           \n";
        if (boleto.getInformacionPelicula() != null) {
            texto = texto + "Pelicula: " + boleto.getInformacionPelicula().getTitulo() + "\n";
            texto = texto + "Clasificacion: " + boleto.getInformacionPelicula().getClasificacion() + "\n";
        }
        texto = texto + "Sala: " + boleto.getInformacionSala() + "\n";
        if (boleto.getInformacionFuncion() != null) {
            Funcion f = boleto.getInformacionFuncion();
            texto = texto + "Fecha: " + f.getFecha() + "\n";
            texto = texto + "Horario: " + f.getHorarioHrs() + ":" + f.getHorarioMin() + "\n";
        }
        texto = texto + "Asientos: ";
        List<Asiento> asientos = boleto.getAsientosAsignados();
        if (asientos != null) {
            for (int i = 0; i < asientos.size(); i++) {
                texto = texto + asientos.get(i).getIdentificador() + " ";
            }
        }
        texto = texto + "\nTotal Pagado: $" + boleto.getTotalPagado();
        return texto;
    }
}
