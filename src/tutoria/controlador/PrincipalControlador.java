package tutoria.controlador;

import tutoria.Tutoria;
import tutoria.modelo.pojo.Profesor;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utilidad.Utilidades;

public class PrincipalControlador {

    @FXML
    private Label lbNombre;
    @FXML
    private Label lbRol;
    @FXML
    private Label lbNumPersonal;

    public void obtenerSesion(Profesor profesorSesion) {
        lbNombre.setText(profesorSesion.getNombre() + " " + profesorSesion.getApellidoPaterno() + " " + profesorSesion.getApellidoMaterno());
        lbNumPersonal.setText(String.valueOf(profesorSesion.getNoPersonal())); 
        lbRol.setText("Rol: " + profesorSesion.getRol());
    }

    private void navegarA(String titulo, String nombreArchivo, int accion) {
        try {
            FXMLLoader cargador = new FXMLLoader(Tutoria.class.getResource("vistas/" + nombreArchivo + ".fxml"));
            Parent vista = cargador.load();

            // CASO 1: GESTIONAR TUTORÍA (Menú Secundario)
            if (accion == 2) {
                GestionarTutoriaControlador controlador = cargador.getController();
                try {
                    int numPersonal = Integer.parseInt(lbNumPersonal.getText());
                    String rol = lbRol.getText(); 
                    controlador.inicializarDatos(numPersonal, rol);
                    // -----------------------------
                    
                } catch (NumberFormatException e) {
                    Utilidades.mostrarAlertaSimple("Error", "El número de personal no es válido.", Alert.AlertType.ERROR);
                    return;
                }
            } 
            
            // CASO 2: GESTIONAR PROBLEMÁTICAS
            else if (accion == 5) {
                GestionarProblematicaControlador controlador = cargador.getController();
                try {
                    int numPersonal = Integer.parseInt(lbNumPersonal.getText());
                    controlador.inicializarDatos(numPersonal);
                } catch (NumberFormatException e) {
                    Utilidades.mostrarAlertaSimple("Error", "No se pudo recuperar el ID del profesor.", Alert.AlertType.ERROR);
                    return;
                }
            }

            // CASO 3: COORDINADOR
            else if (accion == 4) {
                AsignarTutoradoControlador controlador = cargador.getController();
            }

            Scene escena = new Scene(vista);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle(titulo);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error de Navegación", "No se pudo abrir la ventana: " + nombreArchivo, Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void clicCerrarSesion(ActionEvent event) {
        try {
            Parent vista = FXMLLoader.load(Tutoria.class.getResource("vistas/FXMLInicioSesion.fxml"));
            Scene escena = new Scene(vista);
            Stage stPrincipal = (Stage) lbNombre.getScene().getWindow();
            stPrincipal.setScene(escena);
            stPrincipal.setTitle("Iniciar sesión");
            stPrincipal.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void gestionarTutoria(ActionEvent event){
        // Validación inicial para entrar al menú
        // (Aunque luego validaremos cada botón dentro, es bueno validar aquí también)
        navegarA("Gestionar Horario", "GestionarTutoria", 2);
    }
  
    @FXML
    private void gestionarProblematica(ActionEvent event) {
        if(lbRol.getText().toUpperCase().contains("TUTOR")) {
            navegarA("Gestión de Problemáticas", "GestionarProblematica", 5);
        } else {
             Utilidades.mostrarAlertaSimple("Acceso Denegado", "Se requiere rol de Tutor", Alert.AlertType.WARNING);
        }
    }
    
    @FXML
    private void asignarTutorado(ActionEvent event) {
         if (lbRol.getText().toUpperCase().contains("COORDINADOR")) {
            navegarA("Asignar Tutorado", "AsignarTutorado", 4);
        } else {
             Utilidades.mostrarAlertaSimple("Acceso Denegado", "Se requiere rol de Coordinador", Alert.AlertType.WARNING);
        }
    }
}