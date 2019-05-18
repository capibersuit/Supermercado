package ar.gov.chris.client.widgets;

import java.util.Date;
import java.util.LinkedList;
import java.util.Set;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;

import ar.gov.chris.client.datos.DatosLista;
import ar.gov.chris.client.datos.DatosReprtePrecios;
import ar.gov.chris.client.pantalla.PantallaListaDeCompras;
import ar.gov.chris.client.util.Mate;

public class WidgetMostrarPrecios extends Composite {

	private FlowPanel principal;
	private FlexTable listas;
	private Label titulo_label;
	private int next_row;

	//	private boolean ene;
	//	private boolean feb;
	//	private boolean mar;
	//	private boolean abr;
	//	private boolean may;
	//	private boolean jun;
	//	private boolean jul;
	//	private boolean ago;
	//	private boolean sep;
	//	private boolean oct;
	//	private boolean nov;
	//	private boolean dic;

	private PantallaListaDeCompras parent;

	DatosReprtePrecios lista;

	/** Crea un {@link WidgetMostrarLineas} a partir de los par�metros.
	 * 
	 * @param lista Lista de l�neas a mostrar.
	 * @param titulo T�tulo del widget.
	 */
	public WidgetMostrarPrecios(LinkedList<DatosReprtePrecios> datos_precios,
			String titulo, String fecha_desde, String fecha_hasta) {
		this.parent= parent;

		principal= new FlowPanel();
		listas= new FlexTable();
		titulo_label= new Label(titulo);
		titulo_label.addStyleName("LabelDistinguido");

		separador_fechas();

		//		listas.setWidget(0, 2, precios_label);
		//		listas.setWidget(0, 3, fechas_label);


		next_row= 0; 

		//		LinkedList<DatosLista> lista_ordenada= parent.ordenar_listas(lista);

		for (final DatosReprtePrecios precios : datos_precios) {
			next_row++;
			listas.getRowFormatter().setStyleName(next_row, "ContenidoTablas");



			if(next_row % 11 == 0) {
				separador_fechas();
			} else {

				//			if(fecha_desde.compareTo(precios.getFechas()) < 0 && fecha_hasta.compareTo(precios.getFechas()) > 0) {

				int nex_col=0;
				int indice_precio=0;
				listas.setText(next_row, nex_col, precios.getId_prod());
				nex_col++;
				listas.setText(next_row, nex_col, precios.getNombre_prod());
				nex_col++;


				String[] fechas_split =precios.getFechas().split("_");
				
				String[] precios_split =precios.getPrecios().split("_");
				
				
				float precio_inicial= Float.parseFloat(precios_split[0]);
				float precio_final= Float.parseFloat(precios_split[precios_split.length-1]);
				
				float aux1= precio_final-precio_inicial;
				float aux2= aux1/precio_inicial;
				
				float porcentaje_de_aumento= aux2*100;
				porcentaje_de_aumento=Mate.poner_dos_decimales(porcentaje_de_aumento);
				
				listas.setText(next_row, nex_col,  String.valueOf(porcentaje_de_aumento));
				nex_col++;


				boolean ene = false;
				boolean feb = false;
				boolean mar = false;
				boolean abr= false;
				boolean may= false;
				boolean jun= false;
				boolean jul= false;
				boolean ago= false;
				boolean sep= false;
				boolean oct= false;
				boolean nov= false;
				boolean dic= false;


				for (String fecha : fechas_split) {

					//				nex_col=2;

					String mes= fecha.substring(5, 7);
					//************************************ENERO************************************************************************
					if(mes.equalsIgnoreCase("01") /*&& !ene*/)		{
						if(!ene) {
							listas.setText(next_row, nex_col,  Mate.poner_dos_decimales(precios_split[indice_precio++]));
							nex_col++;
							ene= true;
							continue;
						} else {
							listas.setText(next_row, nex_col-1,  Mate.poner_dos_decimales(precios_split[indice_precio++]));
							continue;
						}
					}
					else if(!ene){

						listas.setText(next_row, nex_col,  "--");
						nex_col++;
						ene= true;
					}
					//************************************FEBRERO************************************************************************

					if(mes.equalsIgnoreCase("02") /*&& !feb*/)	{		
						if(!feb) {
							listas.setText(next_row, nex_col,  Mate.poner_dos_decimales(precios_split[indice_precio++]));	
							nex_col++;
							feb= true;
							continue;
						} else {
							listas.setText(next_row, nex_col-1,  Mate.poner_dos_decimales(precios_split[indice_precio++]));
							continue;
						}
					}
					else if(!feb){
						listas.setText(next_row, nex_col,  "--");
						nex_col++;
						feb= true;
					} 
					//************************************MARZO************************************************************************

					if(mes.equalsIgnoreCase("03") /*&& !mar*/)		{
						if(!mar) {
							listas.setText(next_row, nex_col,  Mate.poner_dos_decimales(precios_split[indice_precio++]));
							nex_col++;
							mar=true;
							continue;
						} else {
							listas.setText(next_row, nex_col-1,  Mate.poner_dos_decimales(precios_split[indice_precio++]));
							continue;
						}
					}
					else if(!mar){
						listas.setText(next_row, nex_col,  "--");
						nex_col++;
						mar=true;
					}
					//************************************ABRIL************************************************************************

					if(mes.equalsIgnoreCase("04") )		{	
						if(!abr) {
						listas.setText(next_row, nex_col,  Mate.poner_dos_decimales(precios_split[indice_precio++]));
						nex_col++;
						abr= true;
						continue;
					} else {
						listas.setText(next_row, nex_col-1,  Mate.poner_dos_decimales(precios_split[indice_precio++]));
						continue;
					}
					}
					else if(!abr){
						listas.setText(next_row, nex_col,  "--");
						nex_col++;
						abr=true;
					}
					//************************************MAYO************************************************************************

					if(mes.equalsIgnoreCase("05") )		{
						if(!may) {
						listas.setText(next_row, nex_col,  Mate.poner_dos_decimales(precios_split[indice_precio++]));
						nex_col++;
						may= true;
						continue;
					} else {
						listas.setText(next_row, nex_col-1,  Mate.poner_dos_decimales(precios_split[indice_precio++]));
						continue;
					}
					}
					else if(!may){
						listas.setText(next_row, nex_col,  "--");
						nex_col++;
						may=true;
					}
					//************************************JUNIO************************************************************************

					if(mes.equalsIgnoreCase("06") ){	
						if(!jun) {
						listas.setText(next_row, nex_col,  Mate.poner_dos_decimales(precios_split[indice_precio++]));
						nex_col++;
						jun= true;
						continue;
					} else {
						listas.setText(next_row, nex_col-1,  Mate.poner_dos_decimales(precios_split[indice_precio++]));
						continue;
					}
					}

					else if(!jun){
						listas.setText(next_row, nex_col,  "--");
						nex_col++;
						jun=true;
					}
					
					//************************************JULIO************************************************************************

					if(mes.equalsIgnoreCase("07") ){		
						if(!jul) {
						listas.setText(next_row, nex_col,  Mate.poner_dos_decimales(precios_split[indice_precio++]));
						nex_col++;
						jul= true;
						continue;
					} else {
						listas.setText(next_row, nex_col-1,  Mate.poner_dos_decimales(precios_split[indice_precio++]));
						continue;
					}
					}

					else if(!jul){
						listas.setText(next_row, nex_col,  "--");
						nex_col++;
						jul=true;
					}
					
					//************************************AGOSTO************************************************************************

					if(mes.equalsIgnoreCase("08") ){		
						if(!ago) {
						listas.setText(next_row, nex_col,  Mate.poner_dos_decimales(precios_split[indice_precio++]));
						nex_col++;
						ago=true;
						continue;
					} else {
						listas.setText(next_row, nex_col-1,  Mate.poner_dos_decimales(precios_split[indice_precio++]));
						continue;
					}
					}
					else if(!ago){
						listas.setText(next_row, nex_col,  "--");
						nex_col++;
						ago=true;
					}
					
					//************************************SEPTIEMBRE************************************************************************

					if(mes.equalsIgnoreCase("09") ){	
						if(!sep) {
						listas.setText(next_row, nex_col,  Mate.poner_dos_decimales(precios_split[indice_precio++]));
						nex_col++;
						sep=true;
						continue;
					} else {
						listas.setText(next_row, nex_col-1,  Mate.poner_dos_decimales(precios_split[indice_precio++]));
						continue;
					}
					}
					else if(!sep){
						listas.setText(next_row, nex_col,  "--");
						nex_col++;
						sep=true;
					}
					//************************************OCTUBRE************************************************************************

					if(mes.equalsIgnoreCase("10") ){		
						if(!oct) {
						listas.setText(next_row, nex_col,  Mate.poner_dos_decimales(precios_split[indice_precio++]));
						nex_col++;
						oct= true;
						continue;
					} else {
						listas.setText(next_row, nex_col-1,  Mate.poner_dos_decimales(precios_split[indice_precio++]));
						continue;
					}
					}
					else if(!oct){
						listas.setText(next_row, nex_col,  "--");
						nex_col++;
						oct=true;
					}
					//************************************NOVIEMBRE************************************************************************

					if(mes.equalsIgnoreCase("11") ) {	
						if(!nov) {
						listas.setText(next_row, nex_col,  Mate.poner_dos_decimales(precios_split[indice_precio++]));
						nex_col++;
						nov= true;
						continue;
					} else {
						listas.setText(next_row, nex_col-1,  Mate.poner_dos_decimales(precios_split[indice_precio++]));
						continue;
					}
					}
					else if(!nov){
						listas.setText(next_row, nex_col,  "--");
						nex_col++;
						nov=true;
					}
					if(mes.equalsIgnoreCase("12") ) {	
						if(!dic) {
						listas.setText(next_row, nex_col,  Mate.poner_dos_decimales(precios_split[indice_precio++]));
						nex_col++;
						dic= true;
						continue;
					} else {
						listas.setText(next_row, nex_col-1,  Mate.poner_dos_decimales(precios_split[indice_precio++]));
						continue;
					}
					}
					//************************************DICIEMBRE************************************************************************

					else if(!dic){
						listas.setText(next_row, nex_col,  "--");
						dic=true;
					}
					//				}

					//				listas.setText(next_row, 2,  precios.getPrecios());
					//				listas.setText(next_row, 3, precios.getFechas());
					//				

					listas.getRowFormatter().setStyleName(next_row, "ContenidoTablas");
					next_row++;
				}
				//***************
			}
			//AHORA ME MUESTRA POCOS PRODUCTOS ...WidgetMostrarPrecios (SI BIEN AHORA SI ME MUESTRA LA LECHE Y EL ALA EN LOS 2 AÑOS!!)

			//***************
		}

		listas.setStyleName("PanelConBordesExpandido");
		//			listas.getRowFormatter().setStyleName(0, "HeaderTablas");

		//		agregar_handler();

		principal.add(titulo_label);
		principal.add(listas);
		initWidget(principal);
	}



