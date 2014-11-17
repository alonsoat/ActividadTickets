package practica02_03;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JTable;

import java.awt.Panel;

import javax.swing.JButton;

import java.awt.GridLayout;

import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JComboBox;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UIGTicket extends JPanel {
	
	/**
	 * 
	 * 
	 * 
	 */
	
	private static final long serialVersionUID = -7508988054079361673L;
	private JTable table;
	private JTextField text_buscar;
	private String[] depart = {"Todas", "Administraci�", "Inform�tica", "Diseny", "M�rketing"};
	private ButtonGroup group = new ButtonGroup();
	private DefaultTableModel modelo;
	private JComboBox<String> comb_depart;
	private JRadioButton rdbtn_activas;
	private JRadioButton rdbtn_cerradas;
	private JRadioButton rdbtn_todas;
	private JPanel panel_central;
	
	private JButton btn_MostrarMensajes;
	private JButton btn_eliminar;
	private JButton btn_modificar;
	private JButton btn_agregar;
	
	
	/**
	 * Create the panel.
	 * @throws SQLException 
	 * 
	 */
	public UIGTicket(final Connection conexion) throws SQLException {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel_botones = new JPanel();
		panel_botones.setBorder(new EmptyBorder(20, 10, 20, 10));
		panel_botones.setBackground(Color.WHITE);
		add(panel_botones, BorderLayout.EAST);
		panel_botones.setLayout(new GridLayout(6, 1, 0, 30));
		
		
		btn_agregar = new JButton("Agregar");
		btn_agregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
							
				llamarAgregarTicket(conexion);
				
			}
		});
		panel_botones.add(btn_agregar);
		
		btn_modificar = new JButton("Modificar");
		btn_modificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Codigo de modificar ticket
				
			}
		});
		btn_modificar.setEnabled(false);
		panel_botones.add(btn_modificar);
		
		btn_eliminar = new JButton("Eliminar");
		btn_eliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Codigo de elminar ticket
				
			}
		});
		btn_eliminar.setEnabled(false);
		panel_botones.add(btn_eliminar);
		
		btn_MostrarMensajes = new JButton("Mensajes");
		btn_MostrarMensajes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Codigo de mostrar mensajes
				
			}
		});
		btn_MostrarMensajes.setEnabled(false);
		panel_botones.add(btn_MostrarMensajes);
		
		JPanel panel_filtro = new JPanel();
		panel_filtro.setBackground(Color.WHITE);
		panel_filtro.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Filtro", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(panel_filtro, BorderLayout.NORTH);
		GridBagLayout gbl_panel_filtro = new GridBagLayout();
		gbl_panel_filtro.columnWidths = new int[] {90, 258, 160, 0, 0, 0};
		gbl_panel_filtro.rowHeights = new int[] {50};
		gbl_panel_filtro.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_filtro.rowWeights = new double[]{0.0};
		panel_filtro.setLayout(gbl_panel_filtro);
		
		JPanel panel_busqueda = new JPanel();
		panel_busqueda.setBackground(Color.WHITE);
		panel_busqueda.setBorder(new TitledBorder(null, "Buscar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		GridBagConstraints gbc_panel_busqueda = new GridBagConstraints();
		gbc_panel_busqueda.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_busqueda.gridwidth = 2;
		gbc_panel_busqueda.insets = new Insets(0, 0, 0, 5);
		gbc_panel_busqueda.gridx = 0;
		gbc_panel_busqueda.gridy = 0;
		panel_filtro.add(panel_busqueda, gbc_panel_busqueda);
		panel_busqueda.setLayout(new GridLayout(0, 1, 0, 0));
		
		text_buscar = new JTextField();
		panel_busqueda.add(text_buscar);
		text_buscar.setColumns(30);
		
		JPanel panel_estado = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_estado.getLayout();
		flowLayout.setHgap(12);
		panel_estado.setBorder(new TitledBorder(null, "Estado", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_estado.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel_estado = new GridBagConstraints();
		gbc_panel_estado.fill = GridBagConstraints.VERTICAL;
		gbc_panel_estado.anchor = GridBagConstraints.WEST;
		gbc_panel_estado.insets = new Insets(0, 0, 0, 5);
		gbc_panel_estado.gridx = 2;
		gbc_panel_estado.gridy = 0;
		panel_filtro.add(panel_estado, gbc_panel_estado);
		
		rdbtn_todas = new JRadioButton("Todas");
		rdbtn_todas.setBackground(Color.WHITE);
		rdbtn_todas.setSelected(true);
		rdbtn_todas.setActionCommand("%");
		panel_estado.add(rdbtn_todas);
		group.add(rdbtn_todas);
		
		rdbtn_activas = new JRadioButton("Abiertas");
		rdbtn_activas.setBackground(Color.WHITE);
		rdbtn_activas.setSelected(false);
		rdbtn_activas.setActionCommand("Obert");
		panel_estado.add(rdbtn_activas);
		
		rdbtn_cerradas = new JRadioButton("Cerradas");
		rdbtn_cerradas.setBackground(Color.WHITE);
		rdbtn_cerradas.setSelected(false);
		rdbtn_cerradas.setActionCommand("Tancat");
		panel_estado.add(rdbtn_cerradas);
		
		group.add(rdbtn_activas);
		group.add(rdbtn_cerradas);
		
		JPanel panel_departa = new JPanel();
		panel_departa.setBackground(Color.WHITE);
		panel_departa.setBorder(new TitledBorder(null, "Departamento", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_departa = new GridBagConstraints();
		gbc_panel_departa.insets = new Insets(0, 0, 0, 5);
		gbc_panel_departa.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_departa.gridx = 3;
		gbc_panel_departa.gridy = 0;
		panel_filtro.add(panel_departa, gbc_panel_departa);
		comb_depart = new JComboBox(depart);
		panel_departa.add(comb_depart);
		
		JButton btn_refrescar = new JButton(new ImageIcon("refresco.icon"));
		btn_refrescar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					mostrarTabla(conexion);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btn_refrescar = new GridBagConstraints();
		gbc_btn_refrescar.gridx = 4;
		gbc_btn_refrescar.gridy = 0;
		panel_filtro.add(btn_refrescar, gbc_btn_refrescar);
		panel_central = new JPanel(new GridLayout());
		add(panel_central, BorderLayout.CENTER);
		
		mostrarTabla(conexion);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
								
				if(table.getSelectedRow() != -1){
					
					btn_modificar.setEnabled(true);
					btn_eliminar.setEnabled(true);
					btn_MostrarMensajes.setEnabled(true);
					
				} else {
					
					btn_modificar.setEnabled(false);
					btn_eliminar.setEnabled(false);
					btn_MostrarMensajes.setEnabled(false);
					
				}
			}
		});
	}
	
	public void mostrarTabla(Connection conexion) throws SQLException{
		boolean todo_bien = true;
		
		modelo = new DefaultTableModel();	
		table = new JTable();		
		
		table.setEnabled(true);
		table.setBorder(null);
		table.setModel(modelo);
		modelo.fireTableDataChanged();
		
		
		modelo.addColumn("ID");
		modelo.addColumn("Estado");
		modelo.addColumn("Fecha Apertura");
		modelo.addColumn("Fecha Cerrada");
		modelo.addColumn("Usuario");
		modelo.addColumn("Departamento");
				
		TicketUtil tickets_bus = new TicketUtil();
		ArrayList<Ticket> tickets = null;
		
		if(text_buscar.getText().equals("")){
			tickets = tickets_bus.buscar(conexion, group.getSelection().getActionCommand(), devolverDepartamento());
		}else{
			try{
				
				tickets = tickets_bus.buscar(conexion, Integer.parseInt(text_buscar.getText()), group.getSelection().getActionCommand(), devolverDepartamento());
			
			}catch(NumberFormatException e){
				
				text_buscar.setText("");
				JOptionPane.showMessageDialog(null, "Debes introducir n�meros");
				todo_bien = false;
				
			}
		}
		
		if(todo_bien){
			Ticket ticket = null;
			
			for(int i=0; i<tickets.size(); i++){
				
				Object[] fila = new Object[6];
				ticket = tickets.get(i);
				fila[0] = ticket.getId();
				fila[1] = ticket.getEstado();
				fila[2] = ticket.getFecha_apert();
				fila[3] = ticket.getFecha_cerr();
				
				Usuario usuario = new Usuario();
				UsuarioUtil usuutil = new UsuarioUtil();
				usuario = usuutil.getUsuarioTicket(conexion, ticket.getId());
				
				fila[4] = usuario.getNombre();
				fila[5] = usuario.getDepartament();
				
				modelo.addRow(fila);
				
			}
			
			//Para que no se puedan modificar los campos
			for (int j = 0; j < table.getColumnCount(); j++){
				
			    Class<?> col_class = table.getColumnClass(j);
			    table.setDefaultEditor(col_class, null);        // remove editor
			    
			}
			
			panel_central.removeAll();
			
			panel_central.add(new JScrollPane(table), BorderLayout.CENTER);
			
			SwingUtilities.updateComponentTreeUI(panel_central);
			
		}
	
	}
	
	public String devolverDepartamento(){
		
		if(comb_depart.getSelectedIndex() == 0){
			
			return "%";
			
		}else{
			
			return depart[comb_depart.getSelectedIndex()];
		}
		
	}
	
	public void llamarAgregarTicket(Connection conexion){
		
		UIAgregarUsuario agregar_u = new UIAgregarUsuario(conexion, this);
		agregar_u.setVisible(true);
		
	}
	
	

}
