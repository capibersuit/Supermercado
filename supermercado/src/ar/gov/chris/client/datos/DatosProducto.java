package ar.gov.chris.client.datos;


import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DatosProducto implements IsSerializable /*, Comparable<DatosProducto> */{
	
	int id;
	String nombre;
	//String descripcion;
	float precio;
	float precio_anterior;
	int cantidad;
	int cantidad_anterior;
	boolean esta_marcada;
	
	
	
	public int getCantidad_anterior() {
		return cantidad_anterior;
	}


	public void setCantidad_anterior(int cantidad_anterior) {
		this.cantidad_anterior = cantidad_anterior;
	}

	
	public float getPrecio_anterior() {
		return precio_anterior;
	}


	public void setPrecio_anterior(float precio_anterior) {
		this.precio_anterior = precio_anterior;
	}


	private Date fecha_venc;
	
	private boolean existe;
	public Date getFecha_compra() {
		return fecha_compra;
	}


	public void setFecha_compra(Date fecha_compra) {
		this.fecha_compra = fecha_compra;
	}


	private int id_compra;
	private Date fecha_compra;

	
	
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
	
	
	
	
	public boolean isExiste() {
		return existe;
	}


	public void setExiste(boolean existe) {
		this.existe = existe;
	}


	public int getId_compra() {
		return id_compra;
	}


	public void setId_compra(int id_compra) {
		this.id_compra = id_compra;
	}


//	@Override
//	public String toString() {
//		return "DatosProducto [id=" + id + ", nombre=" + nombre + ", fecha_venc=" + fecha_venc + ", precio="
//				+ precio + "]";
//	}
	
	


	public void setFechaVenc(Date fecha_venc) {
		this.fecha_venc= fecha_venc;
	}


	@Override
	public String toString() {
		return "DatosProducto [id=" + id + ", nombre=" + nombre + ", precio="
				+ precio + ", cantidad=" + cantidad + ", esta_marcada="
				+ esta_marcada + ", fecha_venc=" + fecha_venc + ", existe="
				+ existe + ", id_compra=" + id_compra + ", fecha_compra="
				+ fecha_compra + "]";
	}


	public Object getFechaVenc() {
		// TODO Auto-generated method stub
		return fecha_venc;
	}


	public Date getFecha_venc() {
		return fecha_venc;
	}


	public void setFecha_venc(Date fecha_venc) {
		this.fecha_venc = fecha_venc;
	}
	
	
	
	
//	@Override
//	public int compareTo(DatosProducto datos) {
////		//puedo hacer esto porque String implementa Comparable
//	    return nombre.compareTo(datos.getNombre());
//	}
	
	
	

}
