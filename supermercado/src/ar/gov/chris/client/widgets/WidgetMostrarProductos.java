package ar.gov.chris.client.widgets;

import java.util.Date;
import java.util.LinkedList;

import ar.gov.chris.client.Supermercado;
import ar.gov.chris.client.datos.DatosProducto;
import ar.gov.chris.client.pantalla.Pantalla;
import ar.gov.chris.client.pantalla.PantallaProductos;
import ar.gov.chris.client.pantalla.PantallaVistaDeCompra;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;

public class WidgetMostrarProductos extends Composite {

	private FlowPanel principal;
	private FlexTable lista_prod;
	private Label titulo_label;
	private Label fecha_compra_lablel;
	private Label cant_prod_label;
	private Label subtotal_literal_label;
	private Label subtotal_compra_label;

	private Label desc_coto_literal_label;
	private Label desc_coto_label;

	private Label desc_coto_decripcion= new Label("Ingrese su descuento y presione tab");
	private TextBox desc_coto= new TextBox();
	
	private Label desc_tarj_literal_label;
	private Label desc_tarj_label;
	
	private Label total_literal_label;
	Label total_final_label;
	

	private int next_row;
	private int next_col=0;
	private Pantalla parent;

	private static int CANT_FILAS_FINAL= 3;
	
	int cant_prod= 0;


	//	private DatosProducto prod_actual;


//	private PushButton btn_borrar;
//	private PushButton btn_actualizar;
//	private PushButton btn_marcar;


	private WidgetConfirmar confirmar_borrado;
//	private WidgetAgregarProducto actualizar_prod;
	protected float desc_coto_float;
	
	private float total=0;
	float subtotal_compra=0;
	private String nombre_prod_ant="";
	


