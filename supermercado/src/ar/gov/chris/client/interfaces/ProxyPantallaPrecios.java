package ar.gov.chris.client.interfaces;

import java.util.Set;

import ar.gov.chris.client.datos.DatosReprtePrecios;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionBD;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionNoAutorizado;

import com.google.gwt.user.client.rpc.RemoteService;

public interface ProxyPantallaPrecios  extends RemoteService {

	Set<DatosReprtePrecios> buscar_precios(String desde, String hasta) throws GWT_ExcepcionBD, GWT_ExcepcionNoAutorizado;

}
