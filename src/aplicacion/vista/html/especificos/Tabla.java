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

	/***
	 * Añade la fila buffer al encabezado de la tabla
	 */
	public void addFilaAEncabezado() {
		this.encabezado.add(this.fila);
	}

	/***
	 * Inicializa el cuerpo
	 */
	public void prepararCuerpo() {
		this.cuerpo = new ArrayList<ArrayList<Tag>>();
	}

	/***
	 * Añade la fila buffer al cuerpo de la tabla
	 */
	public void addFilaACuerpo() {
		this.cuerpo.add(this.fila);
	}

	/***
	 * Añade un valor a la fila buffer
	 * 
	 * @param contenido Nuevo valor o columna de la tabla
	 * @param tipo      head o data
	 */
	public void addAFila(Tag contenido, String tipo) {
		this.fila.add(Tag.incrustarEn(contenido, tipo));
	}

	/***
	 * Elimina los datos de la fila buffer
	 */
	public void resetFila() {
		this.fila = new ArrayList<Tag>();
	}

	/***
	 * Añade los datos del encabezado al tag thead y los datos del cuerpo al tbody.
	 * Finalmente devuelve un Tag table con el thead y el tbody
	 * 
	 * @return Tag table con los datos
	 */
	private Tag prepararTabla() {
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
		return tabla;
	}

	/***
	 * Construye el Tag table y lo devuelve como string
	 */
	@Override
	public String toString() {
		return prepararTabla().toString();
	}

	/***
	 * Añade la tabla a la página
	 * 
	 * @param pagina Página a mostrar
	 * @return Html con la tabla añadida
	 */
	public Html addAPagina(Html pagina) {
		pagina.addABody(prepararTabla());
		return pagina;
	}
}
