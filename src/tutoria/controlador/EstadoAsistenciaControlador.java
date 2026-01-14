package tutoria.controlador;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import tutoria.modelo.pojo.Tutorado;
import tutoria.modelo.dao.TutoradoDAO; 
import tutoria.modelo.ConexionDB;      

public class EstadoAsistenciaControlador implements Initializable {

    private int numeroPersonal;
    private int accion;
    private Tutorado tutoradoEdicion;

    @FXML
    private Label lbNombreAlumno;
    @FXML
    private Label lbMatricula;
    @FXML
    private ToggleGroup grupoAsistencia;
    @FXML
    private RadioButton rbAsistio;
    @FXML
    private RadioButton rbNoAsistio;
    @FXML
    private RadioButton rbJustificado;
    @FXML
    private TextArea txtObservaciones;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
    }

    public void inicializarDatosParaEdicion(Tutorado tutorado, int numeroPersonal, int accion) {
        this.tutoradoEdicion = tutorado;
        this.numeroPersonal = numeroPersonal;
        this.accion = accion;

        if (tutoradoEdicion != null) {
            lbNombreAlumno.setText(tutoradoEdicion.getNombre());
            lbMatricula.setText(tutoradoEdicion.getMatricula());

            
            if (accion > 0) {
                txtObservaciones.setText(tutoradoEdicion.getObservaciones());
                String estatus = tutoradoEdicion.getEstado();

                if (estatus != null) {
                    switch (estatus) {
                        case "Asistencia":
                            rbAsistio.setSelected(true);
                            break;
                        case "Falta":
                            rbNoAsistio.setSelected(true);
                            break;
                        case "Justificado":
                            rbJustificado.setSelected(true);
                            break;
                        default:
                            rbAsistio.setSelected(false);
                            break;
                    }
                }
            }
        }
    }

    @FXML
    public void cancelar(ActionEvent event) {
        Stage stage = (Stage) txtObservaciones.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void guardar(ActionEvent event) {
        
        String estatusBD = null;

        if (rbAsistio.isSelected()) {
            estatusBD = "Asistencia";
        } else if (rbNoAsistio.isSelected()) {
            estatusBD = "Falta";
        } else if (rbJustificado.isSelected()) {
            estatusBD = "Justificado";
        }

        if (estatusBD == null) {
            mostrarAlerta("Error", "Debes seleccionar un estado de asistencia.");
            return;
        }

     
        String observaciones = txtObservaciones.getText();

        Connection conexion = ConexionDB.abrirConexionBD();

        if (conexion != null) {
            boolean exito = false;

            try {
               
                
                exito = TutoradoDAO.registrarAsistencia(
                        tutoradoEdicion.getMatricula(),
                        this.numeroPersonal,
                        estatusBD,
                        observaciones,
                        conexion
                );

                conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

           
            if (exito) {
                mostrarAlerta("Éxito", "Información guardada correctamente.");
                Stage stage = (Stage) txtObservaciones.getScene().getWindow();
                stage.close();
            } else {
                mostrarAlerta("Error", "No se pudo guardar en la base de datos.");
            }
        } else {
            mostrarAlerta("Error", "Sin conexión a la base de datos.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}