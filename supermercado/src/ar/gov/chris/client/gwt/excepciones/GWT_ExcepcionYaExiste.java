package ar.gov.chris.client.gwt.excepciones;

public class GWT_ExcepcionYaExiste extends Exception {

	private static final long serialVersionUID = -1585823419621923337L;

	/** Construye una expeción sin parámetros.
	 */
	public GWT_ExcepcionYaExiste() {
	 super();
	}

	/** Construye una expeción con el mensaje de error parámetro.
	 * 
	 * @param texto El mensaje de error.
	 */
	public GWT_ExcepcionYaExiste(String texto) {
	 super(texto);
	}
	
	/** Construye una expeción en base a otra parámetro.
	 * Toma del parámetro el mensaje y el stack trace.
	 * 
	 * @param ex La excepción parámetro.
	 */
	public GWT_ExcepcionYaExiste(Exception ex) {
	 super(ex.getMessage());
	 this.setStackTrace(ex.getStackTrace());
	}
}
