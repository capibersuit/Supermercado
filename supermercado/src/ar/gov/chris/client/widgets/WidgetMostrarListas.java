package ar.gov.chris.client.widgets;

import java.util.Date;
import java.util.LinkedList;
import java.util.Set;

import ar.gov.chris.client.datos.DatosLista;
import ar.gov.chris.client.pantalla.PantallaListaDeCompras;
import ar.gov.chris.client.util.Mate;
import ar.gov.chris.client.util.Util;

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
			String titulo, final PantallaListaDeCompras parent, Date fecha_desde, Date fecha_hasta) {
		this.parent= parent;

		principal= new FlowPanel();
		listas= new FlexTable();
		titulo_label= new Label(titulo);
		titulo_label.addStyleName("LabelDistinguido");
		//		btn_ir = new PushButton(new Image("C:\Users\STUART\Downloads\boton-ir.png"));
		Image imagen= new Image("/boton-ir.png");
		//		btn_ir = new PushButton("Ver lista");
		//		btn_ir = new PushButton(new Image("/supermercado/src/ar/gov/chris/client/widgets/boton-ir.png"));

		Label id_compra_label= new Label("Id");
		Label comentario_label= new Label("Comentario");
		Label super_label= new Label("Super");
		Label desc_label= new Label("Desc");
		Label suc_label= new Label("Sucursal");

		Label fecha_label= new Label("Fecha");

		Label pagado_literal_label= new Label("Pagado");


		Label id_lista_label= new Label("Ir");
		Label borrar_label= new Label("B");
		Label actualizar_label= new Label("A");

		listas.setWidget(0, 0, id_compra_label);

		listas.setWidget(0, 1, comentario_label);
		
		listas.setWidget(0, 2, desc_label);

		listas.setWidget(0, 3, super_label);

		listas.setWidget(0, 4, suc_label);

		listas.setWidget(0, 5, fecha_label);
		
		listas.setWidget(0, 6, pagado_literal_label);
		listas.setWidget(0, 7, id_lista_label);
		
		listas.setWidget(0, 8, borrar_label);
		listas.setWidget(0, 9, actualizar_label);

		next_row= 1; 

		LinkedList<DatosLista> lista_ordenada= parent.ordenar_listas(lista);

		for (final DatosLista list : lista_ordenada) {

			//			this.lista= list;

			if(fecha_desde.compareTo(list.getFecha()) < 0 && fecha_hasta.compareTo(list.getFecha()) > 0) {

				btn_ir = new PushButton(new Image("/imagenes/boton-ir.jpg"));
				btn_ir.setTitle("Compra nro. "+ list.getId());
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

				//			CheckBox check_quitar= new CheckBox("Quitar lineas");
				listas.setText(next_row, 0, String.valueOf(list.getId()));
				listas.setText(next_row, 1, list.getComentario());
				
				listas.setText(next_row, 2, String.valueOf(list.getPorcentaje_descuento()));
				
				String id_suc= String.valueOf(list.getId_sucursal());
				
				listas.setText(next_row, 3, Util.obtener_supermercado_de_sucursal(id_suc).obtener_descripcion());
				listas.setText(next_row, 4, Util.mapear_sucursal_por_id(id_suc).obtener_descripcion());

				listas.setText(next_row, 5, list.getFecha().toString());

				Label pagado_label =new Label();
				pagado_label.addStyleName("ComplejidadMedia");

				String pagado_str= String.valueOf(Mate.poner_dos_decimales(list.getPagado()));

				pagado_label.setText(pagado_str);

				listas.setWidget(next_row, 6,  pagado_label);


				listas.setWidget(next_row, 7, btn_ir);
				listas.setWidget(next_row, 8, btn_borrar);
				listas.setWidget(next_row, 9, btn_actualizar);

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
		}
		listas.setStyleName("PanelConBordesExpandido");
		listas.getRowFormatter().setStyleName(0, "HeaderTablas");

		//		agregar_handler();

		principal.add(titulo_label);
		principal.add(listas);
		initWidget(principal);
	}

//	private float poner_dos_decimales(float precio_total) {
//		return (float) (Math.round(precio_total*100)/100.0d);
//	}

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
