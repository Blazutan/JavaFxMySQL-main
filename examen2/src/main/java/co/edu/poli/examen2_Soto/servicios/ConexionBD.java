package co.edu.poli.examen2_Soto.servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Administra la conexión compartida a la base de datos MySQL.
 * Las credenciales se obtienen de las variables DB_URL, DB_USER y DB_PASSWORD
 * definidas en un archivo {@code .env}.
 */
public class ConexionBD {

	private static ConexionBD instancia;
	private Connection conexion;

	/**
	 * Carga la configuración del entorno y establece la conexión con MySQL.
	 *
	 * @throws Exception si no se encuentra la configuración o no es posible
	 *                   establecer la conexión
	 */
	private ConexionBD() throws Exception {

		Path envDirectory = Paths.get("").toAbsolutePath().normalize();
		Path envFile = envDirectory.resolve(".env");
		if (!Files.isRegularFile(envFile) && envDirectory.getParent() != null
				&& Files.isRegularFile(envDirectory.getParent().resolve(".env"))) {
			envDirectory = envDirectory.getParent();
		}

		Dotenv dotenv = Dotenv.configure().directory(envDirectory.toString()).load();

		String url = dotenv.get("DB_URL");
		String user = dotenv.get("DB_USER");
		String pass = dotenv.get("DB_PASSWORD");

		if (url == null || user == null || pass == null) {
			throw new RuntimeException("Faltan variables en el .env");
		}

		Class.forName("com.mysql.cj.jdbc.Driver");
		conexion = DriverManager.getConnection(url, user, pass);
	}

	/**
	 * Obtiene la instancia única del administrador de conexión.
	 *
	 * @return instancia compartida
	 * @throws Exception si no se puede inicializar la conexión
	 */
	public static ConexionBD getInstancia() throws Exception {
		if (instancia == null) {
			instancia = new ConexionBD();
		}
		return instancia;
	}

	/**
	 * Obtiene la conexión activa, creándola de nuevo si está cerrada.
	 *
	 * @return conexión a la base de datos
	 * @throws Exception si no se puede crear o recuperar la conexión
	 */
	public Connection getConexion() throws Exception {
		if (conexion == null || conexion.isClosed()) {
			instancia = new ConexionBD();
			return instancia.conexion;
		}
		return conexion;
	}
}
