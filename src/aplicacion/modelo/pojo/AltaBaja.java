package aplicacion.modelo.pojo;

import java.util.Date;

public class AltaBaja {

	private Integer id;
	private String correo;
	private String nombre;
	private String tipoAccion;
	private Date fecha;

	public AltaBaja(Integer id, String correo, String nombre, String tipoAccion, Date fecha) {
		this.id = id;
		this.correo = correo;
		this.nombre = nombre;
		this.tipoAccion = tipoAccion;
		this.fecha = fecha;
	}

	public Integer getId() {
		return id;
	}

	public String getCorreo() {
		return correo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getTipoAccion() {
		return tipoAccion;
	}

	public Date getFecha() {
		return fecha;
	}

}
