package aplicacion.controlador;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import aplicacion.modelo.ejb.SesionesEJB;
import aplicacion.modelo.ejb.UsuariosEJB;
import aplicacion.modelo.pojo.Usuario;
import aplicacion.vista.PaginaLogin;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	UsuariosEJB usuariosEJB;

	@EJB
	SesionesEJB sesionesEJB;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		LogSingleton log = LogSingleton.getInstance();
		String error = request.getParameter("error");

		Usuario usuario = sesionesEJB.usuarioLogeado(session);

		if (usuario != null) {
			try {
				response.sendRedirect("Principal");
			} catch (IOException e) {
				log.getLoggerLogin().error("Se ha producido un error en GET de Login: ", e);
			}
		} else {
			response.setContentType("text/html; charset=UTF-8");
			PaginaLogin paginaLogin = new PaginaLogin(error);
			try {
				paginaLogin.print(response.getWriter());
			} catch (IOException e) {
				log.getLoggerLogin().error("Se ha producido un error en GET de Login: ", e);
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		LogSingleton log = LogSingleton.getInstance();

		String correo = request.getParameter("correo");
		String paswd = request.getParameter("paswd");

		if (session != null) {
			Usuario usuario = usuariosEJB.existeUsuario(correo, paswd);
			if (usuario == null) {
				sesionesEJB.logoutUsuario(session);
			}
			try {
				response.sendRedirect("Principal");
			} catch (IOException e) {
				log.getLoggerLogin().error("Se ha producido un error en POST Login: ", e);
			}
		} else {
			Usuario usuario = usuariosEJB.existeUsuario(correo, paswd);
			if (usuario == null) {
				try {
					response.sendRedirect("Login?error=El correo o la contraseña no son correctos");
				} catch (IOException e) {
					log.getLoggerLogin().error("Se ha producido un error en POST Login: ", e);
				}
			} else {
				if (usuario.isValidado()) {
					session = request.getSession(true);
					sesionesEJB.loginUsuario(session, usuario);

					try {
						response.sendRedirect("Principal");
					} catch (IOException e) {
						log.getLoggerLogin().error("Se ha producido un error en POST Login: ", e);
					}
				}
			}
		}
	}

}
