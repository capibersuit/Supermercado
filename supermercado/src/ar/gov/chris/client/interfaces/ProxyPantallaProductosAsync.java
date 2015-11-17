package ar.gov.chris.client.interfaces;

import ar.gov.chris.client.datos.DatosProducto;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProxyPantallaProductosAsync {

	void agregar_producto(DatosProducto datos_prod,
			AsyncCallback<Void> asyncCallback);

}
