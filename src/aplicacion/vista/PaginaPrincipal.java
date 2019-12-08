package aplicacion.vista;

import java.io.PrintWriter;

import aplicacion.modelo.pojo.Usuario;
import aplicacion.vista.html.Cuerpo;
import aplicacion.vista.html.Header;
import aplicacion.vista.html.especificos.Formulario;
import aplicacion.vista.html.especificos.Html;
import aplicacion.vista.html.especificos.Tag;

public class PaginaPrincipal {

	private Html pagina;
	private Header header;
	private Cuerpo cuerpo;

	@SuppressWarnings("unused")
	public PaginaPrincipal(Usuario usu, String altura, String peso, String resul) {
		this.pagina = new Html("Principal", "css/style.css", "js/script.js");
		this.header = new Header(usu.getNombre(), usu.getFoto());
		if (usu != null) {
			this.header.addLogout();
			this.header.addDarseDeBaja();
		} else {
			this.header.addRegistro();
			this.header.addLogin();
		}
		this.header.addAPagina(this.pagina);
		pagina.addABody(new Tag("h1", "Calcular IMC", true, true));
		Formulario form = crearFormCalc();
		Tag resultado = new Tag("p", resul, true, true);
		this.cuerpo = new Cuerpo(form, null, resultado);
		this.cuerpo.addAPagina(this.pagina);
	}

	private Formulario crearFormCalc() {
		Formulario form = new Formulario("GET", "Principal");
		form.addItem(new Tag("p", "Peso", true, true));
		Tag peso = new Tag("input", null, false, false);
		peso.prepararAtributos();
		peso.addAtributo("type", "number");
		peso.addAtributo("name", "peso");
		form.addItem(peso);
		form.addItem(new Tag("p", "Altura", true, true));
		Tag altura = new Tag("input", null, false, false);
		altura.prepararAtributos();
		altura.addAtributo("type", "number");
		altura.addAtributo("name", "altura");
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
