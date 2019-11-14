package ar.gov.chris.client.widgets;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import ar.gov.chris.client.datos.DatosProducto;
import ar.gov.chris.client.pantalla.Pantalla;
import ar.gov.chris.client.pantalla.PantallaProductos;
import ar.gov.chris.client.pantalla.PantallaVistaDeCompra;
import ar.gov.chris.client.util.Mate;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;

public class WidgetMostrarProductos extends Composite {

	//***************************************************************
	private static final int NUMERO_DE_COLUMNA_PRECIO_TOTAL = 7; // VER DE MODIFICAR ESTAS COSAS para que todo quede bien luego de incorporar 
	//las columans de cant_gramos y etcs !!!!
	
	private static final int NUMERO_DE_COLUMNA_CANT_GRAMOS = 6;
	
	private static final int NUMERO_DE_COLUMNA_BOTON_MARCAR = 8;
	private static final int NUMERO_DE_COLUMNA_BOTON_ACTUALIZAR = 9;
	private static final int NUMERO_DE_COLUMNA_BOTON_BORRAR = 10;

	
	//***************************************************************
	private FlowPanel principal;
	private FlexTable lista_prod;
	private Label titulo_label;
	private Label fecha_compra_lablel;
	private Label fecha_compra_literal_lablel;
	private Label id_compra_lablel;
	private Label cant_prod_label;
	
	private Label subtotal_literal_label;
	private Label subtotal_compra_label;
	
	private Label subtotal_2_literal_label;
	private Label subtotal_compra_2_label;

	private Label desc_coto_literal_label;
	private Label desc_coto_label;

	private Label desc_coto_decripcion= new Label("Ingrese su descuento y presione tab");
	private TextBox desc_coto= new TextBox();
	private Label porcentaje_desc_decripcion= new Label("Porcentaje de descuento que tiene esta compra");
	private TextBox porcentaje_desc= new TextBox();
	
	private Label desc_tarj_literal_label;
	private Label desc_tarj_label;
	
	private Label total_literal_label;
	Label total_final_label;
	

	private int next_row;
	private int next_col=0;
	private Pantalla parent;

	private static int CANT_FILAS_FINAL= 5;
	
	int cant_prod= 0;


	//	private DatosProducto prod_actual;


//	private PushButton btn_borrar;
//	private PushButton btn_actualizar;
//	private PushButton btn_marcar;


	private WidgetConfirmar confirmar_borrado;
//	private WidgetAgregarProducto actualizar_prod;
	protected float desc_coto_float;
	
	private float total=0;
	float subtotal_compra=0;
	float subtotal_compra_2=0;
	private String nombre_prod_ant="";
	
	private CheckBox cb_marcar_desmarcar_todos;
	
	private int porcentaje_de_descuento;
	


