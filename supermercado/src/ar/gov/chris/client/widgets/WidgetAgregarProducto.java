package ar.gov.chris.client.widgets;

import ar.gov.chris.client.datos.DatosProducto;
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
	
	private boolean es_update;
	
	private DatosProducto datos_prod;
	
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
		if(es_update)
			datos_prod= prod;
		nombre= new TextBox();
		precio= new TextBox();
		agregar= new Button("Agregar");
		cancelar= new Button("Cancelar");
		if(es_update) {
			this.setText("Actualizar Producto");
			nombre.setText(prod.getNombre());
			precio.setText(String.valueOf((prod.getPrecio())));
			agregar.setText("Actualizar");
		} else
			this.setText("Agregar Nuevo Producto");
		
//		DisplayTituloWidget cudap= new DisplayTituloWidget("CUDAP: ", box_cudap);
		
		
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
				if(parent instanceof PantallaProductos) {
					if(es_update) {
						datos_prod.setNombre(nombre.getText());
						datos_prod.setPrecio(Float.parseFloat(precio.getText()));
						((PantallaProductos)parent).actualizar_producto(datos_prod);
						
					} else
					    ((PantallaProductos)parent).agregar_producto(nombre.getText(), precio.getText());
				
				} else {
					if(parent instanceof PantallaVistaDeCompra)
						if(es_update) {
							datos_prod.setNombre(nombre.getText());
							datos_prod.setPrecio(Float.parseFloat(precio.getText()));
							((PantallaVistaDeCompra)parent).actualizar_producto(datos_prod);
							
						} else
							((PantallaVistaDeCompra)parent).agregar_producto(nombre.getText(), precio.getText());
	
				}
					
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
