package ar.gov.chris.client.interfaces;

import java.util.Set;

import ar.gov.chris.client.datos.DatosReprtePrecios;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProxyPantallaPreciosAsync {

	void buscar_precios(String desde, String hasta, AsyncCallback<Set<DatosReprtePrecios>> asyncCallback);

}
