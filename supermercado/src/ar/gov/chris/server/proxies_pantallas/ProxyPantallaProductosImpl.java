package ar.gov.chris.server.proxies_pantallas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import ar.gov.chris.client.datos.DatosProducto;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionBD;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionNoExiste;
import ar.gov.chris.client.interfaces.ProxyPantallaProductos;
import ar.gov.chris.server.clases.Lista;
import ar.gov.chris.server.clases.Producto;
import ar.gov.chris.server.excepciones.ExcepcionBD;
import ar.gov.chris.server.excepciones.ExcepcionNoExiste;
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
	public void borrar_producto(String nombre) throws GWT_ExcepcionBD {
		
		ConexionBD con= this.obtener_transaccion();
		boolean commit= false;

		try {
			
			
			Producto prod= new Producto(con, nombre);
			prod.borrar(con);
			commit= true;
		
		} catch (ExcepcionBD e) {
			e.printStackTrace();
		} catch (ExcepcionNoExiste e) {
			e.printStackTrace();
		} finally {
			this.cerrar_transaccion(con, commit);
		}		
	}
	
	@Override
	public void borra_producto_de_lista(String nombre, int id_compra) throws GWT_ExcepcionBD {
		ConexionBD con= this.obtener_transaccion();
		boolean commit= false;	
		
		
			try {
				Producto prod= new Producto(con, nombre);
				
				con.ejecutar_sql("DELETE FROM rel_listas_productos WHERE id_compra = " + id_compra + " AND id_prod = "+ prod.getId());
				
				commit= true;
				
			} catch (ExcepcionBD e) {
				e.printStackTrace();
			} catch (ExcepcionNoExiste e) {
				e.printStackTrace();
			}finally {
				this.cerrar_transaccion(con, commit);
			}
		

	}



	@Override
	public Set<DatosProducto> buscar_productos() throws GWT_ExcepcionBD{
		Set <DatosProducto> datos_conj= new HashSet<DatosProducto>();
		ConexionBD con= this.obtener_transaccion();
		boolean commit= true;	

		
		try {
			ResultSet rs= con.select("SELECT * FROM productos ORDER BY nombre");
			
			while (rs.next()) {
				DatosProducto datos= new DatosProducto();

				datos.setId(rs.getInt("id"));
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
		} finally {
			this.cerrar_transaccion(con, commit);
		}

		
		
		return datos_conj;
	}
	
	@Override
	public Set<DatosProducto> buscar_productos_lista(int id_lista) throws GWT_ExcepcionBD, GWT_ExcepcionNoExiste{
		Set <DatosProducto> datos_conj= new HashSet<DatosProducto>();
		ConexionBD con= this.obtener_transaccion();
		
		boolean commit= true;	


//		Lista l = new Lista(comentario, fecha);
		
		try {
			ResultSet rs= con.select("SELECT * FROM rel_listas_productos where id_compra = " + id_lista);
			
			while (rs.next()) {
				
				Producto p = new Producto(con, rs.getInt("id_prod"));
				DatosProducto datos= new DatosProducto();
				
				datos.setId(rs.getInt("id_prod"));
				datos.setNombre(p.getNombre());
				datos.setPrecio(rs.getFloat("precio"));
				datos.setCantidad(rs.getInt("cant"));
				datos_conj.add(datos);
			}
			
		} catch (ExcepcionBD e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExcepcionNoExiste e) {
			throw new GWT_ExcepcionNoExiste(e);
		} finally {
			this.cerrar_transaccion(con, commit);
		}

		
		
		return datos_conj;
	}


	@Override
	public void agregar_producto_a_lista(DatosProducto datos_prod, int id_compra, int cant) throws GWT_ExcepcionBD {
		boolean commit= false;
		ConexionBD con = this.obtener_transaccion();

		try {
			Producto prod= new Producto(con, datos_prod.getNombre());
			int id_prod= prod.getId();
			
			con.ejecutar_sql("INSERT INTO rel_listas_productos (id_compra, id_prod, cant, precio) VALUES (" +
			id_compra+"," + id_prod +"," +cant  +","+ prod.getPrecio() + ")");
			
			commit= true;

		} catch (ExcepcionBD e) {
			e.printStackTrace();
		}catch (ExcepcionNoExiste e) {
			e.printStackTrace();
		} finally {
			this.cerrar_transaccion(con, commit);
		}
		
		
	}


	@Override
	public void actualizar_producto(DatosProducto datos_prod) throws GWT_ExcepcionBD {
		boolean commit= false;
		ConexionBD con = this.obtener_transaccion();

		try {
			Producto prod= new Producto(con, datos_prod.getId());
			int id_prod= prod.getId();
			String query= "UPDATE productos SET precio= "+ datos_prod.getPrecio() +", nombre= '"+ datos_prod.getNombre() + "' WHERE id = " + id_prod;
//			con.ejecutar_sql("UPDATE productos SET precio= "+ prod.getPrecio() +", nombre= "
//			+ prod.getNombre() + " WHERE id = " + id_prod);
			con.ejecutar_update(query);
			commit= true;

		} catch (ExcepcionBD e) {
			e.printStackTrace();
		}catch (ExcepcionNoExiste e) {
			e.printStackTrace();
		} finally {
			this.cerrar_transaccion(con, commit);
		}		
	}
	
	@Override
	public void actualizar_producto_a_lista(DatosProducto datos_prod, String id_compra) throws GWT_ExcepcionBD {
		boolean commit= false;
		ConexionBD con = this.obtener_transaccion();

		try {
			Producto prod= new Producto(con, datos_prod.getId());
//			int id_prod= prod.getId();
			
			con.ejecutar_sql("UPDATE rel_listas_productos SET precio= "+ datos_prod.getPrecio() 
					+" WHERE id_compra = " +id_compra+" AND id_prod= " + prod.getId());
			
			verificar_si_cambiar_precio_global(con, datos_prod, id_compra);
			
			commit= true;

		} catch (ExcepcionBD e) {
			e.printStackTrace();
		}catch (ExcepcionNoExiste e) {
			e.printStackTrace();
		} finally {
			this.cerrar_transaccion(con, commit);
		}
		
		
	}


	private void verificar_si_cambiar_precio_global(ConexionBD con,
			DatosProducto datos_prod, String id_compra) throws ExcepcionBD {
			
		Date fecha = null;
		Date fecha2 = null;
		ResultSet rs= con.select("select fecha from listas where id =" + id_compra);
		ResultSet rs2= con.select("select max(fecha) from listas");
		try {
			
			if(rs.next()) 
			 fecha= rs.getDate("fecha");
			if(rs2.next()) 
			 fecha2= rs2.getDate("max");
			
			if(!fecha2.after(fecha)) {
				String query= "UPDATE productos SET precio= "+ datos_prod.getPrecio() + " WHERE id = " + datos_prod.getId();

				con.ejecutar_update(query);
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}




	

}