	private void separador_fechas() {
		Label id_prod_label= new Label("Id");
		Label nombre_prod_label= new Label("Producto");
		//		Label precios_label= new Label("Precios");
		//		Label fechas_label= new Label("Fechas");
		
		Label porcentaje_label= new Label("% Aumento");


		Label ene_label= new Label("Ene");
		Label feb_label= new Label("Feb");
		Label mar_label= new Label("Mar");
		Label abr_label= new Label("Abr");
		Label may_label= new Label("May");
		Label jun_label= new Label("Jun");
		Label jul_label= new Label("Jul");
		Label ago_label= new Label("Ago");
		Label sep_label= new Label("Sep");
		Label oct_label= new Label("Oct");
		Label nov_label= new Label("Nov");
		Label dic_label= new Label("Dic");



		listas.setWidget(next_row, 0, id_prod_label);
		listas.setWidget(next_row, 1, nombre_prod_label);

		listas.setWidget(next_row, 2, porcentaje_label);

		
		listas.setWidget(next_row, 3, ene_label);
		listas.setWidget(next_row, 4, feb_label);
		listas.setWidget(next_row, 5, mar_label);
		listas.setWidget(next_row, 6, abr_label);
		listas.setWidget(next_row, 7, may_label);
		listas.setWidget(next_row, 8, jun_label);
		listas.setWidget(next_row, 9, jul_label);
		listas.setWidget(next_row, 10, ago_label);
		listas.setWidget(next_row, 11, sep_label);
		listas.setWidget(next_row, 12, oct_label);
		listas.setWidget(next_row, 13, nov_label);
		listas.setWidget(next_row, 14, dic_label);

		listas.getRowFormatter().setStyleName(next_row, "HeaderTablas");

		next_row++;
	}



//	private float poner_dos_decimales(float precio_total) {
//		return (float) (Math.round(precio_total*100)/100.0d);
//	}


//	private String poner_dos_decimales(String precio_total) {
//
//		float f = Float.parseFloat(precio_total); 
//		float precio_redondeado= (float) (Math.round(f*100)/100.0d);
//		return String.valueOf(precio_redondeado);
//	}








}
