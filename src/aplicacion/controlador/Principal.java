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
import aplicacion.modelo.pojo.Calculo;
import aplicacion.modelo.pojo.Usuario;
import aplicacion.vista.PaginaPrincipal;

@WebServlet("/Principal")
public class Principal extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	SesionesEJB sesionesEJB;

	@EJB
	CalculosEJB calculosEJB;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		LogSingleton log = LogSingleton.getInstance();

		Usuario usuario = sesionesEJB.usuarioLogeado(session);

		response.setContentType("text/html; charset=UTF-8");
		PaginaPrincipal paginaPrincipal = new PaginaPrincipal(usuario, null);
		try {
			paginaPrincipal.print(response.getWriter());
		} catch (IOException e) {
			log.getLoggerPrincipal().error("Se ha producido un error en GET Principal: ", e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		LogSingleton log = LogSingleton.getInstance();
		String peso = request.getParameter("peso");
		String altura = request.getParameter("altura");

		Usuario usuario = sesionesEJB.usuarioLogeado(session);
		Calculo calculo = calculosEJB.calcula(peso, altura);
		response.setContentType("text/html; charset=UTF-8");
		PaginaPrincipal paginaPrincipal = new PaginaPrincipal(usuario, String.format("%.2f", calculo.getImc()));
		try {
			paginaPrincipal.print(response.getWriter());
		} catch (IOException e) {
			log.getLoggerPrincipal().error("Se ha producido un error en POST Principal:", e);
		}
	}

}
