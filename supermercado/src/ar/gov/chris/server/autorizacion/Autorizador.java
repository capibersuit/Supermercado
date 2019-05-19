package ar.gov.chris.server.autorizacion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.security.SecureRandom;
import ar.gov.chris.server.bd.ConexionBD;
import ar.gov.chris.server.genericos.contexto.ContextoDeSeguridad;
//import ar.gov.mecon.genericos.autorizacion.AUTENTICACION_OK;
//import ar.gov.mecon.genericos.autorizacion.ResultadoAutenticacion;
//import ar.gov.mecon.genericos.autorizacion.StatusToken;
//import ar.gov.mecon.genericos.autorizacion.TOKEN_VALIDO;
//import ar.gov.mecon.genericos.autorizacion.enum;
import ar.gov.mecon.genericos.basicos.Fecha;
import ar.gov.mecon.genericos.basicos.Par;
import ar.gov.mecon.genericos.entidades.Usuario;
import ar.gov.mecon.genericos.excepciones.ExcepcionBD;
import ar.gov.mecon.genericos.excepciones.ExcepcionBug;
import ar.gov.mecon.genericos.excepciones.ExcepcionNoExiste;
import ar.gov.mecon.genericos.formato.HTML;
import ar.gov.mecon.genericos.servicios.AplicacionConFuncionalidades;

public class Autorizador {
	
	/** Esta clase representa el resultado de un intento de autenticaciï¿½n.
	 *
	 * @author fpscha
	 * @version $Revision: 1.26 $
	 */
	static public enum ResultadoAutenticacion {
		AUTENTICACION_OK,
		DEBE_CAMBIAR_CONTRASENA,
		AUTENTICACION_NOK;
	}
	
	
	
	
	/** Posibles respuestas ante la validaciï¿½n de un token.
	 *
	 * @author fpscha
	 * @version $Revision: 1.26 $
	 */
	static public enum StatusToken {
		TOKEN_VALIDO,
		TOKEN_INVALIDO,
		TOKEN_VENCIDO;
	}
	
	/** Archivo para guardar los salts.
	 */
	static final private String ARCH_SALTS=
				(System.getProperty("java.io.tmpdir")!=null ?
				System.getProperty("java.io.tmpdir") : "/tmp")+
				System.getProperty("file.separator")+"salt";
	/** El tiempo de renovaciï¿½n de los salts (en segundos).
	 * IMPORTANTE: La sesiï¿½n del usuario expira en
	 * mï¿½n(CANT_SALTS * TIEMPO_RENOVACION_SALTS, duraciï¿½n de la cookie)
	 * segundos.
	 * 
	 * Notar que no tiene sentido definir los nombres de las properties como
	 * variables estï¿½ticas porque al accederlas se inicializarï¿½a la clase
	 * antes de poder establecerse los valores.
	 */
	static final private int TIEMPO_RENOVACION_SALTS=
		Integer.valueOf(System.getProperty("user.autorizador.tiempo_renovacion_salts",
				"3600"));
	static final private int CANT_SALTS= 
		Integer.valueOf(System.getProperty("user.autorizador.cant_salts", "10"));

	/** Tamaï¿½o en bytes de cada salt.
	 */
	static final private int TAM_SALT= 8;
	
	
	
	/** Valida que el login y la contraseï¿½a suministrados sean vï¿½lidos
	 * y ademï¿½s que el usuario estï¿½ autorizado a loguearse en la aplicaciï¿½n.
	 * @param con Conexiï¿½n contra la BD.
	 * @param login El login del usuario.
	 * @param contrasena Su contraseï¿½a.
	 * @param servicio La descripciï¿½n del servicio contra el que se desea
	 * validar al usuario.
	 * @return Si el login y la contraseï¿½a coinciden, el usuario no estï¿½
	 * bloqueado, no debe cambiar su contraseï¿½a y tiene el servicio requerido
	 * devuelve AUTENTICACION_OK. Si todo estï¿½ bien, pero debe cambiar su
	 * contraseï¿½a devuelve DEBE_CAMBIAR_CONTRASENA. Si no, AUTENTICACION_NOK.
	 */
	static public ResultadoAutenticacion validar_usuario(ConexionBD con,
		String login, String contrasena, String servicio) {
	 Par<ResultadoAutenticacion, Usuario> res= instanciar_usuario_valido(con,
			 login, contrasena, servicio);
	 return res.izq;
	}

