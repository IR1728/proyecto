package tutoria.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import tutoria.modelo.dao.FechaTutoriaDAO;
import tutoria.modelo.dao.ReporteTutoriaDAO;
import tutoria.modelo.pojo.FechaTutoria;
import tutoria.modelo.pojo.ReporteTutoria;
import utilidad.Utilidades;

public class RegistrarReporteTutoriaControlador implements Initializable {

    @FXML
    private ComboBox<FechaTutoria> cbFecha;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private TextArea taComentarios;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;
    
    private int idTutor;
    private boolean esConsulta = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarFechas();
    }    
    
    public void inicializarDatos(int idTutor) {
        this.idTutor = idTutor;
    }
    
    public void mostrarDetalles(ReporteTutoria reporte) {
        this.esConsulta = true;
        
        taDescripcion.setText(reporte.getDescripcion());
        taComentarios.setText(reporte.getComentariosGenerales());
        
        if (cbFecha.getItems() != null) {
            for (FechaTutoria fecha : cbFecha.getItems()) {
                if (fecha.getIdFechaTutoria() == reporte.getIdFechaTutoria()) {
                    cbFecha.setValue(fecha);
                    break;
                }
            }
        }
        
        cbFecha.setDisable(true);
        taDescripcion.setEditable(false);
        taComentarios.setEditable(false);
        
        btnGuardar.setVisible(false);
        btnCancelar.setText("Cerrar");
    }
    
    private void cargarFechas() {
        try {
            ArrayList<FechaTutoria> lista = FechaTutoriaDAO.obtenerFechasTutoria();
            ObservableList<FechaTutoria> fechasObservables = FXCollections.observableArrayList(lista);
            cbFecha.setItems(fechasObservables);
        } catch (SQLException ex) {
            ex.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error de Carga", "No se pudieron cargar los periodos disponibles.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
        if(esConsulta) return;

        if (cbFecha.getValue() == null) {
            Utilidades.mostrarAlertaSimple("Campo requerido", "Por favor seleccione el periodo y sesión.", Alert.AlertType.WARNING);
            return;
        }
        
        if (taDescripcion.getText().isEmpty() || taComentarios.getText().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campos vacíos", "Debe completar la descripción y los comentarios generales.", Alert.AlertType.WARNING);
            return;
        }
        
        ReporteTutoria reporte = new ReporteTutoria();
        reporte.setDescripcion(taDescripcion.getText());
        reporte.setComentariosGenerales(taComentarios.getText());
        reporte.setNoPersonal(this.idTutor);
        reporte.setIdFechaTutoria(cbFecha.getValue().getIdFechaTutoria());
        
        try {
            if (ReporteTutoriaDAO.comprobarExistenciaReporte(reporte.getIdFechaTutoria(), this.idTutor)) {
                Utilidades.mostrarAlertaSimple("Duplicado", "Ya existe un reporte registrado para esta sesión.", Alert.AlertType.WARNING);
                return;
            }
            
            boolean guardado = ReporteTutoriaDAO.registrarReporte(reporte);
            
            if (guardado) {
                Utilidades.mostrarAlertaSimple("Éxito", "El reporte se ha guardado correctamente.", Alert.AlertType.INFORMATION);
                cerrarVentana();
            } else {
                Utilidades.mostrarAlertaSimple("Error", "No se pudo guardar el reporte.", Alert.AlertType.ERROR);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error DB", "Error de conexión.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        cerrarVentana();
    }
    
    private void cerrarVentana() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
}