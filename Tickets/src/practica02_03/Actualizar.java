package practica02_03;

import java.sql.Connection;
import java.sql.SQLException;

public class Actualizar extends Thread{
	
	UIMensajes uim;
	boolean actualizar;
	
	public Actualizar() throws SQLException{
		
	
		
	}
	
	public void run(UIMensajes uim, boolean actualizar, Connection conexion, int id_ticket) throws SQLException, InterruptedException{
		
		while(actualizar == true){
			
			sleep(5000);
			uim.mostrarTabla(conexion, id_ticket);
			
		}
		
			
	}
	
	

}
