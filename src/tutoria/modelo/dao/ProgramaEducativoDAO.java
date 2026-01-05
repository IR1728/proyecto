package tutoria.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import tutoria.modelo.ConexionDB;
import tutoria.modelo.pojo.ProgramaEducativo;

public class ProgramaEducativoDAO {
    
    public static ArrayList<ProgramaEducativo> obtenerProgramasEducativos() throws SQLException {
        ArrayList<ProgramaEducativo> programas = new ArrayList<>();
        Connection conn = ConexionDB.abrirConexionBD();
        
        if (conn != null) {
            String sql = "SELECT nrc, nombre FROM programa_educativo ORDER BY nombre ASC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                ProgramaEducativo programa = new ProgramaEducativo();
                programa.setNrc(rs.getInt("nrc"));
                programa.setNombre(rs.getString("nombre"));
                programas.add(programa);
            }
            conn.close();
        }
        return programas;
    }
}