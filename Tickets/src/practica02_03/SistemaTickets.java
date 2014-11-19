package practica02_03;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.awt.Color;

public class SistemaTickets extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5243002624411531663L;
	private JPanel contentPane;
	
	int id = -1;
	
	/**
	 * Create the frame.
	 */
	public SistemaTickets(final Connection conexion, boolean admin, final Usuario login) {
		setResizable(false);
		setTitle("Gestor de Tickets");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 840, 500);
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);
		setJMenuBar(menuBar);
		
		JButton btnTicket = new JButton("Tickets");
		btnTicket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					UIGTicket uigt = new UIGTicket(conexion, login);

					uigt.setVisible(true);
					contentPane.add(uigt);
					
					SwingUtilities.updateComponentTreeUI(uigt);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		menuBar.add(btnTicket);
		
		JButton btnUsuarios = new JButton("Usuarios");
		menuBar.add(btnUsuarios);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}
}
	
