package ar.gov.chris.client.interfaces;

import java.util.List;
import java.util.Set;

import ar.gov.chris.client.clases.Sucursal;
import ar.gov.chris.client.clases.Super;
import ar.gov.chris.client.datos.DatosLista;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProxyPantallaListasAsync {
	
	void agregar_lista(DatosLista datos_list,
			AsyncCallback<Integer> asyncCallback);

	void buscar_listas(AsyncCallback<Set<DatosLista>> asyncCallback);

	void existe_lista(int id_compra, AsyncCallback<Void> asyncCallback);

	void borrar_lista(int id_compra, AsyncCallback<Void> asyncCallback);

	void actualizar_lista(DatosLista datos_lista, boolean actualizar_desc,
			AsyncCallback<Void> asyncCallback);

	void mostrar_ocultar_prod_en_lista(int id_compra,
			AsyncCallback<Void> asyncCallback);

	void lista_esta_visible(int id_compra, AsyncCallback<DatosLista> asyncCallback);

	void buscar_desc_coto(int id_compra, AsyncCallback<Float> asyncCallback);

	void hab_deshab_botones(String valueOf, boolean botones_habilitados,
			AsyncCallback<Void> asyncCallback);

	void buscar_anios_primera_y_ultima_compra(AsyncCallback<int[]> asyncCallback);

	void buscar_sucursales(AsyncCallback<List<Sucursal>> asyncCallback);

	void buscar_supermercados(AsyncCallback<List<Super>> asyncCallback);

//	void actualizar_descuento_lista(DatosLista dl,
//			boolean b, AsyncCallback<Void> asyncCallback);
}
