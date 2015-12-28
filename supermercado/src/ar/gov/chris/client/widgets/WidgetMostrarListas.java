package ar.gov.chris.client.widgets;

import java.util.Set;

import ar.gov.chris.client.datos.DatosLista;
import ar.gov.chris.client.pantalla.PantallaListaDeCompras;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;

public class WidgetMostrarListas extends Composite {

	private FlowPanel principal;
	private FlexTable listas;
	private Label titulo_label;
	private int next_row;
	
	private PushButton btn_ir;
	private PushButton btn_borrar;
	private PushButton btn_actualizar;
	
	private WidgetConfirmar confirmar_borrado;
	private WidgetAgregarLista actualizar_lista;

	private PantallaListaDeCompras parent;

	DatosLista lista;
	
	/** Crea un {@link WidgetMostrarLineas} a partir de los par�metros.
	 * 
	 * @param lista Lista de l�neas a mostrar.
	 * @param titulo T�tulo del widget.
	 */
	public WidgetMostrarListas(final Set<DatosLista> lista, 
			String titulo, final PantallaListaDeCompras parent) {
		this.parent= parent;

		principal= new FlowPanel();
		listas= new FlexTable();
		titulo_label= new Label(titulo);
		titulo_label.addStyleName("LabelDistinguido");
//		btn_ir = new PushButton(new Image("C:\Users\STUART\Downloads\boton-ir.png"));
		Image imagen= new Image("/boton-ir.png");
//		btn_ir = new PushButton("Ver lista");
//		btn_ir = new PushButton(new Image("/supermercado/src/ar/gov/chris/client/widgets/boton-ir.png"));

		Label comentario_label= new Label("Comentario");
		Label fecha_label= new Label("Fecha");
		Label id_lista_label= new Label("Ir");
		Label borrar_label= new Label("B");
		Label actualizar_label= new Label("A");

		listas.setWidget(0, 0, comentario_label);
		listas.setWidget(0, 1, fecha_label);
		listas.setWidget(0, 2, id_lista_label);
		listas.setWidget(0, 3, borrar_label);
		listas.setWidget(0, 4, actualizar_label);

		next_row= 1; 

		for (final DatosLista list : lista) {
			
//			this.lista= list;
			
			btn_ir = new PushButton(new Image("/imagenes/boton-ir.jpg"));
			btn_ir.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					History.newItem("PantallaVistaDeCompra-"+ list.getId());
					History.fireCurrentHistoryState();;
				}
				});
			
			btn_borrar= new PushButton(new Image("/imagenes/boton-eliminar.jpg"));
			btn_actualizar= new PushButton(new Image("/imagenes/boton-actualizar.jpg"));
			
			final ClickHandler handler = new ClickHandler(){
				public void onClick(ClickEvent arg0) {
//					if(parent instanceof PantallaProductos)
//						((PantallaProductos)parent).borrar_producto(prod.getNombre());
//					else if(parent instanceof PantallaVistaDeCompra)
					
//						((PantallaVistaDeCompra)parent).borrar_lista(((PantallaVistaDeCompra)parent).getId_compra());
						parent.borrar_lista(list.getId());

//					hide();
				}
			};
			
			btn_borrar.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				confirmar_borrado= new WidgetConfirmar(parent, "Está seguro que quiere borrar la lista de compra", handler);
				confirmar_borrado.show();
			}
			});
			
			btn_actualizar.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				actualizar_lista= new WidgetAgregarLista(parent, list);
				actualizar_lista.show();
			}
			});
//			agregar_handler();

//			CheckBox check_quitar= new CheckBox("Quitar l�nea");
			listas.setText(next_row, 0, list.getComentario());
			listas.setText(next_row, 1, list.getFecha().toString());
			listas.setWidget(next_row, 2, btn_ir);
			listas.setWidget(next_row, 3, btn_borrar);
			listas.setWidget(next_row, 4, btn_actualizar);

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
			
//		agregar_handler();
		
		principal.add(titulo_label);
		principal.add(listas);
		initWidget(principal);
	}
	
//	void agregar_handler() {
//	btn_ir.addClickHandler(new ClickHandler() {
//		
//		@Override
//		public void onClick(ClickEvent event) {
//			History.newItem("PantallaVistaDeCompra-"+ lista.getId());
//			History.fireCurrentHistoryState();;
//		}
//		});
//    }
}
