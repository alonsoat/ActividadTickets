package practica02_03;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class UIUsuarios extends JPanel {

	private static final long serialVersionUID = -7508988054079361673L;
	private JTable table;
	private JTextField text_buscar;
	private String[] depart = {"Todas", "Administració", "Informàtica", "Disseny", "Màrketing"};
	private ArrayList<Integer> ids_table = new ArrayList<>();
	private ButtonGroup group = new ButtonGroup();
	private DefaultTableModel modelo;
	private JComboBox<String> comb_depart;
	private JPanel panel_central;
	private JButton btn_eliminar;
	private JButton btn_editar;
	private JButton btn_agregar;
	private Usuario login;
	
	/**
	 * Create the panel.
	 */
	public UIUsuarios(final Connection conexion, final Usuario login
			) throws SQLException {
		setLayout(new BorderLayout(0, 0));
		this.login = login;
		JPanel panel_botones = new JPanel();
		panel_botones.setBorder(new EmptyBorder(20, 10, 20, 10));
		panel_botones.setBackground(Color.WHITE);
		add(panel_botones, BorderLayout.EAST);
		panel_botones.setLayout(new GridLayout(6, 1, 0, 30));
		
		
		btn_agregar = new JButton("Agregar");
		btn_agregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
				llamarAgregarUsuario(conexion, login);
				
			}
		});
		panel_botones.add(btn_agregar);
		
		btn_editar = new JButton("Editar");
		btn_editar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
			}
		});
		btn_editar.setEnabled(false);
		panel_botones.add(btn_editar);
		
		btn_eliminar = new JButton("Eliminar");
		btn_eliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
			}
		});
		btn_eliminar.setEnabled(false);
		panel_botones.add(btn_eliminar);
		
		JPanel panel_filtro = new JPanel();
		panel_filtro.setBackground(Color.WHITE);
		panel_filtro.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Filtro", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(panel_filtro, BorderLayout.NORTH);
		GridBagLayout gbl_panel_filtro = new GridBagLayout();
		gbl_panel_filtro.columnWidths = new int[] {90, 258, 160, 0, 0, 0, 0};
		gbl_panel_filtro.rowHeights = new int[] {50};
		gbl_panel_filtro.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_filtro.rowWeights = new double[]{0.0};
		panel_filtro.setLayout(gbl_panel_filtro);
		
		JPanel panel_busqueda = new JPanel();
		panel_busqueda.setBackground(Color.WHITE);
		panel_busqueda.setBorder(new TitledBorder(null, "Buscar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		GridBagConstraints gbc_panel_busqueda = new GridBagConstraints();
		gbc_panel_busqueda.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_busqueda.gridwidth = 4;
		gbc_panel_busqueda.insets = new Insets(0, 0, 0, 5);
		gbc_panel_busqueda.gridx = 0;
		gbc_panel_busqueda.gridy = 0;
		panel_filtro.add(panel_busqueda, gbc_panel_busqueda);
		panel_busqueda.setLayout(new GridLayout(0, 1, 0, 0));
		
		text_buscar = new JTextField();
		text_buscar.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				try {
					mostrarTabla(conexion);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel_busqueda.add(text_buscar);
		text_buscar.setColumns(30);
		
		JPanel panel_departa = new JPanel();
		panel_departa.setBackground(Color.WHITE);
		panel_departa.setBorder(new TitledBorder(null, "Departamento", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_departa = new GridBagConstraints();
		gbc_panel_departa.insets = new Insets(0, 0, 0, 5);
		gbc_panel_departa.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_departa.gridx = 4;
		gbc_panel_departa.gridy = 0;
		panel_filtro.add(panel_departa, gbc_panel_departa);
		comb_depart = new JComboBox(depart);
		comb_depart.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try {
					mostrarTabla(conexion);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel_departa.add(comb_depart);
		panel_central = new JPanel(new GridLayout());
		add(panel_central, BorderLayout.CENTER);
		
		mostrarTabla(conexion);
		
		
	}
	
	public void mostrarTabla(Connection conexion) throws SQLException{

		boolean con_id = false;
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
				
		UsuarioUtil usuarios_bus = new UsuarioUtil();
		ArrayList<Usuario> usuarios = null;
		
		int pos;
		
		if(text_buscar.getText().equals("")){
			
			con_id=false;
			
			usuarios = usuarios_bus.buscar(conexion, group.getSelection().getActionCommand(), devolverDepartamento());
			
		
		} else {
			
			try{
				
				con_id=true;
				
				usuarios = usuarios_bus.buscar(conexion, Integer.parseInt(text_buscar.getText()), group.getSelection().getActionCommand(), devolverDepartamento());

				
			}catch(NumberFormatException e){
				
				text_buscar.setText("");
				JOptionPane.showMessageDialog(null, "Debes introducir números");
				todo_bien=false;
				
			}
		}
		
		String adminis;
		
		if(todo_bien){
			pos = 0;
			Ticket ticket = null;
				
			UsuarioUtil usuutil = null;
			Usuario usuario = null;
				
			for(int i=0; i<usuarios.size(); i++){
				pos++;
				
				Object[] fila = new Object[3];
				usuario = usuarios.get(i);
				fila[0] = usuario.getNombre();
				fila[1] = usuario.getMail();
				fila[2] = usuario.getDepartament();
				
				if(usuario.isAdmin()){
					
					adminis="Administrador";
					
				}else{
					
					adminis="Usuario";
					
				}
				
				fila[3] = adminis;
					
				modelo.addRow(fila);
					
			}
			
				
			//Para que no se puedan modificar los campos
			for (int j = 0; j < table.getColumnCount(); j++){
					
				Class<?> col_class = table.getColumnClass(j);
				table.setDefaultEditor(col_class, null);        // remove editor
			    
			}
						
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
										
					deshabilitarBotones();
					
				}
			});
				
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

	
	public void llamarModificarTicket(Connection conexion, Usuario login){
		
		
		int id=(int) table.getValueAt(table.getSelectedRow(), 0);
		String estado = (String) table.getValueAt(table.getSelectedRow(), 1);
		String fecha_apert = (String) table.getValueAt(table.getSelectedRow(), 2);
		String fecha_cerr = (String) table.getValueAt(table.getSelectedRow(), 3);
		
	
		Ticket ticket = new Ticket(id, estado, fecha_apert, fecha_cerr);
		
		if(ticket.getEstado().equals("Obert")){
			
			ticket.setEstado("Tancat");
			
		}else if(ticket.getEstado().equals("Tancat")){
			
			ticket.setEstado("Obert");
			
		}
		ticket.actualizar(conexion, login.isAdmin());
		
		try {
			mostrarTabla(conexion);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		deshabilitarBotones();	
	
		
	}
	
	public void llamarEliminarTicket(Connection conexion) throws SQLException{
		
		int id=(int) table.getValueAt(table.getSelectedRow(), 0);
		String estado = (String) table.getValueAt(table.getSelectedRow(), 1);
		String fecha_apert = (String) table.getValueAt(table.getSelectedRow(), 2);
		String fecha_cerr = (String) table.getValueAt(table.getSelectedRow(), 3);
		
		Ticket ticket = new Ticket(id, estado, fecha_apert, fecha_cerr);
		
		ticket.eliminar(conexion);
		deshabilitarBotones();
		mostrarTabla(conexion);
		
	}
	
	public void deshabilitarBotones(){
		
		if(table.getSelectedRow() != -1){
			
			btn_editar.setEnabled(true);
			btn_eliminar.setEnabled(true);
	
		} else {
			
			btn_editar.setEnabled(false);
			btn_eliminar.setEnabled(false);
			
		}
		
	}
	

}
