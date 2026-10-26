package Controller;

import Model.Producto;
import

import java.util.HashMap;
import java.util.Map;
public class GestorProducto {
    private Map<Integer,Producto> listaProducto=new HashMap<>();

    public boolean registrarProducto(Producto producto){
        if (producto==null) {
            System.out.println("El producto no puede ser null");
            return false;
        }
        if (producto.getIdProducto() <= 0) {
            System.out.println("El ID debe ser mayor que cero.");
            return false;
        }
        if(listaProducto.containsKey(producto.getIdProducto())) {
            System.out.println("El ide ya esta registrado");
            return false;
        }
        listaProducto.put(producto.getIdProducto(), producto);
        return true;
    }

    public void listarProductos(){
        if (listaProducto.isEmpty()) {
            System.out.println("No se ha registrado ningun producto");
        }else{
            for(Producto productoActual:listaProducto.values()){
                productoActual.imprimirInformacion();
                System.out.println();
            }
        }
    }
    public Producto buscarProducto(int id){
        if (listaProducto.isEmpty()) {
            System.out.println("No hay productos registrados");            
            return null;
        }
        Producto productoEncontrado=listaProducto.get(id);
        if (productoEncontrado==null) {
            System.out.println("No se encontro el producto");
        }
        return productoEncontrado;
    }
    //Modificacion
    public boolean modificarNombre(int id, String nuevoNombre) {
        Producto producto = buscarProducto(id);
        if (producto == null) return false;

        producto.setNombre(nuevoNombre);
        return true;
    }
    public boolean modificarPrecioCompra(int id, float precioCompra) {
        Producto producto = buscarProducto(id);
        if (producto == null) {
            return false;
        }
        if (precioCompra < 0) {
            System.out.println("El precio de compra no puede ser negativo.");
            return false;
        }
        producto.setPrecioCompra(precioCompra);
        return true;
    }
    public boolean modificarStockActual(int id,int stockActual){
        Producto producto=buscarProducto(id);
        if (producto == null) {
            return false;
        }else if (stockActual<0) {
            System.out.println("El stock no puede ser negativo");
            return false;
        }
        producto.setStockActual(stockActual);
        return true;
    }
    public boolean modificarStockMinimo(int id,int stockMinimo){
        Producto producto=buscarProducto(id);
        if (producto == null) {
            return false;
        }else if (stockMinimo<0) {
            System.out.println("El stock no puede ser negativo");
            return false;
        }
        producto.setStockMinimo(stockMinimo);
        return true;
    }
    //Productos Hijas
    public boolean modificarPrecioVentaProducto(ProductoVenta producto, float precioVenta) {
        if (producto == null) {
            return false;
        }
        if (precioVenta < 0) {
            System.out.println("El precio de venta no puede ser negativo.");
            return false;
        }
        producto.setPrecioVenta(precioVenta);
        return true;
    }

    public boolean modificarPrecioVentaMixto(ProductoMixto producto, float precioVenta) {
        if (producto == null) {
            return false;
        }
        if (precioVenta < 0) {
            System.out.println("El precio de venta no puede ser negativo.");
            return false;
        }
        producto.setPrecioVenta(precioVenta);
        return true;
    }

    public  boolean aumentarStock(int id,int cantidad){
        Producto producto=buscarProducto(id);
        if (producto == null) {
            return false;
        }else if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor que cero");
            return false;
        }
        int stockNuevo=producto.getStockActual() + cantidad;
        producto.setStockActual(stockNuevo);
        return true;
    }
    public boolean disminuirStock(int id,int cantidad){
        Producto producto=buscarProducto(id);
        if (producto == null) {
            return false;
        }else if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor que cero");
            return false;
        }
        int stockNuevo=producto.getStockActual() - cantidad;
        if (stockNuevo<0) {
            System.out.println("El stock no puede ser negativo");
            return false;
        }
        producto.setStockActual(stockNuevo);
        return true;
    }

    public boolean eliminarProducto(int id){
        Producto producto=buscarProducto(id);
        if (producto == null) {
            return false;
        }
        listaProducto.remove(id);
        return true;

    }
    public void listarProductosStockBajo() {
        for (Producto producto : listaProducto.values()) {
            if (producto.tieneStockBajo()) {
                producto.imprimirInformacion();
                System.out.println();
            }
        }
    }

}
