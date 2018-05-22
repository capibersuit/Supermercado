package ar.gov.chris.client.interfaces;

import ar.gov.chris.client.datos.DatosAutorizacion;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionBD;

import com.google.gwt.user.client.rpc.RemoteService;

public interface ProxyPantalla extends RemoteService {
	/** Procesa la autorización de un usuario para una funcionalidad en particular.
	 * 
	 * @param pantalla Funcionalidad para la que se quiere autorizar.
	 * @return Datos de la autorización.
	 * @throws GWT_ExcepcionBD si hay algún problema con la BD.
	 * @throws GWT_ExcepcionRuntime Si hay alguan excepción runtime.
	 */
	DatosAutorizacion autorizado(String pantalla) throws GWT_ExcepcionBD, GWT_ExcepcionBD;

	/** Devuelve el string de la URL de la aplicación desde la cual se llama al método.
	 * @return Ídem.
	 */
	String obtener_url_app();
}

