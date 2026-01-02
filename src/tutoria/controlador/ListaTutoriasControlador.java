package tutoria.controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tutoria.modelo.ConexionDB;
import tutoria.modelo.dao.HorarioDAO;
import tutoria.modelo.pojo.Horario; // Asegúrate de importar tu POJO Horario

public class ListaTutoriasControlador implements Initializable {

    @FXML
    private TableView<Horario> tvTutorias;
    @FXML
    private TableColumn<Horario, String> colFecha;
    @FXML
    private TableColumn<Horario, String> colHora;
    @FXML
    private TableColumn<Horario, String> colModalidad;
    @FXML
    private TableColumn<Horario, String> colLugar;
    @FXML
    private TableColumn<Horario, String> colPersonal;
    @FXML
    private TableColumn<Horario, String> colIdHorario;
    private int numeroPersonal;
    private int accion;
    

    // Lista observable para la tabla
    private ObservableList<Horario> listaHorarios;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        tvTutorias.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }    
    
    private void configurarTabla() {
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        colModalidad.setCellValueFactory(new PropertyValueFactory<>("modalidad"));
        colLugar.setCellValueFactory(new PropertyValueFactory<>("lugar"));
        colPersonal.setCellValueFactory(new PropertyValueFactory<>("numeroPersonal"));
        colIdHorario.setCellValueFactory(new PropertyValueFactory<>("idHorario"));
        tvTutorias.setRowFactory(tv -> {
        TableRow<Horario> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
           
            if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                
                Horario horarioSeleccionado = row.getItem();
               
                irAPantallaEdicion(horarioSeleccionado);
            }
        });
        return row;
    });
    }
    
    private void cargarDatosTabla() {


    try {
        // Obtenemos los datos del DAO
        ArrayList<Horario> datosBD = HorarioDAO.obtenerDatosHorario(this.numeroPersonal, ConexionDB.abrirConexionBD());
        
    
        
        // Convertimos a ObservableList
        listaHorarios = FXCollections.observableArrayList(datosBD);
        
        // Asignamos a la tabla
        tvTutorias.setItems(listaHorarios);
        
        
    } catch (SQLException ex) {
        Logger.getLogger(GestionarHorarioControlador.class.getName()).log(Level.SEVERE, null, ex);
        ex.printStackTrace();
    }
}
    public void asignarNumeroPersonal(int numeroPersonal,int accion){
        this.accion = accion;
        this.numeroPersonal = numeroPersonal;
        cargarDatosTabla();
    }
    
    @FXML
    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) tvTutorias.getScene().getWindow();
        stage.close();
    }
    private void irAPantallaEdicion(Horario horario) {
    try {
        // 1. Cargar el FXML de la ventana de edición (Ajusta el nombre de tu archivo)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tutoria/vistas/GestionarHorario.fxml"));
        Parent root = loader.load();

        // 2. Obtener el controlador de la nueva ventana
        GestionarHorarioControlador controlador = loader.getController();

        // 3. PASAR LOS DATOS (Necesitas crear este método en GestionarHorarioControlador)
        // Le pasamos el horario completo para que llene los campos automáticamente
        controlador.inicializarDatosParaEdicion(horario,this.numeroPersonal,2); 

        // 4. Mostrar la ventana
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Editar Horario");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        
        // 5. Opcional: Recargar la tabla al cerrar la edición para ver los cambios
        cargarDatosTabla(); 

    } catch (IOException ex) {
        ex.printStackTrace();
    }
}
}