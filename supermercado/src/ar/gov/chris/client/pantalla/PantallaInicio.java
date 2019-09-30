package ar.gov.chris.client.pantalla;


import ar.gov.chris.client.clases.BuscadorDatosEstaticos;
import ar.gov.chris.client.interfaces.ProxyPantallaListas;
import ar.gov.chris.client.interfaces.ProxyPantallaListasAsync;
import ar.gov.chris.client.interfaces.ProxyPantallaPrecios;
import ar.gov.chris.client.interfaces.ProxyPantallaPreciosAsync;
import ar.gov.chris.client.interfaces.ProxyPantallaProductos;
import ar.gov.chris.client.interfaces.ProxyPantallaProductosAsync;
import ar.gov.chris.client.widgets.MensajeAlerta;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class PantallaInicio extends Pantalla {
	
	protected ProxyPantallaListasAsync proxy_listas;
	protected ProxyPantallaProductosAsync proxy_prod;
	protected ProxyPantallaPreciosAsync proxy_precios;


	protected HorizontalPanel menu;
	protected HorizontalPanel menu_version;
	
	Button btn_productos;
	Button btn_listas;
	Button btn_venc;
	Button btn_venc_ord;
	Button btn_precios;
	Button btn_precios_constantes;
	Button btn_logueo;

	protected final Label label_version= new Label("SCLS - VERSIÓN: v"+ VERSION + " _");
	protected Label label_nombre_maquina= new Label();
	protected Label label_login= new Label();

	
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
		menu_version= new HorizontalPanel();
		btn_productos= new Button("<u>P</u>roductos");
		btn_listas= new Button("<u>L</u>istas");
		btn_venc= new Button("<u>V</u>encimientos");
		btn_venc_ord= new Button("Vencimientos <u>o</u>rd");
		btn_precios= new Button("P<u>r</u>ecios");
		btn_precios_constantes= new Button("Precios Cons");
		btn_logueo= new Button("Lo<u>g</u>ueo");
		
		label_nombre_maquina.setText("_ "+BuscadorDatosEstaticos.nombre_maquina.toLowerCase());
//		nombre_maquina.setText("_ "+NOMBRE_MAQUINA.toLowerCase());

		label_login.setText(" __ ¡Bienvenido "+Cookies.getCookie("usr")+ "!");

		menu.add(btn_productos);
		menu.add(btn_listas);
		menu.add(btn_venc);
		menu.add(btn_venc_ord);
		menu.add(btn_precios);
		menu.add(btn_precios_constantes);
		menu.add(btn_logueo);
		
		menu.setStyleName("menu-fijo");
		
//		label_version.setStyleName("version");
		
//		menu_version.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		
		menu_version.add(label_version);
		menu_version.add(label_nombre_maquina);
		menu_version.add(label_login);
		menu_version.setStyleName("version");
		menu_version.getElement().getStyle().setDisplay(Display.BLOCK);




//		panel.add(btn_productos);
//		panel.add(btn_listas);
		panel.add(menu);
		panel.add(menu_version);
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
