package ar.gov.chris.client.datos;

import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DatosListaProdCompleta implements IsSerializable {
	
	Set<DatosProducto> lista_prod;
	float descuento_del_super;
	int porcentaje_de_descuento;
	public Set<DatosProducto> getLista_prod() {
		return lista_prod;
	}
	
	
	public void setLista_prod(Set<DatosProducto> lista_prod) {
		this.lista_prod = lista_prod;
	}
	public float getDescuento_del_super() {
		return descuento_del_super;
	}
	public void setDescuento_del_super(float descuento_del_super) {
		this.descuento_del_super = descuento_del_super;
	}
	public int getPorcentaje_de_descuento() {
		return porcentaje_de_descuento;
	}
	public void setPorcentaje_de_descuento(int porcentaje_de_descuento) {
		this.porcentaje_de_descuento = porcentaje_de_descuento;
	}

	
}