	/** Crea un {@link WidgetMostrarProductos} a partir de los par�metros.
	 * 
	 * @param lista_productos Lista de l�neas a mostrar.
	 * @param titulo Titulo del widget.
	 * @param fecha_compra 
	 */
	public WidgetMostrarProductos(final LinkedList<DatosProducto> lista_productos, 
			String titulo, final int num_compra, final Pantalla parent, 
			final float descuento_coto, Date fecha_compra, final int porcentaje_descuento) {

		//		cant_prod= lista_productos.size();

		this.parent= parent;
		this.desc_coto.setText(String.valueOf(Mate.poner_dos_decimales(descuento_coto)));
		this.porcentaje_desc.setText(String.valueOf(porcentaje_descuento));
		this.desc_coto_float= descuento_coto;
		this.porcentaje_de_descuento=porcentaje_descuento;
		principal= new FlowPanel();
		lista_prod= new FlexTable();
		FlexTable resumen = new FlexTable();

		titulo_label= new Label(titulo);
		titulo_label.addStyleName("LabelDistinguido");

		resumen.setWidget(1,1,titulo_label);
		if(fecha_compra != null) {
			fecha_compra_literal_lablel= new Label(" Fecha de compra " );
			fecha_compra_literal_lablel.addStyleName("LabelDistinguido");
			fecha_compra_lablel= new Label("  " + fecha_compra.toString());
			fecha_compra_lablel.addStyleName("LabelDistinguido");

			id_compra_lablel= new Label("n° " + num_compra);
			id_compra_lablel.addStyleName("LabelDistinguido");

			resumen.setWidget(1,2, id_compra_lablel);
			resumen.setWidget(1,3, fecha_compra_literal_lablel);
			resumen.setWidget(1,4, fecha_compra_lablel);

			resumen.getRowFormatter().setStyleName(1,"ContenidoTablas");
		}
			cant_prod_label= new Label();

			cant_prod_label.setStyleName("HeaderTablas");

			subtotal_literal_label= new Label("Subtotal");
			subtotal_literal_label.addStyleName("LabelDistinguido");
			subtotal_compra_label= new Label();
			subtotal_compra_label.addStyleName("LabelDistinguido");
			
			subtotal_2_literal_label= new Label("Subtotal 2");
			subtotal_2_literal_label.addStyleName("LabelDistinguido");
			subtotal_compra_2_label= new Label();
			subtotal_compra_2_label.addStyleName("LabelDistinguido");

			desc_coto_literal_label= new Label("Desc del super");
			desc_coto_literal_label.addStyleName("LabelDistinguido");
			desc_coto_label= new Label();
			desc_coto_label.addStyleName("LabelDistinguido");

			desc_tarj_literal_label= new Label("Desc Tarj " + porcentaje_de_descuento + "%");
			desc_tarj_literal_label.addStyleName("LabelDistinguido");
			desc_tarj_label= new Label();
			desc_tarj_label.addStyleName("LabelDistinguido");

			total_literal_label= new Label("Total");
			total_literal_label.addStyleName("LabelDistinguido");
			total_final_label= new Label();
			total_final_label.addStyleName("LabelDistinguido");

			Label marcar_maviso_label= new Label("MM");
			Label id_prod_label= new Label("ID");

			cb_marcar_desmarcar_todos= new CheckBox();
			cb_marcar_desmarcar_todos.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					marcar_todos();
				}
			});

			Label nombre_prod_label= new Label("Producto");
			Label precio_label= new Label("Precio");
			Label precio_kg_label= new Label("Precio x KG");
			Label cant_label= new Label("Cantidad");
			Label cant_gramos_label= new Label("grs");
			Label total_label= new Label("Precio total");
			Label borrar_label= new Label("B");
			Label actualizar_label= new Label("A");
			Label marcar_label= new Label("Marcar");

			//		lista_prod.setWidget(0, next_col, marcar_maviso_label);

			lista_prod.setWidget(0, next_col, cb_marcar_desmarcar_todos);

			next_col++;
			lista_prod.setWidget(0, next_col, id_prod_label);
			next_col++;
			lista_prod.setWidget(0, next_col, nombre_prod_label);
			next_col++;
			lista_prod.setWidget(0, next_col, precio_label);
			next_col++;
			lista_prod.setWidget(0, next_col, precio_kg_label);
			next_col++;

			if(titulo.equalsIgnoreCase("Vista de compra")) {
				lista_prod.setWidget(0, next_col, cant_label);
				next_col++;
				lista_prod.setWidget(0, next_col, cant_gramos_label);
				next_col++;
				lista_prod.setWidget(0, next_col, total_label);
				next_col++;
			}
			lista_prod.setWidget(0, next_col, borrar_label);

			if(titulo.equalsIgnoreCase("Vista de compra")) {

				lista_prod.setWidget(0, next_col, marcar_label);
				next_col++;
			}

			lista_prod.setWidget(0, next_col, actualizar_label);
			next_col++;

			lista_prod.setWidget(0, next_col, borrar_label);

			next_row= 1; 
			int tamanio_lista= lista_productos.size();
			int i=1;
			for (final DatosProducto prod : lista_productos) {

				next_col=0;

				insertar_producto(titulo, parent, prod, false);
				//Esto es para que no me sume una fila mas al final.

				if(i < tamanio_lista)
					next_row++;
				i++;
			}

			if(titulo.equalsIgnoreCase("Vista de compra")) {

				desc_coto.addBlurHandler(new BlurHandler(){
					public void onBlur(BlurEvent event) {
						String aux= desc_coto.getText();
						if (!aux.isEmpty()) {
							try {
								desc_coto_float= Float.parseFloat(aux);
							} catch (NumberFormatException e){
								desc_coto.setText(String.valueOf(descuento_coto));
								MensajeAlerta.mensaje_error("Error: debe ingresar un importe valido");
								return;
							}
							//***
							String desc_coto_str= String.valueOf(Mate.poner_dos_decimales(desc_coto_float));
							desc_coto_label.setText(desc_coto_str);
							lista_prod.setWidget(next_row-3, 3, desc_coto_label);
							//						lista_prod.setText(next_row-2, 3, String.valueOf(Mate.poner_dos_decimales(desc_coto_float)));


							subtotal_compra_2= (subtotal_compra-desc_coto_float);
							
							String subtotal_compra_2_str= String.valueOf(Mate.poner_dos_decimales(subtotal_compra_2));
							subtotal_compra_2_label.setText(subtotal_compra_2_str);
							
							float tot_aux= (subtotal_compra-desc_coto_float);
							total= tot_aux;
							float desc_tarj=tot_aux/100*porcentaje_de_descuento;
							total= total -(desc_tarj);


							String desc_tarj_str= String.valueOf(Mate.poner_dos_decimales(desc_tarj));
							desc_tarj_label.setText(desc_tarj_str);
							lista_prod.setWidget(next_row-1, 3, desc_tarj_label);


							String total_str= String.valueOf(Mate.poner_dos_decimales(total));
							total_final_label.setText(total_str);
							lista_prod.setWidget(next_row, 3, total_final_label);

							((PantallaVistaDeCompra)parent).set_descuento_o_porcentaje(num_compra, desc_coto_float, porcentaje_de_descuento);

						}}});

				//***********************************************************************************************************************************
				porcentaje_desc.addBlurHandler(new BlurHandler(){
					public void onBlur(BlurEvent event) {
						String aux= porcentaje_desc.getText();
						if (!aux.isEmpty()) {
							try {
								porcentaje_de_descuento= Integer.parseInt(aux);
							} catch (NumberFormatException e){
								porcentaje_desc.setText(String.valueOf(porcentaje_descuento));
								MensajeAlerta.mensaje_error("Error: debe ingresar un porcentaje valido");
								return;
							}
							//***
							String desc_coto_str= String.valueOf(Mate.poner_dos_decimales(desc_coto_float));
							desc_coto_label.setText(desc_coto_str);
							lista_prod.setWidget(next_row-3, 3, desc_coto_label);
							//						lista_prod.setText(next_row-2, 3, String.valueOf(Mate.poner_dos_decimales(desc_coto_float)));

							subtotal_compra_2= (subtotal_compra-desc_coto_float);
							
							String subtotal_compra_2_str= String.valueOf(Mate.poner_dos_decimales(subtotal_compra_2));
							subtotal_compra_2_label.setText(subtotal_compra_2_str);

							float tot_aux= (subtotal_compra-desc_coto_float);
							total= tot_aux;
							float desc_tarj=tot_aux/100*porcentaje_de_descuento;
							total= total -(desc_tarj);


							String desc_tarj_str= String.valueOf(Mate.poner_dos_decimales(desc_tarj));
							desc_tarj_label.setText(desc_tarj_str);
							lista_prod.setWidget(next_row-1, 3, desc_tarj_label);

							String desc_tarj_literal_label_str= String.valueOf("Desc Tarj " + porcentaje_de_descuento + "%");

							desc_tarj_literal_label.setText(desc_tarj_literal_label_str);
							lista_prod.setWidget(next_row-1, 2, desc_tarj_literal_label);						

							String total_str= String.valueOf(Mate.poner_dos_decimales(total));
							total_final_label.setText(total_str);
							lista_prod.setWidget(next_row, 3, total_final_label);

							((PantallaVistaDeCompra)parent).set_descuento_o_porcentaje(num_compra, desc_coto_float, porcentaje_de_descuento);

						}}});

				//***********************************************************************************************************************************
				insertar_final(tamanio_lista);
			}

			lista_prod.setStyleName("PanelConBordesExpandido");
			lista_prod.getRowFormatter().setStyleName(0, "HeaderTablas");

			//		principal.add(titulo_label);

			principal.add(resumen);

			if(parent instanceof PantallaVistaDeCompra) {

				principal.add(desc_coto_decripcion);
				principal.add(desc_coto);
				principal.add(porcentaje_desc_decripcion);
				principal.add(porcentaje_desc);
			}
		principal.add(cant_prod_label);
     	principal.add(lista_prod);
		initWidget(principal);
	}



	public void insertar_final(int tam_lista) {
		
		if(parent instanceof PantallaVistaDeCompra && tam_lista > 0)
			next_row++;
				
		lista_prod.setWidget(next_row, 2, subtotal_literal_label);
		String subtotal_compra_str= String.valueOf(Mate.poner_dos_decimales(subtotal_compra));
		subtotal_compra_label.setText(subtotal_compra_str);
		lista_prod.setWidget(next_row, 3, subtotal_compra_label);
		next_row++;

		lista_prod.setWidget(next_row, 2, desc_coto_literal_label);
		String desc_coto_str= String.valueOf(Mate.poner_dos_decimales(desc_coto_float));
		desc_coto_label.setText(desc_coto_str);
		lista_prod.setWidget(next_row, 3, desc_coto_label);
		next_row++;
		
		subtotal_compra_2= subtotal_compra-desc_coto_float;
		
		lista_prod.setWidget(next_row, 2, subtotal_2_literal_label);
		String subtotal_compra_2_str= String.valueOf(Mate.poner_dos_decimales(subtotal_compra_2));
		subtotal_compra_2_label.setText(subtotal_compra_2_str);
		lista_prod.setWidget(next_row, 3, subtotal_compra_2_label);
		next_row++;
		
		float tot_aux= (subtotal_compra-desc_coto_float);
		total= tot_aux;
		float desc_tarj=tot_aux/100*porcentaje_de_descuento;
		total= total -(desc_tarj);

		lista_prod.setWidget(next_row, 2, desc_tarj_literal_label);
		String desc_tarj_str= String.valueOf(Mate.poner_dos_decimales(desc_tarj));
		desc_tarj_label.setText(desc_tarj_str);
		lista_prod.setWidget(next_row, 3, desc_tarj_label);
		next_row++;

//			int desc_coto_int= Integer.parseInt(desc_coto.getText());
		
//			float tot_aux= (subtotal_compra-desc_coto_float);
//			total= tot_aux;
//			total= total - (tot_aux/100*20);
					
		lista_prod.setWidget(next_row, 2, total_literal_label);
		String total_str= String.valueOf(Mate.poner_dos_decimales(total));
		total_final_label.setText(total_str);
		lista_prod.setWidget(next_row, 3, total_final_label);
	}


	/**
	 * 
	 * @param titulo
	 * @param parent
	 * @param prod
	 * @param resetear_coordenadas
	 */
	public void insertar_producto(String titulo, final Pantalla parent,
			final DatosProducto prod, boolean resetear_coordenadas) {
		
		CheckBox check_quitar= new CheckBox();

		
		if(resetear_coordenadas) {
			
//		next_row= 1;
			if(titulo.equalsIgnoreCase("Vista de compra"))
				next_row = next_row-CANT_FILAS_FINAL + 1;
			else
				next_row++;
		next_col= 0;
		}
		//**********************
		
		PushButton btn_borrar;
		PushButton btn_actualizar;
		PushButton btn_marcar;
		
//		btn_borrar= new PushButton("Borrar");
//		btn_actualizar= new PushButton("Actualizar");
		btn_marcar= new PushButton("Marcar");
		
		
		//*********************
		Image imagen_boton_actualizar= new Image("imagenes/boton-actualizar.jpg");
		Image imagen_boton_borrar= new Image("imagenes/boton-eliminar.jpg");
		imagen_boton_actualizar.setAltText("Actualizar");
		imagen_boton_borrar.setAltText("Borrar");

		btn_borrar= new PushButton(imagen_boton_borrar);	
		btn_actualizar= new PushButton(imagen_boton_actualizar);
		
		//**********************


		final ClickHandler handler = new ClickHandler(){
			public void onClick(ClickEvent arg0) {
				if(parent instanceof PantallaProductos) {
//					int fila_a_borrar= next_row;
//					lista_prod.removeRow(fila_a_borrar);
					((PantallaProductos)parent).borrar_producto(prod.getNombre());
					
				} else if(parent instanceof PantallaVistaDeCompra)
					((PantallaVistaDeCompra)parent).borra_producto_de_lista(prod, ((PantallaVistaDeCompra)parent).getId_compra());
				//hide();
				confirmar_borrado.hide();
			}
		};

		btn_borrar.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				//				confirmar_borrado= new WidgetConfirmar(parent, /*prod_actual*/prod, "Esta seguro que quiere borrar el producto", );
				//				confirmar_borrado.show();
				//****
				confirmar_borrado= new WidgetConfirmar(parent, "Esta seguro que quiere borrar el producto", handler);
				confirmar_borrado.show();
				//****
			}
		});

		btn_actualizar.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				WidgetAgregarProducto actualizar_prod;
				actualizar_prod= new WidgetAgregarProducto(parent, prod);
				actualizar_prod.show();
			}
		});

		
		final int row_a_marcar= next_row;
		
		ClickHandler handler_marcar= new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				boolean marcada= prod.isEsta_marcada();

				if(marcada)
					lista_prod.getRowFormatter().setStyleName(row_a_marcar, "ContenidoTablas");
				else {
					if(((PantallaVistaDeCompra)parent).isVer_marcados())
						lista_prod.getRowFormatter().setStyleName(row_a_marcar, "ComplejidadMedia");
					else
						lista_prod.getRowFormatter().setVisible(row_a_marcar, false);
				}
				prod.setEsta_marcada(!marcada);
				((PantallaVistaDeCompra)parent).actualizar_producto(prod, true);
				
				
			}
		};

		btn_marcar.addClickHandler(handler_marcar);
		
		//			prod_actual= prod;
		//			agregar_handler();
		
		
		//*************************
		
		check_quitar.setFormValue(String.valueOf(prod.getId()));
		
