package ar.gov.chris.client.interfaces;

import java.util.Set;

import ar.gov.chris.client.datos.DatosProducto;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionBD;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionNoExiste;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionYaExiste;

import com.google.gwt.user.client.rpc.RemoteService;

public interface ProxyPantallaProductos extends RemoteService {

	void agregar_producto(DatosProducto datos_prod) throws GWT_ExcepcionBD, GWT_ExcepcionNoExiste, GWT_ExcepcionYaExiste;

	Set<DatosProducto> buscar_productos() throws GWT_ExcepcionBD;

	Set<DatosProducto> buscar_productos_lista(int id_lista)
			throws GWT_ExcepcionBD, GWT_ExcepcionNoExiste;

	void borrar_producto(String nombre) throws GWT_ExcepcionBD;

	void borra_producto_de_lista(String nombre, int id_compra) throws GWT_ExcepcionBD;

	void actualizar_producto(DatosProducto datos_prod) throws GWT_ExcepcionBD;

	void actualizar_producto_a_lista(DatosProducto datos_prod, String id_compra)
			throws GWT_ExcepcionBD;

	void agregar_producto_a_lista(DatosProducto datos_prod, int id_compra,
			int cant) throws GWT_ExcepcionBD;

}
