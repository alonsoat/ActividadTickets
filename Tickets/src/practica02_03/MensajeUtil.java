package practica02_03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MensajeUtil {
	
	private static PreparedStatement preparedStatament = null;
	
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
	
	public static void copiaSeguridadMensajes(Connection conexion, String ruta){
		
		String barra = "";
		
		if(ruta != null){
		
			if(ruta.contains("/")){
				barra = "/";
			}else if(ruta.contains("\\")){
				barra = "\\\\";
			}
			
			try{
				
				String sql = "SELECT  id,titol "
							+ "FROM missatges "
							+ "INTO OUTFILE ? "
							+ "FIELDS TERMINATED BY ? "
							+ "ENCLOSED BY ? "
							+"LINES TERMINATED BY ?";
	
				preparedStatament = conexion.prepareStatement(sql);
				
				preparedStatament.setString(1, ruta + barra + "copiaMensajes.csv");
				preparedStatament.setString(2,",");
				preparedStatament.setString(3, "\"");
				preparedStatament.setString(4, "\n");
				
				
				
				ResultSet rs = preparedStatament.executeQuery();
			
			
			}catch(SQLException ex){
				
				System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError haciendo copia de seguridad de  mensajes");
				
			}
		}
	}
	
	

}
