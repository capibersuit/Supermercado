package ar.gov.chris.shared;

import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

import ar.gov.chris.server.excepciones.ExcepcionBug;

//import ar.gov.mecon.genericos.autorizacion.VerificadorDePasswords;
//import ar.gov.mecon.genericos.basicos.Sanitizador;

public class Sanitizador {
	/** Caracteres permitidos en todo tipo de expresi�n.
	 */
	static final public String PERMITIDOS= "a-zA-Z0-9_@.: �����������ڡ!�?\\(\\)\n\r\t\\-\\+/\\\\";
	/** Caracteres extra permitido en SQL.
	 */
	static final public String PERMITIDOS_SQL= ",;<>\\|";
	
	static final public String DIGITOS= "0-9";
	
	static final public String PERMITIDOS_FECHA= "0-9: /\\-";
	
	/** Devuelve una expresi�n "sana" de entrada.
	 * @param entrada El string a sanitizar.
	 * @param permitidos Caracteres permitidos.
	 * @param reemplazo Reemplazo de los caracteres no permitidos.
	 * @return El string sanitizado.
	 */
	static private String sanitizar(String entrada, String permitidos,
		String reemplazo) {
	 String salida= null;
	 
	 try {
		 RE r= new RE("[^"+permitidos+"]");
		 salida= r.subst(entrada, reemplazo, RE.REPLACE_ALL);
	 } catch (RESyntaxException e) {
		 throw new ExcepcionBug("Error en la sintaxis de la regexp"+
					" de sanitizar: "+e.getMessage());
	 }
	 
	 return salida;
	}
	
	/** Sanitiza un string reemplazando los caracteres no permitidos por
	 * "_".
	 * @param entrada El string a sanitizar.
	 * @param extra_permitidos Otros caracteres permitidos.
	 * @return El string sanitizado.
	 * @see Sanitizador#PERMITIDOS
	 */
	static public String sanitizar(String entrada, String extra_permitidos) {
	 return (entrada==null) ? null :
		 sanitizar(entrada, PERMITIDOS+extra_permitidos, "_");
	}

	/** Sanitiza un string reemplazando los caracteres no permitidos por
	 * "_".
	 * @param entrada El string a sanitizar.
	 * @return El string sanitizado.
	 * @see Sanitizador#PERMITIDOS
	 */
	static public String sanitizar(String entrada) {
	 return (entrada==null) ? null :
		 sanitizar(entrada, PERMITIDOS, "_");
	}
	
	/** Sanitiza un objeto que es realmente un string reemplazando
	 * los caracteres no permitidos por "_".
	 * @param entrada El string a sanitizar.
	 * @return El string sanitizado.
	 * @see Sanitizador#PERMITIDOS
	 */
	static public String sanitizar(Object entrada) {
	 return (entrada==null) ? null :
		 sanitizar((String) entrada, PERMITIDOS, "_");
	}
	
	/** Elimina los ' de un string.
	 * @param entrada El string a sanitizar.
	 * @return El string de entrada con los ' reemplazados por _.
	 */
	static private String sanitizar_re(String entrada) {
	 
	 return sanitizar(entrada, "\\$\\^\\|\\(\\)\\[\\]\\+\\*");
	}
	
	/** Sanitiza un objeto que es realmente un string reemplazando
	 * los caracteres no permitidos por "_".
	 * @param entrada El string a sanitizar.
	 * @return El string sanitizado.
	 * @see Sanitizador#PERMITIDOS
	 * @see Sanitizador#PERMITIDOS_SQL
	 */
	static public String sanitizar_sql(Object entrada) {
	 return (entrada==null) ? null :
		 sanitizar((String) entrada, PERMITIDOS+PERMITIDOS_SQL, "_");
	}

//	/** Sanitiza un objeto que es realmente un string que representa una
//	 * contrasena, reemplazando los caracteres no permitidos por "_".
//	 * @param entrada El string a sanitizar.
//	 * @return El string sanitizado.
//	 * @see Sanitizador#PERMITIDOS
//	 * @see VerificadorDePasswords#SIMBOLOS_PUNTUACION_NT
//	 */
//	static public String sanitizar_contrasena(Object entrada) {
//	 return (entrada==null) ? null :
//		 sanitizar((String) entrada, PERMITIDOS+
//		 	VerificadorDePasswords.SIMBOLOS_PUNTUACION_NT, "_");
//	}

