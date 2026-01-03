package tutoria.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import tutoria.modelo.ConexionDB;
import tutoria.modelo.pojo.Problematica;

public class ProblematicaDAO {

    // Método para REGISTRAR una nueva problemática
    public static boolean registrarProblematica(Problematica p) throws SQLException {
        boolean resultado = false;
        Connection conn = ConexionDB.abrirConexionBD();
        
        if (conn != null) {
            // Usamos CURDATE() de SQL para guardar la fecha del servidor automáticamente
            String sql = "INSERT INTO problematica (titulo, descripcion, fechaRegistro, matricula) "
                       + "VALUES (?, ?, CURDATE(), ?)";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getTitulo());
            ps.setString(2, p.getDescripcion());
            ps.setString(3, p.getMatricula()); // Importante: Aquí va la matrícula del alumno
            
            int filasAfectadas = ps.executeUpdate();
            resultado = (filasAfectadas > 0);
            
            ps.close();
            conn.close();
        }
        return resultado;
    }

    // Método para CONSULTAR problemáticas de un alumno específico
    public static ArrayList<Problematica> obtenerProblematicasPorAlumno(String matricula) throws SQLException {
        ArrayList<Problematica> lista = new ArrayList<>();
        Connection conn = ConexionDB.abrirConexionBD();
        
        if (conn != null) {
            String sql = "SELECT idProblematica, titulo, descripcion, fechaRegistro "
                       + "FROM problematica WHERE matricula = ?";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, matricula);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Problematica p = new Problematica();
                p.setIdProblematica(rs.getInt("idProblematica"));
                p.setTitulo(rs.getString("titulo"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setFechaRegistro(rs.getString("fechaRegistro"));
                p.setMatricula(matricula);
                
                lista.add(p);
            }
            rs.close();
            ps.close();
            conn.close();
        }
        return lista;
    }
}