	/** Crea un {@link WidgetMostrarProductos} a partir de los par�metros.
	 * 
	 * @param lista_productos Lista de l�neas a mostrar.
	 * @param titulo Titulo del widget.
	 * @param fecha_compra 
	 */
	public WidgetMostrarProductos(final LinkedList<DatosProducto> lista_productos, 
			String titulo, final int num_compra, final Pantalla parent, final float descuento_coto, Date fecha_compra) {
			
//		cant_prod= lista_productos.size();

		this.parent= parent;
		this.desc_coto.setText(String.valueOf(descuento_coto));
		this.desc_coto_float= descuento_coto;
		principal= new FlowPanel();
		lista_prod= new FlexTable();
		HorizontalPanel hp = new HorizontalPanel();
		
		titulo_label= new Label(titulo);
		titulo_label.addStyleName("LabelDistinguido");
		
		hp.add(titulo_label);
		if(fecha_compra != null) {
			fecha_compra_lablel= new Label("-" + fecha_compra.toString());
			fecha_compra_lablel.addStyleName("LabelDistinguido");
			hp.add(fecha_compra_lablel);
		}
//		if(titulo.equalsIgnoreCase("Vista de compra")) {
//			cant_prod_label= new Label("Esta compra tiene: " + cant_prod + " productos.");
//		} else 
//			cant_prod_label= new Label("Cantidad de productos registrados: " + cant_prod);
//		
		cant_prod_label= new Label();
		
		cant_prod_label.setStyleName("HeaderTablas");
		
		subtotal_literal_label= new Label("Subtotal");
		subtotal_literal_label.addStyleName("LabelDistinguido");
		subtotal_compra_label= new Label();
		subtotal_compra_label.addStyleName("LabelDistinguido");
		
		desc_coto_literal_label= new Label("Desc Coto");
		desc_coto_literal_label.addStyleName("LabelDistinguido");
		desc_coto_label= new Label();
		desc_coto_label.addStyleName("LabelDistinguido");
		
		desc_tarj_literal_label= new Label("Desc Tarj 20%");
		desc_tarj_literal_label.addStyleName("LabelDistinguido");
		desc_tarj_label= new Label();
		desc_tarj_label.addStyleName("LabelDistinguido");
		
		total_literal_label= new Label("Total");
		total_literal_label.addStyleName("LabelDistinguido");
		total_final_label= new Label();
		total_final_label.addStyleName("LabelDistinguido");
		

		Label id_prod_label= new Label("ID");

		Label nombre_prod_label= new Label("Producto");
		Label precio_label= new Label("Precio");
		Label cant_label= new Label("Cantidad");
		Label total_label= new Label("Precio total");
		Label borrar_label= new Label("Borrar");
		Label actualizar_label= new Label("Actualizar");
		Label marcar_label= new Label("Marcar");
		
		
		lista_prod.setWidget(0, next_col, id_prod_label);
		next_col++;
		lista_prod.setWidget(0, next_col, nombre_prod_label);
		next_col++;
		lista_prod.setWidget(0, next_col, precio_label);
		next_col++;

		if(titulo.equalsIgnoreCase("Vista de compra")) {
			lista_prod.setWidget(0, next_col, cant_label);
			next_col++;
			lista_prod.setWidget(0, next_col, total_label);
			next_col++;
		}
		lista_prod.setWidget(0, next_col, borrar_label);

		//		if(!titulo.equalsIgnoreCase("Vista de compra")) {
		lista_prod.setWidget(0, next_col, borrar_label);
		next_col++;
		//		}
		lista_prod.setWidget(0, next_col, actualizar_label);
		next_col++;
		
		if(titulo.equalsIgnoreCase("Vista de compra")) 

			lista_prod.setWidget(0, next_col, marcar_label);

		next_row= 1; 
		int tamanio_lista= lista_productos.size();
		int i=1;
		for (final DatosProducto prod : lista_productos) {
			
//		for (final DatosProducto prod : lista_productos) {

			next_col=0;

//			btn_borrar= new PushButton("Borrar");
//			btn_actualizar= new PushButton("Actualizar");
//			btn_marcar= new PushButton("Marcar");
//
//
//			final ClickHandler handler = new ClickHandler(){
//				public void onClick(ClickEvent arg0) {
//					if(parent instanceof PantallaProductos) {
//						int fila_a_borrar= next_row;
//						lista_prod.removeRow(fila_a_borrar);
//						((PantallaProductos)parent).borrar_producto(prod.getNombre());
//						
//					} else if(parent instanceof PantallaVistaDeCompra)
//						((PantallaVistaDeCompra)parent).borra_producto_de_lista(prod.getNombre(), ((PantallaVistaDeCompra)parent).getId_compra());
//					//					hide();
//				}
//			};
//
//			btn_borrar.addClickHandler(new ClickHandler() {
//
//				@Override
//				public void onClick(ClickEvent event) {
//					//				confirmar_borrado= new WidgetConfirmar(parent, /*prod_actual*/prod, "Esta seguro que quiere borrar el producto", );
//					//				confirmar_borrado.show();
//					//****
//					confirmar_borrado= new WidgetConfirmar(parent, "Esta seguro que quiere borrar el producto", handler);
//					confirmar_borrado.show();
//					//****
//				}
//			});
//
//			btn_actualizar.addClickHandler(new ClickHandler() {
//
//				@Override
//				public void onClick(ClickEvent event) {
//					actualizar_prod= new WidgetAgregarProducto(parent, prod);
//					actualizar_prod.show();
//				}
//			});
//
//			
//			final int row_a_marcar= next_row;
//			
//			ClickHandler handler_marcar= new ClickHandler() {
//
//				@Override
//				public void onClick(ClickEvent event) {
//					boolean marcada= prod.isEsta_marcada();
//
//					if(marcada)
//						lista_prod.getRowFormatter().setStyleName(row_a_marcar, "ContenidoTablas");
//					else
//						lista_prod.getRowFormatter().setStyleName(row_a_marcar, "ComplejidadMedia");
//					prod.setEsta_marcada(!marcada);
//					((PantallaVistaDeCompra)parent).actualizar_producto(prod);
//					
//					
//				}
//			};
//
//			btn_marcar.addClickHandler(handler_marcar);
//			
//			//			prod_actual= prod;
//			//			agregar_handler();


			
			insertar_producto(titulo, parent, prod, false);
			//Esto es para que no me sume una fila mas al final.
			
			if(i < tamanio_lista)
				next_row++;
			i++;
		}
		
		if(titulo.equalsIgnoreCase("Vista de compra")) {
			desc_coto.addBlurHandler(new BlurHandler(){
				public void onBlur(BlurEvent event) {
					String aux= desc_coto.getText();
					if (!aux.isEmpty()) {
						try {
						desc_coto_float= Float.parseFloat(aux);
						} catch (NumberFormatException e){
							desc_coto.setText(String.valueOf(descuento_coto));
							MensajeAlerta.mensaje_error("Error: debe ingresar un importe valido");
							return;
						}
						//***
						String desc_coto_str= String.valueOf(poner_dos_decimales(desc_coto_float));
						desc_coto_label.setText(desc_coto_str);
						lista_prod.setWidget(next_row-2, 3, desc_coto_label);
//						lista_prod.setText(next_row-2, 3, String.valueOf(poner_dos_decimales(desc_coto_float)));

						//***
						
						
						float tot_aux= (subtotal_compra-desc_coto_float);
						total= tot_aux;
						float desc_tarj=tot_aux/100*20;
						total= total -(desc_tarj);
						
						
						
						//***
						
						String desc_tarj_str= String.valueOf(poner_dos_decimales(desc_tarj));
						desc_tarj_label.setText(desc_tarj_str);
						lista_prod.setWidget(next_row-1, 3, desc_tarj_label);
						
						
						//****
						
						
						
						String total_str= String.valueOf(poner_dos_decimales(total));
						total_final_label.setText(total_str);
						lista_prod.setWidget(next_row, 3, total_final_label);
						
						((PantallaVistaDeCompra)parent).set_descuento_coto(num_compra, desc_coto_float);
						
				}}});
			
			
			insertar_final(tamanio_lista);
			
		}

		lista_prod.setStyleName("PanelConBordesExpandido");
		lista_prod.getRowFormatter().setStyleName(0, "HeaderTablas");

//		principal.add(titulo_label);
		
		principal.add(hp);
		
//		principal.add(desc_coto_decripcion);
		if(parent instanceof PantallaVistaDeCompra) {
//		principal.add(fecha_compra_lablel);	
		principal.add(desc_coto_decripcion);
		principal.add(desc_coto);
		}
		principal.add(cant_prod_label);
		principal.add(lista_prod);
		initWidget(principal);
	}



