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
        // Administrador por defecto
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
            System.out.println("Aviso al inicializar datos: " + e.getMessage());
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

        System.out.print("Crea tu PIN secreto de acceso (ej. 4 digitos): ");
        String pin = scanner.nextLine();
        if (pin.trim().isEmpty()) {
            System.out.println("El PIN no puede estar vacio.");
            return;
        }

        Administrador nuevo = new Administrador(nombre.trim(), pin.trim());
        sistema.registrarAdministrador(nuevo);
        System.out.println("¡Administrador '" + nombre + "' registrado con exito! Ya puedes iniciar sesion con tu PIN.");
    }

    private static void menuLoginAdmin(Sistema sistema) {
        System.out.println("\n--- ACCESO DE ADMINISTRADOR ---");
        System.out.print("Usuario / Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("PIN secreto: ");
        String pin = scanner.nextLine();

        Administrador adminLogueado = sistema.autenticarAdmin(nombre, pin);
        if (adminLogueado == null) {
            System.out.println("Error: Credenciales incorrectas (Usuario o PIN no coinciden).");
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
                    System.out.println("\n=== ADMINISTRADORES DEL SISTEMA ===");
                    for (Administrador a : sistema.getAdministradores().values()) {
                        System.out.println("• " + a.getNombre());
                    }
                    break;
                case 6:
                    System.out.println("Cerrando sesion...");
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
            System.out.println("1. Agregar Cine");
            System.out.println("2. Modificar Cine");
            System.out.println("3. Eliminar Cine");
            System.out.println("4. Listar Cines registrados");
            System.out.println("5. Volver");
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
                        System.out.println("¡Cine '" + nombre + "' registrado con exito!");
                    }
                    break;
                case 2:
                    System.out.println("\nCines disponibles:");
                    listarCinesDisponibles(sistema);
                    System.out.print("Nombre actual del cine a modificar: ");
                    String nombreActual = scanner.nextLine();
                    try {
                        Cine cActual = sistema.obtenerCine(nombreActual);
                        System.out.print("Nuevo nombre para el cine: ");
                        String nombreNuevo = scanner.nextLine();
                        Cine cNuevo = new Cine(nombreNuevo);
                        cNuevo.setSalas(cActual.getSalas());
                        sistema.modificarCine(nombreActual, cNuevo);
                        System.out.println("¡Cine modificado exitosamente!");
                    } catch (EntidadNoEncontradaException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("\nCines disponibles:");
                    listarCinesDisponibles(sistema);
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
                    System.out.println("\n=== CINES REGISTRADOS ===");
                    listarCinesDisponibles(sistema);
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (op != 5);
    }

    private static void listarCinesDisponibles(Sistema sistema) {
        if (sistema.getCines().isEmpty()) {
            System.out.println("  (No hay cines registrados)");
        } else {
            for (Cine c : sistema.getCines().values()) {
                System.out.println("  • " + c.getNombreCine() + " (Salas: " + c.getSalas().size() + ")");
            }
        }
    }

    private static void menuGestionarSalas(Sistema sistema) {
        System.out.println("\n--- GESTION DE SALAS ---");
        if (sistema.getCines().isEmpty()) {
            System.out.println("Primero debes registrar al menos un cine.");
            return;
        }

        System.out.println("Cines disponibles:");
        listarCinesDisponibles(sistema);
        System.out.print("Ingresa el nombre del cine donde deseas gestionar salas: ");
        String nombreCine = scanner.nextLine();
        Cine cine;
        try {
            cine = sistema.obtenerCine(nombreCine);
        } catch (EntidadNoEncontradaException e) {
            System.out.println("El cine no existe: " + e.getMessage());
            return;
        }

        int op = 0;
        do {
            System.out.println("\nCine seleccionado: " + cine.getNombreCine());
            System.out.println("1. Agregar Sala");
            System.out.println("2. Modificar Sala");
            System.out.println("3. Eliminar Sala");
            System.out.println("4. Listar Salas de este cine");
            System.out.println("5. Volver");
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
                    System.out.println("¡Sala agregada con " + (filas * asientosFila) + " asientos!");
                    break;
                case 2:
                    System.out.println("\nSalas actuales en " + cine.getNombreCine() + ":");
                    listarSalasDeCine(cine);
                    System.out.print("ID de la sala que deseas modificar: ");
                    String idSalaActual = scanner.nextLine();
                    try {
                        Sala sActual = cine.obtenerSala(idSalaActual);
                        System.out.print("Nuevo ID para la sala: ");
                        String nuevoId = scanner.nextLine();
                        System.out.print("Nuevas filas: ");
                        int nFilas = Integer.parseInt(scanner.nextLine());
                        System.out.print("Nuevos asientos por fila: ");
                        int nAsientosFila = Integer.parseInt(scanner.nextLine());
                        System.out.print("Nuevo precio base: $");
                        double nPrecio = Double.parseDouble(scanner.nextLine());

                        Sala sMod = new Sala(nuevoId);
                        sMod.generarAsientos(nFilas, nAsientosFila, sActual.getTipoSala(), nPrecio);
                        sMod.setProgramacion(sActual.getProgramacion());

                        cine.modificaSala(idSalaActual, nuevoId, sMod);
                        System.out.println("¡Sala modificada exitosamente!");
                    } catch (EntidadNoEncontradaException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("\nSalas actuales:");
                    listarSalasDeCine(cine);
                    System.out.print("ID de la sala a eliminar: ");
                    String idEliminar = scanner.nextLine();
                    try {
                        cine.eliminaSala(idEliminar);
                        System.out.println("¡Sala eliminada correctamente!");
                    } catch (EntidadNoEncontradaException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("\n=== SALAS EN " + cine.getNombreCine().toUpperCase() + " ===");
                    listarSalasDeCine(cine);
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (op != 5);
    }

    private static void listarSalasDeCine(Cine cine) {
        if (cine.getSalas().isEmpty()) {
            System.out.println("  (No hay salas en este cine)");
        } else {
            for (Sala s : cine.getSalas().values()) {
                System.out.println("  • " + s.getIdSala() + " - Asientos: " + s.getTotalAsientos() + " - Precio base: $" + s.getPrecioBase());
            }
        }
    }

    private static void menuGestionarPeliculas(Sistema sistema) {
        int op = 0;
        do {
            System.out.println("\n--- GESTION DE PELICULAS ---");
            System.out.println("1. Agregar Pelicula al catalogo");
            System.out.println("2. Modificar Pelicula");
            System.out.println("3. Eliminar Pelicula");
            System.out.println("4. Ver catalogo completo");
            System.out.println("5. Volver");
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
                    System.out.println("¡Pelicula '" + tit + "' agregada al catalogo!");
                    break;
                case 2:
                    System.out.println("\nPeliculas en catalogo:");
                    listarPeliculasDisponibles(sistema);
                    System.out.print("Titulo de la pelicula a modificar: ");
                    String tActual = scanner.nextLine();
                    try {
                        Pelicula pelActual = sistema.obtenerPelicula(tActual);
                        System.out.println("Ingresa los nuevos datos:");
                        System.out.print("Nuevo Titulo: ");
                        String nTit = scanner.nextLine();
                        System.out.print("Nueva Duracion: ");
                        String nDur = scanner.nextLine();
                        System.out.print("Nueva Clasificacion: ");
                        String nCla = scanner.nextLine();
                        System.out.print("Nuevo Genero: ");
                        String nGen = scanner.nextLine();
                        System.out.print("Nuevo Director: ");
                        String nDir = scanner.nextLine();

                        Pelicula pelMod = new Pelicula(nTit, nDur, nCla, nGen, nDir, pelActual.getActores(), pelActual.getSinopsis());
                        sistema.modificarPelicula(tActual, pelMod);
                        System.out.println("¡Pelicula modificada con exito!");
                    } catch (EntidadNoEncontradaException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("\nPeliculas en catalogo:");
                    listarPeliculasDisponibles(sistema);
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
                    System.out.println("\n=== CATALOGO DE PELICULAS ===");
                    listarPeliculasDisponibles(sistema);
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (op != 5);
    }

    private static void listarPeliculasDisponibles(Sistema sistema) {
        if (sistema.getCatalogoPeliculas().isEmpty()) {
            System.out.println("  (No hay peliculas en el catalogo)");
        } else {
            for (Pelicula pel : sistema.getCatalogoPeliculas().values()) {
                System.out.println("  • " + pel.getTitulo() + " (" + pel.getClasificacion() + ") - " + pel.getDuracion() + " - " + pel.getGenero());
            }
        }
    }

    private static void menuGestionarFunciones(Sistema sistema) {
        int op = 0;
        do {
            System.out.println("\n--- GESTION DE FUNCIONES ---");
            System.out.println("1. Agregar Funcion");
            System.out.println("2. Modificar Funcion");
            System.out.println("3. Eliminar / Cancelar Funcion");
            System.out.println("4. Ver funciones programadas");
            System.out.println("5. Volver");
            System.out.print("Opcion: ");

            try {
                op = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                op = 0;
            }

            switch (op) {
                case 1:
                    System.out.println("\n=== CINES DISPONIBLES ===");
                    listarCinesDisponibles(sistema);
                    if (sistema.getCines().isEmpty()) break;

                    System.out.print("Nombre del Cine: ");
                    String nCine = scanner.nextLine();
                    try {
                        Cine c = sistema.obtenerCine(nCine);

                        System.out.println("\n=== SALAS DISPONIBLES EN " + c.getNombreCine().toUpperCase() + " ===");
                        listarSalasDeCine(c);
                        if (c.getSalas().isEmpty()) break;

                        System.out.print("ID de la Sala: ");
                        String nSala = scanner.nextLine();
                        Sala s = c.obtenerSala(nSala);

                        System.out.println("\n=== PELICULAS DISPONIBLES ===");
                        listarPeliculasDisponibles(sistema);
                        if (sistema.getCatalogoPeliculas().isEmpty()) break;

                        System.out.print("Titulo de la Pelicula: ");
                        String nPeli = scanner.nextLine();
                        Pelicula pel = sistema.obtenerPelicula(nPeli);

                        System.out.print("Fecha (AAAA-MM-DD): ");
                        String fecha = scanner.nextLine();
                        System.out.print("Hora de inicio (0-23): ");
                        int hrs = Integer.parseInt(scanner.nextLine());
                        System.out.print("Minuto de inicio (0-59): ");
                        int min = Integer.parseInt(scanner.nextLine());

                        Funcion f = new Funcion(fecha, hrs, min, pel);
                        if (s.programarFuncion(f)) {
                            System.out.println("¡Funcion programada exitosamente en " + c.getNombreCine() + " - " + s.getIdSala() + "!");
                        }
                    } catch (EntidadNoEncontradaException | ConflictoHorarioException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("\n=== CINES DISPONIBLES ===");
                    listarCinesDisponibles(sistema);
                    System.out.print("Nombre del Cine: ");
                    String cineMod = scanner.nextLine();
                    try {
                        Cine cM = sistema.obtenerCine(cineMod);

                        System.out.println("\nSalas disponibles:");
                        listarSalasDeCine(cM);
                        System.out.print("ID de la Sala: ");
                        String salaMod = scanner.nextLine();
                        Sala sM = cM.obtenerSala(salaMod);

                        System.out.println("\nFunciones programadas en esa sala:");
                        mostrarFuncionesDeSala(sM);

                        System.out.print("Fecha actual de la funcion a modificar (AAAA-MM-DD): ");
                        String fAct = scanner.nextLine();
                        System.out.print("Hora actual de la funcion (0-23): ");
                        int hAct = Integer.parseInt(scanner.nextLine());

                        System.out.println("\n--- NUEVOS DATOS PARA LA FUNCION ---");
                        System.out.println("Peliculas disponibles:");
                        listarPeliculasDisponibles(sistema);
                        System.out.print("Nueva Pelicula: ");
                        String nPeliN = scanner.nextLine();
                        Pelicula peliNueva = sistema.obtenerPelicula(nPeliN);

                        System.out.print("Nueva Fecha (AAAA-MM-DD): ");
                        String fN = scanner.nextLine();
                        System.out.print("Nueva Hora (0-23): ");
                        int hN = Integer.parseInt(scanner.nextLine());
                        System.out.print("Nuevos Minutos (0-59): ");
                        int mN = Integer.parseInt(scanner.nextLine());

                        Funcion fModificada = new Funcion(fN, hN, mN, peliNueva);
                        if (sistema.modificarFuncion(cineMod, salaMod, fAct, hAct, fModificada)) {
                            System.out.println("¡Funcion modificada exitosamente!");
                        } else {
                            System.out.println("No se pudo modificar la funcion (verifica la fecha y hora actual).");
                        }
                    } catch (EntidadNoEncontradaException | ConflictoHorarioException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("\n=== CINES DISPONIBLES ===");
                    listarCinesDisponibles(sistema);
                    System.out.print("Nombre del Cine: ");
                    String cElim = scanner.nextLine();
                    try {
                        Cine cineE = sistema.obtenerCine(cElim);

                        System.out.println("\nSalas disponibles:");
                        listarSalasDeCine(cineE);
                        System.out.print("ID de la Sala: ");
                        String sElim = scanner.nextLine();
                        Sala salaE = cineE.obtenerSala(sElim);

                        System.out.println("\nFunciones programadas en esa sala:");
                        mostrarFuncionesDeSala(salaE);

                        System.out.print("Fecha de la funcion a cancelar (AAAA-MM-DD): ");
                        String fCancel = scanner.nextLine();
                        System.out.print("Hora de la funcion a cancelar (0-23): ");
                        int hCancel = Integer.parseInt(scanner.nextLine());

                        if (sistema.eliminarFuncion(cElim, sElim, fCancel, hCancel)) {
                            System.out.println("¡Funcion cancelada exitosamente!");
                        } else {
                            System.out.println("No se encontro una funcion con esa fecha y hora.");
                        }
                    } catch (EntidadNoEncontradaException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                    break;

                case 4:
                    System.out.print("Nombre del cine para consultar funciones (o presiona Enter para ver todos): ");
                    String cConsulta = scanner.nextLine();
                    if (cConsulta.trim().isEmpty()) {
                        for (Cine cineObj : sistema.getCines().values()) {
                            System.out.println("\n========================================");
                            System.out.println("CINE: " + cineObj.getNombreCine().toUpperCase());
                            for (Sala salaObj : cineObj.getSalas().values()) {
                                System.out.println("  • " + salaObj.getIdSala() + " (" + salaObj.getProgramacion().size() + " funciones):");
                                mostrarFuncionesDeSala(salaObj);
                            }
                        }
                    } else {
                        try {
                            Cine cineObj = sistema.obtenerCine(cConsulta);
                            System.out.println("\nCINE: " + cineObj.getNombreCine().toUpperCase());
                            for (Sala salaObj : cineObj.getSalas().values()) {
                                System.out.println("  • " + salaObj.getIdSala() + ":");
                                mostrarFuncionesDeSala(salaObj);
                            }
                        } catch (EntidadNoEncontradaException ex) {
                            System.out.println("Error: " + ex.getMessage());
                        }
                    }
                    break;

                case 5:
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (op != 5);
    }

    private static void mostrarFuncionesDeSala(Sala sala) {
        if (sala.getProgramacion().isEmpty()) {
            System.out.println("    (No hay funciones programadas)");
        } else {
            for (Funcion func : sala.getProgramacion()) {
                System.out.println("    - Pelicula: " + func.getPeliculaProyectada().getTitulo() + " | Fecha: " + func.getFecha() + " | Hora: " + func.getHorarioHrs() + ":" + func.getHorarioMin());
            }
        }
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
                    listarPeliculasDisponibles(sistema);
                    break;
                case 2:
                    System.out.println("\n=== COMPRA DE BOLETOS ===");
                    if (sistema.getCatalogoPeliculas().isEmpty()) {
                        System.out.println("No hay peliculas disponibles.");
                        break;
                    }

                    List<Pelicula> pelis = new ArrayList<>(sistema.getCatalogoPeliculas().values());
                    for (int i = 0; i < pelis.size(); i++) {
                        System.out.println((i + 1) + ". " + pelis.get(i).getTitulo());
                    }
                    System.out.print("Elige el numero de pelicula: ");
                    int num = Integer.parseInt(scanner.nextLine());
                    if (num < 1 || num > pelis.size()) {
                        System.out.println("Opcion invalida.");
                        break;
                    }
                    Pelicula peliElegida = pelis.get(num - 1);

                    Funcion funcEncontrada = null;
                    Sala salaEncontrada = null;
                    for (Cine c : sistema.getCines().values()) {
                        for (Sala s : c.getSalas().values()) {
                            for (Funcion f : s.getProgramacion()) {
                                if (f.getPeliculaProyectada().getTitulo().equalsIgnoreCase(peliElegida.getTitulo())) {
                                    funcEncontrada = f;
                                    salaEncontrada = s;
                                    break;
                                }
                            }
                        }
                    }

                    if (funcEncontrada == null) {
                        System.out.println("No hay funciones para esta pelicula.");
                        break;
                    }

                    System.out.println("\nFuncion disponible: " + funcEncontrada.getFecha() + " " + funcEncontrada.getHorarioHrs() + ":" + funcEncontrada.getHorarioMin());
                    funcEncontrada.getSala().mostrarAsientos();

                    System.out.print("Ingresa el asiento (ej. A1): ");
                    String idA = scanner.nextLine();
                    Asiento as = funcEncontrada.obtenerAsiento(idA);

                    if (as != null && as.getEstadoAsiento().equalsIgnoreCase("DISPONIBLE")) {
                        List<Asiento> seleccion = new ArrayList<>();
                        seleccion.add(as);

                        System.out.println("Selecciona metodo de pago:");
                        System.out.println("1. Tarjeta de Credito");
                        System.out.println("2. Paypal");
                        System.out.println("3. Transferencia");
                        System.out.print("Opcion: ");
                        String m = scanner.nextLine();

                        MetodoDePago metodo;
                        if (m.equals("2")) {
                            System.out.print("Correo Paypal: ");
                            metodo = new Paypal(scanner.nextLine());
                        } else if (m.equals("3")) {
                            System.out.print("Cuenta bancaria: ");
                            metodo = new TransferenciaElectronica(scanner.nextLine());
                        } else {
                            System.out.print("Numero de tarjeta (16 digitos): ");
                            metodo = new TarjetaCredito(scanner.nextLine());
                        }

                        try {
                            Boleto b = cliente.comprarBoleto(funcEncontrada, salaEncontrada.getIdSala(), seleccion, metodo);
                            if (b != null) {
                                sistema.registrarBoleto(b);
                                System.out.println();
                                b.imprimirBoleto();
                            }
                        } catch (AsientoOcupadoException ex) {
                            System.out.println("Error al comprar: " + ex.getMessage());
                        }
                    } else {
                        System.out.println("Asiento no valido o no disponible.");
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