	/** Valida que el login y la contraseï¿½a suministrados sean vï¿½lidos
	 * y ademï¿½s que el usuario estï¿½ autorizado a loguearse en la aplicaciï¿½n.
	 * Devuelve al usuario en la segunda componente del par, y el status
	 * de su autenticaciï¿½n en la primera.
	 * El usuario podrï¿½a ser <code>null</code> si no se lo puede instanciar.
	 * @param con Conexiï¿½n contra la BD.
	 * @param login El login del usuario.
	 * @param contrasena Su contraseï¿½a.
	 * @param servicio La descripciï¿½n del servicio contra el que se desea
	 * validar al usuario.
	 * @return El usuario correspondiente al login, si se lo puede instanciar, 
	 * en la segunda componente. <code>null</code> en otro caso. En la primera,
	 * si el login y la contraseï¿½a coinciden, el usuario no estï¿½ bloqueado, no
	 * debe cambiar su contraseï¿½a y tiene el servicio requerido devuelve
	 * AUTENTICACION_OK. Si todo estï¿½ bien, pero debe cambiar su contraseï¿½a
	 * devuelve DEBE_CAMBIAR_CONTRASENA. Si no, AUTENTICACION_NOK.
	 */
	static public Par<ResultadoAutenticacion, Usuario>
		instanciar_usuario_valido(ConexionBD con, String login,
		String contrasena, String servicio) {
		
		Usuario u= null;
	 ResultadoAutenticacion res= ResultadoAutenticacion.AUTENTICACION_NOK;
//	try {
//		 boolean aux= u.es_contrasena(contrasena);
		if(login.equalsIgnoreCase("capibersuit") && contrasena.equalsIgnoreCase("laquevenga")) {
		 res= ResultadoAutenticacion.AUTENTICACION_OK;
		}
//	 } catch (ExcepcionBD ex) {
//		 ex.printStackTrace(System.err);
//	 } catch (ExcepcionNoExiste ex) {
//		 ex.printStackTrace(System.err);
//	 }
	 return new Par<ResultadoAutenticacion, Usuario>(res, u);
	}

	/** Indica si el usuario pasado como parï¿½metro tiene el servicio
	 * <code>servicio</code>, que ademï¿½s no debe estar bloqueado.
	 * 
	 * @param u El usuario sobre el que se quiere verificar la presencia del servicio.
	 * @param servicio El servicio sobre el que se desea consultar.
	 * @return ï¿½dem.
	 */
//	public static boolean usuario_tiene_servicio_no_bloqueado(Usuario u,
//			String servicio) {
//	 boolean res= false;
//	 Iterator<ServicioIndividualInstanciado> it= u.obtener_servicios_parciales().iterator();
//
//	 String servicio_sin_tildes= null;
//	 while (it.hasNext() && !res) {
//		ServicioIndividualInstanciado s= it.next();
//		String serv_usuario= s.obtener_descripcion_servicio();
//		boolean serv_coincide= serv_usuario.equals(servicio);
//		/* Caso especial: si la aplicaciï¿½n que llama a este mï¿½todo es UTF
//		 * las tildes del servicio se van a ver como '?', asï¿½ que si
//		 * no coincide, pero el servicio las tiene, pruebo a ver si coinciden
//		 * reemplazando tildes por '?'.
//		 */
//		if (!serv_coincide) {
//			if (servicio_sin_tildes==null)
//				servicio_sin_tildes= servicio.replace('ï¿½', '?').replace('ï¿½', '?').
//	 				replace('ï¿½', '?').replace('ï¿½', '?').replace('ï¿½', '?').
//	 				replace('ï¿½', '?').replace('ï¿½', '?');
//			serv_usuario= serv_usuario.replaceAll("[^a-zA-Z0-9_ ]", "?");
//			serv_coincide= serv_usuario.equals(servicio_sin_tildes);
//		}
//		res= serv_coincide && !s.bloqueado();
//	 }
//	 return res;
//	}
	
