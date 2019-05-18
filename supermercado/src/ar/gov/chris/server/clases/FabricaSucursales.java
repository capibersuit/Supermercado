package ar.gov.chris.server.clases;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.gov.chris.client.clases.Sucursal;
import ar.gov.chris.server.bd.ConexionBD;
import ar.gov.chris.server.excepciones.ExcepcionBD;
import ar.gov.chris.server.excepciones.ExcepcionBug;
import ar.gov.chris.server.excepciones.ExcepcionNoExiste;

public class FabricaSucursales extends FabricaEnumeradosDeBDSinValoresEnum<Sucursal> {
	private static final String TABLA= "sucursales";
	private static final String COL_DESC= "direccion";
	
	@Override
	public List<Sucursal> cargar_todos(ConexionBD con, boolean solo_habilitados) 
	throws ExcepcionBD {
		List<Sucursal> ret= new ArrayList<Sucursal>();
		String habilitado= solo_habilitados?"habilitado":"true";
		String query= "SELECT id, id_super, localidad, "+obtener_col_desc()+
			" FROM "+obtener_tabla()+" WHERE "+habilitado+" ORDER BY "+obtener_col_desc();
		ResultSet rs= con.select(query);
		try {
			while (rs.next()) {
				Sucursal tipo= new Sucursal(
						rs.getInt("id"), 
						rs.getString(obtener_col_desc()),
						rs.getString("localidad"), 
						rs.getInt("id_super"));
				ret.add(tipo);
			} 
		} catch (SQLException e) {
			throw new ExcepcionBug("Query incorrecto: " + query);
		} 
		return ret;
	}
	
	@Override
	public Sucursal cargar_enumerado(ConexionBD con, int id) 
	throws ExcepcionNoExiste, ExcepcionBD {
		
		String query= "SELECT id, id_super, "+obtener_col_desc()+
			" FROM "+obtener_tabla() + " WHERE id= " + id;
		ResultSet rs= con.select(query);
		try {
			Sucursal tipo_problema;
			if (rs.next()) {
				tipo_problema= new Sucursal(
			    		rs.getInt("id"), 
			    		rs.getString(obtener_col_desc()), 
						rs.getString("localidad"), 
			    		rs.getInt("id_super"));
            } else { 
				throw new ExcepcionNoExiste();
			  }
			return tipo_problema;
		} catch (SQLException e) {
			throw new ExcepcionBug("Query incorrecto: " + query);
		  }
	}
	
	@Override
	protected Class<Sucursal> obtener_clase() {
		return Sucursal.class;
	}
	
	@Override
	protected String obtener_col_desc() {
		return COL_DESC;
	}
	@Override
	protected String obtener_tabla() {
		return TABLA;
	}
}

