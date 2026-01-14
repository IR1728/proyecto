package tutoria.modelo.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Autenticacion{

  public static ResultSet autenticarUsuario(String noPersonal, String password, Connection conexionBD) throws SQLException {
   

    if (conexionBD != null) {
      String consulta = "SELECT idProfesor, nombre, "
        + "apellidoPaterno, apellidoMaterno, noPersonal, p.idRol, nombreRol "
        + "FROM profesor p "
        + "INNER JOIN rol r ON r.idRol = p.idRol "
        + "WHERE noPersonal = ? AND contrasena = ?";

      PreparedStatement sentencia = conexionBD.prepareStatement(consulta);

      sentencia.setString(1, noPersonal);
      sentencia.setString(2, password);

      ResultSet resultado = sentencia.executeQuery();

      return resultado;
    }

    throw new SQLException("No hay conexión a la base de datos");
  }
}
