package ar.gov.chris.client.datos;


import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DatosProducto implements IsSerializable /*, Comparable<DatosProducto> */{
	
	int id;
	String nombre;
	//String descripcion;
	float precio;
	float precio_anterior;
	float precio_kg;
	float precio_kg_anterior;
	int cantidad;
	int cantidad_anterior;
	int cant_en_gramos;
	int cant_en_gramos_anterior;

	boolean esta_marcada;
	private Date fecha_venc;
	int id_super;
	
	
	
	public int getCantidad_anterior() {
		return cantidad_anterior;
	}


	public void setCantidad_anterior(int cantidad_anterior) {
		this.cantidad_anterior = cantidad_anterior;
	}
	
	

	public int getCant_en_gramos() {
		return cant_en_gramos;
	}


	public void setCant_en_gramos(int cant_en_gramos) {
		this.cant_en_gramos = cant_en_gramos;
	}


	public int getCant_en_gramos_anterior() {
		return cant_en_gramos_anterior;
	}


	public void setCant_en_gramos_anterior(int cant_en_gramos_anterior) {
		this.cant_en_gramos_anterior = cant_en_gramos_anterior;
	}


	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	
	public float getPrecio_anterior() {
		return precio_anterior;
	}


	public void setPrecio_anterior(float precio_anterior) {
		this.precio_anterior = precio_anterior;
	}
	
	public float getPrecio_kg() {
		return precio_kg;
	}


	public void setPrecio_kg(float precio_kg) {
		this.precio_kg = precio_kg;
	}


	public float getPrecio_kg_anterior() {
		return precio_kg_anterior;
	}


	public void setPrecio_kg_anterior(float precio_kg_anterior) {
		this.precio_kg_anterior = precio_kg_anterior;
	}


	
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


	public int getId_super() {
		return id_super;
	}


	public void setId_super(int id_super) {
		this.id_super = id_super;
	}


	public void setFechaVenc(Date fecha_venc) {
		this.fecha_venc= fecha_venc;
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


	@Override
	public String toString() {
		return "DatosProducto [id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", precio_anterior="
				+ precio_anterior + ", precio_kg=" + precio_kg + ", precio_kg_anterior=" + precio_kg_anterior
				+ ", cantidad=" + cantidad + ", cantidad_anterior=" + cantidad_anterior + ", cant_en_gramos="
				+ cant_en_gramos + ", cant_en_gramos_anterior=" + cant_en_gramos_anterior + ", esta_marcada="
				+ esta_marcada + ", fecha_venc=" + fecha_venc + ", id_super=" + id_super + ", existe=" + existe
				+ ", id_compra=" + id_compra + ", fecha_compra=" + fecha_compra + "]";
	}


	
	


}
