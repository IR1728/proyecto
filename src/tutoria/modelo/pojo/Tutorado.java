package tutoria.modelo.pojo;

public class Tutorado {
    private String matricula;
    private String nombre;
    private String carrera;
    private String evaluado; 
    private String observaciones;
    private String estado;
    private int idAsistencia;
    private String problematica;

    public String getProblematica() {
        return problematica;
    }

    public void setProblematica(String problematica) {
        this.problematica = problematica;
    }
   
  
    private int fkTutor; 
    
    public Tutorado() {}

    public Tutorado(String matricula, String nombre, String carrera, String evaluado) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.carrera = carrera;
        this.evaluado = evaluado;
    }

    public String getEvaluado() {
        return evaluado;
    }

    public void setEvaluado(String evaluado) {
        this.evaluado = evaluado;
    }
 
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }
    
    @Override
    public String toString() {
        return nombre;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstado() {
        return estado; 
    }

    public void setEstado(String estatus) {
        this.estado = estatus;
    }

    public int getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(int idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public int getFkTutor() {
        return fkTutor;
    }

    public void setFkTutor(int fkTutor) {
        this.fkTutor = fkTutor;
    }
}