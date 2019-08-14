package ar.gov.chris.client.pantalla;

import java.util.Date;
import java.util.LinkedList;
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
import ar.gov.chris.client.datos.DatosLista;
import ar.gov.chris.client.datos.DatosListaProdCompleta;
import ar.gov.chris.client.datos.DatosProducto;
import ar.gov.chris.client.gwt.OraculoConComodin;
import ar.gov.chris.client.interfaces.ProxyPantallaListas;
import ar.gov.chris.client.interfaces.ProxyPantallaListasAsync;
import ar.gov.chris.client.interfaces.ProxyPantallaProductos;
import ar.gov.chris.client.interfaces.ProxyPantallaProductosAsync;
import ar.gov.chris.client.widgets.MensajeAlerta;
import ar.gov.chris.client.widgets.WidgetMostrarProductos;
import ar.gov.chris.client.util.JavaScript;
import ar.gov.chris.client.util.Mate;

public class PantallaVistaDeCompra extends PantallaInicio {

	private ProxyPantallaListasAsync proxy_listas;
	private ProxyPantallaProductosAsync proxy_prod;

	private int id_compra;
	private Date fecha_compra;
	private String id_compra_str;

	private boolean ver_marcados;
	private Button btn_ver_marcados;
	private Button btn_marcar_productos;
	private Button btn_desmarcar_productos;


	private Button btn_ir_a_inicio;
	private Button btn_agregar_prod;
	
	private OraculoConComodin oraculo_productos= new OraculoConComodin();
	private SuggestBox sb_productos= new SuggestBox(oraculo_productos);
	private ListBox cant_prod;
	private WidgetMostrarProductos prod;
	
	private LinkedList<DatosProducto> lista_productos;
	protected Button boton_imprimir= new Button("Imprimir");
	
	private float descuento_coto;
	private int porcentaje_de_descuento;
	
	private Button btn_deshabilitar_botones;
	protected boolean botones_habilitados;


	public PantallaVistaDeCompra() {
//		super();
//		inicializar();
	}
	
	public PantallaVistaDeCompra(String id) {
		super();
//		inicializar();
//		id_compra_str= id;
		try {
		id_compra= Integer.parseInt(id);
		existe_lista(id_compra);
		} catch (NumberFormatException e){
			MensajeAlerta.mensaje_error("Error: id de compra mal formado");
			History.newItem(Supermercado.PANTALLA_INICIO);
		}
		}
	
//	@Override
//	protected void pantalla_principal2() {
//		panel.clear();

//		btn_agregar_lista= new Button("Nueva Lista");
//		panel.add(btn_agregar_lista);
//		
//		agregar_lista= new WidgetAgregarLista(this);
//		agregar_handlers();
		
//		super.pantalla_principal();
//		try {
//			id_compra= Integer.parseInt(id_compra_str);
//			existe_lista(id_compra);
//			} catch (NumberFormatException e){
//				MensajeAlerta.mensaje_error("Error: id de compra mal formado");
//				History.newItem(Supermercado.PANTALLA_INICIO);
//			}
//	}

	
//	private void existe_lista(final int id_compra) {
//		proxy_listas.existe_lista(id_compra, new AsyncCallback<Void> (){
//
//			@Override
//			public void onFailure(Throwable caught) {
//				MensajeAlerta.mensaje_error("Error: " + caught.getMessage());	
//				History.newItem(Supermercado.PANTALLA_INICIO);
//			}
//
//			@Override
//			public void onSuccess(Void result) {
//				
//				proxy_listas.lista_esta_visible(id_compra, new AsyncCallback<Integer> (){
//
//					@Override
//					public void onFailure(Throwable caught) {
//						MensajeAlerta.mensaje_error("Error: " + caught.getMessage());	
//						History.newItem(Supermercado.PANTALLA_INICIO);
//					}
//
//					@Override
//					public void onSuccess(Integer result) {
//						ver_marcados= result==1;
//						armar_pantalla_principal();
//						
//					}
//					
//				});
//			}
//			
//		});
//	}
	
