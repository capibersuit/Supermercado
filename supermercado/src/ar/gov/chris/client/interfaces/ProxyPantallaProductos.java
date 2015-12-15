package ar.gov.chris.client.interfaces;

import java.util.Set;

import ar.gov.chris.client.datos.DatosProducto;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionBD;

import com.google.gwt.user.client.rpc.RemoteService;

public interface ProxyPantallaProductos extends RemoteService {

	void agregar_producto(DatosProducto datos_prod) throws GWT_ExcepcionBD;

	Set<DatosProducto> buscar_productos() throws GWT_ExcepcionBD;

	void agregar_producto_a_lista(DatosProducto datos_prod, int id_compra,
			int cant) throws GWT_ExcepcionBD;

	Set<DatosProducto> buscar_productos_lista(int id_lista)
			throws GWT_ExcepcionBD;

}
