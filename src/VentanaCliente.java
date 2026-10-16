import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaCliente extends JFrame {
    private Sistema sistema;
    private Cliente clienteActivo;
    private VentanaLogin ventanaLogin;

    public VentanaCliente(Sistema sistema, Cliente cliente, VentanaLogin ventanaLogin) {
        this.sistema = sistema;
        this.clienteActivo = cliente;
        this.ventanaLogin = ventanaLogin;

        // Configuración básica de la ventana
        this.setTitle("Sistema - Cliente");
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

        JLabel lblTitulo = new JLabel("Sistema - Cliente");
        lblTitulo.setBounds(20, 10, 200, 25);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 15));
        panelHeader.add(lblTitulo);
        this.add(panelHeader);

        // barras de busqueda
        JLabel lblPregunta = new JLabel("¿Qué vamos a ver hoy?");
        lblPregunta.setBounds(30, 60, 200, 25);
        lblPregunta.setForeground(new Color(100, 180, 255));
        lblPregunta.setFont(new Font("Arial", Font.BOLD, 13));
        this.add(lblPregunta);

        JTextField txtBuscador = new JTextField("  buscando...");
        txtBuscador.setBounds(240, 60, 380, 28);
        txtBuscador.setBackground(new Color(20, 30, 50));
        txtBuscador.setForeground(Color.LIGHT_GRAY);
        txtBuscador.setFont(new Font("Arial", Font.ITALIC, 12));
        this.add(txtBuscador);

        // tabla simulando la pelicula 
        JPanel peli1 = crearCuadroPelicula(40, 110, 100, 100);
        JPanel peli2 = crearCuadroPelicula(160, 110, 100, 100);
        JPanel peli3 = crearCuadroPelicula(40, 230, 100, 100);
        JPanel peli4 = crearCuadroPelicula(160, 230, 100, 100);
        JPanel peli5 = crearCuadroPelicula(320, 200, 100, 100);

        this.add(peli1);
        this.add(peli2);
        this.add(peli3);
        this.add(peli4);
        this.add(peli5);

        // Botones de revisar horario y comprar
        JButton btnHorario = new JButton("revisar horario");
        btnHorario.setBounds(320, 120, 170, 30);
        btnHorario.setBackground(new Color(25, 35, 55));
        btnHorario.setForeground(Color.WHITE);
        btnHorario.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(btnHorario);

        JButton btnComprar = new JButton("comprar");
        btnComprar.setBounds(320, 160, 170, 30);
        btnComprar.setBackground(new Color(25, 35, 55));
        btnComprar.setForeground(Color.WHITE);
        btnComprar.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(btnComprar);

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

    private JPanel crearCuadroPelicula(int x, int y, int ancho, int alto) {
        JPanel cuadro = new JPanel();
        cuadro.setBounds(x, y, ancho, alto);
        cuadro.setBackground(new Color(20, 20, 25));
        cuadro.setBorder(BorderFactory.createLineBorder(new Color(90, 100, 120), 2));
        return cuadro;
    }

    public void mostrarCartelera() {}
    public void seleccionarFuncion() {}
    public void mostrarMatrizAsientos() {}
    public void realizarCompra() {}

    public void cerrarSesion() {
        this.dispose(); // Cierra esta ventana
        if (this.ventanaLogin != null) {
            this.ventanaLogin.setVisible(true); // Vuelve a mostrar el Login
        }
    }
}
