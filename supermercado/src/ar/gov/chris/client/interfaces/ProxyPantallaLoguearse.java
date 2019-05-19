package ar.gov.chris.client.interfaces;

import ar.gov.chris.client.datos.DatosAutorizacion;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionBD;



public interface ProxyPantallaLoguearse extends ProxyPantalla {

	/** Verifica si el usuario puede loguearse a la aplicación.
	 * 
	 * @param login Login del usuario.
	 * @param contrasena Contraseña del usuario.
	 * @param ambito Ambito en el que se quiere validar al usuario.
	 * @return Ídem.
	 * @throws GWT_ExcepcionBD Si hay algún problema con la BD.
	 * @throws GWT_ExcepcionRuntime Si hay alguan excepción runtime.
	 */
	DatosAutorizacion validar_usuario(String login, String contrasena) 
		throws GWT_ExcepcionBD, GWT_ExcepcionBD;	
}

