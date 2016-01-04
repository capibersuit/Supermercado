package ar.gov.chris.client.pantalla;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import ar.gov.chris.client.clases.NombreProdComparator;
import ar.gov.chris.client.datos.DatosProducto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusWidget;


public class Pantalla extends Composite {
	protected FlowPanel panel= new FlowPanel();
//	private final Image imagen_espera= new Image("imagenes/loading.gif");
//	private final HTML msj_espera= new HTML();
//	private FlowPanel panel_espera= null;
	
	/** Constructor sin par�metros.
	 */
	public Pantalla() {
	 this.panel.setStyleName("PanelPrincipal");
	 initWidget(this.panel);
	}
	

	/** Termina de crear el proxy para comunicarse con el servidor.
	 * @param endpoint El endpoint para la comunicaci�n con el servidor.
	 * @param pantalla El nombre de la pantalla (sin el prefijo "Pantalla").
	 */
	protected void inicializar(ServiceDefTarget endpoint, String pantalla) {
	 try {
		 String moduleRelativeURL= GWT.getModuleBaseURL() +
		 		"proxies_pantallas/ProxyPantalla"+pantalla;
		 endpoint.setServiceEntryPoint(moduleRelativeURL);
	 } catch (Exception ex) {
		 System.out.println("Excepci�n: " + ex.getMessage());
	 }
	}

	/** Le da foco a un componente (tiene que ser un FocusWidget).
	 * 
	 * @param widget El componente que va a recibir el foco.
	 */
	static public void dar_foco(final FocusWidget widget) {
	 ScheduledCommand s= new ScheduledCommand() {
		
		@Override
		public void execute() {
			widget.setFocus(true);
			
		}
	 };
	 s.execute();
	}
	
	protected LinkedList<DatosProducto> ordenar_productos(Set<DatosProducto> lista_prod) {
		LinkedList<DatosProducto> lista_para_ordenar= new LinkedList<DatosProducto>();
		//Obtengo todos los datos de las productos y los agrego a una lista.
		for (Iterator<DatosProducto> iter = lista_prod.iterator(); iter.hasNext();) {
			DatosProducto prods = iter.next();
			lista_para_ordenar.add(prods);
		}	
		//Ordeno por nombre del producto la lista con los datos de los productos que obtuve.
		Collections.sort(lista_para_ordenar, new NombreProdComparator());
		return lista_para_ordenar;
	}

	public void agregar_producto(String nombre, String precio) {
		// TODO Auto-generated method stub
		
	}


	

}
