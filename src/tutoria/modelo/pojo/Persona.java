package tutoria.modelo.pojo;

public abstract class Persona {
  private String nombre;
  private String apellidoPaterno;
  private String apellidoMaterno;
  private String fechaNacimiento;

  public String getNombre() {
    return nombre;
  }

  public String getApellidoPaterno() {
    return apellidoPaterno;
  }

  public String getApellidoMaterno() {
    return apellidoMaterno;
  }
  
  public String getNombreCompleto() {
    return getNombre() + getApellidoPaterno() + getApellidoMaterno();
  }

  public String getFechaNacimiento() {
    return fechaNacimiento;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setApellidoPaterno(String apellidoPaterno) {
    this.apellidoPaterno = apellidoPaterno;
  }

  public void setApellidoMaterno(String apellidoMaterno) {
    this.apellidoMaterno = apellidoMaterno;
  }

  public void setFechaNacimiento(String fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
  }
}