	private void existe_lista(final int id_compra) {
		proxy_listas.existe_lista(id_compra, new AsyncCallback<Void> (){

			@Override
			public void onFailure(Throwable caught) {
				MensajeAlerta.mensaje_error("Error: " + caught.getMessage());	
				History.newItem(Supermercado.PANTALLA_INICIO);
			}

			@Override
			public void onSuccess(Void result) {
				
				proxy_listas.lista_esta_visible(id_compra, new AsyncCallback<DatosLista> (){

					@Override
					public void onFailure(Throwable caught) {
						MensajeAlerta.mensaje_error("Error: " + caught.getMessage());	
						History.newItem(Supermercado.PANTALLA_INICIO);
					}

					@Override
					public void onSuccess(DatosLista result) {
						ver_marcados= result.isVer_marcados();
						fecha_compra= result.getFecha();
						botones_habilitados= result.isBotones_habilitados();
						armar_pantalla_principal();
						
					}
					
				});
			}
			
		});
	}
	
//	@Override
	protected void armar_pantalla_principal() {
//		panel.clear();

		cant_prod= new ListBox();
		
		for(int i=0; i < 13; i++) {
			
			Integer intObj = new Integer(i+1);
			String numero= Integer.toString(intObj);
			cant_prod.addItem(numero);
		}
	 proxy_prod.buscar_productos(id_compra, new AsyncCallback<Set<DatosProducto>>() {
		 
		 public void onSuccess(Set<DatosProducto> lista_prod) {
		  for (DatosProducto p : lista_prod) {
			  
			  oraculo_productos.add(p.getNombre());
		  }
		  mostrar_pantalla();
		 }
							
		 
		 public void onFailure(Throwable caught) {
				History.newItem("PantallaLoguearseSimple-PantallaVistaDeCompra-"+id_compra);

			 MensajeAlerta.mensaje_error("Error al buscar los productos disponibles para agregar a esta compra: " + caught.getMessage());

		 }
	 });
	}

	protected void mostrar_pantalla() {
		proxy_prod.buscar_productos_lista(id_compra, new AsyncCallback<DatosListaProdCompleta>() {
		 
		 public void onSuccess(DatosListaProdCompleta lista_prod_completa) {
		  
			 
//			 Collections.sort(lista_prod);
			 
		   Set<DatosProducto> lista_prod= lista_prod_completa.getLista_prod();
		   lista_productos= ordenar_productos(lista_prod);
		   
		   
			 
//		   btn_ir_a_inicio= new Button("Ir a Inicio");
//		   panel.add(btn_ir_a_inicio);
		   sb_productos.setWidth("300px");
		   HorizontalPanel h = new HorizontalPanel();
		   VerticalPanel vp_prod= new VerticalPanel();
		   vp_prod.add(sb_productos);
		   vp_prod.add(new Label("[Usar * para ver todos]"));
//		   sb_productos.setFocus(true);
		   h.add(vp_prod);
		   h.add(cant_prod);
		   btn_ver_marcados= new Button("Ver/Ocultar marcados");
		   btn_marcar_productos= new Button("Marcar productos");
		   btn_desmarcar_productos= new Button("Desmarcar productos");
		   btn_deshabilitar_botones= new Button("Deshabilitar botones");
		   


		   btn_agregar_prod= new Button("Agregar producto");
		   
//			deshabilitar_botones(botones_habilitados);

		   
		   panel.add(h);
		   
		   //*************************************************************************************************************************************
		   //TODO: Hoy 03-02-2018 me doy cuenta que el eclipse me subraya desc_coto de naranja diciendome:
		   // "The value of the local variable desc_coto is not used"
		   // y efectivamente YO NO veo donde corno uso esta variable... PERO, si comento/borro esta linea
		   // cuando cargo la pantalla de vista de una compra, no se dibuja la pantalla mas alla del texbox
		   // del descuento, justamente
		   //TODO: Hoy 23/05/2019 me doy cuenta que no hacia falta guardara el resultado en una variable local ya que
		   //el valor del descuento lo guardé en la variable privada de la clase descuento_coto !!!!
		   
//		   float desc_coto=
		   
//		   get_descuento_coto(id_compra);
		   
		   
		   descuento_coto= lista_prod_completa.getDescuento_del_super();
		   porcentaje_de_descuento= lista_prod_completa.getPorcentaje_de_descuento();
			
			prod= new WidgetMostrarProductos(lista_productos, "Vista de compra", id_compra, PantallaVistaDeCompra.this,
					descuento_coto, fecha_compra, porcentaje_de_descuento);
			  
				HorizontalPanel hp = new HorizontalPanel();
				
				hp.add(btn_marcar_productos);

				hp.add(btn_desmarcar_productos);


				hp.add(boton_imprimir);
				hp.add(btn_ver_marcados);
				
				hp.add(btn_deshabilitar_botones);
				hp.add(btn_agregar_prod);

//			   panel.add(boton_imprimir);
//			   panel.add(btn_ver_marcados);
//			   panel.add(btn_agregar_prod);
//			   
			   panel.add(hp);
			   
			   panel.add(prod);
			   
			   deshabilitar_botones(botones_habilitados);

			   agregar_handlers();
		   
		 //***************************************************************************************************************************************
//		   prod= new WidgetMostrarProductos(lista_productos, "Vista de compra", id_compra, PantallaVistaDeCompra.this, desc_coto);
//		   panel.add(h);
//		   panel.add(boton_imprimir);
//		   panel.add(btn_ver_marcados);
//
//		   panel.add(btn_agregar_prod);
//		   panel.add(prod);
//
//		   agregar_handlers();
	}
		 public void onFailure(Throwable caught) {
				History.newItem("PantallaLoguearseSimple-PantallaVistaDeCompra-"+id_compra);

			 MensajeAlerta.mensaje_error("Error al buscar los productos de la compra: " + id_compra + " " +caught.getMessage());

			 }
		});
	}
	
