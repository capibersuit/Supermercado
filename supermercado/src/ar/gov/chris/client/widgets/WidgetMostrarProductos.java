package ar.gov.chris.client.widgets;

import java.util.Set;

import ar.gov.chris.client.datos.DatosProducto;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class WidgetMostrarProductos extends Composite {

	private FlowPanel principal;
	private FlexTable lista_prod;
	private Label titulo_label;
	private int next_row;
	
	/** Crea un {@link WidgetMostrarLineas} a partir de los parámetros.
	 * 
	 * @param lista Lista de líneas a mostrar.
	 * @param titulo Título del widget.
	 */
	public WidgetMostrarProductos(final Set<DatosProducto> lista, 
			String titulo) {
		principal= new FlowPanel();
		lista_prod= new FlexTable();
		titulo_label= new Label(titulo);
		titulo_label.addStyleName("LabelDistinguido");

		Label nombre_prod_label= new Label("Producto");
		Label precio_label= new Label("Precio");
//		Label marca_label= new Label("Marca");
//		Label modelo_label= new Label("Modelo");
//		Label nro_serie_label= new Label("Nro de serie");
//		Label opciones_label= new Label("Opciones");
		
		lista_prod.setWidget(0, 0, nombre_prod_label);
		lista_prod.setWidget(0, 1, precio_label);
//		lista_lineas.setWidget(0, 2, marca_label);
//		lista_lineas.setWidget(0, 3, modelo_label);
//		lista_lineas.setWidget(0, 4, nro_serie_label);
//		lista_lineas.setWidget(0, 5, opciones_label);
		
		next_row= 1; 

		for (DatosProducto prod : lista) {
//			CheckBox check_quitar= new CheckBox("Quitar línea");
			lista_prod.setText(next_row, 0, prod.getNombre());
			lista_prod.setText(next_row, 1, String.valueOf(prod.getPrecio()));
//		lista_lineas.setText(next_row, 2, prod.obtener_marca());
//		lista_lineas.setText(next_row, 3, prod.obtener_modelo());
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
}
