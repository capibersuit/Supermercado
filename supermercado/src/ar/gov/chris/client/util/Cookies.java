package ar.gov.chris.client.util;
import java.util.Date;

import ar.gov.chris.client.datos.DatosAutorizacion;

/** Esta clase provee métodos estáticos para el manejo de Cookies.
*
* @author fpscha
* @version $Revision: 1.3 $
*/
public class Cookies {
	
	/** Pone las cookies relacionadas con la autenticación.
	 * Colateralmente devuelve cuánto dura la misma.
	 * 
	 * @param respuesta_servidor La respuesta de autenticación enviada por el
	 * servidor.
	 * @return La duración, en segundos, del token de autenticación.
	 */
	public static int poner_cookies_autorizacion(DatosAutorizacion respuesta_servidor) {
	 int duracion= respuesta_servidor.getDuracion();
	 Date expiracion= respuesta_servidor.getExpiracion_cookie();
	 setCookie("usr", respuesta_servidor.getLogin(), expiracion);
	 setCookie("fecha", respuesta_servidor.getFecha(), expiracion);
	 setCookie("token", respuesta_servidor.getToken(), expiracion);
	 setCookie("duracion", Integer.toString(duracion),
				 expiracion);
	 
	 return duracion;
	}
	
	/** Limpia los cookies de autenticación, más los que se le pasen como
	 * parámetro.
	 *
	 * @param cookies_extra Los cookies extra a limpiar.
	 */
	public static void limpiar_cookies(String[] cookies_extra) {
	 Date fecha= new Date();
	 setCookie("usr", null, fecha);
	 setCookie("fecha", null, fecha);
	 setCookie("token", null, fecha);
	 setCookie("duracion", null, fecha);
	 if (cookies_extra!=null) {
		 for (int i= 0; i < cookies_extra.length; i++) {
			setCookie(cookies_extra[i], null, fecha);
		}
	 }
	}
	
	/** Wrapper para setCookie, para escribir menos.
	 *
	 * @param nombre El nombre de la cookie.
	 * @param val El valor de la cookie.
	 * @param fecha_expiracion La fecha de expiración.
	 * @see com.google.gwt.user.client.Cookies#setCookie()
	 */
	private static void setCookie(String nombre, String val,
			Date fecha_expiracion) {
	 com.google.gwt.user.client.Cookies.setCookie(nombre, val, fecha_expiracion);
	}
}
