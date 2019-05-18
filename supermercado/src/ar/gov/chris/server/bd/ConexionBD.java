package ar.gov.chris.server.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

//import org.postgresql.largeobject.LargeObjectManager;



//import ar.gov.mecon.genericos.basicos.EnumMECON;
//import ar.gov.mecon.genericos.basicos.LectorConfiguracion;
import ar.gov.chris.shared.Sanitizador;
//import ar.gov.mecon.genericos.bd.Cache;
import ar.gov.chris.server.bd.HashMapSQL;
import ar.gov.chris.server.clases.LectorPropiedades;
//import ar.gov.mecon.genericos.bd.ConexionBD.TipoConexion;
import ar.gov.chris.server.excepciones.ExcepcionBD;
import ar.gov.chris.server.excepciones.ExcepcionBug;
import ar.gov.chris.server.excepciones.ExcepcionConexionBD;
import ar.gov.chris.server.excepciones.ExcepcionIO;
//import ar.gov.mecon.genericos.excepciones.ExcepcionConexionBD;
import ar.gov.chris.server.excepciones.ExcepcionNoExiste;

public class ConexionBD {
	/**
	 * Tipos de conexi�n posibles.
	 */
	public enum TipoConexion {
		SOLO_LECTURA,
		LECTOESCRITURA
	}

	// Por default, me conecto a una BD que permita leer y escribir.
	private TipoConexion tipo_conexion= TipoConexion.LECTOESCRITURA;
	
	private Connection conn;
//	private final Cache cache= new Cache();
	private boolean db_link_conectado= false;
	private Statement select_stmt= null;
   
	/**
	 * Genera una conexi�n contra la BD con todos los par�metros por omisi�n.
	 * 
	 * @throws ExcepcionConexionBD Si hay alg�n problema.
	 */	
	public ConexionBD() throws ExcepcionConexionBD {
	 this(TipoConexion.LECTOESCRITURA);
	}

	/**
	 * Crea una conexi�n contra la BD y a continuaci�n abre una conexi�n DBLink con los
	 * par�metros que se le pasan.
	 * 
	 * @param host_dblink El host al que conectarse por DBLink.
	 * @param bd_dblink La BD a la que conectarse por DBLink.
	 * @param usr_dblink El usr que hay que usar al conectarse por DBLink.
	 * @param contrasena_dblink La contrase�a a usar para conectarse por DBLink.
	 * @throws ExcepcionBD Si hay alg�n problema al conectarse por DBLink.
	 */
	public ConexionBD(String host_dblink, String bd_dblink, String usr_dblink,
			String contrasena_dblink) throws ExcepcionBD {
	 this(TipoConexion.LECTOESCRITURA);
	 this.select("SELECT dblink_connect('hostaddr="+host_dblink+" dbname="+bd_dblink+
			 " user="+usr_dblink+" password="+contrasena_dblink+"'::text)");
	 this.db_link_conectado= true;
	}
	
	/**
	 * Crea una conexi�n contra la BD y a continuaci�n abre una conexi�n DBLink con los
	 * par�metros que se le pasan.
	 * 
	 * @param tipo_conexion El tipo de la conexi�n.
	 * @param host_dblink El host al que conectarse por DBLink.
	 * @param bd_dblink La BD a la que conectarse por DBLink.
	 * @param usr_dblink El usr que hay que usar al conectarse por DBLink.
	 * @param contrasena_dblink La contrase�a a usar para conectarse por DBLink.
	 * @throws ExcepcionBD Si hay alg�n problema al conectarse por DBLink.
	 */
	public ConexionBD(TipoConexion tipo_conexion, String host_dblink, String bd_dblink,
			String usr_dblink, String contrasena_dblink) throws ExcepcionBD {
	 this(tipo_conexion);
	 this.select("SELECT dblink_connect('hostaddr="+host_dblink+" dbname="+bd_dblink+
			 " user="+usr_dblink+" password="+contrasena_dblink+"'::text)");
	 this.db_link_conectado= true;
	}

