package ar.gov.chris.server.proxies_pantallas;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import ar.gov.chris.client.GreetingService;
import ar.gov.chris.client.datos.DatosProducto;
import ar.gov.chris.client.interfaces.ProxyPantallaProductos;

@SuppressWarnings("serial")
public class ProxyPantallaProductosImpl extends RemoteServiceServlet implements
ProxyPantallaProductos {
	
	public void agregar_producto(DatosProducto datos_prod){
		
		System.out.print("hola mundo!!!");
	}

}
