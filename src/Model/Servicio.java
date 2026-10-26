public class Servicio {
    private int idServicio;
    private String nombre;
    private double precioHabitual;

    public Servicio(int idServicio, String nombre,double precioHabitual) {
        setIdServicio(idServicio);
        setNombre(nombre);
        setPrecioHabitual(precioHabitual);
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        if (idServicio > 0) {
            this.idServicio = idServicio;
        } else {
            System.out.println("El ID del servicio debe ser mayor que cero.");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.equals("")) {
            this.nombre = nombre;
        } else {
            System.out.println("El nombre no puede estar vacio.");
        }
    }

    public double getPrecioHabitual() {
        return precioHabitual;
    }

    public void setPrecioHabitual(double precioHabitual) {
        if (precioHabitual >= 0) {
            this.precioHabitual = precioHabitual;
        } else {
            System.out.println("El precio no puede ser negativo.");
        }
    }
}