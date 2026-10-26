package Model;

public abstract class Persona {
    private int idPersona;
    private String nombre;
    private String dni;
    private String telefono;
    private String correo;

    public Persona(int idPersona,String nombre,String dni,String telefono, String correo){
        if (idPersona > 0) {
            this.idPersona = idPersona;
        }else {
            System.out.println("El ID debe ser mayor que cero.");
        }
        setNombre(nombre);
        setDni(dni);
        setTelefono(telefono);
        setCorreo(correo);
    }

    public int getIdPersona(){
        return idPersona;
    }

    public void setNombre(String nombre){
        if (nombre != null && !nombre.equals("")) {
            this.nombre = nombre;
        } else {
            System.out.println("El nombre no puede estar vacio.");
        }
    }
    public String getNombre(){
        return nombre;
    }

    public void setDni(String dni){
        if (dni != null && dni.length() == 8) {
            this.dni = dni;
        } else {
            System.out.println("El DNI debe tener 8 dígitos.");
        }
    }
    public String getDni(){
        return dni;
    }

    public void setTelefono(String telefono){
        if (telefono != null && telefono.length() == 9) {
            this.telefono = telefono;
        } else {
            System.out.println("Ingrese los 9 dígitos seguidos, sin espacios ni guiones");
        }
    }
    public String getTelefono(){
        return telefono;
    }

    public void setCorreo(String correo){
        this.correo=correo;
    }
    public String getCorreo(){
        return correo;
    }

    public void imprimirInformacion(){
        System.out.println("ID Persona: "+idPersona);
        System.out.println("Nombre: "+nombre);
        System.out.println("DNI: "+dni);
        System.out.println("Telefono: "+telefono);
        if (correo!=null) {
            System.out.println("Correo: "+correo);
        }
    }

}
