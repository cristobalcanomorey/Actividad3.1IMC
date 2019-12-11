package aplicacion.modelo.ejb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import aplicacion.modelo.dao.CalculoDAO;
import aplicacion.modelo.pojo.Calculo;
import aplicacion.modelo.pojo.Usuario;

@Stateless
@LocalBean
public class CalculosEJB {

	public Float calcula(Float peso, Float altura) {
		return peso / (altura * altura);
	}

	public ArrayList<Calculo> getHistorial(Usuario usuario) {
		ArrayList<Calculo> calculos = CalculoDAO.getHistorial(usuario);
		for (Calculo calculo : calculos) {
			calculo.setImc(calcula(calculo.getPeso(), calculo.getEstatura()));
		}
		return calculos;
	}

	public static ArrayList<Calculo> rsAArrayList(ResultSet rs) throws SQLException {
		ArrayList<Calculo> calcs = new ArrayList<Calculo>();
		while (rs.next()) {
			Calculo c = new Calculo(rs.getInt("id"), rs.getFloat("estatura"), rs.getFloat("peso"), rs.getDate("fecha"));
			calcs.add(c);
		}
		return calcs;
	}

	public void guardarCalculo(Usuario usuario, Calculo calculo) {
		CalculoDAO.insertCalculo(usuario, calculo);
	}

}
