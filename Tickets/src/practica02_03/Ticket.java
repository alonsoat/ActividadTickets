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
	
	public Ticket(){
		
	}
	
	public Ticket(int id,String estado,String fecha_apert,String fecha_cerr){
		
		this.id=id;
		this.estado=estado;
		this.fecha_apert=fecha_apert;
		this.fecha_cerr=fecha_cerr;
		
	}
	
	
	public void insertar(Connection conexion){
		
		try{
			
		String sql = "INSERT INTO tickets (estat) VALUES (?);";
		
		
		preparedStatament = conexion.prepareStatement(sql,preparedStatament.RETURN_GENERATED_KEYS);
		
		
		preparedStatament.setString(1,this.estado);
	
		preparedStatament.executeUpdate();
		
		try (ResultSet cogerId = preparedStatament.getGeneratedKeys()) {

			if (cogerId.next()) {

				
				this.id=cogerId.getInt(1);

			} else {

				System.out.println("No se puede recoger el ultimo Id");

			}

		}
		
		preparedStatament.close();
	
	
		
		
		}catch(SQLException ex){
			
			System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError insertando ticket");
			
		}
		
		
		
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
		
				preparedStatament.close();

				}catch(SQLException ex){
					
					System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError leyendo el ticket");
					
				}
			
				
			
			}
		
		public void actualizar(Connection conexion,boolean admin){
			
			try{
				
				String sql = "UPDATE tickets SET estat=? WHERE id = ?";
				preparedStatament = conexion.prepareStatement(sql);
				
			
				preparedStatament.setString(1,this.estado);
				preparedStatament.setInt(2,this.id);
				
				if(this.estado.equals("Tancat") && admin == true ){
					
					/*
					 * Probar las 2 formas de cambiar la fecha. 
					 * Desde codigo en el propio metodo, mezclando 2 conexiones.
					 * Llamando al metodo cambiarFecha , que se le pasa una conexion.
					 * 
					 * Hacer consulta para saber si el usuario es administrador
					 */
					
					String sqlFecha = "UPDATE tickets SET data_tanca=CURRENT_TIMESTAMP WHERE id=?";
					
					PreparedStatement preparedStatament2 = conexion.prepareStatement(sqlFecha);
					
					preparedStatament2.setInt(1, this.id);
					
					preparedStatament2.executeUpdate();
					
					preparedStatament2.close();
					
					//cambiarFecha(conexion);
					
					
				}else if(this.estado.equals("Obert") && admin == true ){
					
					String sqlFecha = "UPDATE tickets SET data_tanca=null AND data_obri=CURRENT_TIMESTAMP WHERE id=?";
					
					PreparedStatement preparedStatament2 = conexion.prepareStatement(sqlFecha);
					
					preparedStatament2.setInt(1, this.id);
					
					preparedStatament2.executeUpdate();
					
					preparedStatament2.close();
					
					
					
				}
				
				preparedStatament.executeUpdate();
				
				preparedStatament.close();
				
			}catch(SQLException ex){
				
				System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError actualizando el ticket");
			}
			
		}
		
		
		
		public void eliminar(Connection conexion){
			
			try{
				
			String sql = "DELETE FROM tickets WHERE id = ?";
			
			preparedStatament = conexion.prepareStatement(sql);
			
			preparedStatament.setInt(1, this.id);
		
			preparedStatament.executeUpdate();
			
			preparedStatament.close();
			
			}catch(SQLException ex){
				
				System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError eliminando el ticket");
				
				
			}
		}
		
		
		public void cambiarFecha(Connection conexion){
			
			try{
			
			String sqlFecha = "UPDATE tickets SET data_tanca=CURRENT_TIMESTAMP";
			preparedStatament = conexion.prepareStatement(sqlFecha);
			
			
			preparedStatament.executeUpdate();
			
			preparedStatament.close();
			
			}catch(SQLException ex){
				
				System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError cambiando la fecha");
				
				
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
	
	


