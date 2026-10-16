package co.edu.poli.examen2_Soto.vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Punto de entrada de la aplicación JavaFX para la gestión de tarjetas.
 * Carga la interfaz principal definida en {@code formCard.fxml}.
 */
public class App extends Application {

	/** Escena principal de la aplicación. */
	private static Scene scene;

	/**
	 * Inicializa la ventana principal y presenta el formulario de tarjetas.
	 *
	 * @param stage ventana principal proporcionada por JavaFX
	 * @throws IOException si no se puede cargar el archivo FXML de la interfaz
	 */
	@Override
	public void start(Stage stage) throws IOException {
		TabPane root = (TabPane) FXMLLoader.load(getClass().getResource("/co/edu/poli/examen2_Soto/formCard.fxml"));
		scene = new Scene(root);
		stage.setTitle("Tarjetas");
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Reemplaza el contenido de la escena principal por otra vista FXML.
	 *
	 * @param fxml nombre de la vista FXML, sin la extensión {@code .fxml}
	 * @throws IOException si no se puede cargar la vista solicitada
	 */
	static void setRoot(String fxml) throws IOException {
		scene.setRoot(loadFXML(fxml));
	}

	/**
	 * Carga una vista FXML ubicada en el paquete de recursos de la aplicación.
	 *
	 * @param fxml nombre de la vista FXML, sin la extensión {@code .fxml}
	 * @return nodo raíz definido en el archivo FXML
	 * @throws IOException si no se puede cargar o procesar el archivo FXML
	 */
	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
		return fxmlLoader.load();
	}

	/**
	 * Inicia el ciclo de vida de JavaFX.
	 *
	 * @param args argumentos de línea de comandos
	 */
	public static void main(String[] args) {
		launch();
	}

}