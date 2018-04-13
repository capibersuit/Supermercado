package ar.gov.chris.client.widgets;

import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ar.gov.chris.client.datos.DatosProducto;
import ar.gov.chris.client.pantalla.Pantalla;
import ar.gov.chris.client.pantalla.PantallaVencimientos;
import ar.gov.chris.client.pantalla.PantallaVencimientosOrd;
import ar.gov.chris.client.pantalla.PantallaVistaDeCompra;

import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.AbstractHasData;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.RowCountChangeEvent;

public class WidgetMostrarVencimientosOrd extends Composite {

	private FlowPanel principal;
	private FlexTable lista_prod;
	private Label titulo_label;

	int next_row;
	int next_col;
	
	private PantallaVencimientosOrd parent;

	public WidgetMostrarVencimientosOrd(final LinkedList<DatosProducto> lista_productos, 
			String titulo, PantallaVencimientosOrd pantallaVencimientosOrd) {

		this.parent= pantallaVencimientosOrd;
		principal= new FlowPanel();

		
		CellTable<DatosProducto> table = new CellTable<DatosProducto>();
			
	
		
		    // Create a data provider.
		    ListDataProvider<DatosProducto> dataProvider = new ListDataProvider<DatosProducto>();

		    // Connect the table to the data provider.
		    dataProvider.addDataDisplay(table);
		    final List<DatosProducto> list = dataProvider.getList();
		    for (DatosProducto producto : lista_productos) {
		      list.add(producto);
		    }
		    
		    final ListHandler<DatosProducto> columnSortHandlerID = new ListHandler<DatosProducto>(
		            list);
		    
		    final ListHandler<DatosProducto> columnSortHandlerNombre = new ListHandler<DatosProducto>(
		            list);
		    
		    final ListHandler<DatosProducto> columnSortHandlerVenc = new ListHandler<DatosProducto>(
		            list);
		    
		    final ListHandler<DatosProducto> columnSortHandlerIdCompra = new ListHandler<DatosProducto>(
		            list);
		    
		    final ListHandler<DatosProducto> columnSortHandlerFechaCompra = new ListHandler<DatosProducto>(
		            list);
		    
		    
		  //----------------------------- columna Id  ------------------------------------
		    

			// Create name column.
		    final TextColumn<DatosProducto> columna_id = new TextColumn<DatosProducto>() {
		      @Override
		      public String getValue(DatosProducto prod) {
		        return String.valueOf(prod.getId());
		      }
		    };
		    
		    Header<String> columnHeaderID = new Header<String>(new ClickableTextCell()) {
		        @Override
		        public String getValue() {
		            return "ID";
		        }
		    };

		    columnHeaderID.setUpdater(new ValueUpdater<String>() {
		        
		        public void update(String value) {
		            //if (Window.confirm("Want to do?")){
//		        	columna_nombre.setSortable(true);
		        	columnSortHandlerID.setComparator(columna_id,
		                        new Comparator<DatosProducto>() {
		                          public int compare(DatosProducto o1, DatosProducto o2) {
		                            if (o1 == o2) {
		                              return 0;
		                            }

		                            // Compare the name columns.
		                            if (o1 != null) {
//		                            	
//		                            	String uno=String.valueOf(o1.getId());
//		                            	String dos;
////		                            	if(o2!= null)
//		                            	 dos=String.valueOf(o2.getId());
//		                            	 int res= (o2 != null) ? uno.compareTo(dos):1;
//		                            	return res;
		                            	
//		                              return (o2 != null) ? String.valueOf(o1.getId()).compareTo(String.valueOf(o2.getId())) : 1;
//			                          return (Integer) ((o2 != null) ? o1.getId() < o2.getId() : 1);
		                            	
		                            	
//			                          return (Integer) ((o2 != null) ? o1.getId().compareTo(o2.getId()) : 1);
		                            	
		                            	return o2.getId()-o1.getId();

		                            }
		                            return -1;
		                          }
		                        });
		           // } else nombre_columna.setSortable(false);
		        }
		    });
		    // Make the name column sortable.
		    columna_id.setSortable(true);
		    
		    //----------------------------- fin columna id  ------------------------------------

		  //----------------------------- columna Id_compra  ------------------------------------
		    

			// Create name column.
		    final TextColumn<DatosProducto> columna_id_compra = new TextColumn<DatosProducto>() {
		      @Override
		      public String getValue(DatosProducto prod) {
		        return String.valueOf(prod.getId_compra());
		      }
		    };
		    
		    Header<String> columnHeaderIdCompra = new Header<String>(new ClickableTextCell()) {
		        @Override
		        public String getValue() {
		            return "ID COMPRA";
		        }
		    };

		    columnHeaderIdCompra.setUpdater(new ValueUpdater<String>() {
		        
		        public void update(String value) {
		            //if (Window.confirm("Want to do?")){
//		        	columna_nombre.setSortable(true);
		        	columnSortHandlerIdCompra.setComparator(columna_id_compra,
		                        new Comparator<DatosProducto>() {
		                          public int compare(DatosProducto o1, DatosProducto o2) {
		                            if (o1 == o2) {
		                              return 0;
		                            }

		                            // Compare the name columns.
		                            if (o1 != null) {

		                            	return o2.getId_compra()-o1.getId_compra();

		                            }
		                            return -1;
		                          }
		                        });
		           // } else nombre_columna.setSortable(false);
		        }
		    });
		    // Make the name column sortable.
		    columna_id_compra.setSortable(true);
		    
		    //----------------------------- fin columna id_compra  ------------------------------------

		    
		    
		    //----------------------------- columna Producto  ------------------------------------


		    // Create Producto column.
		    final TextColumn<DatosProducto> columna_nombre = new TextColumn<DatosProducto>() {
		      @Override
		      public String getValue(DatosProducto DatosProducto) {
		        return DatosProducto.getNombre();
		      }
		    };
		    
		    
		    Header<String> columnHeaderProducto = new Header<String>(new ClickableTextCell()) {
		        @Override
		        public String getValue() {
		            return "Producto";
		        }
		    };

		    columnHeaderProducto.setUpdater(new ValueUpdater<String>() {
		        
		        public void update(String value) {
		            //if (Window.confirm("Want to do?")){
//		        	columna_nombre.setSortable(true);
		                columnSortHandlerNombre.setComparator(columna_nombre,
		                        new Comparator<DatosProducto>() {
		                          public int compare(DatosProducto o1, DatosProducto o2) {
		                            if (o1 == o2) {
		                              return 0;
		                            }

		                            // Compare the name columns.
		                            if (o1 != null) {
		                              return (o2 != null) ? o1.getNombre().compareTo(o2.getNombre()) : 1;
		                            }
		                            return -1;
		                          }
		                        });
		           // } else nombre_columna.setSortable(false);
		        }
		    });
		    // Make the name column sortable.
		    columna_nombre.setSortable(true);
		    
		    
		    //----------------------------- fin columna Producto  ------------------------------------

		  //----------------------------- columna fecha  ------------------------------------


		    // Create Producto column.
		    final TextColumn<DatosProducto> columna_fecha = new TextColumn<DatosProducto>() {
		      @Override
		      public String getValue(DatosProducto DatosProducto) {
		        return DatosProducto.getFecha_venc().toString();
		      }
		    };
		    
		    
		    Header<String> columnHeaderFecha = new Header<String>(new ClickableTextCell()) {
		        @Override
		        public String getValue() {
		            return "Vence";
		        }
		    };

		    columnHeaderFecha.setUpdater(new ValueUpdater<String>() {
		        
		        public void update(String value) {
		            //if (Window.confirm("Want to do?")){
//		        	columna_nombre.setSortable(true);
		                columnSortHandlerVenc.setComparator(columna_fecha,
		                        new Comparator<DatosProducto>() {
		                          public int compare(DatosProducto o1, DatosProducto o2) {
		                            if (o1 == o2) {
		                              return 0;
		                            }

		                            // Compare the name columns.
		                            if (o1 != null) {
		                              return (o2 != null) ? o1.getFecha_venc().compareTo(o2.getFecha_venc()) : 1;
		                            }
		                            return -1;
		                          }
		                        });
		           // } else nombre_columna.setSortable(false);
		        }
		    });
		    // Make the name column sortable.
		    columna_fecha.setSortable(true);
		    
		    
		    //----------------------------- fin columna fecha  ------------------------------------

		  //----------------------------- columna fecha compra ------------------------------------


		    // Create Producto column.
		    final TextColumn<DatosProducto> columna_fecha_compra = new TextColumn<DatosProducto>() {
		      @Override
		      public String getValue(DatosProducto DatosProducto) {
		        return DatosProducto.getFecha_compra().toString();
		      }
		    };
		    
		    
		    Header<String> columnHeaderFechaCompra = new Header<String>(new ClickableTextCell()) {
		        @Override
		        public String getValue() {
		            return "Fecha Compra";
		        }
		    };

		    columnHeaderFechaCompra.setUpdater(new ValueUpdater<String>() {
		        
		        public void update(String value) {
		            //if (Window.confirm("Want to do?")){
//		        	columna_nombre.setSortable(true);
		                columnSortHandlerNombre.setComparator(columna_fecha_compra,
		                        new Comparator<DatosProducto>() {
		                          public int compare(DatosProducto o1, DatosProducto o2) {
		                            if (o1 == o2) {
		                              return 0;
		                            }

		                            // Compare the name columns.
		                            if (o1 != null) {
		                              return (o2 != null) ? o1.getFecha_compra().compareTo(o2.getFecha_compra()) : 1;
		                            }
		                            return -1;
		                          }
		                        });
		           // } else nombre_columna.setSortable(false);
		        }
		    });
		    // Make the name column sortable.
		    columna_fecha_compra.setSortable(true);
		    
		    
		    //----------------------------- fin columna fecha compra  ------------------------------------

		    // Add the columns.
		    table.addColumn(columna_id, columnHeaderID);
		    table.addColumn(columna_nombre, columnHeaderProducto);
		    table.addColumn(columna_fecha, columnHeaderFecha);
		    table.addColumn(columna_id_compra, columnHeaderIdCompra);
		    table.addColumn(columna_fecha_compra, columnHeaderFechaCompra);






		    // Add the data to the data provider, which automatically pushes it to the
		    // widget.


		    // Add a ColumnSortEvent.ListHandler to connect sorting to the
		    // java.util.List.
		    //------------------ Code to add --------------------------------//
		    VerticalPanel vp = new VerticalPanel();




		    table.addColumnSortHandler(columnSortHandlerID);
		    table.addColumnSortHandler(columnSortHandlerNombre);
		    table.addColumnSortHandler(columnSortHandlerVenc);
		    
		    table.addColumnSortHandler(columnSortHandlerIdCompra);
		    table.addColumnSortHandler(columnSortHandlerFechaCompra);

		    

		  //------------------ Code end --------------------------------//
		    // We know that the data is sorted alphabetically by default.
		    table.getColumnSortList().push(columna_nombre);
		    
		    
		    //Aca le pongo la cantidad de filas que quiero que me muesre.
		    //Si no pongo esta linea por default me muestra 15
//		    table.setVisibleRange(0,100);
		    
		    setupOnePageList(table);
		    
		    vp.setStyleName("ContenidoTablas");

		    // Add it to the root panel.
		    vp.add(table);
		    
		    principal.add(vp);
			initWidget(principal);
		//*********************************************************************************************************************


	}

	//https://stackoverflow.com/questions/6119415/gwt-celltable-only-displaying-15-rows
	
	public static void setupOnePageList(final AbstractHasData<?> cellTable) {
	    cellTable.addRowCountChangeHandler(new RowCountChangeEvent.Handler() {
	        @Override
	        public void onRowCountChange(RowCountChangeEvent event) {
	            cellTable.setVisibleRange(new Range(0, event.getNewRowCount()));
	        }
	    });
	}
	
}
