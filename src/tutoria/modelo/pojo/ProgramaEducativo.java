package tutoria.modelo.pojo;

public class ProgramaEducativo {
    
    private int nrc;
    private String nombre;

    public ProgramaEducativo() {
    }

    public ProgramaEducativo(int nrc, String nombre) {
        this.nrc = nrc;
        this.nombre = nombre;
    }

    public int getNrc() {
        return nrc;
    }

    public void setNrc(int nrc) {
        this.nrc = nrc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return nombre + " (" + nrc + ")";
    }
}