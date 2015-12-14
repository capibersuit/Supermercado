package ar.gov.chris.server.proxies_pantallas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

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


	@Override
	public Set<DatosProducto> buscar_productos() throws GWT_ExcepcionBD{
		Set <DatosProducto> datos_conj= new HashSet<DatosProducto>();
		ConexionBD con= this.obtener_transaccion();

		
		try {
			ResultSet rs= con.select("SELECT * FROM productos");
			
			while (rs.next()) {
				DatosProducto datos= new DatosProducto();

				datos.setNombre(rs.getString("nombre"));
				datos.setPrecio(rs.getFloat("precio"));
				datos_conj.add(datos);
			}
			
		} catch (ExcepcionBD e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		return datos_conj;
	}

}
