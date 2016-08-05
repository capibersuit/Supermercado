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

	void actualizar_lista(DatosLista datos_lista, boolean actualizar_desc,
			AsyncCallback<Void> asyncCallback);

	void mostrar_ocultar_prod_en_lista(int id_compra,
			AsyncCallback<Void> asyncCallback);

	void lista_esta_visible(int id_compra, AsyncCallback<DatosLista> asyncCallback);

	void buscar_desc_coto(int id_compra, AsyncCallback<Float> asyncCallback);

//	void actualizar_descuento_lista(DatosLista dl,
//			boolean b, AsyncCallback<Void> asyncCallback);
}
