package aplicacion.controlador;

import java.io.IOException;
import java.util.Date;

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

@WebServlet("/Historial")
public class Historial extends HttpServlet {
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
		if (usuario != null) {
			usuario.setCalculos(calculosEJB.getHistorial(usuario));
		} else {
			try {
				response.sendRedirect("Principal");
			} catch (IOException e) {
				log.getLoggerHistorial().error("Se ha producido un error en GET Historial: ", e);
			}
		}
		response.setContentType("text/html; charset=UTF-8");
		PaginaPrincipal paginaPrincipal = new PaginaPrincipal(usuario, null);
		try {
			paginaPrincipal.print(response.getWriter());
		} catch (IOException e) {
			log.getLoggerHistorial().error("Se ha producido un error en GET Historial: ", e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		LogSingleton log = LogSingleton.getInstance();
		String peso = request.getParameter("peso");
		String altura = request.getParameter("altura");

		Usuario usuario = sesionesEJB.usuarioLogeado(session);
		if (usuario != null) {
			usuario.setCalculos(calculosEJB.getHistorial(usuario));
		} else {
			try {
				response.sendRedirect("Principal");
			} catch (IOException e) {
				log.getLoggerHistorial().error("Se ha producido un error en POST Historial: ", e);
			}
		}
		Float p = null;
		Float a = null;
		String imc = null;
		if (peso != null && altura != null) {
			p = Float.parseFloat(peso);
			a = Float.parseFloat(altura);
			Calculo calculo = new Calculo(null, p, a, new Date());
			calculo.setImc(calculosEJB.calcula(p, a));
			imc = String.format("%.2f", calculo.getImc());
		}
		response.setContentType("text/html; charset=UTF-8");
		PaginaPrincipal paginaPrincipal = new PaginaPrincipal(usuario, imc);
		try {
			paginaPrincipal.print(response.getWriter());
		} catch (IOException e) {
			log.getLoggerHistorial().error("Se ha producido un error en POST Historial: ", e);
		}

	}

}
