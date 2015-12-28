package ar.gov.chris.bin;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ar.gov.chris.server.excepciones.ExcepcionNoExiste;

public class Lotes {
	
	/**
	 * Lee strings de un archivo, en formato de uno por línea.
	 * 
	 * @param arch Nombre del archivo.
	 * @return Lista con los strings leídos, en el orden en el que figuraban.
	 * @throws ExcepcionNoExiste Si no existe el archivo.
	 * @throws IOException si hay algún problema con la lectura del archivo.
	 */
	static public ArrayList<String> leer_strings(String arch) throws ExcepcionNoExiste,
			IOException {
	 ArrayList<String> res= new ArrayList<String>();
	 StringBuffer palabra= new StringBuffer();
	 byte[] buf= new byte[100];
	 int aux= 0, j;
	 
	 try {
		 FileInputStream fis= new FileInputStream(arch);

		 do {
			 aux= fis.read(buf);
			 for (j= 0; j<aux; j++) {
				 if (buf[j]!='\n')
					 palabra.append((char) buf[j]);
				 else {
					 // Termino una palabra y empiezo una nueva.
					 res.add(palabra.toString());
					 palabra= new StringBuffer();
				 }
			 }
		 } while (aux>=0);

		 // Guardo la última.
		 if (palabra.length()>0) {
			 res.add(palabra.toString());
		 }

		 fis.close();
	 } catch (FileNotFoundException ex) {
		 throw new ExcepcionNoExiste(ex.getMessage());
	 }

	 return res;
	}

	/**
	 * Dado un archivo de strings separados por <code>separador</code>, devuelve una lista
	 * arreglos de string, en el mismo orden que en el archivo.
	 * 
	 * @param archivo El nombre del archivo.
	 * @param separador El separafor.
	 * @return Ídem.
	 * @throws IOException Si hay algún problema en la lectura del archivo.
	 */
	public static List<String[]> leer_campos(String archivo, String separador) throws
			IOException {
	 List<String[]> res= new ArrayList<String[]>();
	 FileReader f= new FileReader(archivo);
	 BufferedReader lector= new BufferedReader(f);
	 String linea;
	 while ((linea= lector.readLine())!=null) {
		 String[] campos= linea.split(separador);
		 res.add(campos);
	 }
	 lector.close();
	 return res;
	}

}
