package tutoria.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import tutoria.modelo.dao.PlaneacionTutoriaDAO;
import tutoria.modelo.pojo.FechaTutoria;
import tutoria.modelo.pojo.PlaneacionTutoria;
import utilidad.Utilidades;

public class RegistrarPlaneacionControlador implements Initializable {

    @FXML private ComboBox<FechaTutoria> cbFechas;
    @FXML private TextArea taObjetivo;
    @FXML private TextArea taInstrucciones;
    @FXML private Button btnGuardar; 
    
    private int noPersonal;
    private boolean esEdicion = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarFechas();
        configurarListenerFecha();
    }
    
    public void inicializarDatos(int noPersonal) {
        this.noPersonal = noPersonal;
    }
    
    private void cargarFechas() {
        try {
            ObservableList<FechaTutoria> lista = FXCollections.observableArrayList(PlaneacionTutoriaDAO.obtenerFechasDisponibles());
            cbFechas.setItems(lista);
        } catch (SQLException ex) {
            Utilidades.mostrarAlertaSimple("Error", "Error al cargar periodos: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void configurarListenerFecha() {
        cbFechas.valueProperty().addListener(new ChangeListener<FechaTutoria>() {
            @Override
            public void changed(ObservableValue<? extends FechaTutoria> observable, FechaTutoria oldValue, FechaTutoria newValue) {
                if (newValue != null) {
                    cargarDatosPlaneacion(newValue.getIdFechaTutoria());
                }
            }
        });
    }

    private void cargarDatosPlaneacion(int idFechaTutoria) {
        PlaneacionTutoria planeacionExistente = PlaneacionTutoriaDAO.obtenerPlaneacionPorFecha(idFechaTutoria);
        
        if (planeacionExistente != null) {
            taObjetivo.setText(planeacionExistente.getObjetivos());
            taInstrucciones.setText(planeacionExistente.getJustificacion());
            esEdicion = true;
        } else {
            taObjetivo.clear();
            taInstrucciones.clear();
            esEdicion = false;
        }
    }

    @FXML
    private void clicGuardar() {
        FechaTutoria fechaSeleccionada = cbFechas.getValue();
        
        if (fechaSeleccionada != null && !taObjetivo.getText().isEmpty()) {
            PlaneacionTutoria plan = new PlaneacionTutoria();
            plan.setIdFechaTutoria(fechaSeleccionada.getIdFechaTutoria());
            plan.setNoPersonal(this.noPersonal); 
            plan.setNrc(55678); 
            plan.setObjetivos(taObjetivo.getText());
            plan.setJustificacion(taInstrucciones.getText());
            
            boolean exito;
            
            if (esEdicion) {
                exito = PlaneacionTutoriaDAO.actualizarPlaneacion(plan);
                if(exito) Utilidades.mostrarAlertaSimple("Actualización", "La planeación se ha actualizado correctamente.", Alert.AlertType.INFORMATION);
            } else {
                exito = PlaneacionTutoriaDAO.registrarPlaneacion(plan);
                if(exito) Utilidades.mostrarAlertaSimple("Registro", "Planeación registrada correctamente.", Alert.AlertType.INFORMATION);
            }
            
            if (exito) {
                ((Stage) taObjetivo.getScene().getWindow()).close();
            } else {
                Utilidades.mostrarAlertaSimple("Error", "No se pudo guardar la información.", Alert.AlertType.ERROR);
            }
            
        } else {
            Utilidades.mostrarAlertaSimple("Campos vacíos", "Seleccione una fecha y escriba el objetivo.", Alert.AlertType.WARNING);
        }
    }
}