//		check_quitar.setFormValue(String.valueOf(next_row));

		
		lista_prod.setWidget(next_row, next_col, check_quitar);
		next_col++;

		lista_prod.setText(next_row, next_col, String.valueOf(prod.getId()));
		next_col++;
		
		lista_prod.setText(next_row, next_col, prod.getNombre());
		next_col++;
		
		float precio= prod.getPrecio();
		
		precio = Mate.poner_dos_decimales(precio);
		
		float precio_kg= prod.getPrecio_kg();
		
		precio_kg = Mate.poner_dos_decimales(precio_kg);
		
//		subtotal_compra+= precio;
		
		lista_prod.setText(next_row, next_col, String.valueOf(precio));
		next_col++;
		
		lista_prod.setText(next_row, next_col, String.valueOf(precio_kg));
		next_col++;
		
		if(titulo.equalsIgnoreCase("Vista de compra")) {
			lista_prod.setText(next_row, next_col, ((prod.getCantidad()!=0) ? String.valueOf(prod.getCantidad()) : "NA"));
			next_col++;
			
			lista_prod.setText(next_row, next_col, ((prod.getCant_en_gramos()!=0) ? String.valueOf(prod.getCant_en_gramos()) : "NA"));
			next_col++;

			float precio_total= prod.getPrecio() * prod.getCantidad();
			subtotal_compra+= precio_total;
			
//				precio_total= (float) (Math.round(precio_total*100)/100.0d);
			
			lista_prod.setText(next_row, next_col, String.valueOf(Mate.poner_dos_decimales(precio_total)));
			next_col++;
		}
		
		if(titulo.equalsIgnoreCase("Vista de compra")) {
			lista_prod.setWidget(next_row, next_col, btn_marcar);
			next_col++;
		}
		
		lista_prod.setWidget(next_row, next_col, btn_actualizar);
		next_col++;

		lista_prod.setWidget(next_row, next_col, btn_borrar);


		//		lista_lineas.setText(next_row, 4, prod.obtener_nro_serie());
		//		if (lista.size() > 1) {
		//			check_quitar.setFormValue(linea.obtener_nro_cuenta() + " " +
		//					linea.obtener_descripcion_categoria_accesorio() + " (" +linea.obtener_id() + ")");
		//			lista_lineas.setWidget(next_row, 5, check_quitar);
		//		}
		
		if(prod.isEsta_marcada()) {
			if(((PantallaVistaDeCompra)parent).isVer_marcados())
				lista_prod.getRowFormatter().setStyleName(next_row, "ComplejidadMedia");
			else
				lista_prod.getRowFormatter().setVisible(next_row, false);
		} else {
			lista_prod.getRowFormatter().setStyleName(next_row, "ContenidoTablas");
		}