	private void agregar_handlers() {
		
		btn_marcar_productos.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				Set<String> prods_seleccionadas= prod.obtener_seleccionados();
				if (prods_seleccionadas.size() > 0) {
//						marcar_varios(prods_seleccionadas);
						marcar_productos_afectados(prods_seleccionadas);

					
				} else {
					MensajeAlerta.mensaje_error("No se han seleccionado productos " +
					"para marcar");
				}
			}
		});
		
		btn_desmarcar_productos.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				Set<String> prods_seleccionadas= prod.obtener_seleccionados();
				if (prods_seleccionadas.size() > 0) {
//						marcar_varios(prods_seleccionadas);
						desmarcar_productos_afectados(prods_seleccionadas);

					
				} else {
					MensajeAlerta.mensaje_error("No se han seleccionado productos " +
					"para desmarcar");
				}
			}
		});
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
	    sb_productos.setAccessKey('s');

	    
//	    btn_ir_a_inicio.addClickHandler(new ClickHandler() {
//			
//			@Override
//			public void onClick(ClickEvent event) {
//				History.newItem(Supermercado.PANTALLA_INICIO);
//				History.fireCurrentHistoryState();
//			}
//		});
	    
	    boton_imprimir.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				imprimir_datos_en_pantalla();	
			}
		});
	    
	    btn_ver_marcados.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				mostrar_ocultar_prod_en_lista(!ver_marcados);
				}
		});
	    
	    btn_deshabilitar_botones.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				deshabilitar_botones(botones_habilitados);	
			}
		});
	}
	
//	protected void marcar_varios(Set<String> prods_seleccionadas) {
//		prod.
//	}

	protected void deshabilitar_botones(boolean botones_habilitados) {
		btn_desmarcar_productos.setEnabled(botones_habilitados);
		btn_ver_marcados.setEnabled(botones_habilitados);
		btn_marcar_productos.setEnabled(botones_habilitados);
		btn_agregar_prod.setEnabled(botones_habilitados);
		this.botones_habilitados= !botones_habilitados;
		prod.deshabilitar_todos_los_botones(botones_habilitados);
		
		proxy_listas.hab_deshab_botones(String.valueOf(id_compra),  botones_habilitados, new AsyncCallback<Void>(){
			public void onFailure(Throwable caught) {
				MensajeAlerta.mensaje_error("Ocurrio un error al intentar borrar " +
						"el producto de la lista: " + caught.getMessage());
			}
			public void onSuccess(Void result) {
				
//				prod.actualizar_producto(datos_prod);

//				Window.Location.reload();
			}
			
		});
		
	}

	protected void mostrar_ocultar_prod_en_lista(boolean ver_marcados) {
		{
			
			DatosLista datos_lista= new DatosLista();
			datos_lista.setId(id_compra);
			datos_lista.setVer_marcados(ver_marcados);
			
			proxy_listas.actualizar_lista(datos_lista, false, new AsyncCallback<Void>(){
				public void onFailure(Throwable caught) {
					MensajeAlerta.mensaje_error("Ocurrio un error al intentar mostrar u ocultar " +
							"los productos en la lista: " + caught.getMessage());
				}
				public void onSuccess(Void result) {
					
					Window.Location.reload();

				}
				
			});
					
		}		
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
		
		String productos= "";

		//Armo el encabezado.
		encabezado="<table border=1 cellpadding=\"30\" width =100%>" +
				"<tr><td align=\"center\"><font size=\"+1\"</font>Impresion de lista de supermercado</td></tr></table>";

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
		String tabla_productos= "";
		tabla_productos+= "<table border=1 width =100%>";

		tabla_productos+= "<tr>";
		tabla_productos+= "<td>" + "Producto" + "</td>\n";
		tabla_productos+= "<td>" + "Cantidad" + "</td>";
		tabla_productos+= "<td>" + "Precio" + "</td>";
		

		tabla_productos+= "</tr>";

		LinkedList<DatosProducto> prods= lista_productos;
		for (DatosProducto prod: prods) {
			tabla_productos+=  "<tr>";
			tabla_productos+= "<td align=\"center\">" +
					prod.getNombre() + "</td>\n";
			
			tabla_productos+= "<td align=\"center\">";
			tabla_productos+= prod.getCantidad() + "</td>";
			
			tabla_productos+= "<td align=\"center\">";
			tabla_productos+= Mate.poner_dos_decimales(prod.getPrecio()) + "</td>";
			
			tabla_productos+=  "</tr>";
		}
		tabla_productos+= "</table>";
		return tabla_productos;
	}

	//TODO: Esta funcion esta repetida en WidgetMostrarProductos
	//      ver de dejarla en un solo sitio.
