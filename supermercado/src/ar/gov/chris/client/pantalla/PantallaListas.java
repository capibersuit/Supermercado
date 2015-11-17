package ar.gov.chris.client.pantalla;

import com.google.gwt.user.client.ui.Button;

public class PantallaListas extends Pantalla{

	
	private Button btn_agregar_lista;

	public PantallaListas() {
		super();
		pantalla_principal();
	}

	private void pantalla_principal() {
		
		btn_agregar_lista= new Button("Nueva Lista");
		panel.add(btn_agregar_lista);
	}

}