//		next_row++;
		
		cant_prod++;
		
		if(parent instanceof PantallaVistaDeCompra) {
			cant_prod_label.setText("Esta compra tiene: " + cant_prod + " productos."); //= new Label("Esta compra tiene: " + cant_prod + " productos.");
		} else 
			cant_prod_label.setText("Cantidad de productos registrados: " + cant_prod);
				
	}
	
	
//	public marcar_filas() {
//		if(prod.isEsta_marcada()) {
//			if(((PantallaVistaDeCompra)parent).isVer_marcados())
//				lista_prod.getRowFormatter().setStyleName(next_row, "ComplejidadMedia");
//			else
//				lista_prod.getRowFormatter().setVisible(next_row, false);
//		} else {
//			lista_prod.getRowFormatter().setStyleName(next_row, "ContenidoTablas");
//		}
//	}
//
//	private float Mate.poner_dos_decimales(float precio_total) {
//		return (float) (Math.round(precio_total*100)/100.0d);
//	}

	public void remover_producto(String nombre, int cant_gramos) {

		//Esto es para evitar que me tire error cuando el metodo se ejecuta 2 veces... QUE NO SE PORQUE CORNO PASA...
		if(!nombre_prod_ant.equalsIgnoreCase(nombre)) {
			nombre_prod_ant= nombre;

			int filas_de_la_lista= lista_prod.getRowCount();
			if(parent instanceof PantallaVistaDeCompra)
				filas_de_la_lista= filas_de_la_lista-CANT_FILAS_FINAL;

			for(int i = 1; i < filas_de_la_lista; i++) {

				try {
					String nombre_prod= lista_prod.getText(i, 2);
					String gramos= lista_prod.getText(i, NUMERO_DE_COLUMNA_CANT_GRAMOS);
										
					int fila = 0;
					if(nombre.equalsIgnoreCase(nombre_prod) && (gramos.equalsIgnoreCase("NA")|| Integer.parseInt(gramos)== cant_gramos)) {
						fila= i; 
						
						if(parent instanceof PantallaVistaDeCompra) {
						//--------------------------------------------------------------

						String precio_prod_total= lista_prod.getText(i, NUMERO_DE_COLUMNA_PRECIO_TOTAL);


						subtotal_compra= subtotal_compra- Float.parseFloat(precio_prod_total);
						String subtotal_compra_str= String.valueOf(Mate.poner_dos_decimales(subtotal_compra));
						subtotal_compra_label.setText(subtotal_compra_str);
						
						String desc_coto_str= String.valueOf(Mate.poner_dos_decimales(desc_coto_float));
						desc_coto_label.setText(desc_coto_str);
						
						
						//**************26/09/2019*************
						
						subtotal_compra_2= (subtotal_compra-desc_coto_float);
						
						String subtotal_compra_2_str= String.valueOf(Mate.poner_dos_decimales(subtotal_compra_2));
						subtotal_compra_2_label.setText(subtotal_compra_2_str);
						
						//*****************
						
						
						float tot_aux= (subtotal_compra-desc_coto_float);
						total= tot_aux;
						float desc_tarj=tot_aux/100*porcentaje_de_descuento;
						total= total -(desc_tarj);

						String desc_tarj_str= String.valueOf(Mate.poner_dos_decimales(desc_tarj));
						desc_tarj_label.setText(desc_tarj_str);
						


									
						String total_str= String.valueOf(Mate.poner_dos_decimales(total));
						total_final_label.setText(total_str);
						}
						//--------------------------------------------------------------
						lista_prod.removeRow(fila);
						next_row--;
						cant_prod--;
						
						if(parent instanceof PantallaVistaDeCompra) {
							cant_prod_label.setText("Esta compra tiene: " + cant_prod + " productos."); //= new Label("Esta compra tiene: " + cant_prod + " productos.");
						} else 
							cant_prod_label.setText("Cantidad de productos registrados: " + cant_prod);
						break;
					}
				} catch (IndexOutOfBoundsException e) {
					MensajeAlerta.mensaje_error("Ocurrio un error al intentar borrar " +
							"el producto: " + e.getMessage());
					break;
				}
			}
		}
	}
	
	
