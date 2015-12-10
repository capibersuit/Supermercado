package ar.gov.chris.server.clases;

import java.util.Date;

import ar.gov.chris.server.bd.ConexionBD;
import ar.gov.chris.server.bd.HashMapSQL;
import ar.gov.chris.server.excepciones.ExcepcionBD;

public class Lista extends PersistenteEnBD {
	
	String comentario;
	Date fecha;
	
	public Lista(String comentario, Date fecha) {
		super();
		this.comentario = comentario;
		this.fecha = fecha;
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

	@Override
	public String toString() {
		return "Lista [comentario=" + comentario + ", fecha=" + fecha + "]";
	}
	
	public void grabar(ConexionBD con) throws ExcepcionBD {
		
		 HashMapSQL lista_campos= new HashMapSQL();
		 lista_campos.put("comentario", this.comentario);
		 lista_campos.put("fecha", this.fecha);

		super.grabar(con, lista_campos, "public.listas", "public.listas", true, "", id, false);
	}

}
