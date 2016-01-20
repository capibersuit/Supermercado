package ar.gov.chris.client.pantalla;


import ar.gov.chris.client.interfaces.ProxyPantallaListas;
import ar.gov.chris.client.interfaces.ProxyPantallaListasAsync;
import ar.gov.chris.client.interfaces.ProxyPantallaProductos;
import ar.gov.chris.client.interfaces.ProxyPantallaProductosAsync;
import ar.gov.chris.client.widgets.MensajeAlerta;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;

public class PantallaInicio extends Pantalla {
	
	protected ProxyPantallaListasAsync proxy_listas;
	protected ProxyPantallaProductosAsync proxy_prod;


	Button btn_productos;
	Button btn_listas;
	
	public PantallaInicio() {
		super();
		inicializar();
		pantalla_principal();
//		cargar_pantalla();
	}

	public PantallaInicio(String msj) {
		MensajeAlerta.mensaje_error(msj);	
		}
	//@Override
	protected void pantalla_principal() {
		
		btn_productos= new Button("Productos");
		btn_listas= new Button("Listas");
		panel.add(btn_productos);
		panel.add(btn_listas);
		agregar_listener();
	}

	private void agregar_listener() {
		btn_listas.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				panel.clear();
				History.newItem("PantallaListaDeCompras");}
		});
		btn_productos.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
//				panel.clear();
				History.newItem("PantallaProductos");}
		});
		
	}
	
	/** Se crea el proxy_carga para comunicarse con el servidor.
	 */
	protected void inicializar(){
		
		this.proxy_prod= (ProxyPantallaProductosAsync)
		GWT.create(ProxyPantallaProductos.class);
		super.inicializar((ServiceDefTarget) this.proxy_prod, "Productos");
				
		this.proxy_listas= (ProxyPantallaListasAsync)
		GWT.create(ProxyPantallaListas.class);
		super.inicializar((ServiceDefTarget) this.proxy_listas, "Listas");
	}

}
