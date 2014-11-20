package practica02_03;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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

public class UIAgregarUsuario extends JFrame {

	private JPanel contentPane;
	private JTextField txtnombre;
	private JPasswordField pass;
	private JPasswordField pass_rep;
	private JTextField textField;
	private String[] depart = {"Departamento", "Administració", "Informàtica", "Disseny", "Màrketing"};
	private JComboBox comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIAgregarUsuario frame = new UIAgregarUsuario();
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
	public UIAgregarUsuario() {
		setTitle("Crear Usuario");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 403, 244);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 23, 127, 14);
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
		txtnombre.setBounds(147, 20, 220, 20);
		contentPane.add(txtnombre);
		txtnombre.setColumns(10);
		
		pass = new JPasswordField();
		pass.setBounds(147, 45, 220, 20);
		contentPane.add(pass);
		
		pass_rep = new JPasswordField();
		pass_rep.setBounds(147, 69, 220, 20);
		contentPane.add(pass_rep);
		
		comboBox = new JComboBox(depart);
		comboBox.setBounds(146, 119, 111, 20);
		contentPane.add(comboBox);
		
		textField = new JTextField();
		textField.setBounds(147, 94, 220, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JCheckBox chck_admin = new JCheckBox("Si");
		chck_admin.setBounds(147, 143, 97, 23);
		contentPane.add(chck_admin);
		
		JButton btn_aceptar = new JButton("Aceptar");
		btn_aceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(txtnombre.equals("")){
					
					JOptionPane.showMessageDialog(null, "Debe introducir un nombre");
					
				} else if((pass.equals("")) || (pass_rep.equals(""))){
					
					JOptionPane.showMessageDialog(null, "Debe introducir un password");
					
				} else if(depart[comboBox.getSelectedIndex()].equals("Departamento")){
					
					JOptionPane.showMessageDialog(null, "Falta seleccionar un departamento");
					
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
}
