package aplicacion.modelo.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import aplicacion.modelo.dao.UsuarioDAO;
import aplicacion.modelo.pojo.Usuario;

@Stateless
@LocalBean
public class UsuariosEJB {

	public Usuario existeUsuario(String correo, String paswd) {
		UsuarioDAO usuariosDAO = new UsuarioDAO();
		return usuariosDAO.existeUsuario(correo, paswd);
	}

}
