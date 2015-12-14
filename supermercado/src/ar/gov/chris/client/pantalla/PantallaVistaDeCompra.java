package ar.gov.chris.client.pantalla;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import ar.gov.chris.client.interfaces.ProxyPantallaListas;
import ar.gov.chris.client.interfaces.ProxyPantallaListasAsync;

public class PantallaVistaDeCompra extends Pantalla {

	private ProxyPantallaListasAsync proxy_listas;
	private int id_compra;


	public PantallaVistaDeCompra() {
//		super();
//		inicializar();
	}
	
	
	public PantallaVistaDeCompra(String id) {
		super();
		inicializar();
		id_compra= Integer.parseInt(id);
		
		}


	/** Se crea el proxy_carga para comunicarse con el servidor.
	 */
	protected void inicializar(){
		this.proxy_listas= (ProxyPantallaListasAsync)
		GWT.create(ProxyPantallaListas.class);
		super.inicializar((ServiceDefTarget) this.proxy_listas, "Listas");
	}
}
