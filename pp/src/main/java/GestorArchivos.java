import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Map;
import java.io.FileNotFoundException;

/**
 * Responsable de la persistencia de datos del sistema mediante archivos de texto.
 * Permite guardar y recuperar el estado de las películas y la infraestructura al abrir o cerrar el programa.
 * 
 * @author Alejandro Mora Tapia, Chino Campos Yeshua Ismael y Salazar Rioú Pablo de Jesús
 * @version 1.0
 */

public class GestorArchivos {
    
        /**
     * Escribe el catálogo completo de películas en un archivo de texto plano separado por comas (.txt).
     * 
     * @param catalogo El diccionario de películas a respaldar.
     */

    public void guardarPeliculas(Map<String, Pelicula> catalogo){
        try{
            //sobrescribimos el archivo completo utilizando un for-each
            FileWriter fw= new FileWriter("Peliculas.txt");
            PrintWriter pw= new PrintWriter(fw);

            for(Pelicula p : catalogo.values()){
                //Escibimos los datos separando por comas
                pw.println(p.getTitulo() + "," + p.getDuracion() + "," +
                           p.getClasificacion() + "," + p.getGenero() + ","
                           + p.getDirector() + "," + p.getSinopsis());
            }
            pw.close();
        }catch(Exception e){
            System.err.println("Error al guardar las peliculas: " + e.getMessage());
        }
    }

        /**
     * Lee el archivo de texto de películas y reconstruye los objetos en memoria.
     * 
     * @param sistemaActivo El sistema al que se le inyectarán las películas cargadas.
     * @throws FormatoArchivoException Si el archivo carece de campos obligatorios o está corrupto.
     */

    public void cargarPeliculas(Sistema sistemaActivo) throws FormatoArchivoException{
        try {
            File archivo = new File("peliculas.txt");
                if (!archivo.exists()) {
                    return;
                }

            Scanner lector = new Scanner(archivo);
            int numeroLine = 1;

            while (lector.hasNextLine()) {
                String linea = lector.nextLine();
                String[] datos = linea.split(","); // Separamos la linea con las comas
                
                if (datos.length < 6) {
                    lector.close();
                    throw new FormatoArchivoException("EL archivo peliculas.txt se encuentra corrupto en la linea " + numeroLine + "Falta datos o separadores");
                }
                Pelicula peli = new Pelicula(datos[0], datos[1], datos[2], datos[3], datos[4], new java.util.ArrayList<>(), datos[5]);
                sistemaActivo.agregarPelicula(peli);
            }
            lector.close();
        } catch (FileNotFoundException e) {
            System.err.println("No se encontro el archivo fisico");
        }
    }

        /**
     * Respalda la estructura jerárquica de Cines, Salas, Funciones y Boletos vendidos 
     * utilizando etiquetas de identificación en un archivo de texto.
     * 
     * @param cines El diccionario de cines activos en el sistema.
     */

    public void guardarInfraestructura(Map<String, Cine> cines) {
        try {
            FileWriter fw = new FileWriter("Infraestructura.txt");
            PrintWriter pw = new PrintWriter(fw);

            for (Cine cine : cines.values()) {
                // 1. Guardamos el Cine
                pw.println("CINE|" + cine.getNombreCine());

                for (Sala sala : cine.getSalas().values()) {
                    // 2. Guardamos la Sala con sus dimensiones
                    pw.println("SALA|" + cine.getNombreCine() + "|" + sala.getIdSala() + "|" + 
                            sala.getFilas() + "|" + sala.getAsientosPorFila() + "|" + 
                            sala.getTipoSala() + "|" + sala.getPrecioBase());

                    for (Funcion f : sala.getProgramacion()) {
                        // 3. Guardamos la Función
                        pw.println("FUNCION|" + cine.getNombreCine() + "|" + sala.getIdSala() + "|" + 
                            f.getFecha() + "|" + f.getHorarioHrs() + "|" + f.getHorarioMin() + "|" + 
                            f.getPeliculaProyecta().getTitulo());

                        // 4. Guardamos SOLO los asientos que ya fueron vendidos
                        for (Asiento a : f.getAsientos().values()) {
                            if (a.getEstadoAsiento().equals("VENDIDO")) {
                                pw.println("BOLETO|" + cine.getNombreCine() + "|" + sala.getIdSala() + "|" + 
                                f.getFecha() + "|" + f.getHorarioHrs() + "|" + f.getHorarioMin() + "|" + 
                                a.getIdentificador());
                            }
                        }
                    }
                }
            }
            pw.close();
        } catch(Exception e) {
            System.err.println("Error al guardar la infraestructura: " + e.getMessage());
        }
    }

        /**
     * Lee el archivo de infraestructura y reconstruye la jerarquía de Cines, Salas y programación, 
     * marcando además los asientos que ya fueron vendidos previamente.
     * 
     * @param sistema El sistema principal sobre el que se reconstruirán los datos.
     */

     public void cargarInfraestructura(Sistema sistema) {
        try {
            File archivo = new File("Infraestructura.txt");
            if (!archivo.exists()) return; // Si es la primera vez, no hace nada

            Scanner lector = new Scanner(archivo);
            while (lector.hasNextLine()) {
                String linea = lector.nextLine();
                String[] d = linea.split("\\|");
                String etiqueta = d[0];

                try {
                    if (etiqueta.equals("CINE")) {
                        sistema.agregarCine(new Cine(d[1]));
                    } 
                    else if (etiqueta.equals("SALA")) {
                        Cine c = sistema.obtenerCine(d[1]);
                        Sala s = new Sala(d[2]);
                        // Reconstruimos sus butacas base
                        s.generarAsientos(Integer.parseInt(d[3]), Integer.parseInt(d[4]), d[5], Double.parseDouble(d[6]));
                        c.agregarSala(d[2], s);
                    } 
                    else if (etiqueta.equals("FUNCION")) {
                        Cine c = sistema.obtenerCine(d[1]);
                        Sala s = c.obtenerSala(d[2]);
                        Pelicula p = sistema.obtenerPelicula(d[6]);
                        if(p != null && s != null){
                            Funcion f = new Funcion(d[3], Integer.parseInt(d[4]), Integer.parseInt(d[5]), p);
                            s.programarFuncion(f); // Al programarla, copia las butacas limpias
                        }
                    } 
                    else if (etiqueta.equals("BOLETO")) {
                        Cine c = sistema.obtenerCine(d[1]);
                        Sala s = c.obtenerSala(d[2]);
                        // Buscamos la función exacta y manchamos el asiento como VENDIDO
                        for(Funcion f : s.getProgramacion()) {
                            if(f.getFecha().equals(d[3]) && f.getHorarioHrs() == Integer.parseInt(d[4]) && f.getHorarioMin() == Integer.parseInt(d[5])) {
                                Asiento asientoComprado = f.obtenerAsiento(d[6]);
                                if(asientoComprado != null) asientoComprado.vender();
                            }
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Omitiendo línea corrupta de infraestructura...");
                }
            }
            lector.close();
        } catch (FileNotFoundException e) {
            System.err.println("No se encontró el archivo de infraestructura");
        }
    }
}