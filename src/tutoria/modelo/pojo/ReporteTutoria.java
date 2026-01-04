package tutoria.modelo.pojo;

public class ReporteTutoria {
    
    private int idReporteTutoria;
    private String descripcion;
    private String comentariosGenerales;
    private String estatus;
    private int idFechaTutoria;
    private int noPersonal;
    
    // Atributo auxiliar para mostrar el nombre del periodo en tablas
    private String periodoInfo; 

    public ReporteTutoria() {
    }

    public int getIdReporteTutoria() { return idReporteTutoria; }
    public void setIdReporteTutoria(int idReporteTutoria) { this.idReporteTutoria = idReporteTutoria; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getComentariosGenerales() { return comentariosGenerales; }
    public void setComentariosGenerales(String comentariosGenerales) { this.comentariosGenerales = comentariosGenerales; }

    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }

    public int getIdFechaTutoria() { return idFechaTutoria; }
    public void setIdFechaTutoria(int idFechaTutoria) { this.idFechaTutoria = idFechaTutoria; }

    public int getNoPersonal() { return noPersonal; }
    public void setNoPersonal(int noPersonal) { this.noPersonal = noPersonal; }

    public String getPeriodoInfo() { return periodoInfo; }
    public void setPeriodoInfo(String periodoInfo) { this.periodoInfo = periodoInfo; }
    
    @Override
    public String toString() {
        return "Reporte: " + descripcion + " (" + estatus + ")";
    }
}