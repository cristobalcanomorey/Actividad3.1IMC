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

}
