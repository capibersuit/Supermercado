package ar.gov.chris.server.clases;

import java.sql.ResultSet;
import java.sql.SQLException;

import ar.gov.chris.server.bd.ConexionBD;
import ar.gov.chris.server.bd.HashMapSQL;
import ar.gov.chris.server.excepciones.ExcepcionBD;
import ar.gov.chris.server.excepciones.ExcepcionNoExiste;

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
	
	public Producto(ConexionBD con, int id) throws ExcepcionBD, ExcepcionNoExiste {
		 String query= "SELECT * FROM productos WHERE id=" + id;
		 this.cargar_producto(con, query, "Producto con id " + id);
		}
	
	public Producto(ConexionBD con, String nombre) throws ExcepcionBD, ExcepcionNoExiste {
		 String query= "SELECT * FROM productos WHERE nombre= '" + nombre + "'";
		 this.cargar_producto(con, query, "Producto con nombre " + nombre);
		}
	
		private void cargar_producto(ConexionBD con, String query, String texto_error) 
				throws ExcepcionBD, ExcepcionNoExiste {
		 try {
			 ResultSet rs= con.select(query);
			 if (rs.next()) {
				 this.nombre= rs.getString("nombre");
				 this.precio= rs.getFloat("precio");
				 super.cargar_persistente_sin_baja_fisica(rs);
			 } else throw new ExcepcionNoExiste(texto_error);
		 } catch(SQLException ex) {
				throw new ExcepcionBD(ex);
		 }

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
	
	public void borrar(ConexionBD con) throws ExcepcionBD {
		con.ejecutar_sql("DELETE FROM productos WHERE id = "+ this.id);
		
	}
}
