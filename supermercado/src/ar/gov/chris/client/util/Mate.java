package ar.gov.chris.client.util;

public class Mate {
	
	public static float poner_dos_decimales(float precio_total) {
		return (float) (Math.round(precio_total*100)/100.0d);
	}
	
	public static String poner_dos_decimales(String precio_total) {

		float f = Float.parseFloat(precio_total); 
		float precio_redondeado= (float) (Math.round(f*100)/100.0d);
		return String.valueOf(precio_redondeado);
	}

}
