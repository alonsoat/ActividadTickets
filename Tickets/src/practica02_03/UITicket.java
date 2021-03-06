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

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class UITicket extends JPanel {

	private static final long serialVersionUID = -7508988054079361673L;
	private JTable table;
	private JTextField text_buscar;
	private String[] depart = { "Todas", "Administraci�", "Inform�tica",
			"Disseny", "M�rketing" };
	private ArrayList<Integer> ids_table = new ArrayList<>();
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
	private Usuario login;
	private JButton btn_act;

	/**
	 * Create the panel.
	 * 
	 * @throws SQLException
	 * 
	 */
	public UITicket(final Connection conexion, final Usuario login)
			throws SQLException {
		setLayout(new BorderLayout(0, 0));
		this.login = login;
		JPanel panel_botones = new JPanel();
		panel_botones.setBorder(new EmptyBorder(20, 10, 20, 10));
		panel_botones.setBackground(Color.WHITE);
		add(panel_botones, BorderLayout.EAST);
		panel_botones.setLayout(new GridLayout(6, 1, 0, 30));

		btn_agregar = new JButton("Crear");
		btn_agregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				llamarAgregarTicket(conexion, login);

			}
		});
		if (login.isAdmin()) {
			btn_agregar.setEnabled(false);
		} else {
			btn_agregar.setEnabled(true);
		}
		panel_botones.add(btn_agregar);

		btn_modificar = new JButton("Modificar");
		if(!login.isAdmin()){
			btn_modificar.setVisible(false);
		}
		btn_modificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				llamarModificarTicket(conexion, login);

			}
		});
		
				btn_MostrarMensajes = new JButton("Mensajes");
				btn_MostrarMensajes.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						try {
							llamarMensajesTicket(conexion);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				});
				btn_MostrarMensajes.setEnabled(false);
				panel_botones.add(btn_MostrarMensajes);
		btn_modificar.setEnabled(false);
		panel_botones.add(btn_modificar);

		btn_eliminar = new JButton("Eliminar");
		
		if(!login.isAdmin()){
			btn_eliminar.setVisible(false);
		}
		
		btn_eliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					llamarEliminarTicket(conexion);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btn_eliminar.setEnabled(false);
		panel_botones.add(btn_eliminar);

		JPanel panel_filtro = new JPanel();
		panel_filtro.setBackground(Color.WHITE);
		panel_filtro.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Filtro",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		add(panel_filtro, BorderLayout.NORTH);
		GridBagLayout gbl_panel_filtro = new GridBagLayout();
		gbl_panel_filtro.columnWidths = new int[] { 90, 258, 160, 0, 0, 0, 0 };
		gbl_panel_filtro.rowHeights = new int[] { 50 };
		gbl_panel_filtro.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_filtro.rowWeights = new double[] { 0.0 };
		panel_filtro.setLayout(gbl_panel_filtro);

		JPanel panel_busqueda = new JPanel();
		panel_busqueda.setBackground(Color.WHITE);
		panel_busqueda.setBorder(new TitledBorder(null, "Buscar",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		GridBagConstraints gbc_panel_busqueda = new GridBagConstraints();
		gbc_panel_busqueda.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_busqueda.gridwidth = 2;
		gbc_panel_busqueda.insets = new Insets(0, 0, 0, 5);
		gbc_panel_busqueda.gridx = 0;
		gbc_panel_busqueda.gridy = 0;
		panel_filtro.add(panel_busqueda, gbc_panel_busqueda);
		panel_busqueda.setLayout(new GridLayout(0, 1, 0, 0));

		text_buscar = new JTextField();
		text_buscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					try {
						mostrarTabla(conexion);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
		});
		panel_busqueda.add(text_buscar);
		text_buscar.setColumns(30);

		JPanel panel_estado = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_estado.getLayout();
		flowLayout.setHgap(12);
		panel_estado.setBorder(new TitledBorder(null, "Estado",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_estado.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel_estado = new GridBagConstraints();
		gbc_panel_estado.fill = GridBagConstraints.VERTICAL;
		gbc_panel_estado.anchor = GridBagConstraints.WEST;
		gbc_panel_estado.insets = new Insets(0, 0, 0, 5);
		gbc_panel_estado.gridx = 2;
		gbc_panel_estado.gridy = 0;
		panel_filtro.add(panel_estado, gbc_panel_estado);

		rdbtn_todas = new JRadioButton("Todas");
		rdbtn_todas.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (rdbtn_todas.isSelected() && rdbtn_cerradas != null
						&& rdbtn_activas != null) {
					try {
						mostrarTabla(conexion);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});
		rdbtn_todas.setBackground(Color.WHITE);
		rdbtn_todas.setSelected(true);
		rdbtn_todas.setActionCommand("%");
		panel_estado.add(rdbtn_todas);

		rdbtn_activas = new JRadioButton("Abiertas");
		rdbtn_activas.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (rdbtn_activas.isSelected()) {
					try {
						mostrarTabla(conexion);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});
		rdbtn_activas.setBackground(Color.WHITE);
		rdbtn_activas.setSelected(false);
		rdbtn_activas.setActionCommand("Obert");
		panel_estado.add(rdbtn_activas);

		rdbtn_cerradas = new JRadioButton("Cerradas");
		rdbtn_cerradas.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (rdbtn_cerradas.isSelected()) {
					try {
						mostrarTabla(conexion);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});
		rdbtn_cerradas.setBackground(Color.WHITE);
		rdbtn_cerradas.setSelected(false);
		rdbtn_cerradas.setActionCommand("Tancat");
		panel_estado.add(rdbtn_cerradas);

		group.add(rdbtn_todas);
		group.add(rdbtn_activas);
		group.add(rdbtn_cerradas);

		JPanel panel_departa = new JPanel();
		panel_departa.setBackground(Color.WHITE);
		panel_departa.setBorder(new TitledBorder(null, "Departamento",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_departa = new GridBagConstraints();
		gbc_panel_departa.insets = new Insets(0, 0, 0, 5);
		gbc_panel_departa.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_departa.gridx = 3;
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

		btn_act = new JButton("Actualizar");
		btn_act.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					mostrarTabla(conexion);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		GridBagConstraints gbc_btn_act = new GridBagConstraints();
		gbc_btn_act.insets = new Insets(0, 0, 0, 5);
		gbc_btn_act.gridx = 4;
		gbc_btn_act.gridy = 0;
		panel_filtro.add(btn_act, gbc_btn_act);
		panel_central = new JPanel(new GridLayout());
		add(panel_central, BorderLayout.CENTER);

		mostrarTabla(conexion);

		SwingUtilities.updateComponentTreeUI(this);

	}

	public void mostrarTabla(Connection conexion) throws SQLException {

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

		TicketUtil tickets_bus = new TicketUtil();
		ArrayList<Ticket> tickets = null;
		ids_table.removeAll(ids_table);

		int pos;

		if (text_buscar.getText().equals("")) {

			con_id = false;

			if (login.isAdmin()) {

				tickets = tickets_bus.buscar(conexion, group.getSelection()
						.getActionCommand(), devolverDepartamento());

			} else {
				tickets = tickets_bus.buscar(conexion, group.getSelection()
						.getActionCommand(), devolverDepartamento(), login
						.getId());

				// Hacer consulta solo para ver sus tickets

			}

		} else {

			try {

				con_id = true;

				if (login.isAdmin()) {

					tickets = tickets_bus.buscar(conexion, Integer
							.parseInt(text_buscar.getText()), group
							.getSelection().getActionCommand(),
							devolverDepartamento());

				} else {
					tickets = tickets_bus.buscar(conexion, Integer
							.parseInt(text_buscar.getText()), group
							.getSelection().getActionCommand(),
							devolverDepartamento(), login.getId());

					// Hacer consulta solo para ver sus tickets

				}

			} catch (NumberFormatException e) {

				text_buscar.setText("");
				JOptionPane.showMessageDialog(null, "Debes introducir n�meros");
				todo_bien = false;

			}
		}

		if (todo_bien) {
			pos = 0;
			Ticket ticket = null;

			UsuarioUtil usuutil = null;
			Usuario usuario = null;

			for (int i = 0; i < tickets.size(); i++) {
				pos++;

				Object[] fila = new Object[6];
				ticket = tickets.get(i);
				fila[0] = ticket.getId();
				fila[1] = ticket.getEstado();
				fila[2] = ticket.getFecha_apert();
				fila[3] = ticket.getFecha_cerr();

				usuario = new Usuario();
				usuutil = new UsuarioUtil();

				if (con_id) {
					usuario = usuutil.getUsuarioTicket(conexion,
							ticket.getId(), devolverDepartamento(), group
									.getSelection().getActionCommand(), pos);
				} else {
					usuario = usuutil.getUsuarioTicket(conexion,
							devolverDepartamento(), group.getSelection()
									.getActionCommand(), pos);
				}

				ids_table.add(usuario.getId());
				fila[4] = usuario.getNombre();
				fila[5] = usuario.getDepartament();

				modelo.addRow(fila);

			}

			// Para que no se puedan modificar los campos
			for (int j = 0; j < table.getColumnCount(); j++) {

				Class<?> col_class = table.getColumnClass(j);
				table.setDefaultEditor(col_class, null); // remove editor

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

	public String devolverDepartamento() {

		if (comb_depart.getSelectedIndex() == 0) {

			return "%";

		} else {

			return depart[comb_depart.getSelectedIndex()];
		}

	}

	public void llamarAgregarTicket(Connection conexion, Usuario login) {

		UIAgregarTicket agregar_t = new UIAgregarTicket(conexion, this,
				login.getId());
		agregar_t.setVisible(true);

	}

	public void llamarModificarTicket(Connection conexion, Usuario login) {

		int id = (int) table.getValueAt(table.getSelectedRow(), 0);
		String estado = (String) table.getValueAt(table.getSelectedRow(), 1);
		String fecha_apert = (String) table.getValueAt(table.getSelectedRow(),
				2);
		String fecha_cerr = (String) table
				.getValueAt(table.getSelectedRow(), 3);

		Ticket ticket = new Ticket(id, estado, fecha_apert, fecha_cerr);

		if (ticket.getEstado().equals("Obert")) {

			ticket.setEstado("Tancat");

		} else if (ticket.getEstado().equals("Tancat")) {

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

	public void llamarEliminarTicket(Connection conexion) throws SQLException {

		int id = (int) table.getValueAt(table.getSelectedRow(), 0);
		String estado = (String) table.getValueAt(table.getSelectedRow(), 1);
		String fecha_apert = (String) table.getValueAt(table.getSelectedRow(),
				2);
		String fecha_cerr = (String) table
				.getValueAt(table.getSelectedRow(), 3);

		Ticket ticket = new Ticket(id, estado, fecha_apert, fecha_cerr);

		ticket.eliminar(conexion);
		deshabilitarBotones();
		mostrarTabla(conexion);

	}

	public void llamarMensajesTicket(Connection conexion) throws SQLException,
			InterruptedException {

		int id_ticket = (int) table.getValueAt(table.getSelectedRow(), 0);

		UIMensajes int_mensajes = new UIMensajes(conexion, id_ticket, login,
				ids_table.get(table.getSelectedRow()));
		// Actualizar a = new Actualizar();
		// a.run(int_mensajes, conexion, id_ticket);
		removeAll();
		add(int_mensajes, BorderLayout.CENTER);

		SwingUtilities.updateComponentTreeUI(this);

	}

	public void deshabilitarBotones() {

		if (table.getSelectedRow() != -1) {

			if (login.isAdmin()) {
				btn_agregar.setEnabled(false);
			} else {
				btn_agregar.setEnabled(true);
			}

			String estado_consulta = (String) table.getValueAt(
					table.getSelectedRow(), 1);

			if (estado_consulta.equals("Obert")) {
				btn_modificar.setEnabled(true);
				btn_modificar.setText("Cerrar");
			} else if (estado_consulta.equals("Tancat") && login.isAdmin()) {
				btn_modificar.setEnabled(true);
				btn_modificar.setText("Reabrir");
			} else if (estado_consulta.equals("Tancat") && !login.isAdmin()) {
				btn_modificar.setVisible(false);
				btn_modificar.setEnabled(false);
				btn_modificar.setText("Reabrir");
			}

			// Modificar si al final usuario puede cerrar
			if (!login.isAdmin()) {
				btn_modificar.setEnabled(false);
				btn_modificar.setVisible(false);
			}

			if (login.isAdmin()) {
				btn_eliminar.setEnabled(true);
			} else {
				btn_eliminar.setEnabled(false);
				btn_eliminar.setVisible(false);
			}

			if (estado_consulta.equals("Tancat")) {
				btn_MostrarMensajes.setEnabled(false);
			} else if (estado_consulta.equals("Obert")) {
				btn_MostrarMensajes.setEnabled(true);
			}

		} else {

			btn_modificar.setEnabled(false);
			btn_modificar.setText("Modificar");
			btn_eliminar.setEnabled(false);
			btn_MostrarMensajes.setEnabled(false);

			if (login.isAdmin()) {
				btn_agregar.setEnabled(false);
			} else {
				btn_agregar.setEnabled(true);
			}

		}

	}

}
