package aplicacion.controlador;

import java.io.File;
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
import aplicacion.vista.PaginaBaja;

/***
 * Servlet para dar de baja a los usuarios
 * 
 * @author tofol
 *
 */
@WebServlet("/Baja")
public class Baja extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	UsuariosEJB usuariosEJB;

	@EJB
	SesionesEJB sesionesEJB;

	/***
	 * Si el usuario está logeado le responde con la página de advertencia, si no lo
	 * redirige a la página principal.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		LogSingleton log = LogSingleton.getInstance();

		Usuario usuario = sesionesEJB.usuarioLogeado(session);
		if (usuario != null) {
			response.setContentType("text/html; charset=UTF-8");
			PaginaBaja paginaBaja = new PaginaBaja();
			try {
				paginaBaja.print(response.getWriter());
			} catch (IOException e) {
				log.getLoggerBaja().error("Se ha producido un error en Get Baja: ", e);
			}
		} else {
			try {
				response.sendRedirect("Principal");
			} catch (IOException e) {
				log.getLoggerBaja().error("Se ha producido un error en Get Baja: ", e);
			}
		}
	}

	/***
	 * Si el usuario está logeado cierra la sesión y da de baja al usuario, si no lo
	 * redirige a la página principal.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		LogSingleton log = LogSingleton.getInstance();

		Usuario usuario = sesionesEJB.usuarioLogeado(session);
		if (usuario != null) {
			// Si tenía fóto de perfil se borra.
			if (!usuario.getFoto().equals("default.png")) {
				String uploadPath = getServletContext().getRealPath("") + File.separator
						+ UsuariosEJB.getUploadDirectory();
				File foto = new File(uploadPath + File.separator + usuario.getFoto());
				foto.delete();
			}
			session.invalidate();
			usuariosEJB.bajar(usuario);
		}
		try {
			response.sendRedirect("Principal");
		} catch (IOException e) {
			log.getLoggerBaja().error("Se ha producido un error en Post Baja: ", e);
		}

	}

}
