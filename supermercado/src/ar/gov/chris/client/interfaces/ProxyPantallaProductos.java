package ar.gov.chris.client.interfaces;

import ar.gov.chris.client.datos.DatosProducto;

import com.google.gwt.user.client.rpc.RemoteService;

public interface ProxyPantallaProductos extends RemoteService {

	void agregar_producto(DatosProducto datos_prod);

}
