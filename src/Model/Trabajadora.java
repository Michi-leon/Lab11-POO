package Model;

public class Trabajadora extends Persona{
    private String especialidad;
    public Trabajadora(int idPersona,String nombre,String dni,String telefono, String correo, String especialidad){
        super(idPersona,nombre,dni,telefono,correo);
        this.especialidad = especialidad;
    }
    public String getEspecialidad(){
        return especialidad;
    }
    public void setEspecialidad(String especialidad){
        this.especialidad = especialidad;
    }

    @Override
    public void imprimirInformacion(){
        super.imprimirInformacion();
        System.out.println("Especialidad: " + especialidad);
    }
}
