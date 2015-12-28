package ar.gov.chris.server.excepciones;

/**
 * Excepción para indicar que algo ya existe.
 * @author STUART
 *
 */
public class ExcepcionYaExiste extends Excepcion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Creates new ExcepcionYaExiste. */
	public ExcepcionYaExiste() {
	}
	
	/** Construye una excepción con el mensaje indicado, y le sufija "ya existe".
	 * @param msg El mensaje
	 */
	public ExcepcionYaExiste(String msg) {
	 super(msg+" ya existe.");
	}
	
	/** Construye una {@link ExcepcionYaExiste}, donde el sufijo
	 * es opcional.
	 * 
	 * @param msg El mensaje.
	 * @param incluir_sufijo Indica si incluir o no el sufijo.
	 */
	public ExcepcionYaExiste(String msg, boolean incluir_sufijo) {
	 super(msg+(incluir_sufijo ? " ya existe." : ""));
	}
}