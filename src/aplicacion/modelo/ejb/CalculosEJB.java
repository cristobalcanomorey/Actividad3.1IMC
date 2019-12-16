package aplicacion.modelo.ejb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import aplicacion.modelo.dao.CalculoDAO;
import aplicacion.modelo.pojo.Calculo;
import aplicacion.modelo.pojo.Usuario;

@Stateless
@LocalBean
public class CalculosEJB {

	/***
	 * Realiza el calculo IMC
	 * 
	 * @param peso
	 * @param altura
	 * @return Resultado del cálculo
	 */
	public static Float calcula(Float peso, Float altura) {
		return peso / (altura * altura);
	}

	/***
	 * Obtiene todos los cálculos de un usuario
	 * 
	 * @param usuario Usuario en cuestión
	 * @return ArrayList con sus cálculos
	 */
	public ArrayList<Calculo> getHistorial(Usuario usuario) {
		ArrayList<Calculo> calculos = CalculoDAO.getHistorial(usuario);
		if (calculos != null) {
			for (Calculo calculo : calculos) {
				calculo.setImc(calcula(calculo.getPeso(), calculo.getEstatura()));
			}
		}
		return calculos;
	}

	/***
	 * Convierte de ResultSet a ArrayList con calculos
	 * 
	 * @param rs ResultSet a convertir
	 * @return ArrayList con calculos
	 * @throws SQLException Error de la BBDD
	 */
	public static ArrayList<Calculo> rsAArrayList(ResultSet rs) throws SQLException {
		ArrayList<Calculo> calcs = new ArrayList<Calculo>();
		while (rs.next()) {
			Calculo c = new Calculo(rs.getInt("id"), rs.getFloat("estatura"), rs.getFloat("peso"), rs.getDate("fecha"));
			c.setImc(calcula(c.getPeso(), c.getEstatura()));
			calcs.add(c);
		}
		return calcs;
	}

	public void guardarCalculo(Usuario usuario, Calculo calculo) {
		CalculoDAO.insertCalculo(usuario, calculo);
	}

	public static String fechaAString(Date fecha) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(fecha);
	}

}
