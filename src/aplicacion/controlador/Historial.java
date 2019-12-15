package aplicacion.controlador;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import aplicacion.modelo.LogSingleton;
import aplicacion.modelo.ejb.CalculosEJB;
import aplicacion.modelo.ejb.SesionesEJB;
import aplicacion.modelo.pojo.Usuario;
import aplicacion.vista.PaginaPrincipal;

/***
 * Servlet para mostrar el historial del usuario.
 * 
 * @author tofol
 *
 */
@WebServlet("/Historial")
public class Historial extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	SesionesEJB sesionesEJB;

	@EJB
	CalculosEJB calculosEJB;

	/***
	 * Si el usuario está logeado le añade los calculos de BBDD y muestra la página
	 * de historial, si no lo redirige a la página principal.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		LogSingleton log = LogSingleton.getInstance();
		Usuario usuario = sesionesEJB.usuarioLogeado(session);
		if (usuario != null) {
			// Le añade el historial de cálculos al usuario para mostrarlos.
			usuario.setCalculos(calculosEJB.getHistorial(usuario));
		} else {
			try {
				response.sendRedirect("Principal");
			} catch (IOException e) {
				log.getLoggerHistorial().error("Se ha producido un error en GET Historial: ", e);
			}
		}
		response.setContentType("text/html; charset=UTF-8");
		PaginaPrincipal paginaPrincipal = new PaginaPrincipal(usuario, null, true);
		try {
			paginaPrincipal.print(response.getWriter());
		} catch (IOException e) {
			log.getLoggerHistorial().error("Se ha producido un error en GET Historial: ", e);
		}
	}
}
