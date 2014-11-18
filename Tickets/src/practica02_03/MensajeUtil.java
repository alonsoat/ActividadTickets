package practica02_03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MensajeUtil {
	
	private PreparedStatement preparedStatament = null;
	
	public ArrayList<Mensaje> mostarMensajes(Connection conexion,int id_ticket,int id_usuario){
		
		ArrayList<Mensaje> mensajes = new ArrayList<Mensaje>();
		
		
		try{
			
		String sql = "SELECT * FROM missatges WHERE id_usuari = ? AND id_ticket = ?";
		
		preparedStatament = conexion.prepareStatement(sql);
		
		preparedStatament.setInt(1,id_usuario);
		preparedStatament.setInt(2, id_ticket);
		
		
		ResultSet rs = preparedStatament.executeQuery();
		
		while(rs.next()){
			
			int id = rs.getInt("id");
			String titulo = rs.getString("titol");
			String texto = rs.getString("text");
			String imagen = rs.getString("imatge");
			String fechaCrea = rs.getString("data_crea");
			
			Mensaje m = new Mensaje(id,titulo,texto,imagen,fechaCrea);
			
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
