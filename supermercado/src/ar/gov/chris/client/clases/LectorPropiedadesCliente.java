package ar.gov.chris.client.clases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class LectorPropiedadesCliente {
	
	 private static Properties propiedades = new Properties();
//	 private static String ARCH_CONF="/supermercado/src/ar/gov/chris/server/clases/archivo.properties";

	 private static String ARCH_CONF_WIN="C:\\supermercado.properties";
	 
	 private static String ARCH_CONF_LINUX="/usr/local/supermercado/bin/supermercado.properties";
	 
	 private static String ARCH_CONF;

	 static String SistemaOperativo = System.getProperty("os.name");
	 
	 
	/** Devuelve el valor asociado a una clave.
	 * @param clave La clave.
	 * @return El valor.
	 * @throws GWT_ExcepcionIO Si hay alg�n problema con el archivo de propiedades.
	 */
	public static  String obtener_valor(String clave) {
		
		if(SistemaOperativo.indexOf("Win") >= 0)
			ARCH_CONF= ARCH_CONF_WIN;
		else
			ARCH_CONF= ARCH_CONF_LINUX;
		
		String valor = "";
		//try {
			
			FileInputStream inputStream = null;
			try {
				inputStream = new FileInputStream(ARCH_CONF);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			
//			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(ARCH_CONF);
			// Si pude obtener el archivo de propiedades, entonces las cargo.
			if (inputStream != null) {
				try {
					propiedades.load(inputStream);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
//		} catch (IOException e) {
//			throw new ExcepcionIO("El archivo de propiedades '" + 
//					ARCH_CONF + "' no fue encontrado � no se pudo leer." +
//					" Notifique a los administradores del sistema.");
//		}
		// Obtengo el valor de la propiedad que me interesa.
		valor= propiedades.getProperty(clave);
		return valor;
	}
}

