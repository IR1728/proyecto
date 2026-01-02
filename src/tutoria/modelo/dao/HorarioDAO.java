/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tutoria.modelo.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import tutoria.modelo.pojo.Horario;

/**
 *
 * @author jiran
 */
public class HorarioDAO {

    public static boolean registrarHorario(Horario horario, Connection conexionBD) throws SQLException {
        if (conexionBD != null) {
            String consulta = "INSERT INTO horario (fecha, hora, modalidad, lugar,numeroPersonal) "
                            + "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, horario.getFecha());     
            sentencia.setString(2, horario.getHora());
            sentencia.setString(3, horario.getModalidad());
            sentencia.setString(4, horario.getLugar());
            sentencia.setInt(5, horario.getNumeroPersonal());   
            int filasAfectadas = sentencia.executeUpdate();
            return filasAfectadas > 0;
        }
        throw new SQLException("No hay conexión a la base de datos");
    }
    
    public static boolean modificarHorario(Horario horario, Connection conexionBD) throws SQLException {
        if (conexionBD != null) {
            System.out.println("llegue 2");
            String consulta = "UPDATE horario SET fecha = ?, hora = ?, modalidad = ?, lugar = ?, numeroPersonal = ? "
                            + "WHERE idHorario = ?";
System.out.println("horario"+horario.toString()+horario.getHora()+horario.getLugar()+horario.getFecha()+horario.getModalidad()+horario.getIdHorario());
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);

            sentencia.setString(1, horario.getFecha());      
            sentencia.setString(2, horario.getHora());
            sentencia.setString(3, horario.getModalidad());
            sentencia.setString(4, horario.getLugar());
            sentencia.setInt(5, horario.getNumeroPersonal());   
            sentencia.setInt(6, horario.getIdHorario());

            int filasAfectadas = sentencia.executeUpdate();
            System.out.println("filas afectadas"+filasAfectadas);
            return filasAfectadas > 0;
        }
        throw new SQLException("No hay conexión a la base de datos");
    }
    
    public static ArrayList<Horario> obtenerDatosHorario(int numeroPersonal, Connection conexionBD) throws SQLException {
        
        ArrayList<Horario> listaHorarios = new ArrayList<>();
        
        if (conexionBD != null){
            
            String consulta = "SELECT idHorario, fecha, hora, modalidad, lugar, numeroPersonal "
                            + "FROM horario "
                            + "WHERE numeroPersonal = ?";

            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);

            sentencia.setInt(1, numeroPersonal);

            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Horario horario = new Horario(resultado.getInt("idHorario"),resultado.getString("fecha"),resultado.getString("modalidad"),resultado.getString("hora"),resultado.getString("lugar"),resultado.getInt("numeroPersonal"));
                listaHorarios.add(horario);
            }
        
            resultado.close();
            sentencia.close();

            return listaHorarios;
            
        }
        
        throw new SQLException("No hay conexión a la base de datos");
    }
}  