	/**
	 * Genera una conexi�n contra una BD del tipo indicado en el par�metro.
	 * 
	 * @param tipo Tipo de conexi�n deseada.
	 * @throws ExcepcionConexionBD Si hay alg�n problema.
	 */	
	public ConexionBD(TipoConexion tipo) throws ExcepcionConexionBD {
	 String driver= "";
	 String usr= null, url= null, contrasena= null, hostbd= null, bd= null,	path_bd= null,
	 		path;
	 tipo_conexion= tipo;
	 
	 // Adec�o los par�metros en base al tipo de BD.
	 if (tipo_conexion.equals(TipoConexion.LECTOESCRITURA)) {
		 driver= "org.postgresql.Driver";
		 driver= "org.postgresql.Driver";
		 try {
			hostbd= LectorPropiedades.obtener_valor("hostbd");
		} catch (ExcepcionIO ex) {
			throw new ExcepcionConexionBD("Error al buscar el host de la BD: "
					 +ex.getMessage());
		}//"192.168.1.30";
//		 hostbd= "localhost";
		 bd= "supermercado";
		 url= "jdbc:postgresql://"+ hostbd + "/"+bd; 
		 usr= "postgres";
		 contrasena= "laquevenga"; 
		 path_bd= "public"; 
		 
//		 hostbd= LectorConfiguracion.obtener_valor("bd_host");
//		 bd= LectorConfiguracion.obtener_valor("bd_bd");
//		 url= "jdbc:postgresql://"+ hostbd + "/"+bd+"?ssl";
//		 usr= LectorConfiguracion.obtener_valor("bd_usr");
//		 contrasena= LectorConfiguracion.obtener_valor("bd_contrasena");
//		 path_bd= LectorConfiguracion.obtener_valor("bd_path");
	 } else {
		 if (tipo_conexion.equals(TipoConexion.SOLO_LECTURA)) {
			 driver= "org.postgresql.Driver";
			 
			 hostbd= "localhost";
			 try {
					hostbd= LectorPropiedades.obtener_valor("hostbd");
				} catch (ExcepcionIO ex) {
					throw new ExcepcionConexionBD("Error al buscar el host de la BD: "
							 +ex.getMessage());
				}
			 //"192.168.1.30";

			 bd= "supermercado";
			 url= "jdbc:postgresql://"+ hostbd + "/"+bd; //+"?ssl";
			 usr= "postgres";
			 contrasena= "laquevenga";  //.obtener_valor("bd_contrasena_lectura");
			 path_bd= "public"; // LectorConfiguracion.obtener_valor("bd_path_lectura");
		 }
	 }
	 // Verifico que est�n bien definidos los par�metros.
	 if (hostbd==null || bd==null || usr==null || contrasena==null || path_bd==null) {
		 System.out.println("Falta alg�n par�metro para conectarse a la BD.\n" +
				 "host="+hostbd+"\nbd="+bd+"\nusr="+usr+"\n"+
				 "contrasena="+(contrasena==null ? "null" : "no es null")+
				 "\npath="+path_bd+"\n");
		 throw new RuntimeException("Alg�n par�metro de configuraci�n de la BD no est� " +
		 		"definido. Revisar en el archivo de configuraci�n los par�metros " +
		 		"bd_host, bd_usr, bd_contrasena y bd_path.");
	 }
	 path= path_bd+", public";

	 // Cargo el driver.
	 try {
		 Class.forName(driver);
	 } catch (ClassNotFoundException ex) {
		 throw new ExcepcionBug("Conexi�n Supermercado - driver: "+ex.getMessage());
	 }

	 // Hago la conexi�n.
	 try {
		 this.conn= DriverManager.getConnection(url, usr, contrasena);
	 } catch (SQLException ex) {
		 ex.printStackTrace();
		 throw new ExcepcionConexionBD("conect�ndose a "+url+" como "+usr+".", ex);
	 }

	 this.tipo_conexion= tipo;
	 
	 // Seteo el tipo de fecha.
	 try {
		 this.ejecutar_sql("SET DateStyle = European");
	 } catch (ExcepcionBD ex) {
		 throw new ExcepcionConexionBD("Error al establecer el formato de fecha: "
				 +ex.getMessage());
	 }
	 
	 // Seteo el path de schemas.
	 if (path!=null) {
		 try {
			 this.ejecutar_sql("SET search_path TO "+path);
		 } catch (ExcepcionBD ex) {
			 throw new ExcepcionConexionBD("Error al establecer el path de schemas: "
					 +ex.getMessage());
		 }
	 }
	}
	
