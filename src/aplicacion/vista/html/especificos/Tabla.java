package aplicacion.vista.html.especificos;

import java.util.ArrayList;

public class Tabla {

	private Tag thead;
	private ArrayList<ArrayList<Tag>> encabezado = new ArrayList<ArrayList<Tag>>();
	private ArrayList<Tag> fila = new ArrayList<Tag>();
	private Tag tbody;
	private ArrayList<ArrayList<Tag>> cuerpo = null;

	public Tabla() {
		this.thead = new Tag("thead", null, true, true);
		this.tbody = new Tag("tbody", null, true, true);
	}

	public void addFilaAEncabezado() {
		this.encabezado.add(this.fila);
	}

	public void prepararCuerpo() {
		this.cuerpo = new ArrayList<ArrayList<Tag>>();
	}

	public void addFilaACuerpo() {
		this.cuerpo.add(this.fila);
	}

	public void addAFila(Tag contenido, String tipo) {
		this.fila.add(Tag.incrustarEn(contenido, tipo));
	}

	public void resetFila() {
		this.fila = new ArrayList<Tag>();
	}

	public Html addAPagina(Html pagina) {
		thead.prepararHijos();
		for (ArrayList<Tag> cab : encabezado) {
			Tag tr = new Tag("tr", null, true, true);
			tr.prepararHijos();
			for (Tag tag : cab) {
				tr.addChild(tag);
			}
			thead.addChild(tr);
		}
		tbody.prepararHijos();
		for (ArrayList<Tag> arrayList : cuerpo) {
			Tag tr = new Tag("tr", null, true, true);
			tr.prepararHijos();
			for (Tag tag : arrayList) {
				tr.addChild(tag);
			}
			tbody.addChild(tr);
		}
		Tag tabla = new Tag("table", null, true, true);
		tabla.prepararHijos();
		tabla.addChild(thead);
		tabla.addChild(tbody);
		pagina.addABody(tabla);
		return pagina;
	}
}
