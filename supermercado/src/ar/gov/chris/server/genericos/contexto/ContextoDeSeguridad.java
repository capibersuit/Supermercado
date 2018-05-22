package ar.gov.chris.server.genericos.contexto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import ar.gov.chris.server.excepciones.ExcepcionNoAutorizado;
import ar.gov.mecon.genericos.basicos.Fecha;
import ar.gov.mecon.genericos.basicos.IP;
import ar.gov.mecon.genericos.basicos.Sanitizador;
import ar.gov.mecon.genericos.encripcion.Crypto;
import ar.gov.mecon.genericos.excepciones.ExcepcionFormatoInvalido;

import ar.gov.mecon.genericos.sistema.ManejadorDeCookies;

//import gnu.crypto;

public class ContextoDeSeguridad {/** Cookies usadas para autenticar.
 * 
 */
public static final String COOKIE_TOKEN= "token";
public static final String COOKIE_DURACION= "duracion";
public static final String COOKIE_USR= "usr";
public static final String COOKIE_FECHA= "fecha";

protected String aplicacion;
protected String login;
protected Fecha fecha;
protected IP ip;
protected int duracion;
protected String token;

/** Constructor sin par�metros.
 */
public ContextoDeSeguridad() {
}

/** Constructor por copia.
 * @param cs El contexto de seguridad del cual copiarse.
 */
public ContextoDeSeguridad(ContextoDeSeguridad cs) {
 aplicacion= (cs.aplicacion != null ? new String(cs.aplicacion) : null);
 login=      (cs.login != null      ? new String(cs.login)      : null);
 fecha=      (cs.fecha != null      ? new Fecha(cs.fecha)       : null);
 ip=         (cs.ip != null         ? new IP(cs.ip)             : null);
 token=      (cs.token != null      ? new String(cs.token)      : null);
 duracion=   cs.duracion;
}

/** Crea un contexto de seguridad a partir de un pedido a un servlet.
 * @param pedido El pedido que recibe el servlet.
 * @param aplicacion La aplicaci�n que representa al servlet.
 */
public ContextoDeSeguridad(HttpServletRequest pedido,
	String aplicacion) throws ExcepcionNoAutorizado{
 Cookie[] cookies;
 String aux;
 	 
 cookies= pedido.getCookies();

 try {
	this.ip= new IP(pedido.getRemoteAddr());
 } catch (ExcepcionFormatoInvalido ex) {
	 throw new ExcepcionNoAutorizado(ex.getMessage());
 }
 
 aux= ManejadorDeCookies.buscar_cookie(cookies, COOKIE_FECHA);
 if (aux==null)
	 throw new ExcepcionNoAutorizado("Cookie ausente ("+COOKIE_FECHA+")");
 try {
	aux= aux.replace("%2F", "/");
	aux= aux.replace("%20", " ");
	aux= aux.replace("%3A", ":");
	this.fecha= new Fecha(aux);
 } catch (java.text.ParseException ex) {
	 throw new ExcepcionNoAutorizado(ex.getMessage());
 }
 
 aux= ManejadorDeCookies.buscar_cookie(cookies, COOKIE_USR);
 if (aux==null)
	 throw new ExcepcionNoAutorizado("Cookie ausente ("+COOKIE_USR+")");
 this.login= Sanitizador.sanitizar(aux);
 
 aux= ManejadorDeCookies.buscar_cookie(cookies, COOKIE_DURACION);
 if (aux==null)
	 throw new ExcepcionNoAutorizado("Cookie ausente ("+COOKIE_DURACION+")");
 this.duracion= Integer.parseInt(aux);
 
 aux= ManejadorDeCookies.buscar_cookie(cookies, COOKIE_TOKEN);
 if (aux==null)
	 throw new ExcepcionNoAutorizado("Cookie ausente ("+COOKIE_TOKEN+")");
 this.token= aux;
 
 this.aplicacion= aplicacion;
}

/** Devuelve el login del usuario.
 * @return �dem.
 */
public String obtener_login() {
 return this.login;
}

/** Devuelve la fecha del login.
 * @return �dem.
 */
public Fecha obtener_fecha() {
 return this.fecha;
}

/** Devuelve la IP del usuario.
 * @return �dem.
 */
public IP obtener_IP() {
 return this.ip;
}

/** Devuelve la duraci�n de la sesi�n.
 * @return �dem.
 */
public int obtener_duracion() {
 return this.duracion;
}

/** Devuelve el nombre de la aplicaci�n.
 * @return �dem.
 */
public String obtener_aplicacion() {
 return this.aplicacion;
}

/** Devuelve el nombre de la aplicaci�n de manera normalizada
 * (en min�scula, sin espacios ni acentos).
 * @return �dem.
 */
//public String obtener_aplicacion_normalizada() {
// String res= this.aplicacion.replace(' ', '_');
// res= res.toLowerCase();
// res= res.replace('�', 'a');
// res= res.replace('�', 'e');
// res= res.replace('�', 'i');
// res= res.replace('�', 'o');
// res= res.replace('�', 'u');
// res= res.replace('�', 'n');
// return res;
//}

/** Devuelve el token del usuario.
 * @return �dem.
 */
public String obtener_token() {
 return this.token;
}

/** Este m�todo cambia el token, pero su uso es �nicamente para testing.
 * 
 * @param token El nuevo token.
 */
public void cambiar_token(String token) {
 this.token= token;
}

/** Crea un nuevo Contexto de Seguridad a partir de sus par�metros.
 * @param login Login del usuario.
 * @param fecha Fecha en la que se logue�.
 * @param ip IP desde la que se conect� el usuario.
 * @param duracion Duraci�n de la sesi�n.
 * @param aplicacion Nombre de la aplicaci�n a la cual se logue� el
 * usuario.
 */
public ContextoDeSeguridad(String login, Fecha fecha, IP ip,
	String aplicacion, int duracion) {
 this.ip= ip;
 this.login= login;
 this.fecha= fecha;
 this.duracion= duracion;
 this.aplicacion= aplicacion;
}

/** Genera un token que ser� usado para autenticar al usuario
 * (v�a cookie) a partir del salt par�metro.
 * @param salt El salt a utilizar.
 * @return String conteniendo el token.
 */	
public String generar_token(byte[] salt) {
 return generar_token(salt, this.login, this.ip.toString(),
		 this.fecha.toString(), Integer.toString(this.duracion));
}

/** Genera un token de autenticaci�n en base a los strings pasados
 * como par�metro.
 * 
 * @param salt El salt a utilizar.
 * @param strings Los strings sobre los que se calcula el token.
 * @return String conteniendo el token;
 */
protected String generar_token(byte[] salt, String ... strings) {
 StringBuffer str= new StringBuffer();
 str.append(gnu.crypto.util.Util.toString(salt));
 for (int i= 0; i < strings.length; i++) {
	str.append('-');
	str.append(strings[i]);
 }
 String token= Crypto.md5_hex(str.toString());
 return token;
}

}