	/**
	 * Comienza una transacci�n.
	 * 
	 * @throws ExcepcionBD Si hay alg�n problema.
	 */	
	public void begin_transaction() throws ExcepcionBD {
	 try {
		 this.conn.setAutoCommit(false);
	 } catch (SQLException ex) {
		 throw new ExcepcionBD(ex);
	 }
	}

	/**
	 * Hace COMMIT de una transacci�n abierta.
	 * 
	 * @throws ExcepcionBD Si hay alg�n problema.
	 */	
	public void commit() throws ExcepcionBD {
	 try {
		 this.conn.commit();
		 this.conn.setAutoCommit(true);
	 } catch (SQLException ex) {
		 throw new ExcepcionBD(ex);
	 }
	}
	
	/**
	 * Hace COMMIT de una transacci�n abierta y cierra la conexi�n.
	 * 
	 * @throws ExcepcionBD Si hay alg�n problema.
	 */	
	public void commit_y_cerrar() throws ExcepcionBD {
	 this.commit();
	 this.cerrar();
	}

	/**
	 * Hace COMMIT de una transacci�n abierta y comienza una nueva.
	 * 
	 * @throws ExcepcionBD Si hay alg�n problema.
	 */	
	public void commit_y_reabrir_transaccion() throws ExcepcionBD {
	 // Limpio el cach� por si hay alg�n problema al hacer el commit.
//	 this.cache.limpiar();
	 try {
		 this.conn.commit();
		 // Empiezo una nueva.
		 this.conn.setAutoCommit(false);
	 } catch (SQLException ex) {
		 throw new ExcepcionBD(ex);
	 }
	}
	
	/**
	 * Aborta una transacci�n abierta.
	 * 
	 * @throws ExcepcionBD Si hay alg�n problema.
	 */	
	public void rollback() throws ExcepcionBD {
	 try {
		 this.conn.rollback();
//		 this.cache.limpiar();
		 this.conn.setAutoCommit(true);
	 } catch (SQLException ex) {
		 throw new ExcepcionBD(ex);
	 }
	}

	/**
	 * Aborta una transacci�n abierta y cierra la conexi�n.
	 * 
	 * @throws ExcepcionBD Si hay alg�n problema.
	 */	
	public void rollback_y_cerrar() throws ExcepcionBD {
	 this.rollback();
	 this.cerrar();
	}
	
	/**
	 * Cierra la conexi�n.
	 * 
	 * @throws ExcepcionBD Si hay alg�n problema.
	 */
	public void cerrar() throws ExcepcionBD {
	 try {
		 if (this.db_link_conectado)
			 this.select("SELECT dblink_disconnect()");
		 
		 this.conn.close();
	 } catch (SQLException ex) {
		 throw new ExcepcionBD(ex);
	 }
	}
	
	/**
	 * Aborta la transacci�n actual y comienza una nueva.
	 * 
	 * @throws ExcepcionBD Si hay alg�n problema.
	 */
	public void abortar_y_reabrir_transaccion() throws ExcepcionBD {
	 try {
		 // Termino la actual.
		 this.conn.rollback();
//		 this.cache.limpiar();
		 // Empiezo una nueva.
		 this.conn.setAutoCommit(false);
	 } catch (SQLException ex) {
		 throw new ExcepcionBD(ex);
	 }
	}

	/**
	 * Devuelve un statement que permite generar queries para la base.
	 * 
	 * @throws SQLException Si hay alg�n problema.
	 * @return Un statement.
	 */	
	public Statement createStatement() throws SQLException {
	 return this.conn.createStatement();
	}
	
	/**
	 * Indica de qu� tipo es una conexi�n.
	 * 
	 * @return El tipo de la conexi�n.
	 */
	public TipoConexion obtener_tipo_conexion() {
	 return this.tipo_conexion;
	}
	
