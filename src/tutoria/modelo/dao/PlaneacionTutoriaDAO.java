package tutoria.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import tutoria.modelo.ConexionDB;
import tutoria.modelo.pojo.FechaTutoria;
import tutoria.modelo.pojo.PlaneacionTutoria;

public class PlaneacionTutoriaDAO {

    public static ArrayList<FechaTutoria> obtenerFechasDisponibles() throws SQLException {
        ArrayList<FechaTutoria> fechas = new ArrayList<>();
        Connection con = ConexionDB.abrirConexionBD();
        if (con != null) {
            try {
                String sql = "SELECT idFechaTutoria, periodo, numeroSesion FROM fechatutoria";
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    FechaTutoria ft = new FechaTutoria();
                    ft.setIdFechaTutoria(rs.getInt("idFechaTutoria"));
                    ft.setPeriodo(rs.getString("periodo"));
                    ft.setNumeroSesion(rs.getInt("numeroSesion"));
                    fechas.add(ft);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw ex;
            } finally {
                ConexionDB.cerrarConexionBD();
            }
        }
        return fechas;
    }

    public static PlaneacionTutoria obtenerPlaneacionPorFecha(int idFechaTutoria) {
        PlaneacionTutoria planeacion = null;
        Connection con = ConexionDB.abrirConexionBD();
        if (con != null) {
            try {
                String sql = "SELECT * FROM planeacion_tutoria WHERE idFechaTutoria = ?";
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, idFechaTutoria);
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    planeacion = new PlaneacionTutoria();
                    planeacion.setIdPlaneacionTutoria(rs.getInt("idPlaneacionTutoria"));
                    planeacion.setIdFechaTutoria(rs.getInt("idFechaTutoria"));
                    planeacion.setNoPersonal(rs.getInt("noPersonal"));
                    planeacion.setNrc(rs.getInt("nrc"));
                    planeacion.setObjetivos(rs.getString("objetivos"));
                    planeacion.setJustificacion(rs.getString("justificacion"));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                ConexionDB.cerrarConexionBD();
            }
        }
        return planeacion;
    }

    public static boolean registrarPlaneacion(PlaneacionTutoria planeacion) {
        boolean resultado = false;
        Connection con = ConexionDB.abrirConexionBD();
        if (con != null) {
            try {
                String sql = "INSERT INTO planeacion_tutoria (idFechaTutoria, noPersonal, nrc, objetivos, justificacion) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, planeacion.getIdFechaTutoria());
                pstmt.setInt(2, planeacion.getNoPersonal());
                pstmt.setInt(3, planeacion.getNrc());
                pstmt.setString(4, planeacion.getObjetivos());
                pstmt.setString(5, planeacion.getJustificacion());
                int filasAfectadas = pstmt.executeUpdate();
                resultado = filasAfectadas > 0;
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                ConexionDB.cerrarConexionBD();
            }
        }
        return resultado;
    }

    public static boolean actualizarPlaneacion(PlaneacionTutoria planeacion) {
        boolean resultado = false;
        Connection con = ConexionDB.abrirConexionBD();
        if (con != null) {
            try {
                String sql = "UPDATE planeacion_tutoria SET objetivos = ?, justificacion = ?, noPersonal = ? WHERE idFechaTutoria = ?";
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, planeacion.getObjetivos());
                pstmt.setString(2, planeacion.getJustificacion());
                pstmt.setInt(3, planeacion.getNoPersonal());
                pstmt.setInt(4, planeacion.getIdFechaTutoria());
                
                int filasAfectadas = pstmt.executeUpdate();
                resultado = filasAfectadas > 0;
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                ConexionDB.cerrarConexionBD();
            }
        }
        return resultado;
    }
}