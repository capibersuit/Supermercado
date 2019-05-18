package ar.gov.chris.client.clases;

public class Super extends EnumeradoDeBDSinValoresEnum {
	
	private static final long serialVersionUID= 6436590730886056730L;

	/** Constructor sin parámetros.
	 */
	public Super() {
		super();
	}
	
	/** Constructor con parametros. SÓLO debe ser utilizado por su fábrica, que
	 * trae los datos desde la BD o por la pantalla o widget con los datos 
	 * suministrados por el servidor.
	 * 
	 * @param id El id en la BD del supermercado.
	 * @param descripcion La descripcion en la BD del supermercado.
	 */
	public Super(int id, String descripcion) {
		super(id, descripcion);
	}
}
