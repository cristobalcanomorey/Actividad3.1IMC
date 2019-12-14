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
	private ArrayList<Calculo> calculos = null;

	public Usuario(Integer id, String correo, String nombre, String password, String foto, boolean validado,
			Date fechaRegistro) {
		this.id = id;
		this.correo = correo;
		this.nombre = nombre;
		this.password = password;
		this.foto = foto;
		this.validado = validado;
		this.fechaRegistro = fechaRegistro;
	}

	public Usuario(String correo, String nombre, String password, String foto, boolean validado, Date fechaRegistro) {
		this.correo = correo;
		this.nombre = nombre;
		this.password = password;
		this.foto = foto;
		this.validado = validado;
		this.fechaRegistro = fechaRegistro;
	}

	public void setCalculos(ArrayList<Calculo> calculos) {
		this.calculos = calculos;
	}

	public ArrayList<Calculo> getCalculos() {
		return this.calculos;
	}

	public String getNombre() {
		return nombre;
	}

	public String getFoto() {
		return foto;
	}

	public boolean isValidado() {
		return validado;
	}

	public Integer getId() {
		return this.id;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public String getPassword() {
		return password;
	}

	public String getCorreo() {
		return correo;
	}

}
