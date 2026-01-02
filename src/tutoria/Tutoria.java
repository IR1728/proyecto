/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tutoria;

import java.util.logging.Level; 
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import utilidad.Utilidades;

/**
 *
 * @author jiran
 */
public class Tutoria extends Application {

    /**
     * @param args the command line arguments
     */
private static final Logger LOGGER =
        Logger.getLogger(Tutoria.class.getName()); 
 
 
 

  @Override
  public void start(Stage primaryStage) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("vistas/InicioSesion.fxml"));
      Scene scene = new Scene(root);
      primaryStage.setTitle("Control Escolar - Inicio de Sesión");
      primaryStage.setScene(scene);
      primaryStage.show();
      configureUncaughtErrorHandler();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void configureUncaughtErrorHandler() {
    Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
      @Override
      public void uncaughtException(Thread thread, Throwable e) {
        Platform.runLater(() -> {
         LOGGER.log(Level.SEVERE, "Error inesperado", e); 
          Utilidades.mostrarAlertaSimple("Error de Sistema", e.getMessage(), Alert.AlertType.ERROR);
        });
      }
    });
  }

  public static void main(String[] args) {
    launch(args);
  }
    
}
