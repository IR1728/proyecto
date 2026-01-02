package tutoria.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import tutoria.modelo.pojo.Profesor;

public class ProfesorDAO {

    public static ArrayList<Profesor> obtenerListaProfesores(Connection conexion) throws SQLException {
        ArrayList<Profesor> lista = new ArrayList<>();
        
        if (conexion != null) {
            // Pedimos las columnas correctas
            String sql = "SELECT idProfesor, noPersonal, nombre, apellidoPaterno, apellidoMaterno FROM profesor";
            
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Profesor p = new Profesor();
                p.setIdProfesor(rs.getInt("idProfesor"));
                
                // CAMBIO: Lectura directa. int de la BD -> int del Objeto
                // Sin conversiones raras.
                p.setNoPersonal(rs.getInt("noPersonal")); 
                
                p.setNombre(rs.getString("nombre"));
                p.setApellidoPaterno(rs.getString("apellidoPaterno"));
                p.setApellidoMaterno(rs.getString("apellidoMaterno"));
                
                lista.add(p);
            }
            rs.close();
            ps.close();
        }
        return lista;
    }
}