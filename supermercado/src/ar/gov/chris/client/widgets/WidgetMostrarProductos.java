package ar.gov.chris.client.widgets;

import java.util.Set;

import ar.gov.chris.client.datos.DatosProducto;
import ar.gov.chris.client.pantalla.Pantalla;
import ar.gov.chris.client.pantalla.PantallaVistaDeCompra;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;

public class WidgetMostrarProductos extends Composite {

	private FlowPanel principal;
	private FlexTable lista_prod;
	private Label titulo_label;
	private int next_row;
	private int next_col=0;
	private Pantalla parent;
	
//	private DatosProducto prod_actual;

	
	private PushButton btn_borrar;
	private PushButton btn_actualizar;

	private WidgetConfirmar confirmar_borrado;
	private WidgetAgregarProducto actualizar_prod;

	
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

		Label nombre_prod_label= new Label("Producto");
		Label precio_label= new Label("Precio");
		Label cant_label= new Label("Cantidad");
		Label total_label= new Label("Precio total");
		Label borrar_label= new Label("Borrar");
		Label actualizar_label= new Label("Actualizar");
		
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
		
		next_row= 1; 

		for (final DatosProducto prod : lista) {
			next_col=0;
			
			btn_borrar= new PushButton("Borrar");
			btn_actualizar= new PushButton("Actualizar");
			btn_borrar.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				confirmar_borrado= new WidgetConfirmar(parent, /*prod_actual*/prod);
				confirmar_borrado.show();
			}
			});
			
			btn_actualizar.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				actualizar_prod= new WidgetAgregarProducto(parent, prod);
				actualizar_prod.show();
			}
			});

//			prod_actual= prod;
//			agregar_handler();

			
			lista_prod.setText(next_row, next_col, prod.getNombre());
			next_col++;
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

//		lista_lineas.setText(next_row, 4, prod.obtener_nro_serie());
//		if (lista.size() > 1) {
//			check_quitar.setFormValue(linea.obtener_nro_cuenta() + " " +
//					linea.obtener_descripcion_categoria_accesorio() + " (" +linea.obtener_id() + ")");
//			lista_lineas.setWidget(next_row, 5, check_quitar);
//		}
			lista_prod.getRowFormatter().setStyleName(next_row, "ContenidoTablas");
		next_row++;
		}
		lista_prod.setStyleName("PanelConBordesExpandido");
		lista_prod.getRowFormatter().setStyleName(0, "HeaderTablas");
			
		principal.add(titulo_label);
		principal.add(lista_prod);
		initWidget(principal);
	}
	
//	void agregar_handler() {
//		btn_borrar.addClickHandler(new ClickHandler() {
//			
//			@Override
//			public void onClick(ClickEvent event) {
//				confirmar_borrado= new WidgetConfirmar(parent, prod_actual);
//				confirmar_borrado.show();
//			}
//			});
//	    }
}
