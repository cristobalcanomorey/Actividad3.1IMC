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

	public Usuario existeUsuario(String correo, String paswd) {
		return UsuarioDAO.existeUsuario(correo, paswd);
	}

	public boolean existeUsuario(String correo) {
		return UsuarioDAO.existeUsuario(correo);
	}

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

	public boolean registrarUsuario(Usuario nuevo) {
		String codigo = codigoAleatorio();
		UsuarioDAO.insertUsuario(nuevo);
		Usuario usu = UsuarioDAO.existeUsuario(nuevo.getCorreo(), nuevo.getPassword());
		ValidacionDAO.insertValidacion(usu, codigo);
		return enviarCorreo(usu, codigo);
	}

	private boolean enviarCorreo(Usuario nuevo, String codigo) {
		Mail correo = mailEJB.getMail("smtp.gmail.com", 587, "imcpractica@gmail.com", "contrasenyaimc");
		Tag enlace = new Tag("a", "Haz clic aquí para validar tu cuenta.", true, true);
		enlace.prepararAtributos();
		enlace.addAtributo("href", "Validacion?codigo=" + codigo);
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

	public boolean validar(String codigo) {
		String idUsuario = ValidacionDAO.selectIdUsuario(codigo);
		if (idUsuario != null) {
			UsuarioDAO.validarPorId(idUsuario);
			ValidacionDAO.borrar(codigo);
			return true;
		}
		return false;
	}
}
