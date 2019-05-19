package ar.gov.chris.server.genericos.basicos;

import java.net.InetAddress;
import java.net.UnknownHostException;

import ar.gov.mecon.genericos.excepciones.ExcepcionBug;
import ar.gov.mecon.genericos.excepciones.ExcepcionFormatoInvalido;

public class IP {
	final static private String octeto=
			"([0-9]{1,2}|1([0-9]{2})|2[0-4][0-9]|25[0-5])";
		/** Regexp a utilizar para matchear una IP.
		 */
		final static public String REGEXP_IP=
			"^"+octeto+"\\."+octeto+"\\."+octeto+"\\."+octeto+"$";

		private InetAddress ip;
		
		/** Constructor. */
		public IP() {}
			
		/** Crea una nueva IP. Notar: si se le pasa un nombre lo resuelve
		 * consultando al DNS.
		 * 
		 * @param host_o_ip Nombre de host o IP.
		 * @throws ExcepcionFormatoInvalido Si la <code>host_o_ip</code> no
		 * representa ni a un nombre ni a una IP v�lida.
		 */
		public IP(String host_o_ip) throws ExcepcionFormatoInvalido {
		 try {
			 /* Si es un nombre debe empezar con una letra, y si no debe matchear la regular
			  * expression de IPs. */
//			 if (host_o_ip==null ||
//				(!host_o_ip.matches("^[a-zA-Z].*")/* && !host_o_ip.matches(REGEXP_IP)*/))
//				 throw new ExcepcionFormatoInvalido("IP inv�lida: " + host_o_ip);
			 this.ip= InetAddress.getByName(host_o_ip);
		 } catch (UnknownHostException ex) {
			throw new ExcepcionFormatoInvalido("IP inv�lida: "+host_o_ip);
		 }
		}
		
		/** Constructor por copia.
		 * @param i IP de la cual copiarse.
		 */
		public IP(IP i) {
		 try {
			this.ip= InetAddress.getByName(i.toString());
		 } catch (UnknownHostException ex) {
			throw new ExcepcionBug("IP inv�lida: "+i.toString());
		 }
		}

		/** Devuelve la representaci�n del IP como string en octetos punteados.
		 * @return El string mencionado.
		 */
		@Override
		public String toString() {
		 return this.ip.getHostAddress();
		}

}
