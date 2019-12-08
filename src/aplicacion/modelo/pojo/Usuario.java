package aplicacion.modelo.pojo;

import java.util.ArrayList;
import java.util.Date;

public class Usuario {

	private Integer id;
	private String correo;
	private String nombre;
	private String password;
	private String foto;
	private boolean validado;
	private Date fechaRegistro;
	private ArrayList<Calculo> calculos;

	public Usuario(Integer id, String correo, String nombre, String password, String foto, boolean validado,
			Date fechaRegistro, ArrayList<Calculo> calculos) {
		this.id = id;
		this.correo = correo;
		this.nombre = nombre;
		this.password = password;
		this.foto = foto;
		this.validado = validado;
		this.fechaRegistro = fechaRegistro;
		this.calculos = calculos;
	}

	public String getNombre() {
		return nombre;
	}

	public String getFoto() {
		return foto;
	}

}
