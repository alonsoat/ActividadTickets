package practica02_03;

import javax.swing.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.regex.*;
import java.awt.*;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UILogin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4387414166643188514L;
	private JPanel contentPane;
	private JTextField textUsuario;
	private JPasswordField passwordField;
	private JTextField textIPaddress;

	private String usuario;
	private String password;

	private static Connection conexion;
	private String bbdd = "tickets";
	private String usuari = "tickets";
	private String pass = "1234";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UILogin frame = new UILogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UILogin() {
		setResizable(false);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 216, 173);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		Panel panel = new Panel();
		contentPane.add(panel);
		panel.setLayout(new GridLayout(3, 2, 0, 10));

		JLabel lblUsuario = new JLabel("Usuario:");
		panel.add(lblUsuario);

		textUsuario = new JTextField();
		textUsuario.setToolTipText("Usuario con el que se quiere acceder.");
		panel.add(textUsuario);
		textUsuario.setColumns(10);

		JLabel lblPassword = new JLabel("Password:");
		panel.add(lblPassword);

		passwordField = new JPasswordField();
		passwordField.setToolTipText("Password del usuario");
		panel.add(passwordField);

		JLabel lblIpAddress = new JLabel("IP Server:");
		panel.add(lblIpAddress);

		textIPaddress = new JFormattedTextField();
		textIPaddress.setToolTipText("IP del servidor a conectar");
		textIPaddress.setText("127.0.0.1");
		textIPaddress.setInputVerifier(new InputVerifier() {
			Pattern pat = Pattern
					.compile("\\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
							+ "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
							+ "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
							+ "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b");

			public boolean shouldYieldFocus(JComponent input) {
				boolean inputOK = verify(input);
				if (inputOK) {
					return true;
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null,
							"La IP no es valida, introduzca una valida",
							"Error", JOptionPane.WARNING_MESSAGE);
					return false;
				}
			}

			public boolean verify(JComponent input) {
				JTextField field = (JTextField) input;
				Matcher m = pat.matcher(field.getText());
				return m.matches();
			}
		});
		panel.add(textIPaddress);
		textIPaddress.setColumns(10);

		Panel panel_confirm = new Panel();
		contentPane.add(panel_confirm, BorderLayout.SOUTH);
		FlowLayout fl_panel_confirm = new FlowLayout(FlowLayout.CENTER, 5, 5);
		panel_confirm.setLayout(fl_panel_confirm);

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				usuario = textUsuario.getText();
				password = new String(passwordField.getPassword());

				if (usuario.equals("")) {

					JOptionPane.showMessageDialog(null,
							"El usuario no puede estar vacio");

				} else if (password.equals("")) {

					JOptionPane.showMessageDialog(null,
							"El password no puede estar vacio");

				} else {

					Usuario login = new Usuario();

					try {

						conexion = DriverManager.getConnection("jdbc:mysql://"
								+ textIPaddress.getText() + "/" + bbdd
								+ "?zeroDateTimeBehavior=convertToNull",
								usuari, pass);

						login.setNombre(usuario);
						login.setPass(password);

						if (login.validarUsuario(conexion)) {

							SistemaTickets sistick = new SistemaTickets(
									conexion, login.isAdmin(), login);

							sistick.setVisible(true);
							dispose();

						} else {

							JOptionPane.showMessageDialog(null,
									"El usuario o password no son validos");
						}

					} catch (SQLException e) {

						JOptionPane.showMessageDialog(null,
								"Error en la conexi�n");

					}
				}

			}

		});
		panel_confirm.add(btnAceptar);

		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		panel_confirm.add(btnSalir);
	}

	public boolean copiaSeguridad() {

		String executeCmd = "C:\\xampp\\mysql\\bin\\mysqldump.exe -u root -p  --add-drop-database -B tickets -r C:\\xampp\\prueba2.txt ";
		Process runtimeProcess;

		try {

			runtimeProcess = Runtime.getRuntime().exec(executeCmd);
			int processComplete = runtimeProcess.waitFor();

			if (processComplete == 0) {

				JOptionPane.showMessageDialog(null,
						"Copia de seguridad creada con exito");

				return true;

			} else {

				JOptionPane.showMessageDialog(null,
						"No se ha podido crear la copia de seguridad");

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return false;
	}

}
