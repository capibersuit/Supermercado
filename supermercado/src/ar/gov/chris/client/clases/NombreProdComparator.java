package ar.gov.chris.client.clases;

import java.util.Comparator;

import ar.gov.chris.client.datos.DatosProducto;

public class NombreProdComparator implements Comparator<DatosProducto>  {
	@Override
	  public int compare(DatosProducto datos_1, DatosProducto datos_2) {
	    return datos_1.getNombre().compareTo(datos_2.getNombre());
	  }

}