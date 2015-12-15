package ar.gov.chris.client.widgets;

import java.util.Set;

import ar.gov.chris.client.datos.DatosLista;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class WidgetMostrarListas extends Composite {

	private FlowPanel principal;
	private FlexTable listas;
	private Label titulo_label;
	private int next_row;
	
	/** Crea un {@link WidgetMostrarLineas} a partir de los par�metros.
	 * 
	 * @param lista Lista de l�neas a mostrar.
	 * @param titulo T�tulo del widget.
	 */
	public WidgetMostrarListas(final Set<DatosLista> lista, 
			String titulo) {
		principal= new FlowPanel();
		listas= new FlexTable();
		titulo_label= new Label(titulo);
		titulo_label.addStyleName("LabelDistinguido");

		Label comentario_label= new Label("Comentario");
		Label fecha_label= new Label("Fecha");
//		Label marca_label= new Label("Marca");
//		Label modelo_label= new Label("Modelo");
//		Label nro_serie_label= new Label("Nro de serie");
//		Label opciones_label= new Label("Opciones");
		
		listas.setWidget(0, 0, comentario_label);
		listas.setWidget(0, 1, fecha_label);
//		lista_lineas.setWidget(0, 2, marca_label);
//		lista_lineas.setWidget(0, 3, modelo_label);
//		lista_lineas.setWidget(0, 4, nro_serie_label);
//		lista_lineas.setWidget(0, 5, opciones_label);
		
		next_row= 1; 

		for (DatosLista list : lista) {
//			CheckBox check_quitar= new CheckBox("Quitar l�nea");
			listas.setText(next_row, 0, list.getComentario());
			listas.setText(next_row, 1, list.getFecha().toString());
//		lista_lineas.setText(next_row, 2, prod.obtener_marca());
//		lista_lineas.setText(next_row, 3, prod.obtener_modelo());
//		lista_lineas.setText(next_row, 4, prod.obtener_nro_serie());
//		if (lista.size() > 1) {
//			check_quitar.setFormValue(linea.obtener_nro_cuenta() + " " +
//					linea.obtener_descripcion_categoria_accesorio() + " (" +linea.obtener_id() + ")");
//			lista_lineas.setWidget(next_row, 5, check_quitar);
//		}
			listas.getRowFormatter().setStyleName(next_row, "ContenidoTablas");
		next_row++;
		}
		listas.setStyleName("PanelConBordesExpandido");
		listas.getRowFormatter().setStyleName(0, "HeaderTablas");
			
		principal.add(titulo_label);
		principal.add(listas);
		initWidget(principal);
	}
}

