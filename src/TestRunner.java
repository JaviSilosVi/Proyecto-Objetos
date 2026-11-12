import java.util.ArrayList;
import java.util.List;

/**
 * Ejecutor de pruebas unitarias por consola para el Avance 2.
 * Ejecuta los 8 metodos criticos probando tanto el Camino Feliz como el Camino Triste.
 * Imprime un reporte detallado con evidencia de ejecucion.
 */
public class TestRunner {

    private static int pruebasPasadas = 0;
    private static int pruebasTotales = 0;

    public static void main(String[] args) {
        System.out.println("        EVIDENCIA DE EJECUCION DE PRUEBAS UNITARIAS       ");

        probarMetodo1_AsientoVender();
        probarMetodo2_SalaAgregarFuncion();
        probarMetodo3_SistemaObtenerPelicula();
        probarMetodo4_BoletoCalcularTotal();
        probarMetodo5_TarjetaCreditoProcesarPago();
        probarMetodo6_SistemaEliminarCine();
        probarMetodo7_CineObtenerSala();
        probarMetodo8_AdministradorValidarPin();

        System.out.println("RESUMEN DE PRUEBAS: " + pruebasPasadas + " de " + pruebasTotales + " exitosas");
        if (pruebasPasadas == pruebasTotales) {
            System.out.println("RESULTADO: TODAS LAS PRUEBAS PASARON AL 100% ");
        } else {
            System.out.println("RESULTADO: HUBO FALLOS EN LA EJECUCION");
        }
    }

    // METODO 1: Asiento.vender()
    private static void probarMetodo1_AsientoVender() {
        System.out.println("\n[METODO 1] Asiento.vender()");
        // Camino Feliz
        pruebasTotales++;
        try {
            Asiento a = new Asiento("A1", "TRADICIONAL", 80.0);
            boolean vendido = a.vender();
            if (vendido && a.getEstadoAsiento().equals("VENDIDO")) {
                System.out.println("  Camino Feliz: Asiento disponible vendido exitosamente");
                pruebasPasadas++;
            } else {
                System.out.println(" Camino Feliz: No cambio el estado correctament");
            }
        } catch (Exception e) {
            System.out.println("  Camino Feliz: Excepcion inesperada: " + e.getMessage());
        }

        // Camino Triste
        pruebasTotales++;
        try {
            Asiento a = new Asiento("A1", "TRADICIONAL", 80.0);
            a.vender();
            a.vender(); // Debe lanzar excepcion
            System.out.println("   Camino Triste: Permitió sobreventa del asiento.");
        } catch (AsientoOcupadoException e) {
            System.out.println("  [Camino Triste: AsientoOcupadoException capturada con exito -> \"" + e.getMessage() + "\"");
            pruebasPasadas++;
        } catch (Exception e) {
            System.out.println("   Camino Triste: Lanzo excepcion incorrecta: " + e.getClass().getSimpleName());
        }
    }

    // METODO 2: Sala.agregarFuncion()
    private static void probarMetodo2_SalaAgregarFuncion() {
        System.out.println("\n[METODO 2] Sala.agregarFuncion()");
        Pelicula pelicula = new Pelicula("Avatar", "2:40", "B", "Ciencia Ficcion", "James Cameron", new ArrayList<>(), "Pandora");

        // Camino Feliz
        pruebasTotales++;
        try {
            Sala sala = new Sala("Sala 1", 5, 5, 80.0);
            Funcion f1 = new Funcion("2026-12-01", 18, 0, pelicula);
            boolean agregada = sala.agregarFuncion(f1);
            if (agregada && sala.getProgramacion().size() == 1) {
                System.out.println("  Camino Feliz: Funcion agregada en horario disponible");
                pruebasPasadas++;
            } else {
                System.out.println("  Camino Feliz: No se agrego la funcion");
            }
        } catch (Exception e) {
            System.out.println("  Camino Feliz: Excepcion inesperada: " + e.getMessage());
        }

        // Camino Triste
        pruebasTotales++;
        try {
            Sala sala = new Sala("Sala 1", 5, 5, 80.0);
            Funcion f1 = new Funcion("2026-12-01", 18, 0, pelicula);
            Funcion f2 = new Funcion("2026-12-01", 18, 0, pelicula); // Mismo horario
            sala.agregarFuncion(f1);
            sala.agregarFuncion(f2);
            System.out.println("  Camino Triste: Permitió funciones encimadas en la misma sala");
        } catch (ConflictoHorarioException e) {
            System.out.println("  Camino Triste: ConflictoHorarioException capturada con exito -> \"" + e.getMessage() + "\"");
            pruebasPasadas++;
        } catch (Exception e) {
            System.out.println("  Camino Triste: Lanzo excepcion incorrecta");
        }
    }

