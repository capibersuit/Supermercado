package ar.gov.chris.client.widgets;

import java.util.Arrays;
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

	DatosLista lista;
	
	String[] meses_str = new String[]{
			"Enero",
			"Febebro",
			"Marzo",
			"Abril",
			"Mayo",
			"Junio",
			"Julio",
			"Agosto",
			"Septiembre",
			"Octubre",
			"Noviembre",
			"Diciembre"};
	
	private static final int ID_MES_DICIEMBRE_EN_LA_LISTA = 11;


	/** Crea un {@link WidgetMostrarLineas} a partir de los par�metros.
	 * 
	 * @param lista Lista de l�neas a mostrar.
	 * @param titulo T�tulo del widget.
	 */
	public WidgetMostrarListas(final Set<DatosLista> lista, 
			String titulo, final PantallaListaDeCompras parent, Date fecha_desde, Date fecha_hasta) {
		
  		Boolean[] meses_ya_usados = new Boolean[12];
		Arrays.fill(meses_ya_usados, Boolean.FALSE);
				
		principal= new FlowPanel();
		listas= new FlexTable();
		titulo_label= new Label(titulo);
		titulo_label.addStyleName("LabelDistinguido");

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
		
		float total_pagado_mes_actual=0;

		Date fecha_compra_mas_reciente= lista_ordenada.getFirst().getFecha();
		
//		int mes_compra_anterior=-1;
		int mes_compra_anterior=fecha_compra_mas_reciente.getMonth();
				
		//xxx: Aca obtengo la primera compra en realidad, o sea la mas antigua (actualmente 2016-01-27!)
		// 		creo que quise decir la ultima de la lista ordenada....
		DatosLista compra_mas_antigua= lista_ordenada.getLast();
		
		boolean es_la_compra_mas_reciente_anio_actual= true;

		for (final DatosLista list : lista_ordenada) {
						
			Label total_pagado_mes_actual_label =new Label("0");
			
			total_pagado_mes_actual_label.addStyleName("MesSeparador");
			
			Label total_literal_label= new Label("TOTAL DEL MES");
			total_literal_label.addStyleName("TotalDelMes");

			String total_pagado_mes_actua_str="0";
		
			//XXX: No me quedó bien claro porque con este if logro que me aparezca el total de enero
			//	   en cada año (salvo en 2016 por ser el primero)
			if(compra_mas_antigua.equals(list)) {
				
				total_pagado_mes_actua_str= String.valueOf(Mate.poner_dos_decimales(total_pagado_mes_actual));
				total_pagado_mes_actual_label.setText(total_pagado_mes_actua_str);
				listas.setWidget(next_row, 5, total_literal_label );
				listas.setWidget(next_row, 6, total_pagado_mes_actual_label );
			}
				
			if(fecha_desde.compareTo(list.getFecha()) < 0 && fecha_hasta.compareTo(list.getFecha()) > 0) {
				
				float pagado_compra_actual= list.getPagado();			
				int mes_compra_actual=list.getFecha().getMonth();
				Date fecha_compra_actual= list.getFecha();
				total_pagado_mes_actual+= pagado_compra_actual;
											
				if(mes_compra_actual!= mes_compra_anterior && !fecha_compra_actual.equals(fecha_compra_mas_reciente) && mes_compra_actual!=ID_MES_DICIEMBRE_EN_LA_LISTA) {

					total_pagado_mes_actual= total_pagado_mes_actual-pagado_compra_actual;
					
					total_pagado_mes_actua_str= String.valueOf(Mate.poner_dos_decimales(total_pagado_mes_actual));
					total_pagado_mes_actual_label.setText(total_pagado_mes_actua_str);
					
					listas.setWidget(next_row, 5, total_literal_label );
					listas.setWidget(next_row, 6, total_pagado_mes_actual_label );
					
					next_row++;

					total_pagado_mes_actual=pagado_compra_actual;
					mes_compra_anterior=mes_compra_actual;
				}
				
				if(es_la_compra_mas_reciente_anio_actual)
					mes_compra_anterior=mes_compra_actual;
				
				if(!meses_ya_usados[mes_compra_actual]) {									
					separador_meses(mes_compra_actual);
					meses_ya_usados[mes_compra_actual]= true;						
				} 
				
				Image imagen_boton_ir= new Image("imagenes/boton-ir.jpg");
				imagen_boton_ir.setAltText(""+list.getId());
				btn_ir = new PushButton(imagen_boton_ir);
				btn_ir.setTitle("Compra nro. "+ list.getId());
				btn_ir.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						History.newItem("PantallaVistaDeCompra-"+ list.getId());
						History.fireCurrentHistoryState();;
					}
				});
//				String directorio_imagenes= System.getProperty("user.dir")+ "/imagenes/boton-eliminar.jpg";
//				String directorio_imagenes=  "/supermercado/imagenes/boton-eliminar.jpg";
				
				Image imagen_boton_borrar= new Image("imagenes/boton-eliminar.jpg");
//				Image imagen_boton_borrar= new Image("/imagenes/boton-eliminar.jpg");
				
//				Image imagen_boton_borrar= new Image("../../imagenes/delete.gif");
				imagen_boton_borrar.setAltText("Borrar");

				btn_borrar= new PushButton(imagen_boton_borrar);

				Image imagen_boton_actualizar= new Image("imagenes/boton-actualizar.jpg");
//				Image imagen_boton_actualizar= new Image("../../imagenes/boton-actualizar.jpg");
				imagen_boton_actualizar.setAltText("Actualizar");
				
				btn_actualizar= new PushButton(imagen_boton_actualizar);
				
				final ClickHandler handler = new ClickHandler(){
					public void onClick(ClickEvent arg0) {
						parent.borrar_lista(list.getId());
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

				//			CheckBox check_quitar= new CheckBox("Quitar lineas");
				listas.setText(next_row, 0, String.valueOf(list.getId()));
				listas.setText(next_row, 1, list.getComentario());
				
				listas.setText(next_row, 2, String.valueOf(list.getPorcentaje_descuento()));
				
				String id_suc= String.valueOf(list.getId_sucursal());
				
				listas.setText(next_row, 3, Util.obtener_supermercado_de_sucursal(id_suc).obtener_descripcion());
				listas.setText(next_row, 4, Util.mapear_sucursal_por_id(id_suc).obtener_descripcion());

				listas.setText(next_row, 5, list.getFecha().toString());

				Label pagado_label =new Label();
				pagado_label.addStyleName("Pagado");

				String pagado_str= String.valueOf(Mate.poner_dos_decimales(list.getPagado()));
				
				if (!list.isBotones_habilitados() && list.getPagado()!= 0)
					pagado_label.addStyleName("PagadoYControlado");
				else if (list.getPagado()== 0)
					pagado_label.addStyleName("SinNetoApagarIngresado");

				pagado_label.setText(pagado_str);

				listas.setWidget(next_row, 6, pagado_label);
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
	
	private void separador_meses(int mes) {		
		Label mes_separador =new Label();
		mes_separador.addStyleName("MesSeparador");		
		
		mes_separador.setText(meses_str[mes]);
		listas.setWidget(next_row, 5,  mes_separador);		
		next_row++;
	}
}
