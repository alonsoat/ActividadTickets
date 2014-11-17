package practica02_03;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UIModificarTicket extends JDialog {

	private JPanel contentPane;
	private String[] estados = {"Abierta", "Cerrada"};
	private JComboBox comboBox;

	/**
	 * Create the frame.
	 */
	public UIModificarTicket(final Connection conexion, final UIGTicket uigTicket, final Ticket ticket) {
		setLocationRelativeTo(uigTicket);
		setModal(true);
		setResizable(false);
		setTitle("Modificar Ticket");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 239, 122);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lbl_estado = new JLabel("Estado:");
		contentPane.add(lbl_estado);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		FlowLayout fl_panel = new FlowLayout(FlowLayout.CENTER, 5, 5);
		panel.setLayout(fl_panel);
		
		comboBox = new JComboBox(estados);
		panel.add(comboBox);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btn_aceptar = new JButton("Aceptar");
		btn_aceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				ticket.setEstado(estados[comboBox.getSelectedIndex()]);
				ticket.actualizar(conexion);
				dispose();
				
			}
		});
		panel_1.add(btn_aceptar);
		
		JButton btn_cancelar = new JButton("Cancelar");
		btn_cancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				
			}
		});
		panel_1.add(btn_cancelar);
	}
}
