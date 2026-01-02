/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tutoria.modelo.pojo;

/**
 *
 * @author jiran
 */
public class Horario {
    int idHorario;
    String fecha;
    String  modalidad;
    String  hora;
    String  lugar;
    int numeroPersonal;

    public int getIdHorario() {
        return idHorario;
    }

    public String getFecha() {
        return fecha;
    }

    public String getModalidad() {
        return modalidad;
    }

    public String getHora() {
        return hora;
    }

    public String getLugar() {
        return lugar;
    }

    public int getNumeroPersonal() {
        return numeroPersonal;
    }

    public Horario(int idHorario, String fecha, String modalidad, String hora, String lugar, int numeroPersonal) {
        this.idHorario = idHorario;
        this.fecha = fecha;
        this.modalidad = modalidad;
        this.hora = hora;
        this.lugar = lugar;
        this.numeroPersonal = numeroPersonal;
    }


    
}
