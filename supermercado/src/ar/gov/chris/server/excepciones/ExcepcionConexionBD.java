package ar.gov.chris.server.excepciones;

public class ExcepcionConexionBD extends java.lang.RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new <code>ExcepcionConexionBD</code> without detail message.
	 */
	public ExcepcionConexionBD() {
	}
	
	/**
	 * Constructs an <code>ExcepcionConexionBD</code> with the specified detail message.
	 * @param msg the detail message.
	 */
	public ExcepcionConexionBD(String msg) {
	 super(msg);
	}

	/** Constructor.
	 * @param ex Excepci�n a partir de la cual construirse.
	 */
	public ExcepcionConexionBD(Exception ex) {
	 super(ex);
	}
	
	/** Constructor.
	 * @param msj_adicional Un mensaje.
	 * @param ex Excepci�n a partir de la cual construirse.
	 */
	public ExcepcionConexionBD(String msj_adicional, Exception ex) {
	 super(ex.getMessage()+" "+msj_adicional, ex);
	}
}
