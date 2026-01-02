package tutoria.controlador;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tutoria.modelo.ConexionDB;
import tutoria.modelo.dao.ProfesorDAO;
import tutoria.modelo.dao.TutoradoDAO;
import tutoria.modelo.pojo.Profesor;
import tutoria.modelo.pojo.Tutorado;

public class AsignarTutoradoControlador implements Initializable {

    @FXML
    private TableView<Tutorado> tvTutorados;
    @FXML
    private TableColumn<Tutorado, String> colMatricula;
    @FXML
    private TableColumn<Tutorado, String> colNombre;
    @FXML
    private TableColumn<Tutorado, String> colCarrera;
    
    @FXML
    private ComboBox<Profesor> cbTutores; 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarDatosTabla();
        cargarListaProfesores();
        tvTutorados.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void configurarTabla() {
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCarrera.setCellValueFactory(new PropertyValueFactory<>("carrera"));
    }

    private void cargarDatosTabla() {
        try {
            // Carga todos los alumnos para que el coordinador decida
            ArrayList<Tutorado> datosBD = TutoradoDAO.obtenerTodosLosTutorados(ConexionDB.abrirConexionBD());
            ObservableList<Tutorado> listaTutorados = FXCollections.observableArrayList(datosBD);
            tvTutorados.setItems(listaTutorados);
        } catch (SQLException ex) {
            Logger.getLogger(AsignarTutoradoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarListaProfesores() {
        try {
            // Carga la lista de profesores para el ComboBox
            ArrayList<Profesor> profesoresBD = ProfesorDAO.obtenerListaProfesores(ConexionDB.abrirConexionBD());
            ObservableList<Profesor> listaProfesores = FXCollections.observableArrayList(profesoresBD);
            cbTutores.setItems(listaProfesores);
        } catch (SQLException ex) {
            Logger.getLogger(AsignarTutoradoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
        Tutorado alumnoSeleccionado = tvTutorados.getSelectionModel().getSelectedItem();
        Profesor profesorSeleccionado = cbTutores.getSelectionModel().getSelectedItem();

        if (alumnoSeleccionado == null) {
            mostrarAlerta("Selección requerida", "Selecciona un alumno de la tabla.");
            return;
        }
        if (profesorSeleccionado == null) {
            mostrarAlerta("Selección requerida", "Selecciona un profesor de la lista.");
            return;
        }

        guardarAsignacion(alumnoSeleccionado, profesorSeleccionado);
    }
private void guardarAsignacion(Tutorado alumno, Profesor profesor) {

       try {
            Connection conexion = ConexionDB.abrirConexionBD();
            if (conexion != null) {
                
                // CAMBIO: Ya no usamos Integer.parseInt().
                // El profesor ya tiene el número como int.
                int noPersonalInt = profesor.getNoPersonal(); 

                System.out.println("Asignando al profesor: " + noPersonalInt); // Debug

                // Enviamos el int directo al DAO
                boolean exito = TutoradoDAO.asignarTutorAAlumno(alumno.getMatricula(), noPersonalInt, conexion);
                
                if (exito) {
                    mostrarAlerta("Éxito", "Tutor asignado correctamente.");
                    cargarDatosTabla(); 
                } else {
                    mostrarAlerta("Error", "No se pudo realizar la asignación.");
                }
                conexion.close();
            }
        } catch (SQLException ex) {
            // ... logs ...
            mostrarAlerta("Error", "Error de base de datos.");
        }

    }

    @FXML
    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) tvTutorados.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}