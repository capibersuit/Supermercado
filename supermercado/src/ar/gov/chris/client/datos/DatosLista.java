package ar.gov.chris.client.datos;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DatosLista implements IsSerializable {
	
	int id;
	int id_sucursal;
	String comentario;
	Date fecha;
	private boolean ver_marcados;
	private float pagado;
	private float desc_coto;
	private boolean botones_habilitados;

	
	
	public int getId_sucursal() {
		return id_sucursal;
	}
	public void setId_sucursal(int id_sucursal) {
		this.id_sucursal = id_sucursal;
	}
	public float getDesc_coto() {
		return desc_coto;
	}
	public void setDesc_coto(float desc_coto) {
		this.desc_coto = desc_coto;
	}
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
	
	public void setPagado(float pagado) {
		this.pagado = pagado;
	}
	public float getPagado() {
		return pagado;
	}
	public boolean isBotones_habilitados() {
		return botones_habilitados;
	}
	public void setBotones_habilitados(boolean botones_habilitados) {
		this.botones_habilitados = botones_habilitados;
	}
	
	
	
	
}
