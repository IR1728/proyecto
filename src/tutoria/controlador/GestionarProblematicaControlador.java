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
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tutoria.Tutoria; 
import utilidad.Utilidades; 

public class GestionarProblematicaControlador implements Initializable {

    
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnModificar;

    
    private int numeroPersonal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
   
    public void inicializarDatos(int numeroPersonal){
        this.numeroPersonal = numeroPersonal;
    }

    @FXML
    private void clicRegistrarProblematica(ActionEvent event) {
        navegarA("Seleccionar Alumno", "ListaTutorado", 5);
    }

    @FXML
    private void clicModificarProblematica(ActionEvent event) {
       
        Utilidades.mostrarAlertaSimple("Información", "Módulo de consulta en construcción.", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void clicVolver(ActionEvent event) {
        Stage stage = (Stage) btnRegistrar.getScene().getWindow();
        stage.close();
    }

    private void navegarA(String titulo, String nombreArchivo, int accion) {
        try {
          
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("/tutoria/vistas/" + nombreArchivo + ".fxml"));
            Parent vista = cargador.load();
   
           
            if(accion == 5) {
                
                ListaTutoradoControlador controlador = cargador.getController();
                
               
                controlador.asignarNumeroPersonal(this.numeroPersonal, accion);
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
}