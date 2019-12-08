package aplicacion.vista.html.especificos;

import java.util.ArrayList;

public class Html {

	private Tag doctype;
	private ArrayList<Tag> contenidoHead = new ArrayList<Tag>();
	private ArrayList<Tag> contenidoBody = new ArrayList<Tag>();

	/***
	 * Constructor html, añade tags en contenidoHead
	 * 
	 * @param titulo     Título de la página
	 * @param rutaCss    Ruta al archivo css
	 * @param rutaScript Ruta al archivo javascript
	 */
	public Html(String titulo, String rutaCss, String rutaScript) {
		Tag doctype = new Tag("!DOCTYPE", null, false, false);
		doctype.prepararAtributos();
		doctype.addAtributo("html", null);
		this.doctype = doctype;
		Tag title = new Tag("title", titulo, true, true);
		Tag meta1 = new Tag("meta", null, false, false);
		meta1.prepararAtributos();
		meta1.addAtributo("charset", "UTF-8");
		Tag meta2 = new Tag("meta", null, false, false);
		meta2.prepararAtributos();
		meta2.addAtributo("content", "width=device-width, initial-scale=1.0");
		Tag linkCss = null;
		if (rutaCss != null) {
			linkCss = new Tag("link", null, false, false);
			linkCss.prepararAtributos();
			linkCss.addAtributo("href", rutaCss);
			linkCss.addAtributo("rel", "stylesheet");
			linkCss.addAtributo("type", "text/css");
			linkCss.addAtributo("title", "Color");
		}
		Tag script = null;
		if (rutaScript != null) {
			script = new Tag("script", null, true, true);
			script.prepararAtributos();
			script.addAtributo("src", rutaScript);
		}

		addAHead(title);
		addAHead(meta1);
		addAHead(meta2);
		addAHead(linkCss);
		addAHead(script);
	}

	public void addAHead(Tag contenido) {
		if (contenido != null) {
			this.contenidoHead.add(contenido);
		}
	}

	public void addABody(Tag contenido) {
		if (contenido != null) {
			this.contenidoBody.add(contenido);
		}
	}

	/***
	 * Construye el Tag html añadiendo sus hijos y los devuelve como string
	 */
	@Override
	public String toString() {
		String s = this.doctype.toString();
		Tag html = new Tag("html", null, true, true);
		Tag head = new Tag("head", null, true, true);
		html.prepararHijos();
		head.prepararHijos();
		for (Tag tag : contenidoHead) {
			head.addChild(tag);
		}
		Tag body = new Tag("body", null, true, true);
		body.prepararHijos();
		for (Tag tag : contenidoBody) {
			body.addChild(tag);
		}
		html.addChild(head);
		html.addChild(body);
		s += html.toString();
		return s;
	}

}
