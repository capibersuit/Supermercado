package ar.gov.chris.server.clases;

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
	
	public void grabar() {
		
		super.grabar(con, lista_campos, tabla, tabla_secuencia, nuevo, condicion, id, solo_si_no_existe)
	}
}
