package ar.gov.chris.client.pantalla;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class PantallaUiBinder extends UIObject implements IsWidget {

	private static PantallaUiBinderUiBinder uiBinder = GWT
			.create(PantallaUiBinderUiBinder.class);

	interface PantallaUiBinderUiBinder extends
			UiBinder<Element, PantallaUiBinder> {
	}

	@UiField
	SpanElement nameSpan;

	public PantallaUiBinder(String firstName) {
		setElement(uiBinder.createAndBindUi(this));
		nameSpan.setInnerText(firstName);
	}

	@Override
	public Widget asWidget() {
		// TODO Auto-generated method stub
		return null;
	}

}
