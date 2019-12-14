package aplicacion.vista;

import java.io.PrintWriter;

import aplicacion.vista.html.Cuerpo;
import aplicacion.vista.html.Header;
import aplicacion.vista.html.especificos.Formulario;
import aplicacion.vista.html.especificos.Html;
import aplicacion.vista.html.especificos.Tag;

public class PaginaRegistro {
	private Html pagina;
	private Header header;
	private Cuerpo cuerpo;

	/***
	 * Crea la página de registro
	 * 
	 * @param error   Error del usuario
	 * @param enviado True si se ha enviado un correo al usuario, False si no
	 */
	public PaginaRegistro(String error, boolean enviado) {
		this.pagina = new Html("Registro", "css/style.css", "js/script.js");
		this.header = new Header(null, null);
		this.header.addAPagina(this.pagina);
		Formulario form = null;
		Tag resultado = null;
		if (!enviado) {
			pagina.addABody(new Tag("h1", "Registrate", true, true));
			form = crearFormRegistro();
		} else {
			resultado = new Tag("h2", "Te hemos enviado un correo para validar tu cuenta", true, true);
			Tag volver = new Tag("a", "Volver a la página principal", true, true);
			volver.prepararAtributos();
			volver.addAtributo("href", "Principal");
			pagina.addABody(volver);
		}
		this.cuerpo = new Cuerpo(form, null, resultado);
		this.cuerpo.addAPagina(this.pagina);
		if (error != null) {
			this.pagina.addABody(new Tag("p", error, true, true));
		}
	}

	/***
	 * Crea el formulario de Registro
	 * 
	 * @return Formulario
	 */
	private Formulario crearFormRegistro() {
		Formulario form = new Formulario("POST", "Registro");

		// Añade el enctype="multipart/form-data" al formulario
		Tag f = form.getForm();
		f.addAtributo("enctype", "multipart/form-data");
		form.setForm(f);

		// Añade el input para nombre de usuario
		form.addItem(new Tag("p", "Nombre de usuario", true, true));
		Tag nombre = new Tag("input", null, false, false);
		nombre.prepararAtributos();
		nombre.addAtributo("type", "string");
		nombre.addAtributo("name", "nombre");
		nombre.addAtributo("required", null);
		form.addItem(nombre);

		// Añade el input para correo
		form.addItem(new Tag("p", "Correo", true, true));
		Tag correo = new Tag("input", null, false, false);
		correo.prepararAtributos();
		correo.addAtributo("type", "email");
		correo.addAtributo("name", "correo");
		correo.addAtributo("required", null);
		form.addItem(correo);

		// Añade el input para contraseña
		form.addItem(new Tag("p", "Contraseña", true, true));
		Tag password = new Tag("input", null, false, false);
		password.prepararAtributos();
		password.addAtributo("type", "password");
		password.addAtributo("name", "paswd");
		password.addAtributo("required", null);
		form.addItem(password);

		// Añade el input para la foto de perfil
		form.addItem(new Tag("p", "Foto de perfil", true, true));
		Tag foto = new Tag("input", null, false, false);
		foto.prepararAtributos();
		foto.addAtributo("type", "file");
		foto.addAtributo("name", "avatar");
		foto.addAtributo("accept", "image/png,image/jpeg,image/jpg");
		form.addItem(foto);

		// Añade el botón de enviar
		Tag enviar = new Tag("input", null, false, false);
		enviar.prepararAtributos();
		enviar.addAtributo("type", "submit");
		enviar.addAtributo("value", "Registrarse");
		form.addItem(enviar);
		return form;
	}

	public void print(PrintWriter out) {
		out.println(this.pagina.toString());
	}
}
