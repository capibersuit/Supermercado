package ar.gov.chris.client.pantalla;

import java.util.Date;
import java.util.HashMap;

import ar.gov.chris.client.datos.DatosAutorizacion;
import ar.gov.chris.client.interfaces.ProxyPantallaLoguearse;
import ar.gov.chris.client.interfaces.ProxyPantallaLoguearseAsync;
import ar.gov.chris.client.util.Cookies;
import ar.gov.mecon.componentes.client.clases.Ambito;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;

public class PantallaLoguearseSimple extends Pantalla {
	protected static final String FUNCIONALIDAD= "funcionalidad_global_ap";
	protected Grid tabla;
	protected Button aceptar;
	protected Button limpiar;
//	protected TextBoxMecon login;
	protected TextBox login;

	protected PasswordTextBox contrasena;
	protected HTML resultado;
	protected HorizontalPanel botones;
	protected ProxyPantallaLoguearseAsync proxy;
	
	/** Constructor sin parámetros.
	 */
	public PantallaLoguearseSimple() {
		super();
		inicializar();
//		validacion_previa(proxy, FUNCIONALIDAD, true);
		pantalla_principal();
		
	}
	
	@Override
	protected void pantalla_principal() {
		// Si me llaman es que ya está logueado.
		mostrar_pantalla_login(true);
	}

//	@Override
//	protected void pantalla_no_autorizado() {
//		// Si me llaman es porque no está logueado.
////		mostrar_pantalla_login(false);
//	}

	/** Se crea el proxy para comunicarse con el servidor.
	 */
	protected void inicializar() {
		this.proxy= (ProxyPantallaLoguearseAsync) GWT.create(ProxyPantallaLoguearse.class);
//		super.inicializar((ServiceDefTarget) this.proxy, "Loguearse");
		super.inicializar((ServiceDefTarget) this.proxy, "Loguearse");
		
	}
	
	/** Inicializa elementos comunes a todas las pantallas de logueo. 
	 */
	protected void inicializar_elementos_comunes() {
		tabla= new Grid(2, 2);
		aceptar= new Button("Aceptar");
		limpiar= new Button("Limpiar");
//		login= new TextBoxMecon(aceptar);
		login= new TextBox();

		login.ensureDebugId("loguin_loguearse");
//		contrasena= new PasswordTextBoxMecon(aceptar);
		contrasena= new PasswordTextBox();

		resultado= new HTML("");
		
		tabla= new Grid(3, 2);
		tabla.setWidget(0, 0, new HTML("Login: "));
		tabla.setWidget(1, 0, new HTML("Contraseña: "));
		tabla.setWidget(0, 1, login);
		tabla.setWidget(1, 1, contrasena);
		
		botones= new HorizontalPanel();
		botones.setSpacing(3);
		botones.add(aceptar);
		botones.add(limpiar);
		
		agregar_listeners_comunes();
		
		agregar_listeners();
	}
	
	/**Agrega listeners comunes a todas las pantallas de logueo.
	 */
	protected void agregar_listeners_comunes() {
		limpiar.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				login.setText("");
				contrasena.setText("");
				resultado.setText("");
			}
		});
	}
	
	
	protected void agregar_listeners() {
		agregar_listeners_comunes();
		aceptar.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				
				proxy.validar_usuario(login.getText(), contrasena.getText(),
//						new AsyncCallback<DatosAutorizacion>(resultado) {
					    new AsyncCallback<DatosAutorizacion>() {				

					
					@SuppressWarnings("deprecation")
					public void onSuccess(DatosAutorizacion res) {
						if (!res.autenticado_y_autorizado()) {
							// Digo que no.
							resultado.setText(res.getMensaje_error());
						} else {
							// Digo que sí y le pongo las cookies.
							Cookies.poner_cookies_autorizacion(res);
							
							History.newItem("PantallaListaDeCompras");
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
	}
	
	
	protected void mostrar_pantalla_login(boolean ya_logueado) {		
				
		
		inicializar_elementos_comunes();
		
		if (ya_logueado) 
			resultado.setText("Ud. ya está logueado.");
		else {
			
		}
		
		
		panel.add(tabla);
		panel.add(botones);
		panel.add(resultado);

		// Seteo el foco en el textbox de login.
		dar_foco(login);

	}
	
//	/** Agrega los listeners.
//	 */
//	protected abstract void agregar_listeners();
//	
//	/** Arma y muestra la pantalla de logueo.
//	 * 
//	 * @param ya_logueado Indica si el usuario ya está logueado.
//	 */
//	protected abstract void mostrar_pantalla_login(boolean ya_logueado);
}
