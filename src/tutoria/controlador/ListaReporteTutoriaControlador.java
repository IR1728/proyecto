package tutoria.controlador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tutoria.Tutoria;
import tutoria.modelo.dao.ReporteTutoriaDAO;
import tutoria.modelo.pojo.ReporteTutoria;
import utilidad.Utilidades;

public class ListaReporteTutoriaControlador implements Initializable {

    @FXML
    private TableView<ReporteTutoria> tbReportes;
    @FXML
    private TableColumn<ReporteTutoria, String> colPeriodo;
    @FXML
    private TableColumn<ReporteTutoria, String> colDescripcion;
    @FXML
    private TableColumn<ReporteTutoria, String> colEstatus;
    @FXML
    private Button btnCerrar;
    @FXML
    private Button btnExportar;
    
    private int idTutor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        configurarDobleClic();
    }    
    
    private void configurarColumnas() {
        colPeriodo.setCellValueFactory(new PropertyValueFactory<>("periodoInfo"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colEstatus.setCellValueFactory(new PropertyValueFactory<>("estatus"));
    }
    
    private void configurarDobleClic() {
        tbReportes.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && tbReportes.getSelectionModel().getSelectedItem() != null) {
                ReporteTutoria seleccionado = tbReportes.getSelectionModel().getSelectedItem();
                irPantallaDetalle(seleccionado);
            }
        });
    }
    
    private void irPantallaDetalle(ReporteTutoria reporte) {
        try {
            FXMLLoader cargador = new FXMLLoader(Tutoria.class.getResource("vistas/RegistrarReporteTutoria.fxml"));
            Parent vista = cargador.load();
            
            RegistrarReporteTutoriaControlador controlador = cargador.getController();
            controlador.mostrarDetalles(reporte);
            
            Scene escena = new Scene(vista);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Detalle de Reporte");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "No se pudo abrir el detalle del reporte.", Alert.AlertType.ERROR);
        }
    }
    
    public void inicializarDatos(int idTutor) {
        this.idTutor = idTutor;
        cargarInformacionTabla();
    }
    
    private void cargarInformacionTabla() {
        try {
            ArrayList<ReporteTutoria> lista = ReporteTutoriaDAO.obtenerReportesPorTutor(this.idTutor);
            ObservableList<ReporteTutoria> datos = FXCollections.observableArrayList(lista);
            tbReportes.setItems(datos);
        } catch (SQLException ex) {
            ex.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "No se pudo cargar el historial.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicCerrar(ActionEvent event) {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clicExportar(ActionEvent event) {
        if (tbReportes.getItems().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Sin datos", "No hay información para exportar.", Alert.AlertType.WARNING);
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reporte TXT");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de Texto (*.txt)", "*.txt"));
        
        Stage stage = (Stage) btnExportar.getScene().getWindow();
        File archivo = fileChooser.showSaveDialog(stage);

        if (archivo != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                writer.write("======================================================================");
                writer.newLine();
                writer.write("                  HISTORIAL DE REPORTES DE TUTORÍA");
                writer.newLine();
                writer.write("======================================================================");
                writer.newLine();
                writer.newLine();

                for (ReporteTutoria reporte : tbReportes.getItems()) {
                    writer.write("PERIODO / SESIÓN: " + reporte.getPeriodoInfo());
                    writer.newLine();
                    writer.write("ESTATUS:          " + reporte.getEstatus());
                    writer.newLine();
                    writer.write("DESCRIPCIÓN:      " + reporte.getDescripcion());
                    writer.newLine();
                    writer.write("----------------------------------------------------------------------");
                    writer.newLine();
                }
                
                Utilidades.mostrarAlertaSimple("Éxito", "El archivo de texto se ha generado correctamente.", Alert.AlertType.INFORMATION);
            } catch (IOException ex) {
                ex.printStackTrace();
                Utilidades.mostrarAlertaSimple("Error", "No se pudo generar el archivo de texto.", Alert.AlertType.ERROR);
            }
        }
    }
}