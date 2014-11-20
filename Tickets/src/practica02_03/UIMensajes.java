package practica02_03;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import java.awt.GridLayout;

import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.UIManager;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;

public class UIMensajes extends JPanel {
	
	private JTable table;
	private DefaultTableModel modelo;
	private JTextField txt_mensaje;
	JPanel panel_central;
	private JTextField txt_titulo;
	private boolean actualizar = true;
	
	/**
	 * Create the panel.
	 * @throws SQLException 
	 * @throws InterruptedException 
	 */
	public UIMensajes(final Connection conexion, final int id_ticket, final Usuario login, final int id_usuario) throws SQLException, InterruptedException {

		setLayout(new BorderLayout(0, 0));
		panel_central = new JPanel();
		panel_central.setLayout(new GridLayout());
		add(panel_central, BorderLayout.CENTER);
		
		mostrarTabla(conexion, id_ticket);
		
		JPanel panel_mensaje_c = new JPanel();
		panel_mensaje_c.setBackground(Color.WHITE);
		panel_mensaje_c.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Incidencia", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(panel_mensaje_c, BorderLayout.SOUTH);
		panel_mensaje_c.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_titulo = new JPanel();
		panel_titulo.setBackground(Color.WHITE);
		panel_titulo.setBorder(new TitledBorder(null, "Titulo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_mensaje_c.add(panel_titulo, BorderLayout.NORTH);
		panel_titulo.setLayout(new BorderLayout(100, 0));
		
		txt_titulo = new JTextField();
		panel_titulo.add(txt_titulo, BorderLayout.CENTER);
		txt_titulo.setColumns(30);
		
		JButton btn_mostrar = new JButton("Mostrar Mensaje");
		btn_mostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!(table.getSelectedRow() == -1)){
					JOptionPane.showMessageDialog(null, "[Titulo]\n" + table.getValueAt(table.getSelectedRow(), 1) + "\n\n[Mensaje]\n" + table.getValueAt(table.getSelectedRow(), 2));
				}
			}
		});
		panel_titulo.add(btn_mostrar, BorderLayout.EAST);
		
		JPanel panel_mensaje = new JPanel();
		panel_mensaje.setBackground(Color.WHITE);
		panel_mensaje.setBorder(new TitledBorder(null, "Mensaje", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_mensaje_c.add(panel_mensaje, BorderLayout.CENTER);
		panel_mensaje.setLayout(new GridLayout(0, 1, 0, 0));
		
		txt_mensaje = new JTextField();
		txt_mensaje.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
				
					enviarMensaje(conexion, id_ticket, login.getId());
					
				}
			}
		});
		panel_mensaje.add(txt_mensaje);
		txt_mensaje.setColumns(10);
		
		JPanel panel_botones = new JPanel();
		panel_botones.setBackground(Color.WHITE);
		panel_mensaje_c.add(panel_botones, BorderLayout.EAST);
		panel_botones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
		
		JButton btn_enviar = new JButton("Enviar");
		btn_enviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				enviarMensaje(conexion, id_ticket, login.getId());
				
			}
		});
		panel_botones.add(btn_enviar);
		
		JButton btn_Volver = new JButton("Volver");
		btn_Volver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					actualizar=false;
					removeAll();
					UITicket uigt = new UITicket(conexion, login);
					add(uigt);
					uigt.setVisible(true);
					SwingUtilities.updateComponentTreeUI(uigt);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		//Actualizar a = new Actualizar();
		//a.run(this, actualizar, conexion, id_ticket);
		
		panel_botones.add(btn_Volver);
		
	}
	
	public void mostrarTabla(Connection conexion, int id_ticket) throws SQLException{
		
		modelo = new DefaultTableModel();	
		modelo.fireTableDataChanged();
		table = new JTable();		
		
		table.setEnabled(true);
		table.setBorder(null);
		table.setModel(modelo);
		
		modelo.addColumn("Usuario");
		modelo.addColumn("Titulo");
		modelo.addColumn("Texto");
		modelo.addColumn("Fecha Creación");
				
		MensajeUtil mensajes_bus = new MensajeUtil();
		ArrayList<Mensaje> mensajes = mensajes_bus.mostarMensajes(conexion, id_ticket);
		
		Mensaje mensaje = null;
		Usuario usuario = null;
		
		for(int i=0; i<mensajes.size(); i++){
						
			Object[] fila = new Object[4];
			mensaje = mensajes.get(i);
			
			usuario = new Usuario();

			usuario.setId(mensaje.getId_usuario());
			usuario.buscarId(conexion);
			
			fila[0] = usuario.getNombre();
			fila[1] = mensaje.getTitol();
			fila[2] = mensaje.getText();
			fila[3] = mensaje.getFechaCrea();
					
			modelo.addRow(fila);
					
		}
			
			
		//Para que no se puedan modificar los campos
		for (int j = 0; j < table.getColumnCount(); j++){
					
			Class<?> col_class = table.getColumnClass(j);
			table.setDefaultEditor(col_class, null);        // remove editor
			    
		}
			
		panel_central.removeAll();			
		panel_central.add(table);
		
		SwingUtilities.updateComponentTreeUI(this);
		
		
		
		
	}
	
	public void enviarMensaje(Connection conexion, int id_ticket, int id_usuario){	
			
		if(txt_titulo.equals("") && txt_mensaje.equals("")){
					
			JOptionPane.showMessageDialog(null, "Falta introducir un titulo y el mensaje de la consulta");
					
		}else if(txt_titulo.equals("")){
					
			JOptionPane.showMessageDialog(null, "Falta introducir un titulo de la consulta");
					
		} else if(txt_mensaje.equals("")){
					
			JOptionPane.showMessageDialog(null, "Falta introducir el mensaje de la consulta");
					
		}else{
					
			try {
						
				Mensaje msg = new Mensaje(txt_titulo.getText(), txt_mensaje.getText());
				msg.setId_ticket(id_ticket);
				msg.setId_usuario(id_usuario);
	
				msg.insertar(conexion);
				txt_titulo.setText("");
				txt_mensaje.setText("");
				mostrarTabla(conexion, id_ticket);
						
			} catch (SQLException e1) {
					// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}	
	
	}
}
