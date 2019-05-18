package ar.gov.chris.client.clases;

import java.io.Serializable;

/** Clase serializable que contiene los datos de un enumerado que se encuentra 
 * en la BD, y cuyas constantes nunca se usan directamente. Sólo deber ser
 * construida desde la Fábrica correspondiente que traerá sus datos desde la BD.
 * 
 * @author STUART
 *
 */
public class EnumeradoDeBDSinValoresEnum implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5084943583922738562L;
	private int id;
	private String descripcion;
	
	/** Constructor sin parámetros.
	 */
	public EnumeradoDeBDSinValoresEnum() {
	}
	
	/** Constructor con parámetros. SÓLO debe ser utilizado por su fábrica, que
	 * trae los datos desde la BD o por la pantalla o widget con los datos 
	 * suministrados por el servidor.
	 * 
	 * @param id El id en la BD del tipo de problema.
	 * @param nombre El nombre en la BD del tipo de problema.
	 */
	public EnumeradoDeBDSinValoresEnum(int id, String nombre) {
		this.descripcion= nombre;
		this.id= id;
	}

	/** Cambia el valor de la descripción. SÓLO debe ser utilizado por su fábrica, que
	 * trae los datos desde la BD o por la pantalla o widget con los datos 
	 * suministrados por el servidor.
	 * 
	 * @param desc La descripción en la BD del tipo de problema.
	 */
	public void cambiar_descripcion(String desc) {
		this.descripcion= desc;
	}

	/** Cambia el id del enumerado. SÓLO debe ser utilizado por su fábrica, que
	 * trae los datos desde la BD o por la pantalla o widget con los datos 
	 * suministrados por el servidor.
	 * 
	 * @param id El id en la BD del enumerado.
	 */
	public void cambiar_id(int id) {
		this.id= id;
	}

	/** Devuelve la descripción del enumerado.
	 * 
	 * @return Ídem.
	 */
	public String obtener_descripcion() {
		return this.descripcion;
	}

	/** Devuelve el id del enumerado.
	 * 
	 * @return Ídem.
	 */
	public int obtener_id() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return this.descripcion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnumeradoDeBDSinValoresEnum other = (EnumeradoDeBDSinValoresEnum) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
}
