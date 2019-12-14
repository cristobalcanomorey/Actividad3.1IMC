package aplicacion.modelo.pojo;

public class Mail {
	private String host = "";
	private int port = 0;
	private String correo = "";
	private String password = "";

	public Mail(String host, int port, String username, String password) {
		this.host = host;
		this.port = port;
		this.correo = username;
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getCorreo() {
		return correo;
	}

	public String getPassword() {
		return password;
	}
}