	public void insertar_final(int tam_lista) {
		
		if(parent instanceof PantallaVistaDeCompra && tam_lista > 0)
			next_row++;
				
		lista_prod.setWidget(next_row, 2, subtotal_literal_label);
		String subtotal_compra_str= String.valueOf(poner_dos_decimales(subtotal_compra));
		subtotal_compra_label.setText(subtotal_compra_str);
		lista_prod.setWidget(next_row, 3, subtotal_compra_label);
		next_row++;

		lista_prod.setWidget(next_row, 2, desc_coto_literal_label);
		String desc_coto_str= String.valueOf(poner_dos_decimales(desc_coto_float));
		desc_coto_label.setText(desc_coto_str);
		lista_prod.setWidget(next_row, 3, desc_coto_label);
		next_row++;
		
		float tot_aux= (subtotal_compra-desc_coto_float);
		total= tot_aux;
		float desc_tarj=tot_aux/100*20;
		total= total -(desc_tarj);

		lista_prod.setWidget(next_row, 2, desc_tarj_literal_label);
		String desc_tarj_str= String.valueOf(poner_dos_decimales(desc_tarj));
		desc_tarj_label.setText(desc_tarj_str);
		lista_prod.setWidget(next_row, 3, desc_tarj_label);
		next_row++;

//			int desc_coto_int= Integer.parseInt(desc_coto.getText());
		
//			float tot_aux= (subtotal_compra-desc_coto_float);
//			total= tot_aux;
//			total= total - (tot_aux/100*20);
					
		lista_prod.setWidget(next_row, 2, total_literal_label);
		String total_str= String.valueOf(poner_dos_decimales(total));
		total_final_label.setText(total_str);
		lista_prod.setWidget(next_row, 3, total_final_label);
	}



