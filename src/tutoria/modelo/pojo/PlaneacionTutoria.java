package tutoria.modelo.pojo;

public class PlaneacionTutoria {
    private int idPlaneacionTutoria;
    private int idFechaTutoria;
    private int noPersonal;     
    private int nrc;            
    private String objetivos;   
    private String justificacion; 

    public PlaneacionTutoria() {
    }

    public PlaneacionTutoria(int idFechaTutoria, int noPersonal, int nrc, String objetivos, String justificacion) {
        this.idFechaTutoria = idFechaTutoria;
        this.noPersonal = noPersonal;
        this.nrc = nrc;
        this.objetivos = objetivos;
        this.justificacion = justificacion;
    }

    public int getIdPlaneacionTutoria() { return idPlaneacionTutoria; }
    public void setIdPlaneacionTutoria(int idPlaneacionTutoria) { this.idPlaneacionTutoria = idPlaneacionTutoria; }

    public int getIdFechaTutoria() { return idFechaTutoria; }
    public void setIdFechaTutoria(int idFechaTutoria) { this.idFechaTutoria = idFechaTutoria; }

    public int getNoPersonal() { return noPersonal; }
    public void setNoPersonal(int noPersonal) { this.noPersonal = noPersonal; }

    public int getNrc() { return nrc; }
    public void setNrc(int nrc) { this.nrc = nrc; }

    public String getObjetivos() { return objetivos; }
    public void setObjetivos(String objetivos) { this.objetivos = objetivos; }

    public String getJustificacion() { return justificacion; }
    public void setJustificacion(String justificacion) { this.justificacion = justificacion; }
}