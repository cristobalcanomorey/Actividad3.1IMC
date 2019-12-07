package aplicacion.vista.html;

import java.util.HashMap;

public class Tag {

	private String tag;
	private String contenido;
	private HashMap<String, String> atri;

	public Tag(String tag, String contenido) {
		this.tag = tag;
		this.contenido = contenido;
		this.atri = null;
	}

	public void prepararAtributos() {
		this.atri = new HashMap<String, String>();
	}

	public void addAtributo(String atributo, String valor) {
		if (valor != null) {
			this.atri.put(atributo, valor);
		} else {
			this.atri.put(atributo, "");
		}
	}

	@Override
	public String toString() {
		String s = "<" + tag;
		if (atri != null) {
			for (String i : atri.keySet()) {
				if (atri.get(i).equals("")) {
					s += " " + i;
				} else {
					s += " " + i + "=" + "'" + atri.get(i) + "'";
				}
			}
		}
		s += ">" + contenido + "</" + tag + ">";
		return s;
	}

}
