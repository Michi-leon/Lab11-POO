public class DetalleVentaProducto extends DetalleVenta {
    private Producto producto;

    public DetalleVentaProducto(int idDetalle, int cantidad,double precioUnitario, double descuento, Producto producto) {
        super(idDetalle, cantidad, precioUnitario, descuento);
        setProducto(producto);
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        if (producto != null) {
            this.producto = producto;
        } else {
            System.out.println("El producto no puede ser nulo.");
        }
    }

    @Override
    public double calcularSubtotal() {
        return (getCantidad() * getPrecioUnitario())- getDescuento();
    }
}