	/**
	 * Genera String SQL para INSERT/UPDATE/SELECT de un solo registro.
	 * 
	 * @param lista_campos Lista de campos
	 * @param tabla Tabla en cuestion
	 * @param nuevo Si es nuevo o no
	 * @param id El id num�rico del registro.
	 * @param nombre_atr_id El nombre del atributo id para el registro.
	 * @param condicion Condicion para la busqueda del registro en cuestion.
	 * @param es_select Si se quiere generar el query que busca un registro espec�fico.
	 * @return String con sentencia SQL formateada.
	 */
	static public String generar_string_SQL(HashMapSQL lista_campos, String tabla,
			boolean nuevo, int id, String nombre_atr_id, String condicion,
			boolean es_select) {
	 System.out.println("Estoy entrando al metodo GENERAR STRING SQL!!! ");
			
//	  Si lo quiero grabar con un id en particular, lo agrego a la lista de campos
	 if (id!=0 && nuevo)
		 lista_campos.put(nombre_atr_id, id);
	
	 Object objvalor;
	 String clave, valor, clase;
	 StringBuffer campos= new StringBuffer();
	 StringBuffer valores= new StringBuffer();
	 StringBuffer update= new StringBuffer();
	 StringBuffer select= new StringBuffer();
	 String result= "";
	 int cuenta= 0;

	 Iterator<?> iter= lista_campos.keySet().iterator();
	 while (iter.hasNext()) {
		 clave= (String) iter.next();
		 objvalor= lista_campos.get(clave);
		 if (objvalor==null)
			 valor= "NULL";
		 else {
			 valor= objvalor.toString();
			 clase= objvalor.getClass().getName();
			 if ((clase.compareTo("java.lang.String")==0) ||
					 (clase.compareTo("java.lang.Character")==0) ||
					 (clase.compareTo("java.util.Date")==0) 
					 ) {
				 /* En Postgres 9.1 ahora las barras son, por default, tratadas como caracteres ordinarios,
				  * por lo cual ya no es necesario escaparlas.*/
				 //valor= Sanitizador.escapar_barras(valor);
				 valor= Sanitizador.escapar_comillas(valor);
				 // Le pongo las comillas.
				 valor= "'" +valor+ "'";
			 }
		 }
			
		 if (cuenta>=1) {
			 campos.append(", ");
			 valores.append(", ");
			 update.append(", ");
			 select.append(" AND ");
		 }
		 if (es_select) {
			 select.append(clave);
			 if (valor.equals("NULL")) 
				 select.append(" IS ");
			 else 
				 select.append("= ");
			 
			 select.append(valor);
		 } else if (nuevo) {
			 campos.append(clave);
			 valores.append(valor);
		 } else {
			 update.append(clave);
			 update.append("= ");
			 update.append(valor);
		 }
		
		 cuenta++;
	 }
	 // Armo la cadena con los textos que prepar� antes.
	 if (es_select) {
		 result= "SELECT * FROM "+tabla+" WHERE "+select;
	 } else if (nuevo) {
		 result= "INSERT INTO "+tabla+" ("+campos+") VALUES ("+valores+")";
	 } else {
		 result= "UPDATE "+tabla+" SET "+update+" WHERE ("+condicion+")";
	 }
	
	 // Borro el id de la lista de campos
	 if (id!=0 && nuevo)
		 lista_campos.remove(nombre_atr_id);
	 
	 return result;
	}
	
	/**
	 * Ejecuta el query solicitado.
	 * 
	 * @param query Query SQL.
	 * @throws ExcepcionBD Excepci�n SQL.
	 * @return {@link ResultSet} con el resultado.
	 */
	public ResultSet select(String query) throws ExcepcionBD {
	 return select(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, 0);
	}
	
	/**
	 * Ejecuta el query solicitado.
	 * 
	 * @param query Query SQL.
	 * @param tam_buffer La cantidad de registros que se traen por vez. Debe usarse 0, que
	 * es el valor default, para indicar que se traigan todos. Este par�metro determina el
	 * tama�o del cach� a utilizar por el driver JDBC.
	 * @throws ExcepcionBD Excepci�n SQL.
	 * @return {@link ResultSet} con el resultado.
	 */
	public ResultSet select(String query, int tam_buffer) throws ExcepcionBD {
	 return select(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY,
			 tam_buffer);
	}
	
	/**
	 * Este m�todo sirve para obtener un campo num�rico en base a un select. Es decir, se
	 * espera que query devuelva una �nica fila con un campo num�rico.
	 * 
	 * @param query El query a realizar.
	 * @param msj_error_no_existe El mensaje a devolver si el registro no existe.
	 * @return El campo num�rico que figura en el query.
	 * @throws ExcepcionBD Si hay alg�n problema en la base.
	 * @throws ExcepcionNoExiste Si el query no devuelve ning�n registro.
	 */
	public int select(String query, String msj_error_no_existe)	throws ExcepcionBD,
			ExcepcionNoExiste {
	 int res= 0;
	 ResultSet rs= this.select(query);
	 try {
		 if (rs.next()) {
			 res= rs.getInt(1);
		 } else
			 throw new ExcepcionNoExiste(msj_error_no_existe, true);
	 } catch (SQLException ex) {
		 throw new ExcepcionBD(ex);
	 }
	 return res;
	}

