package aplicacion.controlador;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import aplicacion.modelo.LogSingleton;

/***
 * Servlet para cerrar la sesión.
 * 
 * @author tofol
 *
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/***
	 * Si hay una sesión abierta la cierra y redirige a la página principal.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		LogSingleton log = LogSingleton.getInstance();
		HttpSession session = request.getSession(false);

		if (session != null) {
			session.invalidate();
		}

		try {
			response.sendRedirect("Principal");
		} catch (IOException e) {
			log.getLoggerLogout().error("Se ha producido un error en Get Logout: ", e);
		}
	}

}
