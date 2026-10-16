import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaAdmin extends JFrame {
    private Sistema sistema;
    private Administrador adminActivo;
    private VentanaLogin ventanaLogin;

    public VentanaAdmin(Sistema sistema, Administrador admin, VentanaLogin ventanaLogin) {
        this.sistema = sistema;
        this.adminActivo = admin;
        this.ventanaLogin = ventanaLogin;

        // Configuración básica de la ventana
        this.setTitle("Sistema - Cine (Administrador)");
        this.setSize(700, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(10, 15, 35));

        // Encabezado
        JPanel panelHeader = new JPanel();
        panelHeader.setBounds(0, 0, 700, 45);
        panelHeader.setBackground(new Color(15, 30, 60));
        panelHeader.setLayout(null);

        JLabel lblTitulo = new JLabel("Sistema - Cine");
        lblTitulo.setBounds(20, 10, 200, 25);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 15));
        panelHeader.add(lblTitulo);
        this.add(panelHeader);

        // Saludo
        JLabel lblBienvenido = new JLabel("Bienvenido Administrador");
        lblBienvenido.setBounds(25, 55, 300, 25);
        lblBienvenido.setForeground(new Color(100, 180, 255));
        lblBienvenido.setFont(new Font("Arial", Font.BOLD, 14));
        this.add(lblBienvenido);

        // Opciones a modificar (etiqueta con fondo)
        JLabel lblOpciones = new JLabel("  Opciones a modificar");
        lblOpciones.setBounds(25, 90, 170, 28);
        lblOpciones.setOpaque(true);
        lblOpciones.setBackground(new Color(18, 55, 75));
        lblOpciones.setForeground(new Color(120, 220, 210));
        lblOpciones.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(lblOpciones);

        // Botones del panel izquierdo (Películas, Cines, Salas, Funciones)
        JButton btnPeliculas = new JButton("Peliculas");
        btnPeliculas.setBounds(40, 140, 110, 30);
        btnPeliculas.setBackground(new Color(150, 170, 190));
        btnPeliculas.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(btnPeliculas);

        JButton btnCines = new JButton("Cines");
        btnCines.setBounds(40, 180, 110, 30);
        btnCines.setBackground(new Color(150, 170, 190));
        btnCines.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(btnCines);

        JButton btnSalas = new JButton("Salas");
        btnSalas.setBounds(40, 220, 110, 30);
        btnSalas.setBackground(new Color(150, 170, 190));
        btnSalas.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(btnSalas);

        JButton btnFunciones = new JButton("Funciones");
        btnFunciones.setBounds(40, 260, 110, 30);
        btnFunciones.setBackground(new Color(150, 170, 190));
        btnFunciones.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(btnFunciones);

        // Tabla del Administrador (tal cual tu mockup: pelicula, valor actual, valor nuevo)
        String[] columnas = {"pelicula", "valor actual", "valor nuevo"};
        Object[][] datos = {
            {"titulo", "kunfuPanda", "ironman2"},
            {"duracion", "1:25", "1:32"},
            {"genero", "comedia", "accion"},
            {"clasificacion", "A", "B"}
        };

        DefaultTableModel modeloTabla = new DefaultTableModel(datos, columnas);
        JTable tabla = new JTable(modeloTabla);
        tabla.setBackground(new Color(25, 45, 30));
        tabla.setForeground(Color.WHITE);
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.setRowHeight(25);
        tabla.getTableHeader().setBackground(new Color(50, 80, 40));
        tabla.getTableHeader().setForeground(Color.YELLOW);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBounds(230, 140, 400, 130);
        this.add(scrollTabla);

        // Botón Guardar Cambios
        JButton btnGuardar = new JButton("guardar cambios");
        btnGuardar.setBounds(330, 300, 180, 30);
        btnGuardar.setBackground(new Color(40, 80, 50));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(btnGuardar);

        // Botón Cerrar Sesión (Navega de regreso al Login)
        JButton btnCerrarSesion = new JButton("Cerrar sesion");
        btnCerrarSesion.setBounds(530, 400, 120, 30);
        btnCerrarSesion.setBackground(new Color(60, 20, 20));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(btnCerrarSesion);

        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });
    }

    public void registrarPelicula() {}
    public void modificarPelicula() {}
    public void eliminarPelicula() {}
    public void agregarSala() {}
    public void programarFuncion() {}

    public void cerrarSesion() {
        this.dispose(); // Cierra esta ventana
        if (this.ventanaLogin != null) {
            this.ventanaLogin.setVisible(true); // Vuelve a mostrar el Login
        }
    }
}
