package practica02_03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TicketUtil {
	
	private PreparedStatement preparedStatament = null;
	
	public ArrayList<Ticket> buscar(Connection conexion,int busqueda, String activa, String depart){
		
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		
		try{
			
			
			String sql = "SELECT t.* "
					+ "FROM tickets AS t , missatges AS m , usuaris AS u "
					+ "WHERE t.id = m.id_ticket "
					+ "AND m.id_usuari = u.id "
					+ "AND t.id = ? "
					+ "AND t.estat LIKE ? "
					+ "AND u.departament LIKE ?;";
			
			preparedStatament = conexion.prepareStatement(sql);
			
			preparedStatament.setInt(1, busqueda);
			preparedStatament.setString(2, activa);
			preparedStatament.setString(3, depart);
			
			ResultSet rs = preparedStatament.executeQuery();
			
			while(rs.next()){
				
				int id= rs.getInt("id");
				String estado = rs.getString("estat");
				String fecha_abri = rs.getString("data_obri");
				String fecha_cerr = rs.getString("data_tanca");
			
				tickets.add(new Ticket(id, estado, fecha_abri, fecha_cerr));
			
			}
	
			rs.close();
			preparedStatament.close();
			
		}catch(SQLException ex){
			
			System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError recuperando tickets con ID");
			
		}
		
		return tickets;
			
	}
	
	
	
	public ArrayList<Ticket> buscar(Connection conexion, String activa, String depart){
		
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();

		try{
			
			
			String sql = "SELECT t.* "
					+ "FROM tickets AS t , missatges AS m , usuaris AS u "
					+ "WHERE m.id_usuari = u.id "
					+ "AND t.id = m.id_ticket "
					+ "AND t.estat LIKE ? "
					+ "AND u.departament LIKE ?;";
			
			preparedStatament = conexion.prepareStatement(sql);
			
			preparedStatament.setString(1, activa);
			preparedStatament.setString(2, depart);
			
			ResultSet rs = preparedStatament.executeQuery();
			
			while(rs.next()){
				
				int id = rs.getInt("id");
				String estado = rs.getString("estat");
				String fecha_abri = rs.getString("data_obri");
				String fecha_cerr = rs.getString("data_tanca");
	
			
				tickets.add(new Ticket(id, estado, fecha_abri, fecha_cerr));
		
			}
	
			rs.close();
			preparedStatament.close();
			
		}catch(SQLException ex){
			
			System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError recuperando tickets sin ID");
			
		}
		
		return tickets;
				
	}

	

}
