/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tutoria.controlador;
import javafx.scene.control.Alert;
import javafx.fxml.FXML;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tutoria.modelo.ConexionDB;
import tutoria.modelo.pojo.Horario;
import utilidad.Utilidades;
import tutoria.modelo.dao.HorarioDAO;
/**
 * FXML Controller class
 *
 * @author jiran
 */
public class GestionarHorarioControlador implements Initializable {
    
private Horario horarioEdicion; 
    private int numeroPersonal;
    @FXML
    private DatePicker fechaHorario;
    @FXML
    private TextField txtHora;
    @FXML
    private ComboBox<String> cmbModalidad;
    @FXML
    private TextField txtLugar;
    private int accion;
    private int idHorario;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbModalidad.getItems().addAll("Presencial", "Virtual");
    }    
    
    @FXML
    private void guardarHorario(ActionEvent event) {
        
        if(fechaHorario.getValue() == null || txtHora.getText().isEmpty() || 
           cmbModalidad.getSelectionModel().isEmpty() || txtLugar.getText().isEmpty()){
            
            Utilidades.mostrarAlertaSimple("Campos vacíos", 
                "Por favor llena todos los datos de la sesión", 
                Alert.AlertType.WARNING);
        } else {
    
           String fecha = fechaHorario.getValue().toString();
           String  modalidad = cmbModalidad.getValue();
           String  hora = txtHora.getText();
           String  lugar = txtLugar.getText();
          
           Horario horario = new Horario(this.idHorario, fecha, modalidad, hora, lugar,this.numeroPersonal);
            try {
                boolean registroInsertado ;
                if(this.accion==1){
                    registroInsertado = HorarioDAO.registrarHorario(horario, ConexionDB.abrirConexionBD());
                }else{
                    registroInsertado = HorarioDAO.modificarHorario(horario, ConexionDB.abrirConexionBD());
                }
               
                if(registroInsertado){
                     Utilidades.mostrarAlertaSimple("","El horario se guardó exitosamente", Alert.AlertType.CONFIRMATION);
                    if(this.accion==1){
                        fechaHorario.setValue(null);
                        cmbModalidad.setValue("Presencial");
                        txtLugar.setText("");
                        txtHora.setText("");
                    }
                }           
            } catch (SQLException ex) {
                Logger.getLogger(GestionarHorarioControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        
        txtLugar.getScene().getWindow().hide();
    }
   
   public void asignarNumeroPersonal(int numeroPersonal, int accion){
      
        this.numeroPersonal = numeroPersonal;
        
        this.accion = accion;
        
      
    }



public void inicializarDatosParaEdicion(Horario horario,int numeroPersonal,int accion) {
    this.horarioEdicion = horario;
    this.numeroPersonal=numeroPersonal;
    this.idHorario=horario.getIdHorario();
    this.accion=accion;
    System.out.println("horario"+this.horarioEdicion.toString()+this.horarioEdicion.getHora()+this.horarioEdicion.getLugar()+this.horarioEdicion.getFecha()+this.horarioEdicion.getModalidad()+this.horarioEdicion.getIdHorario());
    txtHora.setText(horario.getHora());
    txtLugar.setText(horario.getLugar());
    cmbModalidad.setValue(horario.getModalidad());
   
    if(horario.getFecha() != null) {
        fechaHorario.setValue(LocalDate.parse(horario.getFecha())); 
    }
}
}
