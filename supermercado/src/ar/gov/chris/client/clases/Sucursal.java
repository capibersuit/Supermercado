package ar.gov.chris.client.clases;

public class Sucursal extends EnumeradoDeBDSinValoresEnum {
	
	private static final long serialVersionUID= -304813793490559870L;
	private String localidad;
	private int id_super;
	
	/** Constructor sin parámetros.
	 */
	public Sucursal() {
		super();
	}
	
	/** Constructor con parámetros. SÓLO debe ser utilizado por su fábrica, que
	 * trae los datos desde la BD o por la pantalla o widget con los datos 
	 * suministrados por el servidor.
	 * 
	 * @param id El id en la BD de la Sucursal.
	 * @param nombre El nombre en la BD de la Sucursal.
	 * @param id_super ID del supermercado al cual pertenece la sucursal en la BD.
	 */
	public Sucursal(int id, String nombre, String localidad, int id_super) {
		super(id, nombre);
		this.localidad= localidad;
		this.id_super= id_super;
	}
	
	/** getter del atributo homónimo.
	 * 
	 * @return int id.
	 */
	public int id_super() {
		return id_super;
	}
	/** getter del atributo homónimo.
	 * 
	 * @return String localidad.
	 */
	public String localidad() {
		return localidad;
	}
}