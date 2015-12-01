package ar.gov.chris.server.proxies_pantallas;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import ar.gov.chris.client.datos.DatosProducto;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionBD;
import ar.gov.chris.client.interfaces.ProxyPantallaProductos;
import ar.gov.chris.server.clases.Producto;
import ar.gov.chris.server.excepciones.ExcepcionBD;
import ar.gov.chris.server.bd.ConexionBD;
import ar.gov.chris.server.bd.PoolDeConexiones;

@SuppressWarnings("serial")
public class ProxyPantallaProductosImpl extends ProxyPantallaCHRISImpl implements
ProxyPantallaProductos {
	
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

	
	public void agregar_producto(DatosProducto datos_prod) throws GWT_ExcepcionBD{
		
		ConexionBD con= this.obtener_transaccion();
		boolean commit= false;

		try {
			
			
			Producto prod= new Producto(datos_prod.getNombre(), datos_prod.getPrecio());
			prod.grabar(con);
			commit= true;
		
		} catch (ExcepcionBD e) {
			e.printStackTrace();
		} finally {
			this.cerrar_transaccion(con, commit);
		}
		
		
	}

}
