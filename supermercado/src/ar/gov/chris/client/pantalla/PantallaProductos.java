package ar.gov.chris.client.pantalla;

//import ar.gov.chris.client.GreetingService;
//import ar.gov.chris.client.GreetingServiceAsync;
//import ar.gov.chris.client.GreetingService;
import java.util.Set;

import ar.gov.chris.client.GreetingServiceAsync;
import ar.gov.chris.client.datos.DatosProducto;
import ar.gov.chris.client.interfaces.ProxyPantallaProductos;
import ar.gov.chris.client.interfaces.ProxyPantallaProductosAsync;
import ar.gov.chris.client.widgets.WidgetAgregarProducto;
import ar.gov.chris.client.widgets.MensajeAlerta;
import ar.gov.chris.client.widgets.WidgetMostrarProductos;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
	
//	private final GreetingServiceAsync greetingService = GWT
//			.create(GreetingService.class);
    private Set<DatosProducto> datos_prod;

	private WidgetAgregarProducto agregar_prod;
	private WidgetMostrarProductos productos;
	

	public PantallaProductos() {
		super();
		inicializar();
		pantalla_principal();
	}

	private void pantalla_principal() {
		panel.clear();
//		proxy_prod.buscar_productos();
//		btn_productos= new Button("Nuevo Producto");
//		panel.add(btn_productos);
//		agregar_prod= new WidgetAgregarProducto(this);
		obtener_datos_productos();
//		agregar_handlers();
	}
	
	private void obtener_datos_productos() {
		proxy_prod.buscar_productos(new AsyncCallback<Set<DatosProducto>>(){
			public void onFailure(Throwable caught) {
				MensajeAlerta.mensaje_error("Ocurri� un error al intentar buscar " +
						"los productos: " + caught.getMessage());
			}
			public void onSuccess(Set<DatosProducto> result) {
				datos_prod= result;
				armar_pantalla();			
			}
			
		});
				
	}

	protected void armar_pantalla() {
		btn_productos= new Button("Nuevo Producto");
		panel.add(btn_productos);
		agregar_prod= new WidgetAgregarProducto(this);	
		productos= new WidgetMostrarProductos(datos_prod, "Lista de productos");
		panel.add(productos);
		agregar_handlers();

	}

	@Override
	public void agregar_producto(String nombre, String precio) {
		DatosProducto datos_prod= new DatosProducto();
		datos_prod.setNombre(nombre);
		datos_prod.setPrecio(Float.parseFloat(precio));
			
		proxy_prod.agregar_producto(datos_prod, new AsyncCallback<Void>(){
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
	
	
	
	private void agregar_handlers() {
		btn_productos.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				agregar_prod.show();
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
