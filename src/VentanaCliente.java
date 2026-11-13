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
import java.util.List;

public class VentanaCliente extends JFrame {
    private Sistema sistema;
    private Cliente clienteActivo;
    private VentanaLogin ventanaLogin;

    // Película actualmente seleccionada por el cliente
    private Pelicula peliculaSeleccionada = null;
    private JLabel lblPeliculaActual;

    public VentanaCliente(Sistema sistema, Cliente cliente, VentanaLogin ventanaLogin) {
        this.sistema = sistema;
        this.clienteActivo = cliente;
        this.ventanaLogin = ventanaLogin;

        // Configuración básica de la ventana
        this.setTitle("Sistema - Cliente");
        this.setSize(720, 520);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(10, 15, 35));

        // Encabezado
        JPanel panelHeader = new JPanel();
        panelHeader.setBounds(0, 0, 720, 45);
        panelHeader.setBackground(new Color(15, 30, 60));
        panelHeader.setLayout(null);

        JLabel lblTitulo = new JLabel("Sistema - Cartelera de Clientes");
        lblTitulo.setBounds(20, 10, 300, 25);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 15));
        panelHeader.add(lblTitulo);
        this.add(panelHeader);

        // Barra de búsqueda "¿Qué vamos a ver hoy?"
        JLabel lblPregunta = new JLabel("¿Qué vamos a ver hoy?");
        lblPregunta.setBounds(30, 60, 180, 25);
        lblPregunta.setForeground(new Color(100, 180, 255));
        lblPregunta.setFont(new Font("Arial", Font.BOLD, 13));
        this.add(lblPregunta);

        JTextField txtBuscador = new JTextField();
        txtBuscador.setBounds(210, 60, 340, 28);
        txtBuscador.setBackground(new Color(20, 30, 50));
        txtBuscador.setForeground(Color.WHITE);
        txtBuscador.setCaretColor(Color.WHITE);
        txtBuscador.setFont(new Font("Arial", Font.PLAIN, 12));
        this.add(txtBuscador);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(560, 60, 90, 28);
        btnBuscar.setBackground(new Color(40, 90, 140));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 11));
        this.add(btnBuscar);

        // Indicador de película seleccionada
        lblPeliculaActual = new JLabel("Película seleccionada: (Haz clic en una película abajo)");
        lblPeliculaActual.setBounds(40, 100, 600, 22);
        lblPeliculaActual.setForeground(new Color(180, 230, 150));
        lblPeliculaActual.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(lblPeliculaActual);

        // Generar cuadros dinámicos con las películas reales del catálogo
        List<Pelicula> peliculas = new ArrayList<>(sistema.getCatalogoPeliculas().values());
        int[][] posiciones = {
            {40, 135}, {165, 135},
            {40, 250}, {165, 250},
            {290, 200}
        };

        for (int i = 0; i < posiciones.length; i++) {
            Pelicula p = (i < peliculas.size()) ? peliculas.get(i) : null;
            JPanel cuadro = crearCuadroPelicula(posiciones[i][0], posiciones[i][1], 110, 100, p);
            this.add(cuadro);
        }

        // Si hay al menos una película, seleccionar la primera por defecto
        if (!peliculas.isEmpty()) {
            seleccionarPelicula(peliculas.get(0));
        }

        // Botón "Revisar Horario"
        JButton btnHorario = new JButton("Revisar horario");
        btnHorario.setBounds(430, 150, 210, 35);
        btnHorario.setBackground(new Color(25, 45, 75));
        btnHorario.setForeground(Color.WHITE);
        btnHorario.setFont(new Font("Arial", Font.BOLD, 13));
        this.add(btnHorario);

        // Botón "Comprar"
        JButton btnComprar = new JButton("Comprar Boleto");
        btnComprar.setBounds(430, 200, 210, 35);
        btnComprar.setBackground(new Color(30, 110, 60));
        btnComprar.setForeground(Color.WHITE);
        btnComprar.setFont(new Font("Arial", Font.BOLD, 13));
        this.add(btnComprar);

        // Botón "Ver Cartelera Completa"
        JButton btnCartelera = new JButton("Ver Cartelera Completa");
        btnCartelera.setBounds(430, 250, 210, 32);
        btnCartelera.setBackground(new Color(50, 60, 80));
        btnCartelera.setForeground(Color.WHITE);
        btnCartelera.setFont(new Font("Arial", Font.BOLD, 11));
        this.add(btnCartelera);

        // Botón Cerrar Sesión
        JButton btnCerrarSesion = new JButton("Cerrar sesion");
        btnCerrarSesion.setBounds(530, 420, 130, 30);
        btnCerrarSesion.setBackground(new Color(60, 20, 20));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(btnCerrarSesion);

        // ==========================================
        // ACCIONES DE LOS BOTONES
        // ==========================================

        // Acción: Buscar Película por Título (con EntidadNoEncontradaException)
        ActionListener accionBuscar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String criterio = txtBuscador.getText().trim();
                if (criterio.isEmpty()) {
                    JOptionPane.showMessageDialog(VentanaCliente.this, "Por favor escribe el título de una película a buscar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    Pelicula encontrada = sistema.obtenerPelicula(criterio); // throws EntidadNoEncontradaException
                    seleccionarPelicula(encontrada);
                    JOptionPane.showMessageDialog(VentanaCliente.this, 
                        "¡Película encontrada!\n" + encontrada.getTitulo() + " (" + encontrada.getClasificacion() + " - " + encontrada.getGenero() + ")", 
                        "Resultado de Búsqueda", JOptionPane.INFORMATION_MESSAGE);
                } catch (EntidadNoEncontradaException ex) {
                    JOptionPane.showMessageDialog(VentanaCliente.this, 
                        ex.getMessage(), 
                        "Película No Encontrada", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        btnBuscar.addActionListener(accionBuscar);
        txtBuscador.addActionListener(accionBuscar);

        // Acción: Revisar Horario
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

        // Acción: Comprar Boleto (con captura de AsientoOcupadoException)
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

        // Acción: Ver Cartelera Completa
        btnCartelera.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder sb = new StringBuilder("=== CARTELERA EN CARTELERA ===\n\n");
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
    }

    private void seleccionarPelicula(Pelicula p) {
        this.peliculaSeleccionada = p;
        if (p != null) {
            lblPeliculaActual.setText("Película seleccionada: " + p.getTitulo().toUpperCase() + " (" + p.getClasificacion() + " - " + p.getGenero() + ")");
        }
    }

    private JPanel crearCuadroPelicula(int x, int y, int ancho, int alto, Pelicula pelicula) {
        JPanel cuadro = new JPanel();
        cuadro.setBounds(x, y, ancho, alto);
        cuadro.setLayout(null);
        cuadro.setBackground(new Color(20, 25, 40));
        cuadro.setBorder(BorderFactory.createLineBorder(new Color(70, 90, 130), 2));

        if (pelicula != null) {
            JLabel lblTituloPeli = new JLabel("<html><center>" + pelicula.getTitulo() + "</center></html>", SwingConstants.CENTER);
            lblTituloPeli.setBounds(5, 10, ancho - 10, 40);
            lblTituloPeli.setForeground(Color.WHITE);
            lblTituloPeli.setFont(new Font("Arial", Font.BOLD, 11));
            cuadro.add(lblTituloPeli);

            JLabel lblInfo = new JLabel("[" + pelicula.getClasificacion() + "] " + pelicula.getDuracion(), SwingConstants.CENTER);
            lblInfo.setBounds(5, 55, ancho - 10, 20);
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
            JLabel lblVacio = new JLabel("Disponible", SwingConstants.CENTER);
            lblVacio.setBounds(5, 40, ancho - 10, 20);
            lblVacio.setForeground(Color.GRAY);
            lblVacio.setFont(new Font("Arial", Font.ITALIC, 10));
            cuadro.add(lblVacio);
        }

        return cuadro;
    }

    private void revisarHorariosDePelicula(Pelicula pelicula) {
        StringBuilder info = new StringBuilder();
        info.append("HORARIOS PARA: ").append(pelicula.getTitulo().toUpperCase()).append("\n\n");

        int conteo = 0;
        for (Cine cine : sistema.getCines().values()) {
            for (Sala sala : cine.getSalas().values()) {
                for (Funcion f : sala.getProgramacion()) {
                    if (f.getPeliculaProyectada().getTitulo().equalsIgnoreCase(pelicula.getTitulo())) {
                        conteo++;
                        String hora = f.getHorarioHrs() + ":" + (f.getHorarioMin() < 10 ? "0" + f.getHorarioMin() : f.getHorarioMin());
                        info.append("• Cine: ").append(cine.getNombreCine())
                            .append(" | ").append(sala.getIdSala()).append(" (").append(sala.getTipoSala()).append(")")
                            .append("\n   Fecha: ").append(f.getFecha()).append(" a las ").append(hora).append(" hrs.")
                            .append(" | Precio: $").append(sala.getPrecioBase()).append("\n\n");
                    }
                }
            }
        }

        if (conteo == 0) {
            JOptionPane.showMessageDialog(this, 
                "No hay funciones programadas para '" + pelicula.getTitulo() + "' en este momento.", 
                "Sin Funciones", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, info.toString(), "Horarios Disponibles", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void iniciarFlujoCompra(Pelicula pelicula) {
        // 1. Buscar las funciones de esa película
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
        for (Cine c : sistema.getCines().values()) {
            for (Sala s : c.getSalas().values()) {
                for (Funcion f : s.getProgramacion()) {
                    if (f.getPeliculaProyectada().getTitulo().equalsIgnoreCase(pelicula.getTitulo())) {
                        funciones.add(new InfoFuncion(c, s, f));
                    }
                }
            }
        }

        if (funciones.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No hay funciones programadas para '" + pelicula.getTitulo() + "'. Elige otra película.", 
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Selección de función mediante diálogo
        JComboBox<InfoFuncion> comboFunciones = new JComboBox<>(funciones.toArray(new InfoFuncion[0]));
        int opcFuncion = JOptionPane.showConfirmDialog(this, comboFunciones, "Selecciona la Función Deseada:", JOptionPane.OK_CANCEL_OPTION);
        if (opcFuncion != JOptionPane.OK_OPTION) return;

        InfoFuncion eleccion = (InfoFuncion) comboFunciones.getSelectedItem();
        Funcion fElegida = eleccion.funcion;
        Sala sElegida = eleccion.sala;

        // 2. Mostrar asientos disponibles
        StringBuilder croquis = new StringBuilder("DISTRIBUCIÓN DE ASIENTOS (" + sElegida.getIdSala() + "):\n\n");
        for (Asiento a : fElegida.getSala().getAsientos().values()) {
            croquis.append("[").append(a.getIdentificador()).append(" : ").append(a.getEstadoAsiento()).append("]  ");
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
            JOptionPane.showMessageDialog(this, "El asiento '" + asientoDeseado + "' ya está " + asiento.getEstadoAsiento() + ".\nPor favor elige otro.", "Asiento No Disponible", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 3. Selección de método de pago
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

        // 4. Intentar compra con validación de excepciones (Fail-Fast)
        List<Asiento> seleccion = new ArrayList<>();
        seleccion.add(asiento);

        try {
            Boleto boleto = clienteActivo.comprarBoleto(fElegida, sElegida.getIdSala(), seleccion, pago); // throws AsientoOcupadoException
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
            // RETROALIMENTACIÓN VISUAL DE EXCEPCIÓN DEL PILAR 2 Y 3:
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
        this.dispose(); // Cierra esta ventana
        if (this.ventanaLogin != null) {
            this.ventanaLogin.setVisible(true); // Vuelve a mostrar el Login
        }
    }
}
