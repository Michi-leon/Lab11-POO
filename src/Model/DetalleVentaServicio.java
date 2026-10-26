package Model;

public class DetalleVentaServicio extends DetalleVenta {
    private Servicio servicio;

    public DetalleVentaServicio(int idDetalle, int cantidad,double precioUnitario, double descuento, Servicio servicio) {
        super(idDetalle, cantidad, precioUnitario, descuento);
        setServicio(servicio);
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        if (servicio != null) {
            this.servicio = servicio;
        } else {
            System.out.println("El servicio no puede ser nulo.");
        }
    }

    @Override
    public double calcularSubtotal() {
        return (getCantidad() * getPrecioUnitario())- getDescuento();
    }
}