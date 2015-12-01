package ar.gov.chris.server.bd;

import ar.gov.chris.server.excepciones.ExcepcionBD;
import ar.gov.chris.server.bd.ConexionBD.*;

import java.util.concurrent.LinkedBlockingQueue;


public class PoolDeConexiones {
	/** Clase que representa un pool de conexiones de un tipo dado (es decir, maneja 
	 * conexiones de sólo lectura o de lectoescritura, pero no ambas).
	 */
	private static class SubpoolDeConexiones {
		private final TipoConexion tipo_conexion;
		private int cant_conexiones_activas= 0;
		private int cant_conexiones_iniciales= 2;
		private int cant_maxima_conexiones= SIN_MAXIMO;
		/** Indica si, en caso de haberse excedido la cantidad de conexiones iniciales,
		 * al devolverse una conexión al pool ésta debe cerrarse.
		 */
		private boolean volver_a_cant_inicial= true;
		private final LinkedBlockingQueue<ConexionBD> cola= new LinkedBlockingQueue<ConexionBD>();

		/** Constructor.
		 * @param tipo El tipo de conexiones que va a manejar el subpool.
		 * @param cant_conexiones_iniciales La cantidad de conexiones iniciales.
		 * @param cant_max_conexiones La cantidad máxima de conexiones.
		 * @param volver_a_cant_inicial Indica si se debe volver a la cantidad
		 * inicial de conexiones cuando se devuelve una al pool.
		 * @throws ExcepcionBD Si no se pueden crear las conexiones iniciales.
		 */
		public SubpoolDeConexiones(TipoConexion tipo, int cant_conexiones_iniciales, 
			int cant_max_conexiones, boolean volver_a_cant_inicial)
			throws ExcepcionBD {
		 this.tipo_conexion= tipo;
		 this.cant_conexiones_iniciales= cant_conexiones_iniciales;
		 this.cant_maxima_conexiones= cant_max_conexiones;
		 this.volver_a_cant_inicial= volver_a_cant_inicial;
		 for (int i= 0; i < this.cant_conexiones_iniciales; i++)
			 agregar_conexion(); 
		}
		
		/** Agrega una conexión al pool.
		 * @throws ExcepcionBD Si hay algún problema con la BD.
		 */
		private void agregar_conexion() throws ExcepcionBD {
		 ConexionBD con= null;
		 if (host_dblink==null)
			 // No se usa DBLink.
			 con= new ConexionBD(this.tipo_conexion);
		 else
			 // Sí se usa.
			 con= new ConexionBD(this.tipo_conexion, host_dblink, bd_dblink,
					 usr_dblink, contrasena_dblink);
		 this.cant_conexiones_activas++;
		 this.cola.add(con);
		}
		
		/** Devuelve una conexión del pool.
		 * @return Una conexión del pool.
		 * @throws ExcepcionBD Si no hay más conexiones disponibles.
		 */
		public synchronized ConexionBD obtener_conexion() throws ExcepcionBD {
		 if (cola.size()==0) {
			 if (this.cant_maxima_conexiones==SIN_MAXIMO ||
			 	this.cant_conexiones_activas<this.cant_maxima_conexiones) {
				 agregar_conexion();
			 } else {
				 // No tengo más conexiones ni permiso para agregar otra.
				 throw new ExcepcionBD("El pool de conexiones a la BD no tiene " +
				 		"más disponibles (actualmente " + this.cant_conexiones_activas +
				 		"). Incrementar el máximo.");
			 }
		 }
		 // A esta altura seguro tengo una conexión en la cola.
		 ConexionBD con= this.cola.poll();

		 return con;
		}
		
		/** Devuelve una conexión al pool (previo cerrarla).
		 * @param con La conexión a devolver.
		 * @throws ExcepcionBD Si hay algún problema al cerrar la conexión.
		 */
		public synchronized void devolver_conexion(ConexionBD con)
			throws ExcepcionBD {
		 if (this.volver_a_cant_inicial &&
			this.cant_conexiones_activas>this.cant_conexiones_iniciales) {
			 // Hay que cerrarla.
			 this.cant_conexiones_activas--;
			 con.cerrar();
		 } else
			 // Vuelvo a encolarla.
			 this.cola.add(con);
		}
	}
	
	/** Constante para indicar que se deben generar todas las nuevas correcciones
	 * que sean necesarias.
	 */
	static final public int SIN_MAXIMO= -1;

	static private boolean subpools_inicializados= false;
	// Indica si se deben crear conexiones de sólo lectura o no.
	static private boolean tambien_solo_lectura= false;
	static private SubpoolDeConexiones pool_solo_lectura;
	static private SubpoolDeConexiones pool_lectoescritura;
	static private int cant_conexiones_iniciales= 2;
	static private int cant_maxima_conexiones= SIN_MAXIMO;
	static private int cant_conexiones_iniciales_solo_lectura= 2;
	static private int cant_maxima_conexiones_solo_lectura= SIN_MAXIMO;
	static private String host_dblink= null;
	static private String bd_dblink= null;
	static private String usr_dblink= null;
	static private String contrasena_dblink= null;
	
	/** Indica si, en caso de haberse excedido la cantidad de conexiones iniciales,
	 * al devolverse una conexión al pool ésta debe cerrarse.
	 */
	static private boolean volver_a_cant_inicial= true;
	static private boolean volver_a_cant_inicial_solo_lectura= true;
	
