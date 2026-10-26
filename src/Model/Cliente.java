package Model;
import java.time.LocalDate;

public class Cliente extends Persona{
    private int idCliente;
    private String observacion;
    private String fechaRegistro; 

    public Cliente(int idPersona,String nombre,String dni,String telefono, String correo,int idCliente, String observacion,String fechaRegistro){
        super(idPersona,nombre,dni,telefono,correo);
        if (idCliente > 0) {
            this.idCliente = idCliente;
        }else {
            System.out.println("El ID debe ser mayor que cero.");
        }
        this.observacion=observacion;
        this.fechaRegistro=fechaRegistro;
    }
    public Cliente(int idPersona,String nombre,String dni,String telefono, String correo,int idCliente, String observacion){
        super(idPersona,nombre,dni,telefono,correo);
        if (idCliente > 0) {
            this.idCliente = idCliente;
        }else {
            System.out.println("El ID debe ser mayor que cero.");
        }
        this.observacion=observacion;
        this.fechaRegistro = LocalDate.now().toString(); 
    }

    public int getIdCliente(){
        return idCliente;
    }
    public void setObservacion(String observacion){
        this.observacion=observacion;
    }
    public String getObservacion(){
        return observacion;
    }
    public String getFechaRegistro() {
        return fechaRegistro;
    }

    @Override 
    public void imprimirInformacion(){
        super.imprimirInformacion();
        System.out.println("Id: "+idCliente);
        if (observacion!=null) {
            System.out.println("Observacion: "+observacion);
        }
        System.out.println("Fecha de Registro: "+fechaRegistro);
    }

}
