package co.edu.poli.examen2_Soto.controlador;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import co.edu.poli.examen2_Soto.modelo.Credito;
import co.edu.poli.examen2_Soto.modelo.Debito;
import co.edu.poli.examen2_Soto.modelo.EstadoTarjeta;
import co.edu.poli.examen2_Soto.modelo.Tarjeta;
import co.edu.poli.examen2_Soto.modelo.Titular;
import co.edu.poli.examen2_Soto.servicios.DAOTarjeta;
import co.edu.poli.examen2_Soto.servicios.DAOTitular;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * Controlador JavaFX para consultar, crear y actualizar tarjetas.
 * Vincula los controles del formulario con los servicios de persistencia.
 */
public class ControlFormCard {

	@FXML
	private Button bttConsulta;
	@FXML
	private TextField txtTarjeta1;
	@FXML
	private TextArea txtAreaResultado;
	@FXML
	private ComboBox<EstadoTarjeta> cmbEstadoConsulta;
	@FXML
	private Button bttActualizarEstado;
	@FXML
	private Button bttCreacion;
	@FXML
	private TextField txtTarjeta2;
	@FXML
	private DatePicker datepk1;
	@FXML
	private ComboBox<Titular> cmbTitular;
	@FXML
	private ComboBox<EstadoTarjeta> cmbEstadoCreacion;
	@FXML
	private RadioButton radio1; // Débito
	@FXML
	private RadioButton radio2; // Crédito
	@FXML
	private ToggleGroup tipo;

	private DAOTarjeta daoTarjeta;

	private DAOTitular daoTitular;

	private String numeroTarjetaConsultada;

	/**
	 * Inicializa los servicios, estados disponibles y controles del formulario.
	 * JavaFX invoca este método después de inyectar los componentes FXML.
	 */
	@FXML
	private void initialize() {
		daoTarjeta = new DAOTarjeta();
		daoTitular = new DAOTitular();

		datepk1.setValue(LocalDate.now());
		cmbEstadoConsulta.getItems().setAll(EstadoTarjeta.values());
		cmbEstadoCreacion.getItems().setAll(EstadoTarjeta.values());
		cmbEstadoCreacion.setValue(EstadoTarjeta.ACTIVA);
		bttActualizarEstado.setDisable(true);

		List<Titular> lista = null;
		try {
			lista = daoTitular.readall();
		} catch (Exception e) {
			mostrarAlerta(e.getMessage());
		}
		cmbTitular.getItems().setAll(lista);

		txtTarjeta1.focusedProperty().addListener((obs, oldVal, newVal) -> {
			if (!newVal)
				validarSoloNumeros(txtTarjeta1);
		});

		txtTarjeta2.focusedProperty().addListener((obs, oldVal, newVal) -> {
			if (!newVal)
				validarSoloNumeros(txtTarjeta2);
		});

	}

	/**
	 * Busca la tarjeta cuyo número se ingresó y muestra sus datos y estado.
	 *
	 * @param event evento generado por la acción de consulta
	 */
	@FXML
	private void pressConsulta(ActionEvent event) {
		txtAreaResultado.setText("");
		String numero = txtTarjeta1.getText().trim();
		if (!numero.isEmpty()) {
			try {
				Tarjeta t = daoTarjeta.readone(numero);

				if (t != null) {
					numeroTarjetaConsultada = t.getNumero();
					cmbEstadoConsulta.setValue(t.getEstado());
					bttActualizarEstado.setDisable(false);
					txtAreaResultado.setText(t.toString());
				} else {
					numeroTarjetaConsultada = null;
					cmbEstadoConsulta.setValue(null);
					bttActualizarEstado.setDisable(true);
					mostrarAlerta("No existe el numero de tarjeta");
				}
			} catch (Exception e) {
				numeroTarjetaConsultada = null;
				cmbEstadoConsulta.setValue(null);
				bttActualizarEstado.setDisable(true);
				mostrarAlerta(e.getMessage());
			}
		} else
			mostrarAlerta("Ingrese número de tarjeta");
	}

