package practica02_03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class TicketUtil {

	private static PreparedStatement preparedStatament = null;

	public ArrayList<Ticket> buscar(Connection conexion, int busqueda,
			String activa, String depart) {

		ArrayList<Ticket> tickets = new ArrayList<Ticket>();

		try {

			String sql = "SELECT DISTINCT t.* "
					+ "FROM tickets AS t , missatges AS m , usuaris AS u "
					+ "WHERE t.id = m.id_ticket " + "AND m.id_usuari = u.id "
					+ "AND t.id = ? " + "AND t.estat LIKE ? "
					+ "AND u.admin = 0 " + "AND u.departament LIKE ? "
					+ "ORDER BY t.id; ";

			preparedStatament = conexion.prepareStatement(sql);

			preparedStatament.setInt(1, busqueda);
			preparedStatament.setString(2, activa);
			preparedStatament.setString(3, depart);

			ResultSet rs = preparedStatament.executeQuery();

			while (rs.next()) {

				int id = rs.getInt("id");
				String estado = rs.getString("estat");
				String fecha_abri = rs.getString("data_obri");
				String fecha_cerr = rs.getString("data_tanca");

				tickets.add(new Ticket(id, estado, fecha_abri, fecha_cerr));

			}

			rs.close();
			preparedStatament.close();

		} catch (SQLException ex) {

			System.err.println(ex.getErrorCode() + " ," + ex.getMessage()
					+ " ," + ex.getSQLState()
					+ "\nError recuperando tickets con ID");

		}

		return tickets;

	}

	public ArrayList<Ticket> buscar(Connection conexion, String activa,
			String depart) {

		ArrayList<Ticket> tickets = new ArrayList<Ticket>();

		try {

			String sql = "SELECT DISTINCT t.* "
					+ "FROM tickets AS t , missatges AS m , usuaris AS u "
					+ "WHERE m.id_usuari = u.id " + "AND t.id = m.id_ticket "
					+ "AND t.estat LIKE ? " + "AND u.departament LIKE ? "
					+ "AND u.admin = 0 " + "ORDER BY t.id;";

			preparedStatament = conexion.prepareStatement(sql);

			preparedStatament.setString(1, activa);
			preparedStatament.setString(2, depart);

			ResultSet rs = preparedStatament.executeQuery();

			while (rs.next()) {

				int id = rs.getInt("id");
				String estado = rs.getString("estat");
				String fecha_abri = rs.getString("data_obri");
				String fecha_cerr = rs.getString("data_tanca");

				tickets.add(new Ticket(id, estado, fecha_abri, fecha_cerr));

			}

			rs.close();
			preparedStatament.close();

		} catch (SQLException ex) {

			System.err.println(ex.getErrorCode() + " ," + ex.getMessage()
					+ " ," + ex.getSQLState()
					+ "\nError recuperando tickets sin ID");

		}

		return tickets;

	}

	public ArrayList<Ticket> buscar(Connection conexion, int busqueda,
			String activa, String depart, int id_usuario) {

		ArrayList<Ticket> tickets = new ArrayList<Ticket>();

		try {

			String sql = "SELECT DISTINCT t.* "
					+ "FROM tickets AS t , missatges AS m , usuaris AS u "
					+ "WHERE t.id = m.id_ticket " + "AND m.id_usuari = u.id "
					+ "AND t.id = ? " + "AND t.estat LIKE ? "
					+ "AND u.departament LIKE ? " + "AND u.admin = 0 "
					+ "AND u.id = ?";

			preparedStatament = conexion.prepareStatement(sql);

			preparedStatament.setInt(1, busqueda);
			preparedStatament.setString(2, activa);
			preparedStatament.setString(3, depart);
			preparedStatament.setInt(4, id_usuario);

			ResultSet rs = preparedStatament.executeQuery();

			while (rs.next()) {

				int id = rs.getInt("id");
				String estado = rs.getString("estat");
				String fecha_abri = rs.getString("data_obri");
				String fecha_cerr = rs.getString("data_tanca");

				tickets.add(new Ticket(id, estado, fecha_abri, fecha_cerr));

			}

			rs.close();
			preparedStatament.close();

		} catch (SQLException ex) {

			System.err.println(ex.getErrorCode() + " ," + ex.getMessage()
					+ " ," + ex.getSQLState()
					+ "\nError recuperando tickets con ID");

		}

		return tickets;

	}

	public ArrayList<Ticket> buscar(Connection conexion, String activa,
			String depart, int id_usuario) {

		ArrayList<Ticket> tickets = new ArrayList<Ticket>();

		try {

			String sql = "SELECT DISTINCT t.* "
					+ "FROM tickets AS t , missatges AS m , usuaris AS u "
					+ "WHERE m.id_usuari = u.id " + "AND t.id = m.id_ticket "
					+ "AND t.estat LIKE ? " + "AND u.departament LIKE ? "
					+ "AND u.admin = 0 " + "AND u.id = ?";

			preparedStatament = conexion.prepareStatement(sql);

			preparedStatament.setString(1, activa);
			preparedStatament.setString(2, depart);
			preparedStatament.setInt(3, id_usuario);

			ResultSet rs = preparedStatament.executeQuery();

			while (rs.next()) {

				int id = rs.getInt("id");
				String estado = rs.getString("estat");
				String fecha_abri = rs.getString("data_obri");
				String fecha_cerr = rs.getString("data_tanca");

				tickets.add(new Ticket(id, estado, fecha_abri, fecha_cerr));

			}

			rs.close();
			preparedStatament.close();

		} catch (SQLException ex) {

			System.err.println(ex.getErrorCode() + " ," + ex.getMessage()
					+ " ," + ex.getSQLState()
					+ "\nError recuperando tickets sin ID");

		}

		return tickets;

	}

	public static void copiaSeguridadTickets(Connection conexion, String ruta) {

		String barra = "";

		if (ruta != null) {
			if (ruta.contains("/")) {
				barra = "/";
			} else if (ruta.contains("\\")) {
				barra = "\\\\";
			}

			try {

				String sql = "SELECT  * " + "FROM tickets " + "INTO OUTFILE ? "
						+ "FIELDS TERMINATED BY ? " + "ENCLOSED BY ? "
						+ "LINES TERMINATED BY ?";

				preparedStatament = conexion.prepareStatement(sql);

				preparedStatament.setString(1, ruta + barra
						+ "copiaTickets.csv");
				preparedStatament.setString(2, ",");
				preparedStatament.setString(3, "\"");
				preparedStatament.setString(4, "\n");

				ResultSet rs = preparedStatament.executeQuery();
				rs.close();
				preparedStatament.close();
			} catch (SQLException ex) {

				System.err.println(ex.getErrorCode() + " ," + ex.getMessage()
						+ " ," + ex.getSQLState()
						+ "\nError haciendo copia de seguridad de los tickets");
				

				JOptionPane.showMessageDialog(null, "No tienes permisos para crear en esa ruta");

			}
		}

	}

}
