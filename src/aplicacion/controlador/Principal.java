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

/***
 * Servlet para la página principal
 * 
 * @author tofol
 *
 */
@WebServlet("/Principal")
public class Principal extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	SesionesEJB sesionesEJB;

	@EJB
	CalculosEJB calculosEJB;

	/***
	 * Muestra la página principal con o sin el usuario.
	 * 
	 * Si el usuario está logeado le quita el historial para que no se muestre la
	 * tabla en la página principal.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		LogSingleton log = LogSingleton.getInstance();

		Usuario usuario = sesionesEJB.usuarioLogeado(session);

		response.setContentType("text/html; charset=UTF-8");
		if (usuario != null) {
			usuario.setCalculos(null);
		}
		PaginaPrincipal paginaPrincipal = new PaginaPrincipal(usuario, null, false);
		try {
			paginaPrincipal.print(response.getWriter());
		} catch (IOException e) {
			log.getLoggerPrincipal().error("Se ha producido un error en GET Principal: ", e);
		}
	}

	/***
	 * Muestra la página principal con el resultado del calculo IMC y lo guarda si
	 * el usuario está logeado.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		LogSingleton log = LogSingleton.getInstance();
		String peso = request.getParameter("peso");
		String altura = request.getParameter("altura");

		Usuario usuario = sesionesEJB.usuarioLogeado(session);
		Float p = null;
		Float a = null;
		String imc = null;
		if (peso != null && altura != null) {
			p = Float.parseFloat(peso);
			a = Float.parseFloat(altura);
			Calculo calculo = new Calculo(null, a, p, new Date());
			calculo.setImc(CalculosEJB.calcula(p, a));
			imc = String.format("%.2f", calculo.getImc());
			if (usuario != null) {
				calculosEJB.guardarCalculo(usuario, calculo);
			}
		}

		response.setContentType("text/html; charset=UTF-8");
		PaginaPrincipal paginaPrincipal = new PaginaPrincipal(usuario, imc, false);
		try {
			paginaPrincipal.print(response.getWriter());
		} catch (IOException e) {
			log.getLoggerPrincipal().error("Se ha producido un error en POST Principal:", e);
		}
	}

}
