package practica02_03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MensajeUtil {
	
	private PreparedStatement preparedStatament = null;
	
	public ArrayList<Mensaje> mostarMensajes(Connection conexion,int id_tickets){
		
		ArrayList<Mensaje> mensajes = new ArrayList<Mensaje>();
		
		
		try{
			
		String sql = "SELECT * FROM missatges WHERE id_ticket = ? ORDER BY data_crea ASC";
		preparedStatament = conexion.prepareStatement(sql);
		
		
		preparedStatament.setInt(1, id_tickets);
		
		
		ResultSet rs = preparedStatament.executeQuery();
		
		while(rs.next()){
			
			int id = rs.getInt("id");
			String titulo = rs.getString("titol");
			String texto = rs.getString("text");
			String imagen = rs.getString("imatge");
			String fechaCrea = rs.getString("data_crea");
			int id_usuari = rs.getInt("id_usuari");
			int id_ticket = rs.getInt("id_ticket");
			
			Mensaje m = new Mensaje(id,titulo,texto,imagen,fechaCrea,id_usuari,id_ticket);
			
			mensajes.add(m);
			
		}
		
		rs.close();
		preparedStatament.close();
		
		}catch(SQLException ex){
			
			System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError recuperando mensajes");
			
		}
	
		return mensajes;
		
	}
	

}
