package ar.gov.chris.client.clases;

import java.util.Comparator;

import ar.gov.chris.client.datos.DatosLista;

public class FechaListaComparator implements Comparator<DatosLista>  {
	@Override
	  public int compare(DatosLista datos_1, DatosLista datos_2) {
	    return datos_1.getFecha().compareTo(datos_2.getFecha());
	  }

}