package ar.gov.chris.client.widgets;

import java.util.Date;
import java.util.LinkedList;

import ar.gov.chris.client.datos.DatosProducto;
import ar.gov.chris.client.pantalla.Pantalla;
import ar.gov.chris.client.pantalla.PantallaVencimientos;
import ar.gov.chris.client.pantalla.PantallaVistaDeCompra;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;

public class WidgetMostrarVencimientos extends Composite {

	private FlowPanel principal;
	private FlexTable lista_prod;
	private Label titulo_label;

	int next_row;
	int next_col;
	
	private PantallaVencimientos parent;

	public WidgetMostrarVencimientos(final LinkedList<DatosProducto> lista_productos, 
			String titulo, PantallaVencimientos pantallaVencimientos) {

		this.parent= pantallaVencimientos;
		
		lista_prod= new FlexTable();
		principal= new FlowPanel();
		//		HorizontalPanel hp = new HorizontalPanel();
		//		
		//		titulo_label= new Label(titulo);
		//		titulo_label.addStyleName("LabelDistinguido");
		//		
		//		hp.add(titulo_label);
		//		
		////		if(titulo.equalsIgnoreCase("Vista de compra")) {
		////			cant_prod_label= new Label("Esta compra tiene: " + cant_prod + " productos.");
		////		} else 
		////			cant_prod_label= new Label("Cantidad de productos registrados: " + cant_prod);
		////		
		//		cant_prod_label= new Label();
		//		
		//		cant_prod_label.setStyleName("HeaderTablas");
		//	


		Label id_prod_label= new Label("ID");

		Label nombre_prod_label= new Label("Producto");
		Label vence_label= new Label("Vence");
		Label existe_label= new Label("Existe");
		Label compra_label= new Label("Compra");
		next_col=0;

		lista_prod.setWidget(0, next_col, id_prod_label);
		next_col++;
		lista_prod.setWidget(0, next_col, nombre_prod_label);
		next_col++;

		lista_prod.setWidget(0, next_col, vence_label);
		next_col++;
		lista_prod.setWidget(0, next_col, existe_label);
		next_col++;

		lista_prod.setWidget(0, next_col, compra_label);


		next_row= 1; 
		int tamanio_lista= lista_productos.size();
		int i=1;
		for (final DatosProducto prod : lista_productos) {
			next_col=0;

			lista_prod.setText(next_row, next_col, String.valueOf(prod.getId()));
			next_col++;
			lista_prod.setText(next_row, next_col, prod.getNombre());
			next_col++;
			lista_prod.setText(next_row, next_col, prod.getFecha_venc().toString());
			next_col++;
			lista_prod.setText(next_row, next_col, prod.isExiste()? "SI":"NO");
			next_col++;
			lista_prod.setText(next_row, next_col, String.valueOf(prod.getId_compra()));
			next_col++;

			PushButton btn_cambiar_existencia;
		
			btn_cambiar_existencia= new PushButton("Cambiar existencia");
			lista_prod.setWidget(next_row, next_col, btn_cambiar_existencia);
			
			btn_cambiar_existencia.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					
					boolean existe= prod.isExiste();

					if(existe)
						lista_prod.setText(next_row, next_col-2, "NO");
					else {
						lista_prod.setText(next_row, next_col-2, "SI");
					
					
				}
					
					prod.setExiste(!existe);
					((PantallaVencimientos)parent).actualizar_producto(prod);
					
			}});

			
			
			//******************************
			
			
//			
//			ClickHandler handler_marcar= new ClickHandler() {
//
//				@Override
//				public void onClick(ClickEvent event) {
//					
//					boolean marcada= prod.isEsta_marcada();
//
//					if(marcada)
//						lista_prod.getRowFormatter().setStyleName(row_a_marcar, "ContenidoTablas");
//					else {
//						if(((PantallaVistaDeCompra)parent).isVer_marcados())
//							lista_prod.getRowFormatter().setStyleName(row_a_marcar, "ComplejidadMedia");
//						else
//							lista_prod.getRowFormatter().setVisible(row_a_marcar, false);
//					}
//					prod.setEsta_marcada(!marcada);
//					((PantallaVistaDeCompra)parent).actualizar_producto(prod);
//					
//					
//				}
//			};
//
//			btn_marcar.addClickHandler(handler_marcar);
			
			
			//**************************

			lista_prod.getRowFormatter().setStyleName(next_row, "ContenidoTablas");

			next_row++;

		}

		lista_prod.setStyleName("PanelConBordesExpandido");
		lista_prod.getRowFormatter().setStyleName(0, "HeaderTablas");

		//		principal.add(titulo_label);

		//		principal.add(hp);


		principal.add(lista_prod);
		initWidget(principal);
	}

}
