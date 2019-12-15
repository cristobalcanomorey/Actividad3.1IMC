package aplicacion.controlador;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import aplicacion.modelo.LogSingleton;
import aplicacion.modelo.ejb.UsuariosEJB;

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
			usuariosEJB.validar(codigo);
			try {
				response.sendRedirect("Principal");
			} catch (IOException e) {
				log.getLoggerRegistro().error("Se ha producido un error en Get Validacion: ", e);
			}
		}
	}

}