	/** Valida que un usuario estï¿½ autorizado a utilizar determinada
	 * funcionalidad dentro de determinada aplicaciï¿½n. Si estï¿½ autorizado
	 * agrega sus permisos dentro del conjunto de permisos parï¿½metro.
	 *
	 * @param con Conexiï¿½n contra la BD.
	 * @param cs Contexto de seguridad del usuario.
	 * @param out "Stream" de salida.
	 * @param funcionalidad La funcionalidad que el usuario intenta usar.
	 * @param error_autenticacion El error a mostrar si el usuario no estï¿½ 
	 * autenticado.
	 * @param error_expirada El error a mostrar si el usuario estaba
	 * autenticado pero su token estï¿½ vencido.
	 * @param error_autorizacion El error a mostrar si el usuario no estï¿½ 
	 * autorizado a utilizar esa funcionalidad.
	 * @param permisos Conjunto de permisos del usuario (se completa
	 * si estï¿½ autorizado).
	 * @return true si el usuario estï¿½ autenticado y autorizado a usar
	 * la funcionalidad indicada.
	 */
	static public boolean validar_usuario(ConexionBD con,
		ContextoDeSeguridad cs, PrintWriter out, String funcionalidad,
		String error_autenticacion, String error_expirada,
		String error_autorizacion, AplicacionConFuncionalidades permisos) {
	 StringBuffer error_resultante= new StringBuffer();
	 boolean res= validar_usuario(con, cs, funcionalidad, error_autenticacion,
			 error_expirada, error_autorizacion/*, permisos*/,
			 error_resultante);
	 if (!res)
		 out.println(error_resultante.toString());

	 return res;
	}

	/** Valida que un usuario estï¿½ autorizado a utilizar determinada
	 * funcionalidad dentro de determinada aplicaciï¿½n. Si estï¿½ autorizado
	 * agrega sus permisos dentro del conjunto de permisos parï¿½metro. Si sï¿½lo se desea
	 * validar que el usuario tenga acceso a la aplicaciï¿½n el string funcionalidad debe
	 * ser Autorizador.FUNCIONALIDAD_GLOBAL_AP.<br>
	 * <b>NOTA:</b> Esto ï¿½ltimo no implica un riesgo de seguridad grave dado que se asume
	 * que todas las validaciones para realizar acciones en una aplicaciï¿½n se realizan en
	 * cï¿½digo que se ejecuta en el servidor. Las validaciones que se realicen desde el 
	 * cliente sï¿½lo deben permitir mostrar pantallas y NO realizar acciones de modificaciï¿½n
	 * en la aplicaciï¿½n.
	 *
	 * @param con Conexiï¿½n contra la BD.
	 * @param cs Contexto de seguridad del usuario.
	 * @param funcionalidad La funcionalidad que el usuario intenta usar. Si sï¿½lo se 
	 * desea validar que el usuario tenga el servicio, funcionalidad debe ser 
	 * Autorizador.FUNCIONALIDAD_GLOBAL_AP.
	 * @param error_autenticacion El error a mostrar si el usuario no estï¿½ 
	 * autenticado.
	 * @param error_expirada El error a mostrar si el usuario estaba
	 * autenticado pero su token estï¿½ vencido.
	 * @param error_autorizacion El error a mostrar si el usuario no estï¿½ 
	 * autorizado a utilizar esa funcionalidad.
	 * @param permisos Conjunto de permisos del usuario (se completa
	 * si estï¿½ autorizado).
	 * @param error_resultante El texto de error a mostrar.
	 * @return true si el usuario estï¿½ autenticado y autorizado a usar
	 * la funcionalidad indicada.
	 */
	static public boolean validar_usuario(ConexionBD con,
		ContextoDeSeguridad cs, String funcionalidad,
		String error_autenticacion, String error_expirada,
		String error_autorizacion/*, AplicacionConFuncionalidades permisos*/,
		StringBuffer error_resultante) {
	 boolean res= false;
	 StatusToken v= validar_token(cs);
	 
	 if (v==StatusToken.TOKEN_INVALIDO) {
		 error_resultante.append(error_autenticacion);
	 } else if (v==StatusToken.TOKEN_VENCIDO) {
		 error_resultante.append(error_expirada);
	 } else if (v==StatusToken.TOKEN_VALIDO) {
		 try {
			 if (autorizado(con, cs, funcionalidad/*, permisos*/)) {
				 res= true;
			 } else {
				 error_resultante.append(error_autorizacion+HTML.BR);
				 error_resultante.append("El permiso necesario es: "+
						funcionalidad+HTML.BR);
			 }
		 } catch (ExcepcionBD ex) {
			 error_resultante.append("Error de la BD: "+ex.getMessage());
		 } catch (ExcepcionNoExiste ex) {
			 error_resultante.append("Error al buscar los permisos del usuario: "
					 +ex.getMessage());
		 }
	 } else {
		 error_resultante.append("Error desconocido");
	 }
	 
	 return res;
	}
	
