import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        // Inicializar el sistema central
        Sistema sistema = new Sistema();
        sistema.inicia();

        // Cargar datos iniciales de prueba para el modelo
        cargarDatosIniciales(sistema);

        Scanner scanner = new Scanner(System.in);
        int opcionPrincipal = 0;

        do {
            System.out.println("  Sistema Gestor De Cines ");
            System.out.println("1. Abrir Interfaz Grafica");
            System.out.println("2. Probar Sistema por Consola ");
            System.out.println("3. Salir");
            System.out.print("Selecciona una opcion: ");

            String entrada = scanner.nextLine();
            try {
                opcionPrincipal = Integer.parseInt(entrada);
            } catch (Exception e) {
                opcionPrincipal = 0;
            }

            switch (opcionPrincipal) {
                case 1:
                    VentanaLogin login = new VentanaLogin(sistema);
                    login.setVisible(true);
                    break;

                case 2:
                    menuConsola(sistema, scanner);
                    break;

                case 3:
                    System.out.println("\nSaliendo del programa");
                    break;

                default:
                    System.out.println("Opcion no valida");
                    break;
            }

        } while (opcionPrincipal != 3 && opcionPrincipal != 1);
    }

    private static void cargarDatosIniciales(Sistema sistema) {
        // Administrador inicial
        Administrador admin = new Administrador("Admin Pablo", "1234");

        // Crear películas
        List<String> actores1 = new ArrayList<>();
        actores1.add("Jack Black");
        actores1.add("Dustin Hoffman");
        Pelicula p1 = new Pelicula("Kung Fu Panda", "1:32", "A", "Animacion / Comedia", "Mark Osborne", actores1, "Po el panda sueña con convertirse en un maestro del kung fu.");

        List<String> actores2 = new ArrayList<>();
        actores2.add("Robert Downey Jr.");
        actores2.add("Gwyneth Paltrow");
        Pelicula p2 = new Pelicula("Iron Man 2", "2:04", "B", "Accion / Ciencia Ficcion", "Jon Favreau", actores2, "Tony Stark se enfrenta a nuevas amenazas corporativas y militares.");

        admin.registraPelicula(p1, sistema);
        admin.registraPelicula(p2, sistema);

        // Crear Cine y Salas
        Cine cine1 = new Cine("Cinepolis Centro");
        Sala sala1 = new Sala("Sala 1");
        sala1.generarAsientos(3, 4, "TRADICIONAL", 80.0); // 12 asientos
        cine1.agregaSala("Sala 1", sala1);
        sistema.agregaCine(cine1);

        // Programar función
        Funcion f1 = new Funcion("2026-10-20", 18, 30, p1);
        sistema.programarFuncion("Cinepolis Centro", "Sala 1", f1);
    }

    private static void menuConsola(Sistema sistema, Scanner scanner) {
        int opcionRol = 0;
        do {
            System.out.println("1. Entrar como Administrador");
            System.out.println("2. Entrar como Cliente");
            System.out.println("3. Regresar al Menu Principal");
            System.out.print("Selecciona tu rol: ");

            String entrada = scanner.nextLine();
            try {
                opcionRol = Integer.parseInt(entrada);
            } catch (Exception e) {
                opcionRol = 0;
            }

            switch (opcionRol) {
                case 1:
                    menuAdmin(sistema, scanner);
                    break;
                case 2:
                    menuCliente(sistema, scanner);
                    break;
                case 3:
                    System.out.println("Regresando...");
                    break;
                default:
                    System.out.println("Opcion no valida.");
                    break;
            }
        } while (opcionRol != 3);
    }

    private static void menuAdmin(Sistema sistema, Scanner scanner) {
        Administrador admin = new Administrador("Admin General", "1234");
        System.out.print("\nIngresa el PIN de Administrador (PIN por defecto: 1234): ");
        String pinIngresado = scanner.nextLine();

        if (!admin.validarPin(pinIngresado)) {
            System.out.println("PIN incorrecto. Acceso denegado.");
            return;
        }

        System.out.println("Bienvenido " + admin.getNombre() );
        int opcionAdmin = 0;
        do {
            System.out.println("\nPanel de Administrador");
            System.out.println("1. Ver catalogo de peliculas ");
            System.out.println("2. Registrar nueva pelicula");
            System.out.println("3. Ver cines y salas registradas");
            System.out.println("4. Cerrar sesion de administrador");
            System.out.print("Elige una opcion: ");

            String entrada = scanner.nextLine();
            try {
                opcionAdmin = Integer.parseInt(entrada);
            } catch (Exception e) {
                opcionAdmin = 0;
            }

            switch (opcionAdmin) {
                case 1:
                    System.out.println("\nCatalogo de peliculas");
                    Iterator<Pelicula> itPeli = sistema.getCatalogoPeliculas().iterator();
                    while (itPeli.hasNext()) {
                        Pelicula p = itPeli.next();
                        System.out.println("- Titulo: " + p.getTitulo() + " | Duracion: " + p.getDuracion() + " | Clasificacion: " + p.getClasificacion() + " | Genero: " + p.getGenero());
                    }
                    break;

                case 2:
                    System.out.print("Titulo de la pelicula: ");
                    String tit = scanner.nextLine();
                    System.out.print("Duracion: ");
                    String dur = scanner.nextLine();
                    System.out.print("Clasificacion (A, B, C): ");
                    String cla = scanner.nextLine();
                    System.out.print("Genero: ");
                    String gen = scanner.nextLine();
                    System.out.print("Director: ");
                    String dir = scanner.nextLine();

                    Pelicula nueva = new Pelicula(tit, dur, cla, gen, dir, new ArrayList<>(), "Sinopsis pendiente.");
                    admin.registraPelicula(nueva, sistema);
                    System.out.println("Pelicula registrada con exito ");
                    break;

                case 3:
                    System.out.println("\nCINES Y SALA");
                    Iterator<Cine> itCine = sistema.getCines().iterator();
                    while (itCine.hasNext()) {
                        Cine c = itCine.next();
                        System.out.println("Cine: " + c.getNombreCine());
                        Iterator<Sala> itSala = c.getSalas().iterator();
                        while (itSala.hasNext()) {
                            Sala s = itSala.next();
                            System.out.println("  -> Sala: " + s.getIdSala() + " (" + s.getTotalAsientos() + " asientos)");
                        }
                    }
                    break;

                case 4:
                    System.out.println("Cerrando sesion ");
                    break;

                default:
                    System.out.println("Opcion no valida.");
                    break;
            }
        } while (opcionAdmin != 4);
    }

    private static void menuCliente(Sistema sistema, Scanner scanner) {
        System.out.print("\nIngresa tu nombre de cliente: ");
        String nombre = scanner.nextLine();
        Cliente cliente = new Cliente(nombre);
        System.out.println("Bienvenido " + cliente.getNombre());

        int opcionCliente = 0;
        do {
            System.out.println("\nPanel de cliente");
            System.out.println("1. Ver cartelera disponible ");
            System.out.println("2. Comprar boleto (elegir pelicula y asiento)");
            System.out.println("3. Cerrar sesion de cliente");
            System.out.print("Elige una opcion: ");

            String entrada = scanner.nextLine();
            try {
                opcionCliente = Integer.parseInt(entrada);
            } catch (Exception e) {
                opcionCliente = 0;
            }

            switch (opcionCliente) {
                case 1:
                    System.out.println("\ncartelera de cine");
                    Iterator<Pelicula> itCartelera = sistema.getCatalogoPeliculas().iterator();
                    while (itCartelera.hasNext()) {
                        Pelicula p = itCartelera.next();
                        System.out.println("* " + p.getTitulo() + " (" + p.getClasificacion() + ") - " + p.getGenero() + " [" + p.getDuracion() + "]");
                        System.out.println("  Sinopsis: " + p.getSinopsis());
                    }
                    break;

                case 2:
                    List<Pelicula> catalogo = sistema.getCatalogoPeliculas();
                    if (catalogo == null || catalogo.isEmpty()) {
                        System.out.println("No hay peliculas disponibles en el catalogo en este momento.");
                        break;
                    }

                    System.out.println("\n=== SELECCIONA UNA PELICULA ===");
                    int indice = 1;
                    Iterator<Pelicula> itListaPelis = catalogo.iterator();
                    while (itListaPelis.hasNext()) {
                        Pelicula p = itListaPelis.next();
                        System.out.println(indice + ". " + p.getTitulo() + " (" + p.getClasificacion() + ") - " + p.getDuracion());
                        indice++;
                    }

                    System.out.print("Ingresa el numero de la pelicula que deseas ver: ");
                    int numPeli = 0;
                    try {
                        numPeli = Integer.parseInt(scanner.nextLine());
                    } catch (Exception e) {
                        numPeli = 0;
                    }

                    if (numPeli < 1 || numPeli > catalogo.size()) {
                        System.out.println("Seleccion invalida.");
                        break;
                    }

                    Pelicula peliculaElegida = catalogo.get(numPeli - 1);
                    System.out.println("\nHas seleccionado: " + peliculaElegida.getTitulo());

                    // Buscar una función que proyecte la película seleccionada
                    Funcion funcionEncontrada = null;
                    Sala salaEncontrada = null;
                    Iterator<Cine> itCines = sistema.getCines().iterator();
                    while (itCines.hasNext() && funcionEncontrada == null) {
                        Cine c = itCines.next();
                        Iterator<Sala> itSalas = c.getSalas().iterator();
                        while (itSalas.hasNext() && funcionEncontrada == null) {
                            Sala s = itSalas.next();
                            Iterator<Funcion> itFunciones = s.getProgramacion().iterator();
                            while (itFunciones.hasNext() && funcionEncontrada == null) {
                                Funcion f = itFunciones.next();
                                if (f.getPeliculaProyectada().getTitulo().equalsIgnoreCase(peliculaElegida.getTitulo())) {
                                    funcionEncontrada = f;
                                    salaEncontrada = s;
                                }
                            }
                        }
                    }

                    if (funcionEncontrada == null || salaEncontrada == null) {
                        System.out.println("Lo sentimos, no hay funciones programadas para esta pelicula actualmente.");
                        break;
                    }

                    System.out.println("Funcion encontrada: " + funcionEncontrada.getFecha() + " a las " + funcionEncontrada.getHorarioHrs() + ":" + funcionEncontrada.getHorarioMin());
                    System.out.println("\nAsientos para esta funcion:");
                    Iterator<Asiento> itAsientos = funcionEncontrada.getAsientos().iterator();
                    while (itAsientos.hasNext()) {
                        Asiento a = itAsientos.next();
                        System.out.print("[" + a.getIdentificador() + " - " + a.getEstadoAsiento() + "] ");
                    }
                    System.out.println();

                    System.out.print("\nIngresa el identificador del asiento a comprar (ejemplo: A1): ");
                    String idAsiento = scanner.nextLine();

                    Asiento asientoElegido = funcionEncontrada.obtenerAsiento(idAsiento);
                    if (asientoElegido != null) {
                        if (asientoElegido.getEstadoAsiento().equalsIgnoreCase("DISPONIBLE")) {
                            List<Asiento> seleccion = new ArrayList<>();
                            seleccion.add(asientoElegido);

                            System.out.println("\nSelecciona metodo de pago:");
                            System.out.println("1. Tarjeta de Credito");
                            System.out.println("2. Paypal");
                            System.out.println("3. Transferencia Bancaria");
                            System.out.print("Opcion: ");
                            String m = scanner.nextLine();

                            MetodoDePago metodo;
                            if (m.equals("2")) {
                                System.out.print("Ingresa tu correo de Paypal: ");
                                String correo = scanner.nextLine();
                                metodo = new Paypal(correo);
                            } else if (m.equals("3")) {
                                System.out.print("Ingresa tu cuenta bancaria: ");
                                String cuenta = scanner.nextLine();
                                metodo = new TransferenciaElectronica(cuenta);
                            } else {
                                System.out.print("Ingresa tu numero de tarjeta (16 digitos): ");
                                String tarjeta = scanner.nextLine();
                                metodo = new TarjetaCredito(tarjeta);
                            }

                            Boleto boleto = cliente.comprarBoleto(funcionEncontrada, salaEncontrada.getIdSala(), seleccion, metodo);
                            if (boleto != null) {
                                sistema.registrarBoleto(boleto);
                                System.out.println();
                                boleto.imprimirBoleto();
                            }
                        } else {
                            System.out.println("Ese asiento no esta disponible (Estado: " + asientoElegido.getEstadoAsiento() + ").");
                        }
                    } else {
                        System.out.println("Asiento no encontrado.");
                    }
                    break;

                case 3:
                    System.out.println("Cerrando sesion de cliente...");
                    break;

                default:
                    System.out.println("Opcion no valida.");
                    break;
            }
        } while (opcionCliente != 3);
    }
}
