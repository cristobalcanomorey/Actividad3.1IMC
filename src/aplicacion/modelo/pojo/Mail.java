package aplicacion.modelo.pojo;

public class Mail {
	private String host = "";
	private int port = 0;
	private String username = "";
	private String password = "";

	public Mail(String host, int port, String username, String password) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
