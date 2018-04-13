package ar.gov.chris.client.clases;

import java.util.Comparator;

import ar.gov.chris.client.datos.DatosReprtePrecios;

public class NombreProdPrecioComparator implements Comparator<DatosReprtePrecios>  {
	@Override
	  public int compare(DatosReprtePrecios datos_1, DatosReprtePrecios datos_2) {
	    return datos_1.getNombre_prod().compareTo(datos_2.getNombre_prod());
	  }

}
