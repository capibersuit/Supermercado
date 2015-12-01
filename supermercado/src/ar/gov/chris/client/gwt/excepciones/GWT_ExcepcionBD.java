package ar.gov.chris.client.gwt.excepciones;

public class GWT_ExcepcionBD extends Exception {

	private static final long serialVersionUID = 4844758132131839893L;

	/** Construye una expeci�n sin par�metros.
	 */
	public GWT_ExcepcionBD() {
	 super();
	}

	/** Construye una expeci�n con el mensaje de error par�metro.
	 * 
	 * @param texto El mensaje de error.
	 */
	public GWT_ExcepcionBD(String texto) {
	 super(texto);
	}
	
	/** Construye una expeci�n en base a otra par�metro.
	 * Toma del par�metro el mensaje y el stack trace.
	 * 
	 * @param ex La excepci�n par�metro.
	 */
	public GWT_ExcepcionBD(Exception ex) {
	 super(ex.getMessage());
	 this.setStackTrace(ex.getStackTrace());
	}
}

