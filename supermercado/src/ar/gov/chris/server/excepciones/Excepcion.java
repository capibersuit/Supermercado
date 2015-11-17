package ar.gov.chris.server.excepciones;

public class Excepcion extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Constructor.
	 */
	public Excepcion() {
	}

	/** Construye una excepci�n a partir de un mensaje.
	 * @param message El mensaje.
	 */
	public Excepcion(String message) {
		super(message);
	}

	/** Construye una excepci�n a partir de otra.
	 * @param cause Un throwable.
	 */
	public Excepcion(Throwable cause) {
		super(cause);
	}

	/** Construye una excepci�n a partir de un mensaje y un throwable.
	 * @param message El mensaje.
	 * @param cause El Throwable.
	 */
	public Excepcion(String message, Throwable cause) {
		super(message, cause);
	}

}
