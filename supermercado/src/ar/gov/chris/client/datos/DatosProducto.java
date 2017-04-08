package ar.gov.chris.client.datos;


import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DatosProducto implements IsSerializable /*, Comparable<DatosProducto> */{
	
	int id;
	String nombre;
	//String descripcion;
	float precio;
	int cantidad;
	boolean esta_marcada;
	private Date fecha_venc;
	
	
	public DatosProducto() {
		
	}
			
	
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
	public boolean isEsta_marcada() {
		return esta_marcada;
	}
	public void setEsta_marcada(boolean esta_marcada) {
		this.esta_marcada = esta_marcada;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	@Override
	public String toString() {
		return "DatosProducto [id=" + id + ", nombre=" + nombre + ", fecha_venc=" + fecha_venc + ", precio="
				+ precio + "]";
	}


	public void setFechaVenc(Date fecha_venc) {
		this.fecha_venc= fecha_venc;
	}


	public Object getFechaVenc() {
		// TODO Auto-generated method stub
		return fecha_venc;
	}
	
//	@Override
//	public int compareTo(DatosProducto datos) {
////		//puedo hacer esto porque String implementa Comparable
//	    return nombre.compareTo(datos.getNombre());
//	}
	
	
	

}
