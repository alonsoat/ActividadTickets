package practica02_03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;

public class Ticket {

	private String estado,fecha_apert,fecha_cerr;
	private int id;
		
	
	private PreparedStatement preparedStatament = null;
	
	
	public Ticket(int id,String estado,String fecha_apert,String fecha_cerr){
		
		this.id=id;
		this.estado=estado;
		this.fecha_apert=fecha_apert;
		this.fecha_cerr=fecha_cerr;
		
	}
	
	
	public int insertar(Connection conexion){
		
		int id_ticket = 0;
		
		try{
			
		String sql = "INSERT INTO tickets (estat,data_obri,data_tanca) VALUES (?,?,?);";
		
		
		preparedStatament = conexion.prepareStatement(sql);
		
		
		preparedStatament.setString(1,"Alberto");
		preparedStatament.setString(2,"pepe");
		preparedStatament.setString(3,"1235");
		
		preparedStatament.executeUpdate();
		
		try (ResultSet cogerId = preparedStatament.getGeneratedKeys()) {

			if (cogerId.next()) {

				id_ticket = cogerId.getInt(1);

			} else {

				System.out.println("No se puede recoger el ultimo Id");

			}

		}
		
		preparedStatament.close();
		
	
		
		
		}catch(SQLException ex){
			
			System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError insertando ticket");
			
		}
		
		return id_ticket;
	
		
		
		
		
	}
	
	
		public void leer(Connection conexion){
			
			try{
				
				String sql = "SELECT * FROM tickets;";
				
				preparedStatament = conexion.prepareStatement(sql);
				ResultSet rs = preparedStatament.executeQuery(sql);
				
				while(rs.next()){
					
				String estado = rs.getString("estat");
				String fechaAbre = rs.getString("data_obri");
				String fechaCierra = rs.getString("data_tanca");
					
				System.out.println("Estado: " + estado + "\nFecha Abertura: "+ fechaAbre+  "\nFecha Cierra: " + fechaCierra);	
				System.out.println("===================================================================================");
			
				}
		

				}catch(SQLException ex){
					
					System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError leyendo el ticket");
					
				}
			
			
			
			}
		
		public void actualizar(Connection conexion,int id){
			
			try{
				
				String sql = "UPDATE tickets SET estat=?,data_tanca==? WHERE id = ?";
				preparedStatament = conexion.prepareStatement(sql);
				
				preparedStatament.setString(1,"Pepe");
				preparedStatament.setString(2,"alberto_a@hotmail.es");
				
				preparedStatament.executeUpdate();
				
				preparedStatament.close();
				
			}catch(SQLException ex){
				
				System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError actualizando el ticket");
			}
			
		}
		
		
		
		public void eliminar(Connection conexion,int id){
			
			try{
				
			String sql = "DELETE FROM tickets WHERE id = ?";
			
			preparedStatament = conexion.prepareStatement(sql);
			
			preparedStatament.setInt(1, id);
		
			preparedStatament.executeUpdate();
			
			preparedStatament.close();
			
			}catch(SQLException ex){
				
				System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError eliminando el ticket");
				
				
			}
		}
		
		
		public String getFecha_apert() {
			return fecha_apert;
		}


		public void setFecha_apert(String fecha_apert) {
			this.fecha_apert = fecha_apert;
		}


		public String getFecha_cerr() {
			return fecha_cerr;
		}


		public void setFecha_cerr(String fecha_cerr) {
			this.fecha_cerr = fecha_cerr;
		}


		public int getId() {
			return id;
		}


		public void setId(int id) {
			this.id = id;
		}


		public String getEstado() {
			return estado;
		}


		public void setEstado(String estado) {
			this.estado = estado;
		}
		
	}
	
	


