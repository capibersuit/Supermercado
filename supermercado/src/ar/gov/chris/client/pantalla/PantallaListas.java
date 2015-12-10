package ar.gov.chris.client.pantalla;

import ar.gov.chris.client.datos.DatosLista;
import ar.gov.chris.client.interfaces.ProxyPantallaListasAsync;
import ar.gov.chris.client.widgets.WidgetAgregarLista;
import ar.gov.chris.client.widgets.MensajeAlerta;
import ar.gov.chris.server.proxies_pantallas.ProxyPantallaListasImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;

public class PantallaListas extends Pantalla {
	
	
	private Button btn_agregar_lista;

	private ProxyPantallaListasAsync proxy_listas;

	
	private WidgetAgregarLista agregar_lista;
	

	public PantallaListas() {
		super();
		inicializar();
		pantalla_principal();
	}

	private void pantalla_principal() {
		
		btn_agregar_lista= new Button("Nueva Lista");
		panel.add(btn_agregar_lista);
		
		agregar_lista= new WidgetAgregarLista(this);
		agregar_handlers();
	}
	
	
	public void agregar_lista(String comentario) {
		DatosLista datos_list= new DatosLista();
		datos_list.setComentario(comentario);
	
		proxy_listas.agregar_lista(datos_list, new AsyncCallback<Void>(){
			public void onFailure(Throwable caught) {
				MensajeAlerta.mensaje_error("Ocurrió un error al intentar agregar " +
						"el producto: " + caught.getMessage());
			}
			public void onSuccess(Void result) {
//				agregar_item_historial_cliente(datos_item);
//				recargar_personas();
			}
			
		});
	}
	
	
	
	private void agregar_handlers() {
		btn_agregar_lista.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				agregar_lista.show();
			}
		});
	}

	/** Se crea el proxy_carga para comunicarse con el servidor.
	 */
	protected void inicializar(){
		this.proxy_listas= (ProxyPantallaListasAsync)
		GWT.create(ProxyPantallaListasImpl.class);
		super.inicializar((ServiceDefTarget) this.proxy_listas, "Listas");
	}
	
	
}