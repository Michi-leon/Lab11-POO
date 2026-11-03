package Model;

public class Servicio{
    private int idServicio;
    private String nombre;
    private String descripcion;
    private double precio;
    private int duracion;

    public Servicio(int idServicio, String nombre, String descripcion,
                    double precio, int duracion) {

        setIdServicio(idServicio);
        setNombre(nombre);
        setDescripcion(descripcion);
        setPrecio(precio);
        setDuracion(duracion);
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        if (idServicio > 0) {
            this.idServicio = idServicio;
        } else {
            System.out.println("El ID debe ser mayor que cero.");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.equals("")) {
            this.nombre = nombre;
        } else {
            System.out.println("El nombre no puede estar vacio");
        }
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio >= 0) {
            this.precio = precio;
        } else {
            System.out.println("El precio no puede ser negativo");
        }
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        if (duracion > 0) {
            this.duracion = duracion;
        } else {
            System.out.println("La duración debe ser mayor que cero");
        }
    }

    public void imprimirInformacion(){
        System.out.println("ID Servicio: " + idServicio);
        System.out.println("Nombre: " + nombre);
        System.out.println("Descripcion: " + descripcion);
        System.out.println("Precio: " + precio);
        System.out.println("Duracion: " + duracion + " minutos");
    }
}