package tutoria.modelo.pojo;

public class FechaTutoria {
    
    private int idFechaTutoria;
    private String periodo;
    private int numeroSesion;
    private String fechaInicio;
    private String fechaFin;

    public FechaTutoria() {
    }

    public FechaTutoria(String periodo, int numeroSesion, String fechaInicio, String fechaFin) {
        this.periodo = periodo;
        this.numeroSesion = numeroSesion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getIdFechaTutoria() {
        return idFechaTutoria;
    }

    public void setIdFechaTutoria(int idFechaTutoria) {
        this.idFechaTutoria = idFechaTutoria;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public int getNumeroSesion() {
        return numeroSesion;
    }

    public void setNumeroSesion(int numeroSesion) {
        this.numeroSesion = numeroSesion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    // Este toString es útil si vas a cargar estos objetos en un ComboBox más adelante
    @Override
    public String toString() {
        return periodo + " - Sesión " + numeroSesion;
    }
}