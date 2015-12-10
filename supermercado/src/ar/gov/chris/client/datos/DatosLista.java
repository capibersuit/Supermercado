package ar.gov.chris.client.datos;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DatosLista implements IsSerializable {
	
	int id;
	String comentario;
	Date fecha;
	
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
	
}
