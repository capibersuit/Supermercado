package ar.gov.chris.client.gwt.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;

public class AsyncCallbackCHRIS <T> implements AsyncCallback<T> {
	
	private HTML resultado_error;

	/**
	 * @param html_error Indica dónde mostrar el error (si se produce).
	 */
	public AsyncCallbackCHRIS(HTML html_error) {
	 this.resultado_error= html_error;
	}

	/**
	 * onFailure del callback.
	 * 
	 * @param caught La excepción que tira el server.
	 */
	@Override
	public void onFailure(Throwable caught) {
	 resultado_error.setHTML(caught.getMessage());
	}

	/**
	 * onSuccess del callback.
	 * 
	 * @param result El objeto que devuelve el server.
	 */
	@Override
	public void onSuccess(T result) {
	 throw new RuntimeException("No se definió onSuccess() para una clase que hereda de "+
	 		"AsyncCallbackMECON.");
	}
}
