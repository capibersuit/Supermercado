package ar.gov.chris.server.excepciones;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;


public class ExcepcionBD extends Excepcion {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new <code>ExcepcionBD</code> without detail message.
	 */
	public ExcepcionBD() {
	}

	/**
	 * Constructs an <code>ExcepcionBD</code> with the specified detail message.
	 * @param msg the detail message.
	 */
	public ExcepcionBD(String msg) {
	 super(msg);
	}

	/** Genera una nueva ExcepcionBD cuyo texto se extrae
	 * (formateado) de la excepci�n SQL que recibe.
	 * @param ex Exception SQL
	 */
	public ExcepcionBD(SQLException ex) {
	 super(ExcepcionBD.generar_string(ex));
	}
	
	/** Este m�todo extrae el texto que viene en una excepci�n SQL.
	 * @param ex La excepci�n SQL.
	 * @return El texto contenido (mensaje SQL, stack trace, etc.)
	 */
	static private String generar_string(SQLException ex) {
	 String res;
	 ByteArrayOutputStream baos= new ByteArrayOutputStream();
	 PrintStream ps= new PrintStream(baos);
	
	 res= "";
	 //res= "\n--- SQLException caught ---\n";
	 while (ex != null) {
		 res+= ex.getMessage() + "\n";
		//res+= "Message:   " + ex.getMessage() + "\n";
		//res+= "SQLState:  " + ex.getSQLState() + "\n";
		//res+= "ErrorCode: " + ex.getErrorCode() + "\n";
		//System.out.println("\nDEBUG: Stack trace:");
		ex.printStackTrace(ps);
		//res+= ps.toString();
		//ex.printStackTrace(System.out);
		//System.out.println("\nDEBUG: Fin stack trace:");
		ex = ex.getNextException();
		//res+= "\n";
	 }
	 return res;
	}
	
	/**
	 * Indica si el error es que se intenta insertar algo que est� duplicado.
	 * @return valor de retorno.
	 */
	public boolean es_clave_duplicada() {
	 return (this.getMessage().indexOf("Ya existe")>=0)
		|| (this.getMessage().indexOf("clave duplicada") >= 0)
		|| (this.getMessage().indexOf("duplicate key") >= 0);
	}
}

