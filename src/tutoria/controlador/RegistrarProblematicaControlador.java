package tutoria.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tutoria.modelo.dao.ProblematicaDAO;
import tutoria.modelo.pojo.Problematica;
import tutoria.modelo.pojo.Tutorado;

public class RegistrarProblematicaControlador implements Initializable {

    @FXML
    private Label lbNombreAlumno;
    @FXML
    private TextField tfTitulo;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;
    

    private String matriculaAlumno;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

   
    public void inicializarDatos(Tutorado alumnoSeleccionado) {
        if (alumnoSeleccionado != null) {
            this.matriculaAlumno = alumnoSeleccionado.getMatricula();
            
            
            lbNombreAlumno.setText(alumnoSeleccionado.getNombre());
            
        }
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
    
        String titulo = tfTitulo.getText();
        String descripcion = taDescripcion.getText();
        
        if (titulo == null || titulo.trim().isEmpty() || descripcion == null || descripcion.trim().isEmpty()) {
            mostrarAlerta("Campos vacíos", "Por favor, ingresa el título y la descripción de la problemática.", Alert.AlertType.WARNING);
            return;
        }
        
       
        Problematica nuevaProblematica = new Problematica();
        nuevaProblematica.setTitulo(titulo);
        nuevaProblematica.setDescripcion(descripcion);
        nuevaProblematica.setMatricula(matriculaAlumno); 
        
       
        try {
            boolean resultado = ProblematicaDAO.registrarProblematica(nuevaProblematica);
            
            if (resultado) {
                mostrarAlerta("Registro Exitoso", "La problemática se ha guardado correctamente.", Alert.AlertType.INFORMATION);
                cerrarVentana();
            } else {
                mostrarAlerta("Error", "No se pudo guardar la información en la base de datos.", Alert.AlertType.ERROR);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error de Conexión", "Hubo un error al conectar con la base de datos:\n" + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        cerrarVentana();
    }
    
    private void cerrarVentana() {
        Stage escenario = (Stage) tfTitulo.getScene().getWindow();
        escenario.close();
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}