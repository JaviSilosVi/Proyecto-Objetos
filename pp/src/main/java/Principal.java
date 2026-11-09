import java.util.ArrayList;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        Administrador admin = new Administrador();
        GestorArchivos gestor = new GestorArchivos();
        Scanner teclado = new Scanner(System.in);

        sistema.inicia();

        // Al iniciar el programa cargamos los datos desde el archivo
        try{
            gestor.cargarPeliculas(sistema);
            gestor.cargarInfraestructura(sistema);
            System.out.println("Base de datos e Infraestructura cargadas.");
    } catch(FormatoArchivoException e){
            System.out.println("\n[ERROR CRITICO DE ARRANQUE] " + e.getMessage());
            System.out.println("Por favor revisa que el archivo no haya sido modificado incorrectamente a mano.");
    }
        int opcionPrincipal = -1;

        // MENU principal
        while (opcionPrincipal != 0){
            try {
                System.out.println("\n=====> BIENVENIDO AL SISTEMA DE CINES <=====");
                System.out.println("1. Ingresar como Administrador");
                System.out.println("2. Ingresar como Cliente");
                System.out.println("0. Salir y Guardar");
                System.out.print("Elige una opcion: ");
                opcionPrincipal = teclado.nextInt();
                teclado.nextLine();
                switch (opcionPrincipal){
                    case 1:
                        System.out.println("Entrando a menu administrador...");
                        menuAdministrador(teclado, admin, sistema, gestor);
                        break;
                    case 2:
                        System.out.println("Entrando a menu cliente...");
                        menuCliente(teclado, sistema, gestor);
                        break;
                    case 0:
                        System.out.println("Guardando datos y Cerrando el sistema...");
                        gestor.guardarPeliculas(sistema.getCatalogoPeliculas());
                        gestor.guardarInfraestructura(sistema.getCines()); 
                        break;
                    default:
                        System.out.println("Opcion no reconocida. Intenta de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("\n[ERROR] Entrada invalida. Por favor ingresa un numero valido.");
                teclado.nextLine(); 
                opcionPrincipal = -1; 
            }
        }
        teclado.close();
    }

       // El menu del administrador
    private static void menuAdministrador(Scanner teclado, Administrador admin, Sistema sistema, GestorArchivos gestor) {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- MENU ADMINISTRADOR ---");
            System.out.println("1. Gestionar Cines");
            System.out.println("2. Gestionar Salas");
            System.out.println("3. Gestionar Funciones");
            System.out.println("4. Gestionar Peliculas");
            System.out.println("0. Regresar al menu principal");
            System.out.print("Elige una opcion: ");

            try {
                opcion = teclado.nextInt();
                teclado.nextLine(); // Limpiar el buffer

                switch (opcion){
                    case 1:
                        menuGestionarCines(teclado, admin, sistema, gestor);
                        break;
                    case 2:
                        menuGestionarSalas(teclado, admin, sistema, gestor);
                        break;
                    case 3:
                        menuGestionarFunciones(teclado, admin, sistema, gestor);
                        break;
                    case 4:
                        menuGestionarPeliculas(teclado, admin, sistema, gestor);
                        break;
                    case 0:
                        System.out.println("Regresando...");
                        break;
                    default:
                        System.out.println("Opcion no valida.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Error: Por favor ingresa un número válido (no letras).");
                teclado.nextLine(); // Limpiar la basura del teclado para que no se cicle
            }
        }
    }
    
private static void menuGestionarCines(Scanner teclado, Administrador admin, Sistema sistema, GestorArchivos gestor) {
        int opcion = -1;
        while(opcion != 0) {
            System.out.println("\n--- GESTIONAR CINES ---");
            System.out.println("1. Agregar Cine");
            System.out.println("2. Modificar Cine");
            System.out.println("3. Eliminar Cine");
            System.out.println("0. Regresar");
            System.out.print("Elige una opcion: ");
            opcion = teclado.nextInt();
            teclado.nextLine();
            
            switch(opcion) {
                case 1: 
                System.out.print("Nombre del nuevo Cine: ");
                String nombreCine = teclado.nextLine();
                sistema.agregarCine(new Cine(nombreCine));
                gestor.guardarInfraestructura(sistema.getCines());
                System.out.println("Cine agregado correctamente");
            break;
                case 2: 
                System.out.print("Nombre del Cine actual: ");
                String nombreActual = teclado.nextLine();
                System.out.print("Nuevo nombre del Cine: ");
                String nombreNuevo = teclado.nextLine();
                if (sistema.modificarCine(nombreActual, new Cine(nombreNuevo))) {
                    gestor.guardarInfraestructura(sistema.getCines());
                    System.out.println("Cine modificado exitosamente.");
                } else {
                    System.out.println("Error: No se encontro el cine.");
                }
            break;
                case 3:
                    System.out.print("Nombre del Cine a eliminar: ");
                    String nombreEliminar = teclado.nextLine();
                    if (sistema.eliminarCine(nombreEliminar)) {
                        gestor.guardarInfraestructura(sistema.getCines());
                        System.out.println("Cine eliminado exitosamente.");
                    } else {
                        System.out.println("Error: No se encontro el cine.");
                    }
            break;
                case 0: 
            break;
                default: System.out.println("Opcion no valida.");
            }
        }
    }

    private static void menuGestionarSalas(Scanner teclado, Administrador admin, Sistema sistema, GestorArchivos gestor) {
        int opcion = -1;
        while(opcion != 0) {
            System.out.println("\n--- GESTIONAR SALAS ---");
            System.out.println("1. Agregar Sala");
            System.out.println("2. Modificar Sala");
            System.out.println("3. Eliminar Sala");
            System.out.println("0. Regresar");
            System.out.print("Elige una opcion: ");
            opcion = teclado.nextInt();
            teclado.nextLine();
            
            Cine cineSeleccionado = null; 

            if (opcion >= 1 && opcion <= 3) {
                System.out.print("ID/Nombre del Cine en especifico: ");
                String idCine = teclado.nextLine();
                cineSeleccionado = sistema.obtenerCine(idCine);
                if (cineSeleccionado == null) {
                System.out.println("Error: El cine no existe.");
                continue;
                }
            }

            switch(opcion) {
                case 1: 
                System.out.print("ID de la nueva Sala: ");
                String idSala = teclado.nextLine();
                Sala nuevaSala = new Sala(idSala);
                cineSeleccionado.agregarSala(idSala, nuevaSala);
                System.out.println("Sala agregada exitosamente al cine.");

                System.out.println("--- GENERACION DE ASIENTOS ---");
                System.out.print("Cantidad de filas (ej. 10): ");
                int filas = teclado.nextInt();
                System.out.print("Asientos por fila (ej. 10): ");
                int asientosPorFila = teclado.nextInt();
                teclado.nextLine();
                System.out.print("Tipo de asiento (NORMAL, PREMIUM, VIP): ");
                String tipoAsiento = teclado.nextLine();
                System.out.print("Precio del asiento: ");
                double precio = teclado.nextDouble();
                teclado.nextLine();
                
                
            
                nuevaSala.generarAsientos(filas, asientosPorFila, tipoAsiento, precio);
                gestor.guardarInfraestructura(sistema.getCines());
                System.out.println("Asientos generados correctamente para la sala.");
            break;
                case 2: 
                System.out.print("ID de la Sala actual: ");
                String idSalaActual = teclado.nextLine();
                System.out.print("Nuevo ID de la Sala: ");
                String nuevoIdSala = teclado.nextLine();
                if (cineSeleccionado.modificarSala(idSalaActual, nuevoIdSala, new Sala(nuevoIdSala))) {
                    gestor.guardarInfraestructura(sistema.getCines());
                    System.out.println("Sala modificada exitosamente.");
                } else {
                    System.out.println("Error: No se encontro la sala en ese cine.");
                }
            break;
                case 3:
                System.out.print("ID de la Sala a eliminar: ");
                String idSalaEliminar = teclado.nextLine();
                if (cineSeleccionado.eliminarSala(idSalaEliminar)) {
                    gestor.guardarInfraestructura(sistema.getCines());
                    System.out.println("Sala eliminada exitosamente.");
                } else {
                    System.out.println(" No se encontro la sala en ese cine.");
                }
            break;
                case 0: 
            break;
                default: System.out.println("Opcion no valida.");
            }
        }
    }

    private static void menuGestionarFunciones(Scanner teclado, Administrador admin, Sistema sistema, GestorArchivos gestor) {
        int opcion = -1;
        while(opcion != 0) {
            System.out.println("\n--- GESTIONAR FUNCIONES ---");
            System.out.println("1. Agregar Funcion");
            System.out.println("2. Modificar Funcion");
            System.out.println("3. Eliminar Funcion");
            System.out.println("0. Regresar");
            System.out.print("Elige una opcion: ");
            opcion = teclado.nextInt();
            teclado.nextLine();
            String idCine = ""; 
            String idSala = "";

            if (opcion == 2 || opcion == 3) {
                System.out.print("ID/Nombre del Cine en especifico: ");
                idCine = teclado.nextLine();
                System.out.print("ID de la Sala en especifico: ");
                idSala = teclado.nextLine();
                // Logica respectiva de modificar/eliminar usando idCine y idSala
            }

            switch(opcion) {
                case 1:
                    System.out.println("PROGRAMAR FUNCION ");
                    System.out.print("ID del Cine: ");
                    String idCineProg = teclado.nextLine();
                    System.out.print("ID de la Sala: ");
                    String idSalaProg = teclado.nextLine();
                    System.out.print("Fecha: ");
                    String fecha = teclado.nextLine();
                    System.out.print("Hora: ");
                    int hora = teclado.nextInt();
                    System.out.print("Minutos: ");
                    int minutos = teclado.nextInt();
                    teclado.nextLine(); 

                    System.out.print("Titulo de la pelicula a proyectar: ");
                    String tituloPeli = teclado.nextLine();
                    Pelicula peliAProyectar = admin.obtenerPelicula(tituloPeli, sistema);

                    if (peliAProyectar != null) {
                        Funcion nuevaFuncion = new Funcion(fecha, hora, minutos, peliAProyectar);
                        try {
                            admin.programarFuncion(idCineProg, idSalaProg, nuevaFuncion, sistema);
                            gestor.guardarInfraestructura(sistema.getCines());
                            System.out.println("Funcion programada con exito.");
                        } catch (ConflictoHorarioException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("La pelicula no existe en el catalogo");
                    }
                    break;
                case 2: 
                System.out.print("Fecha de la funcion actual: ");
                    String fechaAct = teclado.nextLine();
                    System.out.print("Hora de la funcion actual: ");
                    int horaAct = teclado.nextInt();
                    teclado.nextLine();

                    System.out.print("Nueva Fecha: ");
                    String fechaNueva = teclado.nextLine();
                    System.out.print("Nueva Hora: ");
                    int horaNueva = teclado.nextInt();
                    System.out.print("Nuevos Minutos: ");
                    int minNuevo = teclado.nextInt();
                    teclado.nextLine();
                    System.out.print("Titulo de la nueva pelicula a proyectar: ");
                    String tituloPeliMod = teclado.nextLine();
                    Pelicula peliMod = admin.obtenerPelicula(tituloPeliMod, sistema);
                    

                    if (peliMod != null) {
                        Funcion funcionNueva = new Funcion(fechaNueva, horaNueva, minNuevo, peliMod);
                        if (sistema.modificarFuncion(idCine, idSala, fechaAct, horaAct, funcionNueva)) {
                            gestor.guardarInfraestructura(sistema.getCines());
                            System.out.println("Funcion modificada exitosamente.");
                        } else {
                            System.out.println("No se pudo modificar (Cine, Sala o funcion actual no encontrados).");
                        }
                    } else {
                        System.out.println("La pelicula no existe.");
                    }
            break;
                case 3: 
                System.out.print("Fecha de la funcion a eliminar: ");
                String fechaElim = teclado.nextLine();
                System.out.print("Hora de la funcion a eliminar: ");
                int horaElim = teclado.nextInt();
                teclado.nextLine();
                if (sistema.eliminarFuncion(idCine, idSala, fechaElim, horaElim)) {
                    gestor.guardarInfraestructura(sistema.getCines());
                    System.out.println("Funcion eliminada exitosamente.");
                } else {
                    System.out.println("No se pudo eliminar (Cine, Sala o funcion no encontrados).");
                }

            break;
                case 0: 
            break;
                default: System.out.println("Opcion no valida.");
            }
        }
    }
    
    private static void menuGestionarPeliculas(Scanner teclado, Administrador admin, Sistema sistema, GestorArchivos gestor) {
        int opcion = -1;
        while(opcion != 0) {
            System.out.println("\n--- GESTIONAR PELICULAS ---");
            System.out.println("1. Agregar Pelicula");
            System.out.println("2. Modificar Pelicula");
            System.out.println("3. Eliminar Pelicula");
            System.out.println("0. Regresar");
            System.out.print("Elige una opcion: ");
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch(opcion) {
                case 1:
                    System.out.println("REGISTRO DE PELICULA");
                    System.out.print("Titulo: ");
                    String titulo = teclado.nextLine();
                    System.out.print("Duración : ");
                    String duracion = teclado.nextLine();
                    System.out.print("Clasificación : ");
                    String clasificacion = teclado.nextLine();
                    System.out.print("Género: ");
                    String genero = teclado.nextLine();
                    System.out.print("Director: ");
                    String director = teclado.nextLine();
                    System.out.print("Sinopsis: ");
                    String sinopsis = teclado.nextLine();

                    ArrayList<String> actores = new ArrayList<>();
                    System.out.print("Actor principal: ");
                    actores.add(teclado.nextLine());

                    Pelicula nuevaPeli = new Pelicula(titulo, duracion, clasificacion, genero, director, actores, sinopsis);
                    admin.registraPelicula(nuevaPeli, sistema);
                    gestor.guardarPeliculas(sistema.getCatalogoPeliculas());
                    System.out.println("Pelicula registrada exitosamente en el catalogo.");
                    break;
                case 2: 
                    System.out.print("Titulo actual de la pelicula a modificar: ");
                    String tituloAct = teclado.nextLine();
                    System.out.println("Ingresa los nuevos datos:");
                    System.out.print("Nuevo Titulo: ");
                    String tituloN = teclado.nextLine();
                    System.out.print("Nueva Duración: ");
                    String duracionN = teclado.nextLine();
                    System.out.print("Nueva Clasificación: ");
                    String clasificacionN = teclado.nextLine();
                    System.out.print("Nuevo Género: ");
                    String generoN = teclado.nextLine();
                    System.out.print("Nuevo Director: ");
                    String directorN = teclado.nextLine();
                    System.out.print("Nueva Sinopsis: ");
                    String sinopsisN = teclado.nextLine();

                    ArrayList<String> actoresN = new ArrayList<>();
                    System.out.print("Nuevo Actor principal: ");
                    actoresN.add(teclado.nextLine());

                    Pelicula peliMod = new Pelicula(tituloN, duracionN, clasificacionN, generoN, directorN, actoresN, sinopsisN);
                    try {
                        admin.modificaPelicula(tituloAct, peliMod, sistema);
                        gestor.guardarPeliculas(sistema.getCatalogoPeliculas());
                        System.out.println("Pelicula modificada exitosamente.");
                    } catch(EntidadNoEncontradaException e) {
                        System.out.println("Error" + e.getMessage());
                    }
            break;
                case 3: 
                System.out.print("Titulo de la pelicula a eliminar: ");
                String tituloElim = teclado.nextLine();
                    try {
                        admin.eliminarPelicula(tituloElim, sistema);
                        gestor.guardarPeliculas(sistema.getCatalogoPeliculas());
                        System.out.println("Pelicula eliminada exitosamente.");
                    } catch(EntidadNoEncontradaException e) {
                        System.out.println("Error " + e.getMessage());
                    }
            break;
                case 0: 
            break;
                default: System.out.println("Opcion no valida.");
            }
        }
    }

    
// El menu del cliente
    private static void menuCliente(Scanner teclado, Sistema sistema, GestorArchivos gestor) {
        int opcion = -1;
        while (opcion != 0) {
            try {
                System.out.println("\n--- MENU CLIENTE ---");
                System.out.println("1. Consultar Cartelera");
                System.out.println("2. Comprar Boleto");
                System.out.println("0. Regresar al menu principal");
                System.out.print("Elige una opcion: ");
                opcion = teclado.nextInt();
                teclado.nextLine();
                switch (opcion) {
                    case 1:
                        System.out.println("CARTELERA DISPONIBLE ");

                        System.out.println("\n--- CINES DISPONIBLES ---");
                        if (sistema.getCines().isEmpty()) {
                            System.out.println("No hay cines registrados en el sistema.");
                            break;
                        }
                        for (String c : sistema.getCines().keySet()) {
                            System.out.println("- " + c);
                        }
                        
                        System.out.print("¿A qué cine deseas ir?: ");
                        String nombreCine = teclado.nextLine();
                        Cine cineReal = sistema.obtenerCine(nombreCine);

                        if (cineReal == null) {
                            System.out.println("Error: El cine " + nombreCine + " no existe.");
                            break;
                        }

                        System.out.println("\n>> CARTELERA DEL CINE " + nombreCine.toUpperCase() + " <<");
                        boolean hayFunciones = false;
                        for (Sala s : cineReal.getSalas().values()) {
                            for (Funcion f : s.getProgramacion()) {
                                System.out.println("- Pelicula: " + f.getPeliculaProyecta().getTitulo() + 
                                                   " | Sala: " + s.getIdSala() + 
                                                   " | Horario: " + f.getHorarioHrs() + ":" + String.format("%02d", f.getHorarioMin()));
                                hayFunciones = true;
                            }
                        }
                        if (!hayFunciones) {
                            System.out.println("No hay funciones programadas en este cine actualmente.");
                            break;
                        }
                        System.out.println("-----------------------------------");
                

                        System.out.print("Ingresa tu nombre: ");
                        String nombreCliente = teclado.nextLine();
                        Cliente clienteActual = new Cliente(nombreCliente);
                        System.out.print("Cual pelicula deseas ver? ");
                        String tituloElegido = teclado.nextLine();
                        Pelicula peliElegida = sistema.obtenerPelicula(tituloElegido);
                        if (peliElegida != null) {
                        
                            
                            if(cineReal != null){
                                System.out.print("ID de la Sala: ");
                                String salaElegida = teclado.nextLine();
                                Sala salaReal = cineReal.obtenerSala(salaElegida);
                                
                                if(salaReal != null){
                                    // Buscar funciones programadas para la película elegida en esta sala
                                    ArrayList<Funcion> funcionesProgramadas = new ArrayList<>();
                                    for (Funcion f : salaReal.getProgramacion()) {
                                        if (f.getPeliculaProyecta().getTitulo().equalsIgnoreCase(peliElegida.getTitulo())) {
                                            funcionesProgramadas.add(f);
                                        }
                                    }
                                    if (funcionesProgramadas.isEmpty()) {
                                        System.out.println("Error: No hay funciones programadas para '" + tituloElegido + "' en la " + salaElegida + " de " + nombreCine);
                                        break;
                                    }
                                    System.out.println("\nFunciones disponibles para esta pelicula en la sala:");
                                    for (int i = 0; i < funcionesProgramadas.size(); i++) {
                                        Funcion f = funcionesProgramadas.get(i);
                                        System.out.println((i + 1) + ". Fecha: " + f.getFecha() + " | Hora: " + f.getHorarioHrs() + ":" + f.getHorarioMin());
                                    }
                                    System.out.print("Elige el numero de la funcion: ");
                                    int numFuncion = teclado.nextInt();
                                    teclado.nextLine();
                                    if (numFuncion < 1 || numFuncion > funcionesProgramadas.size()) {
                                        System.out.println("Error: Seleccion de funcion invalida.");
                                        break;
                                    }
                                    Funcion funcionElegida = funcionesProgramadas.get(numFuncion - 1);
                                    // Mostrar asientos disponibles de esta función específica
                                    System.out.println("\nAsientos disponibles para esta funcion:");
                                    boolean algunDisponible = false;
                                    for (Asiento a : funcionElegida.getAsientos().values()) {
                                        if (a.getEstadoAsiento().equals("DISPONIBLE")) {
                                            System.out.print("[" + a.getIdentificador() + " (" + a.getTipoAsiento() + "): $" + a.getPrecio() + "] ");
                                            algunDisponible = true;
                                        }
                                    }
                                    System.out.println();
                                    if (!algunDisponible) {
                                        System.out.println("Lo sentimos, no hay asientos disponibles para esta funcion (Sala llena).");
                                        break;
                                    }
                                    System.out.print("ID del Asiento (ej. A1): ");
                                    String idAsiento = teclado.nextLine();
                                    Asiento asientoReal = funcionElegida.obtenerAsiento(idAsiento);
                                    if(asientoReal != null){
                                        System.out.println("\nSelecciona metodo de pago:");
                                        System.out.println("1. Tarjeta de Credito");
                                        System.out.println("2. PayPal");
                                        System.out.println("3. Transferencia Electronica");
                                        int opcPago = teclado.nextInt();
                                        teclado.nextLine(); 
                                        MetodoDePago metodo = null;
                                        switch (opcPago) {
                                            case 1:
                                                System.out.print("Numero de tarjeta: ");
                                                metodo = new TarjetaCredito(teclado.nextLine());
                                                break;
                                            case 2:
                                                System.out.print("Correo de PayPal: ");
                                                metodo = new Paypal(teclado.nextLine());
                                                break;
                                            case 3:
                                                System.out.print("Cuenta bancaria: ");
                                                metodo = new TransferenciaElectronica(teclado.nextInt());
                                                teclado.nextLine();
                                                break;
                                            default:
                                                System.out.println("Metodo no valido, operacion cancelada.");
                                        }
                                        
                                        if(metodo != null){
                                            ArrayList<Asiento> asientosComprar = new ArrayList<>();
                                            asientosComprar.add(asientoReal);
                                            try {
                                                Boleto boleto = clienteActual.comprarBoleto(funcionElegida, salaElegida, asientosComprar, metodo);
                                                if (boleto != null) {
                                                    boleto.imprimirBoleto();
                                                    sistema.registrarBoleto(boleto); // <--- Guardar boleto en el sistema!
                                                }
                                            }catch(AsientoOcupadoException e) {
                                                System.out.println("\n OPERACION CANCELADA: " + e.getMessage());
                                                System.out.println("Por favor, seleciona otro asiento");
                                            }  
                                        }
                                    }else {
                                        System.out.println("Error: El asiento " + idAsiento + " no existe o no esta disponible en esta funcion.");
                                    }
                                }else{
                                    System.out.println("Error: La sala: " + salaElegida + " ,no existe en el cine: " + nombreCine);
                                }
                            }else{
                                System.out.println("Error: El cine " + nombreCine + " no existe");
                            }
                        }else{
                            System.out.println("La pelicula no encontrada en la cartelera");
                        }
                        break;
                    case 0:
                        System.out.println("Regresando...");
                        break;
                    default:
                        System.out.println("Opcion no valida.");
                }
            } catch (Exception e) {
                System.out.println("\n[ERROR] Entrada o dato invalido en el menu de cliente. Volviendo a intentar...");
                teclado.nextLine(); // Limpiar el buffer
                opcion = -1; // Resetear la opcion
            }
        }
    }
}