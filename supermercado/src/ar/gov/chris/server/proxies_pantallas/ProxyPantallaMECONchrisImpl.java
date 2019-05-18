//package ar.gov.chris.server.proxies_pantallas;
//
//import ar.gov.mecon.componentes.server.proxies.ProxyPantallaMECONImpl;
//import ar.gov.mecon.genericos.contexto.ContextoDeSeguridad;
//
//public class ProxyPantallaMECONchrisImpl /*extends ProxyPantallaMECONImpl*/{
//	 
////	ContextoDeSeguridad cs = autenticar_y_autorizar("", "", "");
//	
//	package ar.gov.mecon.componentes.server.proxies;
//
//	import ar.gov.mecon.componentes.client.datos.DatosAutorizacion;
//	import ar.gov.mecon.componentes.client.interfaces.ProxyPantallaMECON;
//	import ar.gov.mecon.componentes.server.clases.ContextoDeSeguridadCA;
//	import ar.gov.mecon.componentes.server.seguridad.AutorizadorPorAmbito;
//	import ar.gov.mecon.genericos.autorizacion.Autorizador;
//	import ar.gov.mecon.genericos.autorizacion.Autorizador.StatusToken;
//	import ar.gov.mecon.genericos.basicos.LectorConfiguracion;
//	import ar.gov.mecon.genericos.basicos.Sanitizador;
//	import ar.gov.mecon.genericos.bd.ConexionBD;
//	import ar.gov.mecon.genericos.bd.ConexionBD.TipoConexion;
//	import ar.gov.mecon.genericos.bd.PoolDeConexiones;
//	import ar.gov.mecon.genericos.contexto.ContextoDeSeguridad;
//	import ar.gov.mecon.genericos.excepciones.ExcepcionBD;
//	import ar.gov.mecon.genericos.excepciones.ExcepcionBug;
//	import ar.gov.mecon.genericos.excepciones.ExcepcionNoAutorizado;
//	import ar.gov.mecon.genericos.servicios.AplicacionConFuncionalidades;
//	import ar.gov.mecon.genericos.util.Introspeccion;
//	import ar.gov.mecon.gwt.client.excepciones.GWT_ExcepcionBD;
//	import ar.gov.mecon.gwt.client.excepciones.GWT_ExcepcionNoAutorizado;
//	import com.google.gwt.user.server.rpc.RemoteServiceServlet;
//	import java.io.PrintStream;
//	import java.lang.reflect.InvocationTargetException;
//	import javax.servlet.ServletConfig;
//	import javax.servlet.ServletException;
//	import javax.servlet.http.HttpServletRequest;
//
//	public class ProxyPantallaMECONImpl
//	  extends RemoteServiceServlet
//	  implements ProxyPantallaMECON
//	{
//	  private static final long serialVersionUID = -8775417557826585044L;
//	  private static String NOMBRE_APLICACION;
//	  protected static String ERR_EST_AUTENTICACION = "No está autenticado, loguéese.";
//	  
//	  protected static String ERR_EST_EXPIRADA = "Autenticación expirada. Loguéese nuevamente.";
//	  
//
//	  protected static String ERR_EST_AUTORIZACION = "Ud. no está autorizado para realizar esta función.";
//	  
//	  private static Class<AplicacionConFuncionalidades> clase_ap_con_funcionalidad;
//	  
//	  protected ConexionBD con_unica = null;
//	  
//	  protected boolean unica_conexion = true;
//	 
//
//	  public ProxyPantallaMECONImpl() {}
//	 
//
//	  public void init(ServletConfig config, boolean conectarse_a_stock, int cant_con_iniciales, int cant_max_con, boolean cerrar_con_sobrantes, String nombre_aplicacion, String err_est_autenticacion, String err_est_autorizacion, String err_est_expirada, String nombre_clase_ap_con_funcionalidad)
//	    throws ServletException, ClassNotFoundException
//	  {
//	    init(config, conectarse_a_stock, cant_con_iniciales, cant_max_con, cerrar_con_sobrantes, nombre_aplicacion, err_est_autenticacion, err_est_autorizacion, err_est_expirada, nombre_clase_ap_con_funcionalidad, 0, 0);
//	  }
//	  
//
//	  public void init(ServletConfig config, boolean conectarse_a_stock, int cant_con_iniciales, int cant_max_con, boolean cerrar_con_sobrantes, String nombre_aplicacion, String err_est_autenticacion, String err_est_autorizacion, String err_est_expirada, String nombre_clase_ap_con_funcionalidad, int cant_con_iniciales_solo_lectura, int cant_max_con_solo_lectura)
//	    throws ServletException, ClassNotFoundException
//	  {
//	    super.init(config);
//	    LectorConfiguracion.inicializar(config);
//	    
//	    NOMBRE_APLICACION = nombre_aplicacion;
//	    if (err_est_autenticacion != null)
//	      ERR_EST_AUTENTICACION = err_est_autenticacion;
//	    if (err_est_autorizacion != null)
//	      ERR_EST_AUTORIZACION = err_est_autorizacion;
//	    if (err_est_expirada != null) {
//	      ERR_EST_EXPIRADA = err_est_expirada;
//	    }
//	    
//	    clase_ap_con_funcionalidad = Class.forName(nombre_clase_ap_con_funcionalidad);
//	    
//
//
//	    if (conectarse_a_stock) {
//	      String host_stock = LectorConfiguracion.obtener_valor("host_stock");
//	      String bd_stock = LectorConfiguracion.obtener_valor("bd_stock");
//	      String usr_stock = LectorConfiguracion.obtener_valor("usr_stock");
//	      String contr_stock = LectorConfiguracion.obtener_valor("contr_stock");
//	      
//	      if ((host_stock == null) || (bd_stock == null) || (usr_stock == null) || (contr_stock == null))
//	      {
//	        throw new ServletException("No están definidos todos los parámetros para conectarse a Stock ({host,bd,usr,contr}_stock).");
//	      }
//	      
//
//	      PoolDeConexiones.establecer_parametros_dblink(host_stock, bd_stock, usr_stock, contr_stock);
//	    }
//	    
//
//
//	    PoolDeConexiones.establecer_cant_conexiones_iniciales(cant_con_iniciales);
//	    PoolDeConexiones.establecer_cant_maxima_conexiones(cant_max_con);
//	    PoolDeConexiones.cerrar_conexiones_sobrantes(cerrar_con_sobrantes);
//	    
//
//
//	    boolean usar_bd_solo_lectura = cant_con_iniciales_solo_lectura > 0;
//	    if (usar_bd_solo_lectura) {
//	      PoolDeConexiones.establecer_cant_conexiones_iniciales_solo_lectura(cant_con_iniciales_solo_lectura);
//	      PoolDeConexiones.establecer_cant_maxima_conexiones_solo_lectura(cant_max_con_solo_lectura);
//	      PoolDeConexiones.cerrar_conexiones_sobrantes(cerrar_con_sobrantes);
//	    }
//	    
//	    try
//	    {
//	      PoolDeConexiones.prearmar_conexiones(usar_bd_solo_lectura);
//	      if (unica_conexion)
//	        con_unica = PoolDeConexiones.obtener_conexion();
//	    } catch (ExcepcionBD ex) {
//	      System.out.println(ex.getMessage());
//	      throw new ServletException(ex);
//	    }
//	  }
//	  
//
//
//
//	  protected ConexionBD obtener_conexion()
//	    throws GWT_ExcepcionBD
//	  {
//	    return obtener_conexion(ConexionBD.TipoConexion.LECTOESCRITURA);
//	  }
//	  
//	  protected ConexionBD obtener_conexion(ConexionBD.TipoConexion tipo)
//	    throws GWT_ExcepcionBD
//	  {
//	    if (unica_conexion) {
//	      throw new RuntimeException("Si se usa una única conexión a la BD no se puede pedir otra más al pool.");
//	    }
//	    ConexionBD con = null;
//	    try
//	    {
//	      con = PoolDeConexiones.obtener_conexion(tipo);
//	    } catch (ExcepcionBD ex) {
//	      throw new GWT_ExcepcionBD(ex);
//	    }
//	    
//	    return con;
//	  }
//	  
//
//	  protected ConexionBD obtener_transaccion()
//	    throws GWT_ExcepcionBD
//	  {
//	    return obtener_transaccion(ConexionBD.TipoConexion.LECTOESCRITURA);
//	  }
//	  
//
//	  protected ConexionBD obtener_transaccion(ConexionBD.TipoConexion tipo)
//	    throws GWT_ExcepcionBD
//	  {
//	    ConexionBD con = null;
//	    try
//	    {
//	      con = PoolDeConexiones.obtener_conexion(tipo);
//	      con.begin_transaction();
//	    } catch (ExcepcionBD ex) {
//	      throw new GWT_ExcepcionBD(ex);
//	    }
//	    
//	    return con;
//	  }
//	  
//	  protected void devolver_conexion(ConexionBD con)
//	    throws GWT_ExcepcionBD
//	  {
//	    try
//	    {
//	      PoolDeConexiones.devolver_conexion(con);
//	    } catch (ExcepcionBD ex) {
//	      throw new GWT_ExcepcionBD(ex.getMessage());
//	    }
//	  }
//	  
//
//	  protected void cerrar_transaccion(ConexionBD con, boolean commit)
//	    throws GWT_ExcepcionBD
//	  {
//	    try
//	    {
//	      if (commit) {
//	        con.commit();
//	      } else
//	        con.rollback();
//	      PoolDeConexiones.devolver_conexion(con);
//	    } catch (ExcepcionBD ex) {
//	      throw new GWT_ExcepcionBD(ex.getMessage());
//	    }
//	  }
//	  
//	  protected void finalize() throws Throwable
//	  {
//	    if (unica_conexion)
//	      PoolDeConexiones.devolver_conexion(con_unica);
//	    super.finalize();
//	  }
//	  
//
//	  public ContextoDeSeguridad autenticar_ya_logueado(ConexionBD con)
//	    throws GWT_ExcepcionNoAutorizado
//	  {
//	    ContextoDeSeguridad cs = null;
//	    try
//	    {
//	      cs = new ContextoDeSeguridad(getThreadLocalRequest(), NOMBRE_APLICACION);
//	      if (Autorizador.validar_token(cs) != Autorizador.StatusToken.TOKEN_VALIDO)
//	      {
//	        cs = intentar_validar_token_con_ambito();
//	      }
//	    } catch (ExcepcionNoAutorizado ex) {
//	      cs = intentar_validar_token_con_ambito();
//	    }
//	    
//	    return cs;
//	  }
//	  
//
//	  private ContextoDeSeguridad intentar_validar_token_con_ambito()
//	    throws GWT_ExcepcionNoAutorizado
//	  {
//	    ContextoDeSeguridad cs = new ContextoDeSeguridadCA(getThreadLocalRequest(), NOMBRE_APLICACION);
//	    
//	    if (Autorizador.validar_token(cs) != Autorizador.StatusToken.TOKEN_VALIDO) {
//	      throw new GWT_ExcepcionNoAutorizado(ERR_EST_AUTENTICACION, false, false);
//	    }
//	    
//
//	    return cs;
//	  }
//	  
//
//	  public ContextoDeSeguridad autenticar_y_autorizar(ConexionBD con, AplicacionConFuncionalidades permisos, String funcionalidad)
//	    throws GWT_ExcepcionNoAutorizado
//	  {
//	    ContextoDeSeguridad cs = null;
//	    StringBuffer error = new StringBuffer();
//	    try
//	    {
//	      cs = new ContextoDeSeguridad(getThreadLocalRequest(), NOMBRE_APLICACION);
//	    } catch (ExcepcionNoAutorizado ex) {
//	      System.out.println("Autorización denegada al generar el contexto de seguridad. Motivo: " + ex.getMessage());
//	      
//	      throw new GWT_ExcepcionNoAutorizado(ERR_EST_AUTORIZACION, false, false);
//	    }
//	    
//	    if (!Autorizador.validar_usuario(con, cs, funcionalidad, ERR_EST_AUTENTICACION, ERR_EST_EXPIRADA, ERR_EST_AUTORIZACION, permisos, error))
//	    {
//
//
//
//	      throw new GWT_ExcepcionNoAutorizado(error.toString(), true, false);
//	    }
//	    
//	    return cs;
//	  }
//	  
//
//	  public ContextoDeSeguridadCA autenticar_y_autorizar_ca(ConexionBD con, AplicacionConFuncionalidades permisos, String funcionalidad, Class<? extends AutorizadorPorAmbito> clase_autorizador)
//	    throws GWT_ExcepcionNoAutorizado, ExcepcionBD
//	  {
//	    ContextoDeSeguridadCA cs = null;
//	    StringBuffer error = new StringBuffer();
//	    try
//	    {
//	      cs = new ContextoDeSeguridadCA(getThreadLocalRequest(), NOMBRE_APLICACION);
//	    } catch (ExcepcionNoAutorizado ex) {
//	      System.out.println("Autorización denegada al generar el contexto de seguridad. Motivo: " + ex.getMessage());
//	      
//
//	      throw new GWT_ExcepcionNoAutorizado(ERR_EST_AUTORIZACION, false, false);
//	    }
//	    
//	    if (!Autorizador.validar_usuario(con, cs, funcionalidad, ERR_EST_AUTENTICACION, ERR_EST_EXPIRADA, ERR_EST_AUTORIZACION, permisos, error))
//	    {
//
//	      throw new GWT_ExcepcionNoAutorizado(error.toString(), false, false);
//	    }
//	    
//
//
//	    if (clase_autorizador != null) {
//	      try {
//	        AutorizadorPorAmbito autorizador = (AutorizadorPorAmbito)Introspeccion.instanciar_objeto(clase_autorizador.getName(), new Class[0], new Object[0]);
//	        
//
//	        if (!autorizador.autorizar(con, cs.obtener_login(), cs.obtener_ambito(), funcionalidad, error))
//	        {
//	          throw new GWT_ExcepcionNoAutorizado(error.toString(), true, false); }
//	      } catch (IllegalAccessException ex) {
//	        ex.printStackTrace();
//	        throw new ExcepcionBug(ex.getMessage());
//	      } catch (SecurityException ex) {
//	        ex.printStackTrace();
//	        throw new ExcepcionBug(ex.getMessage());
//	      } catch (NoSuchMethodException ex) {
//	        ex.printStackTrace();
//	        throw new ExcepcionBug(ex.getMessage());
//	      } catch (IllegalArgumentException ex) {
//	        ex.printStackTrace();
//	        throw new ExcepcionBug(ex.getMessage());
//	      } catch (InvocationTargetException ex) {
//	        ex.printStackTrace();
//	        throw new ExcepcionBug(ex.getMessage());
//	      } catch (ClassNotFoundException ex) {
//	        ex.printStackTrace();
//	        throw new ExcepcionBug(ex.getMessage());
//	      } catch (InstantiationException ex) {
//	        ex.printStackTrace();
//	        throw new ExcepcionBug(ex.getMessage());
//	      }
//	    }
//	    
//
//	    return cs;
//	  }
//	  
//	  protected void usar_unica_conexion(boolean unica_conexion)
//	  {
//	    this.unica_conexion = unica_conexion;
//	  }
//	  
//
//
//	  public DatosAutorizacion autenticado_y_autorizado(String funcionalidad)
//	    throws GWT_ExcepcionBD
//	  {
//	    return autenticado_y_autorizado(funcionalidad, false, null);
//	  }
//	  
//
//
//	  protected DatosAutorizacion autenticado_y_autorizado_ca(String funcionalidad, Class<? extends AutorizadorPorAmbito> clase_autenticador)
//	    throws GWT_ExcepcionBD
//	  {
//	    return autenticado_y_autorizado(funcionalidad, true, clase_autenticador);
//	  }
//	  
//
//	  private DatosAutorizacion autenticado_y_autorizado(String funcionalidad, boolean usar_ambito, Class<? extends AutorizadorPorAmbito> clase_autorizador)
//	    throws GWT_ExcepcionBD
//	  {
//	    DatosAutorizacion res = new DatosAutorizacion();
//	    
//	    res.cambiar_autorizado(true);
//	    ConexionBD con;
//	    ConexionBD con;
//	    if (unica_conexion) {
//	      con = con_unica;
//	    } else {
//	      con = obtener_conexion();
//	    }
//	    try
//	    {
//	      AplicacionConFuncionalidades ap = (AplicacionConFuncionalidades)clase_ap_con_funcionalidad.newInstance();
//	      funcionalidad = Sanitizador.sanitizar(funcionalidad);
//	      ContextoDeSeguridad cs; if (usar_ambito) {
//	        ContextoDeSeguridad cs = autenticar_y_autorizar_ca(con, ap, funcionalidad, clase_autorizador);
//	        res.setAmbito(((ContextoDeSeguridadCA)cs).obtener_ambito());
//	      } else {
//	        cs = autenticar_y_autorizar(con, ap, funcionalidad); }
//	      res.cambiar_autenticado(true);
//	      res.setLogin(cs.obtener_login());
//	    } catch (GWT_ExcepcionNoAutorizado ex) {
//	      res.cambiar_autenticado(ex.estaAutenticado());
//	      res.cambiar_autorizado(ex.estaAutorizado());
//	      res.setMensaje_error(ex.getMessage());
//	      
//	      System.err.println("No autorizado para " + funcionalidad + " (desde " + getThreadLocalRequest().getRemoteAddr() + ")" + ". El error fue: " + ex.getMessage());
//	    }
//	    catch (InstantiationException ex)
//	    {
//	      ex.printStackTrace();
//	      throw new ExcepcionBug(ex.getMessage());
//	    } catch (IllegalAccessException ex) {
//	      ex.printStackTrace();
//	      throw new ExcepcionBug(ex.getMessage());
//	    } catch (ExcepcionBD ex) {
//	      throw new GWT_ExcepcionBD(ex);
//	    } finally {
//	      if (!unica_conexion) {
//	        devolver_conexion(con);
//	      }
//	    }
//	    return res;
//	  }
//	}
//
//}