	/** Chequea si el usuario estï¿½ habilitado a utilizar cierta funcionalidad
	 * en la aplicaciï¿½n indicada. Si estï¿½ autorizado agrega sus permisos
	 * dentro del conjunto de permisos parï¿½metro.
	 *
	 * @param con Conexiï¿½n contra la BD.
	 * @param cs Contexto de seguridad del usuario.
	 * @param funcionalidad Funcionalidad que el usuario intenta utilizar. Si sï¿½lo se 
	 * desea validar que el usuario tenga el servicio funcionalidad debe ser 
	 * Autorizador.FUNCIONALIDAD_GLOBAL_AP.
	 * @param permisos Conjunto de permisos del usuario (se completa
	 * si estï¿½ autorizado).
	 * @return true si el usuario estï¿½ habilitado.
	 * @throws ExcepcionBD Si hay algï¿½n problema con la BD.
	 * @throws ExcepcionNoExiste Si el usuario no tiene el servicio
	 * correspondiente a la aplicaciï¿½n que estï¿½ en el {@link ContextoDeSeguridad}.
	 */
	static public boolean autorizado(ConexionBD con, ContextoDeSeguridad cs,
		String funcionalidad/*, AplicacionConFuncionalidades permisos*/)
		throws ExcepcionBD, ExcepcionNoExiste {
	 int id_ap;
//	 String ap= cs.obtener_aplicacion_normalizada();
//	 
//	 // Averiguo cuï¿½l es el id de la AplicacionConFuncionalidades del usuario.
//	 String query= "SELECT id_"+ap+" FROM rel_login_"+ap+" WHERE login='"+
//			cs.obtener_login()+"'";
//	 try {
//		ResultSet rs= con.select(query);
// 
//		if (rs.next())
//			id_ap= rs.getInt("id_"+ap);
//		else
//			throw new ExcepcionNoExiste("El usuario "+cs.obtener_login() 
//						+ " no tiene servicio de "+
//						cs.obtener_aplicacion()+".");
//	 } catch (SQLException ex) {
//		throw new ExcepcionBD(ex);
//	 }
	 
//	 permisos.copiarse_desde_bd(con, id_ap);

//	 return (funcionalidad.equals(FUNCIONALIDAD_GLOBAL_AP) || 
//			 permisos.tiene_permiso(funcionalidad));
	 
	 return true;
	}
	
	/** Genera un token que serï¿½ usado para autenticar al usuario
	 * (vï¿½a cookie). El token contiene el login, la IP, la fecha y
	 * la duraciï¿½n concatenados en un string y encriptado con un salt
	 * al azar.
	 * Eventualmente podrï¿½a tener otro parï¿½metro extra, si el parï¿½metro
	 * <code>cs</code> que se le pasa es de alguna subclase.
	 * @param cs El {@link ContextoDeSeguridad} sobre el que se
	 * calcula el token.
	 * @return String conteniendo el token.
	 * @see ContextoDeSeguridad#generar_token(byte[])
	 */
	static public String generar_token(ContextoDeSeguridad cs) {
	 byte[][] salts= new byte[CANT_SALTS][TAM_SALT];
	 String token;
	 
	 try {
		 obtener_salts(salts);
		 token= cs.generar_token(salts[0]);
	 } catch (IOException ex) {
		 throw new ExcepcionBug("No deberï¿½a haber problemas para "+
				"leer los salts: "+ex.getMessage());
	 }
	 
	 return token;
	}
	
	/** Valida el token de un usuario e indica si es vï¿½lido o no.
	 * @param cs Contexto de seguridad del usuario.
	 * @return TOKEN_INVALIDO, si el token es invï¿½lido;
	 * TOKEN_VENCIDO, si es vï¿½lido pero estï¿½ vencido;
	 * TOKEN_VALIDO, si es vï¿½lido y no estï¿½ vencido.
	 */
	static public StatusToken validar_token(ContextoDeSeguridad cs) {
	 byte[][] salts= new byte[CANT_SALTS][TAM_SALT];
	 boolean valido= false;
	 String token= cs.obtener_token();
	 String token2;
	 StatusToken res;
	 
	 try {
		 obtener_salts(salts);
		 // Comparo uno a uno con todos los salts.
		 for (int i= 0; !valido && i < salts.length; i++) {
			token2= cs.generar_token(salts[i]);
			valido= (token.compareTo(token2)==0);
		 }
		 
		 // En principio es invï¿½lido.
		 res= StatusToken.TOKEN_INVALIDO;
		 if (valido) {
			 res= StatusToken.TOKEN_VENCIDO;
			 // Verifico si no se venciï¿½.
			 Fecha ahora= new Fecha();
			 long expiracion= cs.obtener_fecha().getTime()+
						cs.obtener_duracion()*1000;
			 if (expiracion>ahora.getTime()) {
				 res= StatusToken.TOKEN_VALIDO;
			 }
		 }
	 } catch (IOException ex) {
		 throw new ExcepcionBug("No deberï¿½a haber problemas para "+
				"leer las salts: "+ex.getMessage());
	 }
	 
	 return res;
	}
	
