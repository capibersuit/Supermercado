package ar.gov.chris.client.interfaces;


import ar.gov.chris.client.datos.DatosAutorizacion;

import com.google.gwt.user.client.rpc.AsyncCallback;

/** Interfaz para implementar el logueo a la aplicación.
 * @author emoscato
 */
public interface ProxyPantallaLoguearseAsync extends ProxyPantallaAsync {
	/** Verifica si el usuario puede loguearse a la aplicación.
	 * 
	 * @param login Login del usuario.
	 * @param contrasena Contraseña del usuario.
	 * @param callback Ídem.
	 */
	void validar_usuario(String login, String contrasena,
			AsyncCallback<DatosAutorizacion> callback);

	void obtener_nombre_maquina_local(AsyncCallback<String> asyncCallback);
}

