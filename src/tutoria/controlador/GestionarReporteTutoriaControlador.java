package tutoria.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tutoria.Tutoria;
import utilidad.Utilidades;

public class GestionarReporteTutoriaControlador implements Initializable {

    @FXML
    private Label lbNombre;
    @FXML
    private Label lbRol; 
    @FXML
    private Label lbNumPersonal;
    
    private int numeroPersonal;
    private String rolActual = ""; 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void inicializarDatos(int numeroPersonal, String rol){
        this.numeroPersonal = numeroPersonal;
        this.rolActual = rol;
        
        if(lbRol != null) lbRol.setText(rol);
        if(lbNumPersonal != null) lbNumPersonal.setText(String.valueOf(numeroPersonal));
    }

    private void navegarA(String titulo, String nombreArchivo, int accion) {
        try {
            FXMLLoader cargador = new FXMLLoader(Tutoria.class.getResource("vistas/" + nombreArchivo + ".fxml"));
            Parent vista = cargador.load();
   
            if (accion == 7) { 
                RegistrarReporteTutoriaControlador ctrl = cargador.getController();
                ctrl.inicializarDatos(this.numeroPersonal);
            }
            else if (accion == 8) { 
                ListaReporteTutoriaControlador ctrl = cargador.getController();
                ctrl.inicializarDatos(this.numeroPersonal);
            }
            
            Scene escena = new Scene(vista);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle(titulo);
            escenario.initModality(Modality.APPLICATION_MODAL); 
            escenario.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "No se pudo abrir la ventana: " + nombreArchivo, Alert.AlertType.ERROR);
        }
    }

    private boolean esTutor() {
        return rolActual != null && rolActual.toUpperCase().contains("TUTOR");
    }

    @FXML
    private void registrarReporteTutoria(ActionEvent event) {
        if (esTutor()) {
            navegarA("Registrar Reporte de Tutoría", "RegistrarReporteTutoria", 7);
        } else {
            Utilidades.mostrarAlertaSimple("Acceso Denegado", "Función exclusiva para Tutores.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void verHistorialReportes(ActionEvent event) {
        if (esTutor()) {
            navegarA("Historial de Reportes", "ListaReporteTutoria", 8);
        } else {
            Utilidades.mostrarAlertaSimple("Acceso Denegado", "Función exclusiva para Tutores.", Alert.AlertType.WARNING);
        }
    }
}