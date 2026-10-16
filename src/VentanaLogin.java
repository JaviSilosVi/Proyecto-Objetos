import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaLogin extends JFrame {
    private Sistema sistema;

    public VentanaLogin(Sistema sistema) {
        this.sistema = sistema;

        // Configuración básica de la ventana
        this.setTitle("Sistema Cine - Login");
        this.setSize(600, 450);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Centrar en la pantalla
        this.setLayout(null); // Posicionamiento simple por coordenadas 
        this.getContentPane().setBackground(new Color(10, 15, 35)); // Fondo oscuro 

        // Encabezado
        JPanel panelHeader = new JPanel();
        panelHeader.setBounds(0, 0, 600, 50);
        panelHeader.setBackground(new Color(15, 30, 60));
        panelHeader.setLayout(null);

        JLabel lblTitulo = new JLabel("Sistema Cine - Login");
        lblTitulo.setBounds(20, 12, 200, 25);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelHeader.add(lblTitulo);
        this.add(panelHeader);

        // Subtítulo
        JLabel lblPregunta = new JLabel("¿Quién nos visita el día de hoy?", SwingConstants.CENTER);
        lblPregunta.setBounds(50, 70, 500, 30);
        lblPregunta.setForeground(Color.WHITE);
        lblPregunta.setFont(new Font("Arial", Font.BOLD, 15));
        this.add(lblPregunta);

        // Botón Administrador
        JButton btnAdmin = new JButton("Administrador");
        btnAdmin.setBounds(200, 120, 200, 35);
        btnAdmin.setBackground(new Color(200, 210, 225));
        btnAdmin.setFont(new Font("Arial", Font.BOLD, 13));
        this.add(btnAdmin);

        // Botón Cliente
        JButton btnCliente = new JButton("Cliente");
        btnCliente.setBounds(200, 170, 200, 35);
        btnCliente.setBackground(new Color(180, 195, 215));
        btnCliente.setFont(new Font("Arial", Font.BOLD, 13));
        this.add(btnCliente);

        // Botón Ingresar
        JButton btnIngresar = new JButton("Ingresar");
        btnIngresar.setBounds(240, 225, 120, 35);
        btnIngresar.setBackground(new Color(30, 100, 220));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 13));
        this.add(btnIngresar);

        // Campo PIN
        JLabel lblPin = new JLabel("PIN");
        lblPin.setBounds(190, 285, 40, 25);
        lblPin.setForeground(Color.WHITE);
        lblPin.setFont(new Font("Arial", Font.BOLD, 13));
        this.add(lblPin);

        JPasswordField txtPin = new JPasswordField();
        txtPin.setBounds(230, 285, 170, 25);
        txtPin.setBackground(new Color(20, 25, 50));
        txtPin.setForeground(Color.WHITE);
        txtPin.setCaretColor(Color.WHITE);
        this.add(txtPin);

        // Botón Cambiar (decorativo como en la imagen)
        JButton btnCambiar = new JButton("Cambiar");
        btnCambiar.setBounds(460, 350, 90, 25);
        btnCambiar.setBackground(new Color(30, 35, 60));
        btnCambiar.setForeground(Color.WHITE);
        this.add(btnCambiar);

        // navegacion basica
        btnAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaAdmin("1234");
            }
        });

        btnCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaCliente();
            }
        });

        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaCliente();
            }
        });
    }

    public void abrirVentanaAdmin(String pinIngresado) {
        Administrador admin = new Administrador("Administrador", "1234");
        VentanaAdmin ventanaAdmin = new VentanaAdmin(this.sistema, admin, this);
        ventanaAdmin.setVisible(true);
        this.setVisible(false); // Ocultar la ventana de Login
    }

    public void abrirVentanaCliente() {
        Cliente cliente = new Cliente("Cliente Invitado");
        VentanaCliente ventanaCliente = new VentanaCliente(this.sistema, cliente, this);
        ventanaCliente.setVisible(true);
        this.setVisible(false); // Ocultar la ventana de Login
    }
}
