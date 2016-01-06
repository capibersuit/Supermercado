package ar.gov.chris.client.interfaces;

import java.util.Set;

import ar.gov.chris.client.datos.DatosLista;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionBD;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionNoExiste;

import com.google.gwt.user.client.rpc.RemoteService;

public interface ProxyPantallaListas extends RemoteService {

	void agregar_lista(DatosLista datos_list) throws GWT_ExcepcionBD;

	Set<DatosLista> buscar_listas() throws GWT_ExcepcionBD;

	void existe_lista(int id_compra) throws GWT_ExcepcionBD, GWT_ExcepcionNoExiste;

	void borrar_lista(int id_compra) throws GWT_ExcepcionBD;

	void actualizar_lista(DatosLista datos_lista) throws GWT_ExcepcionBD;

	void mostrar_ocultar_prod_en_lista(int id_compra);

	int lista_esta_visible(int id_compra) throws GWT_ExcepcionBD, GWT_ExcepcionNoExiste;

	float buscar_desc_coto(int id_compra) throws GWT_ExcepcionBD, GWT_ExcepcionNoExiste;


}
