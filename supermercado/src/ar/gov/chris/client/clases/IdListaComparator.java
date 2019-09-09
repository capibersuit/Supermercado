package ar.gov.chris.client.clases;

import java.util.Comparator;

import ar.gov.chris.client.datos.DatosLista;

public class IdListaComparator implements Comparator<DatosLista>  {
	@Override
	  public int compare(DatosLista datos_1, DatosLista datos_2) {
	    return new Integer(datos_2.getId()).compareTo(new Integer(datos_1.getId()));
	  }

}
