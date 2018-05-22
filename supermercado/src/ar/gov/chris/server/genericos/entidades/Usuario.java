package ar.gov.chris.server.genericos.entidades;

import ar.gov.mecon.genericos.bd.ConexionBD;
import ar.gov.mecon.genericos.excepciones.ExcepcionBD;
import ar.gov.mecon.genericos.excepciones.ExcepcionNoExiste;
import ar.gov.mecon.genericos.servicios.InstanciadorServicios;

public class Usuario {
	
protected String login= "";
	
	/** Contraseña cifrada mediante SHA1, luego codificada en base64
	 * y prefijada por "{SHA}".
	 * 
	 */
	private String contrasena_cifrada= "";
	
	public Usuario(ConexionBD con, String login)
			throws ExcepcionBD, ExcepcionNoExiste {
		 
		 this.cargar_usuario_por_login(con, login);
		}

	private void cargar_usuario_por_login(ConexionBD con, String login2) {
		// TODO Auto-generated method stub
		
	}

}
