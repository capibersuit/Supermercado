package ar.gov.chris.client.gwt.excepciones;

public class GWT_ExcepcionBD extends Exception {

	private static final long serialVersionUID = 4844758132131839893L;

	/** Construye una expeción sin parámetros.
	 */
	public GWT_ExcepcionBD() {
	 super();
	}

	/** Construye una expeción con el mensaje de error parámetro.
	 * 
	 * @param texto El mensaje de error.
	 */
	public GWT_ExcepcionBD(String texto) {
	 super(texto);
	}
	
	/** Construye una expeción en base a otra parámetro.
	 * Toma del parámetro el mensaje y el stack trace.
	 * 
	 * @param ex La excepción parámetro.
	 */
	public GWT_ExcepcionBD(Exception ex) {
	 super(ex.getMessage());
	 this.setStackTrace(ex.getStackTrace());
	}
}

