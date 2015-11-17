package ar.gov.chris.client.gwt;

import ar.gov.chris.client.util.JavaScript;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MensajeDialog extends DialogBox {
	
	public static String ICON_ERROR= "icon-error.gif"; 
	public static String ICON_WARNING= "icon-warning.gif";
	public static String ICON_INFO= "icon-info.gif";
	public static String ICON_QUESTION= "icon-question.gif";

	private final VerticalPanel principal= new VerticalPanel();
	private final HorizontalPanel textPanel= new HorizontalPanel();
	private final Button aceptarButton= new Button("Aceptar");
	private static final String TITULO_DEFAULT= "Mensaje";
	
	/**
	 * Muestra un diálogo con un mensaje. El usuario sólo puede hacer click en OK.
	 * 
	 * @param msg Mensaje a mostrar
	 */
	public MensajeDialog(String msg) {
	 super(true);
	 crear_dialog(TITULO_DEFAULT, msg, false, null, null);
	}
	
	/**
	 * Muestra un diálogo con un mensaje. Al cerrarse, el foco pasa a
	 * <code>proximo_foco</code>.
	 * 
	 * @param msg El mensje a mostrar.
	 * @param proximo_foco El widget que obtendrá el foco cuando el {@link MensajeDialog}
	 * se cierre.
	 */
	public MensajeDialog(String msg, Focusable proximo_foco) {
	 super(true);
	 crear_dialog(TITULO_DEFAULT, msg, false, proximo_foco, null);
	}
	
	/**
	 * Construye un mensaje dialog con el icono y el mensaje pasados como parámetros.
	 * 
	 * @param estilo_icono Puede ser <code>PopupMensaje.ICON_ERROR,
	 * PopupMensaje.ICON_WARNING, PopupMensaje.ICON_INFO,
	 * PopupMensaje.ICON_QUESTION</code>.
	 * @param msg El mensaje.
	 */
	public MensajeDialog(String estilo_icono, String msg) {
	 super(false, true);
	 crear_dialog(TITULO_DEFAULT, msg, false, null, estilo_icono);
	}
	
	/**
	 * Construye un mensaje dialog con el icono y el mensaje pasados como parámetros.
	 * 
	 * @param estilo_icono Puede ser <code>PopupMensaje.ICON_ERROR,
	 * PopupMensaje.ICON_WARNING, PopupMensaje.ICON_INFO,
	 * PopupMensaje.ICON_QUESTION</code>.
	 * @param msg El mensaje.
	 * @param es_html Si el mensaje debe interpretarse como HTML o no.
	 */
	public MensajeDialog(String estilo_icono, String msg, boolean es_html) {
	 super(false, true);
	 crear_dialog(TITULO_DEFAULT, msg, es_html, null, estilo_icono);
	}
	
	/**
	 * Construye un mensaje dialog con el icono, el mensaje y el títlo pasados como 
	 * parámetros.
	 * @param estilo_icono Puede ser <code>PopupMensaje.ICON_ERROR,
	 * PopupMensaje.ICON_WARNING, PopupMensaje.ICON_INFO,
	 * PopupMensaje.ICON_QUESTION</code>.
	 * @param msg El mensaje.
	 * @param titulo El título del dialog.
	 * @param es_html Si el mensaje debe interpretarse como HTML o no.
	 */
	public MensajeDialog(String estilo_icono, String msg, String titulo,
			boolean es_html) {
	 super(false, true);
	 crear_dialog(titulo, msg, es_html, null, estilo_icono);
	}
	
	/**
	 * Agrega un {@link Widget} luego del mensaje y el icono.
	 * 
	 * @param w El {@link Widget} a agregar.
	 */
	public void agregar(Widget w) {
	 principal.add(w);
	}

	/**
	 * Construye el Dialog con el mensaje pasado como parámetro.
	 * 
	 * @param titulo El título del dialog.
	 * @param msg El mensaje.
	 * @param es_html Si el texto del mensaje debe ser interpretado como HTML.
	 * @param proximo_foco El widget que obtendrá el foco al clickearse el botón aceptar.
	 * @param icono Nombre de la imagen a mostrar.
	 */
	private void crear_dialog(String titulo, String msg, boolean es_html,
			final Focusable proximo_foco, String icono) {
	 setAnimationEnabled(true);
	 setText(titulo); 
	 textPanel.setSpacing(8);
	 // Agrego imagen y mensaje
	 if (icono != null)
		 textPanel.add(new Image(GWT.getModuleBaseURL() + icono));
	 
	 Label label_msg;
	 if (es_html)
		 label_msg= new HTML(msg);
	 else 
		 label_msg= new Label(msg);
		 
	 textPanel.add(label_msg);
	 textPanel.setCellVerticalAlignment(label_msg, HasAlignment.ALIGN_MIDDLE);

	 principal.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
	 principal.setSpacing(8);
	 principal.add(textPanel);
	 aceptarButton.ensureDebugId("bt_aceptar_msg_dialog");
	 principal.add(aceptarButton);

	 // Pongo el foco en el botón de aceptar.
	 DeferredCommand.addCommand(new Command() {
	 	@Override
		public void execute() {
	 	 aceptarButton.setFocus(true);
	 	}
	 });
	 
	 // Cuando se presiona aceptar se debe esconder el popup.
	 aceptarButton.addClickHandler(new ClickHandler(){
	 	@Override
		public void onClick(ClickEvent event) {
		 hide();
		 // Pongo el foco en el widget que me pasaron (si me pasaron alguno).
		 if (proximo_foco != null) {
			 DeferredCommand.addCommand(new Command() {
			 	@Override
				public void execute() {
			 	 proximo_foco.setFocus(true);
			 	}
			 });
		 }
		}
	 });

	 setWidget(principal);
	}
	
	/**
	 * Muestra el popup en el centro de la pantalla.
	 */
	@Override
	public void show() {
	 super.show();
	 int left= (Window.getClientWidth() - getOffsetWidth()) / 2 +
	 		JavaScript.getBodyScrollLeft();
     int top= (Window.getClientHeight() - getOffsetHeight()) / 2 +
     		JavaScript.getBodyScrollTop();
     setPopupPosition(left, top);
    }

}
