/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tutoria.controlador;
import tutoria.Tutoria;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tutoria.dominio.AutenticacionImplementacion;
import tutoria.modelo.pojo.Profesor;
import utilidad.Utilidades;


/**
 * FXML Controller class
 *
 * @author jiran
 */
public class InicioSesionControlador implements Initializable {
    
    
  @FXML
  private TextField txtPersonal;
  @FXML
  private TextField txtPassword;

  @FXML
  private Label lblErrorPersonal;
  @FXML
  private Label lblErrorPassword;

  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    private void login(ActionEvent event) {
        
      
        String noPersonal = txtPersonal.getText();
        String password = txtPassword.getText();
        
        if (sonDatosValidos(noPersonal, password)){
        
            validarSesion(noPersonal, password);
        }
    }
    
    
  private boolean sonDatosValidos(String noPersonal, String password) {
    boolean correcto = true;
    lblErrorPersonal.setText("");
    lblErrorPassword.setText("");
    if (noPersonal == null || noPersonal.isEmpty()) {
      correcto = false;
      lblErrorPersonal.setText("Numero de personal obligatorio");
    }
    if (password == null || password.isEmpty()) {
      correcto = false;
      lblErrorPassword.setText("Contraseña obligatoria");
    }
    return correcto;
  }

  private void validarSesion(String noPersonal, String password) {
    HashMap<String, Object> respuesta = AutenticacionImplementacion.verificarSesionProfesor(noPersonal, password);
    boolean error = (boolean) respuesta.get("error");

    if (error) {
      Utilidades.mostrarAlertaSimple("Credenciales incorrectas",
        (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
    } else {
        Profesor profesorSesion = (Profesor) respuesta.get("profesor");
      Utilidades.mostrarAlertaSimple("Credenciales correctas", "Bienvenido(a) profesor(a) " + profesorSesion.getNombre()
        + ", al sistema de administración escolar", Alert.AlertType.INFORMATION);
      irPantallaPrincipal(profesorSesion);
    }
  }
  private void irPantallaPrincipal(Profesor profesorSesion) {
      
    try {
      FXMLLoader cargador = new FXMLLoader(Tutoria.class.getResource("vistas/Principal.fxml"));
      Parent vista = cargador.load();
      PrincipalControlador controlador = cargador.getController();
      controlador.obtenerSesion(profesorSesion);
      Scene escena = new Scene(vista);
      Stage escenario = (Stage) txtPersonal.getScene().getWindow();
      escenario.setScene(escena);
      escenario.setTitle("Inicio");
      escenario.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
    
}
