package utilidad;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import tutoria.Tutoria;
public class Utilidades {
  public static void mostrarAlertaSimple(String titulo, String contenido, Alert.AlertType tipo) {
    Alert alerta = new Alert(tipo);
    alerta.setTitle(titulo);
    alerta.setHeaderText(null);
    alerta.setContentText(contenido);
    alerta.showAndWait();
  }

  public static boolean mostrarConfirmacion(String titulo, String contenido) {
    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
    alerta.setTitle(titulo);
    alerta.setHeaderText(null);
    alerta.setContentText(contenido);
    return alerta.showAndWait().get() == ButtonType.OK;
  }

  public static FXMLLoader obtenerVista(String url) {
    return new FXMLLoader(Tutoria.class.getResource(url));
  }
}
