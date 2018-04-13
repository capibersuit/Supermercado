package ar.gov.chris.client.pantalla;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import ar.gov.chris.client.clases.FechaListaComparator;
import ar.gov.chris.client.datos.DatosLista;
import ar.gov.chris.client.interfaces.ProxyPantallaListas;
import ar.gov.chris.client.interfaces.ProxyPantallaListasAsync;
import ar.gov.chris.client.widgets.MensajeAlerta;
import ar.gov.chris.client.widgets.WidgetAgregarLista;
import ar.gov.chris.client.widgets.WidgetMostrarListas;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class PantallaListaDeCompras extends PantallaInicio {
	
	
	private Button btn_agregar_lista;
//	private Button btn_ir_a_prod;


//	private ProxyPantallaListasAsync proxy_listas;

	
	private WidgetAgregarLista agregar_lista;
	private WidgetMostrarListas listas;
	private WidgetMostrarListas listas2;
	private WidgetMostrarListas listas3;



	protected Set<DatosLista> datos_lista;
	
	
	private DisclosurePanel panel_16;
	private DisclosurePanel panel_17;
	private DisclosurePanel panel_18;



	public PantallaListaDeCompras() {
		super();
//		inicializar();
//		pantalla_principal();
	}

	@Override
	protected void pantalla_principal() {
		panel.clear();

//		btn_agregar_lista= new Button("Nueva Lista");
//		panel.add(btn_agregar_lista);
//		
//		agregar_lista= new WidgetAgregarLista(this);
//		agregar_handlers();
		
		super.pantalla_principal();
		obtener_datos_listas();
	}
	
	
	public void agregar_lista(String comentario) {
		DatosLista datos_list= new DatosLista();
		datos_list.setComentario(comentario);
	
		proxy_listas.agregar_lista(datos_list, new AsyncCallback<Void>(){
			public void onFailure(Throwable caught) {
				MensajeAlerta.mensaje_error("Ocurri� un error al intentar agregar " +
						"el producto: " + caught.getMessage());
			}
			public void onSuccess(Void result) {
				Window.Location.reload();

//				agregar_item_historial_cliente(datos_item);
//				recargar_personas();
			}
			
		});
	}
	
	 protected void obtener_datos_listas() {
		proxy_listas.buscar_listas(new AsyncCallback<Set<DatosLista>>(){
			public void onFailure(Throwable caught) {
				MensajeAlerta.mensaje_error("Ocurrió un error al intentar buscar " +
						"las listas de compras: " + caught.getMessage());
			}
			public void onSuccess(Set<DatosLista> result) {
				datos_lista= result;
				armar_pantalla();			
			}
			
		});
				
	}
	
	
	
	@SuppressWarnings({ "deprecation", "deprecation", "deprecation", "deprecation", "deprecation", "deprecation" })
	protected void armar_pantalla() {
		
		
		Label anio_16= new Label("Año 2016");
		
		panel_16= new DisclosurePanel();
		panel_16.setHeader(anio_16);
		
	   Label anio_17= new Label("Año 2017");
		
		panel_17= new DisclosurePanel();
		panel_17.setHeader(anio_17);
		
		Label anio_18= new Label("Año 2018");
		
		panel_18= new DisclosurePanel();
		panel_18.setHeader(anio_18);
		
//		HorizontalPanel hp = new HorizontalPanel();
		btn_agregar_lista= new Button("Nueva Lista");
//		btn_ir_a_prod= new Button("Ir a productos");
//		hp.add(btn_ir_a_prod);
//		hp.add(btn_agregar_lista);
		menu.add(btn_agregar_lista);

//		panel.add(btn_ir_a_prod);
//		panel.add(btn_agregar_lista);
//		panel.add(hp);
		
		agregar_lista= new WidgetAgregarLista(this, null);
		listas= new WidgetMostrarListas(datos_lista, "Listas de compras", this, new Date(2016-1900,1-1,01), new Date(2016-1900,12-1,31));
		listas2= new WidgetMostrarListas(datos_lista, "Listas de compras", this, new Date(2017-1900,1-1,01), new Date(2017-1900,12-1,31));
		listas3= new WidgetMostrarListas(datos_lista, "Listas de compras", this, new Date(2018-1900,1-1,01), new Date(2018-1900,12-1,31));

		
		panel_16.setContent(listas);
		panel_17.setContent(listas2);
		panel_18.setContent(listas3);

//		panel_16.setOpen(true);
//		panel_17.setOpen(true);
		panel_18.setOpen(true);

		
		panel.add(panel_18);
		panel.add(panel_17);
		panel.add(panel_16);
		agregar_handlers();		
	}

	private void agregar_handlers() {
		btn_agregar_lista.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				agregar_lista.show();
			}
		});
		
//		btn_ir_a_prod.addClickHandler(new ClickHandler() {
//			
//			@Override
//			public void onClick(ClickEvent event) {
//				History.newItem("PantallaProductos");
//				History.fireCurrentHistoryState();
//			}
//		});
	}

	public void borrar_lista(int id) {
		proxy_listas.borrar_lista(id, new AsyncCallback<Void>(){
			public void onFailure(Throwable caught) {
				MensajeAlerta.mensaje_error("Ocurrió un error al intentar borrar " +
						"la lista: " + caught.getMessage());
			}
			public void onSuccess(Void result) {
				Window.Location.reload();

			}
			
		});			
	}

	public void actualizar_producto(DatosLista datos_lista) {
		proxy_listas.actualizar_lista(datos_lista, false, new AsyncCallback<Void>(){
			public void onFailure(Throwable caught) {
				MensajeAlerta.mensaje_error("Ocurri� un error al intentar actualizar " +
						"la lista: " + caught.getMessage());
			}
			public void onSuccess(Void result) {
				Window.Location.reload();

			}
			
		});				
	}
	
	public LinkedList<DatosLista> ordenar_listas(Set<DatosLista> lista_compras) {
		LinkedList<DatosLista> lista_para_ordenar= new LinkedList<DatosLista>();
		//Obtengo todos los datos de las productos y los agrego a una lista.
		for (Iterator<DatosLista> iter = lista_compras.iterator(); iter.hasNext();) {
			DatosLista compra = iter.next();
			lista_para_ordenar.add(compra);
		}	
		//Ordeno por nombre del producto la lista con los datos de los productos que obtuve.
		Collections.sort(lista_para_ordenar, new FechaListaComparator());
		return lista_para_ordenar;
	}

	/** Se crea el proxy_carga para comunicarse con el servidor.
	 */
//	@Override
//	protected void inicializar(){
//		super.inicializar();
//	}
}
