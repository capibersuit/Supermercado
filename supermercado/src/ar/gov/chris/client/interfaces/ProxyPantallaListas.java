package ar.gov.chris.client.interfaces;

import ar.gov.chris.client.datos.DatosLista;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionBD;

import com.google.gwt.user.client.rpc.RemoteService;

public interface ProxyPantallaListas extends RemoteService {

	void agregar_lista(DatosLista datos_list) throws GWT_ExcepcionBD;

}
