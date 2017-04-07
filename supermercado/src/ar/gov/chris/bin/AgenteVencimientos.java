package ar.gov.chris.bin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import ar.gov.chris.client.datos.DatosProducto;
import ar.gov.chris.server.bd.ConexionBD;
import ar.gov.chris.server.clases.CorreoE;
import ar.gov.chris.server.clases.Producto;
import ar.gov.chris.server.excepciones.ExcepcionBD;
import ar.gov.chris.server.excepciones.ExcepcionNoExiste;

public class AgenteVencimientos {

	final private static long SLEEP_TIME= 60000;

	public static void main (String args[]) {


		//		String arch= Sanitizador.sanitizar(args[0]);
		//		;
		//		List<String[]> productos= null;
		//		// Parseo el archivo de entrada para obtener las ï¿½reas.
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

			Set<DatosProducto> productos= buscar_productos_lista();

			if(productos.size() > 0) {
				StringBuffer cuerpo_mail = new StringBuffer();

				cuerpo_mail.append("Estimado habitante de nuestro hogar, se le informa que los siguientes productos estan próximos a vencer..."
						+ ", ¡por favor, tenga a bien consumirlos antes de las fechas indicadas! y por su puesto ¡NO ME HABLEN DE TIRAR!");

				cuerpo_mail.append("<br><br>");
				cuerpo_mail.append("Desde ya muchas gracias por su colaboración.");
				cuerpo_mail.append("<br><br>");


				cuerpo_mail.append("<table border=\"2px\">"
						+ "	<tr> <td><b>Producto</b</td> <td><b>Vencimiento</b</td> </tr>");


				for (DatosProducto prod : productos) {

					cuerpo_mail.append("<tr> <td>" + prod.getNombre() );
					cuerpo_mail.append("<td> " + prod.getFechaVenc() + "</tr>");
					//					cuerpo_mail.append("\n");			
				}

				cuerpo_mail.append("</table>");

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
		} */

	}

	public static Set<DatosProducto> buscar_productos_lista() throws ExcepcionBD, ExcepcionNoExiste{
		Set <DatosProducto> datos_conj= new HashSet<DatosProducto>();
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
		return datos_conj;
	}

}
