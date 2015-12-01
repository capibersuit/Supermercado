package ar.gov.chris.server.clases;

import ar.gov.chris.server.bd.ConexionBD;
import ar.gov.chris.server.bd.HashMapSQL;
import ar.gov.chris.server.excepciones.ExcepcionBD;

public class Producto extends PersistenteEnBD {
	
//	int id;
	String nombre;
	//String descripcion;
	float precio;
	
	public Producto() {
		
	}
	public Producto(int id, String nombre, float precio) {
		
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
	}
	public Producto(String nombre, float precio) {
		
		this.nombre = nombre;
		this.precio = precio;
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
	
	
	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", precio="
				+ precio + "]";
	}
	
	public void grabar(ConexionBD con) throws ExcepcionBD {
		
		 HashMapSQL lista_campos= new HashMapSQL();
		 lista_campos.put("nombre", this.nombre);
		 lista_campos.put("precio", this.precio);

		super.grabar(con, lista_campos, "public.productos", "public.productos", true, "", id, false);
	}
}
