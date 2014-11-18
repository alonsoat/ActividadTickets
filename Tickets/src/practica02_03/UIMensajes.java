package practica02_03;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.GridLayout;

public class UIMensajes extends JPanel {
	/*
	private JTable table;
	private DefaultTableModel modelo;
	
	/**
	 * Create the panel.
	 
	public UIMensajes(Connection conexion, int id_ticket, int id_usuario) {

		modelo = new DefaultTableModel();	
		modelo.fireTableDataChanged();
		
		modelo.addColumn("ID");
		modelo.addColumn("Titulo");
		modelo.addColumn("Texto");
		modelo.addColumn("Imagen");
		modelo.addColumn("Fecha Creación");
				
		MensajeUtil mensajes_bus = new MensajeUtil();
		ArrayList<Mensaje> mensajes = mensajes_bus.mostarMensajes(conexion, id_ticket, id_usuario);
		
		
		Mensaje mensaje = null;
		
		for(int i=0; i<mensajes.size(); i++){
						
			Object[] fila = new Object[5];
			mensaje = mensajes.get(i);
			System.out.println(mensaje.toString());
			fila[0] = mensaje.getId();
			fila[1] = mensaje.getTitol();
			fila[2] = mensaje.getText();
			fila[3] = mensaje.getImatge();
			fila[4] = mensaje.getFechaCrea();
					
			modelo.addRow(fila);
					
		}
			
			
		//Para que no se puedan modificar los campos
		for (int j = 0; j < table.getColumnCount(); j++){
					
			Class<?> col_class = table.getColumnClass(j);
			table.setDefaultEditor(col_class, null);        // remove editor
			    
		}
	
				
		removeAll();
				
		SwingUtilities.updateComponentTreeUI(this);
		
		table = new JTable();		
		
		table.setEnabled(true);
		table.setBorder(null);
		table.setModel(modelo);
		
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane);
		
	}	
		
	*/

}
