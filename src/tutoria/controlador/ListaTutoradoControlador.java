package tutoria.controlador;



import java.io.IOException;

import java.net.URL;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.ResourceBundle;

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

import tutoria.Tutoria;

import tutoria.modelo.ConexionDB;

import tutoria.modelo.dao.TutoradoDAO;

import tutoria.modelo.pojo.Tutorado;



public class ListaTutoradoControlador implements Initializable {



    @FXML

    private TableColumn<Tutorado, String> colMatricula;

    @FXML

    private TableColumn<Tutorado, String> colNombre;

    @FXML

    private TableColumn<Tutorado, String> colCarrera;

    @FXML

    private TableColumn<Tutorado, String> colEstado;

    @FXML

    private TableView<Tutorado> tvTutorados;

    

    @FXML

    private TableColumn<Tutorado, String> colProblematica;

    // VARIABLE RESTAURADA A INT

    private int numeroPersonal; 

    private int accion;



    @Override

    public void initialize(URL url, ResourceBundle rb) {

        configurarTabla();

        tvTutorados.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }    

    

    private void configurarTabla() {

        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        colCarrera.setCellValueFactory(new PropertyValueFactory<>("carrera"));

        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        
        if(this.accion==5){ 
        colProblematica.setCellValueFactory(new PropertyValueFactory<>("problematica"));
        this.colProblematica.setVisible(true);
        }else{
            this.colProblematica.setVisible(false);
        }
            
        

        tvTutorados.setRowFactory(tv -> {

            TableRow<Tutorado> row = new TableRow<>();

            row.setOnMouseClicked(event -> {

                if (event.getClickCount() == 2 && (!row.isEmpty())) {

                    
                    Tutorado tutoradoSeleccionado = row.getItem();

                    System.out.println("entre"+tutoradoSeleccionado.getProblematica() );
                    
                    irAPantallaEdicion(tutoradoSeleccionado);

                }

            });

            return row;

        });

    }


    public void asignarNumeroPersonal(int numeroPersonal, int accion){
System.out.println("llegué aquí"+ numeroPersonal + accion );
        this.accion = accion;

        this.numeroPersonal = numeroPersonal;
        this.configurarTabla();
        cargarDatosTabla();

    }

    

    @FXML

    private void cerrarVentana(ActionEvent event) {

        Stage stage = (Stage) tvTutorados.getScene().getWindow();

        stage.close();

    }


    private void irAPantallaEdicion(Tutorado tutorado) {

        try {
        String vista="";    
        if(this.accion== 3 ){
         vista= "/tutoria/vistas/EstadoAsistencia.fxml";

        }
           
         if(this.accion== 5 ){
         vista= "/tutoria/vistas/RegistrarProblematica.fxml";

        }
            FXMLLoader loader = new FXMLLoader(getClass().getResource(vista));

            Parent root = loader.load();



            if(this.accion== 3 ){
                
                        EstadoAsistenciaControlador controlador = loader.getController();

                        controlador.inicializarDatosParaEdicion(tutorado, this.numeroPersonal, 1); 

            }
            if(this.accion== 5 ){
                
                        RegistrarProblematicaControlador controlador = loader.getController();

                        controlador.inicializarDatos(tutorado); 

            }
            Scene scene = new Scene(root);

            Stage stage = new Stage();

            stage.setScene(scene);

            stage.setTitle("Editar Asistencia");

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();

            

            cargarDatosTabla();



        } catch (IOException ex) {

            ex.printStackTrace();

        }

    }

    


    @FXML

    private void registrarAsistencia(ActionEvent event){

        navegarA("Administración de horario", "GestionarHorario", 1);

    }



    private void navegarA(String titulo, String nombreArchivo, int accion) {

        try {

            FXMLLoader cargador = new FXMLLoader(Tutoria.class.getResource("vistas/" + nombreArchivo + ".fxml"));

            Parent vista = cargador.load();

   

             if (accion == 1) {

              

                GestionarHorarioControlador controlador = cargador.getController();

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

    

    private void cargarDatosTabla() {

        try {

            

            ArrayList<Tutorado> datosBD = TutoradoDAO.obtenerTutoradosPorProfesor(this.numeroPersonal, ConexionDB.abrirConexionBD());

            ObservableList<Tutorado> listaTutorados = FXCollections.observableArrayList(datosBD);

            tvTutorados.setItems(listaTutorados);

        } catch (SQLException ex) {

            ex.printStackTrace();

        }

    }

}