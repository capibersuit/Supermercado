package ar.gov.chris.server.proxies_pantallas;

import javax.servlet.http.HttpServletRequest;

import ar.gov.chris.client.datos.DatosAutorizacion;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionBD;
import ar.gov.chris.client.interfaces.ProxyPantallaLoguearse;
import ar.gov.chris.server.autorizacion.Autorizador;
import ar.gov.chris.server.autorizacion.Autorizador.ResultadoAutenticacion;
import ar.gov.chris.server.bd.ConexionBD;
import ar.gov.chris.server.genericos.basicos.IP;
import ar.gov.chris.server.genericos.contexto.ContextoDeSeguridad;
import ar.gov.mecon.componentes.server.annotations.NoAutenticarYAutorizar;
import ar.gov.mecon.genericos.basicos.Fecha;
//import ar.gov.mecon.genericos.basicos.IP;
import ar.gov.mecon.genericos.basicos.Sanitizador;
import ar.gov.mecon.genericos.excepciones.ExcepcionBD;
import ar.gov.mecon.genericos.excepciones.ExcepcionBug;
import ar.gov.mecon.genericos.excepciones.ExcepcionFormatoInvalido;


public class ProxyPantallaLoguearseImpl extends ProxyPantallaImpl 
implements ProxyPantallaLoguearse {

private static final long serialVersionUID = 6951269587941953299L;

/** Duración por default de los tokens de autenticación.
 */
//static final int DURACION= 9*3600;
//static final int DURACION= 5*60;

static final int DURACION= 3*3600;



/** Constructor sin parámetros.
 */
public ProxyPantallaLoguearseImpl() {
 // Este servlet no usa una única conexión contra la BD.
 this.usar_unica_conexion(false);
}

/** Verifica si el usuario puede loguearse a la aplicación.
 * 
 * @param login Login del usuario.
 * @param contrasena Contraseña del usuario.
 * @param ambito Ambito en el que se desea loguear.
 * @return Ídem.
 * @throws GWT_ExcepcionBD Si hay algún problema con la BD.
 */
@NoAutenticarYAutorizar
public DatosAutorizacion validar_usuario(String login, String contrasena) throws GWT_ExcepcionBD {
 DatosAutorizacion res= null;
 ConexionBD con= obtener_transaccion();
 try {
	login= Sanitizador.sanitizar(login);
	contrasena= Sanitizador.sanitizar(contrasena);
	res= validar_usuario(con, login, contrasena);
 } catch (ExcepcionBD ex) {
	System.out.println(ex.getMessage());
	throw new GWT_ExcepcionBD(ex);
 } finally {
	cerrar_transaccion(con, true);
 }
 return res;
}

/** Verifica si el usuario puede loguearse a la aplicación.
 * 
 * @param con Conexión contra la BD.
 * @param login Login del usuario.
 * @param contrasena Contraseña del usuario.
 * @param ambito Ámbito de validación.
 * @return Ídem.
 * @throws ExcepcionBD Si hay algún problema con la BD. 
 */
private DatosAutorizacion validar_usuario(ConexionBD con, String login, String 
		contrasena)
	throws ExcepcionBD {
 Fecha fecha;
 IP ip = null;
 DatosAutorizacion res= new DatosAutorizacion();

 final ResultadoAutenticacion res_autenticacion= 
	 Autorizador.validar_usuario(con, login, contrasena, NOMBRE_APLICACION);
 /*if (res_autenticacion.equals(ResultadoAutenticacion.DEBE_CAMBIAR_CONTRASENA)) {
	 res.setMensaje_error("Debe cambiar su contraseña antes de poder entrar.");
	 return res;
 } else */ if (!res_autenticacion.equals(ResultadoAutenticacion.AUTENTICACION_OK)) {
	 res.setMensaje_error("Login o contraseña inválidos o usuario no " +
		"autorizado para usar la aplicación");
	 return res;
 }

 HttpServletRequest request= this.getThreadLocalRequest();
// 
 try {
     ip= new IP(request.getRemoteAddr());
//	 ip= new IP("localhost");
 } catch (ExcepcionFormatoInvalido ex) {
	 throw new ExcepcionBug(ex.getMessage());
//	 try {
//		ip= new IP("188.9.9.9");
//		ip = new IP();
//	} catch (ExcepcionFormatoInvalido e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
 }

 // Valido su IP.
//A partir de la version 3.6 que ircorpora a todos los usuarios al sistema 
//sacamos esta restriccion.	 
// if (!Autorizador.validar_ip(ip)) {
//	res.setMensaje_error("No está autorizado a usar la aplicación desde" +
//			" la IP "+ip.toString()+".");
//	return res;
// }
 
 // A esta altura ya está validado.
 res.cambiar_autenticado(true);
 res.cambiar_autorizado(true);
 
 // Genero su Contexto de seguridad.
 fecha= new Fecha();
 ContextoDeSeguridad cs= new ContextoDeSeguridad(login, fecha, ip,
		 		NOMBRE_APLICACION, DURACION);
 
 // Genero sus cookies.
 res.setLogin(login);
 res.setDuracion(DURACION);
 res.setFecha(fecha.toString());
 String token= Autorizador.generar_token(cs);
 res.setToken(token);
 fecha.sumar_segundos(DURACION);
 res.setExpiracion_cookie(fecha.getDate());
// res.setAmbito(ambito);

// // Logueo que el usuario se logueó.
// Logueador.registrar(con, cs, "El usuario "+login+
//				" se logueó al sistema de soporte" +
//				" desde "+ip.toString()+".");
  return res;
}
}
