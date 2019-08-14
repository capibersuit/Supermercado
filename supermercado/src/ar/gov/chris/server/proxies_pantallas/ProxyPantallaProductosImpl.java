package ar.gov.chris.server.proxies_pantallas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import ar.gov.chris.client.datos.DatosListaProdCompleta;
import ar.gov.chris.client.datos.DatosProducto;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionBD;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionNoAutorizado;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionNoExiste;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionYaExiste;
import ar.gov.chris.client.interfaces.ProxyPantallaProductos;
import ar.gov.chris.server.clases.Lista;
import ar.gov.chris.server.clases.Producto;
import ar.gov.chris.server.excepciones.ExcepcionBD;
import ar.gov.chris.server.excepciones.ExcepcionNoExiste;
import ar.gov.chris.server.excepciones.ExcepcionYaExiste;
import ar.gov.chris.server.genericos.contexto.ContextoDeSeguridad;
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


	public DatosProducto agregar_producto(DatosProducto datos_prod) throws GWT_ExcepcionBD, GWT_ExcepcionYaExiste{

		ConexionBD con= this.obtener_transaccion();
		boolean commit= false;

		try {


			Producto prod= new Producto(datos_prod.getNombre(), datos_prod.getPrecio());
			prod.setId_super(datos_prod.getId_super());
			prod.setPrecio_kg(datos_prod.getPrecio_kg());
			prod.grabar(con, true);
			datos_prod.setId(prod.getId());
			commit= true;

		} catch (ExcepcionBD e) {
			throw new GWT_ExcepcionBD(e);
		} catch (ExcepcionYaExiste e) {
			throw new GWT_ExcepcionYaExiste(e);
		} finally {
			this.cerrar_transaccion(con, commit);
		}
		return datos_prod;


	}

	@Override
	public void borrar_producto(String nombre) throws GWT_ExcepcionBD, GWT_ExcepcionNoExiste {

		ConexionBD con= this.obtener_transaccion();
		boolean commit= false;

		try {
			Producto prod= new Producto(con, nombre);
			prod.borrar(con);
			commit= true;

		} catch (ExcepcionBD e) {
			throw new GWT_ExcepcionBD(e);
		} catch (ExcepcionNoExiste e) {
			e.printStackTrace();
			throw new GWT_ExcepcionNoExiste(e);
		} finally {
			this.cerrar_transaccion(con, commit);
		}		
	}

	@Override
	public void borra_producto_de_lista(DatosProducto produ, int id_compra) throws GWT_ExcepcionBD, GWT_ExcepcionNoExiste {
		ConexionBD con= this.obtener_transaccion();
		boolean commit= false;	

		try {
			Producto prod= new Producto(con, produ.getNombre());

			con.ejecutar_sql("DELETE FROM rel_listas_productos WHERE id_compra = " + id_compra + " AND id_prod = "+ prod.getId());

			commit= true;

		} catch (ExcepcionBD e) {
			throw new GWT_ExcepcionBD(e);
		} catch (ExcepcionNoExiste e) {
			throw new GWT_ExcepcionNoExiste(e);
		}finally {
			this.cerrar_transaccion(con, commit);
		}
	}



	@Override
	public Set<DatosProducto> buscar_productos(int id_compra) throws GWT_ExcepcionBD, GWT_ExcepcionNoAutorizado{
		Set <DatosProducto> datos_conj= new HashSet<DatosProducto>();
		ConexionBD con= this.obtener_transaccion();
		boolean commit= true;	


		try {
			
			
			ContextoDeSeguridad cs = autenticar_y_autorizar(con, "zarazz");

			String query= "select * from productos";
			
			if(id_compra!= 0)
				query+= "  where id_super in ( select id_super from sucursales where id in ( select id_sucursal from listas where id ="+ id_compra + "))";
			ResultSet rs= con.select(query);

			while (rs.next()) {
				DatosProducto datos= new DatosProducto();

				datos.setId(rs.getInt("id"));
				datos.setNombre(rs.getString("nombre"));
				datos.setPrecio(rs.getFloat("precio"));
				datos.setPrecio_kg(rs.getFloat("precio_x_kg_venta_al_peso"));
				datos.setId_super(rs.getInt("id_super"));
				
				datos_conj.add(datos);
			}

		} catch (ExcepcionBD e) {
			throw new GWT_ExcepcionBD(e);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new GWT_ExcepcionBD(e);
		} finally {
			this.cerrar_transaccion(con, commit);
		}
		return datos_conj;
	}

	@Override
	public DatosListaProdCompleta buscar_productos_lista(int id_lista) throws GWT_ExcepcionBD, GWT_ExcepcionNoExiste{
		
		DatosListaProdCompleta lista_completa= new DatosListaProdCompleta();
		Set <DatosProducto> datos_conj= new HashSet<DatosProducto>();
		ConexionBD con= this.obtener_transaccion();

		boolean commit= true;	
		
		try {
			Lista l = new Lista(con, id_lista);

			ResultSet rs= con.select("SELECT * FROM rel_listas_productos where id_compra = " + id_lista);

			while (rs.next()) {

				Producto p = new Producto(con, rs.getInt("id_prod"));
				DatosProducto datos= new DatosProducto();

				datos.setId(rs.getInt("id_prod"));
				datos.setNombre(p.getNombre());
				datos.setPrecio(rs.getFloat("precio"));
				datos.setCantidad(rs.getInt("cant"));
				datos.setCant_en_gramos(rs.getInt("cant_en_gramos"));
				datos.setEsta_marcada(rs.getBoolean("esta_marcada"));
				datos.setFecha_venc(rs.getDate("fecha_venc"));
				datos_conj.add(datos);
			}
			
			lista_completa.setDescuento_del_super(l.getDesc_coto());
			lista_completa.setPorcentaje_de_descuento(l.getPorcentaje_de_descuento());
			lista_completa.setLista_prod(datos_conj);

		} catch (ExcepcionBD e) {
			throw new GWT_ExcepcionBD(e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new GWT_ExcepcionBD(e);
		} catch (ExcepcionNoExiste e) {
			throw new GWT_ExcepcionNoExiste(e);
		} finally {
			this.cerrar_transaccion(con, commit);
		}
		return lista_completa;
	}

	@Override
	public Set<DatosProducto> buscar_vencimientos(boolean solo_existentes) throws GWT_ExcepcionBD, GWT_ExcepcionNoAutorizado{
		Set <DatosProducto> datos_conj= new HashSet<DatosProducto>();
		ConexionBD con= this.obtener_transaccion();
		boolean commit= true;	



		try {
			
			ContextoDeSeguridad cs = autenticar_y_autorizar(con, "zarazz");

			
			String query= "SELECT p.id, p.nombre, rlp.fecha_venc, rlp.existe_todavia, rlp.id_compra, l.fecha "
					+ " FROM rel_listas_productos rlp, productos p, listas l "
					+ "where rlp.id_prod = p.id AND rlp.id_compra= l.id and fecha_venc is not null "+ (solo_existentes? " and existe_todavia":"") +"  order by fecha_venc";
			ResultSet rs= con.select(query);

			while (rs.next()) {
				DatosProducto datos= new DatosProducto();

				datos.setId(rs.getInt("id"));
				datos.setNombre(rs.getString("nombre"));
				datos.setFecha_venc(rs.getDate("fecha_venc"));
				datos.setFecha_compra(rs.getDate("fecha"));
				datos.setExiste(rs.getBoolean("existe_todavia"));
				datos.setId_compra(rs.getInt("id_compra"));

				datos_conj.add(datos);
			}

		} catch (ExcepcionBD e) {
			throw new GWT_ExcepcionBD(e);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new GWT_ExcepcionBD(e);
		} finally {
			this.cerrar_transaccion(con, commit);
		}
		return datos_conj;
	}


	@Override
	public DatosProducto agregar_producto_a_lista(DatosProducto datos_prod, int id_compra, int cant, int cant_en_gramos) throws GWT_ExcepcionBD, GWT_ExcepcionNoExiste {
		boolean commit= false;
		ConexionBD con = this.obtener_transaccion();

		try {
			Producto prod= new Producto(con, datos_prod.getNombre());
			int id_prod= prod.getId();

			con.ejecutar_sql("INSERT INTO rel_listas_productos (id_compra, id_prod, cant, cant_en_gramos, precio_x_kg_venta_al_peso, precio, esta_marcada) VALUES (" +
					id_compra+"," + id_prod +"," +cant + "," +cant_en_gramos  +","+prod.getPrecio_kg()+","+prod.getPrecio() +"," +datos_prod.isEsta_marcada() + ")");

			datos_prod.setId(id_prod);
			datos_prod.setPrecio(prod.getPrecio());
			datos_prod.setCantidad(cant);
			datos_prod.setPrecio_kg(prod.getPrecio_kg());
			datos_prod.setCant_en_gramos_anterior(cant_en_gramos);

			commit= true;

		} catch (ExcepcionBD e) {
			throw new GWT_ExcepcionBD(e);
		}catch (ExcepcionNoExiste e) {
			throw new GWT_ExcepcionNoExiste(e);
		} finally {
			this.cerrar_transaccion(con, commit);
		}
		return datos_prod;
	}


	@Override
	public void actualizar_producto(DatosProducto datos_prod) throws GWT_ExcepcionBD, GWT_ExcepcionNoExiste {
		boolean commit= false;
		ConexionBD con = this.obtener_transaccion();

		try {
			Producto prod= new Producto(con, datos_prod.getId());
			int id_prod= prod.getId();
			String query= "UPDATE productos SET precio= "+ datos_prod.getPrecio() + ", precio_x_kg_venta_al_peso= "+ datos_prod.getPrecio_kg() +
			", nombre= '"+ datos_prod.getNombre() + "', "+"id_super=" + datos_prod.getId_super() +" WHERE id = " + id_prod;
			//			con.ejecutar_sql("UPDATE productos SET precio= "+ prod.getPrecio() +", nombre= "
			//			+ prod.getNombre() + " WHERE id = " + id_prod);
			con.ejecutar_update(query);
			commit= true;

		} catch (ExcepcionBD e) {
			throw new GWT_ExcepcionBD(e);
		}catch (ExcepcionNoExiste e) {
			e.printStackTrace();
			throw new GWT_ExcepcionNoExiste(e);

		} finally {
			this.cerrar_transaccion(con, commit);
		}		
	}
	
	public void marcar_desmarcar_productos(String id_compra, Set<String> ids,
			boolean marcar) throws GWT_ExcepcionBD, GWT_ExcepcionNoExiste {
		boolean commit= false;
		ConexionBD con = this.obtener_transaccion();
		
		String IN_DE_IDS= "(";

		try {
			for(int i=0;i<ids.size();i++)

			for (String id_prod :ids) {
				Producto prod= new Producto(con, Integer.valueOf(id_prod));

				IN_DE_IDS+=id_prod;
				if(i!= ids.size()-1)
					IN_DE_IDS+=", ";
			}
				String UPDATE= "UPDATE rel_listas_productos SET esta_marcada= " + marcar +" where id_compra= " + id_compra +
						"AND id_prod IN " + IN_DE_IDS + ")";  

				con.ejecutar_sql(UPDATE);
				commit= true;

			
			
		} catch (ExcepcionBD e) {
			throw new GWT_ExcepcionBD(e);
		}catch (ExcepcionNoExiste e) {
			e.printStackTrace();
			throw new GWT_ExcepcionNoExiste(e);
		} finally {
			this.cerrar_transaccion(con, commit);
		}
	}

	@Override
	public void actualizar_producto_a_lista(DatosProducto datos_prod, String id_compra, 
			boolean cambiar_existencia) throws GWT_ExcepcionBD, GWT_ExcepcionNoExiste {
		boolean commit= false;
		ConexionBD con = this.obtener_transaccion();

		try {
			Producto prod= new Producto(con, datos_prod.getId());
			//			int id_prod= prod.getId();
			Object f_venc= datos_prod.getFechaVenc();
			String fecha_venc= f_venc!= null ? ", fecha_venc= '" + f_venc + "' ": "";

			String UPDATE= "UPDATE rel_listas_productos SET";

			if(cambiar_existencia) {
				boolean existe_prod= datos_prod.isExiste();
				UPDATE+= " existe_todavia= "+ existe_prod;
			} else {
				UPDATE+= " precio= "+ datos_prod.getPrecio() + 
						", esta_marcada= " + datos_prod.isEsta_marcada() + " , cant= "
						+ datos_prod.getCantidad() + fecha_venc;
			}

			String WHERE= " WHERE id_compra = " +id_compra+" AND id_prod= " + prod.getId();
			UPDATE+= WHERE;

			con.ejecutar_sql(UPDATE);

			if(!cambiar_existencia)
				verificar_si_cambiar_precio_global(con, datos_prod, id_compra);

			commit= true;

		} catch (ExcepcionBD e) {
			throw new GWT_ExcepcionBD(e);
		}catch (ExcepcionNoExiste e) {
			e.printStackTrace();
			throw new GWT_ExcepcionNoExiste(e);
		} finally {
			this.cerrar_transaccion(con, commit);
		}


	}


	private void verificar_si_cambiar_precio_global(ConexionBD con,
			DatosProducto datos_prod, String id_compra) throws ExcepcionBD {

		int id_prod= datos_prod.getId();
		float precio= datos_prod.getPrecio();
		Date fecha_de_prod_en_compra_actual = null;
		Date fecha_de_prod_en_otras_compras = null;
		Date aux= null;
		ResultSet rs= con.select("select fecha from listas where id =" + id_compra);
		//		ResultSet rs2= con.select("select max(fecha) from listas");

		ResultSet rs2= con.select("select l.fecha from listas l , rel_listas_productos rlp where l.id= rlp.id_compra and rlp.id_prod=" + id_prod);

		try {

			if(rs.next()) 
				fecha_de_prod_en_compra_actual= rs.getDate("fecha");
			aux= fecha_de_prod_en_compra_actual;
			while(rs2.next())  {
				fecha_de_prod_en_otras_compras= rs2.getDate("fecha");
				if(fecha_de_prod_en_otras_compras.after(aux))
					aux= fecha_de_prod_en_otras_compras;
			}

			if(!aux.after(fecha_de_prod_en_compra_actual)) {
				String query= "UPDATE productos SET precio= "+ precio + " WHERE id = " + id_prod;

				con.ejecutar_update(query);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	

}
