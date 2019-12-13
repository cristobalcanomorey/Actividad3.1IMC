package aplicacion.controlador;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Registro")
public class Registro extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		// * error = parametro error
		// * if(logeado)
		// * redirect(Principal)
		// * else
		// * muestra PaginaRegistro, error
		// *
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		// * nuevo usuario(parametros)
		// * if(logeado)
		// * redirect(Principal)
		// * else
		// * if(nombre == null || password == null)
		// * redirect(Registro?error=algo)
		// * else
		// * usuariosEJB.registrar(usuario){
		// * s s https://www.baeldung.com/java-random-string + idUsuario
		// * s s buscar random en validacion
		// * s s if(no repetido){
		// * s s s insert validacion
		// * s s s enviar correo
		// * s s }
		// * }
	}

}
