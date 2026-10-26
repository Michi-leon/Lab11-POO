package Model;

public abstract class DetalleVenta {
    private int idDetalle;
    private int cantidad;
    private double precioUnitario;
    private double descuento;

    public DetalleVenta(int idDetalle, int cantidad,double precioUnitario, double descuento) {
        setIdDetalle(idDetalle);
        setCantidad(cantidad);
        setPrecioUnitario(precioUnitario);
        setDescuento(descuento);
    }

    public abstract double calcularSubtotal();

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        if (idDetalle > 0) {
            this.idDetalle = idDetalle;
        } else {
            System.out.println("El ID del detalle debe ser mayor que cero.");
        }
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        if (cantidad > 0) {
            this.cantidad = cantidad;
        } else {
            System.out.println("La cantidad debe ser mayor que cero.");
        }
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        if (precioUnitario >= 0) {
            this.precioUnitario = precioUnitario;
        } else {
            System.out.println("El precio unitario no puede ser negativo.");
        }
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        if (descuento >= 0 &&
            descuento <= cantidad * precioUnitario) {
            this.descuento = descuento;
        } else {
            System.out.println("El descuento no es valido.");
        }
    }
}