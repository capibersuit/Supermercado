package ar.gov.chris.client.pantalla;

import ar.gov.chris.client.GreetingService;
import ar.gov.chris.client.GreetingServiceAsync;

import com.google.gwt.core.client.GWT;

public class PantallaProductos extends Pantalla {
	
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	
	
	}