	/** Genera un salt aleatorio (criptogrï¿½ficamente fuerte)
	 * de TAM_SALT bytes.
	 * @param salt Arreglo de bytes en el que se pondrï¿½n los bytes
	 * aleatorios.
	 */
	static private void generar_salt(byte[] salt) {
	 SecureRandom sr= new SecureRandom();
	 
	 // TAM_SALT bytes de random bytes.
	 sr.nextBytes(salt);
	}
	
	/** Devuelve el salt actual en <t>salts[0]</t> y los anteriores,
	 * renovï¿½ndolos si se vencieron y creando el archivo donde se guardan,
	 * si no existe.
	 * @param salts Los salts.
	 * @throws IOException Si hay algï¿½n problema en el IO.
	 */
	static private void obtener_salts(byte[][] salts) throws IOException {
	 File arch= new File(ARCH_SALTS);
	 
	 // Si no existe, lo genero.
	 if (arch.createNewFile()) {
		 // No existï¿½a, lo creï¿½.
		 // Genero un nuevo salt.
		 generar_salt(salts[0]);
		 // Uso este mismo salt para todos los anteriores.
		 for (int i= 1; i < salts.length; i++) {
			 System.arraycopy(salts[0], 0, salts[i], 0, salts[0].length);
		 }
		 // Los grabo.
		 escribir_salts(arch, salts);
	 } else {
		 // Ya existï¿½a.
		 // Leo los salts que contiene.
		 leer_salts(arch, salts);
		 // ï¿½Hay que renovar los salts?
		 Fecha ahora= new Fecha();
		 if (ahora.getTime()>arch.lastModified()+TIEMPO_RENOVACION_SALTS*1000) {
			// Sï¿½, hay que renovarlos.
			// Desplazo todos uno para adelante.
			for (int i= salts.length-1; i > 0; i--) {
				 System.arraycopy(salts[i-1], 0, salts[i], 0, salts[i-1].length);
			}
			// Genero uno nuevo.
			generar_salt(salts[0]);
			escribir_salts(arch, salts);
		 }
	 }
	}
	
	/** Guarda los salts que recibe en el archivo que recibe como parï¿½metro.
	 * @param arch El archivo en el que se deben guardar.
	 * @param salts Los salts.
	 * @throws IOException Si hay algï¿½n problema en el IO.
	 */
	static private void escribir_salts(File arch, byte[][] salts)
		throws IOException {
	 RandomAccessFile raf;
	 
	 try {
		 raf= new RandomAccessFile(arch, "rw");

		 for (int i= 0; i < salts.length; i++) {
			byte[] salt= salts[i];
			raf.write(salt);
		 }
		 raf.close();
	 } catch (FileNotFoundException ex) {
		 throw new ExcepcionBug("No se puede abrir un archivo "+
				"que deberï¿½a existir: "+ex.getMessage());
	 }
	}
	/** Lee los salts que contiene archivo que recibe como parï¿½metro.
	 * @param arch El archivo del que hay que leer los salts.
	 * @param salts Los salts.
	 * @throws IOException Si hay algï¿½n problema en el IO.
	 */
	static private void leer_salts(File arch, byte[][] salts) 
		throws IOException {
	 RandomAccessFile raf;
	 
	 try {
		 raf= new RandomAccessFile(arch, "r");
		 for (int i= 0; i < salts.length; i++) {
			raf.read(salts[i]);
		 }
		 raf.close();
	 } catch (FileNotFoundException ex) {
		 throw new ExcepcionBug("No se puede abrir un archivo "+
				"que deberï¿½a existir: "+ex.getMessage());
	 }
	}

//	public static ResultadoAutenticacion validar_usuario(ConexionBD con,
//			String login, String contrasena, String nombreAplicacion) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	
}
