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
    private Label lbRol; // Si decides mostrarlo visualmente
    
    private int numeroPersonal;
    private String rolActual; // VARIABLE NUEVA PARA GUARDAR EL ROL

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    // --- 1. MODIFICAMOS ESTE MÉTODO PARA RECIBIR EL ROL ---
    public void inicializarDatos(int numeroPersonal, String rol){
        this.numeroPersonal = numeroPersonal;
        this.rolActual = rol;
        
        // Opcional: Mostrarlo en la etiqueta si existe en el FXML
        if(lbRol != null) lbRol.setText(rol);
        
        System.out.println("Usuario: " + numeroPersonal + " | Rol: " + rolActual);
    }

    private void navegarA(String titulo, String nombreArchivo, int accion) {
        try {
            FXMLLoader cargador = new FXMLLoader(Tutoria.class.getResource("vistas/" + nombreArchivo + ".fxml"));
            Parent vista = cargador.load();
   
            if (accion == 1) { // Gestionar Horario
                GestionarHorarioControlador ctrl = cargador.getController();
                ctrl.asignarNumeroPersonal(this.numeroPersonal, accion);
            } else if (accion == 2) { // Lista Tutorias
                ListaTutoriasControlador ctrl = cargador.getController();
                ctrl.asignarNumeroPersonal(this.numeroPersonal, accion);
            } else if (accion == 3) { // Lista Tutorado
                ListaTutoradoControlador ctrl = cargador.getController();
                ctrl.asignarNumeroPersonal(this.numeroPersonal, accion);
            } else if (accion == 5) { // Registrar Fechas
                 RegistrarFechaTutoriaControlador ctrl = cargador.getController();
                 // No requiere datos de sesión
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

    // --- VALIDACIÓN POR BOTÓN ---

    @FXML
    private void registrarHorarios(ActionEvent event){
        // VALIDACIÓN: Solo Tutores
        if (esTutor()) {
            navegarA("Administración de horario", "GestionarHorario", 1);
        } else {
            denegarAcceso("Tutor");
        }
    }

    @FXML
    private void modificarHorario(ActionEvent event){
        // VALIDACIÓN: Solo Tutores
        if (esTutor()) {
            navegarA("Administración de horario", "ListaTutorias", 2);
        } else {
            denegarAcceso("Tutor");
        }
    }
  
    @FXML
    private void registrarAsistenciaTutorado(ActionEvent event){
        // VALIDACIÓN: Solo Tutores
        if (esTutor()) {
            navegarA("Lista de tutorados", "ListaTutorado", 3);     
        } else {
            denegarAcceso("Tutor");
        }          
    }

    @FXML
    private void registrarFechaTutoria(ActionEvent event) {
        // VALIDACIÓN: Solo Coordinadores
        if (esCoordinador()) {
            navegarA("Configuración de Fechas", "RegistrarFechaTutoria", 5);
        } else {
             denegarAcceso("Coordinador");
        }
    }
    
    // --- MÉTODOS AUXILIARES DE VALIDACIÓN ---
    
    private boolean esTutor() {
        return rolActual != null && rolActual.toUpperCase().contains("TUTOR");
    }
    
    private boolean esCoordinador() {
        return rolActual != null && rolActual.toUpperCase().contains("COORDINADOR");
    }
    
    private void denegarAcceso(String rolRequerido) {
        Utilidades.mostrarAlertaSimple("Acceso Denegado", "Esta función es exclusiva para el rol de " + rolRequerido + ".", Alert.AlertType.WARNING);
    }
}