package ar.gov.chris.server.excepciones;

/** Excepción en tiempo de ejecución que se produce cuando se intenta
 *  realizar una operación no autorizada.
 * 
 * @author chris
 *
 */
public class ExcepcionNoAutorizado extends java.lang.RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new <code>ExcepcionNoAutorizado</code> without detail message.
	 */
	public ExcepcionNoAutorizado() {
	}
	
	/**
	 * Constructs an <code>ExcepcionNoAutorizado</code> with the specified detail message.
	 * @param msg the detail message.
	 */
	public ExcepcionNoAutorizado(String msg) {
	 super(msg);
	}
}
