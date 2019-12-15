package aplicacion.controlador;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import aplicacion.modelo.LogSingleton;
import aplicacion.modelo.ejb.SesionesEJB;
import aplicacion.modelo.ejb.UsuariosEJB;
import aplicacion.modelo.pojo.Usuario;
import aplicacion.vista.PaginaLogin;

/***
 * Servlet para iniciar sesión.
 * 
 * @author tofol
 *
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	UsuariosEJB usuariosEJB;

	@EJB
	SesionesEJB sesionesEJB;

	/***
	 * Si el usuario está logeado lo redirige a la página principal, si no le
	 * muestra la página de login
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		LogSingleton log = LogSingleton.getInstance();

		Usuario usuario = sesionesEJB.usuarioLogeado(session);

		if (usuario != null) {
			try {
				response.sendRedirect("Principal");
			} catch (IOException e) {
				log.getLoggerLogin().error("Se ha producido un error en GET de Login: ", e);
			}
		} else {
			String error = request.getParameter("error");
			String err1 = "El correo o la contraseña no son correctos";
			String err2 = "Esta cuenta aún no ha sido validada";
			if (error != null) {
				if (error.equals("1")) {
					error = err1;
				} else if (error.equals("2")) {
					error = err2;
				}
			}
			response.setContentType("text/html; charset=UTF-8");
			PaginaLogin paginaLogin = new PaginaLogin(error);
			try {
				paginaLogin.print(response.getWriter());
			} catch (IOException e) {
				log.getLoggerLogin().error("Se ha producido un error en GET de Login: ", e);
			}
		}
	}

	/***
	 * Inicia la sesión del usuario, lo redirige a Login para mostrar error o lo
	 * redirige a la página principal
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		LogSingleton log = LogSingleton.getInstance();

		String correo = request.getParameter("correo");
		String paswd = request.getParameter("paswd");

		Usuario usuario = usuariosEJB.existeUsuario(correo, paswd);
		if (usuario == null) {
			try {
				response.sendRedirect("Login?error=1");
			} catch (IOException e) {
				log.getLoggerLogin().error("Se ha producido un error en POST Login: ", e);
			}
		} else {
			if (usuario.isValidado()) {
				HttpSession session = request.getSession(true);
				sesionesEJB.loginUsuario(session, usuario);

				try {
					response.sendRedirect("Principal");
				} catch (IOException e) {
					log.getLoggerLogin().error("Se ha producido un error en POST Login: ", e);
				}
			} else {
				try {
					response.sendRedirect("Login?error=2");
				} catch (IOException e) {
					log.getLoggerLogin().error("Se ha producido un error en POST Login: ", e);
				}
			}
		}
	}

}
