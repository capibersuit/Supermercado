package ar.gov.chris.client.interfaces;

import java.util.Set;

import ar.gov.chris.client.datos.DatosProducto;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionBD;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionNoAutorizado;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionNoExiste;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionYaExiste;

import com.google.gwt.user.client.rpc.RemoteService;

public interface ProxyPantallaProductos extends RemoteService {

	DatosProducto agregar_producto(DatosProducto datos_prod) throws GWT_ExcepcionBD, GWT_ExcepcionYaExiste;

	Set<DatosProducto> buscar_productos(int id_compra) throws GWT_ExcepcionBD, GWT_ExcepcionNoAutorizado;

	Set<DatosProducto> buscar_productos_lista(int id_lista)
			throws GWT_ExcepcionBD, GWT_ExcepcionNoExiste;

	void borrar_producto(String nombre) throws GWT_ExcepcionBD, GWT_ExcepcionNoExiste;

	void borra_producto_de_lista(DatosProducto produ, int id_compra) throws GWT_ExcepcionBD, GWT_ExcepcionNoExiste;

	void actualizar_producto(DatosProducto datos_prod) throws GWT_ExcepcionBD, GWT_ExcepcionNoExiste;

	void actualizar_producto_a_lista(DatosProducto datos_prod,
			String id_compra, boolean cambiar_existencia) throws GWT_ExcepcionBD, GWT_ExcepcionNoExiste;

	DatosProducto agregar_producto_a_lista(DatosProducto datos_prod,
			int id_compra, int cant) throws GWT_ExcepcionBD, GWT_ExcepcionNoExiste;

	Set<DatosProducto> buscar_vencimientos(boolean solo_existentes) throws GWT_ExcepcionBD, GWT_ExcepcionNoAutorizado;

	void marcar_desmarcar_productos(String valueOf, Set<String> ids, boolean marcar) throws GWT_ExcepcionBD, GWT_ExcepcionNoExiste;

}
