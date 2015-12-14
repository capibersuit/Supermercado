package ar.gov.chris.server.proxies_pantallas;

import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import ar.gov.chris.client.datos.DatosLista;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionBD;
import ar.gov.chris.client.interfaces.ProxyPantallaListas;
import ar.gov.chris.server.bd.ConexionBD;
import ar.gov.chris.server.bd.PoolDeConexiones;
import ar.gov.chris.server.clases.Lista;
import ar.gov.chris.server.clases.Producto;
import ar.gov.chris.server.excepciones.ExcepcionBD;

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
}

