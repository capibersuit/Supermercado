package ar.gov.chris.client.gwt.excepciones;

public class GWT_ExcepcionFormatoInvalido extends Exception {

	private static final long serialVersionUID= -7271714617132364757L;

	/** Construye una expecion sin parametros.
	 */
	public GWT_ExcepcionFormatoInvalido() {
	 super();
	}

	/** Construye una expecion con el mensaje de error parametro.
	 * 
	 * @param texto El mensaje de error.
	 */
	public GWT_ExcepcionFormatoInvalido(String texto) {
	 super(texto);
	}
	
	/** Construye una expecion en base a otra parametro.
	 * Toma del parametro el mensaje y el stack trace.
	 * 
	 * @param ex La expecion parametro.
	 */
	public GWT_ExcepcionFormatoInvalido(Exception ex) {
	 super(ex.getMessage());
	 this.setStackTrace(ex.getStackTrace());
	}
}

