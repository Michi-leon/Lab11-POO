package Model;

import java.util.ArrayList;

public class Venta {
    private ArrayList<DetalleVenta> detalles;

    public Venta() {
        detalles = new ArrayList<>();
    }

    public void agregarDetalle(DetalleVenta detalle) {
        if (detalle == null) {
            System.out.println("El detalle no puede ser nulo.");
            return;
        }
        detalles.add(detalle);
    }

    public boolean eliminarDetalle(int idDetalle) {
        for (int i = 0; i < detalles.size(); i++) {
            if (detalles.get(i).getIdDetalle() == idDetalle) {
                detalles.remove(i);
                return true;
            }
        }
        return false;
    }

    public double calcularTotal() {
        double total = 0;

        for (DetalleVenta detalle : detalles) {
            total += detalle.calcularSubtotal();
        }

        return total;
    }

    public void mostrarDetalles() {
        if (detalles.isEmpty()) {
            System.out.println("La venta no tiene detalles.");
        } else {
            for (DetalleVenta detalle : detalles) {
                System.out.println("ID detalle: "+ detalle.getIdDetalle());
                System.out.println("Cantidad: "+ detalle.getCantidad());
                System.out.println("Precio unitario: "+ detalle.getPrecioUnitario());
                System.out.println("Descuento: "+ detalle.getDescuento());
                System.out.println("Subtotal: "+ detalle.calcularSubtotal());
            }
        }
    }
}