package practica02_03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;



public class Mensaje {
	

	private String titol,text,imatge;
	private GregorianCalendar fecha = new GregorianCalendar();
	private PreparedStatement preparedStatament = null;
	private int id_usuario,id_ticket;
	
	
	public void insertar(Connection conexion){
		
		try{
			
			
		String sql = "INSERT INTO mensaje (titol,text,imatge,id_usuari,id_ticket) VALUES (?,?,?,?);";
		
	
		preparedStatament = conexion.prepareStatement(sql);
		
		
		preparedStatament.setString(1,"Prueba");
		preparedStatament.setString(2,"Hola , probando ticket");
		preparedStatament.setString(3,"j");
		preparedStatament.setInt(4,1);
		preparedStatament.setInt(5,1);
		
		preparedStatament.executeUpdate();
		
		
		
		preparedStatament.close();
		
		}catch(SQLException ex){
			
			System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError insertando usuario");
			
		}
		
		
		
	}
	
	
	public void leer(Connection conexion){
		
		
		try{
			
			String sql = "SELECT * FROM missatges WHERE id_usuari=? and id_ticket=?;";
			
			preparedStatament = conexion.prepareStatement(sql);
			
			preparedStatament.setInt(1,this.id_usuario);
			preparedStatament.setInt(2,this.id_ticket);
			
			ResultSet rs = preparedStatament.executeQuery();
			
			
			
			/*while(rs.next()){
				
			this.titol = rs.getString("nom");
			this.mail = rs.getString("mail");
			this.pass = rs.getString("pass");
			this.departament = rs.getString("departament");
			this.admin =	rs.getBoolean("admin");
				
			
		
			}*/
	

			}catch(SQLException ex){
				
				System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError leyendo el usuario");
				
			}
		
	
	}
	
	
	public String getTitol() {
		
		return titol;
		
	}
	
	public void setTitol(String titol) {
		
		this.titol = titol;
		
	}
	
	public String getText() {
		
		return text;
		
	}
	
	public void setText(String text) {
		
		this.text = text;
		
	}
	
	public String getImatge() {
		
		return imatge;
		
	}
	
	public void setImatge(String imatge) {
		
		this.imatge = imatge;
		
	}

	public int getId_usuario() {
		
		return id_usuario;
		
	}



	public void setId_usuario(int id_usuario) {
		
		this.id_usuario = id_usuario;
		
	}



	public int getId_ticket() {
		
		return id_ticket;
		
	}



	public void setId_ticket(int id_ticket) {
		
		this.id_ticket = id_ticket;
		
	}
	

}
