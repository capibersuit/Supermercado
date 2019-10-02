package ar.gov.chris.client.pantalla;

import java.util.LinkedList;
import java.util.Set;

import ar.gov.chris.client.datos.DatosReprtePrecios;
import ar.gov.chris.client.widgets.MensajeAlerta;
import ar.gov.chris.client.widgets.WidgetMostrarPrecios;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Label;

public class PantallaPrecios extends PantallaInicio {
	
//private int CANT_ANIOS;

	protected LinkedList<DatosReprtePrecios>  datos_precios16;
	protected LinkedList<DatosReprtePrecios>  datos_precios17;

	protected LinkedList<DatosReprtePrecios>  datos_precios18;

	private DisclosurePanel panel_16;
	private DisclosurePanel panel_17;
	private DisclosurePanel panel_18;


	private WidgetMostrarPrecios ReportePrecios16;
	private WidgetMostrarPrecios ReportePrecios17;
	private WidgetMostrarPrecios ReportePrecios18;




	public PantallaPrecios() {
		super();
	}

	@Override
	protected void pantalla_principal() {
		panel.clear();
//		CANT_ANIOS=2;
		
		mostrar_senal_espera("Consultando, por favor espere...");


		super.pantalla_principal();
		obtener_datos_precios();
	}
	
	

	private void obtener_datos_precios() {
//		CANT_ANIOS=2;
		proxy_precios.buscar_precios("2015-12-31", "2017-01-01",new AsyncCallback<Set<DatosReprtePrecios>>(){
			public void onFailure(Throwable caught) {
				History.newItem("PantallaLoguearseSimple-Precios");
		
				MensajeAlerta.mensaje_error("Ocurrió un error al intentar buscar " +
						"los precios: " + caught.getMessage());
			}
			public void onSuccess(Set<DatosReprtePrecios> result) {
				

				datos_precios16= ordenar_productos_con_precio(result);
				
				//***************************************************************************

				proxy_precios.buscar_precios("2016-12-31", "2018-01-01",new AsyncCallback<Set<DatosReprtePrecios>>(){
					public void onFailure(Throwable caught) {
						MensajeAlerta.mensaje_error("Ocurrió un error al intentar buscar " +
								"los precios: " + caught.getMessage());
					}
					public void onSuccess(Set<DatosReprtePrecios> result) {
						datos_precios17= ordenar_productos_con_precio(result);
						
						
						proxy_precios.buscar_precios("2017-12-31", "2019-01-01",new AsyncCallback<Set<DatosReprtePrecios>>(){
							public void onFailure(Throwable caught) {
								MensajeAlerta.mensaje_error("Ocurrió un error al intentar buscar " +
										"los precios: " + caught.getMessage());
							}
							public void onSuccess(Set<DatosReprtePrecios> result) {
								
								 eliminar_senal_espera();

								datos_precios18= ordenar_productos_con_precio(result);
								
								
								armar_pantalla();			
							}
							
						});			
					}
					
				});	
				
				//***************************************************************************
				
			}
			
		});		
//		proxy_precios.buscar_precios("2016-12-31", "2018-01-01",new AsyncCallback<Set<DatosReprtePrecios>>(){
//			public void onFailure(Throwable caught) {
//				MensajeAlerta.mensaje_error("Ocurrió un error al intentar buscar " +
//						"los precios: " + caught.getMessage());
//			}
//			public void onSuccess(Set<DatosReprtePrecios> result) {
//				datos_precios17= ordenar_productos_con_precio(result);
////				datos_precios= result;//ordenar_vemcimientos(result);
////				armar_pantalla();			
//				//CANT_ANIOS--;
//				
////				while(CANT_ANIOS!=0) {
////					System.out.println("hola");
////				}
//				armar_pantalla();	
//			}
//			
//		});		
		
				

		
	}

	protected void armar_pantalla() {
		
        Label anio_16= new Label("Año 2016");
		
		panel_16= new DisclosurePanel();
		panel_16.setHeader(anio_16);
		
	    Label anio_17= new Label("Año 2017");
		
		panel_17= new DisclosurePanel();
		panel_17.setHeader(anio_17);
	
        Label anio_18= new Label("Año 2018");
		
		panel_18= new DisclosurePanel();
		panel_18.setHeader(anio_18);
//		
//		solo_existentes= new CheckBox("Solo existentes");
//		solo_existentes.setValue(tildado);
		
		
		
		ReportePrecios16= new WidgetMostrarPrecios(datos_precios16, "Reporte Precios", "2015-12-31", "2017-01-01");
		
		
		
//		panel.add(solo_existentes);
//		panel.add(panel_16);
//		panel.add(ReportePrecios16);
		
		ReportePrecios17= new WidgetMostrarPrecios(datos_precios17, "Reporte Precios", "2016-12-31", "2018-01-01");
//		panel.add(panel_17);
//		panel.add(ReportePrecios17);
		
		ReportePrecios18= new WidgetMostrarPrecios(datos_precios18, "Reporte Precios", "2017-12-31", "2019-01-01");

		
		//------------
//		listas= new WidgetMostrarListas(datos_lista, "Listas de compras", this, new Date(2016-1900,1-1,31), new Date(2016-1900,12-1,31));
//		listas2= new WidgetMostrarListas(datos_lista, "Listas de compras", this, new Date(2017-1900,1-1,31), new Date(2017-1900,12-1,31));

		
		panel_16.setContent(ReportePrecios16);
		panel_17.setContent(ReportePrecios17);
		panel_18.setContent(ReportePrecios18);

//		panel_16.setOpen(true);
		panel_18.setOpen(true);

		panel.add(panel_18);
		panel.add(panel_17);
		panel.add(panel_16);
		
		//------------
		agregar_handlers();

	}

	
	private void agregar_handlers() {
//		solo_existentes.addClickHandler(new ClickHandler() {
//		      @Override
//		      public void onClick(ClickEvent event) {
//		        boolean checked = ((CheckBox) event.getSource()).getValue();
////		        Window.alert("It is " + (checked ? "" : "not ") + "checked");
//		        panel.remove(vencimientos);
//		        panel.remove(solo_existentes);
////		        primera_vez= false;
//		        tildado= checked;
//		        obtener_datos_vencimientos(tildado);
//		      }
//
//			
//		    });
		
	}

	
	
}
