package ar.gov.chris.server.clases;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import ar.gov.chris.server.excepciones.ExcepcionIO;

/**
 * Esta clase permite leer par�metros de un archivo de propiedades.
 * @author christian
 *
 */
public class LectorPropiedades {
	
	 private static Properties propiedades = new Properties();
//	 private static String ARCH_CONF="/supermercado/src/ar/gov/chris/server/clases/archivo.properties";

	 private static String ARCH_CONF_WIN="D:\\supermercado.properties";
	 
	 private static String ARCH_CONF_LINUX="/usr/local/supermercado/bin/supermercado.properties";
	 
	 private static String ARCH_CONF;

	 static String SistemaOperativo = System.getProperty("os.name");
	 
	 
	/** Devuelve el valor asociado a una clave.
	 * @param clave La clave.
	 * @return El valor.
	 * @throws GWT_ExcepcionIO Si hay alg�n problema con el archivo de propiedades.
	 */
	public static  String obtener_valor(String clave) throws ExcepcionIO {
		
		if(SistemaOperativo.indexOf("Win") >= 0)
			ARCH_CONF= ARCH_CONF_WIN;
		else
			ARCH_CONF= ARCH_CONF_LINUX;
		
		String valor = "";
		try {
			// Intenta buscar el archivo de propiedades en la carpeta resources.
			
			// De�sta manera busca el archivo de conf en la ruta
			// WEB-INF/clases/archivo.properties
			// y �ste se genera a partir del que est� en la carpeta resources.
			
			FileInputStream inputStream = new FileInputStream(ARCH_CONF);

			// De�sta manera busca el archivo de conf en la ruta
			// WEB-INF/clases/ar/gov/mecon/properties/archivo.properties
			// y �ste se genera a partir del que est� en la carpeta 
			// src/ar/gov/mecon/properties.
			
//			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(ARCH_CONF);
			// Si pude obtener el archivo de propiedades, entonces las cargo.
			if (inputStream != null) {
				propiedades.load(inputStream);
			} else {
				throw new ExcepcionIO("El archivo de propiedades '" + 
						ARCH_CONF + "' no fue encontrado � no se pudo leer." +
						" Notifique a los administradores del sistema.");
			}
		} catch (IOException e) {
			throw new ExcepcionIO("El archivo de propiedades '" + 
					ARCH_CONF + "' no fue encontrado � no se pudo leer." +
					" Notifique a los administradores del sistema.");
		}
		// Obtengo el valor de la propiedad que me interesa.
		valor= propiedades.getProperty(clave);
		return valor;
	}
}
