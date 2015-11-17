package ar.gov.chris.client.util;

public class JavaScript {

	/** Abre una nueva ventana mostrando el HTML pasado como parámetro.
	 * @param html El HTML a mostrar.
	 * @param titulo El título de la ventana.
	 * @param nombre El nombre de la ventana.
	 * @param opciones Las opciones que especifican como mostrarse. 
	 */
	public static native void print_html(String html, String titulo, String nombre,
			String opciones) /*-{
	 var ventana= open("", nombre, opciones);
	 var doc= ventana.document;
	 doc.write(html);
	 doc.title= titulo;
	 doc.close();
	}-*/;
	
	/** Abre una nueva ventana con el link pasado como parámetro.
	 * @param url La url a mostrar e la nueva ventana.
	 * @param nombre El nombre de la ventana.
	 * @param opciones Las opciones que especifican como mostrarse.
	 */
	public static native void abrir_nueva_ventana(String url, String nombre, 
			String opciones) /*-{
	 open(url, nombre, opciones);
	}-*/;

	/** Obtiene el string de parámetros con el que fue llamado el servlet.
	 * 
	 * @return Ídem.
	 */
	public static native String obtener_string_parametros() /*-{
	 return $wnd.location.search;
	}-*/;
	
	/** Redirecciona a una nueva URL.
	 * 
	 * @param url La nueva URL.
	 */
	public static native void redirect(String url)/*-{
     $wnd.location = url;
	}-*/;
	
	/** Devuelve la propiedad Javascript body.scrollLeft.
	 * 
	 * @return Ídem.
	 */
	public static native int getBodyScrollLeft() /*-{
	 return $doc.body.scrollLeft;
	}-*/;

	/** Devuelve la propiedad Javascript body.scrollTop.
	 * 
	 * @return Ídem.
	 */
	public static native int getBodyScrollTop() /*-{
	 return $doc.body.scrollTop;
	}-*/;
	
    
    /** Devuelve la URL de la ubicación actual.
     * 
     * @return Ídem.
     */
    public static native String obtener_ubicacion_actual()/*-{
	 return $wnd.location.href;
	}-*/;
    
    /** Devuelve la URL base de la aplicación. Ie, sin el
     * <code>#Pantalla</code>.
     * 
     * @return Ídem.
     */
    public static String obtener_url_base() {
     String url= obtener_ubicacion_actual();
     return url.substring(0, url.indexOf('#'));
    }
    
    /** Cierra la ventana actual.
     */
    public static native void cerrar_ventana()/*-{
	 $wnd.close();
	}-*/;

}
