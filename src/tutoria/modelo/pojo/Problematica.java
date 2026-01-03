package tutoria.modelo.pojo;

public class Problematica {
    
    private int idProblematica;
    private String titulo;
    private String descripcion;
    private String fechaRegistro; // Lo manejaremos como String para facilitar visualización en Tabla
    private String matricula;     // ID del alumno afectado

    public Problematica() {
    }

    public Problematica(String titulo, String descripcion, String matricula) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.matricula = matricula;
    }

    // --- Getters y Setters ---

    public int getIdProblematica() {
        return idProblematica;
    }

    public void setIdProblematica(int idProblematica) {
        this.idProblematica = idProblematica;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    
    @Override
    public String toString() {
        return titulo; 
    }
}