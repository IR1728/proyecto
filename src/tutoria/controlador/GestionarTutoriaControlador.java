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

public class GestionarTutoriaControlador implements Initializable {

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
   
            if (accion == 1) { 
                GestionarHorarioControlador ctrl = cargador.getController();
                ctrl.asignarNumeroPersonal(this.numeroPersonal, accion);
            } 
            else if (accion == 2) { 
                ListaTutoriaControlador ctrl = cargador.getController();
                ctrl.asignarNumeroPersonal(this.numeroPersonal, accion);
            }
            else if (accion == 3) { 
                ListaTutoradoControlador ctrl = cargador.getController();
                ctrl.asignarNumeroPersonal(this.numeroPersonal, accion);
            }
            else if (accion == 5) { 
                 RegistrarFechaTutoriaControlador ctrl = cargador.getController();
            }
            else if (accion == 6) { 
    RegistrarPlaneacionControlador ctrl = cargador.getController();
}
            else if (accion == 7) { 
                GestionarReporteTutoriaControlador ctrl = cargador.getController();
                ctrl.inicializarDatos(this.numeroPersonal, this.rolActual);
            }
            
            Scene escena = new Scene(vista);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle(titulo);
            escenario.initModality(Modality.APPLICATION_MODAL); 
            escenario.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "No se pudo abrir la ventana.", Alert.AlertType.ERROR);
        }
    }

    private boolean esTutor() {
        return rolActual != null && rolActual.toUpperCase().contains("TUTOR");
    }
    
    private boolean esCoordinador() {
        return rolActual != null && rolActual.toUpperCase().contains("COORDINADOR");
    }

    @FXML
    private void clicRegistrarPlaneacion(ActionEvent event) {
        if (esCoordinador()) {
            try {
                FXMLLoader cargador = new FXMLLoader(getClass().getResource("/tutoria/vistas/RegistrarPlaneacion.fxml"));
                Parent root = cargador.load();
                
                RegistrarPlaneacionControlador ctrl = cargador.getController();
                ctrl.inicializarDatos(this.numeroPersonal); 
                
                Scene escena = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(escena);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                
            } catch (IOException e) {
                e.printStackTrace();
                Utilidades.mostrarAlertaSimple("Error", "No se pudo abrir la ventana de planeación.", Alert.AlertType.ERROR);
            }
        } else {
             Utilidades.mostrarAlertaSimple("Acceso Denegado", "Se requiere rol de Coordinador.", Alert.AlertType.WARNING);
        }
    }
    @FXML
    private void registrarHorarios(ActionEvent event){
        if (esTutor()) {
            navegarA("Administración de horario", "GestionarHorario", 1);
        } else {
            Utilidades.mostrarAlertaSimple("Acceso Denegado", "Se requiere rol de Tutor.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void modificarHorario(ActionEvent event){
        if (esTutor()) {
            navegarA("Administración de horario", "ListaTutorias", 2);
        } else {
            Utilidades.mostrarAlertaSimple("Acceso Denegado", "Se requiere rol de Tutor.", Alert.AlertType.WARNING);
        }
    }
  
    @FXML
    private void registrarAsistenciaTutorado(ActionEvent event){
        if (esTutor()) {
            navegarA("Lista de tutorados", "ListaTutorado", 3);     
        } else {
            Utilidades.mostrarAlertaSimple("Acceso Denegado", "Se requiere rol de Tutor.", Alert.AlertType.WARNING);
        }          
    }

    @FXML
    private void registrarFechaTutoria(ActionEvent event) {
        if (esCoordinador()) {
            navegarA("Configuración de Fechas", "RegistrarFechaTutoria", 5);
        } else {
             Utilidades.mostrarAlertaSimple("Acceso Denegado", "Se requiere rol de Coordinador.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void gestionarReporteTutoria(ActionEvent event) {
        if (esTutor()) {
            navegarA("Generar Reporte de Tutoría", "GestionarReporteTutoria", 7);
        } else {
            Utilidades.mostrarAlertaSimple("Acceso Denegado", "Función exclusiva para Tutores.", Alert.AlertType.WARNING);
        }
    }
  

}