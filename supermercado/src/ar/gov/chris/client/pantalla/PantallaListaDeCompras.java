package ar.gov.chris.client.pantalla;

import java.util.Set;

import ar.gov.chris.client.datos.DatosLista;
import ar.gov.chris.client.datos.DatosProducto;
import ar.gov.chris.client.interfaces.ProxyPantallaListas;
import ar.gov.chris.client.interfaces.ProxyPantallaListasAsync;
import ar.gov.chris.client.widgets.MensajeAlerta;
import ar.gov.chris.client.widgets.WidgetAgregarLista;
import ar.gov.chris.client.widgets.WidgetMostrarListas;
import ar.gov.chris.client.widgets.WidgetMostrarProductos;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;

public class PantallaListaDeCompras extends Pantalla {
	
	
	private Button btn_agregar_lista;
	private Button btn_ir_a_prod;


	private ProxyPantallaListasAsync proxy_listas;

	
	private WidgetAgregarLista agregar_lista;
	private WidgetMostrarListas listas;

	protected Set<DatosLista> datos_lista;
	

	public PantallaListaDeCompras() {
		super();
		inicializar();
		pantalla_principal();
	}

	private void pantalla_principal() {
		
//		btn_agregar_lista= new Button("Nueva Lista");
//		panel.add(btn_agregar_lista);
//		
//		agregar_lista= new WidgetAgregarLista(this);
//		agregar_handlers();
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
//				agregar_item_historial_cliente(datos_item);
//				recargar_personas();
			}
			
		});
	}
	
	private void obtener_datos_listas() {
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
	
	
	
	protected void armar_pantalla() {
		btn_agregar_lista= new Button("Nueva Lista");
		btn_ir_a_prod= new Button("Ir a productos");
		panel.add(btn_ir_a_prod);
		panel.add(btn_agregar_lista);
		
		agregar_lista= new WidgetAgregarLista(this);
		listas= new WidgetMostrarListas(datos_lista, "Listas de compras");
		panel.add(listas);
		agregar_handlers();		
	}

	private void agregar_handlers() {
		btn_agregar_lista.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				agregar_lista.show();
			}
		});
		
		btn_ir_a_prod.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				History.newItem("PantallaProductos");
				History.fireCurrentHistoryState();
			}
		});
	}

	/** Se crea el proxy_carga para comunicarse con el servidor.
	 */
	protected void inicializar(){
		this.proxy_listas= (ProxyPantallaListasAsync)
		GWT.create(ProxyPantallaListas.class);
		super.inicializar((ServiceDefTarget) this.proxy_listas, "Listas");
	}
	
	
}
