/**
 * 
 */
package ar.gov.chris.client.pantalla;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author chris
 *
 */
public class PantallaUiBinder2 extends Composite {

	private static PantallaUiBinder2UiBinder uiBinder = GWT
			.create(PantallaUiBinder2UiBinder.class);

	interface PantallaUiBinder2UiBinder extends
			UiBinder<Widget, PantallaUiBinder2> {
	}

	/**
	 * Because this class has a default constructor, it can
	 * be used as a binder template. In other words, it can be used in other
	 * *.ui.xml files as follows:
	 * <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	 *   xmlns:g="urn:import:**user's package**">
	 *  <g:**UserClassName**>Hello!</g:**UserClassName>
	 * </ui:UiBinder>
	 * Note that depending on the widget that is used, it may be necessary to
	 * implement HasHTML instead of HasText.
	 */
	public PantallaUiBinder2() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	InputElement inputEmail;
	
	@UiField
	InputElement inputPassword;

	public PantallaUiBinder2(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

		
	}

//	@UiHandler("button")
//	void onClick(ClickEvent e) {
//		Window.alert("Hello!");
//	}


}
