package aplicacion.controlador;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import aplicacion.modelo.LogSingleton;
import aplicacion.modelo.ejb.UsuariosEJB;
import aplicacion.vista.PaginaValidacion;

/***
 * Servlet para validar a un usuario
 * 
 * @author tofol
 *
 */
@WebServlet("/Validacion")
public class Validacion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	UsuariosEJB usuariosEJB;

	/***
	 * Si no encuentra el código en BBDD muestra la página de error, si no, valida
	 * el usuario y lo redirige a la página principal.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		LogSingleton log = LogSingleton.getInstance();
		String codigo = request.getParameter("codigo");
		if (codigo != null) {
			boolean caducado = usuariosEJB.validar(codigo);
			if (caducado) {
				response.setContentType("text/html; charset=UTF-8");
				PaginaValidacion paginaValidacion = new PaginaValidacion();
				try {
					paginaValidacion.print(response.getWriter());
				} catch (IOException e) {
					log.getLoggerValidacion().error("Se ha producido un error en Get Validacion: ", e);
				}
			} else {
				try {
					response.sendRedirect("Principal");
				} catch (IOException e) {
					log.getLoggerValidacion().error("Se ha producido un error en Get Validacion: ", e);
				}
			}
		} else {
			try {
				response.sendRedirect("Principal");
			} catch (IOException e) {
				log.getLoggerValidacion().error("Se ha producido un error en Get Validacion: ", e);
			}
		}
	}

}
