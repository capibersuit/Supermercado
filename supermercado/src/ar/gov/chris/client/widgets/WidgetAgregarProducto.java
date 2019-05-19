package ar.gov.chris.client.widgets;

import java.util.Date;

import ar.gov.chris.client.datos.DatosProducto;
import ar.gov.chris.client.gwt.PopupCalendario;
import ar.gov.chris.client.pantalla.Pantalla;
import ar.gov.chris.client.pantalla.PantallaProductos;
import ar.gov.chris.client.pantalla.PantallaVistaDeCompra;
import ar.gov.chris.client.util.JavaScript;
import ar.gov.chris.client.util.Mate;

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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class WidgetAgregarProducto extends DialogBox {

	private FlowPanel panel;
	private Button agregar;
	private Button cancelar;
	private TextBox nombre;
	private TextBox precio;
	private TextBox cantidad;
	private TextBox fecha_venc;
	private Label lblfecha_venc;
	private Button calendario;

	private Pantalla parent;
	
	private boolean es_update;
	
	private DatosProducto datos_prod = new DatosProducto();
	
	/** Constructor para generar un popup con un campo de texto que permite agregar 
	 *  o modificar un producto.
	 * 
	 * @param parent La pantalla parent.
	 * @param id_widget Id para el widget que se agrega.
	 */
	public WidgetAgregarProducto(Pantalla parent, DatosProducto prod) {
		super(true);
		this.parent= parent;
		this.es_update= prod != null;
		if(es_update) { 
			datos_prod= prod;
			datos_prod.setPrecio_anterior(prod.getPrecio());
			datos_prod.setCantidad_anterior(prod.getCantidad());
			
	    }
		nombre= new TextBox();
		precio= new TextBox();
		cantidad = new TextBox();
		agregar= new Button("Agregar");
		cancelar= new Button("Cancelar");
		calendario= new Button("Vencimiento");
		lblfecha_venc= new Label("Fecha");
		fecha_venc= new TextBox();
		if(es_update) {
			this.setText("Actualizar Producto");
			nombre.setText(prod.getNombre());
			precio.setText(String.valueOf((Mate.poner_dos_decimales(prod.getPrecio()))));
			cantidad.setText(String.valueOf(prod.getCantidad()));
			fecha_venc.setText((prod.getFecha_venc()!= null)? prod.getFecha_venc().toString():"");
			agregar.setText("Actualizar");
		} else
			this.setText("Agregar Nuevo Producto");
		
//		DisplayTituloWidget cudap= new DisplayTituloWidget("CUDAP: ", box_cudap);
		
		
		agregar_listeners();
		HorizontalPanel botones= new HorizontalPanel();
		botones.add(agregar);
		if(es_update)
			botones.add(calendario);
		botones.add(cancelar);
		
		panel= new FlowPanel();
		panel.add(nombre);
		panel.add(precio);
		if(parent instanceof PantallaVistaDeCompra) {
			panel.add(cantidad);
			panel.add(lblfecha_venc);
			panel.add(fecha_venc);
		}
		panel.add(botones);
		this.add(panel);
	}

	/** Agrega los listeners a los botones.
	 * @param id_widget ndice para el debugger.
	 */
	private void agregar_listeners() {
		agregar.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent arg0) {
				
				if(!precio.getText().matches(" *") && !nombre.getText().matches(" *")) {
					
					try {
				
				datos_prod.setNombre(nombre.getText().trim());
				datos_prod.setPrecio(Float.parseFloat(precio.getText()));
				
				if(parent instanceof PantallaProductos) {

					if(es_update) {
						((PantallaProductos)parent).actualizar_producto(datos_prod);
					} else
					    ((PantallaProductos)parent).agregar_producto(datos_prod);
				} else {
					if(parent instanceof PantallaVistaDeCompra)
						if(es_update) {
							datos_prod.setCantidad(Integer.parseInt(cantidad.getText()));
							String venc_text= fecha_venc.getText();
							//---------------------------
							if(!venc_text.isEmpty()) {
							//Esto de la fecha lo hago así por ahora,
							//despues veo si lo puedo hacer mejor
							String[] datos= venc_text.split("-", 3);
							String anio=datos[0];
							String mes=datos[1];
							String dia=datos[2];
							
							// no se bien porque pero tengo que  restar 1900 
							// para que me quede bien el anio.
							int anionum= Integer.parseInt(anio)-1900;
							int mesnum= Integer.parseInt(mes)-1;
							int dianum= Integer.parseInt(dia);
							Date f_venc= new Date(anionum, mesnum, dianum);
							
							
							
							//--------------------
							
							
//							Date f_venc= new Date(venc_text);
//							Date f_venc= new Date(DateFormat.parse(venc_text));
							datos_prod.setFechaVenc(f_venc);
							
							
							}
							((PantallaVistaDeCompra)parent).actualizar_producto(datos_prod, false);
						} 
				}
				hide();
			}
			
				 catch (NumberFormatException e) {
					MensajeAlerta.mensaje_error("Error: " + e.getMessage() + ". Ingrese un precio valido.");
//					throw new GWT_ExcepcionFormatoInvalido(e);
				}
			} else {
				MensajeAlerta.mensaje_error("El nombre y el precio no pueden ser vacios.");
			}
		}});
		cancelar.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent arg0) {
				hide();
			}
		});
		nombre.addKeyPressHandler(new KeyPressHandler() {
			 @Override
			 public void onKeyPress(KeyPressEvent event) {
			  if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode())
				  agregar.click();
			 }
		 });
		
		precio.addKeyPressHandler(new KeyPressHandler() {
			 @Override
			 public void onKeyPress(KeyPressEvent event) {
				 //Esto era para no permitir escribir otra cosa que no sea un numero
				 //pero asi tal cual no me sirve porque tampoco me deja "borrar"
				 //o sea, apreto la tecla de backspace y no hace nada...
				 
//				 if (!Character.isDigit(event.getCharCode())) {
//			          ((TextBox) event.getSource()).cancelKey();
//			        }
			  if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode())
				  agregar.click();
			 }
		 });
		
		cantidad.addKeyPressHandler(new KeyPressHandler() {
			 @Override
			 public void onKeyPress(KeyPressEvent event) {
				//Esto era para no permitir escribir otra cosa que no sea un numero
				 //pero asi tal cual no me sirve porque tampoco me deja "borrar"
				 //o sea, apreto la tecla de backspace y no hace nada...
				 
//				 if (!Character.isDigit(event.getCharCode())) {
//			          ((TextBox) event.getSource()).cancelKey();
//			        }
			  if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode())
				  agregar.click();
			 }
		 });
		
		calendario.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
			 PopupCalendario popup= new PopupCalendario(fecha_venc);
			 popup.setPopupPosition(580, 100);
			 popup.show();
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
     if(parent instanceof PantallaProductos) {
     nombre.selectAll();
     dar_foco(nombre);
     }
     if(parent instanceof PantallaVistaDeCompra) {
    	 precio.selectAll();
         dar_foco(precio);
         }
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
