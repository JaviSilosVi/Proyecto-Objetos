import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;




public class Sala {
    private String idSala;
    private List<Funcion> programacion;
    
    // Usaremos el mapa que nos pidio el profesor para busquedas rapidas O(1)
    private Map<String, Asiento> asientos;

    private int filas;
    private int asientosPorFila;
    private String tipoSala;
    private double precioBase;

    public String getIdSala() {
    return this.idSala; 
}
    public int getFilas() {
    return this.filas;
}
    public int getAsientosPorFila() {
    return this.asientosPorFila;
}
    public String getTipoSala() {
    return this.tipoSala; 
}
    public double getPrecioBase() {
    return this.precioBase; 
}


    public Sala(String idSala) {
        this.idSala = idSala;
        // Inicializo la colección
        this.asientos = new HashMap<>(); 
        this.programacion = new ArrayList<>();
    }

    public void agregarAsiento(Asiento asiento) {
        this.asientos.put(asiento.getIdentificador(), asiento);
    }

    public Asiento obtenerAsiento(String idAsiento) {
        return this.asientos.get(idAsiento);
    }

    // Metodo para cumplir la distribución, tipo y precio
    public void generarAsientos(int filas, int asientosPorFila, String tipo, double precio) {
        this.filas = filas;
        this.asientosPorFila = asientosPorFila;
        this.tipoSala = tipo;
        this.precioBase = precio;
        
        for (int i = 0; i < filas; i++) {
            // Convierte el número de fila a letra (0 = 'A', 1 = 'B', etc.)
            char letraFila = (char) ('A' + i); 
            
            for (int j = 1; j <= asientosPorFila; j++) {
                String idAsiento = letraFila + String.valueOf(j);
                Asiento nuevoAsiento = new Asiento(idAsiento, tipo, precio);
                this.asientos.put(idAsiento, nuevoAsiento);
            }
        }
    }

    //Metodo para funciones evitando conflictos de horario
    //Correccion: Sustitucion de this.funcion por this.programacion
    public boolean agregarFuncion (Funcion nuevaFuncion){
        //recorremos las funciones existentes para verificar el tema de conflicto con los horarios
        for(Funcion funcionesActuales : this.programacion){
            //revisamos si las fechas chocan 
            if(funcionesActuales.getFecha().equals(nuevaFuncion.getFecha()) && funcionesActuales.getHorarioHrs() == nuevaFuncion.getHorarioHrs()){
                System.out.println("Error en este horario ya existe una funcion programada");
                return false;
            }
        }
        //ya que el ciclo acabo y no hay conflictos agregamos la funcion 
        nuevaFuncion.inicializarAsientos(this.asientos);
        this.programacion.add(nuevaFuncion);
        return true;
    }

    //metodo para cancelar funciones
    //Coreccion: Sustitucion de this.fuincion por this.programacion
    public boolean cancelarFuncion(String fecha, int horarioHrs){
        Iterator<Funcion> it = this.programacion.iterator();
        while (it.hasNext()){
            Funcion funcionActual = it.next();
            //buscamos la funcion que coincida con la que se quiere eliminar
            if(funcionActual.getFecha().equals(fecha) && funcionActual.getHorarioHrs() == horarioHrs){
                it.remove();
                return true;
            }
        }
        //no se encontro la funcion
        return false;
    }

    public boolean modificarFuncion(Funcion funcionActual, Funcion funcionModificada) throws ConflictoHorarioException {
        // evaluamos si la función vieja realmente existe
        if (this.programacion.contains(funcionActual)) {
            
            // La quitamos temporalmente
            this.programacion.remove(funcionActual);
            
            //bandera para poder verificar que la nueva funcion (modificada) no entra en conflicto con las existentes
            boolean hayConflicto =false;
            for(Funcion f:programacion){
                if (f.getFecha().equals(funcionModificada.getFecha()) && 
                    f.getHorarioHrs() == funcionModificada.getHorarioHrs() &&
                    f.getHorarioMin() == funcionModificada.getHorarioMin()) {
                    hayConflicto = true;
                    break; // Hemos encontrado un conflicto o coque entre funciones
                }
            }
            if(hayConflicto){
                // Si hubo choque, regresamos la función vieja a su lugar y disparamos la alarma
                this.programacion.add(funcionActual);
                throw new ConflictoHorarioException("Error: Ya hay una función programada a esta hora en la sala");
            }else{
                // Si el camino está libre, guardamos la nueva definitivamente
                funcionModificada.inicializarAsientos(this.asientos);
                this.programacion.add(funcionModificada);
                return true;
            }
        }
        return false;
}

    public int getTotalAsientos() {
        return this.asientos.size();
    }


/// Este metodo nos ayuda a evitar conflictos de horario
    public boolean programarFuncion(Funcion nuevaFuncion) throws ConflictoHorarioException {
        // Recorremos las funciones y existentes vemos si hay algun empalme
        for (Funcion f : programacion) {
            if (f.getFecha().equals(nuevaFuncion.getFecha()) && 
                f.getHorarioHrs() == nuevaFuncion.getHorarioHrs() &&
                f.getHorarioMin() == nuevaFuncion.getHorarioMin()) {

                    throw new ConflictoHorarioException("error Ya hay una función a esta hora.");
                
            }
        }
        //si no hay conflictos, entonces agregamos
        nuevaFuncion.inicializarAsientos(this.asientos);
        this.programacion.add(nuevaFuncion);
        return true;
}

    // Método para cancelar una función
    public boolean cancelarFuncion(Funcion funcionACancelar) {
        return this.programacion.remove(funcionACancelar);
    }
    
    // Este getter nos sirve para las pruebas y comprar boletos
    public List<Funcion> getProgramacion() {
        return this.programacion;
    }

    public void mostrarAsientos() {
        if (this.asientos.isEmpty()) {
            System.out.println("No hay asientos generados en esta sala.");
            return;
        }
        System.out.println("\n--- MAPA DE ASIENTOS ---");
        for (int i = 0; i < this.filas; i++) {
            char letraFila = (char) ('A' + i);
            for (int j = 1; j <= this.asientosPorFila; j++) {
                String idAsiento = letraFila + String.valueOf(j);
                Asiento asiento = this.asientos.get(idAsiento);
                if (asiento != null) {
                    if (asiento.getEstadoAsiento().equals("DISPONIBLE")) {
                        System.out.print(String.format("[%3s] ", idAsiento));
                    } else {
                        System.out.print("[ XX ] ");
                    }
                }
            }
            System.out.println();
        }
    }
}
