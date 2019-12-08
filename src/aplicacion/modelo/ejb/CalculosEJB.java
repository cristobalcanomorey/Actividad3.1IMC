package aplicacion.modelo.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import aplicacion.modelo.pojo.Calculo;

@Stateless
@LocalBean
public class CalculosEJB {

	public Calculo calcula(String peso, String altura) {
		Float p = Float.valueOf(peso);
		Float a = Float.valueOf(altura);
		Float imc = p / (a * a);
		Calculo c = new Calculo(null, a, p, imc);
		return c;
	}

}
