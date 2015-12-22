package ar.gov.chris.client.gwt.excepciones;

public class GWT_ExcepcionNoExiste extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Construye una expeción sin parámetros.
	 */
	public GWT_ExcepcionNoExiste() {
	 super();
	}

	/** Construye una expeción en base a otra parámetro.
	 * Toma del parámetro el mensaje y el stack trace.
	 * 
	 * @param ex La excepción parámetro.
	 */
	public GWT_ExcepcionNoExiste(Exception ex) {
	 super(ex.getMessage());
	 this.setStackTrace(ex.getStackTrace());
	}

	/** Construye una expeción con el mensaje de error parámetro.
	 * 
	 * @param string El mensaje de error.
	 */
	public GWT_ExcepcionNoExiste(String string) {
	 super(string);
	}
}

