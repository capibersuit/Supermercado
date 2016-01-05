package ar.gov.chris.client.widgets;

import java.awt.Button;
import java.util.LinkedList;
import java.util.Set;

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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;

public class WidgetMostrarProductos extends Composite {

	private FlowPanel principal;
	private FlexTable lista_prod;
	private Label titulo_label;
	private Label cant_prod_label;
	private Label subtotal_literal_label;
	private Label subtotal_compra_label;

	private Label desc_coto_literal_label;
	private Label desc_coto_label;

	private Label desc_coto_decripcion= new Label("Ingrese el descuento realizado por coto");
	private TextBox desc_coto= new TextBox();
	
	private Label desc_tarj_literal_label;
	private Label desc_tarj_label;
	
	private Label total_literal_label;
	Label total_final_label;
	

	private int next_row;
	private int next_col=0;
	private Pantalla parent;



	//	private DatosProducto prod_actual;


	private PushButton btn_borrar;
	private PushButton btn_actualizar;
	private PushButton btn_marcar;


	private WidgetConfirmar confirmar_borrado;
	private WidgetAgregarProducto actualizar_prod;
	protected float desc_coto_float;
	
	private float total=0;
	float subtotal_compra=0;
	


	/** Crea un {@link WidgetMostrarLineas} a partir de los par�metros.
	 * 
	 * @param lista_productos Lista de l�neas a mostrar.
	 * @param titulo Titulo del widget.
	 */
	public WidgetMostrarProductos(final LinkedList<DatosProducto> lista_productos, 
			String titulo, int num_compra, final Pantalla parent) {

		this.parent= parent;
		principal= new FlowPanel();
		lista_prod= new FlexTable();
		titulo_label= new Label(titulo);
		titulo_label.addStyleName("LabelDistinguido");
		if(titulo.equalsIgnoreCase("Vista de compra")) {
			cant_prod_label= new Label("Esta compra tiene: " + lista_productos.size() + " productos.");
		} else 
			cant_prod_label= new Label("Cantidad de productos registrados: " + lista_productos.size());
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
		

		Label nombre_prod_label= new Label("Producto");
		Label precio_label= new Label("Precio");
		Label cant_label= new Label("Cantidad");
		Label total_label= new Label("Precio total");
		Label borrar_label= new Label("Borrar");
		Label actualizar_label= new Label("Actualizar");
		Label marcar_label= new Label("Marcar");
		
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
		
		for (final DatosProducto prod : lista_productos) {
			next_col=0;

			btn_borrar= new PushButton("Borrar");
			btn_actualizar= new PushButton("Actualizar");
			btn_marcar= new PushButton("Marcar");


			final ClickHandler handler = new ClickHandler(){
				public void onClick(ClickEvent arg0) {
					if(parent instanceof PantallaProductos)
						((PantallaProductos)parent).borrar_producto(prod.getNombre());
					else if(parent instanceof PantallaVistaDeCompra)
						((PantallaVistaDeCompra)parent).borra_producto_de_lista(prod.getNombre(), ((PantallaVistaDeCompra)parent).getId_compra());
					//					hide();
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
					else
						lista_prod.getRowFormatter().setStyleName(row_a_marcar, "ComplejidadMedia");
					prod.setEsta_marcada(!marcada);
					((PantallaVistaDeCompra)parent).actualizar_producto(prod);
					
					
				}
			};

			btn_marcar.addClickHandler(handler_marcar);
			
			//			prod_actual= prod;
			//			agregar_handler();


			
			lista_prod.setText(next_row, next_col, prod.getNombre());
			next_col++;
			
			float precio= prod.getPrecio();
			
			precio = poner_dos_decimales(precio);
			
			subtotal_compra+= precio;
			
			lista_prod.setText(next_row, next_col, String.valueOf(precio));
			next_col++;
			
			if(titulo.equalsIgnoreCase("Vista de compra")) {
				lista_prod.setText(next_row, next_col, ((prod.getCantidad()!=0) ? String.valueOf(prod.getCantidad()) : "NA"));
				next_col++;

				float precio_total= prod.getPrecio() * prod.getCantidad();
				
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
			next_row++;
		}
		
		if(titulo.equalsIgnoreCase("Vista de compra")) {
			desc_coto.addBlurHandler(new BlurHandler(){
				public void onBlur(BlurEvent event) {
					String aux= desc_coto.getText();
					if (!aux.isEmpty()) {
						desc_coto_float= Float.parseFloat(aux);
						
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
				}}});
			
			
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

		lista_prod.setStyleName("PanelConBordesExpandido");
		lista_prod.getRowFormatter().setStyleName(0, "HeaderTablas");

		principal.add(titulo_label);
		principal.add(desc_coto_decripcion);
		principal.add(desc_coto);

		principal.add(cant_prod_label);
		principal.add(lista_prod);
		initWidget(principal);
	}



	private float poner_dos_decimales(float precio_total) {
		return (float) (Math.round(precio_total*100)/100.0d);
	}

}
