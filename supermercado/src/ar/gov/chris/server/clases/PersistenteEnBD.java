package ar.gov.chris.server.clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.HashMap;
//import java.util.Map;

import ar.gov.chris.server.bd.ConexionBD;
import ar.gov.chris.server.bd.HashMapSQL;
import ar.gov.chris.server.excepciones.*;

public abstract class PersistenteEnBD {
	
//	private static final String SE_MODIFICO_EN= "Se modific� en ";
//	private static final String SE_QUITO_DE= "Se quit� de ";
//	private static final String SE_AGREGO_A= "Se agreg� a ";
	/* El id num�rico del objeto, en la BD. 
	 * Debe ser la primary key del objeto en la BD. */
	protected int id;
	/* El nombre del atributo id en la tabla del objeto. Por default es "id". */
	protected String nombre_atributo_id= "id";
	/* vivo indica que el objeto no est� borrado en la base.
	 */
//	private boolean vivo= true;
	/** es_nuevo significa que no existe en la base.
	 */
//	private boolean es_nuevo= true;
	/** esta_modificado significa que la copia en memoria
	 * difiere de lo que figura en la base.
	 */
//	private boolean esta_modificado= false;
	/** En este Map se guardan los valores de los campos
	 * y las modificaciones que se les van haciendo de manera
	 * que al grabar se pueda saber qu� cambi�.
	 */
//	private Map<String, Object> campos_modificados=
//		new HashMap<String, Object>();

	/** Constructor sin par�metros.
	 */
	public PersistenteEnBD() {
	}
	
//	public int grabar(ar.gov.chris.server.bd.ConexionBD con, 
//			ar.gov.chris.server.bd.HashMapSQL lista_campos, Map<String, Object> campos_cambiados,
//			String tabla, String tabla_secuencia, boolean nuevo, String condicion,
//			int id, int id_servicio, String datos, Map<String, String> datos_id,
//			boolean solo_si_no_existe, boolean loguear) throws ExcepcionBD {
//		 
	
	public int grabar(ConexionBD con, HashMapSQL lista_campos, String tabla,
			String tabla_secuencia,  boolean nuevo, String condicion,
			int id, boolean solo_si_no_existe ) throws ExcepcionBD {
		 
		 String query; 
		 if (solo_si_no_existe) {
			 // Genero el query para ver si el registro existe.
			 query= ConexionBD.generar_string_SQL(lista_campos, tabla, nuevo, 
					 id, nombre_atributo_id, condicion, true);
			 ResultSet rs= con.select(query);
			 
			 // Si ya existe, termino
			 try {
				if (rs.next()) {
					 id= rs.getInt(nombre_atributo_id);
					 return id;
				 }
			} catch (SQLException ex) {
				throw new ExcepcionBD(ex);
			}
		 }

		 // Genero el string SQL.
		 query= ConexionBD.generar_string_SQL(lista_campos, tabla, nuevo, id,
				nombre_atributo_id, condicion, false);
		 
		 // Grabo.
		 Statement stmt= con.ejecutar_sql(query);

		 // Si es nuevo y el id es 0, obtengo su id.
	 	 if (nuevo && id == 0) {
			id= con.obtener_id(tabla_secuencia, nombre_atributo_id , stmt);
		 }

		 return id;
		}
	
	/** Carga desde la BD los datos que le corresponden al objeto desde
	 * su perspectiva de PersistenteEnBD.
	 * @param rs El resultado de la consulta.
	 * @throws ExcepcionBD Si hay alg�n problema con la BD.
	 */
	protected void cargar_persistente(ResultSet rs) throws ExcepcionBD {
	 Object baja_fisica;
	 
	 try {
		 this.id= rs.getInt(nombre_atributo_id);
		 baja_fisica= rs.getObject("baja_fisica");
		 if (baja_fisica!=null && ((Boolean) baja_fisica).booleanValue())
			 throw new ExcepcionBug("Se intenta cargar un registro dado de baja.");
	 } catch (SQLException ex) {
		 throw new ExcepcionBD(ex);
	 }
	}
	
	/** Carga desde la BD los datos que le corresponden a un objeto sin baja
	 * f�sica desde su perspectiva de PersistenteEnBD.
	 * @param rs El resultado de la consulta.
	 * @throws ExcepcionBD Si hay alg�n problema con la BD.
	 */
	protected void cargar_persistente_sin_baja_fisica(ResultSet rs) throws ExcepcionBD {
	 try {
		 this.id= rs.getInt(nombre_atributo_id);
	 } catch (SQLException ex) {
		 throw new ExcepcionBD(ex);
	 }
	}

}
