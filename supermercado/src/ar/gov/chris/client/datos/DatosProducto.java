package ar.gov.chris.client.datos;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DatosProducto implements IsSerializable {
	
	int id;
	String nombre;
	//String descripcion;
	float precio;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	@Override
	public String toString() {
		return "DatosProducto [id=" + id + ", nombre=" + nombre + ", precio="
				+ precio + "]";
	}
	
	
	

}
