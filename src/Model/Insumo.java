package Model;

public class Insumo extends Producto {

    public Insumo(int idProducto, String nombre, float precioCompra,  int stockActual, int stockMinimo) {
        super(idProducto, nombre, precioCompra, stockActual, stockMinimo);
    }

    @Override
    public String mostrarTipo() {
        return "Insumo";
    }

    @Override
    public void imprimirInformacion() {
        super.imprimirInformacion();
        System.out.println("Tipo: " + mostrarTipo());
    }
}