package ar.gov.chris.client.gwt;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;

public class PopupCalendario extends PopupPanel {

	/**
	 * Constructor para generar un popup con un calendario que permite elegir fechas.
	 * 
	 * @param toFill Campo a llenar.
	 */
	public PopupCalendario(final TextBox toFill) {
	 super(true);
	 final Calendario calendar= new Calendario();
	 crear_popup(toFill, calendar);
	}

	/**
	 * Constructor para generar un popup con un calendario que permite elegir fechas,
	 * inicializándolo en una fecha en particular.
	 * 
	 * @param toFill Campo a llenar.
	 * @param ano Año de inicializacion.
	 * @param mes Mes de inicializacion.
	 * @param dia Dia de inicializacion.
	 */
	public PopupCalendario(final TextBox toFill, int ano, int mes, int dia) {
	 super(true);
	 final Calendario calendar= new Calendario(ano, mes, dia);
	 crear_popup(toFill, calendar);
	}

	/**
	 * Crea el popup con el calendario.
	 * 
	 * @param toFill Campo a llenar.
	 * @param calendar El calendario a mostrar.
	 */
	private void crear_popup(final TextBox toFill, final Calendario calendar) {
	 calendar.addValueChangeHandler(new ValueChangeHandler<String>() {
	 	@Override
		public void onValueChange(ValueChangeEvent<String> event) {
	 	 toFill.setText(calendar.getStringDate());
	 	 hide();
		}
	 });
	 add(calendar);
	}

	/**
	 * Constructor que genera un {@link PopupCalendario} que agrega una hora específica
	 * (es decir, la hora no la decide el usuario).
	 * 
	 * @param toFill Campo a llenar.
	 * @param hora Hora a agregar.
	 */
	public PopupCalendario(final TextBox toFill, final String hora) {
	 super(true);
	 final Calendario calendar= new Calendario();
	 crear_popup(toFill, calendar);
	 
	 this.addCloseHandler(new CloseHandler<PopupPanel>() {
	 	@Override
		public void onClose(CloseEvent<PopupPanel> event) {
	 	 String contenido= toFill.getText();
	 	 toFill.setText(contenido + " " + hora);
	 	}
	 });
	}
}