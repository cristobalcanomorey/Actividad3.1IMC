package aplicacion.vista;

import java.io.PrintWriter;
import java.sql.ResultSet;

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

	public PaginaPrincipal(Usuario usu, ResultSet historial, String resul) {
		this.pagina = new Html("Principal", "css/style.css", "js/script.js");
		if (usu != null) {
			this.header = new Header(usu.getNombre(), usu.getFoto());
			this.header.addLogout();
			this.header.addHistorial();
			this.header.addDarseDeBaja();
		} else {
			this.header = new Header(null, null);
			this.header.addRegistro();
			this.header.addLogin();
		}
		this.header.addAPagina(this.pagina);
		pagina.addABody(new Tag("h1", "Calcular IMC", true, true));
		Formulario form = crearFormCalc();
		Tag resultado = null;
		if (resul != null) {
			resultado = new Tag("p", resul, true, true);
		}
		Tabla tabla = null;
		if (historial != null) {

		}
		this.cuerpo = new Cuerpo(form, tabla, resultado);
		this.cuerpo.addAPagina(this.pagina);
	}

	private Formulario crearFormCalc() {
		Formulario form = new Formulario("POST", "Principal");
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