//	private float poner_dos_decimales(float precio_total) {
//		return (float) (Math.round(precio_total*100)/100.0d);
//	}
	
	private void agregrar_prod_en_lista() {
		final DatosProducto datos_prod= new DatosProducto();
		datos_prod.setNombre(sb_productos.getText());
		int cant= cant_prod.getSelectedIndex()+1;
		proxy_prod.agregar_producto_a_lista(datos_prod, id_compra, cant, 0, new AsyncCallback<DatosProducto>(){
			public void onFailure(Throwable caught) {
				MensajeAlerta.mensaje_error("Ocurrio un error al intentar agregar " +
						"el producto en la lista: " + caught.getMessage());
			}
			public void onSuccess(DatosProducto result) {
				
				prod.insertar_producto("Vista de compra", PantallaVistaDeCompra.this, result, true);
				//Ac� (por ahora al menos) le paso 1 como parametro, porque como estoy
				// en la funcion que acaba de agregar un producto, seguro que al menos, hay un prod.
				prod.insertar_final(1);
				
				lista_productos.add(result);
//				Window.Location.reload();
				
				sb_productos.setText("");
//				MensajeAlerta.mensaje_info("Prducto agregago correctamente");
//				agregar_item_historial_cliente(datos_item);
//				recargar_personas();
			}
					
		});
				
	}

	public void borra_producto_de_lista(final DatosProducto produ, int id_compra) {
		proxy_prod.borra_producto_de_lista(produ, id_compra, new AsyncCallback<Void>(){
			public void onFailure(Throwable caught) {
				MensajeAlerta.mensaje_error("Ocurri� un error al intentar borrar " +
						"el producto de la lista: " + caught.getMessage());
			}
			public void onSuccess(Void result) {
//				Window.Location.reload();

				
				prod.remover_producto(produ.getNombre(), produ.getCant_en_gramos());
				
							
				boolean pudo_borrar=lista_productos.remove(produ);
//				if(!pudo_borrar)
//					MensajeAlerta.mensaje_error("No se pudo borrar el elemento de la lista de impresion.");
//				agregar_item_historial_cliente(datos_item);
//				recargar_personas();
			}
			
		});			
	}


	public void actualizar_producto(final DatosProducto datos_prod, final boolean es_marcar) {

		proxy_prod.actualizar_producto_a_lista(datos_prod, String.valueOf(id_compra), false, new AsyncCallback<Void>(){
			public void onFailure(Throwable caught) {
				MensajeAlerta.mensaje_error("Ocurrio un error al intentar borrar " +
						"el producto de la lista: " + caught.getMessage());
			}
			public void onSuccess(Void result) {
				
				prod.actualizar_producto(datos_prod, es_marcar);

//				Window.Location.reload();
			}
			
		});			
	}

	


	public void borrar_lista(int id_compra) {
		proxy_listas.borrar_lista(id_compra, new AsyncCallback<Void>(){
			public void onFailure(Throwable caught) {
				MensajeAlerta.mensaje_error("Ocurrio un error al intentar borrar " +
						"el producto de la lista: " + caught.getMessage());
			}
			public void onSuccess(Void result) {
				Window.Location.reload();
			}
			
		});
		
	}
	
	public int getId_compra() {
		return id_compra;
	}


	public void setId_compra(int id_compra) {
		this.id_compra = id_compra;
	}
	
	public boolean isVer_marcados() {
		return ver_marcados;
	}


	public void setVer_marcados(boolean ver_marcados) {
		this.ver_marcados = ver_marcados;
	}

	public void set_descuento_o_porcentaje(int num_compra, float desc, int porcentaje) {
		
		DatosLista dl= new DatosLista();
		dl.setId(num_compra);
		dl.setDesc_coto(desc);
		dl.setPorcentaje_descuento(porcentaje);
		proxy_listas.actualizar_lista(dl, true, new AsyncCallback<Void>(){
			public void onFailure(Throwable caught) {
				MensajeAlerta.mensaje_error("Ocurrio un error al intentar agregar " +
						"el descuento ó el porcentaje de descuento a la compra: " + caught.getMessage());
			}
			public void onSuccess(Void result) {
//				Window.Location.reload();
			}
			
		});		
	}
	
	
