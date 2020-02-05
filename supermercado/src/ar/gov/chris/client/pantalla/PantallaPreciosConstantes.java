package ar.gov.chris.client.pantalla;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

import ar.gov.chris.client.clases.BuscadorDatosEstaticos;
import ar.gov.chris.client.datos.DatosReprtePrecios;
import ar.gov.chris.client.widgets.MensajeAlerta;
import ar.gov.chris.client.widgets.WidgetMostrarPrecios;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Label;

public class PantallaPreciosConstantes extends PantallaInicio {
	
	private int CANT_ANIOS;

	protected LinkedList<DatosReprtePrecios>  datos_precios;
	
	ArrayList<LinkedList<DatosReprtePrecios>>  arreglo= new ArrayList<LinkedList<DatosReprtePrecios>>();

	public PantallaPreciosConstantes() {
		super();
	}

	@Override
	protected void pantalla_principal() {
		panel.clear();
		
		mostrar_senal_espera("Consultando, por favor espere...");


		super.pantalla_principal();
		
		primer_anio_de_compras= BuscadorDatosEstaticos.anios_listas[0];
		anio_actual= BuscadorDatosEstaticos.anios_listas[1];
		CANT_ANIOS=anio_actual-primer_anio_de_compras+1;
		
		obtener_datos_precios();
	}
	
	

	private void obtener_datos_precios() {
		buscando_precios(primer_anio_de_compras);				
	}

	private void buscando_precios(final int primeranio) {
		
		final int anio= primeranio+1;
		
		proxy_precios.buscar_precios(primeranio-1 + "-12-31", primeranio+1 +"-01-01",new AsyncCallback<Set<DatosReprtePrecios>>(){
			public void onFailure(Throwable caught) {
				History.newItem("PantallaLoguearseSimple-Precios");
		
				MensajeAlerta.mensaje_error("Ocurrió un error al intentar buscar " +
						"los precios: " + caught.getMessage());
			}
			public void onSuccess(Set<DatosReprtePrecios> result) {

				datos_precios= ordenar_productos_con_precio(result);
				arreglo.add(datos_precios);
				CANT_ANIOS--;
				if(CANT_ANIOS!=-1) {
					buscando_precios(anio);
				} else {
					CANT_ANIOS=anio_actual-primer_anio_de_compras+1;
					armar_pantalla();
				}
			}
			
		});
	}

	protected void armar_pantalla() {
		
		 eliminar_senal_espera();

		
		for(int i= anio_actual; i >= primer_anio_de_compras; i--) {

			Label anio = new Label("Año " + i);
			
			DisclosurePanel panel_anio= new DisclosurePanel();
			panel_anio.setHeader(anio);
			
			WidgetMostrarPrecios ReportePrecios= new WidgetMostrarPrecios(arreglo.get(i-primer_anio_de_compras), "Reporte Precios " + anio.getText(), "2015-12-31", "2017-01-01");	

			panel_anio.setContent(ReportePrecios);
			panel.add(panel_anio);
			
			if(i == anio_actual)
				panel_anio.setOpen(true);		
		}

	}	
}

