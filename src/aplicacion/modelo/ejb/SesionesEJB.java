package aplicacion.modelo.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.servlet.http.HttpSession;

import aplicacion.modelo.pojo.Usuario;

@Stateless
@LocalBean
public class SesionesEJB {

	/**
	 * Obtiene un usuario de la sesión
	 * 
	 * @param session La sesión dónde buscamos al usuario
	 * @return El usuario que hay en la sesión o null si no hay usuario
	 */
	public Usuario usuarioLogeado(HttpSession session) {
		Usuario usuario = null;

		if (session != null) {
			usuario = (Usuario) session.getAttribute("usuario");
		}
		return usuario;
	}

	/**
	 * Añade un usuario a la sesión
	 * 
	 * @param session La sesión dónde añadir al usuario
	 * @param usuario El usuario a añadir
	 */
	public void loginUsuario(HttpSession session, Usuario usuario) {
		if (session != null) {
			session.setAttribute("usuario", usuario);
		}
	}

	/**
	 * Cierra la sesión
	 * 
	 * @param session La sesión a cerrar
	 */
	public void logoutUsuario(HttpSession session) {
		if (session != null) {
			session.invalidate();
		}
	}
}
