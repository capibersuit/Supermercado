package ar.gov.chris.client.pantalla;

import com.google.gwt.user.client.ui.Button;

public class PantallaListas extends Pantalla{

	private Button btn_productos;
	private Button btn_agregar_lista;

	public PantallaListas() {
		super();
		pantalla_principal();
		// TODO Auto-generated constructor stub
	}

	private void pantalla_principal() {
		
//		btn_productos= new Button("Productos");
		btn_agregar_lista= new Button("Nueva Lista");
//		panel.add(btn_productos);
		panel.add(btn_agregar_lista);
	}

}
