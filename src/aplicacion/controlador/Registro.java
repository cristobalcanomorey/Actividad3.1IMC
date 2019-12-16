package aplicacion.controlador;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import aplicacion.modelo.LogSingleton;
import aplicacion.modelo.ejb.SesionesEJB;
import aplicacion.modelo.ejb.UsuariosEJB;
import aplicacion.modelo.pojo.Usuario;
import aplicacion.vista.PaginaRegistro;

/***
 * Servlet para registrar usuarios.
 * 
 * @author tofol
 *
 */
@WebServlet("/Registro")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5)
public class Registro extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String FALTAN_DATOS = "1";
	private static final String USUARIO_EXISTE = "2";
	private static final String ERROR_CORREO = "3";
	@EJB
	SesionesEJB sesionesEJB;

	@EJB
	UsuariosEJB usuariosEJB;

	/***
	 * Si el usuario está logeado lo redirige a la págin principal, si no, muestra
	 * la página de registro.
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
				log.getLoggerRegistro().error("Se ha producido un error en Get Registro: ", e);
			}
		} else {
			String error = request.getParameter("error");
			if (error != null) {
				if (error.equals(FALTAN_DATOS)) {
					error = "No puedes dejar campos en blanco";
				} else if (error.equals(USUARIO_EXISTE)) {
					error = "Ya existe un usuario con ese correo electrónico";
				} else if (error.equals(ERROR_CORREO)) {
					error = "No te hemos podido mandar el correo de validación, comprueba que has puesto un correo que exista e intentalo de nuevo o vuelve a intentarlo más tarde";
				}
			}
			response.setContentType("text/html; charset=UTF-8");
			PaginaRegistro paginaRegistro = new PaginaRegistro(error, false);
			try {
				paginaRegistro.print(response.getWriter());
			} catch (IOException e) {
				log.getLoggerRegistro().error("Se ha producido un error en Get Registro: ", e);
			}
		}
	}

	/***
	 * Si el usuario está logeado lo redirige a la página principal, si no, guarda
	 * el usuario y su código de autenticación en la BBDD
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		LogSingleton log = LogSingleton.getInstance();

		Usuario logeado = null;
		if (session != null) {
			logeado = sesionesEJB.usuarioLogeado(session);
		}
		if (logeado != null) {
			try {
				if (!response.isCommitted())
					response.sendRedirect("Principal");
			} catch (IOException e) {
				log.getLoggerRegistro().error("Se ha producido un error en Post Registro: ", e);
			}
		}
		String uploadPath = getServletContext().getRealPath("") + File.separator + UsuariosEJB.getUploadDirectory();
		String nombre = request.getParameter("nombre");
		String correo = request.getParameter("correo");
		String paswd = request.getParameter("paswd");
		String fPerfil = null;
		boolean correoEnviado = false;
		Usuario nuevo = null;

		if (nombre != null & correo != null & paswd != null) {
			if (!usuariosEJB.existeUsuario(correo)) {
				try {
					// Establece el correo sin @ y sin puntos como el nombre de su foto de perfil
					String rutaPerfil = correo.replace("@", "_").replace(".", "_");
					fPerfil = usuariosEJB.crearFotoDePerfil(uploadPath, request.getParts(), rutaPerfil);
					nuevo = new Usuario(correo, nombre, paswd, fPerfil, false, new Date());
					correoEnviado = usuariosEJB.registrarUsuario(nuevo);
				} catch (IOException | ServletException e) {
					log.getLoggerRegistro().error("Se ha producido un error en Post Registro: ", e);
				}
			} else {
				try {
					if (!response.isCommitted())
						response.sendRedirect("Registro?error=" + USUARIO_EXISTE);
				} catch (IOException e) {
					log.getLoggerRegistro().error("Se ha producido un error en Post Registro: ", e);
				}
			}
		} else {
			try {
				if (!response.isCommitted())
					response.sendRedirect("Registro?error=" + FALTAN_DATOS);
			} catch (IOException e) {
				log.getLoggerRegistro().error("Se ha producido un error en Post Registro: ", e);
			}
		}

		if (correoEnviado) {
			response.setContentType("text/html; charset=UTF-8");
			PaginaRegistro paginaRegistro = new PaginaRegistro(null, true);
			try {
				paginaRegistro.print(response.getWriter());
			} catch (IOException e) {
				log.getLoggerRegistro().error("Se ha producido un error en Post Registro: ", e);
			}

		} else {
			try {
				if (!response.isCommitted())
					response.sendRedirect("Registro?error=" + ERROR_CORREO);
			} catch (IOException e) {
				log.getLoggerRegistro().error("Se ha producido un error en Post Registro: ", e);
			}
		}
	}

}
