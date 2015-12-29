package ar.gov.chris.client.pantalla;

import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;

import ar.gov.chris.client.Supermercado;
import ar.gov.chris.client.datos.DatosProducto;
import ar.gov.chris.client.gwt.OraculoConComodin;
import ar.gov.chris.client.interfaces.ProxyPantallaListas;
import ar.gov.chris.client.interfaces.ProxyPantallaListasAsync;
import ar.gov.chris.client.interfaces.ProxyPantallaProductos;
import ar.gov.chris.client.interfaces.ProxyPantallaProductosAsync;
import ar.gov.chris.client.widgets.MensajeAlerta;
import ar.gov.chris.client.widgets.WidgetMostrarProductos;
import ar.gov.chris.client.util.JavaScript;

public class PantallaVistaDeCompra extends Pantalla {

	private ProxyPantallaListasAsync proxy_listas;
	private ProxyPantallaProductosAsync proxy_prod;

	private int id_compra;
	private Button btn_ir_a_inicio;
	private Button btn_agregar_prod;
	
	private OraculoConComodin oraculo_productos= new OraculoConComodin();
	private SuggestBox sb_productos= new SuggestBox(oraculo_productos);
	private ListBox cant_prod;
	private WidgetMostrarProductos prod;
	
	private Set<DatosProducto> lista_productos;
	protected Button boton_imprimir= new Button("Imprimir");


	public PantallaVistaDeCompra() {
//		super();
//		inicializar();
	}
	
	
	public int getId_compra() {
		return id_compra;
	}


	public void setId_compra(int id_compra) {
		this.id_compra = id_compra;
	}


	public PantallaVistaDeCompra(String id) {
		super();
		inicializar();
		try {
		id_compra= Integer.parseInt(id);
		existe_lista(id_compra);
		} catch (NumberFormatException e){
			MensajeAlerta.mensaje_error("Error: id de compra mal formado");
			History.newItem(Supermercado.PANTALLA_INICIO);
		}
		
			
		}

	
	private void existe_lista(int id_compra) {
		proxy_listas.existe_lista(id_compra, new AsyncCallback<Void> (){

			@Override
			public void onFailure(Throwable caught) {
				MensajeAlerta.mensaje_error("Error: " + caught.getMessage());	
				History.newItem(Supermercado.PANTALLA_INICIO);
			}

			@Override
			public void onSuccess(Void result) {
				pantalla_principal();
				
					
				
			}
			
		});
	}


	protected void pantalla_principal() {
		panel.clear();

		cant_prod= new ListBox();
		
		for(int i=0; i < 13; i++) {
			
			Integer intObj = new Integer(i+1);
			String numero= Integer.toString(intObj);
			cant_prod.addItem(numero);
		}
	 proxy_prod.buscar_productos(new AsyncCallback<Set<DatosProducto>>() {
		 
		 public void onSuccess(Set<DatosProducto> lista_prod) {
		  for (DatosProducto p : lista_prod) {
			  
			  oraculo_productos.add(p.getNombre());
		  }
		  mostrar_pantalla();
		 }
							
		 
		 public void onFailure(Throwable caught) {
			 MensajeAlerta.mensaje_error("Error: " + caught.getMessage());

		 }
	 });
	}

