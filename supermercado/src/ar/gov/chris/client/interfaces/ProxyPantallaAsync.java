package ar.gov.chris.client.interfaces;


import ar.gov.chris.client.datos.DatosAutorizacion;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProxyPantallaAsync {
	/** Procesa la autorización de un usuario para una funcionalidad en particular.
	 * 
	 * @param pantalla Funcionalidad para la que se quiere autorizar.
	 * @param callback Datos de la autorización.
	 */
	void autorizado(String pantalla, AsyncCallback<DatosAutorizacion> callback);
	
	/** Devuelve el string de la URL de la aplicación desde la cual se llama al método.
	 * @param callback Ídem.
	 */
	void obtener_url_app(AsyncCallback<String> callback);
}

