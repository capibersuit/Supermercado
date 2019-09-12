package ar.gov.chris.server.proxies_pantallas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import ar.gov.chris.client.datos.DatosReprtePrecios;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionBD;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionNoAutorizado;
import ar.gov.chris.client.interfaces.ProxyPantallaPrecios;
import ar.gov.chris.server.bd.ConexionBD;
import ar.gov.chris.server.bd.PoolDeConexiones;
import ar.gov.chris.server.excepciones.ExcepcionBD;
import ar.gov.chris.server.genericos.contexto.ContextoDeSeguridad;

@SuppressWarnings("serial")
public class ProxyPantallaPreciosImpl  extends ProxyPantallaCHRISImpl implements
ProxyPantallaPrecios {

	public void init(ServletConfig config) throws ServletException {
		this.unica_conexion= false;
		try {
			int cant_conexiones= 2;
			super.init(config, false, cant_conexiones, PoolDeConexiones.SIN_MAXIMO, true,
					1, 10);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			throw new ServletException(ex);
		}
	}

	@Override
	public Set<DatosReprtePrecios> buscar_precios(String desde, String hasta)  throws GWT_ExcepcionBD, GWT_ExcepcionNoAutorizado{
		Set <DatosReprtePrecios> datos_conj= new HashSet<DatosReprtePrecios>();
		ConexionBD con= this.obtener_transaccion();
		boolean commit= true;	

		try {
			
			
			ContextoDeSeguridad cs = autenticar_y_autorizar(con, "zarazz");

			
			String query= "select p.id, nombre as producto, string_agg(rlp.precio::text,'_' order by fecha) as precios, string_agg(rlp.precio_x_kg_venta_al_peso::text,'_' order by fecha) as precios_x_kg, string_agg(l.fecha::Date::text, '_' order by fecha) as fechas  from rel_listas_productos rlp, productos p, listas l"
					+ " where l.id= rlp.id_compra and p.id= rlp.id_prod and l.fecha > '" + desde +"'and l.fecha < '" + hasta +"' group by nombre, p.id";
					
			ResultSet rs= con.select(query);
			while (rs.next()) {
				DatosReprtePrecios datos= new DatosReprtePrecios();

				datos.setId_prod(rs.getString("id"));
				datos.setNombre_prod(rs.getString("producto"));
				datos.setPrecios(rs.getString("precios"));
				datos.setPrecios_x_kg(rs.getString("precios_x_kg"));
				datos.setFechas(rs.getString("fechas"));
				datos_conj.add(datos);
			}

		} catch (ExcepcionBD e) {
			throw new GWT_ExcepcionBD(e);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new GWT_ExcepcionBD(e);
		} catch (GWT_ExcepcionNoAutorizado e) {
			throw new GWT_ExcepcionNoAutorizado(e);
		} finally {
			this.cerrar_transaccion(con, commit);
		}
		return datos_conj;
	}

//	private float poner_dos_decimales(float precio_total) {
//		return (float) (Math.round(precio_total*100)/100.0d);
//	}
//	
//	private String poner_dos_decimales(String precio_total) {
//		
//		float f = Float.parseFloat(precio_total); 
//		float precio_redondeado= (float) (Math.round(f*100)/100.0d);
//		return String.valueOf(precio_redondeado);
//	}
	
	
}
