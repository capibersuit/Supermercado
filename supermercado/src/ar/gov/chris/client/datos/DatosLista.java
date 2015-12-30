package ar.gov.chris.client.datos;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DatosLista implements IsSerializable {
	
	int id;
	String comentario;
	Date fecha;
	private boolean ver_marcados;

	
	public boolean isVer_marcados() {
		return ver_marcados;
	}
	public void setVer_marcados(boolean ver_marcados) {
		this.ver_marcados = ver_marcados;
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
	
}
