import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

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
            opcionPrincipal = leerEntero("Selecciona una opcion: ");

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

    // --- Metodos auxiliares reutilizables (evitan codigo duplicado) ---

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private static Cine seleccionarCine(Sistema sistema) {
        List<Cine> listaCines = new ArrayList<>(sistema.getCines().values());
        for (int i = 0; i < listaCines.size(); i++) {
            System.out.println((i + 1) + ". " + listaCines.get(i).getNombreCine() + " (Salas: " + listaCines.get(i).getSalas().size() + ")");
        }
        System.out.print("Selecciona el numero o escribe el nombre del cine: ");
        String entrada = scanner.nextLine().trim();

        try {
            int idx = Integer.parseInt(entrada);
            if (idx >= 1 && idx <= listaCines.size()) {
                return listaCines.get(idx - 1);
            }
        } catch (NumberFormatException e) {
            // Se buscara por nombre
        }

        try {
            return sistema.obtenerCine(entrada);
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    private static void cargarDatosIniciales(Sistema sistema) {
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

    // --- Autenticacion y Menu Administrador ---

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
            nuevo.validarPin(pin.trim());
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
            opcion = leerEntero("Elige una opcion: ");

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

    // --- Submenu: Gestion de Cines ---

    private static void menuGestionarCines(Sistema sistema) {
        int op = 0;
        do {
            System.out.println("\n--- GESTION DE CINES ---");
            System.out.println("1. Registrar nuevo Cine");
            System.out.println("2. Listar Cines registrados");
            System.out.println("3. Eliminar Cine");
            System.out.println("4. Volver");
            op = leerEntero("Opcion: ");

            switch (op) {
                case 1:
                    registrarCine(sistema);
                    break;
                case 2:
                    listarCines(sistema);
                    break;
                case 3:
                    eliminarCine(sistema);
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (op != 4);
    }

    private static void registrarCine(Sistema sistema) {
        System.out.print("Nombre del nuevo cine: ");
        String nombre = scanner.nextLine();
        try {
            sistema.obtenerCine(nombre);
            System.out.println("Ese cine ya se encuentra registrado.");
        } catch (EntidadNoEncontradaException ex) {
            sistema.agregaCine(new Cine(nombre));
            System.out.println("¡Cine '" + nombre + "' registrado con exito en el mapa!");
        }
    }

    private static void listarCines(Sistema sistema) {
        System.out.println("\n=== CINES REGISTRADOS ===");
        if (sistema.getCines().isEmpty()) {
            System.out.println("No hay cines registrados.");
        } else {
            for (Cine c : sistema.getCines().values()) {
                System.out.println("• " + c.getNombreCine() + " (Total de salas: " + c.getSalas().size() + ")");
            }
        }
    }

    private static void eliminarCine(Sistema sistema) {
        System.out.print("Nombre del cine a eliminar: ");
        String cineEliminar = scanner.nextLine();
        try {
            sistema.eliminarCine(cineEliminar);
            System.out.println("Cine eliminado correctamente.");
        } catch (EntidadNoEncontradaException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // --- Submenu: Gestion de Salas ---

    private static void menuGestionarSalas(Sistema sistema) {
        System.out.println("\n--- GESTION DE SALAS ---");
        if (sistema.getCines().isEmpty()) {
            System.out.println("Primero debes registrar al menos un cine.");
            return;
        }

        System.out.println("Cines disponibles:");
        Cine cine = seleccionarCine(sistema);
        if (cine == null) {
            return;
        }

        int op = 0;
        do {
            System.out.println("\n========================================");
            System.out.println("CINE SELECCIONADO: " + cine.getNombreCine().toUpperCase());
            System.out.println("========================================");
            System.out.println("1. Agregar nueva Sala");
            System.out.println("2. Listar Salas de este cine");
            System.out.println("3. Volver");
            op = leerEntero("Opcion: ");

            switch (op) {
                case 1:
                    agregarSala(cine);
                    break;
                case 2:
                    listarSalas(cine);
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (op != 3);
    }

    private static void agregarSala(Cine cine) {
        System.out.print("Identificador de la sala (ej. Sala 2): ");
        String idSala = scanner.nextLine();
        int filas = leerEntero("Numero de filas: ");
        int asientosFila = leerEntero("Asientos por fila: ");
        System.out.print("Precio base del boleto: $");
        double precio = 0.0;
        try {
            precio = Double.parseDouble(scanner.nextLine());
        } catch (Exception e) {
            precio = 0.0;
        }

        Sala nuevaSala = new Sala(idSala);
        nuevaSala.generarAsientos(filas, asientosFila, "TRADICIONAL", precio);
        cine.agregaSala(idSala, nuevaSala);
        System.out.println("¡Sala '" + idSala + "' agregada con exito en " + cine.getNombreCine() + " con " + (filas * asientosFila) + " asientos!");
    }

    private static void listarSalas(Cine cine) {
        System.out.println("\n=== SALAS EN " + cine.getNombreCine().toUpperCase() + " ===");
        if (cine.getSalas().isEmpty()) {
            System.out.println("No hay salas registradas en este cine.");
        } else {
            for (Sala s : cine.getSalas().values()) {
                System.out.println("• " + s.getIdSala() + " - Tipo: " + s.getTipoSala() + " - Asientos totales: " + s.getTotalAsientos() + " - Precio: $" + s.getPrecioBase());
            }
        }
    }

    // --- Submenu: Gestion de Peliculas ---

    private static void menuGestionarPeliculas(Sistema sistema) {
        int op = 0;
        do {
            System.out.println("\nGESTION DE PELICULAS ");
            System.out.println("1. Agregar Pelicula al catalogo");
            System.out.println("2. Ver catalogo completo");
            System.out.println("3. Eliminar Pelicula");
            System.out.println("4. Volver");
            op = leerEntero("Opcion: ");

            switch (op) {
                case 1:
                    agregarPelicula(sistema);
                    break;
                case 2:
                    listarPeliculas(sistema);
                    break;
                case 3:
                    eliminarPelicula(sistema);
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (op != 4);
    }

    private static void agregarPelicula(Sistema sistema) {
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
    }

    private static void listarPeliculas(Sistema sistema) {
        System.out.println("\n=== CATALOGO DE PELICULAS ===");
        if (sistema.getCatalogoPeliculas().isEmpty()) {
            System.out.println("No hay peliculas en el catalogo.");
        } else {
            for (Pelicula pel : sistema.getCatalogoPeliculas().values()) {
                System.out.println("• " + pel.getTitulo() + " (" + pel.getClasificacion() + ") - " + pel.getDuracion() + " - " + pel.getGenero());
            }
        }
    }

    private static void eliminarPelicula(Sistema sistema) {
        System.out.print("Titulo de la pelicula a eliminar: ");
        String tituloElim = scanner.nextLine();
        try {
            sistema.eliminarPelicula(tituloElim);
            System.out.println("Pelicula eliminada del catalogo.");
        } catch (EntidadNoEncontradaException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // --- Submenu: Gestion de Funciones ---

    private static void menuGestionarFunciones(Sistema sistema) {
        int op = 0;
        do {
            System.out.println("\n--- GESTION DE FUNCIONES ---");
            System.out.println("1. Programar nueva Funcion");
            System.out.println("2. Ver funciones programadas por Cine");
            System.out.println("3. Volver");
            op = leerEntero("Opcion: ");

            switch (op) {
                case 1:
                    programarFuncion(sistema);
                    break;
                case 2:
                    consultarFunciones(sistema);
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (op != 3);
    }

    private static void programarFuncion(Sistema sistema) {
        if (sistema.getCines().isEmpty()) {
            System.out.println("No hay cines registrados. Registra un cine primero.");
            return;
        }
        if (sistema.getCatalogoPeliculas().isEmpty()) {
            System.out.println("No hay peliculas en catalogo. Registra una pelicula primero.");
            return;
        }

        System.out.println("\n--- PASO 1: SELECCIONA EL CINE ---");
        Cine cineElegido = seleccionarCine(sistema);
        if (cineElegido == null) {
            return;
        }

        if (cineElegido.getSalas().isEmpty()) {
            System.out.println("El cine '" + cineElegido.getNombreCine() + "' no tiene salas registradas. Agrega una sala primero.");
            return;
        }
        Sala salaElegida = seleccionarSala(cineElegido);
        if (salaElegida == null) {
            return;
        }

        Pelicula peliElegida = seleccionarPelicula(sistema);
        if (peliElegida == null) {
            return;
        }

        System.out.print("Fecha de proyeccion (AAAA-MM-DD): ");
        String fecha = scanner.nextLine();
        int hrs = leerEntero("Hora de inicio (0-23): ");
        int min = leerEntero("Minutos de inicio (0-59): ");

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
    }

    private static Sala seleccionarSala(Cine cine) {
        System.out.println("\n--- PASO 2: SELECCIONA LA SALA EN " + cine.getNombreCine().toUpperCase() + " ---");
        List<Sala> salasDisponibles = new ArrayList<>(cine.getSalas().values());
        for (int i = 0; i < salasDisponibles.size(); i++) {
            Sala sl = salasDisponibles.get(i);
            System.out.println((i + 1) + ". " + sl.getIdSala() + " (" + sl.getTipoSala() + " - " + sl.getTotalAsientos() + " asientos - $" + sl.getPrecioBase() + ")");
        }
        System.out.print("Numero de sala (o ID): ");
        String entrada = scanner.nextLine().trim();

        try {
            int idx = Integer.parseInt(entrada);
            if (idx >= 1 && idx <= salasDisponibles.size()) {
                return salasDisponibles.get(idx - 1);
            }
        } catch (NumberFormatException e) {
            // Buscar por ID
        }

        try {
            return cine.obtenerSala(entrada);
        } catch (EntidadNoEncontradaException ex) {
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }

    private static Pelicula seleccionarPelicula(Sistema sistema) {
        System.out.println("\n--- PASO 3: SELECCIONA LA PELICULA A PROYECTAR ---");
        List<Pelicula> pelisDisponibles = new ArrayList<>(sistema.getCatalogoPeliculas().values());
        for (int i = 0; i < pelisDisponibles.size(); i++) {
            Pelicula pl = pelisDisponibles.get(i);
            System.out.println((i + 1) + ". " + pl.getTitulo() + " (" + pl.getClasificacion() + " - " + pl.getGenero() + " [" + pl.getDuracion() + "])");
        }
        System.out.print("Numero de pelicula (o titulo): ");
        String entrada = scanner.nextLine().trim();

        try {
            int idx = Integer.parseInt(entrada);
            if (idx >= 1 && idx <= pelisDisponibles.size()) {
                return pelisDisponibles.get(idx - 1);
            }
        } catch (NumberFormatException e) {
            // Buscar por titulo
        }

        try {
            return sistema.obtenerPelicula(entrada);
        } catch (EntidadNoEncontradaException ex) {
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }

    private static void consultarFunciones(Sistema sistema) {
        System.out.println("\n=== CONSULTA DE FUNCIONES POR CINE ===");
        if (sistema.getCines().isEmpty()) {
            System.out.println("No hay cines registrados.");
            return;
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
    }

    // --- Submenu: Cliente ---

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
            op = leerEntero("Opcion: ");

            switch (op) {
                case 1:
                    verCartelera(sistema);
                    break;
                case 2:
                    procesoCompraBoleto(sistema, cliente);
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (op != 3);
    }

    private static void verCartelera(Sistema sistema) {
        System.out.println("\n=== CARTELERA DISPONIBLE ===");
        if (sistema.getCatalogoPeliculas().isEmpty()) {
            System.out.println("No hay peliculas en cartelera.");
        } else {
            for (Pelicula p : sistema.getCatalogoPeliculas().values()) {
                System.out.println("• " + p.getTitulo() + " (" + p.getClasificacion() + ") - " + p.getGenero() + " [" + p.getDuracion() + "]");
            }
        }
    }

    private static void procesoCompraBoleto(Sistema sistema, Cliente cliente) {
        System.out.println("\n==================================================");
        System.out.println("            COMPRA DE BOLETOS - CARTELERA         ");
        System.out.println("==================================================");

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
            return;
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

        int numF = leerEntero("Elige el numero de la funcion que deseas ver: ");
        if (numF < 1 || numF > funcionesDisponibles.size()) {
            System.out.println("Opcion de funcion no valida.");
            return;
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
            return;
        }
        if (!as.getEstadoAsiento().equalsIgnoreCase("DISPONIBLE")) {
            System.out.println("El asiento '" + idA + "' ya esta " + as.getEstadoAsiento() + ". Por favor elige otro.");
            return;
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
            Boleto b = cliente.comprarBoleto(funcEncontrada, salaEncontrada.getIdSala(), seleccion, metodo, sistema.getGestorVentas());
            if (b != null) {
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
    }
}
