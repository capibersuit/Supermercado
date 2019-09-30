package ar.gov.chris.client;

import java.util.HashMap;

import ar.gov.chris.client.clases.BuscadorDatosEstaticos;
import ar.gov.chris.client.pantalla.Pantalla;
import ar.gov.chris.client.pantalla.PantallaInicio;
import ar.gov.chris.client.pantalla.PantallaListaDeCompras;
import ar.gov.chris.client.pantalla.PantallaLoguearseSimple;
import ar.gov.chris.client.pantalla.PantallaPrecios;
import ar.gov.chris.client.pantalla.PantallaPreciosConstantes;
import ar.gov.chris.client.pantalla.PantallaProductos;
import ar.gov.chris.client.pantalla.PantallaUiBinder2;
import ar.gov.chris.client.pantalla.PantallaVencimientos;
import ar.gov.chris.client.pantalla.PantallaVencimientosOrd;
import ar.gov.chris.client.pantalla.PantallaVistaDeCompra;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Supermercado implements EntryPoint, ValueChangeHandler<String> {
	
//	private final VerticalPanel panel_aplicacion= new VerticalPanel();
	private final ScrollPanel panel_aplicacion= new ScrollPanel();


	private static final HashMap<String, HTML> TITULOS= new HashMap<String, HTML>();

	public static final String PANTALLA_INICIO= "PantallaInicio";

	
	public static final String PANTALLA_VISTA_DE_COMPRA= "PantallaVistaDeCompra";
	public static final String PANTALLA_PRODUCTOS= "PantallaProductos";
	public static final String PANTALLA_lISTAS= "PantallaListaDeCompras";
	public static final String PANTALLA_VENCIMIENTOS= "PantallaVencimientos";
	public static final String PANTALLA_VENCIMIENTOSORD= "PantallaVencimientosOrd";
	public static final String PANTALLA_PRECIOS= "PantallaPrecios";
	public static final String PANTALLA_PRECIOS_CONSTANTES= "PantallaPreciosConstantes";

	
	public static final String PANTALLA_UIBINDER2= "PantallaUiBinder2";
	
	public static final String PANTALLA_LOGUEARSE= "PantallaLoguearse";
	
	public static final String PANTALLA_LOGUEARSE_SIMPLE= "PantallaLoguearseSimple";




	
	static {
		TITULOS.put(PANTALLA_INICIO, new HTML("Bienvenido al sistema de soporte del MECON"));
		TITULOS.put(PANTALLA_VISTA_DE_COMPRA, new HTML("Vista de compra"));
		TITULOS.put(PANTALLA_PRODUCTOS, new HTML("Productos"));
		TITULOS.put(PANTALLA_lISTAS, new HTML("Listas"));
		TITULOS.put(PANTALLA_VENCIMIENTOS, new HTML("Reporte de vencimientos"));
		TITULOS.put(PANTALLA_VENCIMIENTOSORD, new HTML("Reporte de vencimientos Ordenable"));


	}
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		RootLayoutPanel.get().add(panel_aplicacion);
//		RootPanel.get("menu").add(panel_aplicacion);
		
		// Se cargan todos los datos comunes una sola vez.
//		BuscadorDatosEstaticos.obtener_anios_primera_y_ultima_compra();
		BuscadorDatosEstaticos.obtener_sucursales();
		BuscadorDatosEstaticos.obtener_supermercados();
		
		BuscadorDatosEstaticos.obtener_nombre_maquina_local();
						
		//Add history listener
		History.addValueChangeHandler(this);

		// Inicialmente muestro la pantalla de logueo.
		String initToken= History.getToken();
		if (initToken.length() == 0)
//			initToken= PANTALLA_LOGUEARSE;
			initToken= PANTALLA_LOGUEARSE_SIMPLE;

		History.newItem(initToken);
		History.fireCurrentHistoryState();
	}

	/** Cambia la pantalla de acuerdo al parámetro.
	 * @param event La pantalla a mostrar. 
	 */
	public void onValueChange(ValueChangeEvent<String> event) {
		String historyToken= event.getValue();
		Window.setTitle("Sistema de control de listas de supermercado");
	
		
		if (historyToken.equals(PANTALLA_UIBINDER2)) {
			panel_aplicacion.clear();

			panel_aplicacion.add(new PantallaUiBinder2());
			Window.setTitle("Vista de productos");
		}
		
		if (historyToken.startsWith(PANTALLA_LOGUEARSE_SIMPLE)) {
			panel_aplicacion.clear();

//			panel_aplicacion.add(new PantallaLoguearse());
			
			
			String[] s = historyToken.split("-");
			Pantalla pl= null;
			
			String pantalla_a_redireccionar;
			if (s.length > 1) {
				 pantalla_a_redireccionar= s[1];
				 if(s.length > 2)
					 pantalla_a_redireccionar+= "-"+s[2];
				pl= new PantallaLoguearseSimple(pantalla_a_redireccionar);
				
			} else
				pl= new PantallaLoguearseSimple(null);
			panel_aplicacion.add(pl);

			Window.setTitle("Pantalla de logueo");
		}
	
		if (historyToken.equals(PANTALLA_INICIO)) {
			panel_aplicacion.clear();
			panel_aplicacion.add(new PantallaInicio());
			Window.setTitle("Sistema de control de listas de supermercado");

//			menu.cambiar_titulo_contenido(TITULOS.get(PANTALLA_INICIO));
//			menu.cambiar_contenido(new PantallaInicio());
		}
		
if (historyToken.equals(PANTALLA_lISTAS)) {
	panel_aplicacion.clear();

			panel_aplicacion.add(new PantallaListaDeCompras());
			Window.setTitle("Vista de listas de compras");

}

if (historyToken.equals(PANTALLA_PRODUCTOS)) {
	panel_aplicacion.clear();

	panel_aplicacion.add(new PantallaProductos());
	Window.setTitle("Vista de productos");

}

if (historyToken.startsWith(PANTALLA_VISTA_DE_COMPRA)) {
	panel_aplicacion.clear();

	String[] s = historyToken.split("-");
	Pantalla pv= null;
	if (s.length > 1) {
		String id= s[1];
		pv= new PantallaVistaDeCompra(id);
		Window.setTitle("Vista de compra nro: " + id);
	} else
		pv= new PantallaInicio("ERROR en Vista de compra: No ha ingresado un n�mero de compra.");
	panel_aplicacion.add(pv);
	}

		
if (historyToken.equals(PANTALLA_VENCIMIENTOS)) {
	panel_aplicacion.clear();

			panel_aplicacion.add(new PantallaVencimientos());
			Window.setTitle("Reporte de vencimientos Ordenable");

}

if (historyToken.equals(PANTALLA_VENCIMIENTOSORD)) {
	panel_aplicacion.clear();

			panel_aplicacion.add(new PantallaVencimientosOrd());
			Window.setTitle("Reporte de vencimientos");

}
	
if (historyToken.equals(PANTALLA_PRECIOS)) {
	panel_aplicacion.clear();

			panel_aplicacion.add(new PantallaPrecios());
			Window.setTitle("Reporte de Precios");

}

if (historyToken.equals(PANTALLA_PRECIOS_CONSTANTES)) {
	panel_aplicacion.clear();

			panel_aplicacion.add(new PantallaPreciosConstantes());
			Window.setTitle("Reporte de Precios Constantes");

}
		
//		else if (historyToken.startsWith(PANTALLA_BUSQUEDA_INTERNET)) {
//			String[] s = historyToken.split("-");
//			Pantalla pp= null;
//			if (s.length > 1)
//				pp= new PantallaBusquedaInternet(s[1]);
//			else
//				pp= new PantallaBusquedaInternet();
//			menu.cambiar_titulo_contenido(TITULOS.get(PANTALLA_BUSQUEDA));
//			menu.cambiar_contenido(pp);
//		}
//		else if (historyToken.startsWith(PANTALLA_BUSQUEDA)) {
//			String[] s = historyToken.split("-");
//			Pantalla pp= null;
//			if (s.length > 1)
//				pp= new PantallaBusquedaFICI(s[1]);
//			else
//				pp= new PantallaBusquedaFICI();
//			menu.cambiar_titulo_contenido(TITULOS.get(PANTALLA_BUSQUEDA));
//			menu.cambiar_contenido(pp);
//		}else if (historyToken.startsWith(PANTALLA_CARGAR_WEB)) {
//			menu.cambiar_titulo_contenido(TITULOS.get(PANTALLA_CARGAR_WEB));
//			menu.cambiar_contenido(new PantallaCargarWeb());
//		}
//		else if (historyToken.startsWith(PANTALLA_CARGAR_DIARIO)) {
//			String[] s = historyToken.split("-");
//			PantallaCargarDiario pcd= null;
//			if (s.length > 1)
//				pcd= new PantallaCargarDiario(PERM_CARGA_CASO, s[1]);
//			else
//				pcd= new PantallaCargarDiario(PERM_CARGA_CASO);
//			menu.cambiar_titulo_contenido(TITULOS.get(PANTALLA_CARGAR_DIARIO));
//			menu.cambiar_contenido(pcd);
//		}
//		else if (historyToken.equals(PANTALLA_CARGAR_ESPECIAL)) {
//			menu.cambiar_titulo_contenido(TITULOS.get(PANTALLA_CARGAR_ESPECIAL));
//			menu.cambiar_contenido(new PantallaCargarEspecial(PERM_CARGA_CASO));
//		}
//		else if (historyToken.equals(PANTALLA_CARGAR_MASIVO)) {
//			menu.cambiar_titulo_contenido(TITULOS.get(PANTALLA_CARGAR_MASIVO));
//			menu.cambiar_contenido(new PantallaCargarMasivo(PERM_CARGA_CASO));
//		}
//		else if (historyToken.startsWith(PANTALLA_VISTA_CASO_INTERNET_FICI)) {
//			String[] s = historyToken.split("-");
//			Pantalla pv= null;
//			if (s.length > 1) {
//				String id= s[1];
//				pv= new PantallaVistaCasoInternetAdmin(id, menu);
//				Window.setTitle("Soporte: " + id);
//			} else
//				pv= new PantallaInicio("ERROR en Vista de caso: No ha ingresado un número de caso.");
//			menu.cambiar_contenido(pv);
//		}
//		else if (historyToken.startsWith(PANTALLA_VISTA_CASO_INTERNET_USR)) {
//			String[] s = historyToken.split("-");
//			Pantalla pv= null;
//			if (s.length > 1) {
//				String id= s[1];
//				pv= new PantallaVistaCasoInternetUsr(id, menu);
//				Window.setTitle("Soporte: " + id);
//			} else
//				pv= new PantallaInicio("ERROR en Vista de caso: No ha ingresado un número de caso.");
//			menu.cambiar_contenido(pv);
//		}
//		else if (historyToken.startsWith(PANTALLA_VISTA_CASO_SOPORTE)) {
//			String[] s = historyToken.split("-");
//			Pantalla pv= null;
//			if (s.length > 1) {
//				String id= s[1];
//				pv= new PantallaVistaCasoSoporte(id, menu);
//				Window.setTitle("Soporte: " + id);
//			} else
//				pv= new PantallaInicio("ERROR en Vista de caso: No ha ingresado un número de caso.");
//			menu.cambiar_contenido(pv);
//		}
//		if (historyToken.equals(PANTALLA_CARGAR_CAIDA)) {
//			menu.cambiar_titulo_contenido(TITULOS.get(PANTALLA_CARGAR_CAIDA));
//			menu.cambiar_contenido(new PantallaCargarCaida(PERM_CARGA_CASO));
//		}
//		if (historyToken.equals(PANTALLA_CARGAR_INTERNET_USR)) {
//			menu.cambiar_titulo_contenido(TITULOS.get(PANTALLA_CARGAR_INTERNET_USR));
//			menu.cambiar_contenido(new PantallaCargarInternet(PERM_CARGAR_PEDIDO_INTERNET));
//		}
//		
//		if (historyToken.startsWith(PANTALLA_CARGAR_INTERNET_FICI)) {
//			String[] s = historyToken.split("-");
//			Pantalla pcif= null;
//			if (s.length > 1) 
//				pcif= new PantallaCargarInternetFici(PERM_ADMINISTRAR_PEDIDO_INTERNET, s[1]);
//			else
//				pcif= new PantallaCargarInternetFici(PERM_ADMINISTRAR_PEDIDO_INTERNET);
//			menu.cambiar_titulo_contenido(TITULOS.get(PANTALLA_CARGAR_INTERNET_FICI));
//			menu.cambiar_contenido(pcif);
//		}
//		
//		if (historyToken.startsWith(PANTALLA_CASOS_SOPORTE_PENDIENTES)) {
//			String[] s = historyToken.split("-");
//			Pantalla pp= null;
//			HTML titulo= null;
//			if (historyToken.equals(PANTALLA_CASOS_SOPORTE_PENDIENTES_NA)) {
//				titulo= TITULOS.get(PANTALLA_CASOS_SOPORTE_PENDIENTES_NA);
//				pp= new PantallaCasosPendientesNA();
//			} else if (historyToken.startsWith(PANTALLA_CASOS_SOPORTE_PENDIENTES_INTERNET_USR)) {
//				// Si es mayor había algo después del "-" debe ser "propios".
//				// Si no había nada, muestro los pendientes de internet.
//				if (s.length > 1) {
//					if (s[1].equalsIgnoreCase("propios")) {
//						titulo= TITULOS.get(PANTALLA_CASOS_SOPORTE_PENDIENTES_USUARIO);
//						pp= new PantallaCasosPendientesInternetUsr(true);
//					} 
//				} else {
//					titulo= TITULOS.get(PANTALLA_CASOS_SOPORTE_PENDIENTES_INTERNET_USR);
//					pp= new PantallaCasosPendientesInternetUsr();
//				}
//			} else if (historyToken.startsWith(PANTALLA_CASOS_SOPORTE_PENDIENTES_INTERNET)) {
//				// Si es mayor había algo después del "-" debe ser o "propios" o "NA".
//				// Si no había nada, muestro los pendientes de internet.
//				if (s.length > 1) {
//					if (s[1].equalsIgnoreCase("propios")) {
//						titulo= TITULOS.get(PANTALLA_CASOS_SOPORTE_PENDIENTES_USUARIO);
//						pp= new PantallaCasosPendientesInternet(true, false);
//					} else if (s[1].equalsIgnoreCase("NA")) {
//						
//						//***********************************************************
//						// 24/11/2011: CREO QUE SE PONIA MAL EL TITULO A LA PAGINA DE PENDIENTES NO ASIGNADOS !!!!¿¿¿¿????
//						
//						titulo= TITULOS.get(PANTALLA_CASOS_SOPORTE_PENDIENTES_NA);
//						
////						titulo= TITULOS.get(PANTALLA_CASOS_SOPORTE_PENDIENTES_CAT);
//						
//						//***********************************************************
//						
//						pp= new PantallaCasosPendientesInternet(false, true);
//					}
//				} else {
//					titulo= TITULOS.get(PANTALLA_CASOS_SOPORTE_PENDIENTES);
//					pp= new PantallaCasosPendientesInternet();
//				}
//			} else {
//				if (s.length > 1) {
//					if (s[1].equalsIgnoreCase("mios")) {
//						titulo= TITULOS.get(PANTALLA_CASOS_SOPORTE_PENDIENTES_USUARIO);
//						pp= new PantallaCasosPendientes(true);
//					}
//				} else {
//					titulo= TITULOS.get(PANTALLA_CASOS_SOPORTE_PENDIENTES);
//					pp= new PantallaCasosPendientes();
//				}
//			}
//			menu.cambiar_titulo_contenido(titulo);
//			menu.cambiar_contenido(pp);

	
	
	
	
	
	
	
	
	
	
	
	

	
//	/**
//	 * The message displayed to the user when the server cannot be reached or
//	 * returns an error.
//	 */
//	private static final String SERVER_ERROR = "An error occurred while "
//			+ "attempting to contact the server. Please check your network "
//			+ "connection and try again.";
//
//	/**
//	 * Create a remote service proxy to talk to the server-side Greeting service.
//	 */
//	private final GreetingServiceAsync greetingService = GWT
//			.create(GreetingService.class);
//
//	/**
//	 * This is the entry point method.
//	 */
//	public void onModuleLoad() {
//		final Button sendButton = new Button("Send");
//		final TextBox nameField = new TextBox();
//		nameField.setText("GWT User");
//		final Label errorLabel = new Label();
//
//		// We can add style names to widgets
//		sendButton.addStyleName("sendButton");
//
//		// Add the nameField and sendButton to the RootPanel
//		// Use RootPanel.get() to get the entire body element
//		RootPanel.get("nameFieldContainer").add(nameField);
//		RootPanel.get("sendButtonContainer").add(sendButton);
//		RootPanel.get("errorLabelContainer").add(errorLabel);
//
//		// Focus the cursor on the name field when the app loads
//		nameField.setFocus(true);
//		nameField.selectAll();
//
//		// Create the popup dialog box
//		final DialogBox dialogBox = new DialogBox();
//		dialogBox.setText("Remote Procedure Call");
//		dialogBox.setAnimationEnabled(true);
//		final Button closeButton = new Button("Close");
//		// We can set the id of a widget by accessing its Element
//		closeButton.getElement().setId("closeButton");
//		final Label textToServerLabel = new Label();
//		final HTML serverResponseLabel = new HTML();
//		VerticalPanel dialogVPanel = new VerticalPanel();
//		dialogVPanel.addStyleName("dialogVPanel");
//		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
//		dialogVPanel.add(textToServerLabel);
//		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
//		dialogVPanel.add(serverResponseLabel);
//		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
//		dialogVPanel.add(closeButton);
//		dialogBox.setWidget(dialogVPanel);
//
//		// Add a handler to close the DialogBox
//		closeButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				dialogBox.hide();
//				sendButton.setEnabled(true);
//				sendButton.setFocus(true);
//			}
//		});
//
//		// Create a handler for the sendButton and nameField
//		class MyHandler implements ClickHandler, KeyUpHandler {
//			/**
//			 * Fired when the user clicks on the sendButton.
//			 */
//			public void onClick(ClickEvent event) {
//				sendNameToServer();
//			}
//
//			/**
//			 * Fired when the user types in the nameField.
//			 */
//			public void onKeyUp(KeyUpEvent event) {
//				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
//					sendNameToServer();
//				}
//			}
//
//			/**
//			 * Send the name from the nameField to the server and wait for a response.
//			 */
//			private void sendNameToServer() {
//				// First, we validate the input.
//				errorLabel.setText("");
//				String textToServer = nameField.getText();
//				if (!FieldVerifier.isValidName(textToServer)) {
//					errorLabel.setText("Please enter at least four characters");
//					return;
//				}
//
//				// Then, we send the input to the server.
//				sendButton.setEnabled(false);
//				textToServerLabel.setText(textToServer);
//				serverResponseLabel.setText("");
//				greetingService.greetServer(textToServer,
//						new AsyncCallback<String>() {
//							public void onFailure(Throwable caught) {
//								// Show the RPC error message to the user
//								dialogBox
//										.setText("Remote Procedure Call - Failure");
//								serverResponseLabel
//										.addStyleName("serverResponseLabelError");
//								serverResponseLabel.setHTML(SERVER_ERROR);
//								dialogBox.center();
//								closeButton.setFocus(true);
//							}
//
//							public void onSuccess(String result) {
//								dialogBox.setText("Remote Procedure Call");
//								serverResponseLabel
//										.removeStyleName("serverResponseLabelError");
//								serverResponseLabel.setHTML(result);
//								dialogBox.center();
//								closeButton.setFocus(true);
//							}
//						});
//			}
//		}
//
//		// Add a handler to send the name to the server
//		MyHandler handler = new MyHandler();
//		sendButton.addClickHandler(handler);
//		nameField.addKeyUpHandler(handler);
//	}
}
}
