package ar.gov.chris.server.excepciones;


public class ExcepcionBug extends java.lang.RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new <code>ExcepcionBug</code> without detail message.
	 */
	public ExcepcionBug() {
	}
	
	/**
	 * Constructs an <code>ExcepcionBug</code> with the specified detail message.
	 * @param msg the detail message.
	 */
	public ExcepcionBug(String msg) {
	 super("BUG en el programa: " + msg);
	}
	
	/** Construye una {@link ExcepcionBug} donde el prefijo es optativo.
	 * 
	 * @param msg El mensaje de la excepci�n.
	 * @param incluir_prefijo Indica si incluir o no el prefijo.
	 */
	public ExcepcionBug(String msg, boolean incluir_prefijo) {
	 super((incluir_prefijo ? "BUG en el programa: " : "") + msg);
	}
}