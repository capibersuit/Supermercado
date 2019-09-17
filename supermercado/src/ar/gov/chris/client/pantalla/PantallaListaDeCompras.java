package ar.gov.chris.client.pantalla;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import ar.gov.chris.client.clases.BuscadorDatosEstaticos;
import ar.gov.chris.client.clases.FechaListaComparator;
import ar.gov.chris.client.clases.IdListaComparator;
import ar.gov.chris.client.datos.DatosLista;
import ar.gov.chris.client.widgets.MensajeAlerta;
import ar.gov.chris.client.widgets.WidgetAgregarLista;
import ar.gov.chris.client.widgets.WidgetMostrarListas;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Label;

public class PantallaListaDeCompras extends PantallaInicio {
	
	
	private Button btn_agregar_lista;	
	private WidgetAgregarLista agregar_lista;
	protected Set<DatosLista> datos_lista;	

	public PantallaListaDeCompras() {
		super();
//		inicializar();
//		pantalla_principal();
	}

	@Override
	protected void pantalla_principal() {
		panel.clear();

//		btn_agregar_lista= new Button("Nueva Lista");
//		panel.add(btn_agregar_lista);
//		
//		agregar_lista= new WidgetAgregarLista(this);
//		agregar_handlers();
		

		
		super.pantalla_principal();
		obtener_datos_listas();
	}
	
	
	public void agregar_lista(String comentario, int id_sucursal, int porcentaje) {
		DatosLista datos_list= new DatosLista();
		datos_list.setComentario(comentario);
		datos_list.setId_sucursal(id_sucursal);
		datos_list.setPorcentaje_descuento(porcentaje);
	
		proxy_listas.agregar_lista(datos_list, new AsyncCallback<Void>(){
			public void onFailure(Throwable caught) {
				MensajeAlerta.mensaje_error("Ocurrió un error al intentar agregar " +
						"la lista: " + caught.getMessage());
			}
			public void onSuccess(Void result) {
				Window.Location.reload();

//				agregar_item_historial_cliente(datos_item);
//				recargar_personas();
			}
			
		});
	}
	
	 protected void obtener_datos_listas() {
		 
			mostrar_senal_espera("Consultando, por favor espere...");

		 
		proxy_listas.buscar_listas(new AsyncCallback<Set<DatosLista>>(){
			public void onFailure(Throwable caught) {
				History.newItem("PantallaLoguearseSimple-Compras");

				MensajeAlerta.mensaje_error("Ocurrió un error al intentar buscar " +
						"las listas de compras: " + caught.getMessage());
			}
			public void onSuccess(Set<DatosLista> result) {
				
				 eliminar_senal_espera();

				datos_lista= result;
				
				proxy_listas.buscar_anios_primera_y_ultima_compra(new AsyncCallback<int []>(){
				public void onFailure(Throwable caught) {
					MensajeAlerta.mensaje_error("No se pudieron " +
					"obtener las fechas de la primera y ultima lista: " + caught.getMessage());
				}

				@Override
				public void onSuccess(int[] result) {
					BuscadorDatosEstaticos.anios_listas=result;
					armar_pantalla();
				}
			});
							
			}
			
		});
				
	}
		
	@SuppressWarnings({ "deprecation" })
	protected void armar_pantalla() {
	
			
	for(int i= anio_actual; i >= primer_anio_de_compras; i--) {

		Label anio = new Label("Año " + i);
		
		DisclosurePanel panel_anio= new DisclosurePanel();
		panel_anio.setHeader(anio);
		
		WidgetMostrarListas listas= new WidgetMostrarListas(datos_lista, "Listas de compras", this, new Date(i-1900,1-1,01), new Date(i-1900,12-1,31));
		panel_anio.setContent(listas);
		panel.add(panel_anio);
		
		if(i == anio_actual)
			panel_anio.setOpen(true);		
	}
	
	btn_agregar_lista= new Button("Nueva List<u>a</u>");
	menu.add(btn_agregar_lista);
	agregar_lista= new WidgetAgregarLista(this, null);
	
	agregar_handlers();		

	}

	private void agregar_handlers() {
		btn_agregar_lista.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				agregar_lista.show();
			}
		});
		btn_agregar_lista.setAccessKey('a');
	}

	public void borrar_lista(int id) {
		proxy_listas.borrar_lista(id, new AsyncCallback<Void>(){
			public void onFailure(Throwable caught) {
				MensajeAlerta.mensaje_error("Ocurrió un error al intentar borrar " +
						"la lista: " + caught.getMessage());
			}
			public void onSuccess(Void result) {
				Window.Location.reload();

			}
			
		});			
	}

	public void actualizar_producto(DatosLista datos_lista) {
		proxy_listas.actualizar_lista(datos_lista, true, new AsyncCallback<Void>(){
			public void onFailure(Throwable caught) {
				MensajeAlerta.mensaje_error("Ocurri� un error al intentar actualizar " +
						"la lista: " + caught.getMessage());
			}
			public void onSuccess(Void result) {
				Window.Location.reload();

			}
			
		});				
	}
	
	public LinkedList<DatosLista> ordenar_listas(Set<DatosLista> lista_compras) {
		LinkedList<DatosLista> lista_para_ordenar= new LinkedList<DatosLista>();
		//Obtengo todos los datos de las productos y los agrego a una lista.
		for (Iterator<DatosLista> iter = lista_compras.iterator(); iter.hasNext();) {
			DatosLista compra = iter.next();
			lista_para_ordenar.add(compra);
		}	
		//Ordeno por fecha la compra y en segunda instancia por el id de la misma.
		
		FechaListaComparator c= new FechaListaComparator();
		IdListaComparator c2= new IdListaComparator();

		Collections.sort(lista_para_ordenar, c.thenComparing(c2));
		
		return lista_para_ordenar;
	}

	/** Se crea el proxy_carga para comunicarse con el servidor.
	 */
//	@Override
//	protected void inicializar(){
//		super.inicializar();
//	}
}
