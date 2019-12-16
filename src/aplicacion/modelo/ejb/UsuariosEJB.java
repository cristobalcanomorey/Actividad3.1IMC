package aplicacion.modelo.ejb;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.servlet.http.Part;

import aplicacion.modelo.dao.UsuarioDAO;
import aplicacion.modelo.dao.ValidacionDAO;
import aplicacion.modelo.pojo.Mail;
import aplicacion.modelo.pojo.Usuario;
import aplicacion.vista.html.especificos.Tag;

@Stateless
@LocalBean
public class UsuariosEJB {

	@EJB
	MailEJB mailEJB;

	private static final String UPLOAD_DIRECTORY = "imgs" + File.separator + "users";

	/***
	 * Comprueba si existe un usuario con ese correo y esa contraseña
	 * 
	 * @param correo
	 * @param paswd
	 * @return Usuario con esos datos.
	 */
	public Usuario existeUsuario(String correo, String paswd) {
		return UsuarioDAO.existeUsuario(correo, paswd);
	}

	/***
	 * Comprueba si existe un usuario con ese correo
	 * 
	 * @param correo
	 * @return True si existe, False si no.
	 */
	public boolean existeUsuario(String correo) {
		return UsuarioDAO.existeUsuario(correo);
	}

	/***
	 * Crea la foto de perfil del usuario
	 * 
	 * @param carpeta
	 * @param partes
	 * @param usuario
	 * @return Ruta de la foto
	 * @throws IOException
	 */
	public String crearFotoDePerfil(String carpeta, Collection<Part> partes, String usuario) throws IOException {
		// Si la ruta no existe la crearemos
		File uploadDir = new File(carpeta);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		// Lo utilizaremos para guardar el nombre del archivo
		String fileName = null;

		// Obtenemos el archivo y lo guardamos a disco
		for (Part part : partes) {
			fileName = UsuariosEJB.getFileNameDeUsuario(part, usuario);
			part.write(carpeta + File.separator + fileName);
		}

		// Si es una imagen guardamos la ruta en fPerfil
		if (fileName.matches(".+\\.(jpg|png|jpeg)")) {
			return fileName;
		} else {
			return null;
		}
	}

	/**
	 * Obtiene el nombre original de la foto de perfíl y lo substituye por el nombre
	 * de usuario o por default si no hay
	 * 
	 * @param part    Documento obtenido
	 * @param usuario Nombre de usuario
	 * @return Nombre del documento modificado
	 */
	private static String getFileNameDeUsuario(Part part, String usuario) {
		String resul = "";
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename"))
				resul = content.substring(content.indexOf("=") + 2, content.length() - 1);
		}
		if (resul.indexOf(".") == -1) {
			return "default.png";
		}
		String nombreOriginal = resul.substring(0, resul.indexOf("."));
		resul = resul.replace(nombreOriginal, usuario);
		return resul;
	}

	/***
	 * Registra un usuario y le manda un correo de validación.
	 * 
	 * @param nuevo
	 * @return True si se ha mandado el correo, False si no
	 */
	public boolean registrarUsuario(Usuario nuevo) {
		String codigo = codigoAleatorio();
		UsuarioDAO.insertUsuario(nuevo);
		Usuario usu = UsuarioDAO.existeUsuario(nuevo.getCorreo(), nuevo.getPassword());
		ValidacionDAO.insertValidacion(usu, codigo);
		return enviarCorreo(usu, codigo);
	}

	/***
	 * Envia el correo de validación
	 * 
	 * @param nuevo  Usuario al que se le manda
	 * @param codigo Codigo de validación
	 * @return True si se ha mandado, false si no
	 */
	private boolean enviarCorreo(Usuario nuevo, String codigo) {
		Mail correo = mailEJB.getMail("smtp.gmail.com", 587, "imcpractica@gmail.com", "contrasenyaimc");
		Tag enlace = new Tag("a", "Haz clic aquí para validar tu cuenta.", true, true);
		enlace.prepararAtributos();
		enlace.addAtributo("href", "http://localhost:8080/Actividad3.1IMC/Validacion?codigo=" + codigo);
		return mailEJB.sendMail(nuevo.getCorreo(), "Validar tu cuenta en Actividad3.1IMC", enlace.toString(), correo);
	}

	/***
	 * Crea un String con 21 letras aleatorias entre la a y la z
	 * 
	 * @return String aleatorio
	 */
	private String codigoAleatorio() {
		int leftLimit = 97; // letra 'a'
		int rightLimit = 122; // letra 'z'
		int targetStringLength = 21;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		return buffer.toString();
	}

	public static String fechaAString(Date fecha) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(fecha);
	}

	/***
	 * Si existe el código en la BBDD valida su usuario, borra el código y añade una
	 * transacción en la tabla altas_bajas
	 * 
	 * @param codigo
	 * @return true si no existe, false si si.
	 */
	public boolean validar(String codigo) {
		String idUsuario = ValidacionDAO.selectIdUsuario(codigo);
		if (idUsuario != null) {
			UsuarioDAO.validarPorId(idUsuario);
			ValidacionDAO.borrar(codigo);
			AltasBajasEJB.registrarValidacion(idUsuario);
			return false;
		}
		return true;
	}

	public static String getRutaFotoCompleta(Usuario usu) {
		return UPLOAD_DIRECTORY + File.separator + usu.getFoto();
	}

	public static String getUploadDirectory() {
		return UPLOAD_DIRECTORY;
	}

	/***
	 * Da de baja a un usuario
	 * 
	 * @param usuario
	 */
	public void bajar(Usuario usuario) {
		UsuarioDAO.delete(usuario);
	}

	/***
	 * Borra a todos los usuarios que todavía no han sido validados.
	 */
	public void limpiar() {
		UsuarioDAO.limpiar();
	}
}
