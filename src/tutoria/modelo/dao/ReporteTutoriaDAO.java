package tutoria.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import tutoria.modelo.ConexionDB;
import tutoria.modelo.pojo.ReporteTutoria;

public class ReporteTutoriaDAO {

  
    public static boolean registrarReporte(ReporteTutoria reporte) throws SQLException {
        boolean resultado = false;
        Connection conn = ConexionDB.abrirConexionBD();
        
        if (conn != null) {
            String sql = "INSERT INTO reportetutoria (descripcion, comentariosGenerales, estatus, idFechaTutoria, noPersonal) "
                       + "VALUES (?, ?, ?, ?, ?)";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, reporte.getDescripcion());
            ps.setString(2, reporte.getComentariosGenerales());
            ps.setString(3, "Guardado"); 
            ps.setInt(4, reporte.getIdFechaTutoria());
            ps.setInt(5, reporte.getNoPersonal());
            
            int filas = ps.executeUpdate();
            resultado = (filas > 0);
            
            conn.close();
        }
        return resultado;
    }

    
    public static ArrayList<ReporteTutoria> obtenerReportesPorTutor(int noPersonal) throws SQLException {
        ArrayList<ReporteTutoria> lista = new ArrayList<>();
        Connection conn = ConexionDB.abrirConexionBD();
        
        if (conn != null) {
            String sql = "SELECT r.idReporteTutoria, r.descripcion, r.comentariosGenerales, r.estatus, r.idFechaTutoria, "
                       + "CONCAT(f.periodo, ' - Sesión ', f.numeroSesion) as periodoInfo "
                       + "FROM reportetutoria r "
                       + "INNER JOIN fechatutoria f ON r.idFechaTutoria = f.idFechaTutoria "
                       + "WHERE r.noPersonal = ?";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, noPersonal);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReporteTutoria r = new ReporteTutoria();
                r.setIdReporteTutoria(rs.getInt("idReporteTutoria"));
                r.setDescripcion(rs.getString("descripcion"));
                r.setComentariosGenerales(rs.getString("comentariosGenerales"));
                r.setEstatus(rs.getString("estatus"));
                r.setIdFechaTutoria(rs.getInt("idFechaTutoria"));
                r.setPeriodoInfo(rs.getString("periodoInfo")); // Dato auxiliar
                r.setNoPersonal(noPersonal);
                
                lista.add(r);
            }
            conn.close();
        }
        return lista;
    }
    

    public static boolean comprobarExistenciaReporte(int idFecha, int noPersonal) throws SQLException {
        boolean existe = false;
        Connection conn = ConexionDB.abrirConexionBD();
        if(conn != null){
            String sql = "SELECT COUNT(*) FROM reportetutoria WHERE idFechaTutoria = ? AND noPersonal = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idFecha);
            ps.setInt(2, noPersonal);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                existe = (rs.getInt(1) > 0);
            }
            conn.close();
        }
        return existe;
    }
}