package ar.gov.chris.client.widgets;

import ar.gov.chris.client.pantalla.Pantalla;
import ar.gov.chris.client.pantalla.PantallaListas;
import ar.gov.chris.client.pantalla.PantallaProductos;
import ar.gov.chris.client.pantalla.PantallaVistaDeCompra;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;

public class WidgetAgregarProducto extends DialogBox {

	private FlowPanel panel;
	private Button agregar;
	private Button cancelar;
	private TextBox nombre;
	private TextBox precio;
	private Pantalla parent;
	
	/** Constructor para generar un popup con un campo de texto que permite agregar 
	 * un CUDAP.
	 * 
	 * @param parent La pantalla parent.
	 * @param id_widget Id para el widget que se agrega.
	 */
	public WidgetAgregarProducto(Pantalla parent) {
		super(true);
		this.parent= parent;
		this.setText("Agregar Nuevo Producto");
		nombre= new TextBox();
		precio= new TextBox();
//		DisplayTituloWidget cudap= new DisplayTituloWidget("CUDAP: ", box_cudap);
		
		agregar= new Button("Agregar");
		cancelar= new Button("Cancelar");
		agregar_listeners();
		HorizontalPanel botones= new HorizontalPanel();
		botones.add(agregar);
		botones.add(cancelar);
		
		panel= new FlowPanel();
		panel.add(nombre);
		panel.add(precio);

		panel.add(botones);
		this.add(panel);
	}

	/** Agrega los listeners a los botones.
	 * @param id_widget ndice para el debugger.
	 */
	private void agregar_listeners() {
		agregar.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent arg0) {
				if(parent instanceof PantallaProductos)
					((PantallaProductos)parent).agregar_producto(nombre.getText(), precio.getText());
				else if(parent instanceof PantallaVistaDeCompra)
					((PantallaVistaDeCompra)parent).agregar_producto(nombre.getText(), precio.getText());
				hide();
			}
		});
		cancelar.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent arg0) {
				hide();
			}
		});
	}


}
