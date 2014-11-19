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

public class UIMensajes extends JPanel {
	
	private JTable table;
	private DefaultTableModel modelo;
	private JTextField txt_mensaje;
	
	/**
	 * Create the panel.
	 * @throws SQLException 
	 */
	public UIMensajes(final Connection conexion, final int id_ticket, int id_usuario) throws SQLException {
		
		mostrarTabla(conexion, id_ticket);
		setLayout(new BorderLayout(0, 0));
		JPanel panel_central = new JPanel();
		add(panel_central);
		panel_central.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane(table);
		panel_central.add(scrollPane);
		
		
		
		JPanel panel_mensaje = new JPanel();
		panel_mensaje.setBackground(Color.WHITE);
		panel_mensaje.setBorder(new TitledBorder(null, "Mensaje", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel_mensaje, BorderLayout.SOUTH);
		panel_mensaje.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel_mensaje.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		txt_mensaje = new JTextField();
		txt_mensaje.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					
					try {
						
						Mensaje msg = new Mensaje("hol2", txt_mensaje.getText());

						msg.insertar(conexion);
						
						mostrarTabla(conexion, id_ticket);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
			}
		});
		panel.add(txt_mensaje);
		txt_mensaje.setColumns(10);
		
		JPanel panel_botones = new JPanel();
		panel_mensaje.add(panel_botones, BorderLayout.EAST);
		panel_botones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btn_enviar = new JButton("Enviar");
		panel_botones.add(btn_enviar);
		
		JButton btn_Volver = new JButton("Volver");
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
		modelo.addColumn("Texto");
		modelo.addColumn("Fecha Creación");
				
		MensajeUtil mensajes_bus = new MensajeUtil();
		ArrayList<Mensaje> mensajes = mensajes_bus.mostarMensajes(conexion, id_ticket);
		
		Mensaje mensaje = null;
		Usuario usuario = null;
		
		for(int i=0; i<mensajes.size(); i++){
						
			Object[] fila = new Object[3];
			mensaje = mensajes.get(i);
			
			usuario = new Usuario();

			usuario.setId(mensaje.getId_usuario());
			usuario.buscarId(conexion);
			
			fila[0] = usuario.getNombre();
			fila[1] = mensaje.getText();
			fila[2] = mensaje.getFechaCrea();
					
			modelo.addRow(fila);
					
		}
			
			
		//Para que no se puedan modificar los campos
		for (int j = 0; j < table.getColumnCount(); j++){
					
			Class<?> col_class = table.getColumnClass(j);
			table.setDefaultEditor(col_class, null);        // remove editor
			    
		}
			
		removeAll();
				
		SwingUtilities.updateComponentTreeUI(this);
		
		
		
		
	}
		
	

}
