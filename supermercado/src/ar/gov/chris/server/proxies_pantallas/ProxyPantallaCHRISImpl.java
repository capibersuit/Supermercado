package ar.gov.chris.server.proxies_pantallas;

import ar.gov.chris.client.datos.DatosAutorizacion;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionBD;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionNoAutorizado;
import ar.gov.chris.server.autorizacion.Autorizador;
import ar.gov.chris.server.bd.ConexionBD.*;
import ar.gov.chris.server.excepciones.ExcepcionBD;
import ar.gov.chris.server.excepciones.ExcepcionBug;
import ar.gov.chris.server.excepciones.ExcepcionNoAutorizado;
import ar.gov.chris.server.genericos.contexto.ContextoDeSeguridad;
import ar.gov.chris.server.bd.ConexionBD;
import ar.gov.chris.server.bd.PoolDeConexiones;
import ar.gov.chris.shared.Sanitizador;
import ar.gov.mecon.componentes.server.clases.ContextoDeSeguridadCA;
import ar.gov.mecon.componentes.server.seguridad.AutorizadorPorAmbito;
//import ar.gov.mecon.genericos.autorizacion.Autorizador;
//import ar.gov.mecon.genericos.contexto.ContextoDeSeguridad;
import ar.gov.mecon.genericos.servicios.AplicacionConFuncionalidades;
import ar.gov.mecon.genericos.util.Introspeccion;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ProxyPantallaCHRISImpl extends RemoteServiceServlet {
	
	/**
	 * 
	 */
	
	
	//************************
	
	private static String NOMBRE_APLICACION;
	  protected static String ERR_EST_AUTENTICACION = "No está autenticado, loguéese.";
	  
	  protected static String ERR_EST_EXPIRADA = "Autenticación expirada. Loguéese nuevamente.";
	  

	  protected static String ERR_EST_AUTORIZACION = "Ud. no está autorizado para realizar esta función.";
	
	
	//****************************
	private static final long serialVersionUID = 1L;
	protected ConexionBD con_unica= null;
	/**
	 * Indica si todo el servlet va a usar una única conexión.
	 * En ese caso, ésa se inicializa y termina en los init() y finalize() del servlet,
	 * automágicamente.
	 * 
	 * Si se utiliza esa única conexión, es responsabilidad del servlet que hereda de éste
	 * manejar la concurrencia.
	 * 
	 * Además, en ese caso, el código del servlet tiene la siguiente pinta:
	 * <code>
	 * Coso devolver_coso(int id) {
	 *  return new Coso(con_unica, id);
	 * }
	 * </code>
	 * 
	 * Si no, se parece a:
	 * <code>
	 * Coso devolver_coso(int id) {
	 *  Coso res= null;
	 *  boolean commit= false;
	 *  try {
	 *   ConexionBD con= obtener_transaccion();
	 *   res= new Coso(con, id);
	 *   commit= true;
	 *  } finally {
	 *   cerrar_transaccion(con, commit);
	 *  }
	 *  return res;
	 * }
	 * </code>
	 */
	protected boolean unica_conexion= true;
	

	/**
	 * Incializa el servlet.
	 * 
	 * @param config {@link ServletConfig}
	 * @param conectarse_a_stock Indica si es necesario conectarse a la base de Stock.
	 * @param cant_con_iniciales Cantidad de conexiones iniciales a la BD.
	 * @param cant_max_con Cantidad máxima de conexiones a la BD.
	 * @param cerrar_con_sobrantes Indica si se deben cerrar las conexiones sobrantes a la
	 * BD.
	 * @param nombre_aplicacion Nombre de la aplicación.
	 * @param err_est_autenticacion Error estándar de autenticación (si es
	 * <code>null</code> se usa el que provee esta clase). 
	 * @param err_est_autorizacion Error estándar de autorización (si es <code>null</code>
	 * se usa el que provee esta clase). 
	 * @param err_est_expirada Error estándar de contraseña expirada (si es
	 * <code>null</code> se usa el que provee esta clase).
	 * @param nombre_clase_ap_con_funcionalidad Nombre completo de la clase de la
	 * aplicación con funcionalidad necesaria para verificar los permisos de la
	 * aplicación.
	 * @param cant_con_iniciales_solo_lectura Indica la cantidad de conexiones iniciales a
	 * la BD de sólo lectura. Si es 0 significa que no se utilizará la BD de sólo lectura.
	 * Si se usa, los parámetros de conexión deben figurar en los archivos
	 * {@code web.xml}.
	 * @param cant_max_con_solo_lectura Indica la cantidad máxima de conexiones a la BD de
	 * sólo lectura.
	 * @throws ServletException Si hay algún problema en la inicialización.
	 * @throws ClassNotFoundException Si la clase pasada como parámetro no existe.
	 * @see PoolDeConexiones
	 */
	public void init(ServletConfig config, boolean conectarse_a_stock,
			int cant_con_iniciales, int cant_max_con, boolean cerrar_con_sobrantes,
			int cant_con_iniciales_solo_lectura,
			int cant_max_con_solo_lectura) throws ServletException,
			ClassNotFoundException {
	 super.init(config);
//	 LectorConfiguracion.inicializar(config);
//	 
//	 NOMBRE_APLICACION= nombre_aplicacion;
//	 if (err_est_autenticacion!=null)
//		 ERR_EST_AUTENTICACION= err_est_autenticacion;
//	 
//	 if (err_est_autorizacion!=null)
//		 ERR_EST_AUTORIZACION= err_est_autorizacion;
//	 
//	 if (err_est_expirada!=null)
//		 ERR_EST_EXPIRADA= err_est_expirada;
//	 
//	 // Instancio la clase apropiada.
//	 clase_ap_con_funcionalidad= (Class<AplicacionConFuncionalidades>) Class.forName(nombre_clase_ap_con_funcionalidad);
//	 
	 // Me conecto a Stock si hace falta.
//	 if (conectarse_a_stock) {
//		 String host_stock= LectorConfiguracion.obtener_valor("host_stock");
//		 String bd_stock= LectorConfiguracion.obtener_valor("bd_stock");
//		 String usr_stock= LectorConfiguracion.obtener_valor("usr_stock");
//		 String contr_stock= LectorConfiguracion.obtener_valor("contr_stock");
//	 
//		 if (host_stock==null || bd_stock==null || usr_stock==null || contr_stock==null)
//			 throw new ServletException("No están definidos todos los parámetros para " +
//					 "conectarse a Stock ({host,bd,usr,contr}_stock).");
//	 
//		 PoolDeConexiones.establecer_parametros_dblink(host_stock, bd_stock, usr_stock,
//				 contr_stock);
//	 }

	 // Configuramos los parámetros del pool de conexiones a la BD.
	 PoolDeConexiones.establecer_cant_conexiones_iniciales(cant_con_iniciales);
	 PoolDeConexiones.establecer_cant_maxima_conexiones(cant_max_con);
	 PoolDeConexiones.cerrar_conexiones_sobrantes(cerrar_con_sobrantes);
	 
	 /* Si es necesario usar la BD de sólo lectura configuramos los parámetros con los que
	  * la vamos a utilizar. */
	 boolean usar_bd_solo_lectura= cant_con_iniciales_solo_lectura>0;
//	 if (usar_bd_solo_lectura) {
//		 PoolDeConexiones.establecer_cant_conexiones_iniciales_solo_lectura(cant_con_iniciales_solo_lectura);
//		 PoolDeConexiones.establecer_cant_maxima_conexiones_solo_lectura(cant_max_con_solo_lectura);
//		 PoolDeConexiones.cerrar_conexiones_sobrantes(cerrar_con_sobrantes);
//	 }
	
	 // Inicializamos las conexiones con la BD.
	 try {
		 PoolDeConexiones.prearmar_conexiones(usar_bd_solo_lectura);
		 if (unica_conexion)
			 con_unica= PoolDeConexiones.obtener_conexion();
		 
	 } catch (ExcepcionBD ex) {
		 System.out.println(ex.getMessage());
		 throw new ServletException(ex);
	 }
	}

	/**
	 * Obtiene del pool una conexión contra la BD de lectoescritura.
	 * 
	 * @return Una conexión contra la BD.
	 * @throws GWT_ExcepcionBD Si hay algún problema con la BD o el pool.
	 */
	protected ConexionBD obtener_conexion() throws GWT_ExcepcionBD {
	 return obtener_conexion(TipoConexion.LECTOESCRITURA);
	}
	
	/**
	 * Obtiene del pool una conexión contra la BD del tipo especificado.
	 * 
	 * @param tipo El tipo de conexión deseada.
	 * @return Una conexión contra la BD.
	 * @throws GWT_ExcepcionBD Si hay algún problema con la BD o el pool.
	 */
	protected ConexionBD obtener_conexion(TipoConexion tipo) throws GWT_ExcepcionBD {
	 if (this.unica_conexion)
		 throw new RuntimeException("Si se usa una única conexión a la BD no se puede " +
		 		"pedir otra más al pool.");
	 ConexionBD con= null;
	 // Obtenemos una conexión contra la BD.
	 try {
		 con= PoolDeConexiones.obtener_conexion(tipo);
	 } catch (ExcepcionBD ex) {
		 throw new GWT_ExcepcionBD(ex);
	 }
	 
	 return con;
	}
	
	/**
	 * Obtiene del pool una conexión contra la BD de lectoescritura e inicia una
	 * transacción sobre ella.
	 * 
	 * @return Una conexión contra la BD.
	 * @throws GWT_ExcepcionBD Si hay algún problema con la BD o el pool.
	 */
	protected ConexionBD obtener_transaccion() throws GWT_ExcepcionBD {
	 return obtener_transaccion(TipoConexion.LECTOESCRITURA);
	}
	
	/**
	 * Obtiene del pool una conexión contra la BD del tipo especificado, e inicia una
	 * transacción sobre ella.
	 * 
	 * @param tipo El tipo de conexión deseado.
	 * @return Una conexión contra la BD.
	 * @throws GWT_ExcepcionBD Si hay algún problema con la BD o el pool.
	 */
	protected ConexionBD obtener_transaccion(TipoConexion tipo) throws GWT_ExcepcionBD {
	 ConexionBD con= null;
	 // Inicializamos la conexión con la BD.
	 try {
		 con= PoolDeConexiones.obtener_conexion(tipo);
		 con.begin_transaction();
	 } catch (ExcepcionBD ex) {
		 throw new GWT_ExcepcionBD(ex);
	 }

	 return con;
	}

	/**
	 * Devuelve una conexión contra la BD al pool.
	 * 
	 * @param con Conexión contra la BD a devolver.
	 * @throws GWT_ExcepcionBD Si hay algún problema con la BD o el pool.
	 */
	protected void devolver_conexion(ConexionBD con) throws GWT_ExcepcionBD {
	 try {
		 PoolDeConexiones.devolver_conexion(con);
	 } catch (ExcepcionBD ex) {
		 throw new GWT_ExcepcionBD(ex.getMessage());
	 }
	}

	/**
	 * Devuelve una conexión contra la BD al pool, previo cerrar la transacción, haciendo
	 * commit o rollback, dependiendo del parámetro <code>commit</code>.
	 * 
	 * @param con Conexión contra la BD a devolver.
	 * @param commit Indica si hay que hacer commit (si es true) o rollback (si es false).
	 * @throws GWT_ExcepcionBD Si hay algún problema con la BD o el pool.
	 */
	protected void cerrar_transaccion(ConexionBD con, boolean commit) throws
			GWT_ExcepcionBD {
	 try {
		 if (commit)
			 con.commit();
		 else
			 con.rollback();
		 
		 PoolDeConexiones.devolver_conexion(con);
	 } catch (ExcepcionBD ex) {
		 throw new GWT_ExcepcionBD(ex.getMessage());
	 }
	}

	@Override
	protected void finalize() throws Throwable {
	 if (unica_conexion)
		 PoolDeConexiones.devolver_conexion(con_unica);
	 super.finalize();
	}
	
	
	//*******************************************************************************************************
	
	
	public ContextoDeSeguridad autenticar_ya_logueado(ConexionBD con)
		    throws GWT_ExcepcionNoAutorizado
		  {
		    ContextoDeSeguridad cs = null;
//		    try
//		    {
		      cs = new ContextoDeSeguridad(getThreadLocalRequest(), NOMBRE_APLICACION);
//		      if (Autorizador.validar_token(cs) != Autorizador.StatusToken.TOKEN_VALIDO)
//		      {
//		        cs = intentar_validar_token_con_ambito();
//		      }
//		    } catch (ExcepcionNoAutorizado ex) {
//		      cs = intentar_validar_token_con_ambito();
//		    }
		    
		    return cs;
		  }
		  

//		  private ContextoDeSeguridad intentar_validar_token_con_ambito()
//		    throws GWT_ExcepcionNoAutorizado
//		  {
//		    ContextoDeSeguridad cs = new ContextoDeSeguridadCA(getThreadLocalRequest(), NOMBRE_APLICACION);
//		    
//		    if (Autorizador.validar_token(cs) != Autorizador.StatusToken.TOKEN_VALIDO) {
//		      throw new GWT_ExcepcionNoAutorizado(ERR_EST_AUTENTICACION, false, false);
//		    }
//		    
//
//		    return cs;
//		  }
		  

		  public ContextoDeSeguridad autenticar_y_autorizar(ConexionBD con/*, AplicacionConFuncionalidades permisos*/, String funcionalidad)
		    throws GWT_ExcepcionNoAutorizado
		  {
		    ContextoDeSeguridad cs = null;
		    StringBuffer error = new StringBuffer();
		    try
		    {
		      cs = new ContextoDeSeguridad(getThreadLocalRequest(), NOMBRE_APLICACION);
		    } catch (ExcepcionNoAutorizado ex) {
		      System.out.println("Autorización denegada al generar el contexto de seguridad. Motivo: " + ex.getMessage());
		      
		      throw new GWT_ExcepcionNoAutorizado(ERR_EST_AUTORIZACION, false, false);
		    }
		    
		    if (!Autorizador.validar_usuario(con, cs, funcionalidad, ERR_EST_AUTENTICACION, ERR_EST_EXPIRADA, ERR_EST_AUTORIZACION/*, permisos*/, error))
		    {



		      throw new GWT_ExcepcionNoAutorizado(error.toString(), true, false);
		    }
		    
		    return cs;
		  }
		  

//		  public ContextoDeSeguridadCA autenticar_y_autorizar_ca(ar.gov.mecon.genericos.bd.ConexionBD con, AplicacionConFuncionalidades permisos, String funcionalidad, Class<? extends AutorizadorPorAmbito> clase_autorizador)
//		    throws GWT_ExcepcionNoAutorizado, ExcepcionBD
//		  {
//		    ContextoDeSeguridadCA cs = null;
//		    StringBuffer error = new StringBuffer();
//		    try
//		    {
//		      cs = new ContextoDeSeguridadCA(getThreadLocalRequest(), NOMBRE_APLICACION);
//		    } catch (ExcepcionNoAutorizado ex) {
//		      System.out.println("Autorización denegada al generar el contexto de seguridad. Motivo: " + ex.getMessage());
//		      
//
//		      throw new GWT_ExcepcionNoAutorizado(ERR_EST_AUTORIZACION, false, false);
//		    }
//		    
//		    if (!Autorizador.validar_usuario(con, cs, funcionalidad, ERR_EST_AUTENTICACION, ERR_EST_EXPIRADA, ERR_EST_AUTORIZACION, permisos, error))
//		    {
//
//		      throw new GWT_ExcepcionNoAutorizado(error.toString(), false, false);
//		    }
		    


//		    if (clase_autorizador != null) {
//		      try {
//		        AutorizadorPorAmbito autorizador = (AutorizadorPorAmbito)Introspeccion.instanciar_objeto(clase_autorizador.getName(), new Class[0], new Object[0]);
//		        
//
//		        if (!autorizador.autorizar(con, cs.obtener_login(), cs.obtener_ambito(), funcionalidad, error))
//		        {
//		          throw new GWT_ExcepcionNoAutorizado(error.toString(), true, false); }
//		      } catch (IllegalAccessException ex) {
//		        ex.printStackTrace();
//		        throw new ExcepcionBug(ex.getMessage());
//		      } catch (SecurityException ex) {
//		        ex.printStackTrace();
//		        throw new ExcepcionBug(ex.getMessage());
//		      } catch (NoSuchMethodException ex) {
//		        ex.printStackTrace();
//		        throw new ExcepcionBug(ex.getMessage());
//		      } catch (IllegalArgumentException ex) {
//		        ex.printStackTrace();
//		        throw new ExcepcionBug(ex.getMessage());
//		      }/* catch (InvocationTargetException ex) {
//		        ex.printStackTrace();
//		        throw new ExcepcionBug(ex.getMessage());
//		      } */catch (ClassNotFoundException ex) {
//		        ex.printStackTrace();
//		        throw new ExcepcionBug(ex.getMessage());
//		      } catch (InstantiationException ex) {
//		        ex.printStackTrace();
//		        throw new ExcepcionBug(ex.getMessage());
//		      }
//		    }
		    

//		    return cs;
//		  }
		  
		  protected void usar_unica_conexion(boolean unica_conexion)
		  {
		    this.unica_conexion = unica_conexion;
		  }
		  


		  public DatosAutorizacion autenticado_y_autorizado(String funcionalidad)
		    throws GWT_ExcepcionBD
		  {
		    return autenticado_y_autorizado(funcionalidad, false, null);
		  }
		  


//		  protected DatosAutorizacion autenticado_y_autorizado_ca(String funcionalidad, Class<? extends AutorizadorPorAmbito> clase_autenticador)
//		    throws GWT_ExcepcionBD
//		  {
//		    return autenticado_y_autorizado(funcionalidad, true, clase_autenticador);
//		  }
		  

		  private DatosAutorizacion autenticado_y_autorizado(String funcionalidad, boolean usar_ambito, Class<? extends AutorizadorPorAmbito> clase_autorizador)
		    throws GWT_ExcepcionBD
		  {
		    DatosAutorizacion res = new DatosAutorizacion();
		    
		    res.cambiar_autorizado(true);
		    ConexionBD con;
		    if (unica_conexion) {
		      con = con_unica;
		    } else {
		      con = obtener_conexion();
		    }
		    try
		    {
		      AplicacionConFuncionalidades ap;// = (AplicacionConFuncionalidades)clase_ap_con_funcionalidad.newInstance();
		      funcionalidad = Sanitizador.sanitizar(funcionalidad);
		      ContextoDeSeguridad cs; 
		      
//		      if (usar_ambito) {
//		        ContextoDeSeguridad cs = autenticar_y_autorizar_ca(con, ap, funcionalidad, clase_autorizador);
//		        res.setAmbito(((ContextoDeSeguridadCA)cs).obtener_ambito());
//		        
//		      } else {
		    	  
		        cs = autenticar_y_autorizar(con, funcionalidad); 
//		       }
		      
		      res.cambiar_autenticado(true);
		      res.setLogin(cs.obtener_login());
		    } catch (GWT_ExcepcionNoAutorizado ex) {
		      res.cambiar_autenticado(ex.estaAutenticado());
		      res.cambiar_autorizado(ex.estaAutorizado());
		      res.setMensaje_error(ex.getMessage());
		      
		      System.err.println("No autorizado para " + funcionalidad + " (desde " + getThreadLocalRequest().getRemoteAddr() + ")" + ". El error fue: " + ex.getMessage());
		    } finally {
		      if (!unica_conexion) {
		        devolver_conexion(con);
		      }
		    }
		    return res;
		  }
		
	
	
	
	//**************************************************************************************************************************

}
