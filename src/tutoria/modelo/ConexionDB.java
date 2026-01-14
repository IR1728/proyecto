package tutoria.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import utilidad.Utilidades;

public class ConexionDB {

  private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
  private static final String NOMBRE_BD = "PROYECTO";
  private static final String IP = "localhost";
  private static final String PUERTO = "3306";
  private static final String URL_CONEXION = "jdbc:mysql://"
    + IP + ":" + PUERTO + "/" + NOMBRE_BD + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
  private static final String USUARIO = "usuario";
  private static final String PASSWORD = "contrasena";
  private static Connection CONEXION = null;

  public static Connection abrirConexionBD() {
    try {
      Class.forName(DRIVER);

      if (CONEXION == null || CONEXION.isClosed()) {
        CONEXION = DriverManager.getConnection("jdbc:mysql://localhost:3306/PROYECTO?serverTimezone=UTC", USUARIO, PASSWORD);
      }
    } catch (ClassNotFoundException e) {
      Utilidades.mostrarAlertaSimple("Error", e.getMessage(), Alert.AlertType.NONE);
      e.printStackTrace();
    } catch (SQLException e) {
      Utilidades.mostrarAlertaSimple("Error", e.getMessage(), Alert.AlertType.NONE);
      e.printStackTrace();
    }

    return CONEXION;
  }

  public static void cerrarConexionBD() {
    try {
      if (CONEXION != null && !CONEXION.isClosed()) {
        CONEXION.close();
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    } finally {
      CONEXION = null;
    }
  }
}
