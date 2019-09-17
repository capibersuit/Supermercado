package ar.gov.chris.server.clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import ar.gov.chris.server.bd.ConexionBD;
import ar.gov.chris.server.bd.HashMapSQL;
import ar.gov.chris.server.excepciones.ExcepcionBD;
import ar.gov.chris.server.excepciones.ExcepcionNoExiste;

public class Lista extends PersistenteEnBD {
	
	String comentario;
	Date fecha;
	private int id_sucursal;
	private boolean ver_marcados;
	private float pagado;
	private float desc_coto;
	private boolean botones_hab;
	private int porcentaje_de_descuento;
	
	
	public Lista() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public float getDesc_coto() {
		return desc_coto;
	}

	public void setDesc_coto(float desc_coto) {
		this.desc_coto = desc_coto;
	}


	
	public int getPorcentaje_de_descuento() {
		return porcentaje_de_descuento;
	}

	public void setPorcentaje_de_descuento(int porcentaje_de_descuento) {
		this.porcentaje_de_descuento = porcentaje_de_descuento;
	}

	public float getPagado() {
		return pagado;
	}

	public void setPagado(float pagado) {
		this.pagado = pagado;
	}

	public Lista(String comentario, Date fecha, int id_sucursal, int porcentaje) {
		super();
		this.comentario = comentario;
		this.fecha = fecha;
		this.id_sucursal= id_sucursal;
		this.porcentaje_de_descuento= porcentaje;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public boolean isVer_marcados() {
		return ver_marcados;
	}
	public void setVer_marcados(boolean ver_marcados) {
		this.ver_marcados = ver_marcados;
	}
	
	

	
	
	public int getId_sucursal() {
		return id_sucursal;
	}

	public void setId_sucursal(int id_sucursal) {
		this.id_sucursal = id_sucursal;
	}

	public boolean isBotones_hab() {
		return botones_hab;
	}

	public void setBotones_hab(boolean botones_hab) {
		this.botones_hab = botones_hab;
	}

	@Override
	public String toString() {
		return "Lista [comentario=" + comentario + ", fecha=" + fecha
				+ ", ver_marcados=" + ver_marcados + ", pagado=" + pagado + "]";
	}

	public Lista(ConexionBD con, int id) throws ExcepcionBD, ExcepcionNoExiste {
		 String query= "SELECT * FROM listas WHERE id=" + id;
		 this.cargar_lista(con, query, "Lista con id " + id);
		}
	
	public Lista(ConexionBD con, String nombre) throws ExcepcionBD, ExcepcionNoExiste {
		 String query= "SELECT * FROM listas WHERE comentario= '" + comentario + "'";
		 this.cargar_lista(con, query, "Lista con comentario " + comentario);
		}
	
		private void cargar_lista(ConexionBD con, String query, String texto_error) 
				throws ExcepcionBD, ExcepcionNoExiste {
		 try {
			 ResultSet rs= con.select(query);
			 if (rs.next()) {
				 this.comentario= rs.getString("comentario");
				 this.fecha= rs.getDate("fecha");
				 this.ver_marcados= rs.getBoolean("ver_marcados");
				 this.pagado= rs.getFloat("pagado");
				 this.botones_hab= rs.getBoolean("botones_hab");
				 this.desc_coto= rs.getFloat("desc_coto");
				 this.porcentaje_de_descuento=rs.getInt("porcentaje_desc");
				 this.id_sucursal= rs.getInt("id_sucursal");
				 super.cargar_persistente_sin_baja_fisica(rs);
			 } else throw new ExcepcionNoExiste(texto_error);
		 } catch(SQLException ex) {
				throw new ExcepcionBD(ex);
		 }

		}

	
	public void grabar(ConexionBD con) throws ExcepcionBD {
		
		 HashMapSQL lista_campos= new HashMapSQL();
		 lista_campos.put("comentario", this.comentario);
		 lista_campos.put("fecha", this.fecha);
		 lista_campos.put("pagado", this.pagado);
		 lista_campos.put("id_sucursal", this.id_sucursal);
		 lista_campos.put("porcentaje_desc", this.porcentaje_de_descuento);


		this.id= super.grabar(con, lista_campos, "public.listas", "public.listas", true, "", id, false);
	}

	public void borrar(ConexionBD con) throws ExcepcionBD {
		con.ejecutar_sql("DELETE FROM rel_listas_productos WHERE id_compra = "+ this.id);		
		con.ejecutar_sql("DELETE FROM listas WHERE id = "+ this.id);		
	}
}
