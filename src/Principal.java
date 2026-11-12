import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Principal {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        sistema.inicia();
        cargarDatosIniciales(sistema);

        int opcionPrincipal = 0;
        do {
            System.out.println("\n==================================================");
            System.out.println("          SISTEMA DE GESTION DE CINES             ");
            System.out.println("==================================================");
            System.out.println("1. Abrir Interfaz Grafica (GUI)");
            System.out.println("2. Iniciar Sesion como Administrador");
            System.out.println("3. Registrar nuevo Administrador");
            System.out.println("4. Entrar como Cliente");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opcion: ");

            String entrada = scanner.nextLine();
            try {
                opcionPrincipal = Integer.parseInt(entrada);
            } catch (Exception e) {
                opcionPrincipal = 0;
            }

            switch (opcionPrincipal) {
                case 1:
                    System.out.println("\n[GUI] Abriendo ventana de Login...");
                    VentanaLogin login = new VentanaLogin(sistema);
                    login.setVisible(true);
                    break;
                case 2:
                    menuLoginAdmin(sistema);
                    break;
                case 3:
                    registrarNuevoAdmin(sistema);
                    break;
                case 4:
                    menuCliente(sistema);
                    break;
                case 5:
                    System.out.println("\nSaliendo del programa. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opcion no valida. Intenta de nuevo.");
                    break;
            }
        } while (opcionPrincipal != 5 && opcionPrincipal != 1);
    }

    private static void cargarDatosIniciales(Sistema sistema) {
        // Administradores iniciales
        Administrador admin1 = new Administrador("Pablo", "1234");
        Administrador admin2 = new Administrador("Admin", "0000");
        sistema.registrarAdministrador(admin1);
        sistema.registrarAdministrador(admin2);

        List<String> actores1 = new ArrayList<>();
        actores1.add("Jack Black");
        actores1.add("Dustin Hoffman");
        Pelicula p1 = new Pelicula("Kung Fu Panda", "1:32", "A", "Animacion / Comedia", "Mark Osborne", actores1, "Po el panda sueña con ser maestro de kung fu.");

        List<String> actores2 = new ArrayList<>();
        actores2.add("Robert Downey Jr.");
        actores2.add("Gwyneth Paltrow");
        Pelicula p2 = new Pelicula("Iron Man 2", "2:04", "B", "Accion", "Jon Favreau", actores2, "Tony Stark se enfrenta a nuevas amenazas.");

        admin1.registraPelicula(p1, sistema);
        admin1.registraPelicula(p2, sistema);

        Cine cine1 = new Cine("Cinepolis Centro");
        Sala sala1 = new Sala("Sala 1");
        sala1.generarAsientos(3, 4, "TRADICIONAL", 80.0);
        cine1.agregaSala("Sala 1", sala1);
        sistema.agregaCine(cine1);

        Funcion f1 = new Funcion("2026-11-20", 18, 30, p1);
        try {
            sistema.programarFuncion("Cinepolis Centro", "Sala 1", f1);
        } catch (EntidadNoEncontradaException | ConflictoHorarioException e) {
            System.out.println("Aviso al inicializar: " + e.getMessage());
        }
    }

    private static void registrarNuevoAdmin(Sistema sistema) {
        System.out.println("\n--- REGISTRO DE NUEVO ADMINISTRADOR ---");
        System.out.print("Ingresa tu nombre de usuario: ");
        String nombre = scanner.nextLine();
        if (nombre.trim().isEmpty()) {
            System.out.println("El nombre no puede estar vacio.");
            return;
        }

        if (sistema.getAdministradores().containsKey(nombre.trim().toLowerCase())) {
            System.out.println("Error: Ya existe un administrador registrado con ese nombre.");
            return;
        }

        System.out.print("Crea tu PIN secreto de acceso (minimo 4 digitos): ");
        String pin = scanner.nextLine();
        try {
            Administrador nuevo = new Administrador(nombre.trim(), pin.trim());
            nuevo.validarPin(pin.trim()); // Valida formato defensivo
            sistema.registrarAdministrador(nuevo);
            System.out.println("¡Administrador '" + nombre + "' registrado con exito! Ya puedes iniciar sesion con tu PIN.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error de registro: " + e.getMessage());
        }
    }

    private static void menuLoginAdmin(Sistema sistema) {
        System.out.println("\n--- ACCESO DE ADMINISTRADOR ---");
        System.out.print("Nombre de usuario Administrador: ");
        String nombre = scanner.nextLine();
        System.out.print("PIN secreto: ");
        String pin = scanner.nextLine();

        Administrador adminLogueado = sistema.autenticarAdmin(nombre, pin);
        if (adminLogueado == null) {
            System.out.println("Error: Credenciales incorrectas (Usuario inexistente o PIN invalido).");
            return;
        }

        System.out.println("\n¡Bienvenido al panel, Administrador " + adminLogueado.getNombre() + "!");
        menuAdministrador(sistema, adminLogueado);
    }

    private static void menuAdministrador(Sistema sistema, Administrador admin) {
        int opcion = 0;
        do {
            System.out.println("\n========================================");
            System.out.println("     PANEL DE ADMINISTRACION (" + admin.getNombre() + ")");
            System.out.println("========================================");
            System.out.println("1. Gestionar Cines");
            System.out.println("2. Gestionar Salas");
            System.out.println("3. Gestionar Peliculas");
            System.out.println("4. Gestionar Funciones");
            System.out.println("5. Ver Administradores registrados");
            System.out.println("6. Cerrar Sesion de Administrador");
            System.out.print("Elige una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    menuGestionarCines(sistema);
                    break;
                case 2:
                    menuGestionarSalas(sistema);
                    break;
                case 3:
                    menuGestionarPeliculas(sistema);
                    break;
                case 4:
                    menuGestionarFunciones(sistema);
                    break;
                case 5:
                    System.out.println("\n=== ADMINISTRADORES REGISTRADOS ===");
                    for (Administrador a : sistema.getAdministradores().values()) {
                        System.out.println("• " + a.getNombre());
                    }
                    break;
                case 6:
                    System.out.println("Cerrando sesion de administrador...");
                    break;
                default:
                    System.out.println("Opcion no valida.");
                    break;
            }
        } while (opcion != 6);
    }

    private static void menuGestionarCines(Sistema sistema) {
        int op = 0;
        do {
            System.out.println("\n--- GESTION DE CINES ---");
            System.out.println("1. Registrar nuevo Cine");
            System.out.println("2. Listar Cines registrados");
            System.out.println("3. Eliminar Cine");
            System.out.println("4. Volver");
            System.out.print("Opcion: ");

            try {
                op = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                op = 0;
            }

            switch (op) {
                case 1:
                    System.out.print("Nombre del nuevo cine: ");
                    String nombre = scanner.nextLine();
                    try {
                        sistema.obtenerCine(nombre);
                        System.out.println("Ese cine ya se encuentra registrado.");
                    } catch (EntidadNoEncontradaException ex) {
                        sistema.agregaCine(new Cine(nombre));
                        System.out.println("¡Cine '" + nombre + "' registrado con exito en el mapa!");
                    }
                    break;
                case 2:
                    System.out.println("\n=== CINES REGISTRADOS ===");
                    if (sistema.getCines().isEmpty()) {
                        System.out.println("No hay cines registrados.");
                    } else {
                        for (Cine c : sistema.getCines().values()) {
                            System.out.println("• " + c.getNombreCine() + " (Total de salas: " + c.getSalas().size() + ")");
                        }
                    }
                    break;
                case 3:
                    System.out.print("Nombre del cine a eliminar: ");
                    String cineEliminar = scanner.nextLine();
                    try {
                        sistema.eliminarCine(cineEliminar);
                        System.out.println("Cine eliminado correctamente.");
                    } catch (EntidadNoEncontradaException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (op != 4);
    }

    private static void menuGestionarSalas(Sistema sistema) {
        System.out.println("\n--- GESTION DE SALAS ---");
        if (sistema.getCines().isEmpty()) {
            System.out.println("Primero debes registrar al menos un cine.");
            return;
        }

        System.out.println("Cines disponibles:");
        List<Cine> listaCines = new ArrayList<>(sistema.getCines().values());
        for (int i = 0; i < listaCines.size(); i++) {
            System.out.println((i + 1) + ". " + listaCines.get(i).getNombreCine() + " (Salas actuales: " + listaCines.get(i).getSalas().size() + ")");
        }
        System.out.print("Selecciona el numero o escribe el nombre del cine: ");
        String entradaCine = scanner.nextLine().trim();

        Cine cine = null;
        try {
            int numCine = Integer.parseInt(entradaCine);
            if (numCine >= 1 && numCine <= listaCines.size()) {
                cine = listaCines.get(numCine - 1);
            }
        } catch (NumberFormatException e) {
            // No es numero, buscar por nombre
        }

        if (cine == null) {
            try {
                cine = sistema.obtenerCine(entradaCine);
            } catch (EntidadNoEncontradaException e) {
                System.out.println("Error: " + e.getMessage());
                return;
            }
        }

        int op = 0;
        do {
            System.out.println("\n========================================");
            System.out.println("CINE SELECCIONADO: " + cine.getNombreCine().toUpperCase());
            System.out.println("========================================");
            System.out.println("1. Agregar nueva Sala");
            System.out.println("2. Listar Salas de este cine");
            System.out.println("3. Volver");
            System.out.print("Opcion: ");

            try {
                op = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                op = 0;
            }

            switch (op) {
                case 1:
                    System.out.print("Identificador de la sala (ej. Sala 2): ");
                    String idSala = scanner.nextLine();
                    System.out.print("Numero de filas: ");
                    int filas = Integer.parseInt(scanner.nextLine());
                    System.out.print("Asientos por fila: ");
                    int asientosFila = Integer.parseInt(scanner.nextLine());
                    System.out.print("Precio base del boleto: $");
                    double precio = Double.parseDouble(scanner.nextLine());

                    Sala nuevaSala = new Sala(idSala);
                    nuevaSala.generarAsientos(filas, asientosFila, "TRADICIONAL", precio);
                    cine.agregaSala(idSala, nuevaSala);
                    System.out.println("¡Sala '" + idSala + "' agregada con exito en " + cine.getNombreCine() + " con " + (filas * asientosFila) + " asientos!");
                    break;
                case 2:
                    System.out.println("\n=== SALAS EN " + cine.getNombreCine().toUpperCase() + " ===");
                    if (cine.getSalas().isEmpty()) {
                        System.out.println("No hay salas registradas en este cine.");
                    } else {
                        for (Sala s : cine.getSalas().values()) {
                            System.out.println("• " + s.getIdSala() + " - Tipo: " + s.getTipoSala() + " - Asientos totales: " + s.getTotalAsientos() + " - Precio: $" + s.getPrecioBase());
                        }
                    }
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (op != 3);
    }

    private static void menuGestionarPeliculas(Sistema sistema) {
        int op = 0;
        do {
            System.out.println("\nGESTION DE PELICULAS ");
            System.out.println("1. Agregar Pelicula al catalogo");
            System.out.println("2. Ver catalogo completo");
            System.out.println("3. Eliminar Pelicula");
            System.out.println("4. Volver");
            System.out.print("Opcion: ");

            try {
                op = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                op = 0;
            }

            switch (op) {
                case 1:
                    System.out.print("Titulo: ");
                    String tit = scanner.nextLine();
                    System.out.print("Duracion: ");
                    String dur = scanner.nextLine();
                    System.out.print("Clasificacion (A, B, C): ");
                    String cla = scanner.nextLine();
                    System.out.print("Genero: ");
                    String gen = scanner.nextLine();
                    System.out.print("Director: ");
                    String dir = scanner.nextLine();

                    Pelicula p = new Pelicula(tit, dur, cla, gen, dir, new ArrayList<>(), "Sinopsis pendiente");
                    sistema.agregarPelicula(p);
                    System.out.println("Pelicula '" + tit + "' agregada al catalogo (HashMap)!");
                    break;
                case 2:
                    System.out.println("\n=== CATALOGO DE PELICULAS ===");
                    if (sistema.getCatalogoPeliculas().isEmpty()) {
                        System.out.println("No hay peliculas en el catalogo.");
                    } else {
                        for (Pelicula pel : sistema.getCatalogoPeliculas().values()) {
                            System.out.println("• " + pel.getTitulo() + " (" + pel.getClasificacion() + ") - " + pel.getDuracion() + " - " + pel.getGenero());
                        }
                    }
                    break;
                case 3:
                    System.out.print("Titulo de la pelicula a eliminar: ");
                    String tituloElim = scanner.nextLine();
                    try {
                        sistema.eliminarPelicula(tituloElim);
                        System.out.println("Pelicula eliminada del catalogo.");
                    } catch (EntidadNoEncontradaException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (op != 4);
    }

    private static void menuGestionarFunciones(Sistema sistema) {
        int op = 0;
        do {
            System.out.println("\n--- GESTION DE FUNCIONES ---");
            System.out.println("1. Programar nueva Funcion");
            System.out.println("2. Ver funciones programadas por Cine");
            System.out.println("3. Volver");
            System.out.print("Opcion: ");

            try {
                op = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                op = 0;
            }

            switch (op) {
                case 1:
                    if (sistema.getCines().isEmpty()) {
                        System.out.println("No hay cines registrados. Registra un cine primero.");
                        break;
                    }
                    if (sistema.getCatalogoPeliculas().isEmpty()) {
                        System.out.println("No hay peliculas en catalogo. Registra una pelicula primero.");
                        break;
                    }

                    // Paso 1: Seleccionar Cine
                    System.out.println("\n--- PASO 1: SELECCIONA EL CINE ---");
                    List<Cine> cinesDisponibles = new ArrayList<>(sistema.getCines().values());
                    for (int i = 0; i < cinesDisponibles.size(); i++) {
                        System.out.println((i + 1) + ". " + cinesDisponibles.get(i).getNombreCine() + " (Salas: " + cinesDisponibles.get(i).getSalas().size() + ")");
                    }
                    System.out.print("Numero de cine (o nombre): ");
                    String entradaCine = scanner.nextLine().trim();
                    Cine cineElegido = null;
                    try {
                        int idxC = Integer.parseInt(entradaCine);
                        if (idxC >= 1 && idxC <= cinesDisponibles.size()) {
                            cineElegido = cinesDisponibles.get(idxC - 1);
                        }
                    } catch (NumberFormatException e) {}

                    if (cineElegido == null) {
                        try {
                            cineElegido = sistema.obtenerCine(entradaCine);
                        } catch (EntidadNoEncontradaException ex) {
                            System.out.println("Error: " + ex.getMessage());
                            break;
                        }
                    }

                    // Paso 2: Seleccionar Sala de ese Cine
                    if (cineElegido.getSalas().isEmpty()) {
                        System.out.println("El cine '" + cineElegido.getNombreCine() + "' no tiene salas registradas. Agrega una sala primero.");
                        break;
                    }
                    System.out.println("\n--- PASO 2: SELECCIONA LA SALA EN " + cineElegido.getNombreCine().toUpperCase() + " ---");
                    List<Sala> salasDisponibles = new ArrayList<>(cineElegido.getSalas().values());
                    for (int i = 0; i < salasDisponibles.size(); i++) {
                        Sala sl = salasDisponibles.get(i);
                        System.out.println((i + 1) + ". " + sl.getIdSala() + " (" + sl.getTipoSala() + " - " + sl.getTotalAsientos() + " asientos - $" + sl.getPrecioBase() + ")");
                    }
                    System.out.print("Numero de sala (o ID): ");
                    String entradaSala = scanner.nextLine().trim();
                    Sala salaElegida = null;
                    try {
                        int idxS = Integer.parseInt(entradaSala);
                        if (idxS >= 1 && idxS <= salasDisponibles.size()) {
                            salaElegida = salasDisponibles.get(idxS - 1);
                        }
                    } catch (NumberFormatException e) {}

                    if (salaElegida == null) {
                        try {
                            salaElegida = cineElegido.obtenerSala(entradaSala);
                        } catch (EntidadNoEncontradaException ex) {
                            System.out.println("Error: " + ex.getMessage());
                            break;
                        }
                    }

                    // Paso 3: Seleccionar Película del Catálogo
                    System.out.println("\n--- PASO 3: SELECCIONA LA PELICULA A PROYECTAR ---");
                    List<Pelicula> pelisDisponibles = new ArrayList<>(sistema.getCatalogoPeliculas().values());
                    for (int i = 0; i < pelisDisponibles.size(); i++) {
                        Pelicula pl = pelisDisponibles.get(i);
                        System.out.println((i + 1) + ". " + pl.getTitulo() + " (" + pl.getClasificacion() + " - " + pl.getGenero() + " [" + pl.getDuracion() + "])");
                    }
                    System.out.print("Numero de pelicula (o titulo): ");
                    String entradaPeli = scanner.nextLine().trim();
                    Pelicula peliElegida = null;
                    try {
                        int idxP = Integer.parseInt(entradaPeli);
                        if (idxP >= 1 && idxP <= pelisDisponibles.size()) {
                            peliElegida = pelisDisponibles.get(idxP - 1);
                        }
                    } catch (NumberFormatException e) {}

                    if (peliElegida == null) {
                        try {
                            peliElegida = sistema.obtenerPelicula(entradaPeli);
                        } catch (EntidadNoEncontradaException ex) {
                            System.out.println("Error: " + ex.getMessage());
                            break;
                        }
                    }

                    // Paso 4: Horario
                    System.out.print("Fecha de proyeccion (AAAA-MM-DD): ");
                    String fecha = scanner.nextLine();
                    System.out.print("Hora de inicio (0-23): ");
                    int hrs = Integer.parseInt(scanner.nextLine());
                    System.out.print("Minutos de inicio (0-59): ");
                    int min = Integer.parseInt(scanner.nextLine());

                    try {
                        Funcion nuevaFuncion = new Funcion(fecha, hrs, min, peliElegida);
                        salaElegida.programarFuncion(nuevaFuncion);
                        System.out.println("¡Funcion programada exitosamente!");
                        System.out.println("  • Cine: " + cineElegido.getNombreCine());
                        System.out.println("  • Sala: " + salaElegida.getIdSala());
                        System.out.println("  • Pelicula: " + peliElegida.getTitulo());
                        System.out.println("  • Horario: " + fecha + " a las " + hrs + ":" + (min < 10 ? "0" + min : min) + " hrs.");
                    } catch (ConflictoHorarioException ex) {
                        System.out.println("Error de horario: " + ex.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("\n=== CONSULTA DE FUNCIONES POR CINE ===");
                    if (sistema.getCines().isEmpty()) {
                        System.out.println("No hay cines registrados.");
                        break;
                    }
                    for (Cine c : sistema.getCines().values()) {
                        System.out.println("\n========================================");
                        System.out.println("CINE: " + c.getNombreCine().toUpperCase());
                        System.out.println("========================================");
                        if (c.getSalas().isEmpty()) {
                            System.out.println("  (Sin salas registradas)");
                        } else {
                            for (Sala sala : c.getSalas().values()) {
                                System.out.println("  • " + sala.getIdSala() + " (" + sala.getTipoSala() + "):");
                                if (sala.getProgramacion().isEmpty()) {
                                    System.out.println("      - No hay funciones programadas en esta sala.");
                                } else {
                                    for (Funcion func : sala.getProgramacion()) {
                                        System.out.println("      - Pelicula: " + func.getPeliculaProyectada().getTitulo() + 
                                            " | Fecha: " + func.getFecha() + 
                                            " | Hora: " + func.getHorarioHrs() + ":" + (func.getHorarioMin() < 10 ? "0" + func.getHorarioMin() : func.getHorarioMin()));
                                    }
                                }
                            }
                        }
                    }
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (op != 3);
    }

    private static void menuCliente(Sistema sistema) {
        System.out.print("\nIngresa tu nombre: ");
        String nombre = scanner.nextLine();
        Cliente cliente = new Cliente(nombre);
        System.out.println("¡Bienvenido/a, " + cliente.getNombre() + "!");

        int op = 0;
        do {
            System.out.println("\n--- MENU CLIENTE ---");
            System.out.println("1. Ver cartelera");
            System.out.println("2. Comprar boleto");
            System.out.println("3. Volver al menu principal");
            System.out.print("Opcion: ");

            try {
                op = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                op = 0;
            }

            switch (op) {
                case 1:
                    System.out.println("\n=== CARTELERA DISPONIBLE ===");
                    if (sistema.getCatalogoPeliculas().isEmpty()) {
                        System.out.println("No hay peliculas en cartelera.");
                    } else {
                        for (Pelicula p : sistema.getCatalogoPeliculas().values()) {
                            System.out.println("• " + p.getTitulo() + " (" + p.getClasificacion() + ") - " + p.getGenero() + " [" + p.getDuracion() + "]");
                        }
                    }
                    break;
                case 2:
                    System.out.println("\n==================================================");
                    System.out.println("            COMPRA DE BOLETOS - CARTELERA         ");
                    System.out.println("==================================================");

                    // Recolectar todas las funciones activas con su contexto (cine y sala)
                    class OpcionFuncion {
                        Cine cine;
                        Sala sala;
                        Funcion funcion;
                        OpcionFuncion(Cine c, Sala s, Funcion f) {
                            this.cine = c;
                            this.sala = s;
                            this.funcion = f;
                        }
                    }

                    List<OpcionFuncion> funcionesDisponibles = new ArrayList<>();
                    for (Cine c : sistema.getCines().values()) {
                        for (Sala s : c.getSalas().values()) {
                            for (Funcion f : s.getProgramacion()) {
                                funcionesDisponibles.add(new OpcionFuncion(c, s, f));
                            }
                        }
                    }

                    if (funcionesDisponibles.isEmpty()) {
                        System.out.println("Lo sentimos, en este momento no hay funciones programadas en ningun cine.");
                        break;
                    }

                    System.out.println("Funciones disponibles en cartelera:");
                    for (int i = 0; i < funcionesDisponibles.size(); i++) {
                        OpcionFuncion opc = funcionesDisponibles.get(i);
                        Pelicula pel = opc.funcion.getPeliculaProyectada();
                        System.out.println((i + 1) + ". Pelicula: " + pel.getTitulo() + 
                            " (" + pel.getClasificacion() + " - " + pel.getGenero() + ")" +
                            "\n    Cine: " + opc.cine.getNombreCine() + " | Sala: " + opc.sala.getIdSala() + " (" + opc.sala.getTipoSala() + ")" +
                            "\n    Fecha: " + opc.funcion.getFecha() + " a las " + opc.funcion.getHorarioHrs() + ":" + 
                            (opc.funcion.getHorarioMin() < 10 ? "0" + opc.funcion.getHorarioMin() : opc.funcion.getHorarioMin()) + " hrs." +
                            "\n    Precio base por asiento: $" + opc.sala.getPrecioBase());
                        System.out.println("   ------------------------------------------------");
                    }

                    System.out.print("Elige el numero de la funcion que deseas ver: ");
                    int numF;
                    try {
                        numF = Integer.parseInt(scanner.nextLine());
                    } catch (Exception e) {
                        numF = 0;
                    }

                    if (numF < 1 || numF > funcionesDisponibles.size()) {
                        System.out.println("Opcion de funcion no valida.");
                        break;
                    }

                    OpcionFuncion seleccionada = funcionesDisponibles.get(numF - 1);
                    Funcion funcEncontrada = seleccionada.funcion;
                    Sala salaEncontrada = seleccionada.sala;

                    System.out.println("\n=== DISTRIBUCION DE ASIENTOS ===");
                    System.out.println("Cine: " + seleccionada.cine.getNombreCine() + " - Sala: " + salaEncontrada.getIdSala());
                    salaEncontrada.mostrarAsientos();

                    System.out.print("Ingresa el identificador del asiento deseado (ej. A1): ");
                    String idA = scanner.nextLine().trim().toUpperCase();
                    Asiento as = funcEncontrada.obtenerAsiento(idA);

                    if (as == null) {
                        System.out.println("El asiento '" + idA + "' no existe en esta sala.");
                        break;
                    }

                    if (!as.getEstadoAsiento().equalsIgnoreCase("DISPONIBLE")) {
                        System.out.println("El asiento '" + idA + "' ya esta " + as.getEstadoAsiento() + ". Por favor elige otro.");
                        break;
                    }

                    List<Asiento> seleccion = new ArrayList<>();
                    seleccion.add(as);

                    System.out.println("\nDetalle: Asiento " + as.getIdentificador() + " (" + as.getTipoAsiento() + ") - Precio: $" + as.getPrecio());
                    System.out.println("Selecciona tu metodo de pago:");
                    System.out.println("1. Tarjeta de Credito");
                    System.out.println("2. Paypal");
                    System.out.println("3. Transferencia Electronica");
                    System.out.print("Opcion: ");
                    String m = scanner.nextLine().trim();

                    MetodoDePago metodo;
                    if (m.equals("2")) {
                        System.out.print("Correo electronico de tu cuenta Paypal: ");
                        metodo = new Paypal(scanner.nextLine().trim());
                    } else if (m.equals("3")) {
                        System.out.print("CLABE o Cuenta bancaria para transferencia: ");
                        metodo = new TransferenciaElectronica(scanner.nextLine().trim());
                    } else {
                        System.out.print("Numero de tarjeta (16 digitos): ");
                        metodo = new TarjetaCredito(scanner.nextLine().trim());
                    }

                    try {
                        Boleto b = cliente.comprarBoleto(funcEncontrada, salaEncontrada.getIdSala(), seleccion, metodo);
                        if (b != null) {
                            sistema.registrarBoleto(b);
                            System.out.println("\n========================================");
                            System.out.println("       ¡COMPRA REALIZADA CON EXITO!     ");
                            System.out.println("========================================");
                            b.imprimirBoleto();
                        }
                    } catch (AsientoOcupadoException ex) {
                        System.out.println("\n[ERROR DE VENTA] " + ex.getMessage());
                    } catch (IllegalArgumentException ex) {
                        System.out.println("\n[ERROR DE PAGO] " + ex.getMessage());
                    }
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (op != 3);
    }
}
