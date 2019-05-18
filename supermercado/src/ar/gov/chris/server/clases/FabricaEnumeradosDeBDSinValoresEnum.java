package ar.gov.chris.server.clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.gov.chris.client.clases.EnumeradoDeBDSinValoresEnum;
import ar.gov.chris.server.bd.ConexionBD;
import ar.gov.chris.server.excepciones.ExcepcionBD;
import ar.gov.chris.server.excepciones.ExcepcionBug;
import ar.gov.chris.server.excepciones.ExcepcionNoExiste;

public abstract class FabricaEnumeradosDeBDSinValoresEnum <T extends EnumeradoDeBDSinValoresEnum> {
	
	/** Obtiene la tabla del enumerado que implementa esta clase.
	 * @return String con la tabla.
	 */
	protected abstract String obtener_tabla();
	
	/** Obtiene la columna de descripción/nombre del enumerado que implementa 
	 * esta clase.
	 * @return String con la columna.
	 */
	protected abstract String obtener_col_desc();
	
	/**Obtiene la clase que corresponde a la Fábrica que implementa esta clase.
	 * @return Si es una FabricaSanguches, devolverá Sanguche.class .
	 */
	protected abstract Class<T> obtener_clase();

	/** Fabrica un Tipo de Problema de problema según si id en la tabla tipos_problema.
	 * 
	 * @param con Conexión contra la BD.
	 * @param id Id del tipo problema en la BD.
	 * @return El Enumerado creado.
	 * @throws ExcepcionNoExiste Si no hay ninguna entrada con dicho id.
	 * @throws ExcepcionBD Si no se puede conectar a la BD
	 */
	public T cargar_enumerado(ConexionBD con, int id) 
	throws ExcepcionNoExiste, ExcepcionBD {
		String query= "SELECT " + obtener_col_desc() + " FROM " + obtener_tabla() + 
		" WHERE id=" + id;
		ResultSet rs= con.select(query);
		try {
			T enumerado= obtener_clase().newInstance();
			if (rs.next()) {
				enumerado.cambiar_id(id);
				enumerado.cambiar_descripcion(rs.getString(obtener_col_desc()));
			} else {
				throw new ExcepcionNoExiste();
			}
			return enumerado;
		} catch (SQLException e) {
			throw new ExcepcionBug("Query incorrecto: " + query);
		} catch (InstantiationException e) {
			throw new ExcepcionBug(e.getMessage());
		} catch (IllegalAccessException e) {
			throw new ExcepcionBug(e.getMessage());
		}
	}

	/** Fabrica un enumerado web según su nombre en la tabla correspondiente.
	 * 
	 * @param con Conexión a la BD.
	 * @param nombre Valor del campo "nombre" en la BD.
	 * @return El Enumerado creado.
	 * @throws ExcepcionNoExiste Si no hay ninguna entrada con dicho nombre.
	 * @throws ExcepcionBD Si no se puede conectar a la BD
	 */
	public T cargar_enumerado(ConexionBD con, String nombre) 
	throws ExcepcionNoExiste, ExcepcionBD {
		String query= "SELECT id FROM " + obtener_tabla() + 
			" WHERE " + obtener_col_desc() + "='" + nombre + "'";
		ResultSet rs= con.select(query);
		try {			
			T enumerado= obtener_clase().newInstance();
			if (rs.next()) {
				enumerado.cambiar_id(rs.getInt("id"));
				enumerado.cambiar_descripcion(nombre);
			} else {
				throw new ExcepcionNoExiste();
			}
			return enumerado;
		} catch (SQLException e) {
			throw new ExcepcionBug("Query incorrecto: " + query);
		} catch (InstantiationException e) {
			throw new ExcepcionBug(e.getMessage());
		} catch (IllegalAccessException e) {
			throw new ExcepcionBug(e.getMessage());
		}
	}	
	
	/** Fabrica un set de enumerados web con todos los enumerados disponibles en 
	 * la tabla correspondiente.
	 * @param con Conexión a la BD.
	 * @param solo_habilitados Indica si solo se cargarán los enumerados cuya columna
	 * 'habilitado' sea true.
	 * @return List de EnumeradoWeb con todos los enumerados disponibles.
	 * @throws ExcepcionBD Si no se puede conectar a la BD
	 * @throws IllegalAccessException Si no se puede acceder al constructor de 
	 * dicha clase. 
	 * @throws InstantiationException Si no se puede instanciar dicha clase. 
	 */
	public List<T> cargar_todos( ConexionBD con, boolean solo_habilitados) 
	throws ExcepcionBD, InstantiationException, IllegalAccessException {
		String habilitado= solo_habilitados?"habilitado":"true";
		String query= "SELECT id, "+obtener_col_desc()+
			" FROM "+obtener_tabla()+" WHERE "+habilitado+" ORDER BY " +obtener_col_desc();
		ResultSet rs= con.select(query);
		List<T> ret= new ArrayList<T>();
		try {
			while (rs.next()) {
				T enumerado= obtener_clase().newInstance();
				enumerado.cambiar_id(rs.getInt("id"));
				enumerado.cambiar_descripcion(rs.getString(obtener_col_desc()));
				ret.add(enumerado);
			} 
		} catch (SQLException e) {
			throw new ExcepcionBug("Query incorrecto: " + query);
		} 
		return ret;
	}	
}
