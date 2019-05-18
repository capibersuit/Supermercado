package ar.gov.chris.server.clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.gov.chris.client.clases.Super;
import ar.gov.chris.server.bd.ConexionBD;
import ar.gov.chris.server.excepciones.ExcepcionBD;
import ar.gov.chris.server.excepciones.ExcepcionBug;
import ar.gov.chris.server.excepciones.ExcepcionNoExiste;

public class FabricaSuper extends FabricaEnumeradosDeBDSinValoresEnum<Super> {
	final private static String TABLA= "supermercados";
	final private static String COL_DESC= "empresa";
	@Override
	protected Class<Super> obtener_clase() {
		return Super.class;
	}
	@Override
	protected String obtener_col_desc() {
		return COL_DESC;
	}
	@Override
	protected String obtener_tabla() {
		return TABLA;
	}
	
	@Override
	public List<Super> cargar_todos(ConexionBD con, boolean solo_habilitados) 
	throws ExcepcionBD {
		List<Super> ret= new ArrayList<Super>();
		String habilitado= solo_habilitados?"habilitado":"true";
		String query= "SELECT id, empresa, "+obtener_col_desc()+
			" FROM "+obtener_tabla()+" WHERE "+habilitado+" ORDER BY "+obtener_col_desc();
		ResultSet rs= con.select(query);
		try {
			while (rs.next()) {
				Super tipo= new Super(
						rs.getInt("id"), 
						rs.getString(obtener_col_desc()));
				ret.add(tipo);
			} 
		} catch (SQLException e) {
			throw new ExcepcionBug("Query incorrecto: " + query);
		} 
		return ret;
	}

	@Override
	public Super cargar_enumerado(ConexionBD con, int id) 
	throws ExcepcionNoExiste, ExcepcionBD {
		
		String query= "SELECT id, "+obtener_col_desc()+
			" FROM "+obtener_tabla() + " WHERE id= " + id;
		ResultSet rs= con.select(query);
		try {
			Super supermercado;
			if (rs.next()) {
				supermercado= new Super(
			    		rs.getInt("id"), 
			    		rs.getString(obtener_col_desc()));
			} else {
				throw new ExcepcionNoExiste();
			}
			return supermercado;
		} catch (SQLException e) {
			throw new ExcepcionBug("Query incorrecto: " + query);
		}
	}
}	

