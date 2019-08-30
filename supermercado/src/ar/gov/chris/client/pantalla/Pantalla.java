package ar.gov.chris.client.pantalla;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import ar.gov.chris.client.clases.FechaVencComparator;
import ar.gov.chris.client.clases.NombreProdComparator;
import ar.gov.chris.client.clases.NombreProdPrecioComparator;
import ar.gov.chris.client.datos.DatosProducto;
import ar.gov.chris.client.datos.DatosReprtePrecios;
import ar.gov.chris.client.interfaces.Constantes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusWidget;


public abstract class Pantalla extends Composite {
	protected FlowPanel panel= new FlowPanel();
//	private final Image imagen_espera= new Image("imagenes/loading.gif");
//	private final HTML msj_espera= new HTML();
//	private FlowPanel panel_espera= null;
	
	private Constantes anios = (Constantes) GWT.create(Constantes.class);
	
	protected int anio_actual= anios.anio_actual();
	protected int primer_anio_de_compras= anios.primer_anio_de_compras();
	
	protected static final String VERSION = "3.5";

	
	/** Constructor sin parámetros.
	 */
	public Pantalla() {
	 this.panel.setStyleName("PanelPrincipal");
	 initWidget(this.panel);
	}
	
//	protected void cargar_pantalla() {
//		pantalla_principal();
//	}
	
	/** La pantalla principal a mostrar luego de la validación. 
	 */
	abstract protected void pantalla_principal();
	

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
	
	protected LinkedList<DatosReprtePrecios> ordenar_productos_con_precio(Set<DatosReprtePrecios> lista_prod) {
		LinkedList<DatosReprtePrecios> lista_para_ordenar= new LinkedList<DatosReprtePrecios>();
		//Obtengo todos los datos de las productos y los agrego a una lista.
		for (Iterator<DatosReprtePrecios> iter = lista_prod.iterator(); iter.hasNext();) {
			DatosReprtePrecios prods = iter.next();
			lista_para_ordenar.add(prods);
		}	
		//Ordeno por nombre del producto la lista con los datos de los productos que obtuve.
		Collections.sort(lista_para_ordenar, new NombreProdPrecioComparator());
		return lista_para_ordenar;
	}
	
	protected LinkedList<DatosProducto> ordenar_vemcimientos(Set<DatosProducto> lista_prod) {
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

	public void agregar_producto(String nombre, String precio) {
		// TODO Auto-generated method stub
		
	}


	

}
