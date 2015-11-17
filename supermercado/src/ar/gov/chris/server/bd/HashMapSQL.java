package ar.gov.chris.server.bd;

//import java.util.HashMap;

/** Esta clase es como un {@link HashMap} común pero permite que se le agreguen
 * valores que no son objetos, además de transformar ciertos tipos en la
 * representación que necesita la BD.
 *
 * Ejemplo: true => t
 * @author fpscha
 * @version $Revision: 1.8 $
 */

public class HashMapSQL extends java.util.HashMap<Object, Object> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Creates new HashMapSQL. */
	public HashMapSQL() {
	}
		
	/** �dem put, pero tomando un int.
	 * @param clave Clave.
	 * @param valor Valor.
	 */	
	public void put(String clave, int valor) {
	 super.put(clave, new Integer(valor));
	}
	
	/** �dem put, pero tomando un long.
	 * @param clave Clave.
	 * @param valor Valor.
	 */	
	public void put(String clave, long valor) {
	 super.put(clave, new Long(valor));
	}
	
	/** �dem put, pero tomando un char.
	 * @param clave Clave.
	 * @param valor Valor.
	 */	
	public void put(String clave, char valor) {
	 super.put(clave, new Character(valor));
	}

	/** �dem put, pero tomando un boolean.
	 * @param clave Clave.
	 * @param valor Valor.
	 */	
	public void put(String clave, boolean valor) {
	 String v= "f";
		
	 if (valor)
		v= "t";
		
	 super.put(clave, v);
	}
	
	/** �dem put, pero tomando un double.
	 * @param clave Clave.
	 * @param valor Valor.
	 */	
	public void put(String clave, double valor) {
	 super.put(clave, new Double(valor));
	}

}
