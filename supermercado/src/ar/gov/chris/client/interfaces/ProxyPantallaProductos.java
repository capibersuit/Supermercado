package ar.gov.chris.client.interfaces;

import ar.gov.chris.client.datos.DatosProducto;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionBD;

import com.google.gwt.user.client.rpc.RemoteService;

public interface ProxyPantallaProductos extends RemoteService {

	void agregar_producto(DatosProducto datos_prod) throws GWT_ExcepcionBD;

}