	/**
	 * Guarda en la base de datos el estado seleccionado para la tarjeta consultada.
	 *
	 * @param event evento generado por la acción de actualización
	 */
	@FXML
	private void pressActualizarEstado(ActionEvent event) {
		if (numeroTarjetaConsultada == null) {
			mostrarAlerta("Consulte una tarjeta antes de actualizar su estado.");
			return;
		}
		EstadoTarjeta estado = cmbEstadoConsulta.getValue();
		if (estado == null) {
			mostrarAlerta("Seleccione un estado.");
			return;
		}

		try {
			String resultado = daoTarjeta.updateEstado(numeroTarjetaConsultada, estado);
			mostrarAlerta(resultado);
			Tarjeta actualizada = daoTarjeta.readone(numeroTarjetaConsultada);
			if (actualizada != null) {
				txtAreaResultado.setText(actualizada.toString());
				cmbEstadoConsulta.setValue(actualizada.getEstado());
			}
		} catch (Exception e) {
			mostrarAlerta(e.getMessage());
		}
	}

	/**
	 * Valida los datos ingresados y crea una tarjeta del tipo seleccionado.
	 *
	 * @param event evento generado por la acción de creación
	 */
	@FXML
	private void pressCreacion(ActionEvent event) {

		String numero = txtTarjeta2.getText().trim();
		if (numero.isEmpty()) {
			mostrarAlerta("⚠ Ingrese el número de tarjeta.");
			return;
		}

		if (datepk1.getValue() == null) {
			mostrarAlerta("⚠ Seleccione la fecha de expedición.");
			return;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String fechaExp = datepk1.getValue().format(formatter);

		Titular titular = cmbTitular.getValue();
		if (titular == null) {
			mostrarAlerta("⚠ Seleccione un titular.");
			return;
		}

		EstadoTarjeta estado = cmbEstadoCreacion.getValue();
		if (estado == null) {
			mostrarAlerta("⚠ Seleccione un estado para la tarjeta.");
			return;
		}

		Tarjeta nueva;

		if (radio1.isSelected()) {
			nueva = new Debito(numero, fechaExp, estado, titular, 0.0);
		} else {
			nueva = new Credito(numero, fechaExp, estado, titular, 1000000);
		}

		try {
			String resultado = daoTarjeta.create(nueva);
			mostrarAlerta(resultado);
			if (resultado.startsWith("✔")) {
				limpiarFormCrear();
			}
		} catch (Exception e) {
			mostrarAlerta(e.getMessage());
		}

	}

	/**
	 * Presenta un mensaje informativo al usuario.
	 *
	 * @param mensaje texto que se mostrará en la alerta
	 */
	private void mostrarAlerta(String mensaje) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Resultado");
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}

	/** Restablece los controles del formulario de creación a sus valores iniciales. */
	private void limpiarFormCrear() {
		txtTarjeta2.clear();
		datepk1.setValue(null);
		cmbTitular.setValue(null);
		cmbEstadoCreacion.setValue(EstadoTarjeta.ACTIVA);
		radio1.setSelected(true);
	}

	/**
	 * Comprueba que el campo indicado contenga únicamente dígitos.
	 *
	 * @param txt campo de texto que se va a validar
	 */
	private void validarSoloNumeros(TextField txt) {
		String texto = txt.getText().trim();
		if (!texto.isBlank()) {
			if (!texto.matches("\\d+")) {
				txtAreaResultado.setText("");
				txt.setStyle("-fx-border-color: red;");
				mostrarAlerta("Solo números son permitidos!");
				txt.setText("");
				Platform.runLater(() -> txt.requestFocus());
			} else {
				txt.setStyle("");
			}
		} else
			txt.setStyle("");
	}

}