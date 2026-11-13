import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VentanaAdmin extends JFrame {
    private Sistema sistema;
    private Administrador adminActivo;
    private VentanaLogin ventanaLogin;

    // Estado actual de la vista
    private String seccionActual = "CINES"; // CINES, SALAS, PELICULAS, FUNCIONES
    private Cine cineSeleccionado = null;

    // Componentes de la interfaz
    private JLabel lblSubtitulo;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JButton btnCines;
    private JButton btnSalas;
    private JButton btnPeliculas;
    private JButton btnFunciones;
    private JButton btnAnadir;
    private JButton btnEliminar;

    public VentanaAdmin(Sistema sistema, Administrador admin, VentanaLogin ventanaLogin) {
        this.sistema = sistema;
        this.adminActivo = admin;
        this.ventanaLogin = ventanaLogin;

        // Configuración básica de la ventana
        this.setTitle("Sistema - Cine (Administrador)");
        this.setSize(750, 520);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(10, 15, 35));

        // Encabezado
        JPanel panelHeader = new JPanel();
        panelHeader.setBounds(0, 0, 750, 45);
        panelHeader.setBackground(new Color(15, 30, 60));
        panelHeader.setLayout(null);

        JLabel lblTitulo = new JLabel("Sistema - Cine (Panel de Administración)");
        lblTitulo.setBounds(20, 10, 350, 25);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 15));
        panelHeader.add(lblTitulo);
        this.add(panelHeader);

        // Saludo personalizado con el nombre del admin logueado
        JLabel lblBienvenido = new JLabel("Bienvenido Administrador: " + (admin != null ? admin.getNombre() : "Admin"));
        lblBienvenido.setBounds(25, 55, 350, 25);
        lblBienvenido.setForeground(new Color(100, 180, 255));
        lblBienvenido.setFont(new Font("Arial", Font.BOLD, 14));
        this.add(lblBienvenido);

        // Subtítulo indicador de cine activo
        lblSubtitulo = new JLabel("Cine activo: (Ninguno seleccionado)");
        lblSubtitulo.setBounds(25, 80, 450, 20);
        lblSubtitulo.setForeground(new Color(180, 220, 150));
        lblSubtitulo.setFont(new Font("Arial", Font.ITALIC, 12));
        this.add(lblSubtitulo);

        // Etiqueta "Opciones a modificar"
        JLabel lblOpciones = new JLabel("  Opciones a modificar");
        lblOpciones.setBounds(25, 110, 160, 28);
        lblOpciones.setOpaque(true);
        lblOpciones.setBackground(new Color(18, 55, 75));
        lblOpciones.setForeground(new Color(120, 220, 210));
        lblOpciones.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(lblOpciones);

        // Botones del panel izquierdo
        btnCines = new JButton("1. Cines");
        btnCines.setBounds(35, 150, 140, 32);
        btnCines.setBackground(new Color(60, 110, 160));
        btnCines.setForeground(Color.WHITE);
        btnCines.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(btnCines);

        btnSalas = new JButton("2. Salas");
        btnSalas.setBounds(35, 195, 140, 32);
        btnSalas.setBackground(new Color(80, 95, 115));
        btnSalas.setForeground(Color.WHITE);
        btnSalas.setFont(new Font("Arial", Font.BOLD, 12));
        btnSalas.setEnabled(false); // Se activa al elegir un cine
        this.add(btnSalas);

        btnPeliculas = new JButton("3. Peliculas");
        btnPeliculas.setBounds(35, 240, 140, 32);
        btnPeliculas.setBackground(new Color(80, 95, 115));
        btnPeliculas.setForeground(Color.WHITE);
        btnPeliculas.setFont(new Font("Arial", Font.BOLD, 12));
        btnPeliculas.setEnabled(false);
        this.add(btnPeliculas);

        btnFunciones = new JButton("4. Funciones");
        btnFunciones.setBounds(35, 285, 140, 32);
        btnFunciones.setBackground(new Color(80, 95, 115));
        btnFunciones.setForeground(Color.WHITE);
        btnFunciones.setFont(new Font("Arial", Font.BOLD, 12));
        btnFunciones.setEnabled(false);
        this.add(btnFunciones);

        // Tabla dinámica a la derecha
        modeloTabla = new DefaultTableModel();
        tabla = new JTable(modeloTabla);
        tabla.setBackground(new Color(25, 35, 50));
        tabla.setForeground(Color.WHITE);
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.setRowHeight(25);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getTableHeader().setBackground(new Color(30, 50, 80));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBounds(210, 110, 500, 250);
        this.add(scrollTabla);

        // Botón "Seleccionar Cine" (para vincular las salas y funciones al cine marcado en la tabla)
        JButton btnSeleccionarCine = new JButton("Usar Cine marcado");
        btnSeleccionarCine.setBounds(210, 375, 160, 30);
        btnSeleccionarCine.setBackground(new Color(40, 100, 70));
        btnSeleccionarCine.setForeground(Color.WHITE);
        btnSeleccionarCine.setFont(new Font("Arial", Font.BOLD, 11));
        this.add(btnSeleccionarCine);

        // Botón "Añadir"
        btnAnadir = new JButton("+ Añadir");
        btnAnadir.setBounds(385, 375, 120, 30);
        btnAnadir.setBackground(new Color(30, 80, 130));
        btnAnadir.setForeground(Color.WHITE);
        btnAnadir.setFont(new Font("Arial", Font.BOLD, 11));
        this.add(btnAnadir);

        // Botón "Eliminar" (con manejo de excepciones defensivas)
        btnEliminar = new JButton("- Eliminar");
        btnEliminar.setBounds(515, 375, 120, 30);
        btnEliminar.setBackground(new Color(120, 40, 40));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 11));
        this.add(btnEliminar);

        // Botón Cerrar Sesión
        JButton btnCerrarSesion = new JButton("Cerrar sesion");
        btnCerrarSesion.setBounds(580, 435, 130, 30);
        btnCerrarSesion.setBackground(new Color(50, 20, 20));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(btnCerrarSesion);

        // ==========================================
        // LISTENERS DE NAVEGACIÓN Y TABLA
        // ==========================================

        btnCines.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarCines();
            }
        });

        btnSalas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarSalas();
            }
        });

        btnPeliculas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPeliculas();
            }
        });

        btnFunciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFunciones();
            }
        });

        btnSeleccionarCine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0 && seccionActual.equals("CINES")) {
                    String nombreCine = (String) modeloTabla.getValueAt(fila, 0);
                    try {
                        cineSeleccionado = sistema.obtenerCine(nombreCine);
                        lblSubtitulo.setText("Cine activo: " + cineSeleccionado.getNombreCine().toUpperCase());
                        btnSalas.setEnabled(true);
                        btnPeliculas.setEnabled(true);
                        btnFunciones.setEnabled(true);
                        btnSalas.setBackground(new Color(60, 110, 160));
                        btnPeliculas.setBackground(new Color(60, 110, 160));
                        btnFunciones.setBackground(new Color(60, 110, 160));
                        JOptionPane.showMessageDialog(VentanaAdmin.this, 
                            "Cine '" + cineSeleccionado.getNombreCine() + "' seleccionado.\nAhora puedes gestionar sus Salas, Películas y Funciones.", 
                            "Cine Activo", JOptionPane.INFORMATION_MESSAGE);
                    } catch (EntidadNoEncontradaException ex) {
                        JOptionPane.showMessageDialog(VentanaAdmin.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(VentanaAdmin.this, "Por favor selecciona un cine de la tabla primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnAnadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarAnadir();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarEliminar();
            }
        });

        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });

        // Mostrar Cines por defecto al abrir
        mostrarCines();
    }

    // ==========================================
    // MÉTODOS PARA CARGAR DATOS EN LA TABLA
    // ==========================================

    private void mostrarCines() {
        seccionActual = "CINES";
        btnAnadir.setText("+ Añadir Cine");
        btnEliminar.setText("- Eliminar Cine");

        String[] columnas = {"Nombre del Cine", "Total de Salas Registradas"};
        modeloTabla.setDataVector(null, columnas);

        for (Cine c : sistema.getCines().values()) {
            modeloTabla.addRow(new Object[]{c.getNombreCine(), c.getSalas().size()});
        }
    }

    private void mostrarSalas() {
        if (cineSeleccionado == null) return;
        seccionActual = "SALAS";
        btnAnadir.setText("+ Añadir Sala");
        btnEliminar.setText("- Eliminar Sala");

        String[] columnas = {"ID Sala", "Tipo de Sala", "Total Asientos", "Precio Base"};
        modeloTabla.setDataVector(null, columnas);

        for (Sala s : cineSeleccionado.getSalas().values()) {
            modeloTabla.addRow(new Object[]{s.getIdSala(), s.getTipoSala(), s.getTotalAsientos(), "$" + s.getPrecioBase()});
        }
    }

    private void mostrarPeliculas() {
        seccionActual = "PELICULAS";
        btnAnadir.setText("+ Añadir Película");
        btnEliminar.setText("- Eliminar Película");

        String[] columnas = {"Título", "Clasificación", "Duración", "Género"};
        modeloTabla.setDataVector(null, columnas);

        for (Pelicula p : sistema.getCatalogoPeliculas().values()) {
            modeloTabla.addRow(new Object[]{p.getTitulo(), p.getClasificacion(), p.getDuracion(), p.getGenero()});
        }
    }

    private void mostrarFunciones() {
        if (cineSeleccionado == null) return;
        seccionActual = "FUNCIONES";
        btnAnadir.setText("+ Programar Función");
        btnEliminar.setText("- Cancelar Función");

        String[] columnas = {"Sala", "Película Proyectada", "Fecha", "Horario"};
        modeloTabla.setDataVector(null, columnas);

        for (Sala s : cineSeleccionado.getSalas().values()) {
            for (Funcion f : s.getProgramacion()) {
                String horario = f.getHorarioHrs() + ":" + (f.getHorarioMin() < 10 ? "0" + f.getHorarioMin() : f.getHorarioMin());
                modeloTabla.addRow(new Object[]{s.getIdSala(), f.getPeliculaProyectada().getTitulo(), f.getFecha(), horario});
            }
        }
    }

    // ==========================================
    // DIÁLOGOS DE AÑADIR (PERSISTENCIA EN MEMORIA)
    // ==========================================

    private void ejecutarAnadir() {
        switch (seccionActual) {
            case "CINES":
                String nombreCine = JOptionPane.showInputDialog(this, "Nombre del nuevo Cine (ej. Cinemex Plaza):");
                if (nombreCine != null && !nombreCine.trim().isEmpty()) {
                    sistema.agregaCine(new Cine(nombreCine.trim()));
                    JOptionPane.showMessageDialog(this, "¡Cine '" + nombreCine.trim() + "' registrado exitosamente!");
                    mostrarCines();
                }
                break;

            case "SALAS":
                if (cineSeleccionado == null) {
                    JOptionPane.showMessageDialog(this, "Primero selecciona un cine.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                abrirDialogoNuevaSala();
                break;

            case "PELICULAS":
                abrirDialogoNuevaPelicula();
                break;

            case "FUNCIONES":
                if (cineSeleccionado == null) {
                    JOptionPane.showMessageDialog(this, "Primero selecciona un cine.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                abrirDialogoNuevaFuncion();
                break;
        }
    }

    private void abrirDialogoNuevaSala() {
        JTextField txtId = new JTextField();
        JTextField txtFilas = new JTextField("3");
        JTextField txtCols = new JTextField("4");
        JTextField txtPrecio = new JTextField("80.0");

        Object[] formulario = {
            "ID de la Sala (ej. Sala 2):", txtId,
            "Número de Filas:", txtFilas,
            "Asientos por Fila:", txtCols,
            "Precio Base ($):", txtPrecio
        };

        int opcion = JOptionPane.showConfirmDialog(this, formulario, "Añadir Nueva Sala a " + cineSeleccionado.getNombreCine(), JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            try {
                String idSala = txtId.getText().trim();
                int filas = Integer.parseInt(txtFilas.getText().trim());
                int cols = Integer.parseInt(txtCols.getText().trim());
                double precio = Double.parseDouble(txtPrecio.getText().trim());

                if (idSala.isEmpty()) throw new IllegalArgumentException("El ID de la sala no puede estar vacío.");

                Sala nuevaSala = new Sala(idSala);
                nuevaSala.generarAsientos(filas, cols, "TRADICIONAL", precio);
                cineSeleccionado.agregaSala(idSala, nuevaSala);

                JOptionPane.showMessageDialog(this, "¡Sala '" + idSala + "' agregada con " + (filas * cols) + " asientos!");
                mostrarSalas();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en los datos de la sala: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void abrirDialogoNuevaPelicula() {
        JTextField txtTitulo = new JTextField();
        JTextField txtDuracion = new JTextField("1:45");
        JTextField txtClasif = new JTextField("A");
        JTextField txtGenero = new JTextField("Acción");
        JTextField txtDirector = new JTextField("Director");

        Object[] formulario = {
            "Título de la Película:", txtTitulo,
            "Duración (ej. 2:05):", txtDuracion,
            "Clasificación (A, B, C):", txtClasif,
            "Género:", txtGenero,
            "Director:", txtDirector
        };

        int opcion = JOptionPane.showConfirmDialog(this, formulario, "Registrar Película en Catálogo", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            String tit = txtTitulo.getText().trim();
            if (tit.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El título no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Pelicula peli = new Pelicula(tit, txtDuracion.getText().trim(), txtClasif.getText().trim(), 
                txtGenero.getText().trim(), txtDirector.getText().trim(), new ArrayList<>(), "Sinopsis pendiente");
            sistema.agregarPelicula(peli);
            JOptionPane.showMessageDialog(this, "¡Película '" + tit + "' agregada al catálogo!");
            mostrarPeliculas();
        }
    }

    private void abrirDialogoNuevaFuncion() {
        if (cineSeleccionado.getSalas().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Este cine no tiene salas registradas. Agrega una sala primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (sistema.getCatalogoPeliculas().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay películas en catálogo. Registra una película primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JTextField txtSala = new JTextField();
        JTextField txtPelicula = new JTextField();
        JTextField txtFecha = new JTextField("2026-11-20");
        JTextField txtHora = new JTextField("18");
        JTextField txtMinutos = new JTextField("30");

        Object[] formulario = {
            "ID de la Sala en este cine:", txtSala,
            "Título de la Película:", txtPelicula,
            "Fecha (AAAA-MM-DD):", txtFecha,
            "Hora (0-23):", txtHora,
            "Minutos (0-59):", txtMinutos
        };

        int opcion = JOptionPane.showConfirmDialog(this, formulario, "Programar Función", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            try {
                String idSala = txtSala.getText().trim();
                String titPeli = txtPelicula.getText().trim();
                String fecha = txtFecha.getText().trim();
                int hora = Integer.parseInt(txtHora.getText().trim());
                int min = Integer.parseInt(txtMinutos.getText().trim());

                // Validación con excepciones defensivas
                Sala sala = cineSeleccionado.obtenerSala(idSala); // Lanza EntidadNoEncontradaException si no existe
                Pelicula peli = sistema.obtenerPelicula(titPeli); // Lanza EntidadNoEncontradaException si no existe

                Funcion nuevaF = new Funcion(fecha, hora, min, peli);
                sala.programarFuncion(nuevaF); // Lanza ConflictoHorarioException si se empalma

                JOptionPane.showMessageDialog(this, "¡Función programada con éxito en " + sala.getIdSala() + " a las " + hora + ":" + (min < 10 ? "0" + min : min) + " hrs!");
                mostrarFunciones();
            } catch (EntidadNoEncontradaException ex) {
                JOptionPane.showMessageDialog(this, "Entidad no encontrada: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ConflictoHorarioException ex) {
                JOptionPane.showMessageDialog(this, "Conflicto de Horario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ==========================================
    // ELIMINAR CON VALIDACIÓN DE EXCEPCIONES
    // ==========================================

    private void ejecutarEliminar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona un elemento de la tabla para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        switch (seccionActual) {
            case "CINES":
                String nombreCine = (String) modeloTabla.getValueAt(fila, 0);
                try {
                    sistema.eliminarCine(nombreCine); // throws EntidadNoEncontradaException
                    JOptionPane.showMessageDialog(this, "Cine '" + nombreCine + "' eliminado con éxito.");
                    if (cineSeleccionado != null && cineSeleccionado.getNombreCine().equalsIgnoreCase(nombreCine)) {
                        cineSeleccionado = null;
                        lblSubtitulo.setText("Cine activo: (Ninguno seleccionado)");
                        btnSalas.setEnabled(false);
                        btnPeliculas.setEnabled(false);
                        btnFunciones.setEnabled(false);
                    }
                    mostrarCines();
                } catch (EntidadNoEncontradaException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al Eliminar", JOptionPane.ERROR_MESSAGE);
                }
                break;

            case "SALAS":
                String idSala = (String) modeloTabla.getValueAt(fila, 0);
                try {
                    cineSeleccionado.eliminaSala(idSala); // throws EntidadNoEncontradaException
                    JOptionPane.showMessageDialog(this, "Sala '" + idSala + "' eliminada con éxito.");
                    mostrarSalas();
                } catch (EntidadNoEncontradaException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al Eliminar", JOptionPane.ERROR_MESSAGE);
                }
                break;

            case "PELICULAS":
                String titulo = (String) modeloTabla.getValueAt(fila, 0);
                try {
                    sistema.eliminarPelicula(titulo); // throws EntidadNoEncontradaException
                    JOptionPane.showMessageDialog(this, "Película '" + titulo + "' eliminada del catálogo.");
                    mostrarPeliculas();
                } catch (EntidadNoEncontradaException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al Eliminar", JOptionPane.ERROR_MESSAGE);
                }
                break;

            case "FUNCIONES":
                String salaId = (String) modeloTabla.getValueAt(fila, 0);
                String fechaF = (String) modeloTabla.getValueAt(fila, 2);
                String horaStr = (String) modeloTabla.getValueAt(fila, 3);
                int horaInt = Integer.parseInt(horaStr.split(":")[0]);

                try {
                    Sala s = cineSeleccionado.obtenerSala(salaId);
                    if (s.cancelarFuncion(fechaF, horaInt)) {
                        JOptionPane.showMessageDialog(this, "Función cancelada con éxito.");
                        mostrarFunciones();
                    } else {
                        JOptionPane.showMessageDialog(this, "No se pudo cancelar la función.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (EntidadNoEncontradaException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
        }
    }

    public void cerrarSesion() {
        this.dispose(); // Cierra esta ventana
        if (this.ventanaLogin != null) {
            this.ventanaLogin.setVisible(true); // Vuelve a mostrar el Login
        }
    }
}
