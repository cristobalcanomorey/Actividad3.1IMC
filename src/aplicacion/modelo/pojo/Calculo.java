package aplicacion.modelo.pojo;

public class Calculo {

	private Integer id;
	private Float estatura;
	private Float peso;
	private Float imc;

	public Calculo(Integer id, Float estatura, Float peso, Float imc) {
		this.id = id;
		this.estatura = estatura;
		this.peso = peso;
		this.imc = imc;
	}

	public Float getEstatura() {
		return estatura;
	}

	public Float getPeso() {
		return peso;
	}

	public Float getImc() {
		return imc;
	}

}
