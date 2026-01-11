package tutoria.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import tutoria.modelo.dao.FechaTutoriaDAO;
import tutoria.modelo.pojo.FechaTutoria;

public class RegistrarFechaTutoriaControlador implements Initializable {

    @FXML
    private ComboBox<String> cbPeriodo;
    @FXML
    private ComboBox<Integer> cbSesion;
    @FXML
    private DatePicker dpInicio;
    @FXML
    private DatePicker dpFin;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarPeriodos();
        cargarSesiones();
    }    
    
  
    private void cargarPeriodos() {
        ObservableList<String> periodos = FXCollections.observableArrayList();
        periodos.add("Febrero - Julio 2026");
        periodos.add("Agosto 2026 - Enero 2027");
        cbPeriodo.setItems(periodos);
        
        cbPeriodo.getSelectionModel().selectFirst();
    }
    
    private void cargarSesiones() {
        ObservableList<Integer> sesiones = FXCollections.observableArrayList();
        sesiones.addAll(1, 2, 3);
        cbSesion.setItems(sesiones);
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
      
        if (cbPeriodo.getValue() == null || cbSesion.getValue() == null || 
            dpInicio.getValue() == null || dpFin.getValue() == null) {
            mostrarAlerta("Campos vacíos", "Por favor completa toda la información del periodo.", Alert.AlertType.WARNING);
            return;
        }
        
        
        if (dpInicio.getValue().isAfter(dpFin.getValue())) {
            mostrarAlerta("Fechas inválidas", "La fecha de inicio no puede ser posterior a la fecha de cierre.", Alert.AlertType.WARNING);
            return;
        }

       
        String periodo = cbPeriodo.getValue();
        int sesion = cbSesion.getValue();
        
        String fechaInicio = dpInicio.getValue().toString();
        String fechaFin = dpFin.getValue().toString();
        
        FechaTutoria nuevaFecha = new FechaTutoria(periodo, sesion, fechaInicio, fechaFin);
        
      
        try {
            
            boolean existe = FechaTutoriaDAO.comprobarExistenciaSesion(periodo, sesion);
            if (existe) {
                mostrarAlerta("Duplicado", "Ya existe una configuración para la Sesión " + sesion + " en este periodo.", Alert.AlertType.WARNING);
                return;
            }
            
            boolean guardado = FechaTutoriaDAO.registrarFechaTutoria(nuevaFecha);
            
            if (guardado) {
                mostrarAlerta("Éxito", "Las fechas de tutoría se han configurado correctamente.", Alert.AlertType.INFORMATION);
                cerrarVentana();
            } else {
                mostrarAlerta("Error", "No se pudo guardar la información.", Alert.AlertType.ERROR);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error de Conexión", "Hubo un error al conectar con la base de datos.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        cerrarVentana();
    }
    
    private void cerrarVentana() {
        Stage stage = (Stage) btnGuardar.getScene().getWindow();
        stage.close();
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}