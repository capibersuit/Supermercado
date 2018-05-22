package ar.gov.chris.client.gwt.excepciones;


public class GWT_ExcepcionNoAutorizado extends Exception {

	private static final long serialVersionUID = 6476837305625258391L;
	
	/** Indica si el usuario está autenticado en la aplicación. */
	private boolean autenticado;
	/** Indica si el usuario tiene los permisos necesarios para la funcionalidad. */
	private boolean autorizado;
	
	/**
	 * Creates new {@link GWT_ExcepcionNoAutorizado} without detail message.
	 * @param autenticado Indica si el usuario está autenticado.
	 * @param autorizado Indica si el usuario está autorizado para el uso de la 
	 * aplicación.
	 */
	public GWT_ExcepcionNoAutorizado(boolean autenticado, boolean autorizado) {
	 super();
	 this.autenticado= autenticado;
	 this.autorizado= autorizado;
	}
	
	/**
	 * Constructs an {@link GWT_ExcepcionNoAutorizado} with the specified detail message.
	 * @param msg the detail message.
	 * @param autenticado Indica si el usuario está autenticado.
	 * @param autorizado Indica si el usuario está autorizado para el uso de la 
	 * aplicación.
	 */
	public GWT_ExcepcionNoAutorizado(String msg, boolean autenticado, boolean autorizado) {
	 super(msg);
	 this.autenticado= autenticado;
	 this.autorizado= autorizado;
	}

	/**
	 * Construye una {@link GWT_ExcepcionNoAutorizado} a partir del stack 
	 * y del mensaje de otra excepción.
	 * @param ex La excepción de la cual tomar el mensaje y el stack.
	 * @param autenticado Indica si el usuario está autenticado.
	 * @param autorizado Indica si el usuario está autorizado para el uso de la 
	 * aplicación.
	 */
	public GWT_ExcepcionNoAutorizado(Exception ex, boolean autenticado, boolean autorizado) {
	 this(ex.getMessage(), false, false);
	 this.setStackTrace(ex.getStackTrace());
	}
	
	/**
	 * Construye una {@link GWT_ExcepcionNoAutorizado} a partir del stack 
	 * y del mensaje de otra excepción.
	 * @param ex La excepción de la cual tomar el mensaje y el stack.
	 * @see GWT_ExcepcionNoAutorizado#GWT_ExcepcionNoAutorizado(String, boolean, boolean)
	 */
	@Deprecated
	public GWT_ExcepcionNoAutorizado(Exception ex) {
	 this(ex,false, false);
	}
	
	/**
	 * Creates new {@link GWT_ExcepcionNoAutorizado} without detail message.
	 * @see GWT_ExcepcionNoAutorizado#GWT_ExcepcionNoAutorizado(boolean, boolean)
	 */
	@Deprecated
	public GWT_ExcepcionNoAutorizado() {
	 this(false, false);
	}
	
	/**
	 * Constructs an {@link GWT_ExcepcionNoAutorizado} with the specified detail message.
	 * @param msg the detail message.
	 * @see GWT_ExcepcionNoAutorizado#GWT_ExcepcionNoAutorizado(String, boolean, boolean)
	 */
	@Deprecated
	public GWT_ExcepcionNoAutorizado(String msg) {
	 this(msg, false, false);
	}
	
	/** Responde si el usuario está autenticado en la aplicación.
	 * @return Returns Ídem.
	 */
	public boolean estaAutenticado() {
	 return autenticado;
	}

	/** Responde si el usuario está autorizado para la funcionalidad.
	 * @return Returns Ídem.
	 */
	public boolean estaAutorizado() {
	 return autorizado;
	}
}