//public void set_porcentaje_de_descuento(int num_compra, int porcentaje, float descuento) {
//		
//		DatosLista dl= new DatosLista();
//		dl.setId(num_compra);
//		dl.setPorcentaje_descuento(porcentaje);
//		proxy_listas.actualizar_lista(dl, true, new AsyncCallback<Void>(){
//			public void onFailure(Throwable caught) {
//				MensajeAlerta.mensaje_error("Ocurrio un error al intentar agregar " +
//						"el porcentaje de descuento a la compra: " + caught.getMessage());
//			}
//			public void onSuccess(Void result) {
////				Window.Location.reload();
//			}
//			
//		});		
//	}
	
//	public float get_descuento_coto(final int id_compra) {
//		
//		proxy_listas.buscar_desc_coto(id_compra, new AsyncCallback<Float> (){
//
//			@Override
//			public void onFailure(Throwable caught) {
//				MensajeAlerta.mensaje_error("Error: " + caught.getMessage());					
//			}
//
//			@Override
//			public void onSuccess(Float result){
//				descuento_coto=result;
//				
//				prod= new WidgetMostrarProductos(lista_productos, "Vista de compra", id_compra, PantallaVistaDeCompra.this, descuento_coto, fecha_compra, 20);
//				  
//					HorizontalPanel hp = new HorizontalPanel();
//					
//					hp.add(btn_marcar_productos);
//
//					hp.add(btn_desmarcar_productos);
//
//
//					hp.add(boton_imprimir);
//					hp.add(btn_ver_marcados);
//					
//					hp.add(btn_deshabilitar_botones);
//					hp.add(btn_agregar_prod);
//
////				   panel.add(boton_imprimir);
////				   panel.add(btn_ver_marcados);
////				   panel.add(btn_agregar_prod);
////				   
//				   panel.add(hp);
//				   
//				   panel.add(prod);
//				   
//				   deshabilitar_botones(botones_habilitados);
//
//				   agregar_handlers();
//			}
//		
//	});
//		return descuento_coto;
//}
	
	/** Quita a las personas afectadas indicadas del caso.
	 * 
	 * @param ids Ids de las personas a quitar.
	 */
	public void marcar_productos_afectados(final Set<String> ids) {
		
//		Set<String> solo_ids= new HashSet<String>();
		proxy_prod.marcar_desmarcar_productos(String.valueOf(id_compra), ids, true, new AsyncCallback<Void>(){
			public void onFailure(Throwable caught) {
				MensajeAlerta.mensaje_error("Ocurrio un error al intentar borrar " +
						"el producto de la lista: " + caught.getMessage());
			}
			public void onSuccess(Void result) {
				
//				prod.actualizar_producto(datos_prod);

				Window.Location.reload();
			}
			
		});	
		
		
//		
//		String ids_personas= "";
//		for(Iterator<String> iter= ids.iterator(); iter.hasNext();) {
//			ids_personas+= iter.next() + " - ";
//		}
		
		
	}
	
	
	/** Quita a las personas afectadas indicadas del caso.
	 * 
	 * @param ids Ids de las personas a quitar.
	 */
	public void desmarcar_productos_afectados(final Set<String> ids) {
		
		proxy_prod.marcar_desmarcar_productos(String.valueOf(id_compra), ids,  false, new AsyncCallback<Void>(){
			public void onFailure(Throwable caught) {
				MensajeAlerta.mensaje_error("Ocurrio un error al intentar borrar " +
						"el producto de la lista: " + caught.getMessage());
			}
			public void onSuccess(Void result) {
				
//				prod.actualizar_producto(datos_prod);

				Window.Location.reload();
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
