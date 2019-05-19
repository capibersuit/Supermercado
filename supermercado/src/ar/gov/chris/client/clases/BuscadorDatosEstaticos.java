package ar.gov.chris.client.clases;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import ar.gov.chris.client.interfaces.ProxyPantallaListas;
import ar.gov.chris.client.interfaces.ProxyPantallaListasAsync;
import ar.gov.chris.client.widgets.MensajeAlerta;

public class BuscadorDatosEstaticos {

	private static ProxyPantallaListasAsync proxy;
	public static int [] anios_listas = new int[2];
	/** Todos las sucursales posibles.
	 */
	public static List<Sucursal> sucursales	;
	/** Todas los supermercados posibles.
	 */
	public static List<Super> supermercados;
	
	
	/** Obtiene todas las categorías de problema y las almacena en una constante. 
	 */
	public static void obtener_anios_primera_y_ultima_compra() {
		//if (anios_listas == null ) {
			inicializar();
			proxy.buscar_anios_primera_y_ultima_compra(new AsyncCallback<int []>(){
				public void onFailure(Throwable caught) {
					MensajeAlerta.mensaje_error("No se pudieron " +
					"obtener las fechas de la primera y ultima lista: " + caught.getMessage());
				}

				@Override
				public void onSuccess(int[] result) {
					anios_listas=result;
					
				}
			});
		//}
	}
	
	public static void obtener_sucursales() {
		//if (anios_listas == null ) {
			inicializar();
			proxy.buscar_sucursales(new AsyncCallback<List<Sucursal>>(){
				public void onFailure(Throwable caught) {
					MensajeAlerta.mensaje_error("No se pudieron " +
					"obtener las sucursales: " + caught.getMessage());
				}

				@Override
				public void onSuccess(List<Sucursal> result) {
					sucursales=result;
					
				}
			});
	}
	
	public static void obtener_supermercados() {
		//if (anios_listas == null ) {
			inicializar();
			proxy.buscar_supermercados(new AsyncCallback<List<Super>>(){
				public void onFailure(Throwable caught) {
					MensajeAlerta.mensaje_error("No se pudieron " +
					"obtener las sucursales: " + caught.getMessage());
				}

				@Override
				public void onSuccess(List<Super> result) {
					supermercados=result;
					
				}
			});
		}
	
	
	/** Se crea el proxy para comunicarse con el servidor.
	 */
	private static void inicializar() {
		proxy= (ProxyPantallaListasAsync)
		GWT.create(ProxyPantallaListas.class);
		try {
			String moduleRelativeURL= GWT.getModuleBaseURL() +
			"proxies_pantallas/ProxyPantallaListas";
			((ServiceDefTarget) proxy).setServiceEntryPoint(moduleRelativeURL);
		} catch (Exception ex) {
			System.out.println("Excepción: " + ex.getMessage());
		}
	}
	
}
