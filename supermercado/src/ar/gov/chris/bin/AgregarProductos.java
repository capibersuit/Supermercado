package ar.gov.chris.bin;


import java.io.IOException;
import java.util.List;
import ar.gov.chris.server.bd.ConexionBD;
import ar.gov.chris.server.clases.Producto;
import ar.gov.chris.server.excepciones.ExcepcionBD;
import ar.gov.chris.server.excepciones.ExcepcionYaExiste;
import ar.gov.chris.shared.Sanitizador;

public class AgregarProductos {
	/**
	 * Muestra el correcto uso de la aplicaci�n.
	 */
	private static void mostrar_uso() {
		System.out.println(AgregarProductos.class.getName()+
				" <arch.>");
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main (String args[]) {
		if (args.length!=1) {
			mostrar_uso();
			return;
		}

		String arch= Sanitizador.sanitizar(args[0]);
		;
		List<String[]> productos= null;
		// Parseo el archivo de entrada para obtener las �reas.
		try {
			productos= Lotes.leer_campos(arch, ";");
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
			System.exit(1);
		}

		ConexionBD con= new ConexionBD();
		boolean commit= false;

		for (String[] prod_param : productos) {
			try {
				con.begin_transaction();
				//					commit= false;
				String nombre= Sanitizador.sanitizar(prod_param[0]);
				String precio= Sanitizador.sanitizar(prod_param[1]);

				nombre= nombre.trim();
				precio= precio.trim();

				agregar_producto(con, nombre, precio);

				commit= true;
			} catch (ExcepcionBD ex) {
				System.err.println(ex.getMessage());
			} catch (ExcepcionYaExiste ex) {	
				System.err.println(ex.getMessage());
			}  finally {
				try {
					if (commit)
						con.commit();
					else
						con.rollback();
				} catch (ExcepcionBD ex) {
					System.err.println(ex.getMessage());
				}
			}
		}
		try {
			con.cerrar();
		} catch (ExcepcionBD ex) {
			System.err.println(ex.getMessage());
		}
	}

	private static void agregar_producto(ConexionBD con, String nombre, String precio) throws ExcepcionBD, ExcepcionYaExiste {

		float precio_convert= Float.parseFloat(precio);

		Producto p = new Producto(nombre, precio_convert);

		p.grabar(con, true);
	}
}	