	protected void mostrar_pantalla() {
		proxy_prod.buscar_productos_lista(id_compra, new AsyncCallback<Set<DatosProducto>>() {
		 
		 public void onSuccess(Set<DatosProducto> lista_prod) {
		  
		   lista_productos= lista_prod;
			 
		   btn_ir_a_inicio= new Button("Ir a Inicio");
		   panel.add(btn_ir_a_inicio);
		   sb_productos.setWidth("300px");
		   HorizontalPanel h = new HorizontalPanel();
		   VerticalPanel vp_prod= new VerticalPanel();
		   vp_prod.add(sb_productos);
		   vp_prod.add(new Label("[Usar * para ver todos]"));
//		   sb_productos.setFocus(true);
		   h.add(vp_prod);
		   h.add(cant_prod);
		   btn_agregar_prod= new Button("Agregar producto");
		   prod= new WidgetMostrarProductos(lista_prod, "Vista de compra", id_compra, PantallaVistaDeCompra.this);
		   panel.add(h);
		   panel.add(boton_imprimir);

		   panel.add(btn_agregar_prod);
		   panel.add(prod);

		   agregar_handlers();
	}
		 public void onFailure(Throwable caught) {
			 
			 MensajeAlerta.mensaje_error("Error: " + caught.getMessage());

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
	    sb_productos.setFocus(true);
	    
	    btn_ir_a_inicio.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				History.newItem(Supermercado.PANTALLA_INICIO);
				History.fireCurrentHistoryState();
			}
		});
	    
	    boton_imprimir.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				imprimir_datos_en_pantalla();	
			}
		});
	}
	
	/** Contruye el html con los datos del caso y los muestra en una ventana nueva.
	 */
	private void imprimir_datos_en_pantalla() {
		
		String datos_a_imprimir= armar_html();
		JavaScript.print_html(datos_a_imprimir, "Datos lista de compra", "datos_listas", 
				"menubar=yes,location=no,resizable=yes,scrollbars=yes,status=yes");
	}


	private String armar_html() {
		
		StringBuffer html_datos_pantalla= new StringBuffer();
		String encabezado="";
		String caso_y_fecha= "";	
		String ficista_asignado= "";	
		String datos_solicitud= "";	
		String descripcion= "";	
		String observaciones= "";	
		String productos= "";
		String recibi_conforme= "";	

		//Armo el encabezado.
		encabezado="<table border=1 cellpadding=\"30\" width =100%>" +
				"<tr><td align=\"center\"><font size=\"+1\"</font>Impresión de lista de supermercado</td></tr></table>";
						
//		caso_y_fecha= dibujar_caso_y_fecha();
//		ficista_asignado= dibujar_ficista_responsable();
//		datos_solicitud= dibujar_datos_solicitud();
//		descripcion= dibujar_descripcion();
//		observaciones= dibujar_observaciones();
		productos= dibujar_productos();
//		recibi_conforme= dibujar_conforme();
			
		html_datos_pantalla.append(encabezado);
		html_datos_pantalla.append("<br>");

		
		html_datos_pantalla.append("<strong>DETALLE DE COMPRA</strong>");
		html_datos_pantalla.append(productos);	
		html_datos_pantalla.append("<br><br><br>");
		
		return html_datos_pantalla.toString();
	}
	
	private String dibujar_productos() {
		String equipos_y_accesorios= "";
		equipos_y_accesorios+= "<table border=1 width =100%>";

		equipos_y_accesorios+= "<tr>";
		equipos_y_accesorios+= "<td>" + "Producto" + "</td>\n";
		equipos_y_accesorios+= "<td>" + "Precio" + "</td>";
		equipos_y_accesorios+= "<td>" + "Cantidad" + "</td>";

		equipos_y_accesorios+= "</tr>";

		Set<DatosProducto> prods= lista_productos;
		for (DatosProducto prod: prods) {
			equipos_y_accesorios+=  "<tr>";
			equipos_y_accesorios+= "<td align=\"center\">" +
					prod.getNombre() + "</td>\n";
			
			equipos_y_accesorios+= "<td align=\"center\">";
			equipos_y_accesorios+= prod.getPrecio() + "</td>";
			
			equipos_y_accesorios+= "<td align=\"center\">";
			equipos_y_accesorios+= prod.getCantidad() + "</td>";

			equipos_y_accesorios+=  "</tr>";
		}
		equipos_y_accesorios+= "</table>";
		return equipos_y_accesorios;
	}


	private void agregrar_prod_en_lista() {
		DatosProducto datos_prod= new DatosProducto();
		datos_prod.setNombre(sb_productos.getText());
		int cant= cant_prod.getSelectedIndex()+1;
		proxy_prod.agregar_producto_a_lista(datos_prod, id_compra, cant, new AsyncCallback<Void>(){
			public void onFailure(Throwable caught) {
				MensajeAlerta.mensaje_error("Ocurrió un error al intentar agregar " +
						"el producto en la lista: " + caught.getMessage());
			}
			public void onSuccess(Void result) {
				
				Window.Location.reload();
				
//				MensajeAlerta.mensaje_info("Prducto agregago correctamente");
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


	public void borra_producto_de_lista(String nombre, int id_compra) {
		proxy_prod.borra_producto_de_lista(nombre, id_compra, new AsyncCallback<Void>(){
			public void onFailure(Throwable caught) {
				MensajeAlerta.mensaje_error("Ocurri� un error al intentar borrar " +
						"el producto de la lista: " + caught.getMessage());
			}
			public void onSuccess(Void result) {
				Window.Location.reload();

//				agregar_item_historial_cliente(datos_item);
//				recargar_personas();
			}
			
		});			
	}


	public void actualizar_producto(DatosProducto datos_prod) {

		proxy_prod.actualizar_producto_a_lista(datos_prod, String.valueOf(id_compra), new AsyncCallback<Void>(){
			public void onFailure(Throwable caught) {
				MensajeAlerta.mensaje_error("Ocurrió un error al intentar borrar " +
						"el producto de la lista: " + caught.getMessage());
			}
			public void onSuccess(Void result) {
				Window.Location.reload();
			}
			
		});			
	}

	


	public void borrar_lista(int id_compra) {
		proxy_listas.borrar_lista(id_compra, new AsyncCallback<Void>(){
			public void onFailure(Throwable caught) {
				MensajeAlerta.mensaje_error("Ocurrió un error al intentar borrar " +
						"el producto de la lista: " + caught.getMessage());
			}
			public void onSuccess(Void result) {
				Window.Location.reload();
			}
			
		});
		
	}
}
