package ar.gov.chris.client.widgets;

import java.awt.Button;
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
	private Label total_literal_label;

	private Label total_compra_label;
	private Label desc_coto_label;
	private Label desc_coto_decripcion= new Label("Ingrese el descuento realizado por coto");
	private TextBox desc_coto= new TextBox();
	
	private Label desc_tarj_label;
	private Label total_final_label= new Label("Total");


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
	
	private float total_final=0;
	float total_compra=0;
	


	/** Crea un {@link WidgetMostrarLineas} a partir de los par�metros.
	 * 
	 * @param lista Lista de l�neas a mostrar.
	 * @param titulo T�tulo del widget.
	 */
	public WidgetMostrarProductos(final Set<DatosProducto> lista, 
			String titulo, int num_compra, final Pantalla parent) {

		this.parent= parent;
		principal= new FlowPanel();
		lista_prod= new FlexTable();
		titulo_label= new Label(titulo);
		titulo_label.addStyleName("LabelDistinguido");
		cant_prod_label= new Label("Esta compra tiene: " + lista.size() + " productos.");
		cant_prod_label.setStyleName("HeaderTablas");
		total_literal_label= new Label("Subtotal");
		total_literal_label.addStyleName("LabelDistinguido");
		total_compra_label= new Label();
		total_compra_label.addStyleName("LabelDistinguido");
		desc_coto_label= new Label("Desc Coto");
		desc_coto_label.addStyleName("LabelDistinguido");
		desc_tarj_label= new Label("Desc Tarj");
		desc_tarj_label.addStyleName("LabelDistinguido");
		

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
		
		lista_prod.setWidget(0, next_col, marcar_label);


//		lista_prod.setStyleName(style);

		next_row= 1; 

		
		float subtotal=0;
		float total=0;

		
		for (final DatosProducto prod : lista) {
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
					//				confirmar_borrado= new WidgetConfirmar(parent, /*prod_actual*/prod, "Está seguro que quiere borrar el producto", );
					//				confirmar_borrado.show();
					//****
					confirmar_borrado= new WidgetConfirmar(parent, "Está seguro que quiere borrar el producto", handler);
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
					lista_prod.getRowFormatter().setStyleName(row_a_marcar, "ComplejidadMedia");
					boolean marcada= prod.isEsta_marcada();
					prod.setEsta_marcada(!marcada);
					((PantallaVistaDeCompra)parent).actualizar_producto(prod);
					
					
				}
			};

			btn_marcar.addClickHandler(handler_marcar);
			
			//			prod_actual= prod;
			//			agregar_handler();


			
			lista_prod.setText(next_row, next_col, prod.getNombre());
			next_col++;
			
			total_compra+= prod.getPrecio();
			
			lista_prod.setText(next_row, next_col, String.valueOf(prod.getPrecio()));
			next_col++;
			
			if(titulo.equalsIgnoreCase("Vista de compra")) {
				lista_prod.setText(next_row, next_col, ((prod.getCantidad()!=0) ? String.valueOf(prod.getCantidad()) : "NA"));
				next_col++;

				float precio_total= prod.getPrecio() * prod.getCantidad();
				lista_prod.setText(next_row, next_col, String.valueOf(precio_total));
				next_col++;
			}
			lista_prod.setWidget(next_row, next_col, btn_borrar);
			next_col++;

			lista_prod.setWidget(next_row, next_col, btn_actualizar);
			next_col++;
			
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
						lista_prod.setText(next_row-2, 3, String.valueOf(desc_coto_float));
						float tot_aux= (total_compra-desc_coto_float);
						total_final= tot_aux;
						float desc_tarj=tot_aux/100*20;
						total_final= total_final -(desc_tarj);
						lista_prod.setText(next_row-1, 3, String.valueOf(desc_tarj));
						lista_prod.setWidget(next_row, 2, total_final_label);
						lista_prod.setText(next_row, 3, String.valueOf(total_final));

				}}});
			
			
			lista_prod.setWidget(next_row, 2, total_literal_label);

			String total_compra_str= String.valueOf(total_compra);
			total_compra_label.setText(total_compra_str);
			lista_prod.setWidget(next_row, 3, total_compra_label);
			next_row++;

			lista_prod.setWidget(next_row, 2, desc_coto_label);
			lista_prod.setText(next_row, 3, String.valueOf(desc_coto_float));

			next_row++;

			lista_prod.setWidget(next_row, 2, desc_tarj_label);
			next_row++;

//			int desc_coto_int= Integer.parseInt(desc_coto.getText());
			
			float tot_aux= (total_compra-desc_coto_float);
			total_final= tot_aux;
			total_final= total_final - (tot_aux/100*20);
			lista_prod.setWidget(next_row, 2, total_final_label);
			lista_prod.setText(next_row, 3, String.valueOf(total_final));

			
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

}
