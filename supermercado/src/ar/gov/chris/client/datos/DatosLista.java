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
	private int porcentaje_descuento;

	
	
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
	
	public int getPorcentaje_descuento() {
		return porcentaje_descuento;
	}
	public void setPorcentaje_descuento(int porcentaje_descuento) {
		this.porcentaje_descuento = porcentaje_descuento;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (botones_habilitados ? 1231 : 1237);
		result = prime * result + ((comentario == null) ? 0 : comentario.hashCode());
		result = prime * result + Float.floatToIntBits(desc_coto);
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + id;
		result = prime * result + id_sucursal;
		result = prime * result + Float.floatToIntBits(pagado);
		result = prime * result + porcentaje_descuento;
		result = prime * result + (ver_marcados ? 1231 : 1237);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatosLista other = (DatosLista) obj;
		if (botones_habilitados != other.botones_habilitados)
			return false;
		if (comentario == null) {
			if (other.comentario != null)
				return false;
		} else if (!comentario.equals(other.comentario))
			return false;
		if (Float.floatToIntBits(desc_coto) != Float.floatToIntBits(other.desc_coto))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (id != other.id)
			return false;
		if (id_sucursal != other.id_sucursal)
			return false;
		if (Float.floatToIntBits(pagado) != Float.floatToIntBits(other.pagado))
			return false;
		if (porcentaje_descuento != other.porcentaje_descuento)
			return false;
		if (ver_marcados != other.ver_marcados)
			return false;
		return true;
	}
	
	
	
	
}
