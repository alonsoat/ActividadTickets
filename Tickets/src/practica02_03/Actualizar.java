package practica02_03;

import java.sql.Connection;
import java.sql.SQLException;

public class Actualizar extends Thread{
	
	public Actualizar(UIMensajes uim, boolean actualizar, Connection conexion, int id_ticket) throws SQLException{
		
		Actualizar a = new Actualizar(uim, actualizar, conexion, id_ticket);
		a.run(uim, actualizar, conexion, id_ticket);
		
	}
	
	public void run(UIMensajes uim, boolean actualizar, Connection conexion, int id_ticket) throws SQLException{
		
		while(actualizar == true){
			
			uim.mostrarTabla(conexion, id_ticket);
			
		}
		
			
	}
	
	

}
