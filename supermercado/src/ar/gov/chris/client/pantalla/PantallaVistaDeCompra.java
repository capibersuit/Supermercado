package ar.gov.chris.client.pantalla;

import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;


import ar.gov.chris.client.datos.DatosProducto;
import ar.gov.chris.client.gwt.OraculoConComodin;
import ar.gov.chris.client.interfaces.ProxyPantallaListas;
import ar.gov.chris.client.interfaces.ProxyPantallaListasAsync;
import ar.gov.chris.client.interfaces.ProxyPantallaProductos;
import ar.gov.chris.client.interfaces.ProxyPantallaProductosAsync;
import ar.gov.chris.client.widgets.MensajeAlerta;
import ar.gov.chris.client.widgets.WidgetMostrarProductos;

public class PantallaVistaDeCompra extends Pantalla {

	private ProxyPantallaListasAsync proxy_listas;
	private ProxyPantallaProductosAsync proxy_prod;

	private int id_compra;
	private Button btn_agregar_prod;
	
	private OraculoConComodin oraculo_productos= new OraculoConComodin();
	private SuggestBox sb_productos= new SuggestBox(oraculo_productos);
	private ListBox cant_prod;
	private WidgetMostrarProductos prod;

	public PantallaVistaDeCompra() {
//		super();
//		inicializar();
	}
	
	
	public PantallaVistaDeCompra(String id) {
		super();
		inicializar();
		id_compra= Integer.parseInt(id);
		pantalla_principal();
		
		}

	
	protected void pantalla_principal() {
		panel.clear();

		cant_prod= new ListBox();
		
		for(int i=0; i < 13; i++) {
			
			Integer intObj = new Integer(i+1);
//			cant_prod.setItemText(i, "noSePAraQueSirve");
			String numero= Integer.toString(intObj);
			int a = 2*3;
			a++;
			cant_prod.addItem(numero);
		}
	 proxy_prod.buscar_productos(new AsyncCallback<Set<DatosProducto>>() {
		 
		 public void onSuccess(Set<DatosProducto> lista_prod) {
		  for (DatosProducto p : lista_prod) {
			  
			  oraculo_productos.add(p.getNombre());
//			  lb_cat_totales.addItem(cat);
		  }
		  mostrar_pantalla();
		 }
							
		 
		 public void onFailure(Throwable caught) {
//		  error.setText(caught.getMessage());
//		  principal.add(error);
		 }
	 });
	}

	protected void mostrar_pantalla() {
		proxy_prod.buscar_productos_lista(id_compra, new AsyncCallback<Set<DatosProducto>>() {
		 
		 public void onSuccess(Set<DatosProducto> lista_prod) {
		  
		
		sb_productos.setWidth("300px");
		   HorizontalPanel h = new HorizontalPanel();
		   VerticalPanel vp_prod= new VerticalPanel();
		   vp_prod.add(sb_productos);
		   vp_prod.add(new Label("[Usar * para ver todos]"));
		   h.add(vp_prod);
		   h.add(cant_prod);
//		   hp_cat.add(vp_categ);
		   btn_agregar_prod= new Button("Agregar producto");
		   prod= new WidgetMostrarProductos(lista_prod, "Productos");
		   panel.add(h);
		   panel.add(btn_agregar_prod);
		   panel.add(prod);

		   agregar_handlers();
	}
		 public void onFailure(Throwable caught) {
//			  error.setText(caught.getMessage());
//			  principal.add(error);
			 }
		});
	}
	
	private void agregar_handlers() {
		btn_agregar_prod.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				agregrar_prod_en_lista();
				}
		});
		
	    sb_productos.addKeyPressHandler(new KeyPressHandler() {
			 @Override
			 public void onKeyPress(KeyPressEvent event) {
			  if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode())
				  btn_agregar_prod.click();
			 }
		 });
	}
	
	private void agregrar_prod_en_lista() {
		DatosProducto datos_prod= new DatosProducto();
		datos_prod.setNombre(sb_productos.getText());
		int cant= cant_prod.getSelectedIndex()+1;
		proxy_prod.agregar_producto_a_lista(datos_prod, id_compra, cant, new AsyncCallback<Void>(){
			public void onFailure(Throwable caught) {
				MensajeAlerta.mensaje_error("Ocurri� un error al intentar agregar " +
						"el producto en la lista: " + caught.getMessage());
			}
			public void onSuccess(Void result) {
				
				MensajeAlerta.mensaje_info("Prducto agregago correctamente");
//				agregar_item_historial_cliente(datos_item);
//				recargar_personas();
			}
			
		});
				
	}
	
	/** Se crea el proxy_carga para comunicarse con el servidor.
	 */
	protected void inicializar(){
		this.proxy_listas= (ProxyPantallaListasAsync)
		GWT.create(ProxyPantallaListas.class);
		super.inicializar((ServiceDefTarget) this.proxy_listas, "Listas");
		this.proxy_prod= (ProxyPantallaProductosAsync)
		GWT.create(ProxyPantallaProductos.class);
		super.inicializar((ServiceDefTarget) this.proxy_prod, "Productos");
	}
}
