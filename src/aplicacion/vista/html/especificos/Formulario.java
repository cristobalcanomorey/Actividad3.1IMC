package aplicacion.vista.html.especificos;

import java.util.ArrayList;

public class Formulario {

	private ArrayList<Tag> items = new ArrayList<Tag>();
	private Tag form;

	public Formulario(String method, String action) {
		this.form = new Tag("form", null, true, true);
		this.form.prepararAtributos();
		this.form.addAtributo("method", method);
		this.form.addAtributo("action", action);
	}

	/***
	 * Añade un input al formulario
	 * 
	 * @param item
	 */
	public void addItem(Tag item) {
		this.items.add(item);
	}

	public Tag getForm() {
		return form;
	}

	public void setForm(Tag form) {
		this.form = form;
	}

	/***
	 * Añade el formulario a la página Html
	 * 
	 * @param pagina
	 * @return Página Html con el formulario
	 */
	public Html addAPagina(Html pagina) {
		this.form.prepararHijos();
		for (Tag tag : items) {
			this.form.addChild(tag);
		}
		pagina.addABody(this.form);
		return pagina;
	}
}
