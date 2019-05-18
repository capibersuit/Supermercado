package ar.gov.chris.client.widgets;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.gov.chris.client.clases.BuscadorDatosEstaticos;
import ar.gov.chris.client.clases.Sucursal;
import ar.gov.chris.client.clases.Super;
import ar.gov.chris.client.datos.DatosLista;
import ar.gov.chris.client.gwt.PopupCalendario;
import ar.gov.chris.client.pantalla.PantallaListaDeCompras;
import ar.gov.chris.client.util.JavaScript;
import ar.gov.chris.client.util.Util;

import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
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
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

public class WidgetAgregarLista extends DialogBox {

	private FlowPanel panel;
	private Button agregar;
	private Button cancelar;
	
	private Label lblcomentario;
	private Label lblfecha;
	private Label lblpagado;

	private TextBox comentario;
	private TextBox fecha;
	private TextBox pagado= new TextBox();

	private Button cal;
	private PantallaListaDeCompras parent;
	
	private boolean es_update;
	private DatosLista datos_lista;
	
	private ListBox sucursal;
	private ListBox supermercado;


	
	/** Constructor para generar un popup con un campo de texto que permite agregar 
	 * un CUDAP.
	 * 
	 * @param pantallaListaDeCompras La pantalla parent.
	 * @param id_widget Id para el widget que se agrega.
	 */
	public WidgetAgregarLista(PantallaListaDeCompras pantallaListaDeCompras, DatosLista datos_lista) {
		super(true);
		this.parent= pantallaListaDeCompras;
		this.es_update= datos_lista != null;
		if(es_update)
			this.datos_lista= datos_lista;
//		this.setText("Agregar Nueva Lista");
		
		lblcomentario= new Label("Comentario/Descripcion");
		lblfecha= new Label("Fecha");
		comentario= new TextBox();
		fecha= new TextBox();
		
//		DisplayTituloWidget cudap= new DisplayTituloWidget("CUDAP: ", box_cudap);
		
		agregar= new Button("Agregar");
		cancelar= new Button("Cancelar");
		cal= new Button("Consultar fecha");
		
		
		//***************
		supermercado= new ListBox();
				
		List<Super>  supermercados;
		supermercados= BuscadorDatosEstaticos.supermercados;
		
			for (Super s : supermercados) 
				supermercado.addItem(s.obtener_descripcion(), String.valueOf(s.obtener_id()));
			
		// Pongo en la lista todos los posibles ámbitos.
		sucursal= new ListBox();
		for (Sucursal s : filtrar_sucursales_por_super(Util.mapear_supermercado_por_nombre("COTO").obtener_id())) 
			sucursal.addItem(s.obtener_descripcion(), String.valueOf(s.obtener_id()));
		
		//****************
		
		if(es_update) {
			this.setText("Actualizar lista");
			comentario.setText(datos_lista.getComentario());
//			precio.setText(String.valueOf((prod.getPrecio())));
			fecha.setText(datos_lista.getFecha().toString());
			fecha.setEnabled(false);
			agregar.setText("Actualizar");
			lblpagado= new Label("Importe pagado");
//			pagado= new TextBox();
		} else
			this.setText("Agregar Nueva Lista");
		
		agregar_listeners();
		HorizontalPanel botones= new HorizontalPanel();
		
		botones.add(agregar);
		if(es_update)
			botones.add(cal);

		botones.add(cancelar);
		
		panel= new FlowPanel();
		panel.add(lblcomentario);
		panel.add(comentario);
		if(es_update) {
			panel.add(lblfecha);
			panel.add(fecha);
			panel.add(lblpagado);
			panel.add(pagado);
		}
		
		
		panel.add(supermercado);
		panel.add(sucursal);
		
		panel.add(botones);
		this.add(panel);
	}

	/** Agrega los listeners a los botones.
	 *
	 */
	private void agregar_listeners() {
		agregar.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent arg0) {
				
				if(es_update) {
					
					//Esto de la fecha lo hago así por ahora,
					//despues veo si lo puedo hacer mejor
					String f= fecha.getText();
					String[] datos= f.split("-", 3);
					String anio=datos[0];
					String mes=datos[1];
					String dia=datos[2];
					
					// no se bien porque pero tengo que  restar 1900 
					// para que me quede bien el anio.
					int anionum= Integer.parseInt(anio)-1900;
					int mesnum= Integer.parseInt(mes)-1;
					int dianum= Integer.parseInt(dia);
					Date fecha_act= new Date(anionum, mesnum, dianum);
					datos_lista.setComentario(comentario.getText());
					datos_lista.setFecha(fecha_act);
					if(!pagado.getText().isEmpty()) {
						
						try {
						float importe_pagado= Float.parseFloat(pagado.getText());
						datos_lista.setPagado(importe_pagado);
						parent.actualizar_producto(datos_lista);
						} catch (NumberFormatException e) {
							MensajeAlerta.mensaje_error("Error: " + e.getMessage() + ". Ingrese un importe valido.");
//							throw new GWT_ExcepcionFormatoInvalido(e);
						}
						
					} else
						parent.actualizar_producto(datos_lista);
					
					
				} else
				  parent.agregar_lista(comentario.getText(),Integer.parseInt(sucursal.getValue(sucursal.getSelectedIndex())));
			hide();
			}
		});
		cancelar.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent arg0) {
				hide();
			}
		});
		
		KeyPressHandler Handler= new KeyPressHandler() {
			 @Override
			 public void onKeyPress(KeyPressEvent event) {
			  if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode())
				  agregar.click();
			 }
		 };
		
		comentario.addKeyPressHandler(Handler);
		
//		fecha.addKeyPressHandler(Handler);
		
		pagado.addKeyPressHandler(Handler);
		
		cal.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
			 PopupCalendario popup= new PopupCalendario(fecha);
			 popup.setPopupPosition(580, 100);
			 popup.show();
			} 
		 });
		
		supermercado.addChangeHandler(new ChangeHandler(){
			

			@Override
			public void onChange(ChangeEvent event) {
				cargar_sucursales_asociadas(Integer.parseInt(supermercado.getValue(supermercado.getSelectedIndex())));
				
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
     comentario.selectAll();
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
	
	/** Devuelve el listado de las sucursales dentro del super indicado.
	 * 
	 * @param id_super id del super del cual se quieren obtener las sucursales.
	 * @return Ídem.
	 */
	private List<Sucursal> filtrar_sucursales_por_super(int id_super) {
		List<Sucursal> res= new ArrayList<Sucursal>();
		List<Sucursal>  sucursales;
		sucursales= BuscadorDatosEstaticos.sucursales;

		for (Sucursal sucursal: sucursales){
			if (sucursal.id_super() == id_super)
				res.add(sucursal);
		}
		return res;
	}
	
	/** Carga las subcategorías de una categoría indicada.
	 * 
	 * @param index El id de la categoría seleccionada.
	 */
	private void cargar_sucursales_asociadas(int index) {
		sucursal.clear();

		List<Sucursal> suc= filtrar_sucursales_por_super(index);
		for (Sucursal s: suc) {

//			ambito.addItem(a.obtener_descripcion(), 
//					Integer.toString(a.obtener_id()));
			
			sucursal.addItem(s.obtener_descripcion(), String.valueOf(s.obtener_id()));

		}
	}
	
	
}
