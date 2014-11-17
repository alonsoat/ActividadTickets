package practica02_03;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.sql.Connection;

public class UIAgregarUsuario extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public UIAgregarUsuario(final Connection conexion) {
		setResizable(false);
		setTitle("Nuevo Ticket");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 495, 139);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(new TitledBorder(null, "Mensaje", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(6, 11, 359, 90);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		
		JTextArea textArea = new JTextArea();
		JScrollPane scroll = new JScrollPane(textArea);
		panel.add(scroll);
		
		JButton btn_crear = new JButton("Crear");
		btn_crear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				TicketUtil ticket = new TicketUtil();
				//Codigo de crearMensaje;
				
			}
		});
		btn_crear.setBounds(381, 25, 89, 23);
		contentPane.add(btn_crear);
		
		JButton btn_cancelar = new JButton("Cancelar");
		btn_cancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btn_cancelar.setBounds(381, 59, 89, 23);
		contentPane.add(btn_cancelar);
	}
}
