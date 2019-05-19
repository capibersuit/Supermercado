package ar.gov.chris.client.util;

import ar.gov.chris.client.clases.BuscadorDatosEstaticos;
import ar.gov.chris.client.clases.Sucursal;
import ar.gov.chris.client.clases.Super;

public class Util {
	
	/** Devuelve supermercado asociado a un string (si no existe null).
	 * @param nombre_super El nombre del super.
	 * @return Idem.
	 */
	public static Super mapear_supermercado_por_nombre(String nombre_super) {
	 
	 for (Super supermercado: BuscadorDatosEstaticos.supermercados){
			if (supermercado.obtener_descripcion().equalsIgnoreCase(nombre_super))
				return supermercado;
		}
	return null;
	}
	
	/** Devuelve supermercado asociado a un id (si no existe null).
	 * @param id_super El id del super.
	 * @return Idem.
	 */
	public static Super mapear_supermercado_por_id(String id_super) {
	 
	 for (Super supermercado: BuscadorDatosEstaticos.supermercados){
			if (supermercado.obtener_id()==Integer.parseInt(id_super))
				return supermercado;
		}
	return null;
	}

	
	public static Sucursal mapear_sucursal_por_id(String id_sucursal) {
		 
		 for (Sucursal sucursal: BuscadorDatosEstaticos.sucursales){
				if (sucursal.obtener_id()==Integer.parseInt(id_sucursal))
					return sucursal;
			}
		return null;
		}
	
	public static Sucursal mapear_sucursal_por_nombre(String nombre_sucursal) {
		 
		 for (Sucursal sucursal: BuscadorDatosEstaticos.sucursales){
				if (sucursal.obtener_descripcion().equalsIgnoreCase(nombre_sucursal))
					return sucursal;
			}
		return null;
		}
	
	public static Super obtener_supermercado_de_sucursal(String id_sucursal) {
		
		return mapear_supermercado_por_id(String.valueOf((mapear_sucursal_por_id(id_sucursal)).id_super()));
		
//		return null;
	}
}
