package tutoria.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import tutoria.modelo.ConexionDB;
import tutoria.modelo.pojo.FechaTutoria;

public class FechaTutoriaDAO {

    /**
     * Registra un nuevo periodo de tutoría en la base de datos.
     */
    public static boolean registrarFechaTutoria(FechaTutoria fecha) throws SQLException {
        boolean resultado = false;
        Connection conn = ConexionDB.abrirConexionBD();
        
        if (conn != null) {
            String sql = "INSERT INTO fechatutoria (periodo, numeroSesion, fechaInicio, fechaFin) "
                       + "VALUES (?, ?, ?, ?)";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, fecha.getPeriodo());
            ps.setInt(2, fecha.getNumeroSesion());
            ps.setString(3, fecha.getFechaInicio()); 
            ps.setString(4, fecha.getFechaFin());
            
            int filasAfectadas = ps.executeUpdate();
            resultado = (filasAfectadas > 0);
            
            ps.close();
            conn.close();
        }
        return resultado;
    }
    
    /**
     * Verifica si ya existe una configuración para esa sesión en ese periodo.
     * Sirve para evitar duplicados (ej. Que no registren dos veces la Sesión 1).
     */
    public static boolean comprobarExistenciaSesion(String periodo, int numSesion) throws SQLException {
        boolean existe = false;
        Connection conn = ConexionDB.abrirConexionBD();
        
        if (conn != null) {
            String sql = "SELECT COUNT(*) FROM fechatutoria WHERE periodo = ? AND numeroSesion = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, periodo);
            ps.setInt(2, numSesion);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                existe = (rs.getInt(1) > 0);
            }
            
            conn.close();
        }
        return existe;
    }

    /**
     * Obtiene todas las fechas registradas (Útil para llenar ComboBoxes en otros CUs).
     */
    public static ArrayList<FechaTutoria> obtenerFechasTutoria() throws SQLException {
        ArrayList<FechaTutoria> lista = new ArrayList<>();
        Connection conn = ConexionDB.abrirConexionBD();
        
        if (conn != null) {
            String sql = "SELECT idFechaTutoria, periodo, numeroSesion, fechaInicio, fechaFin "
                       + "FROM fechatutoria ORDER BY periodo DESC, numeroSesion ASC";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                FechaTutoria f = new FechaTutoria();
                f.setIdFechaTutoria(rs.getInt("idFechaTutoria"));
                f.setPeriodo(rs.getString("periodo"));
                f.setNumeroSesion(rs.getInt("numeroSesion"));
                f.setFechaInicio(rs.getString("fechaInicio"));
                f.setFechaFin(rs.getString("fechaFin"));
                
                lista.add(f);
            }
            conn.close();
        }
        return lista;
    }
}