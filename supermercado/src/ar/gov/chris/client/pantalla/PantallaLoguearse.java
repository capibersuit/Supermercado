package ar.gov.chris.client.pantalla;

import ar.gov.chris.client.datos.DatosAutorizacion;
import ar.gov.chris.client.interfaces.ProxyPantallaLoguearse;
import ar.gov.chris.client.interfaces.ProxyPantallaLoguearseAsync;
import ar.gov.chris.client.util.Cookies;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class PantallaLoguearse extends Composite {

	@UiField
	InputElement login;

	@UiField
	InputElement password;

	@UiField
	Button acceder;

	protected HTML resultado = new HTML();


	protected ProxyPantallaLoguearseAsync proxy;

	private static PantallaLoguearseUiBinder uiBinder = GWT.create(PantallaLoguearseUiBinder.class);

	/** Interfaz del binder de {@link PantallaLoguearse}.
	 * 
	 * @author chris
	 *
	 */
	interface PantallaLoguearseUiBinder extends UiBinder<Widget, PantallaLoguearse> {
	}

	/** Constructor sin parámetros.
	 */
	public PantallaLoguearse() {
		inicializar();
		initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * Manejador de acceso.
	 * @param e El evento click.
	 */
	@UiHandler("acceder")	
	/**
	 * Onclick del botón de ingreso a la aplicación.
	 * @param e El evento click.
	 */
	void onClick(ClickEvent e) {

		String usuario=login.getValue();
		String contrasena=password.getValue();


		if(usuario.equalsIgnoreCase("") || contrasena.equalsIgnoreCase("")) {
			PantallaLoguearse.mostrar_span_error();
		} else {
			
//			if(usuario.equalsIgnoreCase("capibersuit") && contrasena.equalsIgnoreCase("laquevenga")) {
//				
//				
//				DatosAutorizacion res= new DatosAutorizacion();
//				// Digo que sí y le pongo las cookies.
//				Cookies.poner_cookies_autorizacion(res);
//				History.newItem("PantallaListaDeCompras");
//			} else {	
//					PantallaLoguearse.cambiar_texto("Contraseña y usuario no autorizados para utilizar la aplicación");
//				}

			proxy.validar_usuario(usuario, contrasena, new AsyncCallback<DatosAutorizacion>() {				
				@Override
				public void onSuccess(DatosAutorizacion res) {
					if (!res.autenticado_y_autorizado()) {
						// Digo que no.
						//					resultado.setText(res.getMensaje_error());
						PantallaLoguearse.cambiar_texto(res.getMensaje_error());

					} else {
						// Digo que sí y le pongo las cookies.
						Cookies.poner_cookies_autorizacion(res);
						// La cookie del ámbito no expira "nunca".
//						Date fecha_expiracion= new Date();
//						fecha_expiracion.setYear(200);
//						com.google.gwt.user.client.Cookies.setCookie("ambito", 
//								Ambito.PAHAC.toString(), fecha_expiracion);
						// Recargo el menu
						History.newItem("PantallaListaDeCompras");
					}
				}

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}

	/**
	 * Función que permite usar javascript para modificar el html una vez cargada la pag.
	 */
	public void onAttach() 
	{ 
		super.onAttach(); 
		login.focus();
	} 

	/**
	 * Cambia el texto del span del html que se usa para mostrar los errores.
	 * @param error El error a mostrar.
	 */
	public static native void cambiar_texto(String error) /*-{	 
	 var elem = $doc.getElementById('spanError');
	 elem.innerHTML = error; 		 
	}-*/;

	/**
	 * Muestra el mensaje de que se debe ingresar el usuario y contraseña.
	 */
	public static native void mostrar_span_error() /*-{
		var myDiv = $doc.getElementById('divError');
		myDiv.style.display = "";		
		}-*/;

		/** Se crea el proxy para comunicarse con el servidor.
		 */
		protected void inicializar() {
			this.proxy= (ProxyPantallaLoguearseAsync) GWT.create(ProxyPantallaLoguearse.class);
			inicializar((ServiceDefTarget) this.proxy, "Loguearse");
			
		}
		/** Termina de crear el proxy para comunicarse con el servidor.
		 * @param endpoint El endpoint para la comunicación con el servidor.
		 * @param pantalla El nombre de la pantalla (sin el prefijo "Pantalla").
		 */
		protected void inicializar(ServiceDefTarget endpoint, String pantalla) {
		 try {
			 String moduleRelativeURL= GWT.getModuleBaseURL() +
			 		"proxies_pantallas/ProxyPantalla"+pantalla;
			 endpoint.setServiceEntryPoint(moduleRelativeURL);
		 } catch (Exception ex) {
			 System.out.println("Excepción: " + ex.getMessage());
		 }
		}
}
