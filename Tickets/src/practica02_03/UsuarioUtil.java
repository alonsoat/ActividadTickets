package practica02_03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioUtil {

	private static PreparedStatement preparedStatament = null;

	public ArrayList<Usuario> listarUsuarios(Connection conexion,
			String departamento) {

		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		try {

			String sql = "SELECT * FROM usuaris WHERE departament LIKE ?;";

			preparedStatament = conexion.prepareStatement(sql);

			preparedStatament.setString(1, departamento);

			ResultSet rs = preparedStatament.executeQuery();

			while (rs.next()) {

				int id = rs.getInt("id");
				String nombre = rs.getString("nom");
				String mail = rs.getString("mail");
				String pass = rs.getString("pass");
				String departament = rs.getString("departament");
				Boolean admin = rs.getBoolean("admin");

				Usuario u = new Usuario(id, nombre, mail, pass, departament,
						admin);

				usuarios.add(u);

			}

			rs.close();
			preparedStatament.close();

		} catch (SQLException ex) {

			System.err.println(ex.getErrorCode() + " ," + ex.getMessage()
					+ " ," + ex.getSQLState() + "\nError recuperando usuarios");

		}

		return usuarios;
	}

	public ArrayList<Usuario> listarUsuarios(Connection conexion,
			String departamento, int id_usuario) {

		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		try {

			String sql = "SELECT * FROM usuaris WHERE departament LIKE ? AND id = ? ;";

			preparedStatament = conexion.prepareStatement(sql);

			preparedStatament.setString(1, departamento);
			preparedStatament.setInt(2, id_usuario);

			ResultSet rs = preparedStatament.executeQuery();

			while (rs.next()) {

				int id = rs.getInt("id");
				String nombre = rs.getString("nom");
				String mail = rs.getString("mail");
				String pass = rs.getString("pass");
				String departament = rs.getString("departament");
				Boolean admin = rs.getBoolean("admin");

				Usuario u = new Usuario(id, nombre, mail, pass, departament,
						admin);

				usuarios.add(u);

			}

			rs.close();
			preparedStatament.close();

		} catch (SQLException ex) {

			System.err.println(ex.getErrorCode() + " ," + ex.getMessage()
					+ " ," + ex.getSQLState() + "\nError recuperando usuarios");

		}

		return usuarios;

	}

	public Usuario getUsuarioTicket(Connection conexion, int id_ticket,
			String depart, String estat, int pos) {

		Usuario u = null;

		try {

			String sql = "SELECT DISTINCT t.id AS id_tick, t.estat, t.data_obri, t. data_tanca, u.* "
					+ "FROM usuaris AS u, missatges AS m , tickets AS t "
					+ "WHERE t.id = m.id_ticket "
					+ "AND m.id_usuari = u.id "
					+ "AND t.id = ? "
					+ "AND t.estat LIKE ? "
					+ "AND u.departament LIKE ? "
					+ "AND u.admin = 0 "
					+ "GROUP BY t.id " + "ORDER BY t.id; ";

			preparedStatament = conexion.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			preparedStatament.setInt(1, id_ticket);
			preparedStatament.setString(2, estat);
			preparedStatament.setString(3, depart);

			ResultSet rs = preparedStatament.executeQuery();

			rs.absolute(pos);

			// while(rs.next()){

			int id = rs.getInt("id");
			String nombre = rs.getString("nom");
			String mail = rs.getString("mail");
			String pass = rs.getString("pass");
			String departament = rs.getString("departament");
			Boolean admin = rs.getBoolean("admin");

			u = new Usuario(id, nombre, mail, pass, departament, admin);

			// }

			rs.close();
			preparedStatament.close();

		} catch (SQLException ex) {

			System.err.println(ex.getErrorCode() + " ," + ex.getMessage()
					+ " ," + ex.getSQLState()
					+ "\nError recuperando usuario desde id ticket");

		}

		return u;

	}

	public Usuario getUsuarioTicket(Connection conexion, String depart,
			String estat, int pos) {

		Usuario u = null;

		try {

			String sql = "SELECT DISTINCT t.id AS id_tick, t.estat, t.data_obri, t. data_tanca, u.* "
					+ "FROM usuaris AS u, missatges AS m , tickets AS t "
					+ "WHERE t.id = m.id_ticket "
					+ "AND m.id_usuari = u.id "
					+ "AND t.estat LIKE ? "
					+ "AND u.departament LIKE ? "
					+ "AND u.admin = 0 " + "GROUP BY t.id " + "ORDER BY t.id; ";

			preparedStatament = conexion.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			preparedStatament.setString(1, estat);
			preparedStatament.setString(2, depart);

			ResultSet rs = preparedStatament.executeQuery();

			rs.absolute(pos);

			// while(rs.next()){

			int id = rs.getInt("id");
			String nombre = rs.getString("nom");
			String mail = rs.getString("mail");
			String pass = rs.getString("pass");
			String departament = rs.getString("departament");
			Boolean admin = rs.getBoolean("admin");

			u = new Usuario(id, nombre, mail, pass, departament, admin);

			// }

			rs.close();
			preparedStatament.close();

		} catch (SQLException ex) {

			System.err.println(ex.getErrorCode() + " ," + ex.getMessage()
					+ " ," + ex.getSQLState()
					+ "\nError recuperando usuario desde id ticket");

		}

		return u;

	}

	public static void copiaSeguridadUsuarios(Connection conexion, String ruta) {

		String barra = "";

		if (ruta != null) {
			if (ruta.contains("/")) {
				barra = "/";
			} else if (ruta.contains("\\")) {
				barra = "\\\\";
			}

			try {

				String sql = "SELECT * " + "FROM usuaris " + "INTO OUTFILE ? "
						+ "FIELDS TERMINATED BY ? " + "ENCLOSED BY ? "
						+ "LINES TERMINATED BY ?";

				preparedStatament = conexion.prepareStatement(sql);

				preparedStatament.setString(1, ruta + barra
						+ "copiaUsuarios.csv");
				preparedStatament.setString(2, ",");
				preparedStatament.setString(3, "\"");
				preparedStatament.setString(4, "\n");

				ResultSet rs = preparedStatament.executeQuery();
				rs.close();
				preparedStatament.close();
			} catch (SQLException ex) {

				System.err
						.println(ex.getErrorCode()
								+ " ,"
								+ ex.getMessage()
								+ " ,"
								+ ex.getSQLState()
								+ "\nError haciendo copia de seguridad de los Usuarios");

			}

		}
	}

}
