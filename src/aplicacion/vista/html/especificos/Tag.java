package aplicacion.vista.html.especificos;

import java.util.ArrayList;
import java.util.HashMap;

public class Tag {

	private String tag;
	private ArrayList<Tag> hijos = null;
	private HashMap<String, String> atributos = null;
	private boolean cierraEtiqueta;
	private boolean etiquetaCierre;
	private boolean nodoTexto = false;
	private String contenido;

	public Tag(String tag, String contenido, boolean cierraEtiqueta, boolean etiquetaCierre) {
		this.tag = tag;
		if (contenido != null) {
			this.prepararHijos();
			Tag tNodo = new Tag(contenido);
			this.hijos.add(tNodo);
		}
		this.atributos = null;
		this.cierraEtiqueta = cierraEtiqueta;
		this.etiquetaCierre = etiquetaCierre;
	}

	/***
	 * Constructor para los nodos de texto
	 * 
	 * @param contenido Contenido del texto
	 */
	public Tag(String contenido) {
		this.contenido = contenido;
		this.nodoTexto = true;
	}

	public void prepararAtributos() {
		this.atributos = new HashMap<String, String>();
	}

	public void addAtributo(String atributo, String valor) {
		if (this.atributos != null) {
			if (valor != null) {
				this.atributos.put(atributo, valor);
			} else {
				this.atributos.put(atributo, "");
			}
		}
	}

	public void prepararHijos() {
		this.hijos = new ArrayList<Tag>();
	}

	public void addChild(Tag hijo) {
		if (this.hijos != null) {
			this.hijos.add(hijo);
		}
	}

	public void addContenido(String texto) {
		this.contenido += texto;
	}

	/***
	 * Rodea el tag contenido en un list item
	 * 
	 * @param contenido Tag contenido
	 * @return Tag li con el contenido
	 */
	public static Tag incrustarEn(Tag contenido, String tag) {
		Tag t = new Tag(tag, null, true, true);
		t.prepararHijos();
		t.addChild(contenido);
		return t;
	}

	@Override
	public String toString() {
		if (nodoTexto) {
			return contenido;
		}
		String s = "<" + tag;
		if (atributos != null) {
			for (String i : atributos.keySet()) {
				if (atributos.get(i).equals("")) {
					s += " " + i;
				} else {
					s += " " + i + "=" + "'" + atributos.get(i) + "'";
				}
			}
		}

		if (cierraEtiqueta) {
			if (etiquetaCierre) {
				s += ">";
				if (hijos != null) {
					for (Tag tag : hijos) {
						s += tag.toString();
					}
				}
				s += "</" + tag + ">";
			} else {
				s += "/>";
			}
		} else {
			s += ">";
		}
		return s;
	}

}