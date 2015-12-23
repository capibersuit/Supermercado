package ar.gov.chris.client.interfaces;

import java.util.Set;

import ar.gov.chris.client.datos.DatosLista;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProxyPantallaListasAsync {
	
	void agregar_lista(DatosLista datos_list,
			AsyncCallback<Void> asyncCallback);

	void buscar_listas(AsyncCallback<Set<DatosLista>> asyncCallback);

	void existe_lista(int id_compra, AsyncCallback<Void> asyncCallback);

	void borrar_lista(int id_compra, AsyncCallback<Void> asyncCallback);

	void actualizar_lista(DatosLista datos_lista,
			AsyncCallback<Void> asyncCallback);
}
