package ar.gov.chris.client.interfaces;

import java.util.LinkedList;
import java.util.Set;

import ar.gov.chris.client.datos.DatosListaProdCompleta;
import ar.gov.chris.client.datos.DatosProducto;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProxyPantallaProductosAsync {

	void agregar_producto(DatosProducto datos_prod,
			AsyncCallback<DatosProducto> asyncCallback);

	void buscar_productos(int id_compra, AsyncCallback<Set <DatosProducto>> asyncCallback);

	void agregar_producto_a_lista(DatosProducto datos_prod, int id_compra,
			int cant, int cant_en_gramos, AsyncCallback<DatosProducto> asyncCallback);

	void buscar_productos_lista(int id_lista,
			AsyncCallback<DatosListaProdCompleta> asyncCallback);

	void borrar_producto(String nombre, AsyncCallback<Void> asyncCallback);

	void borra_producto_de_lista(DatosProducto produ, int id_compra,
			AsyncCallback<Void> asyncCallback);

	void actualizar_producto(DatosProducto datos_prod,
			AsyncCallback<Void> asyncCallback);

	void actualizar_producto_a_lista(DatosProducto datos_prod,
			String id_compra, boolean cambiar_existencia, boolean es_marcar, AsyncCallback<Void> callback);

	void buscar_vencimientos(boolean solo_existentes, AsyncCallback<Set<DatosProducto>> asyncCallback);

	void marcar_desmarcar_productos(String valueOf, Set<String> ids, boolean marcar,
			AsyncCallback<Void> asyncCallback);

	void tomar_base_compra(LinkedList<DatosProducto> lista_productos, Integer id_lista_nueva,
			AsyncCallback<Void> asyncCallback);

}
