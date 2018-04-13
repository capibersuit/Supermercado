package ar.gov.chris.client.clases;

import java.util.Comparator;

import ar.gov.chris.client.datos.DatosProducto;

public class FechaVencComparator implements Comparator<DatosProducto>  {
	@Override
	  public int compare(DatosProducto datos_1, DatosProducto datos_2) {
	    return datos_1.getFecha_venc().compareTo(datos_2.getFecha_venc());
	  }

}
