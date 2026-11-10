import java.util.ArrayList;

/**
 * Representa el comprobante físico o digital de una compra.
 * Agrupa la información de la película, sala, función y los asientos asignados al cliente.
 * 
 * @author Alejandro Mora Tapia, Chino Campos Yeshua Ismael y Salazar Rioú Pablo de Jesús
 * @version 1.0
 */

        /**
     * Constructor de la clase Boleto.
     * Calcula automáticamente el total a pagar iterando sobre los asientos seleccionados.
     * 
     * @param informacionPelicula La película que se va a proyectar.
     * @param informacionSala El identificador de la sala.
     * @param informacionFuncion Los datos de horario y fecha de la función.
     * @param asientosAsignados La lista de asientos que el cliente adquirió.
     */

public class Boleto {
    private Pelicula informacionPelicula;
    private String informacionSala;
    private Funcion informacionFuncion;
    private ArrayList<Asiento> asientosAsignados;
    private double totalPagado;

    public Boleto(Pelicula informacionPelicula, String informacionSala, Funcion informacionFuncion, ArrayList<Asiento> asientosAsignados) {
        this.informacionPelicula = informacionPelicula;
        this.informacionSala = informacionSala;
        this.informacionFuncion = informacionFuncion;
        this.asientosAsignados = asientosAsignados;
        this.totalPagado = calcularTotal();
    }

    // Calculamos el costo total haciendo un ciclo sobre la colección de asientos
    private double calcularTotal() {
        double suma = 0;
        for (Asiento a : asientosAsignados) {
            suma += a.getPrecio();
        }
        return suma;
    }

        /**
     * Imprime en la consola de texto el comprobante visual del boleto, 
     * detallando todos los datos de la compra y el total pagado.
     */

    public void imprimirBoleto() {
        System.out.println("=== BOLETO DE ENTRADA ===");
        System.out.println("Película: " + informacionPelicula.getTitulo());
        System.out.println("Sala: " + informacionSala + " | Función: " + informacionFuncion.getFecha() + " " + informacionFuncion.getHorarioHrs() + ":" + informacionFuncion.getHorarioMin());
        
        System.out.print("Asientos comprados (" + asientosAsignados.size() + "): ");
        for (int i = 0; i < asientosAsignados.size(); i++) {
            System.out.print(asientosAsignados.get(i).getIdentificador());
            if (i < asientosAsignados.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
        System.out.println("Total pagado: $" + totalPagado);
        System.out.println("=========================\n");
    }

        /**
     * Obtiene el monto total que fue pagado por el cliente en esta compra.
     * 
     * @return El costo total sumado de los asientos.
     */

    public double getTotalPagado() {
        return this.totalPagado;
    }
}