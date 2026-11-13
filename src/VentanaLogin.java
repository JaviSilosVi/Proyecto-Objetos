import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
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

        // Campo Usuario
        JLabel lblUser = new JLabel("Usuario");
        lblUser.setBounds(160, 240, 60, 25);
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(lblUser);

        JTextField txtUsuario = new JTextField();
        txtUsuario.setBounds(230, 240, 170, 25);
        txtUsuario.setBackground(new Color(20, 25, 50));
        txtUsuario.setForeground(Color.WHITE);
        txtUsuario.setCaretColor(Color.WHITE);
        this.add(txtUsuario);

        // Campo PIN
        JLabel lblPin = new JLabel("PIN");
        lblPin.setBounds(160, 275, 60, 25);
        lblPin.setForeground(Color.WHITE);
        lblPin.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(lblPin);

        JPasswordField txtPin = new JPasswordField();
        txtPin.setBounds(230, 275, 170, 25);
        txtPin.setBackground(new Color(20, 25, 50));
        txtPin.setForeground(Color.WHITE);
        txtPin.setCaretColor(Color.WHITE);
        this.add(txtPin);

        // Botón Ingresar
        JButton btnIngresar = new JButton("Ingresar");
        btnIngresar.setBounds(240, 315, 150, 35);
        btnIngresar.setBackground(new Color(30, 100, 220));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 13));
        this.add(btnIngresar);

        // Botón Registrar Admin (esquina inferior derecha)
        JButton btnRegistrarAdmin = new JButton("+ Nuevo Admin");
        btnRegistrarAdmin.setBounds(430, 365, 130, 28);
        btnRegistrarAdmin.setBackground(new Color(35, 65, 90));
        btnRegistrarAdmin.setForeground(Color.WHITE);
        btnRegistrarAdmin.setFont(new Font("Arial", Font.BOLD, 11));
        this.add(btnRegistrarAdmin);

        // Acción: Botón Cliente
        btnCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaCliente();
            }
        });

        // Acción: Botón Administrador o Botón Ingresar
        ActionListener accionLoginAdmin = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText().trim();
                String pin = new String(txtPin.getPassword()).trim();

                // Si están vacíos, pedir mediante diálogos rápidos
                if (usuario.isEmpty()) {
                    usuario = JOptionPane.showInputDialog(VentanaLogin.this, "Ingresa tu nombre de Administrador (ej. Pablo o Admin):");
                    if (usuario == null || usuario.trim().isEmpty()) return;
                    usuario = usuario.trim();
                }

                if (pin.isEmpty()) {
                    pin = JOptionPane.showInputDialog(VentanaLogin.this, "Ingresa tu PIN de acceso:");
                    if (pin == null || pin.trim().isEmpty()) return;
                    pin = pin.trim();
                }

                Administrador admin = sistema.autenticarAdmin(usuario, pin);
                if (admin != null) {
                    JOptionPane.showMessageDialog(VentanaLogin.this, 
                        "¡Bienvenido al panel, Administrador " + admin.getNombre() + "!", 
                        "Acceso Concedido", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    VentanaAdmin ventanaAdmin = new VentanaAdmin(sistema, admin, VentanaLogin.this);
                    ventanaAdmin.setVisible(true);
                    VentanaLogin.this.setVisible(false);
                    txtPin.setText("");
                } else {
                    JOptionPane.showMessageDialog(VentanaLogin.this, 
                        "Credenciales incorrectas.\nVerifica que el nombre de usuario y el PIN sean correctos.", 
                        "Error de Acceso", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        btnAdmin.addActionListener(accionLoginAdmin);
        btnIngresar.addActionListener(accionLoginAdmin);

        // Acción: Registrar nuevo Administrador
        btnRegistrarAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuevoNombre = JOptionPane.showInputDialog(VentanaLogin.this, "Ingresa el nombre del nuevo Administrador:");
                if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) return;

                String nuevoPin = JOptionPane.showInputDialog(VentanaLogin.this, "Crea un PIN secreto (mínimo 4 caracteres):");
                if (nuevoPin == null || nuevoPin.trim().isEmpty()) return;

                try {
                    Administrador nuevoAdmin = new Administrador(nuevoNombre.trim(), nuevoPin.trim());
                    nuevoAdmin.validarPin(nuevoPin.trim());
                    sistema.registrarAdministrador(nuevoAdmin);
                    JOptionPane.showMessageDialog(VentanaLogin.this, 
                        "¡Administrador '" + nuevoNombre.trim() + "' registrado con éxito!\nYa puedes iniciar sesión con tu PIN.", 
                        "Registro Exitoso", 
                        JOptionPane.INFORMATION_MESSAGE);
                    txtUsuario.setText(nuevoNombre.trim());
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(VentanaLogin.this, 
                        "Error en el formato: " + ex.getMessage(), 
                        "Error de Validación", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void abrirVentanaCliente() {
        Cliente cliente = new Cliente("Cliente Invitado");
        VentanaCliente ventanaCliente = new VentanaCliente(this.sistema, cliente, this);
        ventanaCliente.setVisible(true);
        this.setVisible(false); // Ocultar la ventana de Login
    }
}
