package tutoria.dominio;

import tutoria.modelo.ConexionDB;
import tutoria.modelo.dao.Autenticacion;
import tutoria.modelo.pojo.Profesor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class AutenticacionImplementacion {

  public static HashMap<String, Object> verificarSesionProfesor(String noPersonal, String password) {
    HashMap<String, Object> respuesta = new LinkedHashMap<>();
    try {
      ResultSet resultado = Autenticacion.autenticarUsuario(noPersonal, password, ConexionDB.abrirConexionBD());

      if (resultado.next()) {
        //Credenciales correctas
        Profesor profesorSesion = new Profesor();
        
        profesorSesion.setIdProfesor(resultado.getInt("idProfesor"));
        profesorSesion.setNombre(resultado.getString("nombre"));
        profesorSesion.setApellidoPaterno(resultado.getString("apellidoPaterno"));
        profesorSesion.setApellidoMaterno(resultado.getString("apellidoMaterno"));
        profesorSesion.setNoPersonal(resultado.getInt("noPersonal"));
        profesorSesion.setIdRol(resultado.getInt("idRol"));
        profesorSesion.setRol(resultado.getString("nombreRol"));
        
        respuesta.put("error", false);
        respuesta.put("mensaje", "Credenciales correctas.");
        respuesta.put("profesor", profesorSesion);
      } else {
        respuesta.put("error", true);
        respuesta.put("mensaje", "Las credenciales proporcionadas son incorrectas, por favor verifica la información");
      }

      ConexionDB.cerrarConexionBD();
    } catch (SQLException ex) {
      respuesta.put("error", true);
      respuesta.put("mensaje", ex.getMessage());
    }

    return respuesta;
  }
}
