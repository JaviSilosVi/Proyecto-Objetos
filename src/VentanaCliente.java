import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VentanaCliente extends JFrame {
    private Sistema sistema;
    private Cliente clienteActivo;
    private VentanaLogin ventanaLogin;

    // Cine y película actualmente seleccionados
    private Cine cineSeleccionado = null;
    private Pelicula peliculaSeleccionada = null;

    // Componentes visuales
    private JComboBox<String> comboCines;
    private JLabel lblPeliculaActual;
    private JPanel panelCuadricula;

    public VentanaCliente(Sistema sistema, Cliente cliente, VentanaLogin ventanaLogin) {
        this.sistema = sistema;
        this.clienteActivo = cliente;
        this.ventanaLogin = ventanaLogin;

        // Configuración básica de la ventana
        this.setTitle("Sistema - Cliente");
        this.setSize(740, 540);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(10, 15, 35));

        // Encabezado
        JPanel panelHeader = new JPanel();
        panelHeader.setBounds(0, 0, 740, 45);
        panelHeader.setBackground(new Color(15, 30, 60));
        panelHeader.setLayout(null);

        JLabel lblTitulo = new JLabel("Sistema - Cartelera de Clientes");
        lblTitulo.setBounds(20, 10, 300, 25);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 15));
        panelHeader.add(lblTitulo);
        this.add(panelHeader);

        // Selector de Cine arriba
        JLabel lblCine = new JLabel("Selecciona tu Cine:");
        lblCine.setBounds(30, 55, 140, 25);
        lblCine.setForeground(new Color(255, 215, 100));
        lblCine.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(lblCine);

        List<String> nombresCines = new ArrayList<>(sistema.getCines().keySet());
        comboCines = new JComboBox<>(nombresCines.toArray(new String[0]));
        comboCines.setBounds(165, 55, 230, 26);
        comboCines.setBackground(new Color(25, 35, 55));
        comboCines.setForeground(Color.BLACK);
        comboCines.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(comboCines);

        // Barra de búsqueda "¿Qué vamos a ver hoy?"
        JLabel lblPregunta = new JLabel("¿Qué vamos a ver hoy?");
        lblPregunta.setBounds(30, 90, 160, 25);
        lblPregunta.setForeground(new Color(100, 180, 255));
        lblPregunta.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(lblPregunta);

        JTextField txtBuscador = new JTextField();
        txtBuscador.setBounds(180, 90, 215, 26);
        txtBuscador.setBackground(new Color(20, 30, 50));
        txtBuscador.setForeground(Color.WHITE);
        txtBuscador.setCaretColor(Color.WHITE);
        txtBuscador.setFont(new Font("Arial", Font.PLAIN, 12));
        this.add(txtBuscador);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(405, 90, 85, 26);
        btnBuscar.setBackground(new Color(40, 90, 140));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 11));
        this.add(btnBuscar);

        // Indicador de película seleccionada
        lblPeliculaActual = new JLabel("Película seleccionada: (Haz clic en una película abajo)");
        lblPeliculaActual.setBounds(30, 125, 650, 22);
        lblPeliculaActual.setForeground(new Color(180, 230, 150));
        lblPeliculaActual.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(lblPeliculaActual);

        // Panel contenedor de los cuadros de películas
        panelCuadricula = new JPanel();
        panelCuadricula.setBounds(30, 155, 380, 280);
        panelCuadricula.setLayout(null);
        panelCuadricula.setBackground(new Color(10, 15, 35));
        this.add(panelCuadricula);

        // Botones del lado derecho
        JButton btnHorario = new JButton("Revisar Horario");
        btnHorario.setBounds(450, 180, 220, 38);
        btnHorario.setBackground(new Color(25, 50, 85));
        btnHorario.setForeground(Color.WHITE);
        btnHorario.setFont(new Font("Arial", Font.BOLD, 13));
        this.add(btnHorario);

        JButton btnComprar = new JButton("Comprar Boleto");
        btnComprar.setBounds(450, 235, 220, 38);
        btnComprar.setBackground(new Color(30, 115, 65));
        btnComprar.setForeground(Color.WHITE);
        btnComprar.setFont(new Font("Arial", Font.BOLD, 13));
        this.add(btnComprar);

        JButton btnCartelera = new JButton("Ver Cartelera Completa");
        btnCartelera.setBounds(450, 290, 220, 35);
        btnCartelera.setBackground(new Color(50, 60, 85));
        btnCartelera.setForeground(Color.WHITE);
        btnCartelera.setFont(new Font("Arial", Font.BOLD, 11));
        this.add(btnCartelera);

        // Botón Cerrar Sesión
        JButton btnCerrarSesion = new JButton("Cerrar sesion");
        btnCerrarSesion.setBounds(550, 440, 120, 30);
        btnCerrarSesion.setBackground(new Color(60, 20, 20));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(btnCerrarSesion);

        // ==========================================
        // ACCIONES DE NAVEGACIÓN Y SELECCIÓN
        // ==========================================

        // Al cambiar de cine en el combo
        comboCines.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCineSeleccionado();
            }
        });

        // Buscar Película
        ActionListener accionBuscar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String criterio = txtBuscador.getText().trim();
                if (criterio.isEmpty()) {
                    JOptionPane.showMessageDialog(VentanaCliente.this, "Escribe el título de la película a buscar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    Pelicula encontrada = sistema.obtenerPelicula(criterio); // throws EntidadNoEncontradaException
                    seleccionarPelicula(encontrada);
                    JOptionPane.showMessageDialog(VentanaCliente.this, 
                        "¡Película encontrada!\n" + encontrada.getTitulo() + " (" + encontrada.getClasificacion() + " - " + encontrada.getGenero() + ")", 
                        "Resultado de Búsqueda", JOptionPane.INFORMATION_MESSAGE);
                } catch (EntidadNoEncontradaException ex) {
                    JOptionPane.showMessageDialog(VentanaCliente.this, ex.getMessage(), "Película No Encontrada", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        btnBuscar.addActionListener(accionBuscar);
        txtBuscador.addActionListener(accionBuscar);

        // Revisar Horario
        btnHorario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (peliculaSeleccionada == null) {
                    JOptionPane.showMessageDialog(VentanaCliente.this, "Por favor selecciona una película primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                revisarHorariosDePelicula(peliculaSeleccionada);
            }
        });

        // Comprar Boleto
        btnComprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (peliculaSeleccionada == null) {
                    JOptionPane.showMessageDialog(VentanaCliente.this, "Por favor selecciona una película primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                iniciarFlujoCompra(peliculaSeleccionada);
            }
        });

        // Ver Cartelera
        btnCartelera.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder sb = new StringBuilder("=== CARTELERA DISPONIBLE ===\n\n");
                for (Pelicula p : sistema.getCatalogoPeliculas().values()) {
                    sb.append("• ").append(p.getTitulo()).append(" [").append(p.getClasificacion()).append("]")
                      .append(" - ").append(p.getGenero()).append(" (").append(p.getDuracion()).append(")\n");
                }
                JOptionPane.showMessageDialog(VentanaCliente.this, sb.toString(), "Cartelera Completa", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });

        // Cargar cine inicial
        actualizarCineSeleccionado();
    }

    private void actualizarCineSeleccionado() {
        String nombreCine = (String) comboCines.getSelectedItem();
        if (nombreCine != null) {
            try {
                this.cineSeleccionado = sistema.obtenerCine(nombreCine);
            } catch (EntidadNoEncontradaException ex) {
                this.cineSeleccionado = null;
            }
        }
        cargarCuadriculaPeliculas();
    }

    private void cargarCuadriculaPeliculas() {
        panelCuadricula.removeAll();

        // Obtener películas con funciones en este cine (o del catálogo si no hay funciones aún)
        Set<Pelicula> peliculasCine = new HashSet<>();
        if (cineSeleccionado != null) {
            for (Sala sala : cineSeleccionado.getSalas().values()) {
                for (Funcion f : sala.getProgramacion()) {
                    peliculasCine.add(f.getPeliculaProyectada());
                }
            }
        }

        List<Pelicula> listaMostrar;
        if (!peliculasCine.isEmpty()) {
            listaMostrar = new ArrayList<>(peliculasCine);
        } else {
            listaMostrar = new ArrayList<>(sistema.getCatalogoPeliculas().values());
        }

        int[][] posiciones = {
            {10, 10}, {130, 10},
            {10, 130}, {130, 130},
            {250, 70}
        };

        for (int i = 0; i < posiciones.length; i++) {
            Pelicula p = (i < listaMostrar.size()) ? listaMostrar.get(i) : null;
            JPanel cuadro = crearCuadroPelicula(posiciones[i][0], posiciones[i][1], 110, 110, p);
            panelCuadricula.add(cuadro);
        }

        panelCuadricula.revalidate();
        panelCuadricula.repaint();

        if (!listaMostrar.isEmpty()) {
            seleccionarPelicula(listaMostrar.get(0));
        } else {
            peliculaSeleccionada = null;
            lblPeliculaActual.setText("No hay películas disponibles en este cine.");
        }
    }

    private void seleccionarPelicula(Pelicula p) {
        this.peliculaSeleccionada = p;
        if (p != null) {
            String cineStr = (cineSeleccionado != null) ? cineSeleccionado.getNombreCine() : "General";
            lblPeliculaActual.setText("Película: " + p.getTitulo().toUpperCase() + " [" + p.getClasificacion() + "] (" + cineStr + ")");
        }
    }

    private JPanel crearCuadroPelicula(int x, int y, int ancho, int alto, Pelicula pelicula) {
        JPanel cuadro = new JPanel();
        cuadro.setBounds(x, y, ancho, alto);
        cuadro.setLayout(null);
        cuadro.setBackground(new Color(20, 25, 45));
        cuadro.setBorder(BorderFactory.createLineBorder(new Color(70, 95, 140), 2));

        if (pelicula != null) {
            JLabel lblTituloPeli = new JLabel("<html><center>" + pelicula.getTitulo() + "</center></html>", SwingConstants.CENTER);
            lblTituloPeli.setBounds(5, 10, ancho - 10, 45);
            lblTituloPeli.setForeground(Color.WHITE);
            lblTituloPeli.setFont(new Font("Arial", Font.BOLD, 11));
            cuadro.add(lblTituloPeli);

            JLabel lblInfo = new JLabel("[" + pelicula.getClasificacion() + "] " + pelicula.getDuracion(), SwingConstants.CENTER);
            lblInfo.setBounds(5, 60, ancho - 10, 20);
            lblInfo.setForeground(new Color(150, 190, 255));
            lblInfo.setFont(new Font("Arial", Font.PLAIN, 10));
            cuadro.add(lblInfo);

            cuadro.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    seleccionarPelicula(pelicula);
                }
            });
        } else {
            JLabel lblVacio = new JLabel("Próximamente", SwingConstants.CENTER);
            lblVacio.setBounds(5, 45, ancho - 10, 20);
            lblVacio.setForeground(Color.GRAY);
            lblVacio.setFont(new Font("Arial", Font.ITALIC, 10));
            cuadro.add(lblVacio);
        }

        return cuadro;
    }

    private void revisarHorariosDePelicula(Pelicula pelicula) {
        StringBuilder info = new StringBuilder();
        String cineStr = (cineSeleccionado != null) ? cineSeleccionado.getNombreCine() : "Todos los cines";
        info.append("HORARIOS PARA: ").append(pelicula.getTitulo().toUpperCase()).append("\n");
        info.append("Cine: ").append(cineStr).append("\n\n");

        int conteo = 0;
        List<Cine> cinesConsultar = (cineSeleccionado != null) ? Collections.singletonList(cineSeleccionado) : new ArrayList<>(sistema.getCines().values());

        for (Cine cine : cinesConsultar) {
            for (Sala sala : cine.getSalas().values()) {
                for (Funcion f : sala.getProgramacion()) {
                    if (f.getPeliculaProyectada().getTitulo().equalsIgnoreCase(pelicula.getTitulo())) {
                        conteo++;
                        String hora = f.getHorarioHrs() + ":" + (f.getHorarioMin() < 10 ? "0" + f.getHorarioMin() : f.getHorarioMin());
                        info.append("• ").append(cine.getNombreCine()).append(" - ").append(sala.getIdSala())
                            .append(" (").append(sala.getTipoSala()).append(")\n")
                            .append("   Fecha: ").append(f.getFecha()).append(" a las ").append(hora).append(" hrs.")
                            .append(" | Precio: $").append(sala.getPrecioBase()).append("\n\n");
                    }
                }
            }
        }

        if (conteo == 0) {
            JOptionPane.showMessageDialog(this, "No hay funciones programadas para '" + pelicula.getTitulo() + "' en este cine.", "Sin Funciones", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, info.toString(), "Horarios Disponibles", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void iniciarFlujoCompra(Pelicula pelicula) {
        class InfoFuncion {
            Cine cine;
            Sala sala;
            Funcion funcion;
            InfoFuncion(Cine c, Sala s, Funcion f) {
                this.cine = c;
                this.sala = s;
                this.funcion = f;
            }
            @Override
            public String toString() {
                String hora = funcion.getHorarioHrs() + ":" + (funcion.getHorarioMin() < 10 ? "0" + funcion.getHorarioMin() : funcion.getHorarioMin());
                return cine.getNombreCine() + " - " + sala.getIdSala() + " (" + funcion.getFecha() + " " + hora + " hrs) - $" + sala.getPrecioBase();
            }
        }

        List<InfoFuncion> funciones = new ArrayList<>();
        List<Cine> cinesConsultar = (cineSeleccionado != null) ? Collections.singletonList(cineSeleccionado) : new ArrayList<>(sistema.getCines().values());

        for (Cine c : cinesConsultar) {
            for (Sala s : c.getSalas().values()) {
                for (Funcion f : s.getProgramacion()) {
                    if (f.getPeliculaProyectada().getTitulo().equalsIgnoreCase(pelicula.getTitulo())) {
                        funciones.add(new InfoFuncion(c, s, f));
                    }
                }
            }
        }

        if (funciones.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay funciones programadas para '" + pelicula.getTitulo() + "' en este cine.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JComboBox<InfoFuncion> comboFunciones = new JComboBox<>(funciones.toArray(new InfoFuncion[0]));
        int opcFuncion = JOptionPane.showConfirmDialog(this, comboFunciones, "Selecciona la Función Deseada:", JOptionPane.OK_CANCEL_OPTION);
        if (opcFuncion != JOptionPane.OK_OPTION) return;

        InfoFuncion eleccion = (InfoFuncion) comboFunciones.getSelectedItem();
        Funcion fElegida = eleccion.funcion;
        Sala sElegida = eleccion.sala;

        // =========================================================
        // LEER ASIENTOS REALES DE LA FUNCIÓN (donde cambian a VENDIDO)
        // =========================================================
        List<Asiento> listaAsientosFuncion = new ArrayList<>(fElegida.getAsientos().values());

        // Ordenar alfabéticamente (A1, A2, A3... B1, B2...)
        Collections.sort(listaAsientosFuncion, new Comparator<Asiento>() {
            @Override
            public int compare(Asiento a1, Asiento a2) {
                return a1.getIdentificador().compareToIgnoreCase(a2.getIdentificador());
            }
        });

        StringBuilder croquis = new StringBuilder("DISTRIBUCIÓN DE ASIENTOS (" + sElegida.getIdSala() + "):\n\n");
        int contador = 0;
        for (Asiento a : listaAsientosFuncion) {
            croquis.append("[").append(a.getIdentificador()).append(" : ").append(a.getEstadoAsiento()).append("]  ");
            contador++;
            if (contador % 4 == 0) {
                croquis.append("\n");
            }
        }

        String asientoDeseado = JOptionPane.showInputDialog(this, 
            croquis.toString() + "\n\nIngresa el identificador del asiento que deseas comprar (ej. A1):");
        if (asientoDeseado == null || asientoDeseado.trim().isEmpty()) return;

        asientoDeseado = asientoDeseado.trim().toUpperCase();
        Asiento asiento = fElegida.obtenerAsiento(asientoDeseado);

        if (asiento == null) {
            JOptionPane.showMessageDialog(this, "El asiento '" + asientoDeseado + "' no existe en esta sala.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!asiento.getEstadoAsiento().equalsIgnoreCase("DISPONIBLE")) {
            JOptionPane.showMessageDialog(this, 
                "El asiento '" + asientoDeseado + "' ya está " + asiento.getEstadoAsiento() + ".\nPor favor elige otro.", 
                "Asiento No Disponible", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Selección de método de pago
        String[] metodos = {"Tarjeta de Crédito", "Paypal", "Transferencia Electrónica"};
        String metodoElegido = (String) JOptionPane.showInputDialog(this, 
            "Precio a pagar: $" + asiento.getPrecio() + "\nSelecciona el Método de Pago:", 
            "Método de Pago", 
            JOptionPane.QUESTION_MESSAGE, null, metodos, metodos[0]);
        if (metodoElegido == null) return;

        MetodoDePago pago;
        if (metodoElegido.equals("Paypal")) {
            String correo = JOptionPane.showInputDialog(this, "Ingresa tu correo de PayPal:");
            if (correo == null || correo.trim().isEmpty()) return;
            pago = new Paypal(correo.trim());
        } else if (metodoElegido.equals("Transferencia Electrónica")) {
            String clabe = JOptionPane.showInputDialog(this, "Ingresa tu CLABE / Cuenta bancaria:");
            if (clabe == null || clabe.trim().isEmpty()) return;
            pago = new TransferenciaElectronica(clabe.trim());
        } else {
            String numTarjeta = JOptionPane.showInputDialog(this, "Ingresa tu número de Tarjeta (16 dígitos):");
            if (numTarjeta == null || numTarjeta.trim().isEmpty()) return;
            pago = new TarjetaCredito(numTarjeta.trim());
        }

        // Intentar compra (Fail-Fast con AsientoOcupadoException)
        List<Asiento> seleccion = new ArrayList<>();
        seleccion.add(asiento);

        try {
            Boleto boleto = clienteActivo.comprarBoleto(fElegida, sElegida.getIdSala(), seleccion, pago);
            if (boleto != null) {
                sistema.registrarBoleto(boleto);
                JOptionPane.showMessageDialog(this, 
                    "¡COMPRA REALIZADA CON ÉXITO!\n\n" +
                    "Película: " + pelicula.getTitulo() + "\n" +
                    "Cine: " + eleccion.cine.getNombreCine() + "\n" +
                    "Sala: " + sElegida.getIdSala() + "\n" +
                    "Asiento: " + asiento.getIdentificador() + "\n" +
                    "Total Pagado: $" + boleto.getTotalPagado() + "\n\n" +
                    "¡Disfruta tu función!", 
                    "Ticket de Compra", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (AsientoOcupadoException ex) {
            JOptionPane.showMessageDialog(this, 
                "Error de Venta: " + ex.getMessage(), 
                "Asiento Ocupado", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, 
                "Error en el Pago: " + ex.getMessage(), 
                "Pago Rechazado", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cerrarSesion() {
        this.dispose();
        if (this.ventanaLogin != null) {
            this.ventanaLogin.setVisible(true);
        }
    }
}
