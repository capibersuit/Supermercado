package ar.gov.chris.client.gwt;

import java.util.*;

import com.google.gwt.user.client.ui.*;

/** Representa un oráculo para usar en una {@link SuggestBox} que permite
 * establecer un caracter comodín de manera de poder obtener todas las posibles
 * sugerencias haciendo un request al oráculo con dicho caracter como query.
 *
 */
public class OraculoConComodin extends SuggestOracle {

	/* Esta clase debería extender a la clase MultiWordSuggestOracle. Esto no se puede
	 * hacer dado que dicha clase es final. 
	 * Es necesario overridear los métodos de MultiWordSuggestOracle "a mano" para 
	 * simular la herencia. */
	private MultiWordSuggestOracle oraculo_multiword;
	private String comodin= "*";
	private ArrayList<MultiWordSuggestOracle.MultiWordSuggestion> sugerencias;
	
	/** Constructor por defecto. El caracter comodín por defecto es '*'.
	 */
	public OraculoConComodin() {
	 this.oraculo_multiword= new MultiWordSuggestOracle();
	 this.sugerencias= new ArrayList<MultiWordSuggestOracle.MultiWordSuggestion>();
	}
	
	/** Construye un oráculo tomando el caracter comodín.
	 * 
	 * @param comodin El comodín a utilizar.
	 */
	public OraculoConComodin(String comodin) {
	 this.oraculo_multiword= new MultiWordSuggestOracle();
	 this.sugerencias= new ArrayList<MultiWordSuggestOracle.MultiWordSuggestion>();
	 this.comodin= comodin;
	}
	
	@Override
	public void requestSuggestions(Request request, Callback callback) {
	 if (request.getQuery().equals(comodin)) {
		 /* Si el request es el caracter comodín, entonces devuelvo como response 
		  * todas las sugerencias. */
		 SuggestOracle.Response response= new SuggestOracle.Response(sugerencias);
		 callback.onSuggestionsReady(request, response);
	 } else {
		 // Sino llamo al del MultiWordSuggestOracle.
		 oraculo_multiword.requestSuggestions(request, callback); 
	 }
	}

	/** Adds a suggestion to the oracle. Each suggestion must be plain text.
	 * @param suggestion the suggestion
	 */
	public void	add(String suggestion) {
	 oraculo_multiword.add(suggestion);
	 sugerencias.add(new MultiWordSuggestOracle.MultiWordSuggestion(suggestion,
			 suggestion));
	}
	
	/** Adds all suggestions specified. Each suggestion must be plain text.
	 * @param collection the collection
	 */
	public void	addAll(Collection<String> collection) {
	 for (String s : collection) {
		 add(s);
	 }
	}

	/** Removes all of the suggestions from the oracle.
	 */
	public void clear() {
	 oraculo_multiword.clear();
	}

	/** Should <code>SuggestOracle.Suggestion</code> display strings be treated as HTML?
	 * If true, this all suggestions' display strings will be interpreted as HTML,
	 * otherwise as text.
	 *  
	 * @return Ídem.
	 */
	public boolean isDisplayStringHTML() {
	 return oraculo_multiword.isDisplayStringHTML();
	}
}