package practica02_03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Usuario {
	
	public Usuario(){
		
	}
	
	public Usuario(Connection conexion, int id){
		
		try{
			
			String sql = "SELECT * FROM usuaris WHERE id = ?;";
			
			preparedStatament = conexion.prepareStatement(sql);
			
			
			preparedStatament.setInt(1, id);
			
			ResultSet rs = preparedStatament.executeQuery();
			
			
			while(rs.next()){
				
				this.id=rs.getInt("id");
				this.nombre=rs.getString("nom");
				this.pass=rs.getString("pass");
				this.mail=rs.getString("mail");
				this.departament=rs.getString("departament");
					
			}
			
			rs.close();
			preparedStatament.close();
			
		}catch(SQLException ex){
			  
			System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError validando el usuario");
			
		}
		
		
	}


	public Usuario(int id,String nombre,String mail,String pass,String departament,boolean admin){
		
		this.id=id;
		this.nombre=nombre;
		this.mail=mail;
		this.pass=pass;
		this.departament=departament;
		this.admin=admin;
	}
	
	private String nombre,mail,pass;
	private String departament;
	private int id;
	
	boolean admin;
	private PreparedStatement preparedStatament = null;
	
	
	public void insertar(Connection conexion){
		
		int id_usuario = 0;
		
		try{
		
		String sql = "INSERT INTO usuaris (nom,mail,pass,departament,admin) VALUES (?,?,SHA1(?),?,?);";
		
	
		preparedStatament = conexion.prepareStatement(sql);
		
		
		preparedStatament.setString(1,this.nombre);
		preparedStatament.setString(2,this.mail);
		preparedStatament.setString(3,this.pass);
		preparedStatament.setString(4,this.departament);
		preparedStatament.setBoolean(5, this.admin);
		
		preparedStatament.executeUpdate();
		
		
		
	/*try (ResultSet cogerId = preparedStatament.getGeneratedKeys()) {

		
			if (cogerId.next()) {

				this.id=cogerId.getInt(1);

			} else {

				System.out.println("No se puede recoger el ultimo Id");

			}

		}*/
		
		preparedStatament.close();
		
	
		
		
		}catch(SQLException ex){
			
			System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError insertando usuario");
			
		}
		
		//return id_usuario;
	
	}
	
	public void leer(Connection conexion){
		
		try{
			
			
			
			String sql = "SELECT * FROM usuaris WHERE nom LIKE ? or id=? ;";
			
			preparedStatament = conexion.prepareStatement(sql);
			
			//preparedStatament.setInt(1,id);
			preparedStatament.setString(1,this.nombre+"%");
			preparedStatament.setInt(2,this.id);
			
			ResultSet rs = preparedStatament.executeQuery();
			
			
			
			while(rs.next()){
				
			this.nombre = rs.getString("nom");
			this.mail = rs.getString("mail");
			this.pass = rs.getString("pass");
			this.departament = rs.getString("departament");
			this.admin =	rs.getBoolean("admin");
				
			
		
			}
	
			rs.close();
			preparedStatament.close();

			}catch(SQLException ex){
				
				System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError leyendo el usuario");
				
			}
		
		
		
		}
	
	
	
	public void actualizar(Connection conexion){
		
		try{
			
			String sql = "UPDATE usuaris SET nom=?,mail=?,pass=SHA1(?),departament=?,admin=? WHERE id = ?";
			preparedStatament = conexion.prepareStatement(sql);
			
			preparedStatament.setString(1,this.nombre);
			preparedStatament.setString(2,this.mail);
			preparedStatament.setString(3,this.pass);
			preparedStatament.setString(4,this.departament);
			preparedStatament.setBoolean(5,  this.admin);
			preparedStatament.setInt(6, this.id);
			
			
			preparedStatament.executeUpdate();
			
			preparedStatament.close();
			
		}catch(SQLException ex){
			
			System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError leyendo el usuario");
		}
		
	}
	
	
	public void eliminar(Connection conexion,int id){
		
		try{
			
		String sql = "DELETE FROM usuaris WHERE id = ?";
		
		preparedStatament = conexion.prepareStatement(sql);
		
		preparedStatament.setInt(1, id);
	
		preparedStatament.executeUpdate();
		
		preparedStatament.close();
		
		}catch(SQLException ex){
			
			System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError eliminando el usuario");
			
			
		}
	}
	
	
	
	public boolean validarUsuario(Connection conexion){
		
		boolean valido=false;
		
		
		try{
			
			String sql = "SELECT * FROM usuaris WHERE nom LIKE ? AND pass LIKE SHA1(?);";
			
			preparedStatament = conexion.prepareStatement(sql);
			
			
			preparedStatament.setString(1, this.nombre);
			preparedStatament.setString(2, this.pass);
			
			ResultSet rs = preparedStatament.executeQuery();
			
			
			while(rs.next()){
				
				this.id=rs.getInt("id");
				this.nombre=rs.getString("nom");
				this.pass=rs.getString("pass");
				this.mail=rs.getString("mail");
				this.departament=rs.getString("departament");
				this.admin=rs.getBoolean("admin");
				
				valido=true;
					
			}
			
			rs.close();
			preparedStatament.close();
			
		}catch(SQLException ex){
			
			System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError validando el usuario");
			
		}
		
		return valido;
		
		
	}
	
	
	/*public int retornarId(Connection conexion){
		
		int id=0;
		
		
		try{
			
			String sql = "SELECT * FROM usuaris WHERE nom LIKE ? AND pass LIKE SHA1(?);";
			
			preparedStatament = conexion.prepareStatement(sql);
			
			
			preparedStatament.setString(1, this.nombre);
			preparedStatament.setString(2, this.pass);
			
			ResultSet rs = preparedStatament.executeQuery();
			
			
			while(rs.next()){
				
				id=rs.getInt("id");
					
			}
			
			
		}catch(SQLException ex){
			
			System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError validando el usuario");
			
		}
		
		return id;
		
		
	}*/
	
	
	
	
	public String getNombre() {
		
		return nombre;
		
	}

	public void setNombre(String nombre) {
		
		this.nombre = nombre;
		
	}

	public String getMail() {
		
		return mail;
		
	}

	public void setMail(String mail) {
		
		this.mail = mail;
		
	}

	public String getPass() {
		
		return pass;
		
	}

	public void setPass(String pass) {
		
		this.pass = pass;
		
	}

	public String getDepartament() {
		
		return departament;
		
	}

	public void setDepartament(String departament) {
		
		this.departament = departament;
		
	}

	public int getId() {
		
		return id;
		
	}

	public void setId(int id) {
		
		this.id = id;
		
	}

	public boolean isAdmin() {
		
		return admin;
		
	}

	public void setAdmin(boolean admin) {
		
		this.admin = admin;
		
	}

	@Override
	public String toString() {
		return "Nombre: " + this.nombre + "\nEmail: "+ this.mail+  "\nPassword: " + this.pass+ "\nDeparamento: "+ this.departament+ "\nAdmin: "+ this.admin	
		+ "\n===================================================================================";
	}

	
	
}
