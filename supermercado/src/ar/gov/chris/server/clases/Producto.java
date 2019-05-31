package ar.gov.chris.server.clases;



import java.sql.ResultSet;
import java.sql.SQLException;

import ar.gov.chris.server.bd.ConexionBD;
import ar.gov.chris.server.bd.HashMapSQL;
import ar.gov.chris.server.excepciones.ExcepcionBD;
import ar.gov.chris.server.excepciones.ExcepcionBug;
import ar.gov.chris.server.excepciones.ExcepcionNoExiste;
import ar.gov.chris.server.excepciones.ExcepcionYaExiste;

public class Producto extends PersistenteEnBD {
	
//	int id;
	String nombre;
	//String descripcion;
	float precio;
	float precio_kg;
	int id_super;
	
	public Producto() {
		
	}
	public Producto(int id, String nombre, float precio, int id_super) {
		
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.id_super = id_super;
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
				 this.precio_kg= rs.getFloat("precio_x_kg_venta_al_peso");
				 this.id_super=rs.getInt("id_super");
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
	
	public float getPrecio_kg() {
		return precio_kg;
	}
	public void setPrecio_kg(float precio_kg) {
		this.precio_kg = precio_kg;
	}
	public int getId_super() {
		return id_super;
	}
	public void setId_super(int id_super) {
		this.id_super = id_super;
	}
	
	
	
	
	@Override
	public String toString() {
		return "Producto [nombre=" + nombre + ", precio=" + precio + ", precio_kg=" + precio_kg + ", id_super="
				+ id_super + "]";
	}
	public void grabar(ConexionBD con, boolean solo_sino_existe) throws ExcepcionBD, ExcepcionYaExiste {
		 if (this.nombre==null || (this.id_super) == 0)
			throw new ExcepcionBug("No se puede grabar un producto sin su nombre o sin su supermercado o con un id_super=0.");
		
		 HashMapSQL lista_campos= new HashMapSQL();
		 lista_campos.put("nombre", this.nombre);
		 lista_campos.put("precio", this.precio);
		 lista_campos.put("precio_x_kg_venta_al_peso", this.precio_kg);
		 lista_campos.put("id_super", this.id_super);

		 try {
		 this.id=super.grabar(con, lista_campos, "public.productos", "public.productos", true, "", id, false);
		 } catch (ExcepcionBD ex) {
			// Manejo el caso donde no se pudo grabar porque la clave está repetida.
				if (ex.es_clave_duplicada()) {
					throw new ExcepcionYaExiste("Ya existe un producto con nombre '"+this.nombre+
			 				"' y id de supermercado "+ this.id_super, false);
				} else {
					 throw ex;
				}
		 }
	}
	
	public void borrar(ConexionBD con) throws ExcepcionBD {
		con.ejecutar_sql("DELETE FROM productos WHERE id = "+ this.id);
		
	}
}