	/**
	 * Ejecuta el query solicitado y devuelve un {@link ResultSet} del tipo indicado, con
	 * el tipo de concurrencia indicado, con el tama�o de buffer indicado.
	 * 
	 * @param query Query SQL.
	 * @param tipo_result_set Uno de <code>ResultSet.TYPE_FORWARD_ONLY, 
	 * ResultSet.TYPE_SCROLL_INSENSITIVE</code>, o
	 * <code>ResultSet.TYPE_SCROLL_SENSITIVE</code>
	 * @param concurrencia_result_set Uno de <code>ResultSet.CONCUR_READ_ONLY</code> o 
	 * <code>ResultSet.CONCUR_UPDATABLE</code>.
	 * @param tam_buffer La cantidad de registros que se traen por vez. Debe usarse 0, que
	 * es el valor default, para indicar que se traigan todos. Este par�metro determina el
	 * tama�o del cach� a utilizar por el driver JDBC.
	 * @return El {@link ResultSet}.
	 * @throws ExcepcionBD Si hay alg�n problema al ejecutar el query.
	 */
	public ResultSet select(String query, int tipo_result_set,
			int concurrencia_result_set, int tam_buffer) throws ExcepcionBD {
	 ResultSet rs;
	
	 try {
		 this.select_stmt= this.conn.createStatement(tipo_result_set,
				 concurrencia_result_set);
		 this.select_stmt.setFetchSize(tam_buffer);
		 rs= this.select_stmt.executeQuery(query);
		 this.select_stmt= null;
	 } catch (SQLException ex) {
		 throw new ExcepcionBD(ex);
	 }

	 return rs;
	}
	
	/**
	 * Cancela el SELECT en progreso (si hay alguno). 
	 * 
	 * @throws ExcepcionBD Si hay alg�n problema.
	 */
	public void cancelar_select() throws ExcepcionBD {
	 if (this.select_stmt!=null)
		 try {
			 this.select_stmt.cancel();
		 } catch (SQLException ex) {
			 throw new ExcepcionBD(ex);
		 }
	}

	/**
	 * Ejecuta comandos en la base, en particular INSERT o UPDATE.
	 * 
	 * @param query es la sentencia SQL a ejecutar en la base.
	 * @throws ExcepcionBD Excepcion SQL 
	 * @return Statement SQL para poder obtener el id autogenerado en caso de haberse
	 * tratado de un INSERT.
	 */
	public Statement ejecutar_sql(String query) throws ExcepcionBD {
	 try {
		 /* Si la conexi�n es s�lo lectura no pueden ejecutarse selects, deletes ni
		  * updates. */
		 if (tipo_conexion.equals(TipoConexion.SOLO_LECTURA) &&
				 query.matches("(?i).*(INSERT|DELETE|UPDATE).*"))
			 throw new ExcepcionBug("No se pueden ejecutar queries de tipo INSERT, " +
			 		"UPDATE o DELETE con una conexi�n de s�lo lectura.");
		 
		 Statement stmt= this.conn.createStatement();
		 stmt.executeUpdate(query);
		 return stmt;
	 } catch (SQLException ex) {
		 throw new ExcepcionBD(ex);
	 }
	}
	
	/**
	 * Ejecuta un comando de UPDATE en la base y devuelve el nro. de filas afectadas por
	 * el UPDATE.
	 * 
	 * @param query Sentencia SQL de UPDATE a ejecutar en la base.
	 * @return El n�mero de filas afectadas por el UPDATE
	 * @throws ExcepcionBD Excepci�n SQL.
	 */
	public int ejecutar_update(String query) throws ExcepcionBD {
	 if (tipo_conexion.equals(TipoConexion.SOLO_LECTURA))
		 throw new ExcepcionBug("No se puede ejecutar un UPDATE en una BD de s�lo " +
		 		"lectura.");
	 try {
		 Statement stmt= this.conn.createStatement();
		 return stmt.executeUpdate(query);
	 } catch (SQLException ex) {
		 throw new ExcepcionBD(ex);
	 }
	}
	
