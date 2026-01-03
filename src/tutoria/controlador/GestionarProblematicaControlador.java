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
import tutoria.Tutoria; // Asegúrate de que esta clase exista, o usa getClass()
import utilidad.Utilidades; // Para las alertas

public class GestionarProblematicaControlador implements Initializable {

    // Componentes del FXML
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnModificar;

    // Variable para guardar la sesión del tutor
    private int numeroPersonal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialización por defecto
    }    
    
    // MÉTODO PARA RECIBIR EL NÚMERO DE PERSONAL (INT)
    public void inicializarDatos(int numeroPersonal){
        this.numeroPersonal = numeroPersonal;
        System.out.println("Gestionar Problematica recibió No. Personal: " + this.numeroPersonal);
    }

    @FXML
    private void clicRegistrarProblematica(ActionEvent event) {
        navegarA("Seleccionar Alumno", "ListaTutorado", 5);
    }

    @FXML
    private void clicModificarProblematica(ActionEvent event) {
        // Aquí iría la navegación a consultar problemáticas
        Utilidades.mostrarAlertaSimple("Información", "Módulo de consulta en construcción.", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void clicVolver(ActionEvent event) {
        Stage stage = (Stage) btnRegistrar.getScene().getWindow();
        stage.close();
    }

    private void navegarA(String titulo, String nombreArchivo, int accion) {
        try {
            // Usamos getClass().getResource para mayor compatibilidad si Tutoria.class da problemas
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("/tutoria/vistas/" + nombreArchivo + ".fxml"));
            Parent vista = cargador.load();
   
            // Lógica para configurar el controlador destino según la acción
            if(accion == 5) {
                // Instanciamos el controlador de la lista de alumnos
                ListaTutoradoControlador controlador = cargador.getController();
                
                // Le pasamos el número de personal (int) para que cargue SUS alumnos
                controlador.asignarNumeroPersonal(this.numeroPersonal, accion);
            }
            
            // Mostrar la ventana
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