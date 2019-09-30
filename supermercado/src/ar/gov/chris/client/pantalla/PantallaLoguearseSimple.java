package ar.gov.chris.client.pantalla;

import ar.gov.chris.client.clases.BuscadorDatosEstaticos;
import ar.gov.chris.client.datos.DatosAutorizacion;
import ar.gov.chris.client.interfaces.ProxyPantallaLoguearse;
import ar.gov.chris.client.interfaces.ProxyPantallaLoguearseAsync;
import ar.gov.chris.client.util.Cookies;
//import ar.gov.mecon.componentes.client.clases.Ambito;
import ar.gov.chris.client.widgets.MensajeAlerta;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;

public class PantallaLoguearseSimple extends Pantalla {
	protected static final String FUNCIONALIDAD= "funcionalidad_global_ap";
	
	protected String pantalla_a_redireccionar;//= "funcionalidad_global_ap";
	
	protected Label header;

	protected FlowPanel contenedor;
	

//	protected Grid tabla;
	protected FlexTable tabla;

	protected Label inicial_sesion;
	protected Label footer;

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
	public PantallaLoguearseSimple(String pantalla_a_redireccionar) {
		super();
		this.pantalla_a_redireccionar= pantalla_a_redireccionar;
		inicializar();
//		validacion_previa(proxy, FUNCIONALIDAD, true);
		
		 this.panel.setStyleName("PanelPrincipalLogueo");
		 proxy.obtener_nombre_maquina_local(new AsyncCallback<String>(){
				public void onFailure(Throwable caught) {
					MensajeAlerta.mensaje_error("No se pudo " +
					"obtener el nombre de la maquina: " + caught.getMessage());
				}
				@Override
				public void onSuccess(String result) {
					NOMBRE_MAQUINA=result.toLowerCase();	
					pantalla_principal();

				}
			});

//		pantalla_principal();
		
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
		contenedor= new FlowPanel();
		tabla= new FlexTable();

		inicial_sesion= new Label("INICIO DE SESIÓN");
		inicial_sesion.setStyleName("cabecera");
		
		footer= new Label("SCLS - VERSIÓN: v"+ VERSION + " __ "+ NOMBRE_MAQUINA);
		footer.setStyleName("footer");
				
		header= new Label("Sistema de control de listas de supermercado");

		
		aceptar= new Button("Aceptar");
		limpiar= new Button("Limpiar");
//		login= new TextBoxMecon(aceptar);
		login= new TextBox();

		login.ensureDebugId("loguin_loguearse");
		login.setStyleName("textbox-logueo");
		login.getElement().setPropertyString("placeholder", " Ingrese su usuario");
//		contrasena= new PasswordTextBoxMecon(aceptar);
		contrasena= new PasswordTextBox();
		contrasena.setStyleName("textbox-logueo");
		contrasena.getElement().setPropertyString("placeholder", " Ingrese su contrasena");



		resultado= new HTML("");
		
//		tabla= new Grid(3, 2);
		tabla.setWidget(0, 0, new HTML("Usuario: "));
		tabla.setWidget(1, 0, new HTML("Contraseña: "));
		tabla.setWidget(0, 1, login);
		tabla.setWidget(1, 1, contrasena);
//		tabla.setCellPadding(55);
//		tabla.setCellSpacing(55);
//		
//		tabla.setStyleName("tableCell-even");
		tabla.setStylePrimaryName("centrar-tabla");

		
		botones= new HorizontalPanel();
		botones.setSpacing(3);
		botones.add(aceptar);
		botones.add(limpiar);
		
		botones.setStylePrimaryName("panel-botones-logueo");
//		botones.setStylePrimaryName("panel-botones-logueo");

		
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
		
		contrasena.addKeyPressHandler(new KeyPressHandler() {
			 @Override
			 public void onKeyPress(KeyPressEvent event) {
			  if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode())
				  aceptar.click();
			 }
		 });
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
							
							if(pantalla_a_redireccionar!=null) {

							if(pantalla_a_redireccionar.startsWith("PantallaVista")) {
								History.newItem(pantalla_a_redireccionar);

							}
								
								
							if(pantalla_a_redireccionar.endsWith("Compras"))
								History.newItem("PantallaListaDeCompras");
							if(pantalla_a_redireccionar.endsWith("Productos"))
								History.newItem("PantallaProductos");
							if(pantalla_a_redireccionar.endsWith("Vencimientos"))
								History.newItem("PantallaVencimientos");
							if(pantalla_a_redireccionar.endsWith("VencimientosOrd"))
								History.newItem("PantallaVencimientosOrd");
							if(pantalla_a_redireccionar.endsWith("Precios"))
								History.newItem("PantallaPrecios");


							} else {

							History.newItem("PantallaListaDeCompras");
							}
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
		
//		if (ya_logueado) 
//			resultado.setText("Ud. ya está logueado.");
//		else {
//			
//		}
		
//		inicial_sesion.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
//		inicial_sesion.getElement().getStyle().setPosition(Position.valueOf(name);

		contenedor.add(inicial_sesion);
		contenedor.add(tabla);
		
		contenedor.add(botones);
		contenedor.add(resultado);
		contenedor.add(footer);
		
//		panel.setStylePrimaryName("logueo-centrado");
		
//		panel.setStylePrimaryName("PanelPrincipalChris");
		
//		panel.setCellHorizontalAlignment( tabla, HasHorizontalAlignment.ALIGN_CENTER );
//		panel.setCellVerticalAlignment( grid, HasVerticalAlignment.ALIGN_MIDDLE );
		
		
		contenedor.setStylePrimaryName("centrar");
//		panel.setStylePrimaryName("innercontent");
		header.setStyleName("header");
		panel.add(header);

		panel.add(contenedor);

		



		// Seteo el foco en el textbox de login.
//		dar_foco(login);
//		login.setFocus(true);
//		contrasena.setFocus(true);
		
		//TODO: tuve que buscar esta solucion para setear el foco en el textBox de login en este pantalla
		// ya que el metodo "dar_foco" NO FUNCA y tampoco lo hace el setFocus directamente...
		//para mas info: 
		
		//  https://stackoverflow.com/questions/5944612/not-able-to-set-focus-on-textbox-in-a-gwt-app#
		
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand () {
	        public void execute () {
	            login.setFocus(true);
	        }
	    });


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
