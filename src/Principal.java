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
            System.out.println("2. Entrar como Administrador");
            System.out.println("3. Entrar como Cliente");
            System.out.println("4. Salir");
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
                    menuAdministrador(sistema);
                    break;
                case 3:
                    menuCliente(sistema);
                    break;
                case 4:
                    System.out.println("\nSaliendo del programa. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opcion no valida. Intenta de nuevo.");
                    break;
            }
        } while (opcionPrincipal != 4 && opcionPrincipal != 1);
    }

    private static void cargarDatosIniciales(Sistema sistema) {
        Administrador admin = new Administrador("Admin Pablo", "1234");

        List<String> actores1 = new ArrayList<>();
        actores1.add("Jack Black");
        actores1.add("Dustin Hoffman");
        Pelicula p1 = new Pelicula("Kung Fu Panda", "1:32", "A", "Animacion / Comedia", "Mark Osborne", actores1, "Po el panda sueña con ser maestro de kung fu.");

        List<String> actores2 = new ArrayList<>();
        actores2.add("Robert Downey Jr.");
        actores2.add("Gwyneth Paltrow");
        Pelicula p2 = new Pelicula("Iron Man 2", "2:04", "B", "Accion", "Jon Favreau", actores2, "Tony Stark se enfrenta a nuevas amenazas.");

        admin.registraPelicula(p1, sistema);
        admin.registraPelicula(p2, sistema);

        Cine cine1 = new Cine("Cinepolis Centro");
        Sala sala1 = new Sala("Sala 1");
        sala1.generarAsientos(3, 4, "TRADICIONAL", 80.0);
        cine1.agregaSala("Sala 1", sala1);
        sistema.agregaCine(cine1);

        Funcion f1 = new Funcion("2026-11-20", 18, 30, p1);
        sistema.programarFuncion("Cinepolis Centro", "Sala 1", f1);
    }

    private static void menuAdministrador(Sistema sistema) {
        Administrador admin = new Administrador("Admin General", "1234");
        System.out.print("\nIngresa el PIN de Administrador (PIN por defecto: 1234): ");
        String pin = scanner.nextLine();

        if (!admin.validarPin(pin)) {
            System.out.println("PIN incorrecto. Acceso denegado.");
            return;
        }

        int opcion = 0;
        do {
            System.out.println("\n========================================");
            System.out.println("         PANEL DE ADMINISTRACION        ");
            System.out.println("========================================");
            System.out.println("1. Gestionar Cines");
            System.out.println("2. Gestionar Salas");
            System.out.println("3. Gestionar Peliculas");
            System.out.println("4. Gestionar Funciones");
            System.out.println("5. Cerrar Sesion de Administrador");
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
                    System.out.println("Cerrando sesion...");
                    break;
                default:
                    System.out.println("Opcion no valida.");
                    break;
            }
        } while (opcion != 5);
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
                    if (sistema.obtenerCine(nombre) == null) {
                        sistema.agregaCine(new Cine(nombre));
                        System.out.println("¡Cine '" + nombre + "' registrado con exito en el mapa!");
                    } else {
                        System.out.println("Ese cine ya se encuentra registrado.");
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
                    if (sistema.eliminarCine(cineEliminar)) {
                        System.out.println("Cine eliminado correctamente.");
                    } else {
                        System.out.println("No se encontro un cine con ese nombre.");
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

        System.out.print("Ingresa el nombre del cine donde deseas gestionar salas: ");
        String nombreCine = scanner.nextLine();
        Cine cine = sistema.obtenerCine(nombreCine);

        if (cine == null) {
            System.out.println("El cine no existe.");
            return;
        }

        int op = 0;
        do {
            System.out.println("\nCine seleccionado: " + cine.getNombreCine());
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
                    System.out.println("¡Sala agregada con " + (filas * asientosFila) + " asientos!");
                    break;
                case 2:
                    System.out.println("\n=== SALAS EN " + cine.getNombreCine().toUpperCase() + " ===");
                    if (cine.getSalas().isEmpty()) {
                        System.out.println("No hay salas en este cine.");
                    } else {
                        for (Sala s : cine.getSalas().values()) {
                            System.out.println("• " + s.getIdSala() + " - Asientos: " + s.getTotalAsientos() + " - Precio: $" + s.getPrecioBase());
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
                    if (sistema.eliminarPelicula(tituloElim)) {
                        System.out.println("Pelicula eliminada del catalogo.");
                    } else {
                        System.out.println("No se encontro esa pelicula.");
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
            System.out.println("\nGESTION DE FUNCIONES ");
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
                    System.out.print("Cine: ");
                    String nCine = scanner.nextLine();
                    Cine c = sistema.obtenerCine(nCine);
                    if (c == null) {
                        System.out.println("Cine no encontrado.");
                        break;
                    }

                    System.out.print("Sala: ");
                    String nSala = scanner.nextLine();
                    Sala s = c.obtenerSala(nSala);
                    if (s == null) {
                        System.out.println("Sala no encontrada.");
                        break;
                    }

                    System.out.print("Pelicula a proyectar: ");
                    String nPeli = scanner.nextLine();
                    Pelicula pel = sistema.obtenerPelicula(nPeli);
                    if (pel == null) {
                        System.out.println("Pelicula no encontrada.");
                        break;
                    }

                    System.out.print("Fecha (AAAA-MM-DD): ");
                    String fecha = scanner.nextLine();
                    System.out.print("Hora (0-23): ");
                    int hrs = Integer.parseInt(scanner.nextLine());
                    System.out.print("Minutos (0-59): ");
                    int min = Integer.parseInt(scanner.nextLine());

                    Funcion f = new Funcion(fecha, hrs, min, pel);
                    if (s.programarFuncion(f)) {
                        System.out.println("¡Funcion programada exitosamente!");
                    }
                    break;
                case 2:
                    System.out.print("Nombre del cine: ");
                    String nomC = scanner.nextLine();
                    Cine cineBuscado = sistema.obtenerCine(nomC);
                    if (cineBuscado != null) {
                        for (Sala sala : cineBuscado.getSalas().values()) {
                            System.out.println("\nSalas: " + sala.getIdSala());
                            for (Funcion func : sala.getProgramacion()) {
                                System.out.println("  • " + func.getPeliculaProyectada().getTitulo() + " - " + func.getFecha() + " " + func.getHorarioHrs() + ":" + func.getHorarioMin());
                            }
                        }
                    } else {
                        System.out.println("Cine no encontrado.");
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

                        Boleto b = cliente.comprarBoleto(funcEncontrada, salaEncontrada.getIdSala(), seleccion, metodo);
                        if (b != null) {
                            sistema.registrarBoleto(b);
                            System.out.println();
                            b.imprimirBoleto();
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
