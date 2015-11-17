package ar.gov.chris.server.excepciones;


public class ExcepcionNoExiste extends Excepcion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Creates new ExcepcionNoExiste. */
	public ExcepcionNoExiste() {
	}
	
	/** Constructor que toma un mensaje de descripci�n del error.
	 * NOTA: El mensaje es modificado agreg�ndose un sufijo "no existe." al mismo.
	 * @param msg El mensaje de descripci�n del error.
	 */
	public ExcepcionNoExiste(String msg) {
	 super(msg + " no existe.");
	}
	
	/** Constructor que toma un mensaje de descripci�n del error y no le agrega 
	 * ning�n texto como sufijo.
	 * @param mensaje El mensaje de descripci�n del error.
	 * @param nada (Se ignora) Para diferenciarlo del constructor que toma s�lo 
	 * un mensaje.
	 */
	public ExcepcionNoExiste(String mensaje, boolean nada) {
	 super(mensaje);
	}

}