public void actualizar_producto(DatosProducto datos, boolean es_marcar) {
		
		int filas_de_la_lista= lista_prod.getRowCount();
	
		int POS_COL_ID_PROD=1;
		
		boolean filas_ya_seteadas= false;
		
		for(int i = 1; i < filas_de_la_lista; i++) {
			
			if(parent instanceof PantallaVistaDeCompra && !filas_ya_seteadas) {
				filas_de_la_lista= filas_de_la_lista-CANT_FILAS_FINAL;
				filas_ya_seteadas= true;
			}
			String id_prod= lista_prod.getText(i, POS_COL_ID_PROD);
			
			int fila = 0;
			int col = POS_COL_ID_PROD + 1;
			
			//EXPLOTA ACA CUANDO QUIERO ACTUALIZAR UN PROD !!!!!!!!!!!!!!!!!!!! 26/09/2019: creo que ahora no explota!!!
			int id_prod_aux=(Integer.parseInt(id_prod));
			boolean es_pantalla_vista= parent instanceof PantallaVistaDeCompra;
			int cant_gramos_aux=0;
			
			if(es_pantalla_vista) { 
				String cant_gramos_str= lista_prod.getText(i, NUMERO_DE_COLUMNA_CANT_GRAMOS);
				cant_gramos_aux= !cant_gramos_str.equalsIgnoreCase("NA") ? Integer.parseInt(cant_gramos_str) : 0; //(a > b) ? a : b;
			}

			if((datos.getId()== id_prod_aux && !es_pantalla_vista ) || (datos.getId()== id_prod_aux && cant_gramos_aux==datos.getCant_en_gramos_anterior())) {
				fila= i; 
				
				lista_prod.setText(fila, col, datos.getNombre());
				col++;
				
				float precio= datos.getPrecio();
				
				precio = Mate.poner_dos_decimales(precio);
							
				lista_prod.setText(fila, col, String.valueOf(precio));
				col++;
				
				float precio_kg= datos.getPrecio_kg();
				
				precio_kg = Mate.poner_dos_decimales(precio_kg);
							
				lista_prod.setText(fila, col, String.valueOf(precio_kg));
				col++;
				
				if(es_pantalla_vista) { 

//				if(titulo.equalsIgnoreCase("Vista de compra")) {
					
					
					
					lista_prod.setText(fila, col, ((datos.getCantidad()!=0) ? String.valueOf(datos.getCantidad()) : "NA"));
					col++;
					lista_prod.setText(fila, col, ((datos.getCant_en_gramos()!=0) ? String.valueOf(datos.getCant_en_gramos()) : "NA"));
					col++;

					float precio_total_anterior= datos.getPrecio_anterior() * datos.getCantidad_anterior();
					
					float precio_total_nuevo= datos.getPrecio() * datos.getCantidad();

					
//						precio_total= (float) (Math.round(precio_total*100)/100.0d);
					
					lista_prod.setText(fila, col, String.valueOf(Mate.poner_dos_decimales(precio_total_nuevo)));
					col++;
					
					
					//***************-------------------------------
					if(!es_marcar) {

						String precio_prod_total= lista_prod.getText(i, 7);

						subtotal_compra= subtotal_compra- precio_total_anterior;

						subtotal_compra= subtotal_compra + Float.parseFloat(precio_prod_total);
						String subtotal_compra_str= String.valueOf(Mate.poner_dos_decimales(subtotal_compra));
						subtotal_compra_label.setText(subtotal_compra_str);

						String desc_coto_str= String.valueOf(Mate.poner_dos_decimales(desc_coto_float));
						desc_coto_label.setText(desc_coto_str);
						
						subtotal_compra_2= (subtotal_compra-desc_coto_float);
						
						String subtotal_compra_2_str= String.valueOf(Mate.poner_dos_decimales(subtotal_compra_2));
						subtotal_compra_2_label.setText(subtotal_compra_2_str);


						float tot_aux= (subtotal_compra-desc_coto_float);
						total= tot_aux;
						float desc_tarj=tot_aux/100*porcentaje_de_descuento;
						total= total -(desc_tarj);

						String desc_tarj_str= String.valueOf(Mate.poner_dos_decimales(desc_tarj));
						desc_tarj_label.setText(desc_tarj_str);

						String total_str= String.valueOf(Mate.poner_dos_decimales(total));
						total_final_label.setText(total_str);

					}
					//**************------------------------------------
								
				break;
				}
			}
		}
//		insertar_final(2);
	}

