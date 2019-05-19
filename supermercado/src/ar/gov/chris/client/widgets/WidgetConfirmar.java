package ar.gov.chris.client.widgets;

import ar.gov.chris.client.datos.DatosProducto;
import ar.gov.chris.client.pantalla.Pantalla;
import ar.gov.chris.client.util.JavaScript;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class WidgetConfirmar extends DialogBox {

	private FlowPanel panel;
	private Button aceptar;
	private Button cancelar;

	private Pantalla parent;
	private DatosProducto prod_actual;
	private ClickHandler handler;
	
	/** Constructor para generar un popup con un campo de texto que permite agregar 
	 * un CUDAP.
	 * @param prod_actual 
	 * 
	 * @param pantallaListaDeCompras La pantalla parent.
	 */
	public WidgetConfirmar(Pantalla pantalla, String mensaje, ClickHandler handler) {
		super(true);
		this.parent= pantalla;
//		this.prod_actual= prod_actual;
		this.setText("Confirmar accion");
		
		this.handler= handler;
		
		aceptar= new Button("Aceptar");
		cancelar= new Button("Cancelar");
		agregar_listeners();
		HorizontalPanel texto= new HorizontalPanel();
		 texto.setSpacing(15);
		 Label msj= new Label(mensaje, true);
		 texto.add(msj);
		HorizontalPanel botones= new HorizontalPanel();
		botones.add(aceptar);
		botones.add(cancelar);
//		agregar_listeners();
		
//		aceptar.setFocus(true);
		
		panel= new FlowPanel();
		 panel.add(texto);

		panel.add(botones);
		this.add(panel);
	}

	/** Agrega los listeners a los botones.
	 * @param id_widget ndice para el debugger.
	 */
	private void agregar_listeners() {
//		aceptar.addClickHandler(new ClickHandler(){
//			public void onClick(ClickEvent arg0) {
//				if(parent instanceof PantallaProductos)
//					((PantallaProductos)parent).borrar_producto(prod_actual.getNombre());
//				else if(parent instanceof PantallaVistaDeCompra)
//					((PantallaVistaDeCompra)parent).borra_producto_de_lista(prod_actual.getNombre(), ((PantallaVistaDeCompra)parent).getId_compra());
//				hide();
//			}
//		});
		
		aceptar.addClickHandler(handler);
		cancelar.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent arg0) {
				hide();
			}
		});
		
		aceptar.addKeyPressHandler(new KeyPressHandler() {
			 @Override
			 public void onKeyPress(KeyPressEvent event) {
			  if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode())
				  aceptar.click();
			  hide();
			 }
		 });
	}

	/**
	 * Muestra el popup en el centro de la pantalla.
	 */
	@Override
	public void show() {
	 super.show();
	 int left= (Window.getClientWidth() - getOffsetWidth()) / 2 +
	 		JavaScript.getBodyScrollLeft();
     int top= (Window.getClientHeight() - getOffsetHeight()) / 2 +
     		JavaScript.getBodyScrollTop();
     setPopupPosition(left, top);
     aceptar.setFocus(true);
      }

}

