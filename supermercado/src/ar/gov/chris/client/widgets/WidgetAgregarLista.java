package ar.gov.chris.client.widgets;

import ar.gov.chris.client.pantalla.Pantalla;
import ar.gov.chris.client.pantalla.PantallaListaDeCompras;
import ar.gov.chris.client.pantalla.PantallaListas;
import ar.gov.chris.client.pantalla.PantallaProductos;
import ar.gov.chris.client.pantalla.PantallaVistaDeCompra;
import ar.gov.chris.client.util.JavaScript;

import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;

public class WidgetAgregarLista extends DialogBox {

	private FlowPanel panel;
	private Button agregar;
	private Button cancelar;
	private TextBox comentario;
	private TextBox precio;
	private PantallaListaDeCompras parent;
	
	/** Constructor para generar un popup con un campo de texto que permite agregar 
	 * un CUDAP.
	 * 
	 * @param pantallaListaDeCompras La pantalla parent.
	 * @param id_widget Id para el widget que se agrega.
	 */
	public WidgetAgregarLista(PantallaListaDeCompras pantallaListaDeCompras) {
		super(true);
		this.parent= pantallaListaDeCompras;
		this.setText("Agregar Nueva Lista");
		comentario= new TextBox();
		
//		DisplayTituloWidget cudap= new DisplayTituloWidget("CUDAP: ", box_cudap);
		
		agregar= new Button("Agregar");
		cancelar= new Button("Cancelar");
		agregar_listeners();
		HorizontalPanel botones= new HorizontalPanel();
		botones.add(agregar);
		botones.add(cancelar);
		
		panel= new FlowPanel();
		panel.add(comentario);

		panel.add(botones);
		this.add(panel);
	}

	/** Agrega los listeners a los botones.
	 * @param id_widget ndice para el debugger.
	 */
	private void agregar_listeners() {
		agregar.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent arg0) {
					parent.agregar_lista(comentario.getText());
			hide();
			}
		});
		cancelar.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent arg0) {
				hide();
			}
		});
		comentario.addKeyPressHandler(new KeyPressHandler() {
			 @Override
			 public void onKeyPress(KeyPressEvent event) {
			  if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode())
				  agregar.click();
			 }
		 });
	}

//	public FocusWidget getComentarioCombo() {
//		// TODO Auto-generated method stub
//		return comentario;
//	}

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
     dar_foco(comentario);
    }
	
	/** Le da foco a un componente (tiene que ser un FocusWidget).
	 * 
	 * @param widget El componente que va a recibir el foco.
	 */
	static public void dar_foco(final FocusWidget widget) {
	 ScheduledCommand s= new ScheduledCommand() {
		
		@Override
		public void execute() {
			widget.setFocus(true);
			
		}
	 };
	 s.execute();
	}
}
