package aplicacion.vista.html;

import java.util.ArrayList;
import java.util.HashMap;

public class Tag {

	private String tag;
	private String contenido;
	private ArrayList<Tag> hijos;
	private HashMap<String, String> atributos;
	private boolean cierraEtiqueta;
	private boolean etiquetaCierre;
	private boolean nodoTexto = false;

	public Tag(String tag, String contenido, boolean cierraEtiqueta, boolean etiquetaCierre) {
		this.tag = tag;
		this.contenido = contenido;
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

	public void prepararHijos() {
		this.hijos = new ArrayList<Tag>();
	}

	public void addAtributo(String atributo, String valor) {
		if (this.atributos != null)
			if (valor != null) {
				this.atributos.put(atributo, valor);
			} else {
				this.atributos.put(atributo, "");
			}
	}

	public void addChild(Tag hijo) {
		if (this.hijos != null)
			this.hijos.add(hijo);
	}

	public void addContenido(String texto) {
		this.contenido += texto;
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
