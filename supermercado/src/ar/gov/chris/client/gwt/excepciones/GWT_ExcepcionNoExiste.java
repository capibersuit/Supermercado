package ar.gov.chris.client.gwt.excepciones;

public class GWT_ExcepcionNoExiste extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Construye una expeci�n sin par�metros.
	 */
	public GWT_ExcepcionNoExiste() {
	 super();
	}

	/** Construye una expeci�n en base a otra par�metro.
	 * Toma del par�metro el mensaje y el stack trace.
	 * 
	 * @param ex La excepci�n par�metro.
	 */
	public GWT_ExcepcionNoExiste(Exception ex) {
	 super(ex.getMessage());
	 this.setStackTrace(ex.getStackTrace());
	}

	/** Construye una expeci�n con el mensaje de error par�metro.
	 * 
	 * @param string El mensaje de error.
	 */
	public GWT_ExcepcionNoExiste(String string) {
	 super(string);
	}
}

