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

public class GestionarReporteTutoriaControlador implements Initializable {

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
    
    // Variable para identificar al tutor que hace el reporte
    private int idTutor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarFechas();
    }    
    
    // Método para recibir el ID del tutor desde la ventana anterior
    public void inicializarDatos(int idTutor) {
        this.idTutor = idTutor;
    }
    
    private void cargarFechas() {
        try {
            // Reutilizamos el DAO de Fechas para llenar el combo
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
        // 1. Validaciones de campos vacíos
        if (cbFecha.getValue() == null) {
            Utilidades.mostrarAlertaSimple("Campo requerido", "Por favor seleccione el periodo y sesión.", Alert.AlertType.WARNING);
            return;
        }
        
        if (taDescripcion.getText().isEmpty() || taComentarios.getText().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campos vacíos", "Debe completar la descripción y los comentarios generales.", Alert.AlertType.WARNING);
            return;
        }
        
        // 2. Preparar el objeto Reporte
        ReporteTutoria reporte = new ReporteTutoria();
        reporte.setDescripcion(taDescripcion.getText());
        reporte.setComentariosGenerales(taComentarios.getText());
        reporte.setNoPersonal(this.idTutor);
        
        FechaTutoria fechaSeleccionada = cbFecha.getValue();
        reporte.setIdFechaTutoria(fechaSeleccionada.getIdFechaTutoria());
        
        // 3. Guardar en Base de Datos
        try {
            // Validación de Negocio: Verificar si ya existe un reporte para esta sesión
            if (ReporteTutoriaDAO.comprobarExistenciaReporte(fechaSeleccionada.getIdFechaTutoria(), this.idTutor)) {
                Utilidades.mostrarAlertaSimple("Duplicado", "Ya existe un reporte registrado para esta sesión. Consulte el historial.", Alert.AlertType.WARNING);
                return;
            }
            
            boolean guardado = ReporteTutoriaDAO.registrarReporte(reporte);
            
            if (guardado) {
                Utilidades.mostrarAlertaSimple("Éxito", "El reporte de tutoría se ha guardado correctamente.", Alert.AlertType.INFORMATION);
                cerrarVentana();
            } else {
                Utilidades.mostrarAlertaSimple("Error", "No se pudo guardar el reporte.", Alert.AlertType.ERROR);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error de Base de Datos", "Hubo un error al conectar con el servidor.", Alert.AlertType.ERROR);
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
}