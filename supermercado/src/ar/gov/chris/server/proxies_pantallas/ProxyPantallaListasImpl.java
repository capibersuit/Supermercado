package ar.gov.chris.server.proxies_pantallas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import ar.gov.chris.client.datos.DatosLista;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionBD;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionNoExiste;
import ar.gov.chris.client.interfaces.ProxyPantallaListas;
import ar.gov.chris.server.bd.ConexionBD;
import ar.gov.chris.server.bd.PoolDeConexiones;
import ar.gov.chris.server.clases.Lista;
import ar.gov.chris.server.excepciones.ExcepcionBD;
import ar.gov.chris.server.excepciones.ExcepcionNoExiste;

@SuppressWarnings("serial")
public class ProxyPantallaListasImpl extends ProxyPantallaCHRISImpl implements
ProxyPantallaListas {
	
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

	/**
	 * @throws GWT_ExcepcionBD 
	 * 
	 */
	
	@Override
	public void agregar_lista(DatosLista datos_list) throws GWT_ExcepcionBD {
		ConexionBD con= this.obtener_transaccion();
		boolean commit= false;

		try {
			
			
			Lista l= new Lista(datos_list.getComentario(), new Date());
			l.grabar(con);
			commit= true;
		
		} catch (ExcepcionBD e) {
			e.printStackTrace();
			throw new GWT_ExcepcionBD(e.getMessage());
		} finally {
			this.cerrar_transaccion(con, commit);
		}
				
	}

	@Override
	public Set<DatosLista> buscar_listas() throws GWT_ExcepcionBD{
		Set <DatosLista> datos_conj= new HashSet<DatosLista>();
		ConexionBD con= this.obtener_transaccion();
		Boolean commit= false;
		
		try {
			ResultSet rs= con.select("SELECT * FROM listas");
			
			while (rs.next()) {
				DatosLista datos= new DatosLista();
				datos.setId(rs.getInt("id"));
				datos.setComentario(rs.getString("comentario"));
				datos.setFecha(rs.getDate("fecha"));
				datos_conj.add(datos);
			}
			commit= true;
			
		} catch (ExcepcionBD e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.cerrar_transaccion(con, commit);
		}

		
		
		return datos_conj;
	}

	@Override
	public void existe_lista(int id_compra) throws GWT_ExcepcionBD, GWT_ExcepcionNoExiste {
		ConexionBD con;
//		Boolean existe= null;
		Boolean commit= false;
		con = this.obtener_transaccion();

		try {
			Lista l = new Lista(con, id_compra);
//			existe= true;
//			existe= l  != null;

		} catch (ExcepcionBD e) {
				throw new GWT_ExcepcionBD(e);
		} catch (ExcepcionNoExiste e) {
			throw new GWT_ExcepcionNoExiste(e);
			} finally {
				this.cerrar_transaccion(con, commit);
			}
	}
}