	/** Establece la cantidad de conexiones iniciales.
	 * @param cant Nueva cantidad de conexiones iniciales.
	 */
	static public void establecer_cant_conexiones_iniciales(int cant) {
	 cant_conexiones_iniciales= cant;
	}
	
	/** Establece la cantidad máxima de conexiones.
	 * @param cant Nueva cantidad máxima de conexiones.
	 */
	static public void establecer_cant_maxima_conexiones(int cant) {
	 cant_maxima_conexiones= cant;
	}

	/** Decide si las conexiones devueltas que exceden la cantidad inicial
	 * deben cerrarse o no. 
	 * @param cerrar Indica si cerrar o no las conexiones devueltas que exceden
	 * la cantidad inicial.
	 */
	static public void cerrar_conexiones_sobrantes(boolean cerrar) {
	 volver_a_cant_inicial= cerrar;
	}

	/** Establece la cantidad de conexiones iniciales de sólo lectura.
	 * @param cant Nueva cantidad de conexiones iniciales.
	 */
	static public void establecer_cant_conexiones_iniciales_solo_lectura(int cant) {
	 cant_conexiones_iniciales_solo_lectura= cant;
	}

	/** Establece la cantidad máxima de conexiones de sólo lectura.
	 * @param cant Nueva cantidad máxima de conexiones.
	 */
	static public void establecer_cant_maxima_conexiones_solo_lectura(int cant) {
	 cant_maxima_conexiones_solo_lectura= cant;
	}

	/** Decide si las conexiones de sólo lectura devueltas que exceden la
	 * cantidad inicial deben cerrarse o no. 
	 * @param cerrar Indica si cerrar o no las conexiones devueltas que exceden
	 * la cantidad inicial.
	 */
	static public void cerrar_conexiones_sobrantes_solo_lectura(boolean cerrar) {
	 volver_a_cant_inicial_solo_lectura= cerrar;
	}

	/** Establece los parámetros DBLink de las conexiones.
	 * 
	 * @param host El host al que conectarse por DBLink.
	 * @param bd La BD a la que conectarse por DBLink.
	 * @param usr El usr que hay que usar al conectarse por DBLink.
	 * @param contrasena La contraseña para conectarse por DBLink. 
	 */
	static public void establecer_parametros_dblink(String host, String bd,
			String usr, String contrasena) {
	 host_dblink= host;
	 bd_dblink= bd;
	 usr_dblink= usr;
	 contrasena_dblink= contrasena;
	}
	
	/** Crea la mínima cantidad de conexiones necesarias, si no están ya
	 * creadas.
	 * @throws ExcepcionBD Si hay algún problema con la BD.
	 */
	static synchronized private void inicializar_si_hace_falta() throws ExcepcionBD {
	 if (subpools_inicializados)
		 return;
	 if (tambien_solo_lectura)
		 pool_solo_lectura= new SubpoolDeConexiones(TipoConexion.SOLO_LECTURA,
				 cant_conexiones_iniciales_solo_lectura, 
				 cant_maxima_conexiones_solo_lectura,
				 volver_a_cant_inicial_solo_lectura);
	 pool_lectoescritura= new SubpoolDeConexiones(TipoConexion.LECTOESCRITURA, 
			 cant_conexiones_iniciales, cant_maxima_conexiones, 
			 volver_a_cant_inicial);
	 subpools_inicializados= true;
	}
	
	/** Establece las conexiones.
	 * @throws ExcepcionBD Si hay algún problema con la BD.
	 */
	static public void prearmar_conexiones() throws ExcepcionBD {
	 inicializar_si_hace_falta();
	}
	
	/** Establece las conexiones.
	 * @param incluir_solo_lectura Indica si se deben incluir o no conexiones
	 * de sólo lectura.
	 * @throws ExcepcionBD Si hay algún problema con la BD.
	 */
	static public void prearmar_conexiones(boolean incluir_solo_lectura)
		throws ExcepcionBD {
	 tambien_solo_lectura= incluir_solo_lectura;
	 inicializar_si_hace_falta();
	}

	/** Devuelve una conexión al pool (previo cerrarla).
	 * @param con La conexión a devolver.
	 * @throws ExcepcionBD Si hay algún problema al cerrar la conexión.
	 */
	static public synchronized void devolver_conexion(ConexionBD con)
		throws ExcepcionBD {
	 if (con.obtener_tipo_conexion().equals(TipoConexion.LECTOESCRITURA))
		 pool_lectoescritura.devolver_conexion(con);
	 else 
		 pool_solo_lectura.devolver_conexion(con);
	}
	
	/** Devuelve una conexión para lectura y escritura del pool.
	 * @return Una conexión del pool.
	 * @throws ExcepcionBD Si no hay más conexiones disponibles.
	 */
	static public synchronized ConexionBD obtener_conexion() throws ExcepcionBD {
	 return obtener_conexion(TipoConexion.LECTOESCRITURA);
	}
	
	/** Devuelve una conexión del pool.
	 * @param tipo_conexion El tipo de la conexión que se desea obtener.
	 * @return Una conexión del pool.
	 * @throws ExcepcionBD Si no hay más conexiones disponibles.
	 */
	static public synchronized ConexionBD obtener_conexion(TipoConexion tipo_conexion) 
		throws ExcepcionBD {
	 inicializar_si_hace_falta();
	 if (tipo_conexion.equals(TipoConexion.LECTOESCRITURA))
		 return pool_lectoescritura.obtener_conexion();
	 else
		 return pool_solo_lectura.obtener_conexion();
	}
}