	public void insertar_producto(String titulo, final Pantalla parent,
			final DatosProducto prod, boolean resetear_coordenadas) {
		
		if(resetear_coordenadas) {
			
//		next_row= 1;
			if(titulo.equalsIgnoreCase("Vista de compra"))
				next_row = next_row-CANT_FILAS_FINAL;
			else
			next_row++;
		next_col= 0;
		}
		//**********************
		
		PushButton btn_borrar;
		PushButton btn_actualizar;
		PushButton btn_marcar;
		
		btn_borrar= new PushButton("Borrar");
		btn_actualizar= new PushButton("Actualizar");
		btn_marcar= new PushButton("Marcar");


		final ClickHandler handler = new ClickHandler(){
			public void onClick(ClickEvent arg0) {
				if(parent instanceof PantallaProductos) {
//					int fila_a_borrar= next_row;
//					lista_prod.removeRow(fila_a_borrar);
					((PantallaProductos)parent).borrar_producto(prod.getNombre());
					
				} else if(parent instanceof PantallaVistaDeCompra)
					((PantallaVistaDeCompra)parent).borra_producto_de_lista(prod, ((PantallaVistaDeCompra)parent).getId_compra());
				//hide();
				confirmar_borrado.hide();
			}
		};

		btn_borrar.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				//				confirmar_borrado= new WidgetConfirmar(parent, /*prod_actual*/prod, "Esta seguro que quiere borrar el producto", );
				//				confirmar_borrado.show();
				//****
				confirmar_borrado= new WidgetConfirmar(parent, "Esta seguro que quiere borrar el producto", handler);
				confirmar_borrado.show();
				//****
			}
		});

		btn_actualizar.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				WidgetAgregarProducto actualizar_prod;
				actualizar_prod= new WidgetAgregarProducto(parent, prod);
				actualizar_prod.show();
			}
		});

		
		final int row_a_marcar= next_row;
		
		ClickHandler handler_marcar= new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				boolean marcada= prod.isEsta_marcada();

				if(marcada)
					lista_prod.getRowFormatter().setStyleName(row_a_marcar, "ContenidoTablas");
				else {
					if(((PantallaVistaDeCompra)parent).isVer_marcados())
						lista_prod.getRowFormatter().setStyleName(row_a_marcar, "ComplejidadMedia");
					else
						lista_prod.getRowFormatter().setVisible(row_a_marcar, false);
				}
				prod.setEsta_marcada(!marcada);
				((PantallaVistaDeCompra)parent).actualizar_producto(prod);
				
				
			}
		};

		btn_marcar.addClickHandler(handler_marcar);
		
		//			prod_actual= prod;
		//			agregar_handler();
		
		
		//*************************
		
		lista_prod.setText(next_row, next_col, String.valueOf(prod.getId()));
		next_col++;
		
		lista_prod.setText(next_row, next_col, prod.getNombre());
		next_col++;
		
		float precio= prod.getPrecio();
		
		precio = poner_dos_decimales(precio);
		
//		subtotal_compra+= precio;
		
		lista_prod.setText(next_row, next_col, String.valueOf(precio));
		next_col++;
		
		if(titulo.equalsIgnoreCase("Vista de compra")) {
			lista_prod.setText(next_row, next_col, ((prod.getCantidad()!=0) ? String.valueOf(prod.getCantidad()) : "NA"));
			next_col++;

			float precio_total= prod.getPrecio() * prod.getCantidad();
			subtotal_compra+= precio_total;
			
//				precio_total= (float) (Math.round(precio_total*100)/100.0d);
			
			lista_prod.setText(next_row, next_col, String.valueOf(poner_dos_decimales(precio_total)));
			next_col++;
		}
		lista_prod.setWidget(next_row, next_col, btn_borrar);
		next_col++;

		lista_prod.setWidget(next_row, next_col, btn_actualizar);
		next_col++;
		
		if(titulo.equalsIgnoreCase("Vista de compra"))
			lista_prod.setWidget(next_row, next_col, btn_marcar);


		//		lista_lineas.setText(next_row, 4, prod.obtener_nro_serie());
		//		if (lista.size() > 1) {
		//			check_quitar.setFormValue(linea.obtener_nro_cuenta() + " " +
		//					linea.obtener_descripcion_categoria_accesorio() + " (" +linea.obtener_id() + ")");
		//			lista_lineas.setWidget(next_row, 5, check_quitar);
		//		}
		
		if(prod.isEsta_marcada()) {
			if(((PantallaVistaDeCompra)parent).isVer_marcados())
				lista_prod.getRowFormatter().setStyleName(next_row, "ComplejidadMedia");
			else
				lista_prod.getRowFormatter().setVisible(next_row, false);
		} else {
			lista_prod.getRowFormatter().setStyleName(next_row, "ContenidoTablas");
		}
