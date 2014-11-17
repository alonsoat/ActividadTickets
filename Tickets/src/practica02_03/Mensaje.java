package practica02_03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;



public class Mensaje {
	
	private int id;
	private String titol,text,imatge,fechaCrea;
	private PreparedStatement preparedStatament = null;
	private int id_usuario,id_ticket;
	
	
	public Mensaje(){
		
		
	}
	
	public Mensaje(int id,String titol,String text,String imatge,String fechaCrea){
		
		this.id=id;
		this.titol = titol;
		this.text = text;
		this.imatge = imatge;
		this.fechaCrea = fechaCrea;
		
	}
	
	public Mensaje(int id,String titol,String text,String imatge){
		
		this.id=id;
		this.titol = titol;
		this.text = text;
		this.imatge = imatge;
		this.fechaCrea = fechaCrea;
		
	}
	
	
	
	public void insertar(Connection conexion){
		
		try{
			
			
		String sql = "INSERT INTO missatges (titol,text,imatge,id_usuari,id_ticket) VALUES (?,?,?,?,?);";
		
	
		preparedStatament = conexion.prepareStatement(sql);
		
		
		preparedStatament.setString(1,this.titol);
		preparedStatament.setString(2,this.text);
		preparedStatament.setString(3,this.imatge);
		preparedStatament.setInt(4,this.id_usuario);
		preparedStatament.setInt(5,this.id_ticket);
		
		preparedStatament.executeUpdate();
		
		
		
		preparedStatament.close();
		
		}catch(SQLException ex){
			
			System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError insertando mensaje");
			
		}
		
		
		
	}
	
	
	public void leer(Connection conexion){
		
		
		try{
			
			String sql = "SELECT * FROM missatges WHERE id_usuari=? and id_ticket=?;";
			
			preparedStatament = conexion.prepareStatement(sql);
			
			preparedStatament.setInt(1,this.id_usuario);
			preparedStatament.setInt(2,this.id_ticket);
			
			ResultSet rs = preparedStatament.executeQuery();
			
			while(rs.next()){
				
			this.titol = rs.getString("nom");
			this.text = rs.getString("text");
			this.imatge = rs.getString("imatge");
			this.fechaCrea = rs.getString("data_crea");
			this.id_usuario =	rs.getInt("id_usuari");
			this.id_ticket = rs.getInt("id_ticket");
				
			
		
			}
	

			}catch(SQLException ex){
				
				System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError leyendo el mensaje");
				
			}
		
	
	}
	
	
	public void actualizar(Connection conexion){
		
		try{
			
			String sql = "UPDATE missatges SET titol=?,text=?,imatge=?) WHERE id_ticket = ? AND id_usuari = ?";
			preparedStatament = conexion.prepareStatement(sql);
			
			preparedStatament.setString(1,this.titol);
			preparedStatament.setString(2,this.text);
			preparedStatament.setString(3,this.imatge);
			preparedStatament.setInt(4,this.id_ticket);
			preparedStatament.setInt(5,this.id_usuario);
			
			
			preparedStatament.executeUpdate();
			
			preparedStatament.close();
			
		}catch(SQLException ex){
			
			System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError actualizando mensaje");
		
		}
		
		
		
		
	}
	
	
	
public void eliminar(Connection conexion){
		
		try{
			
		String sql = "DELETE FROM missatges WHERE id_ticket = ? AND id_usuari= ?";
		
		preparedStatament = conexion.prepareStatement(sql);
		
		preparedStatament.setInt(1, this.id_ticket);
		preparedStatament.setInt(2, this.id_usuario);
	
		preparedStatament.executeUpdate();
		
		preparedStatament.close();
		
		}catch(SQLException ex){
			
			System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError eliminando el mensaje");
			
			
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
	
	
	public int getId() {
		
		return id;
		
	}
	

	public void setId(int id) {
		
		this.id = id;
		
	}
	

	public String getFechaCrea() {
		
		return fechaCrea;
		
	}

	public void setFechaCrea(String fechaCrea) {
		
		this.fechaCrea = fechaCrea;
		
	}


}
