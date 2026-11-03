package Model;

public class ProductoVenta extends Producto {
    private float precioVenta;

    public ProductoVenta(int idProducto, String nombre, float precioCompra, int stockActual, int stockMinimo, float precioVenta) {
        super(idProducto, nombre, precioCompra, stockActual, stockMinimo);
        setPrecioVenta(precioVenta);
    }

    public float getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(float precioVenta) {
        if (precioVenta >= 0) {
            this.precioVenta=precioVenta;
        } else {
            System.out.println("El precio de venta no puede ser negativo.");
        }
    }

    @Override
    public String mostrarTipo() {
        return "Producto de venta";
    }

    @Override
    public void imprimirInformacion() {
        super.imprimirInformacion();
        System.out.println("Tipo: " + mostrarTipo());
        System.out.println("Precio venta: " + precioVenta);
    }
}