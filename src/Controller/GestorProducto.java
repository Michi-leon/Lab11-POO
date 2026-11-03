package Controller;

import Dao.ProductoDao;
import Model.Producto;
import Model.ProductoMixto;
import Model.ProductoVenta;
import java.util.List;

public class GestorProducto {
    private ProductoDao productoDao;

    public GestorProducto(ProductoDao productoDao) {
        this.productoDao = productoDao;
    }

    // REGISTRAR
    public boolean registrarProducto(Producto producto) {
        if (producto == null) {
            System.out.println("El producto no puede ser null");
            return false;
        }
        if (producto.getIdProducto() <= 0) {
            System.out.println("El ID debe ser mayor que cero.");
            return false;
        }
        if (productoDao.buscarPorId(producto.getIdProducto()) != null) {
            System.out.println("El ID ya está registrado en la base de datos.");
            return false;
        }
        
        boolean exito = productoDao.registrar(producto);
        if (exito) {
            System.out.println("Producto registrado correctamente en el sistema.");
        }
        return exito;
    }

    // 2. LISTAR PRODUCTOS
    public void listarProductos() {
        List<Producto> lista = productoDao.listarProductos();
        if (lista.isEmpty()) {
            System.out.println("No se ha registrado ningún producto en la base de datos.");
        } else {
            for (Producto productoActual : lista) {
                productoActual.imprimirInformacion();
                System.out.println();
            }
        }
    }

    // 3. BUSCAR PRODUCTO
    public Producto buscarProducto(int id) {
        Producto productoEncontrado = productoDao.buscarPorId(id);
        if (productoEncontrado == null) {
            System.out.println("No se encontró el producto con ID: " + id);
        }
        return productoEncontrado;
    }

    // 4. ELIMINAR PRODUCTO
    public boolean eliminarProducto(int id) {
        Producto producto = buscarProducto(id);
        if (producto == null) {
            return false;
        }
        boolean exito = productoDao.eliminar(id);
        if (exito) {
            System.out.println("Producto eliminado con éxito de la base de datos.");
        }
        return exito;
    }

    // 5. MODIFICAR NOMBRE
    public boolean modificarNombre(int id, String nuevoNombre) {
        Producto producto = buscarProducto(id);
        if (producto == null) return false;

        if (nuevoNombre == null || nuevoNombre.equals("")) {
            System.out.println("El nombre no puede estar vacío.");
            return false;
        }

        producto.setNombre(nuevoNombre);
        productoDao.actualizar(producto);
        System.out.println("El nombre se ha modificado con éxito.");
        return true;
    }

    // 6. MODIFICAR PRECIO COMPRA
    public boolean modificarPrecioCompra(int id, float precioCompra) {
        Producto producto = buscarProducto(id);
        if (producto == null) return false;

        if (precioCompra < 0) {
            System.out.println("El precio de compra no puede ser negativo.");
            return false;
        }

        producto.setPrecioCompra(precioCompra);
        productoDao.actualizar(producto);
        System.out.println("El precio de compra se ha modificado con éxito.");
        return true;
    }

    // 7. MODIFICAR STOCK ACTUAL
    public boolean modificarStockActual(int id, int stockActual) {
        Producto producto = buscarProducto(id);
        if (producto == null) return false;

        if (stockActual < 0) {
            System.out.println("El stock no puede ser negativo.");
            return false;
        }

        producto.setStockActual(stockActual);
        productoDao.actualizar(producto);
        System.out.println("El stock actual se ha actualizado con éxito.");
        return true;
    }

    // 8. MODIFICAR STOCK MINIMO
    public boolean modificarStockMinimo(int id, int stockMinimo) {
        Producto producto = buscarProducto(id);
        if (producto == null) return false;

        if (stockMinimo < 0) {
            System.out.println("El stock mínimo no puede ser negativo.");
            return false;
        }

        producto.setStockMinimo(stockMinimo);
        productoDao.actualizar(producto);
        System.out.println("El stock mínimo se ha actualizado con éxito.");
        return true;
    }

    // 9. MODIFICAR PRECIO VENTA PRODUCTO
    public boolean modificarPrecioVentaProducto(ProductoVenta producto, float precioVenta) {
        if (producto == null) return false;

        if (precioVenta < 0) {
            System.out.println("El precio de venta no puede ser negativo.");
            return false;
        }

        producto.setPrecioVenta(precioVenta);
        productoDao.actualizar(producto);
        System.out.println("Precio de venta del producto modificado con éxito.");
        return true;
    }

    // 10. MODIFICAR PRECIO VENTA MIXTO
    public boolean modificarPrecioVentaMixto(ProductoMixto producto, float precioVenta) {
        if (producto == null) return false;

        if (precioVenta < 0) {
            System.out.println("El precio de venta no puede ser negativo.");
            return false;
        }

        producto.setPrecioVenta(precioVenta);
        productoDao.actualizar(producto);
        System.out.println("Precio de venta mixto modificado con éxito.");
        return true;
    }

    // 11. AUMENTAR STOCK
    public boolean aumentarStock(int id, int cantidad) {
        Producto producto = buscarProducto(id);
        if (producto == null) return false;

        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor que cero.");
            return false;
        }

        int stockNuevo = producto.getStockActual() + cantidad;
        producto.setStockActual(stockNuevo);
        productoDao.actualizar(producto);
        System.out.println("Stock aumentado con éxito.");
        return true;
    }

    // 12. DISMINUIR STOCK
    public boolean disminuirStock(int id, int cantidad) {
        Producto producto = buscarProducto(id);
        if (producto == null) return false;

        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor que cero.");
            return false;
        }

        int stockNuevo = producto.getStockActual() - cantidad;
        if (stockNuevo < 0) {
            System.out.println("El stock no puede quedar negativo.");
            return false;
        }

        producto.setStockActual(stockNuevo);
        productoDao.actualizar(producto);
        System.out.println("Stock disminuido con éxito.");
        return true;
    }

    // 13. LISTAR PRODUCTOS CON STOCK BAJO
    public void listarProductosStockBajo() {
        List<Producto> lista = productoDao.listarProductos();
        boolean hayStockBajo = false;
        
        for (Producto producto : lista) {
            if (producto.tieneStockBajo()) {
                producto.imprimirInformacion();
                System.out.println();
                hayStockBajo = true;
            }
        }
        
        if (!hayStockBajo) {
            System.out.println("Todos los productos tienen niveles estables de stock.");
        }
    }
}