/** Devuelve los ids las productos seleccionadas para quitar/marcar de la compra.
 * 
 * @return Ídem.
 */
public Set<String> obtener_seleccionados(){
	HashSet<String> res= new HashSet<String>();
	for (int i= 1; i < lista_prod.getRowCount()-CANT_FILAS_FINAL; i++){
		CheckBox cb= (CheckBox) lista_prod.getWidget(i, 0);
		if (cb.getValue()){
			res.add(cb.getFormValue());
		}
	}
	return res;
}

public Set<String> marcar_todos(){
	HashSet<String> res= new HashSet<String>();
	for (int i= 1; i < lista_prod.getRowCount()-CANT_FILAS_FINAL; i++){
		CheckBox cb= (CheckBox) lista_prod.getWidget(i, 0);
		if(cb_marcar_desmarcar_todos.getValue())
			cb.setValue(true);
		else 
			cb.setValue(false);
	}
	return res;
}

public Set<String> deshabilitar_todos_los_botones(boolean botones_habilitados){
	HashSet<String> res= new HashSet<String>();
	for (int i= 1; i < lista_prod.getRowCount()-CANT_FILAS_FINAL; i++){
		PushButton btn_borrar= (PushButton) lista_prod.getWidget(i, NUMERO_DE_COLUMNA_BOTON_BORRAR);
		PushButton btn_act= (PushButton) lista_prod.getWidget(i, NUMERO_DE_COLUMNA_BOTON_ACTUALIZAR);
		PushButton btn_marcar= (PushButton) lista_prod.getWidget(i, NUMERO_DE_COLUMNA_BOTON_MARCAR);

		if(botones_habilitados) {
			btn_borrar.setEnabled(true);
			btn_act.setEnabled(true);
			btn_marcar.setEnabled(true);
		} else { 
			btn_borrar.setEnabled(false);
			btn_act.setEnabled(false);
			btn_marcar.setEnabled(false);
		}
	}
	return res;
}

}
