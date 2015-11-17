package ar.gov.chris.client.pantalla;

import ar.gov.chris.client.GreetingService;
import ar.gov.chris.client.GreetingServiceAsync;
import ar.gov.chris.client.interfaces.ProxyPantallaProductos;
import ar.gov.chris.client.interfaces.ProxyPantallaProductosAsync;
import ar.gov.chris.client.widgets.WidgetAgregarProducto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;

public class PantallaProductos extends Pantalla {
	
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
//	private final GreetingServiceAsync greetingService = GWT
//			.create(GreetingService.class);
	
	private Button btn_productos;

	private ProxyPantallaProductosAsync proxy_prod;
	
	private WidgetAgregarProducto agregar_prod;
	

	public PantallaProductos() {
		super();
		pantalla_principal();
	}

	private void pantalla_principal() {
		
		btn_productos= new Button("Nuevo Producto");
		panel.add(btn_productos);
		agregar_prod= new WidgetAgregarProducto(this);
		agregar_handlers();
	}
	
	@Override
	public void agregar_producto(String nombre, String precio) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	private void agregar_handlers() {
		btn_productos.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
			}
		});
	}

	/** Se crea el proxy_carga para comunicarse con el servidor.
	 */
	protected void inicializar(){
		this.proxy_prod= (ProxyPantallaProductosAsync)
		GWT.create(ProxyPantallaProductos.class);
		super.inicializar((ServiceDefTarget) this.proxy_prod, "Productos");
	}
	
	
	}
