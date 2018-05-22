package ar.gov.chris.client.datos;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.Date;

/** Indica si el usuario fue o no autorizado. En caso positivo, contiene los datos
* necesarios para crear una cookie (de browser).
*
* @author Chris
*/
public class DatosAutorizacion implements IsSerializable {
	/** Indica si el usuario está autenticado en la aplicación */
	private boolean autenticado= false;
	/** Indica si el usuario tiene los permisos necesarios para la funcionalidad */
	private boolean autorizado= false;
	private String mensaje_error= null;
	private int duracion;
	private String login;
	private String fecha;
	private String token;
	private Date expiracion_cookie= null;
	private boolean requiere_cambiar_contrasena= false;
	
	/** Constante que sirve cuando se desea pasar un permiso que tengan todos
	 * los usuarios de todas las aplicaciones.
	 */
	final public static String FUNCIONALIDAD_GLOBAL_AP= "funcionalidad_global_ap";

	/**
	 * @return Returns the duracion.
	 */
	public int getDuracion() {
	 return duracion;
	}

	/**
	 * @param duracion The duracion to set.
	 */
	public void setDuracion(int duracion) {
	 this.duracion= duracion;
	}

	/**
	 * @return Returns the fecha.
	 */
	public String getFecha() {
	 return fecha;
	}

	/**
	 * @param fecha The fecha to set.
	 */
	public void setFecha(String fecha) {
	 this.fecha = fecha;
	}

	/**
	 * @return Returns the login.
	 */
	public String getLogin() {
	 return login;
	}

	/**
	 * @param login The login to set.
	 */
	public void setLogin(String login) {
	 this.login = login;
	}

	/**
	 * @return Returns the token.
	 */
	public String getToken() {
	 return token;
	}

	/**
	 * @param token The token to set.
	 */
	public void setToken(String token) {
	 this.token = token;
	}

	/** Responde si el usuario está autenticado en la aplicación.
	 * @return Returns Ídem.
	 */
	public boolean esta_autenticado() {
	 return autenticado;
	}

	/** Cambia el estado de autenticación.
	 * @param autenticado Indica si el usuario está autenticado en la aplicación.
	 */
	public void cambiar_autenticado(boolean autenticado) {
	 this.autenticado = autenticado;
	}
	
	/** Responde si el usuario está autorizado para la funcionalidad.
	 * @return Returns Ídem.
	 */
	public boolean esta_autorizado() {
	 return autorizado;
	}

	/** Cambia el estado de autorización.
	 * @param autorizado Indica si el usuario está autorizado para dicha funcionalidad.
	 */
	public void cambiar_autorizado(boolean autorizado) {
	 this.autorizado = autorizado;
	}
	
	/**
	 * @return El requiere_cambiar_contrasena.
	 */
	public boolean requiere_cambiar_contrasena() {
	 return this.requiere_cambiar_contrasena;
	}

	/**
	 * @param requiere_cambiar_contrasena El requiere_cambiar_contrasena a establecer.
	 */
	public void requiere_cambiar_contrasena(boolean requiere_cambiar_contrasena) {
	 this.requiere_cambiar_contrasena= requiere_cambiar_contrasena;
	}
	
	/** Corrobora que el usuario esté autenticado en la aplicación,
	 * esté autorizado a con el permiso necesario y no se le haya expirado la sesión.
	 * @return True si el usuario está autenticado Y autorizado. False en caso contrario.
	 */
	public boolean autenticado_y_autorizado() {
	 return autenticado && autorizado;
	}
	
	/**
	 * @return Returns the mensaje_error.
	 */
	public String getMensaje_error() {
	 return mensaje_error;
	}

	/**
	 * @param mensaje_error The mensaje_error to set.
	 */
	public void setMensaje_error(String mensaje_error) {
	 this.mensaje_error = mensaje_error;
	}

	

	

	
	/**
	 * @return Returns the expiracion_cookie.
	 */
	public Date getExpiracion_cookie() {
	 return expiracion_cookie;
	}

	/**
	 * @param expiracion_cookie The expiracion_cookie to set.
	 */
	public void setExpiracion_cookie(Date expiracion_cookie) {
	 this.expiracion_cookie = expiracion_cookie;
	}

}
