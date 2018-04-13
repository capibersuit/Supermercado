package ar.gov.chris.client.pantalla;

import java.util.LinkedList;
import java.util.Set;

import ar.gov.chris.client.datos.DatosLista;
import ar.gov.chris.client.datos.DatosProducto;
import ar.gov.chris.client.widgets.MensajeAlerta;
import ar.gov.chris.client.widgets.WidgetAgregarProducto;
import ar.gov.chris.client.widgets.WidgetMostrarProductos;
import ar.gov.chris.client.widgets.WidgetMostrarVencimientos;
import ar.gov.chris.client.widgets.WidgetMostrarVencimientosOrd;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;

public class PantallaVencimientos extends PantallaInicio {
	
private LinkedList<DatosProducto> datos_prod;
private WidgetMostrarVencimientos vencimientos;
private CheckBox solo_existentes;
//boolean primera_vez= true; //La verdad no me acuerdo para que habia puesto esta variable... la comento porque no la uso...!!

//TODO:
boolean tildado= true; //NO SE POR QUE SI LO DEJO EN TRUE PARA QUE DE ENTRADA ME MUESTRE SOLO LOS EXISTENTES
// CUANDO LLAMA A 		obtener_datos_vencimientos(tildado), tildado esta en false....

//boolean tildado= false;


	
	public PantallaVencimientos() {
		super();
	}

	@Override
	protected void pantalla_principal() {
		panel.clear();

		super.pantalla_principal();
		obtener_datos_vencimientos(true);
	}
	
	private void obtener_datos_vencimientos(boolean solo_existentes) {
		proxy_prod.buscar_vencimientos(solo_existentes, new AsyncCallback<Set<DatosProducto>>(){
			public void onFailure(Throwable caught) {
				MensajeAlerta.mensaje_error("Ocurrió un error al intentar buscar " +
						"los vencimientos: " + caught.getMessage());
			}
			public void onSuccess(Set<DatosProducto> result) {
//				datos_prod= ordenar_productos(result);
				datos_prod= ordenar_vemcimientos(result);
				armar_pantalla();			
			}
			
		});
				
	}
	
	
	protected void armar_pantalla() {
//		btn_productos= new Button("Nuevo Producto");
//		btn_ir_a_listas= new Button("Ir a listas");
//		panel.add(btn_ir_a_listas);

//		panel.add(btn_productos);
//		menu.add(btn_productos);
		
//		agregar_prod= new WidgetAgregarProducto(this, null);	
		
		solo_existentes= new CheckBox("Solo existentes");
		solo_existentes.setValue(tildado);
		vencimientos= new WidgetMostrarVencimientos(datos_prod, "Vencimientos", this);
		panel.add(solo_existentes);
		panel.add(vencimientos);
		agregar_handlers();

	}

	
	private void agregar_handlers() {
		solo_existentes.addClickHandler(new ClickHandler() {
		      @Override
		      public void onClick(ClickEvent event) {
		        boolean checked = ((CheckBox) event.getSource()).getValue();
//		        Window.alert("It is " + (checked ? "" : "not ") + "checked");
		        panel.remove(vencimientos);
		        panel.remove(solo_existentes);
//		        primera_vez= false;
		        tildado= checked;
		        obtener_datos_vencimientos(tildado);
		      }

			
		    });
		
	}

	//TODO: esto esta copiado de otra pantalla... ver si se puede hacer mejor!??
	public void actualizar_producto(final DatosProducto datos_prod) {

		proxy_prod.actualizar_producto_a_lista(datos_prod, String.valueOf(datos_prod.getId_compra()), true, new AsyncCallback<Void>(){
			public void onFailure(Throwable caught) {
				MensajeAlerta.mensaje_error("Ocurrio un error al intentar cambiar " +
						"la existencia del producto: " + caught.getMessage());
			}
			public void onSuccess(Void result) {
				
//				prod.actualizar_producto(datos_prod);

				Window.Location.reload();
			}
			
		});			
	}
	
}