//		next_row++;
		
		cant_prod++;
		
		if(parent instanceof PantallaVistaDeCompra) {
			cant_prod_label.setText("Esta compra tiene: " + cant_prod + " productos."); //= new Label("Esta compra tiene: " + cant_prod + " productos.");
		} else 
			cant_prod_label.setText("Cantidad de productos registrados: " + cant_prod);
				
	}



	private float poner_dos_decimales(float precio_total) {
		return (float) (Math.round(precio_total*100)/100.0d);
	}



	public void remover_producto(String nombre) {

		//Esto es para evitar que me tire error cuando el metodo se ejecuta 2 veces... QUE NO SE PORQUE CORNO PASA...
		if(!nombre_prod_ant.equalsIgnoreCase(nombre)) {
			nombre_prod_ant= nombre;

			int filas_de_la_lista= lista_prod.getRowCount();
			if(parent instanceof PantallaVistaDeCompra)
				filas_de_la_lista= filas_de_la_lista-CANT_FILAS_FINAL;

			for(int i = 1; i < filas_de_la_lista; i++) {

				try {
					String nombre_prod= lista_prod.getText(i, 1);
										
					int fila = 0;
					if(nombre.equalsIgnoreCase(nombre_prod)) {
						fila= i; 
						
						if(parent instanceof PantallaVistaDeCompra) {
						//--------------------------------------------------------------

						String precio_prod_total= lista_prod.getText(i, 4);


						subtotal_compra= subtotal_compra- Float.parseFloat(precio_prod_total);
						String subtotal_compra_str= String.valueOf(poner_dos_decimales(subtotal_compra));
						subtotal_compra_label.setText(subtotal_compra_str);
						
						String desc_coto_str= String.valueOf(poner_dos_decimales(desc_coto_float));
						desc_coto_label.setText(desc_coto_str);
						
						
						float tot_aux= (subtotal_compra-desc_coto_float);
						total= tot_aux;
						float desc_tarj=tot_aux/100*20;
						total= total -(desc_tarj);

						String desc_tarj_str= String.valueOf(poner_dos_decimales(desc_tarj));
						desc_tarj_label.setText(desc_tarj_str);
						


									
						String total_str= String.valueOf(poner_dos_decimales(total));
						total_final_label.setText(total_str);
						}
						//--------------------------------------------------------------
						lista_prod.removeRow(fila);
						next_row--;
						cant_prod--;
						
						if(parent instanceof PantallaVistaDeCompra) {
							cant_prod_label.setText("Esta compra tiene: " + cant_prod + " productos."); //= new Label("Esta compra tiene: " + cant_prod + " productos.");
						} else 
							cant_prod_label.setText("Cantidad de productos registrados: " + cant_prod);
						break;
					}
				} catch (IndexOutOfBoundsException e) {
					MensajeAlerta.mensaje_error("Ocurrio un error al intentar borrar " +
							"el producto: " + e.getMessage());
					break;
				}
			}
		}
	}
	
	
public void actualizar_producto(DatosProducto datos) {
		
		int filas_de_la_lista= lista_prod.getRowCount();
		
		for(int i = 1; i < filas_de_la_lista; i++) {
		
			String id_prod= lista_prod.getText(i, 0);
			
			int fila = 0;
			int col = 1;

			if(datos.getId()==(Integer.parseInt(id_prod))) {
				fila= i; 
				
				lista_prod.setText(fila, col, datos.getNombre());
				col++;
				
				float precio= datos.getPrecio();
				
				precio = poner_dos_decimales(precio);
				
				subtotal_compra+= precio;
				
				lista_prod.setText(fila, col, String.valueOf(precio));
				col++;
				
				if(parent instanceof PantallaVistaDeCompra) { 

//				if(titulo.equalsIgnoreCase("Vista de compra")) {
					lista_prod.setText(fila, col, ((datos.getCantidad()!=0) ? String.valueOf(datos.getCantidad()) : "NA"));
					col++;

					float precio_total= datos.getPrecio() * datos.getCantidad();
					
//						precio_total= (float) (Math.round(precio_total*100)/100.0d);
					
					lista_prod.setText(fila, col, String.valueOf(poner_dos_decimales(precio_total)));
					col++;
								
				break;
				}
			}
		}
//		insertar_final();
	}

}
