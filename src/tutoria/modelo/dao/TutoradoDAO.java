package tutoria.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import tutoria.modelo.pojo.Tutorado;

public class TutoradoDAO {
    
public static ArrayList<Tutorado> obtenerTodosLosTutorados(Connection conexion) throws SQLException {
    ArrayList<Tutorado> lista = new ArrayList<>();
    if (conexion != null) {
        
        String sql = "SELECT matricula, nombre, carrera FROM tutorados";
        
        java.sql.PreparedStatement ps = conexion.prepareStatement(sql);
        java.sql.ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            Tutorado t = new Tutorado();
            t.setMatricula(rs.getString("matricula"));
            t.setNombre(rs.getString("nombre"));
            t.setCarrera(rs.getString("carrera"));
            lista.add(t);
        }
        conexion.close();
    }
    return lista;
}
    
    public static boolean registrarAsistencia(String matricula, int idTutor, String estatus, String observaciones, Connection conexion) {
        boolean resultado = false;
        try {
            String sql = "INSERT INTO asistencia (fk_matricula, fk_tutor, estatus_asistencia, observaciones) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, matricula);
            ps.setInt(2, idTutor);
            ps.setString(3, estatus);
            ps.setString(4, observaciones);
            
            int filasAfectadas = ps.executeUpdate();
            resultado = (filasAfectadas > 0);
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("llegué3");
        return resultado;
    }
    
    public static ArrayList<Tutorado> obtenerTutoradosPorProfesor(int noPersonal, Connection conexion) throws SQLException {
        ArrayList<Tutorado> lista = new ArrayList<>();
        if(conexion != null){
            
        System.out.println("llegué4"+noPersonal);
            String sql = "SELECT t.matricula, t.nombre, t.carrera, a.id_asistencia, " +
                     "a.estatus_asistencia AS estado, a.observaciones,p.descripcion " +
                     "FROM tutorados t " +
                     "LEFT JOIN asistencia a ON t.matricula = a.fk_matricula " +
                    "LEFT JOIN problematica p ON t.matricula = p.matricula " +
                     "WHERE t.fk_tutor = ?";
            
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, noPersonal);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Tutorado t = new Tutorado();
                t.setMatricula(rs.getString("matricula"));
                t.setNombre(rs.getString("nombre"));
                t.setCarrera(rs.getString("carrera"));
                
               
                t.setIdAsistencia(rs.getInt("id_asistencia")); 
                
              
                String estatus = rs.getString("estado");
                String observaciones = rs.getString("observaciones");
                
                t.setEstado(estatus);
                t.setObservaciones(observaciones);
                t.setProblematica(rs.getString("descripcion"));
                
               
                t.setEvaluado(estatus);
                
                lista.add(t);
            }
            rs.close();
            ps.close();
        }
        return lista;
    }
  
    
    public static boolean asignarTutorAAlumno(String matricula, int noPersonal, Connection conexion) throws SQLException {
        boolean resultado = false;
        if(conexion != null){
            String sql = "UPDATE tutorados SET fk_tutor = ? WHERE matricula = ?";
            
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, noPersonal);
            ps.setString(2, matricula);
            
            int filasAfectadas = ps.executeUpdate();
            resultado = (filasAfectadas > 0);
            ps.close();
        }
        return resultado;
    }
}