package aplicacion.modelo.pojo;

import java.util.Date;

public class Calculo {

	private Integer id;
	private Float estatura;
	private Float peso;
	private Float imc;
	private Date fecha;

	public Calculo(Integer id, Float estatura, Float peso, Date fecha) {
		this.id = id;
		this.estatura = estatura;
		this.peso = peso;
		this.fecha = fecha;
	}

	public Float getEstatura() {
		return estatura;
	}

	public Float getPeso() {
		return peso;
	}

	public void setImc(Float imc) {
		this.imc = imc;
	}

	public Float getImc() {
		return imc;
	}

	public Integer getId() {
		return id;
	}

	public Date getFecha() {
		return fecha;
	}

}
