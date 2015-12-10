package ar.gov.chris.client.interfaces;

import ar.gov.chris.client.datos.DatosLista;

import com.google.gwt.user.client.rpc.RemoteService;

public interface ProxyPantallaListas extends RemoteService {

	void agregar_lista(DatosLista datos_list);

}
