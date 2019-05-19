package ar.gov.chris.client.datos;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DatosReprtePrecios implements IsSerializable {
	
	String id_prod;
	String nombre_prod;
	String precios;
	String fechas;
	public String getId_prod() {
		return id_prod;
	}
	public void setId_prod(String id_prod) {
		this.id_prod = id_prod;
	}
	public String getNombre_prod() {
		return nombre_prod;
	}
	public void setNombre_prod(String nombre_prod) {
		this.nombre_prod = nombre_prod;
	}
	public String getPrecios() {
		return precios;
	}
	public void setPrecios(String string) {
		this.precios = string;
	}
	public String getFechas() {
		return fechas;
	}
	public void setFechas(String fechas) {
		this.fechas = fechas;
	}
	
	

}
