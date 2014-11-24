package practica02_03;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UIAgregarUsuario extends JDialog {

	private JPanel contentPane;
	private JTextField txtnombre;
	private JPasswordField pass;
	private JPasswordField pass_rep;
	private JTextField txt_email;
	private String[] depart = { "Elige Dep.", "Administració", "Informàtica",
			"Disseny", "Màrketing" };
	private JComboBox comboBox;
	private JCheckBox chck_admin;

	/**
	 * Create the frame.
	 */
	public UIAgregarUsuario(final Connection conexion, final UIUsuarios uiu) {
		setLocationRelativeTo(uiu);
		setModal(true);
		setResizable(false);
		setTitle("Crear Usuario");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 403, 244);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 23, 97, 14);
		contentPane.add(lblNombre);

		JLabel lblemail = new JLabel("E-Mail:");
		lblemail.setBounds(10, 97, 122, 14);
		contentPane.add(lblemail);

		JLabel lblDepartamento = new JLabel("Departamento:");
		lblDepartamento.setBounds(10, 122, 122, 14);
		contentPane.add(lblDepartamento);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 48, 127, 14);
		contentPane.add(lblPassword);

		JLabel lblPassword_rep = new JLabel("Repetir password:");
		lblPassword_rep.setBounds(10, 72, 127, 14);
		contentPane.add(lblPassword_rep);

		JLabel lblNewLabel = new JLabel("Admin");
		lblNewLabel.setBounds(10, 147, 122, 14);
		contentPane.add(lblNewLabel);

		txtnombre = new JTextField();
		txtnombre.setToolTipText("Introduce nombre de usuario");
		txtnombre.setBounds(147, 20, 220, 20);
		contentPane.add(txtnombre);
		txtnombre.setColumns(10);

		pass = new JPasswordField();
		pass.setToolTipText("Instroduce el password del usuario");
		pass.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {

				if (!new String(pass.getPassword()).equals("")) {

					pass_rep.setEnabled(true);

				} else {

					pass_rep.setEnabled(false);

				}

			}
		});
		pass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					if (!new String(pass.getPassword()).equals("")) {

						pass_rep.setEnabled(true);

					}

				}

			}
		});
		pass.setBounds(147, 45, 220, 20);
		contentPane.add(pass);

		pass_rep = new JPasswordField();
		pass_rep.setToolTipText("Vuelve a introducir el password");
		pass_rep.setEnabled(false);
		pass_rep.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					if (!new String(pass.getPassword()).equals(new String(pass
							.getPassword()))) {

						JOptionPane.showMessageDialog(null,
								"El password no coincide con el anterior");
						pass.setText("");

					}

				}

			}
		});
		pass_rep.setBounds(147, 69, 220, 20);
		contentPane.add(pass_rep);

		comboBox = new JComboBox(depart);
		comboBox.setToolTipText("Introduce el departamento");
		comboBox.setBounds(146, 119, 112, 20);
		contentPane.add(comboBox);

		txt_email = new JTextField();
		txt_email.setToolTipText("Introduce el email del usuario");
		txt_email.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					if (!comprobarCorreo(txt_email.getText())) {

						JOptionPane.showMessageDialog(null,
								"El correo no es valido");
						txt_email.setText("");

					}

				}
			}
		});
		txt_email.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {

				if (!txt_email.getText().equals("")) {
					if (!comprobarCorreo(txt_email.getText())) {

						JOptionPane.showMessageDialog(null,
								"El correo no es valido");
						txt_email.setText("");

					}
				}

			}
		});
		txt_email.setBounds(147, 94, 220, 20);
		contentPane.add(txt_email);
		txt_email.setColumns(10);

		chck_admin = new JCheckBox("Si");
		chck_admin.setBounds(147, 143, 97, 23);
		contentPane.add(chck_admin);

		JButton btn_aceptar = new JButton("Aceptar");
		btn_aceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String nombre = txtnombre.getText();
				String password = new String(pass.getPassword());
				String password_rep = new String(pass_rep.getPassword());
				String departamento = depart[comboBox.getSelectedIndex()];
				String email = txt_email.getText();

				if (nombre.equals("")) {

					JOptionPane.showMessageDialog(null,
							"Debe introducir un nombre");

				} else if (password.equals("") || (password_rep).equals("")) {

					JOptionPane.showMessageDialog(null,
							"Debe introducir un password");

				} else if (departamento.equals("Elige Dep.")) {

					JOptionPane.showMessageDialog(null,
							"Falta seleccionar un departamento");

				} else if (email.equals("")) {

					JOptionPane.showMessageDialog(null, "El correo esta vacio");

				} else {

					Usuario u = new Usuario();
					u.setNombre(nombre);
					u.setPass(password);
					u.setDepartament(departamento);
					u.setMail(email);
					u.setAdmin(chck_admin.isSelected());
					
					try {
						conexion.setAutoCommit(false);
					
					
						u.insertar(conexion);
						
						conexion.commit();
					
					} catch (SQLException e1) {
						
						try {
							
							conexion.rollback();
							
						} catch (SQLException e) {
							
							// TODO Auto-generated catch block
							e.printStackTrace();
							
						}
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					try {
						uiu.mostrarTabla(conexion);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dispose();

				}

			}
		});
		btn_aceptar.setBounds(179, 173, 89, 23);
		contentPane.add(btn_aceptar);

		JButton btn_cancelar = new JButton("Cancelar");
		btn_cancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btn_cancelar.setBounds(278, 173, 89, 23);
		contentPane.add(btn_cancelar);
	}

	public boolean comprobarCorreo(String email) {

		String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		// Compiles the given regular expression into a pattern.
		Pattern pattern = Pattern.compile(PATTERN_EMAIL);

		// Match the given input against this pattern
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();

	}
}