	/** Sanitiza un objeto que es realmente un string representando
	 * una fecha, eliminando los caracteres no permitidos.
	 * 
	 * @param entrada El string a sanitizar.
	 * @return El string sanitizado.
	 * @see Sanitizador#PERMITIDOS_SQL
	 */
	static public String sanitizar_fecha(Object entrada) {
	 return (entrada==null) ? null :
			 sanitizar((String) entrada, PERMITIDOS_FECHA, "");
	}

	/** Sanitiza un objeto que es realmente un string representando
	 * una RE, reemplazando los caracteres no permitidos por "_".
	 * @param entrada El string a sanitizar.
	 * @return El string sanitizado.
	 * @see Sanitizador#PERMITIDOS
	 */
	static public String sanitizar_re(Object entrada) {
	 return (entrada==null) ? null :
		 sanitizar_re((String) entrada);
	}
	
	/** Sanitiza un string que representa una URL,
	 * reemplazando los caracteres no permitidos por "_".
	 * @param url El string a sanitizar.
	 * @return El string sanitizado.
	 */
	static public String sanitizar_url(String url) {
	 return sanitizar(url, PERMITIDOS+"/~", "_");
	}
	
	/** Escapa las comillas simples para poder meterlas en un texto
	 * SQL, reemplaz�ndolas por dobles.
	 * @param texto El texto a escapar.
	 * @return El string sanitizado.
	 */
	static public String escapar_comillas_llevandolas_a_dobles(String texto) {
 	 String salida= null;
	 
	 // Escapo las comillas.
	 try {
		 RE r= new RE("'");
		 salida= r.subst(texto, "\"", RE.REPLACE_ALL);
	 } catch (RESyntaxException e) {
		 throw new ExcepcionBug("Error en la sintaxis de la regexp"+
					" de escapar: "+e.getMessage());
	 }
	 
	 return salida;
	}
	
	/** Escapa las comillas simples para poder meterlas en un texto
	 * SQL.
	 * @param texto El texto a escapar.
	 * @return El string sanitizado.
	 */
	static public String escapar_comillas(String texto) {
	 // Notar: quiero escapar \' primero.
	 return  texto.replace("\\'", "\\\\'").replaceAll("'", "''");
	}
	
	/** Sanitiza un objeto que es realmente un string eliminando
	 * los caracteres no num�ricos y devolviendo un {@link Integer}.
	 * @param entrada El string a sanitizar.
	 * @return El {@link Integer} contenido, <code>null</code> si no
	 * es un entero.
	 */
	static public Integer sanitizar_integer(Object entrada) {
	 if (entrada==null)
		 return null;
	 String salida= sanitizar((String) entrada, DIGITOS, "");

	 Integer res= null;
	 
	 try {
		res= Integer.decode(salida);
	 } catch (NumberFormatException ex) {
		// Si no es un n�mero no hacemos nada, devolvemos null.
	 }
	 
	 return res;
	}
	
	/** Sanitiza un objeto que es realmente un string eliminando
	 * los caracteres no num�ricos y devolviendo un int .
	 * @param entrada El string a sanitizar.
	 * @return El int contenido.
	 */
	static public int sanitizar_int(Object entrada) {
	 if (entrada==null)
		 return 0;
	 String salida= sanitizar((String) entrada, DIGITOS, "");

	 int res= 0;
	 Integer aux= null;
	 
	 try {
		aux= Integer.decode(salida);
		res= aux.intValue();
	 } catch (NumberFormatException ex) {
	 }
	 
	 return res;
	}
	
	/** Escapa las \ para que en las sentencias SQL
	 * \n no se un enter y similares.
	 * @param entrada El string de entrada.
	 * @return El string sanitizado.
	 */
	static public String escapar_barras(String entrada) {
	 StringBuffer salida= new StringBuffer();
	 int pos_ant= 0, pos;

	 while ((pos= entrada.indexOf('\\', pos_ant))>=0) {
		salida.append(entrada.substring(pos_ant, pos));
		salida.append("\\\\");
		pos_ant= pos+1;
	 }
	 
	 salida.append(entrada.substring(pos_ant, entrada.length()));
	 
	 return salida.toString();
	}

}
