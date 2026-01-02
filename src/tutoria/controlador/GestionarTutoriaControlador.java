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
import javafx.stage.Modality;
import javafx.stage.Stage;
import tutoria.Tutoria;

public class GestionarTutoriaControlador implements Initializable {
    
    // VARIABLE RESTAURADA A INT
    private int numeroPersonal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    // MÉTODO RESTAURADO A INT
    public void inicializarDatos(int numeroPersonal){
        this.numeroPersonal = numeroPersonal;
    }

    private void navegarA(String titulo, String nombreArchivo, int accion) {
        try {
            FXMLLoader cargador = new FXMLLoader(Tutoria.class.getResource("vistas/" + nombreArchivo + ".fxml"));
            Parent vista = cargador.load();
   
            if (accion == 1) {
                // Gestionar Horario
                GestionarHorarioControlador controlador = cargador.getController();
                // IMPORTANTE: GestionarHorarioControlador debe esperar un int
                controlador.asignarNumeroPersonal(this.numeroPersonal, accion);
            } 
            
            if(accion == 2) {
                // Lista Tutorias
                ListaTutoriasControlador controlador = cargador.getController();
                // IMPORTANTE: ListaTutoriasControlador debe esperar un int
                controlador.asignarNumeroPersonal(this.numeroPersonal, accion);
            }
            
            if(accion == 3) {
                // Lista Tutorado
                ListaTutoradoControlador controlador = cargador.getController();
                // Pasa el int sin problemas
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
        }
    }

    @FXML
    private void modificarHorario(ActionEvent event){
        navegarA("Administración de horario", "ListaTutorias", 2);
    }
  
    @FXML
    private void registrarHorarios(ActionEvent event){
        navegarA("Administración de horario", "GestionarHorario", 1);
    }
    
    @FXML
    private void registrarAsistenciaTutorado(ActionEvent event){
        navegarA("Lista de tutorados", "ListaTutorado", 3);              
    }
}