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
	private boolean ver_marcados;

	public Lista(String comentario, Date fecha) {
		super();
		this.comentario = comentario;
		this.fecha = fecha;
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

	@Override
	public String toString() {
		return "Lista [comentario=" + comentario + ", fecha=" + fecha + "]";
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

		super.grabar(con, lista_campos, "public.listas", "public.listas", true, "", id, false);
	}

	public void borrar(ConexionBD con) throws ExcepcionBD {
		con.ejecutar_sql("DELETE FROM rel_listas_productos WHERE id_compra = "+ this.id);		
		con.ejecutar_sql("DELETE FROM listas WHERE id = "+ this.id);		
	}
	
	

}
