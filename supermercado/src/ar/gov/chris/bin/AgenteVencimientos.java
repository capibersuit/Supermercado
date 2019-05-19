package ar.gov.chris.bin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import ar.gov.chris.client.clases.FechaVencComparator;
import ar.gov.chris.client.datos.DatosProducto;
import ar.gov.chris.server.bd.ConexionBD;
import ar.gov.chris.server.clases.CorreoE;
import ar.gov.chris.server.clases.LectorPropiedades;
import ar.gov.chris.server.clases.Producto;
import ar.gov.chris.server.excepciones.ExcepcionBD;
import ar.gov.chris.server.excepciones.ExcepcionIO;
import ar.gov.chris.server.excepciones.ExcepcionNoExiste;

public class AgenteVencimientos {

//	final private static long SLEEP_TIME= 60000;
	
	private static String Mensage="";
	private static String Mensage_fin="";

	public static void main (String args[]) {


		//		String arch= Sanitizador.sanitizar(args[0]);
		//		;
		//		List<String[]> productos= null;
		//		// Parseo el archivo de entrada para obtener las �reas.
		//		try {
		//			productos= Lotes.leer_campos(arch, ";");
		//		} catch (IOException ex) {
		//			System.err.println(ex.getMessage());
		//			System.exit(1);
		//		}
		//
		//		ConexionBD con= new ConexionBD();
		boolean commit= false;

		try {
			//while (true) {

			LinkedList<DatosProducto> productos= buscar_productos_lista();

			if(productos.size() > 0) {
				StringBuffer cuerpo_mail = new StringBuffer();
				
				Mensage = LectorPropiedades.obtener_valor("Mensage");	
				Mensage_fin = LectorPropiedades.obtener_valor("MensageFin");	


//				cuerpo_mail.append("Estimado habitante de nuestro hogar, se le informa que los siguientes productos estan próximos a vencer..."
//						+ ", ¡por favor, tenga a bien consumirlos antes de las fechas indicadas! y por su puesto ¡NO ME HABLEN DE TIRAR!");

				

				cuerpo_mail.append("<table border=\"2px\">"
						+ "	<tr> <td><b>Producto</b</td> <td><b>Vencimiento</b</td> </tr>");


				for (DatosProducto prod : productos) {

					cuerpo_mail.append("<tr> <td>" + prod.getNombre() );
					cuerpo_mail.append("<td> " + prod.getFechaVenc() + "</tr>");
					//					cuerpo_mail.append("\n");			
				}

				cuerpo_mail.append("</table>");
				
				cuerpo_mail.append("<br><br>");

				
				cuerpo_mail.append(Mensage);
				
				cuerpo_mail.append("<br><br>");
//				cuerpo_mail.append("Desde ya muchas gracias por su colaboración.");
				cuerpo_mail.append(Mensage_fin);
				cuerpo_mail.append("<br><br>");


				cuerpo_mail.append("<p>Atte. <b>SCLS.</b> </p>");
				cuerpo_mail.append("<p><small>(Sistema de control de listas de supermercado)</small></p>");

				CorreoE correo = new CorreoE();

				correo.SendMail(cuerpo_mail.toString());
				//CorreoE.SendMail(cuerpo_mail.toString());

				//Thread.sleep(SLEEP_TIME);
			}
		} catch (ExcepcionBD e) {
			System.err.println(e.getMessage());
		} catch (ExcepcionNoExiste e) {
			System.err.println(e.getMessage());
		}  /*catch (InterruptedException e) {
			System.err.println(e.getMessage());
		} */ catch (ExcepcionIO e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	//TODO: Este metodo esta copiado de la clase pantalla...
	protected static LinkedList<DatosProducto> ordenar_vemcimientos(Set<DatosProducto> lista_prod) {
		LinkedList<DatosProducto> lista_para_ordenar= new LinkedList<DatosProducto>();
		//Obtengo todos los datos de las productos y los agrego a una lista.
		for (Iterator<DatosProducto> iter = lista_prod.iterator(); iter.hasNext();) {
			DatosProducto prods = iter.next();
			lista_para_ordenar.add(prods);
		}	
		//Ordeno por fecha de venc del producto la lista con los datos de los productos que obtuve.
		Collections.sort(lista_para_ordenar, new FechaVencComparator());
		return lista_para_ordenar;
	}

	public static LinkedList<DatosProducto> buscar_productos_lista() throws ExcepcionBD, ExcepcionNoExiste{
		Set <DatosProducto> datos_conj= new HashSet<DatosProducto>();
		LinkedList<DatosProducto> datos_ordenados= new LinkedList<DatosProducto>();

		ConexionBD con= new ConexionBD();
		con.begin_transaction();

		boolean commit= true;	
		//		Lista l = new Lista(comentario, fecha);
		try {
			ResultSet rs= con.select("SELECT * FROM rel_listas_productos"
					+ " where existe_todavia and fecha_venc < NOW()  + INTERVAL \'7 DAY\'" );

			while (rs.next()) {

				Producto p = new Producto(con, rs.getInt("id_prod"));
				DatosProducto datos= new DatosProducto();

				//				datos.setId(rs.getInt("id_prod"));
				datos.setNombre(p.getNombre());
				//				datos.setPrecio(rs.getFloat("precio"));
				//				datos.setCantidad(rs.getInt("cant"));
				//				datos.setEsta_marcada(rs.getBoolean("esta_marcada"));
				datos.setFechaVenc(rs.getDate("fecha_venc"));
				
				

				datos_conj.add(datos);
				
				datos_ordenados= ordenar_vemcimientos(datos_conj);
			}

		}  catch (SQLException e) {
			e.printStackTrace();
			throw new ExcepcionBD(e);
		} finally {
			try {
				if (commit)
					con.commit_y_cerrar();
				else
					con.rollback_y_cerrar();
			} catch (ExcepcionBD ex) {
				System.err.println(ex.getMessage());
			}
		}
		return datos_ordenados;
	}

}
