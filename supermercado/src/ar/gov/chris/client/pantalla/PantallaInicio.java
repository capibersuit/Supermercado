package ar.gov.chris.client.pantalla;


import ar.gov.chris.client.interfaces.ProxyPantallaListas;
import ar.gov.chris.client.interfaces.ProxyPantallaListasAsync;
import ar.gov.chris.client.interfaces.ProxyPantallaPrecios;
import ar.gov.chris.client.interfaces.ProxyPantallaPreciosAsync;
import ar.gov.chris.client.interfaces.ProxyPantallaProductos;
import ar.gov.chris.client.interfaces.ProxyPantallaProductosAsync;
import ar.gov.chris.client.widgets.MensajeAlerta;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class PantallaInicio extends Pantalla {
	
	protected ProxyPantallaListasAsync proxy_listas;
	protected ProxyPantallaProductosAsync proxy_prod;
	protected ProxyPantallaPreciosAsync proxy_precios;


	protected HorizontalPanel menu;
	
	Button btn_productos;
	Button btn_listas;
	Button btn_venc;
	Button btn_venc_ord;
	Button btn_precios;
	Button btn_precios_constantes;
	Button btn_logueo;


	
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
		menu= new HorizontalPanel();
		btn_productos= new Button("<u>P</u>roductos");
		btn_listas= new Button("<u>L</u>istas");
		btn_venc= new Button("<u>V</u>encimientos");
		btn_venc_ord= new Button("Vencimientos <u>o</u>rd");
		btn_precios= new Button("P<u>r</u>ecios");
		btn_precios_constantes= new Button("Precios Cons");
		btn_logueo= new Button("Lo<u>g</u>ueo");

		menu.add(btn_productos);
		menu.add(btn_listas);
		menu.add(btn_venc);
		menu.add(btn_venc_ord);
		menu.add(btn_precios);
		menu.add(btn_precios_constantes);
		menu.add(btn_logueo);



//		panel.add(btn_productos);
//		panel.add(btn_listas);
		panel.add(menu);
		agregar_listener();
	}

	private void agregar_listener() {
		
		btn_productos.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
//				panel.clear();
				History.newItem("PantallaProductos");}
		});		
		btn_productos.setAccessKey('p');
		
		btn_listas.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				panel.clear();
				History.newItem("PantallaListaDeCompras");}
		});		
		btn_listas.setAccessKey('l');

		btn_venc.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				panel.clear();
				History.newItem("PantallaVencimientos");}
		});	
		btn_venc.setAccessKey('v');
		
		btn_venc_ord.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				panel.clear();
				History.newItem("PantallaVencimientosOrd");}
		});		
		btn_venc_ord.setAccessKey('o');

		btn_precios.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				panel.clear();
				History.newItem("PantallaPrecios");}
		});		
		btn_precios.setAccessKey('r');
		
		btn_precios_constantes.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				panel.clear();
				History.newItem("PantallaPreciosConstantes");}
		});
		
		btn_logueo.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				panel.clear();
				History.newItem("PantallaLoguearseSimple");}
		});		
		btn_logueo.setAccessKey('g');
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
		
		this.proxy_precios= (ProxyPantallaPreciosAsync)
				GWT.create(ProxyPantallaPrecios.class);
				super.inicializar((ServiceDefTarget) this.proxy_precios, "Precios");
	}

}
