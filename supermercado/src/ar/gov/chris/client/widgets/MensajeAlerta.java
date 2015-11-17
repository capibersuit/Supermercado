package ar.gov.chris.client.widgets;

import ar.gov.chris.client.gwt.MensajeDialog;


public class MensajeAlerta {
	private static final String ICON_ERROR = "icon-error.gif";
	private static final String ICON_INFO = "icon-info.gif";
	private static final String ICON_QUESTION = "icon-question.gif";
	private static final String ICON_WARNING = "icon-warning.gif";

	/** Muestra un popup con el mensaje pasado como parámetro y un ícono de 
	 * error.
	 * @param msj Mensaje de error a mostrar, puede contener HTML.
	 */
	public static void mensaje_error(String msj){
		mostrar_mensaje(ICON_ERROR, msj);
	}

	/** Muestra un popup con el mensaje pasado como parámetro y un ícono de 
	 * información.
	 * @param msj Mensaje a mostrar al usuario, puede contener HTML.
	 */
	public static void mensaje_info(String msj){
		mostrar_mensaje(ICON_INFO, msj);
	}
	
	/** Muestra un popup con el mensaje pasado como parámetro y un ícono de 
	 * alerta.
	 * @param msj Warning a mostrar al usuario, puede contener HTML.
	 */
	public static void mensaje_warning(String msj){
		mostrar_mensaje(ICON_WARNING, msj);
	}
	
	/** Muestra un popup con el mensaje pasado como parámetro y un ícono de 
	 * interrogación.
	 * @param msj Pregunta a mostrar al usuario, puede contener HTML.
	 */
	public static void mensaje_question(String msj){
		mostrar_mensaje(ICON_QUESTION, msj);
	}
	
	/**  Muestra un mensaje generico.
	 * @param icono El icono del mensaje.
	 * @param msj El texto del mensaje.
	 */
	private static void mostrar_mensaje(String icono, String msj) {
		MensajeDialog out= new MensajeDialog(icono, msj, true);
		out.show();
	}

}
