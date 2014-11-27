package practica02_03;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JOptionPane;
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
	public SistemaTickets(final Connection conexion, boolean admin,
			final Usuario login) {
		setResizable(false);
		setTitle("Gestor de Tickets");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 880, 500);
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);
		setJMenuBar(menuBar);

		JButton btnTicket = new JButton("Tickets");
		btnTicket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					UITicket uigt = new UITicket(conexion, login);

					uigt.setVisible(true);
					contentPane.removeAll();
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
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					UIUsuarios uiu = new UIUsuarios(conexion, login);

					uiu.setVisible(true);
					contentPane.removeAll();
					contentPane.add(uiu);

					SwingUtilities.updateComponentTreeUI(uiu);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		menuBar.add(btnUsuarios);

		JButton btn_copia = new JButton("Hacer copia de seguridad");
		btn_copia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				/*
				 * UILogin ui = new UILogin();
				 * 
				 * 
				 * ui.copiaSeguridad();
				 */

				String ruta = preguntarRuta();
				
				MensajeUtil.copiaSeguridadMensajes(conexion, ruta);
				UsuarioUtil.copiaSeguridadUsuarios(conexion, ruta);
				TicketUtil.copiaSeguridadTickets(conexion, ruta);
				

			}
		});
		menuBar.add(btn_copia);

		if (login.isAdmin()) {
			btn_copia.setVisible(true);
		} else {
			btn_copia.setVisible(false);
		}

		if (login.isAdmin()) {
			btnUsuarios.setVisible(true);
		} else {
			btnUsuarios.setVisible(false);
		}

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

	public String preguntarRuta() {

		return JOptionPane.showInputDialog(null,
				"Ruta donde desea la copía de seguridad:");

	}

}
