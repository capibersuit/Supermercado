package ar.gov.chris.server.proxies_pantallas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import ar.gov.chris.client.clases.Sucursal;
import ar.gov.chris.client.clases.Super;
import ar.gov.chris.client.datos.DatosLista;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionBD;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionNoAutorizado;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionNoExiste;
import ar.gov.chris.client.interfaces.ProxyPantallaListas;
import ar.gov.chris.server.bd.ConexionBD;
import ar.gov.chris.server.bd.PoolDeConexiones;
import ar.gov.chris.server.clases.FabricaSucursales;
import ar.gov.chris.server.clases.FabricaSuper;
import ar.gov.chris.server.clases.Lista;
import ar.gov.chris.server.excepciones.ExcepcionBD;
import ar.gov.chris.server.excepciones.ExcepcionNoExiste;
import ar.gov.chris.server.genericos.contexto.ContextoDeSeguridad;
import ar.gov.chris.shared.Sanitizador;

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
			
			
			Lista l= new Lista(datos_list.getComentario(), new Date(), datos_list.getId_sucursal());
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
	public Set<DatosLista> buscar_listas() throws GWT_ExcepcionBD, GWT_ExcepcionNoAutorizado{
		Set <DatosLista> datos_conj= new HashSet<DatosLista>();
		ConexionBD con= this.obtener_transaccion();
		Boolean commit= false;
		
		try {
			
			ContextoDeSeguridad cs = autenticar_y_autorizar(con, "zarazz");

			ResultSet rs= con.select("SELECT * FROM listas");
			
			while (rs.next()) {
				DatosLista datos= new DatosLista();
				datos.setId(rs.getInt("id"));
				datos.setComentario(rs.getString("comentario"));
				datos.setFecha(rs.getDate("fecha"));
				datos.setVer_marcados(rs.getBoolean("ver_marcados"));
				datos.setBotones_habilitados(rs.getBoolean("botones_hab"));
				datos.setPagado(rs.getFloat("pagado"));
				datos.setId_sucursal(rs.getInt("id_sucursal"));

				datos_conj.add(datos);
			}
			commit= true;
			
		} catch (ExcepcionBD e) {
			e.printStackTrace();
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
	public void existe_lista(int id_compra) throws GWT_ExcepcionBD, GWT_ExcepcionNoExiste {
		ConexionBD con;
//		Boolean existe= null;
		Boolean commit= false;
		con = this.obtener_transaccion();

		try {
			Lista l = new Lista(con, id_compra);
//			existe= true;
//			existe= l  != null;
			commit = true;
		} catch (ExcepcionBD e) {
				throw new GWT_ExcepcionBD(e);
		} catch (ExcepcionNoExiste e) {
			throw new GWT_ExcepcionNoExiste(e);
			} finally {
				this.cerrar_transaccion(con, commit);
			}
	}

	@Override
	public void borrar_lista(int id_compra) throws GWT_ExcepcionBD {
		ConexionBD con= this.obtener_transaccion();
		boolean commit= false;

		try {
			
			
			Lista l= new Lista(con, id_compra);
			l.borrar(con);
			commit= true;
		
		} catch (ExcepcionBD e) {
			e.printStackTrace();
			throw new GWT_ExcepcionBD(e);

		} catch (ExcepcionNoExiste e) {
			e.printStackTrace();
			throw new GWT_ExcepcionBD(e);

		} finally {
			this.cerrar_transaccion(con, commit);
		}			
	}

	@Override
	public void actualizar_lista(DatosLista datos_lista, boolean actualizar_desc) throws GWT_ExcepcionBD {
		boolean commit= false;
		ConexionBD con = this.obtener_transaccion();

		try {
			Lista lista= new Lista(con, datos_lista.getId());
			int id_lista= lista.getId();
			String comen= Sanitizador.sanitizar(datos_lista.getComentario());
			Date fech= datos_lista.getFecha();
			Boolean ver_marcad= datos_lista.isVer_marcados();
			float pagado= datos_lista.getPagado();
			float desc_coto= datos_lista.getDesc_coto();

			
			String query= "UPDATE listas SET ";
			if(comen != null && fech != null)
				query+=	"comentario= '"+ comen + "', fecha= '"+ fech +"',";
			
			query+=	" ver_marcados = "+ ver_marcad;
			if(pagado !=0)
				query+=	", pagado = "+ pagado; 
//			if(desc_coto !=0)
			if(actualizar_desc)
				query+= ", desc_coto = "+ desc_coto;
			query+= " WHERE id = " + id_lista;
			
//			con.ejecutar_sql("UPDATE productos SET precio= "+ prod.getPrecio() +", nombre= "
//			+ prod.getNombre() + " WHERE id = " + id_prod);
			con.ejecutar_update(query);
			commit= true;

		} catch (ExcepcionBD e) {
			e.printStackTrace();
			throw new GWT_ExcepcionBD(e);

		} catch (ExcepcionNoExiste e) {
			e.printStackTrace();
			throw new GWT_ExcepcionBD(e);

		} finally {
			this.cerrar_transaccion(con, commit);
		}				
	}

	@Override
	public void mostrar_ocultar_prod_en_lista(int id_compra) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DatosLista lista_esta_visible(int id_compra) throws GWT_ExcepcionBD, GWT_ExcepcionNoExiste {
		ConexionBD con;
		Lista l;
		Boolean commit= false;
		con = this.obtener_transaccion();
		DatosLista dl;
		try {
			l = new Lista(con, id_compra);
//			existe= true;
//			existe= l  != null;
			
			dl= new DatosLista();
			dl.setFecha(l.getFecha());
			dl.setVer_marcados(l.isVer_marcados());
			dl.setBotones_habilitados(l.isBotones_hab());
			commit = true;
		} catch (ExcepcionBD e) {
				throw new GWT_ExcepcionBD(e);
		} catch (ExcepcionNoExiste e) {
			throw new GWT_ExcepcionNoExiste(e);
			} finally {
				this.cerrar_transaccion(con, commit);
			}		
		return dl;
	}

	@Override
	public float buscar_desc_coto(int id_compra)throws GWT_ExcepcionBD, GWT_ExcepcionNoExiste {
		ConexionBD con;
		Lista l;
		Boolean commit= false;
		con = this.obtener_transaccion();

		try {
			l = new Lista(con, id_compra);
//			existe= true;
//			existe= l  != null;
			commit = true;
		} catch (ExcepcionBD e) {
				throw new GWT_ExcepcionBD(e);
		} catch (ExcepcionNoExiste e) {
			throw new GWT_ExcepcionNoExiste(e);
			} finally {
				this.cerrar_transaccion(con, commit);
			}		
		return l.getDesc_coto();
	}

	@Override
public void hab_deshab_botones(String id_compra, boolean botones_habilitados) throws GWT_ExcepcionBD {
		
		boolean commit= false;
		ConexionBD con = this.obtener_transaccion();
		

		try {
			
				String UPDATE= "UPDATE public.listas SET botones_hab= " + botones_habilitados +" where id= " + id_compra ;
						  

				con.ejecutar_sql(UPDATE);
				commit= true;

			
			
		} catch (ExcepcionBD e) {
			throw new GWT_ExcepcionBD(e);
		} finally {
			this.cerrar_transaccion(con, commit);
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public int [] buscar_anios_primera_y_ultima_compra() throws GWT_ExcepcionBD {
		
		ConexionBD con;
		Boolean commit= false;
		con = this.obtener_transaccion();
		int [] res = new int[2];
		
		Date hoy= new Date();
		res[0]= hoy.getYear();
		res[1]= hoy.getYear();

		try {
//			ResultSet rs= con.select("SELECT extract(YEAR FROM fecha) as fecha FROM listas order by fecha limit 1");
//			ResultSet rs2= con.select("SELECT extract(YEAR FROM fecha) as fecha FROM listas order by fecha desc limit 1");
			
			ResultSet rs= con.select("SELECT fecha FROM listas order by fecha limit 1");
			ResultSet rs2= con.select("SELECT fecha FROM listas order by fecha desc limit 1");

			while (rs.next()) {
				
				
				int primer_anio=(rs.getDate("fecha").getYear());
				res[0]=primer_anio +1900;
				
			}
			while (rs2.next()) {
				
				
				int ultimo_anio=(rs2.getDate("fecha").getYear());
				res[1]=ultimo_anio +1900;;
			}
			
			
				
				
				
			commit = true;
		} catch (ExcepcionBD e) {
				throw new GWT_ExcepcionBD(e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new GWT_ExcepcionBD(e);
			} finally {
				this.cerrar_transaccion(con, commit);
			}		
		return res;
		
	}

	@Override
		 
				
		public List<Sucursal> buscar_sucursales() 
				throws GWT_ExcepcionBD {
					//No se llama a autenticar_y_autorizar porque se obtienen datos de un enumerado.
					ConexionBD con= this.obtener_transaccion();
					try {
						return (new FabricaSucursales()).cargar_todos(con, false);
					} catch (ExcepcionBD ex) {
						throw new GWT_ExcepcionBD(ex);
					} catch (RuntimeException e) {
						throw new GWT_ExcepcionBD(e);
					} finally {
						cerrar_transaccion(con, false);
					}
				}
				

	@Override
	public List<Super> buscar_supermercados() throws GWT_ExcepcionBD {
		//No se llama a autenticar_y_autorizar porque se obtienen datos de un enumerado.
		ConexionBD con= this.obtener_transaccion();
		try {
			return (new FabricaSuper()).cargar_todos(con, false);
		} catch (ExcepcionBD ex) {
			throw new GWT_ExcepcionBD(ex);
		} catch (RuntimeException e) {
			throw new GWT_ExcepcionBD(e);
		} finally {
			cerrar_transaccion(con, false);
		}
	}

//	@Override
//	public void actualizar_descuento_lista(DatosLista dl) {
//		// TODO Auto-generated method stub
//		
//	}
}