	/**
	 * Obtiene el id actual de una secuencia.
	 * 
	 * @param tabla El nombre de la tabla.
	 * @param stmt Un statement ya creado.
	 * @return El id actual de la secuencia.
	 * @throws ExcepcionBD Si hay alg�n problema con la BD.
	 */
	public int obtener_id(String tabla, Statement stmt) throws ExcepcionBD {
	 return obtener_valor_actual_secuencia(tabla+"_id_seq", stmt);
	}

	/**
	 * Obtiene el id actual de una tabla consultando la tabla de secuencia asociada.
	 * 
	 * @param tabla El nombre de la tabla.
	 * @param nombre_campo_id El nombre del campo num�rico id que es primary key de la
	 * tabla.
	 * @param stmt Un statement ya creado.
	 * @return El id actual de la tabla.
	 * @throws ExcepcionBD Si hay problemas con la BD.
	 */
	public int obtener_id(String tabla, String nombre_campo_id, Statement stmt) throws
			ExcepcionBD {
	 return obtener_valor_actual_secuencia(tabla+"_"+nombre_campo_id+"_seq", stmt);
	}
	
	/**
	 * Obtiene el id actual de una secuencia.
	 * 
	 * @param tabla El nombre de la tabla.
	 * @return El id actual de la secuencia.
	 * @throws ExcepcionBD Si hay alg�n problema con la BD.
	 */
	public int obtener_id(String tabla) throws ExcepcionBD {
	 int id= 0;
	 try {
		 Statement stmt= this.conn.createStatement();
		 id= this.obtener_id(tabla, stmt);
	 } catch (SQLException ex) {
		 throw new ExcepcionBD(ex);
	 }
	 
	 return id;
	}
	
	/**
	 * Obtiene el valor actual de la secuencia pasada por par�metro.
	 * 
	 * @param tabla_secuencia El nombre de la secuencia de la cu�l se quiere conocer el
	 * id.
	 * @param stmt Un statement ya creado.
	 * @return El valor actual de la secuencia.
	 * @throws ExcepcionBD Si hay problemas con la BD.
	 */
	private int obtener_valor_actual_secuencia(String tabla_secuencia, Statement stmt)
			throws ExcepcionBD {
	 int id;
	 String query;
		 
	 query= "SELECT currval('"+tabla_secuencia+"')";
	 try {
		 ResultSet rs= stmt.executeQuery(query);
		 if (rs.next()) {
			 id= rs.getInt("currval");
		 } else {
			 throw new IllegalArgumentException("No se pudo obtener el id de la " +
			 		"sequencia " + tabla_secuencia + ".");
		 }
	 } catch (SQLException ex) {
		 throw new ExcepcionBD(ex);
	 }
			
	 return id;
	}
	
	/**
	 * Obtiene el resultado de un COUNT(*).
	 * 
	 * @param query El query a realizar.
	 * @return El resultado del count.
	 * @throws ExcepcionBD Si hay alg�n problema con la BD.
	 */
	public int obtener_count(String query) throws ExcepcionBD {
	 int count;
		
	 try {
		 ResultSet rs= this.select(query);
		 if (rs.next()) {
			 count= rs.getInt(1);
		 } else {
			 throw new ExcepcionBD("No se pudo obtener el resultado de la cuenta.");
		 }
	 } catch (SQLException ex) {
		 throw new ExcepcionBD(ex);
	 }
		
	 return count;
	}
	
//	/**
//	 * Arma la cl�usula IN para un query SQL, poniendo en �l a los ids de los {@link Enum}
//	 * par�metro.
//	 * 
//	 * @param <T> El tipo de los Enum que se va a incluir en el IN. 
//	 * @param enums Los {@link Enum} a incluir en el IN. Notar que deben implementar
//	 * {@link EnumMECON}. 
//	 * @return �dem.
//	 */
//	static public <T extends EnumMECON> String armar_clausula_in(T... enums) {
//	 return armar_clausula_in_de_objetos(enums);
//	}

//	/**
//	 * Arma la cl�usula IN para un query SQL, poniendo en �l a los strings pasados como
//	 * par�metro.
//	 * 
//	 * @param elementos Los strings a incluir en el IN.
//	 * @return �dem.
//	 */
//	static public String armar_clausula_in(String... elementos) {
//	 return armar_clausula_in_de_objetos(elementos);
//	}
	
//	/**
//	 * Arma la cl�usula IN de objetos para un query SQL, poniendo en �l a ids de los
//	 * {@link Enum} o al toString() de los objetos pasados como par�metro.
//	 * 
//	 * @param elementos Los objetos a incluir en el IN. 
//	 * @return �dem.
//	 */
//	static public String armar_clausula_in_de_objetos(Object[] elementos) {
//	 StringBuffer res= new StringBuffer();
//	 int cant= elementos.length;
//	 res.append("IN (");
//	 int i= 0;
//	 
//	 /* Si me pasan un arreglo de EnumMECON armo la cl�usula con los ids de las
//	  * constantes. Sino asumo que el arreglo es de tipo desconocido y armo la cl�usula 
//	  * appendeando los toString() de los objetos. */
//	 if (elementos instanceof EnumMECON[]) {
//		 EnumMECON[] enums= (EnumMECON[]) elementos;
//		 for (EnumMECON enum1 : enums) {
//			 res.append(enum1.obtener_id());
//			 if (i<cant-1)
//				 res.append(", ");
//			 
//			 i++;
//		 }
//	 } else {
//		 for (Object elem : elementos) {
//			 res.append('\'');
//			 res.append(elem.toString());
//			 res.append('\'');
//			 if (i<cant-1)
//				 res.append(", ");
//			 
//			 i++;
//		 }
//	 }
//	 
//	 res.append(')');
//	 return res.toString();
//	}
	
//	/**
//	 * Devuelve el cach� de la conexi�n.
//	 * 
//	 * @return �dem.
//	 */
//	public Cache cache() {
//	 return this.cache;
//	}

