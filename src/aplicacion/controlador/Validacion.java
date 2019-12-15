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

@WebServlet("/Validacion")
public class Validacion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	UsuariosEJB usuariosEJB;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		String codigo = request.getParameter("codigo");
		if (codigo != null) {
			LogSingleton log = LogSingleton.getInstance();
			boolean caducado = usuariosEJB.validar(codigo);
			if (caducado) {
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
		}
	}

}