    // METODO 3: Sistema.obtenerPelicula()
    private static void probarMetodo3_SistemaObtenerPelicula() {
        System.out.println("\n[METODO 3] Sistema.obtenerPelicula()");
        Sistema sis = new Sistema();
        Pelicula p = new Pelicula("Batman", "2:10", "B", "Accion", "Matt Reeves", new ArrayList<>(), "Gotham");
        sis.agregarPelicula(p);

        // Camino Feliz
        pruebasTotales++;
        try {
            Pelicula res = sis.obtenerPelicula("Batman");
            if (res != null && res.getTitulo().equals("Batman")) {
                System.out.println("  [OK] Camino Feliz: Pelicula encontrada en el HashMap con exito");
                pruebasPasadas++;
            }
        } catch (Exception e) {
            System.out.println("  Camino Feliz: " + e.getMessage());
        }

        // Camino Triste
        pruebasTotales++;
        try {
            sis.obtenerPelicula("PeliculaInexistente");
            System.out.println(" Camino Triste: No lanzo excepcion para pelicula no existente");
        } catch (EntidadNoEncontradaException e) {
            System.out.println("   Camino Triste: EntidadNoEncontradaException capturada con exito -> \"" + e.getMessage() + "\"");
            pruebasPasadas++;
        } catch (Exception e) {
            System.out.println("  Camino Triste: Excepcion incorrecta");
        }
    }

    // METODO 4: Boleto.calcularTotal()
    private static void probarMetodo4_BoletoCalcularTotal() {
        System.out.println("\n[METODO 4] Boleto.calcularTotal()");
        Pelicula p = new Pelicula("Toy Story", "1:30", "A", "Animacion", "John Lasseter", new ArrayList<>(), "Juguetes");
        Funcion f = new Funcion("2026-11-20", 15, 0, p);

        // Camino Feliz
        pruebasTotales++;
        try {
            Asiento a1 = new Asiento("A1", "TRADICIONAL", 80.0);
            Asiento a2 = new Asiento("A2", "TRADICIONAL", 80.0);
            List<Asiento> lista = new ArrayList<>();
            lista.add(a1);
            lista.add(a2);
            Boleto b = new Boleto(p, "Sala 1", f, lista, new Paypal("pablo@cine.com"));
            double total = b.calcularTotal();
            if (total == 160.0) {
                System.out.println("   Camino Feliz: Total calculado con precision ($" + total + ").");
                pruebasPasadas++;
            } else {
                System.out.println("  Camino Feliz: Calculo erroneo: " + total);
            }
        } catch (Exception e) {
            System.out.println("  Camino Feliz: " + e.getMessage());
        }

        // Camino Triste
        pruebasTotales++;
        try {
            Boleto bInvalido = new Boleto(p, "Sala 1", f, new ArrayList<>(), new Paypal("pablo@cine.com"));
            bInvalido.calcularTotal();
            System.out.println("   Camino Triste: Calculo total con lista de asientos vaci");
        } catch (IllegalStateException e) {
            System.out.println("  Camino Triste: IllegalStateException capturada con exito -> \"" + e.getMessage() + "\"");
            pruebasPasadas++;
        } catch (Exception e) {
            System.out.println(" Camino Triste: Excepcion incorrecta: " + e.getClass().getSimpleName());
        }
    }

