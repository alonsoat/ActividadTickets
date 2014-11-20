package practica02_03;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import java.sql.SQLException;
import java.awt.Component;

public class UIAgregarTicket extends JDialog {

	private JPanel contentPane;
	private JTextArea text_mensaje;
	private JTextField text_titulo;

	/**
	 * Create the frame.
	 */
	public UIAgregarTicket(final Connection conexion, final UITicket uigTicket, final int id) {
		setLocationRelativeTo(uigTicket);
		setModal(true);
		setResizable(false);
		setTitle("Nuevo Ticket");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 495, 186);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_mensaje = new JPanel();
		panel_mensaje.setBackground(Color.WHITE);
		panel_mensaje.setBorder(new TitledBorder(null, "Mensaje", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_mensaje.setBounds(10, 63, 361, 90);
		contentPane.add(panel_mensaje);
		panel_mensaje.setLayout(new GridLayout(0, 1, 0, 0));
		
		
		text_mensaje = new JTextArea();
		JScrollPane scroll = new JScrollPane(text_mensaje);
		panel_mensaje.add(scroll);
		
		JButton btn_crear = new JButton("Crear");
		btn_crear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String texto_mensaje = text_mensaje.getText();
				String texto_titulo = text_titulo.getText();
				
				if(texto_mensaje.equals("")){
					
					JOptionPane.showMessageDialog(null, "El mensaje no puede estar vacio");
				} else if(text_titulo.equals("")){
					
					JOptionPane.showMessageDialog(null, "El titulo no puede estar vacio.");
				
				} else{
				
					Ticket ticket = new Ticket();
					ticket.setEstado("Obert");
					
					Mensaje mensaje = new Mensaje();
					ticket.insertar(conexion);
					mensaje.setId_ticket(ticket.getId());
					mensaje.setId_usuario(id);
					mensaje.setImatge("NULL");
					mensaje.setText(texto_mensaje);
					mensaje.setTitol(texto_titulo);
					mensaje.insertar(conexion);
					
					try {
						
						uigTicket.mostrarTabla(conexion);
						
					} catch (SQLException e) {
						
						e.printStackTrace();
						
					}
					uigTicket.deshabilitarBotones();
					dispose();
					
				}
				
			}
		});
		btn_crear.setBounds(381, 25, 89, 23);
		contentPane.add(btn_crear);
		
		JButton btn_cancelar = new JButton("Cancelar");
		btn_cancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					uigTicket.mostrarTabla(conexion);
					
				} catch (SQLException e) {
					
					e.printStackTrace();
					
				}
				uigTicket.deshabilitarBotones();
				dispose();
				
			}
		});
		btn_cancelar.setBounds(381, 59, 89, 23);
		contentPane.add(btn_cancelar);
		
		JPanel panel_titulo = new JPanel();
		panel_titulo.setBackground(Color.WHITE);
		panel_titulo.setBorder(new TitledBorder(null, "Titulo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_titulo.setBounds(10, 11, 359, 43);
		contentPane.add(panel_titulo);
		panel_titulo.setLayout(new GridLayout(0, 1, 0, 0));
		
		text_titulo = new JTextField();
		panel_titulo.add(text_titulo);
		text_titulo.setColumns(10);
	}
}
