package ar.gov.chris.client.interfaces;

import java.util.Set;

import ar.gov.chris.client.datos.DatosProducto;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProxyPantallaProductosAsync {

	void agregar_producto(DatosProducto datos_prod,
			AsyncCallback<DatosProducto> asyncCallback);

	void buscar_productos(AsyncCallback<Set <DatosProducto>> asyncCallback);

	void agregar_producto_a_lista(DatosProducto datos_prod, int id_compra,
			int cant, AsyncCallback<DatosProducto> asyncCallback);

	void buscar_productos_lista(int id_lista,
			AsyncCallback<Set<DatosProducto>> callback);

	void borrar_producto(String nombre, AsyncCallback<Void> asyncCallback);

	void borra_producto_de_lista(String nombre, int id_compra,
			AsyncCallback<Void> asyncCallback);

	void actualizar_producto(DatosProducto datos_prod,
			AsyncCallback<Void> asyncCallback);

	void actualizar_producto_a_lista(DatosProducto datos_prod,
			String id_compra, AsyncCallback<Void> callback);

}
