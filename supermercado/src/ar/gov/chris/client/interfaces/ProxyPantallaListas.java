package ar.gov.chris.client.interfaces;

import java.util.List;
import java.util.Set;

import ar.gov.chris.client.clases.Sucursal;
import ar.gov.chris.client.clases.Super;
import ar.gov.chris.client.datos.DatosLista;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionBD;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionNoAutorizado;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionNoExiste;

import com.google.gwt.user.client.rpc.RemoteService;

public interface ProxyPantallaListas extends RemoteService {

	int agregar_lista(DatosLista datos_list) throws GWT_ExcepcionBD;

	Set<DatosLista> buscar_listas() throws GWT_ExcepcionBD, GWT_ExcepcionNoAutorizado;

	void existe_lista(int id_compra) throws GWT_ExcepcionBD, GWT_ExcepcionNoExiste;

	void borrar_lista(int id_compra) throws GWT_ExcepcionBD;

	void actualizar_lista(DatosLista datos_lista, boolean actualizar_desc) throws GWT_ExcepcionBD;

	void mostrar_ocultar_prod_en_lista(int id_compra);

	DatosLista lista_esta_visible(int id_compra) throws GWT_ExcepcionBD, GWT_ExcepcionNoExiste;

	float buscar_desc_coto(int id_compra) throws GWT_ExcepcionBD, GWT_ExcepcionNoExiste;

	void hab_deshab_botones(String valueOf, boolean botones_habilitados) throws GWT_ExcepcionBD;

	int[] buscar_anios_primera_y_ultima_compra() throws GWT_ExcepcionBD;

	List<Sucursal> buscar_sucursales() throws GWT_ExcepcionBD;

	List<Super> buscar_supermercados() throws GWT_ExcepcionBD;

//	void actualizar_descuento_lista(DatosLista dl, boolean b);


}
