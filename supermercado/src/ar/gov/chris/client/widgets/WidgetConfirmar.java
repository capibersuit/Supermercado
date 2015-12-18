package ar.gov.chris.client.widgets;

import ar.gov.chris.client.datos.DatosProducto;
import ar.gov.chris.client.pantalla.Pantalla;
import ar.gov.chris.client.pantalla.PantallaListaDeCompras;
import ar.gov.chris.client.pantalla.PantallaProductos;
import ar.gov.chris.client.pantalla.PantallaVistaDeCompra;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;

public class WidgetConfirmar extends DialogBox {

	private FlowPanel panel;
	private Button aceptar;
	private Button cancelar;

	private Pantalla parent;
	private DatosProducto prod_actual;
	
	/** Constructor para generar un popup con un campo de texto que permite agregar 
	 * un CUDAP.
	 * @param prod_actual 
	 * 
	 * @param pantallaListaDeCompras La pantalla parent.
	 */
	public WidgetConfirmar(Pantalla pantalla, DatosProducto prod_actual) {
		super(true);
		this.parent= pantalla;
		this.prod_actual= prod_actual;
		this.setText("Confirmar accion");
		
		
		aceptar= new Button("Agregar");
		cancelar= new Button("Cancelar");
		agregar_listeners();
		HorizontalPanel botones= new HorizontalPanel();
		botones.add(aceptar);
		botones.add(cancelar);
		agregar_listeners();
		
		panel= new FlowPanel();

		panel.add(botones);
		this.add(panel);
	}

	/** Agrega los listeners a los botones.
	 * @param id_widget ndice para el debugger.
	 */
	private void agregar_listeners() {
		aceptar.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent arg0) {
				if(parent instanceof PantallaProductos)
					((PantallaProductos)parent).borrar_producto(prod_actual.getNombre());
				else if(parent instanceof PantallaVistaDeCompra)
					((PantallaVistaDeCompra)parent).borra_producto_de_lista(prod_actual.getNombre(), ((PantallaVistaDeCompra)parent).getId_compra());
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

