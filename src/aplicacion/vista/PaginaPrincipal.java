package aplicacion.vista;

import java.io.PrintWriter;

import aplicacion.modelo.ejb.CalculosEJB;
import aplicacion.modelo.ejb.UsuariosEJB;
import aplicacion.modelo.pojo.Calculo;
import aplicacion.modelo.pojo.Usuario;
import aplicacion.vista.html.Cuerpo;
import aplicacion.vista.html.Header;
import aplicacion.vista.html.especificos.Formulario;
import aplicacion.vista.html.especificos.Html;
import aplicacion.vista.html.especificos.Tabla;
import aplicacion.vista.html.especificos.Tag;

public class PaginaPrincipal {

	private Html pagina;
	private Header header;
	private Cuerpo cuerpo;

	/***
	 * Crea la página principal
	 * 
	 * @param usu       Usuario logeado
	 * @param resul     Resultado del cálculo
	 * @param historial True si se tiene que mostrar el historial del usuario
	 */
	public PaginaPrincipal(Usuario usu, String resul, boolean historial) {
		this.pagina = new Html("Principal", "css/style.css", "js/script.js");
		if (usu != null) {
			this.header = new Header(usu.getNombre(), UsuariosEJB.getRutaFotoCompleta(usu));
			this.header.addLogout();
			this.header.addHistorial();
			this.header.addDarseDeBaja();
		} else {
			this.header = new Header(null, null);
			this.header.addRegistro();
			this.header.addLogin();
		}
		this.header.addAPagina(this.pagina);
		Formulario form;
		Tag resultado = null;
		Tabla tabla = null;
		if (resul != null) {
			resultado = new Tag("p", resul, true, true);
		}
		if (historial) {
			pagina.addABody(new Tag("h1", "Historial", true, true));
			form = null;
			tabla = crearTablaHistorial(usu);
			if (tabla == null) {
				resultado = new Tag("p", "No has realizado ningún calculo todavía", true, true);
			}
		} else {
			pagina.addABody(new Tag("h1", "Calcular IMC", true, true));
			form = crearFormCalc("POST", "Principal");
		}

		this.cuerpo = new Cuerpo(form, tabla, resultado);
		this.cuerpo.addAPagina(this.pagina);
	}

	private Tabla crearTablaHistorial(Usuario u) {
		Tabla tabla = null;
		if (u != null) {
			if (u.getCalculos() != null) {
				tabla = new Tabla();
				tabla.addAFila(new Tag("Estatura"), "th");
				tabla.addAFila(new Tag("Peso"), "th");
				tabla.addAFila(new Tag("Fecha"), "th");
				tabla.addAFila(new Tag("IMC"), "th");
				tabla.addFilaAEncabezado();
				tabla.resetFila();
				tabla.prepararCuerpo();
				for (Calculo c : u.getCalculos()) {
					tabla.addAFila(new Tag(c.getEstatura().toString()), "td");
					tabla.addAFila(new Tag(c.getPeso().toString()), "td");
					tabla.addAFila(new Tag(CalculosEJB.fechaAString(c.getFecha())), "td");
					tabla.addAFila(new Tag(String.format("%.2f", c.getImc())), "td");
					tabla.addFilaACuerpo();
					tabla.resetFila();
				}
			}
		}
		return tabla;
	}

	/***
	 * Crea el formulario de cálculo
	 * 
	 * @param metodo
	 * @param sitio
	 * @return
	 */
	private Formulario crearFormCalc(String metodo, String sitio) {
		Formulario form = new Formulario(metodo, sitio);
		form.addItem(new Tag("p", "Peso", true, true));
		Tag peso = new Tag("input", null, false, false);
		peso.prepararAtributos();
		peso.addAtributo("type", "number");
		peso.addAtributo("name", "peso");
		peso.addAtributo("step", "0.01");
		form.addItem(peso);
		form.addItem(new Tag("p", "Altura", true, true));
		Tag altura = new Tag("input", null, false, false);
		altura.prepararAtributos();
		altura.addAtributo("type", "number");
		altura.addAtributo("name", "altura");
		altura.addAtributo("step", "0.01");
		form.addItem(altura);
		Tag enviar = new Tag("input", null, false, false);
		enviar.prepararAtributos();
		enviar.addAtributo("type", "submit");
		enviar.addAtributo("value", "Calcular");
		form.addItem(enviar);
		return form;
	}

	public void print(PrintWriter out) {
		out.println(this.pagina.toString());
	}
}