	/**
	 * Arma un query de tipo SELECT.
	 * 
	 * @param tabla La tabla de la cual se quieren obtener los datos.
	 * @param cols Las columnas que se quieren obtener.
	 * @param condicion La condici�n para filtrar las filas.
	 * @return El query.
	 */
	public static String generar_string_busqueda(String tabla, String cols,
			String condicion) {
	 return "SELECT " + cols + " FROM " + tabla + " WHERE (" + condicion + ")";
	}
	
	/**
	 * Wrapper del m�todo prepareStatement(String) de la clase {@link Connection}.
	 * 
	 * @param query Query a preparar.
	 * @return PreparedStatement relacionado con la {@link Connection} de este objeto.
	 * @throws SQLException Si no se puede parsear el query pasado como par�metro.
	 */
	public PreparedStatement obtener_prepared_statement(String query) throws
			SQLException {
	 return conn.prepareStatement(query);
	}
	
//	/**
//	 * Wrapper del m�todo getLargeObjectAPI() de la clase {@link Connection}.
//	 * 
//	 * @return LargeObjectManager relacionado con la {@link Connection} de este objeto.
//	 * @throws SQLException Si hay alg�n problema creando el objeto.
//	 */
//	public LargeObjectManager obtener_large_object_manager() throws SQLException {
//	 return ((org.postgresql.PGConnection) conn).getLargeObjectAPI();
//	}
	
	/**
	 * Hace que la conexi�n a la BD sea de lectoescritura.
	 * Esto debe usarse s�lo en los contados casos en los que es necesario hacer alg�n
	 * mantenimiento en la BD de lectura.
	 */
	public void marcar_como_lectoescritura() {
	 this.tipo_conexion= TipoConexion.LECTOESCRITURA;
	}
	
	/**
	 * Este m�todo es el inverso a {@link #marcar_como_lectoescritura()}, y debe usarse
	 * una vez finalizadas las tareas de mantenimiento.
	 */
	public void marcar_como_solo_lectura() {
	 this.tipo_conexion= TipoConexion.SOLO_LECTURA;
	}
	
	/**
	 * Indica si la conexi�n est� en estado v�lido. T�picamente se puede usar para
	 * detectar si la transacci�n actual se encuentra anulada.
	 * Notar que si se hace un <code>rollback()</code> la conexi�n sigue siendo v�lida.
	 * 
	 * @return <code>true</code> ssi est� en condiciones de procesar queries.
	 */
	public boolean es_valida() {
	 boolean valida= true;
	 try {
		 ResultSet rs= this.select("SELECT 1");
		 if (!rs.next())
			 valida= false;
	 } catch (ExcepcionBD ex) {
		 valida= false;
	 } catch (SQLException ex) {
		 valida= false;
	 }
	 
	 return valida;
	}

}