    // METODO 5: TarjetaCredito.procesarPago()
    private static void probarMetodo5_TarjetaCreditoProcesarPago() {
        System.out.println("\n[METODO 5] TarjetaCredito.procesarPago()");
        TarjetaCredito tc = new TarjetaCredito("1234567890123456");

        // Camino Feliz
        pruebasTotales++;
        try {
            boolean pagado = tc.procesarPago(150.0);
            if (pagado) {
                System.out.println("  Camino Feliz: Pago de monto valido procesado exitosamente");
                pruebasPasadas++;
            }
        } catch (Exception e) {
            System.out.println("   Camino Feliz: " + e.getMessage());
        }

        // Camino Triste
        pruebasTotales++;
        try {
            tc.procesarPago(-50.0);
            System.out.println("   Camino Triste: Permitió cobrar un monto negativo");
        } catch (IllegalArgumentException e) {
            System.out.println("  Camino Triste: IllegalArgumentException capturada con exito -> \"" + e.getMessage() + "\"");
            pruebasPasadas++;
        }
    }

    // METODO 6: Sistema.eliminarCine()
    private static void probarMetodo6_SistemaEliminarCine() {
        System.out.println("\n[METODO 6] Sistema.eliminarCine()");
        Sistema sis = new Sistema();
        sis.agregaCine(new Cine("Cinepolis Tangamanga"));

        // Camino Feliz
        pruebasTotales++;
        try {
            boolean eliminado = sis.eliminarCine("Cinepolis Tangamanga");
            if (eliminado && sis.getCines().isEmpty()) {
                System.out.println(" Camino Feliz: Cine eliminado correctamente del sistema");
                pruebasPasadas++;
            }
        } catch (Exception e) {
            System.out.println(" Camino Feliz: " + e.getMessage());
        }

        // Camino Triste
        pruebasTotales++;
        try {
            sis.eliminarCine("CineFantasma");
            System.out.println("   Camino Triste: Elimino un cine que no existia");
        } catch (EntidadNoEncontradaException e) {
            System.out.println("  Camino Triste: EntidadNoEncontradaException capturada con exito -> \"" + e.getMessage() + "\"");
            pruebasPasadas++;
        }
    }

    // METODO 7: Cine.obtenerSala()
    private static void probarMetodo7_CineObtenerSala() {
        System.out.println("\n[METODO 7] Cine.obtenerSala()");
        Cine cine = new Cine("Cinemex Plaza");
        Sala sala1 = new Sala("Sala VIP 1", 4, 4, 120.0);
        cine.agregaSala("Sala VIP 1", sala1);

        // Camino Feliz
        pruebasTotales++;
        try {
            Sala res = cine.obtenerSala("Sala VIP 1");
            if (res != null && res.getIdSala().equals("Sala VIP 1")) {
                System.out.println("   Camino Feliz: Sala encontrada en el mapa del cine");
                pruebasPasadas++;
            }
        } catch (Exception e) {
            System.out.println("   Camino Feliz: " + e.getMessage());
        }

        // Camino Triste
        pruebasTotales++;
        try {
            cine.obtenerSala("Sala Inexistente");
            System.out.println("   Camino Triste: Retorno sala inexistente");
        } catch (EntidadNoEncontradaException e) {
            System.out.println("   Camino Triste: EntidadNoEncontradaException capturada con exito -> \"" + e.getMessage() + "\"");
            pruebasPasadas++;
        }
    }

    // METODO 8: Administrador.validarPin()
    private static void probarMetodo8_AdministradorValidarPin() {
        System.out.println("\n[METODO 8] Administrador.validarPin()");
        Administrador admin = new Administrador("Carlos", "9876");

        // Camino Feliz
        pruebasTotales++;
        try {
            boolean valido = admin.validarPin("9876");
            boolean invalido = admin.validarPin("1111");
            if (valido && !invalido) {
                System.out.println("   Camino Feliz: PIN validado correctamente (true para coincidencia, false para incorrecto)");
                pruebasPasadas++;
            }
        } catch (Exception e) {
            System.out.println("  Camino Feliz: " + e.getMessage());
        }

        // Camino Triste
        pruebasTotales++;
        try {
            admin.validarPin(null);
            System.out.println("   Camino Triste: Permitió PIN nulo sin lanzar excepcion");
        } catch (IllegalArgumentException e) {
            System.out.println("  Camino Triste: IllegalArgumentException capturada con exito -> \"" + e.getMessage() + "\"");
            pruebasPasadas++;
        }
    }
}
