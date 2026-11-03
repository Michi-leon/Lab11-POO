package Dao;

import Model.Producto;
import Model.Insumo;
import Model.ProductoVenta;
import Model.ProductoMixto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDao {
    private Connection connection;

    public ProductoDao(Connection connection) {
        this.connection = connection;
    }

    // 1. REGISTRAR
    public boolean registrar(Producto p) {
        String queryMadre = "INSERT INTO productos (id_producto, nombre, precio_compra, stock_actual, stock_minimo) VALUES (?, ?, ?, ?, ?)";
        int filasMadre = 0;
        int filasHija = 0;
        
        try {
            //No guardado automatico
            connection.setAutoCommit(false);

            // Datos Madre
            PreparedStatement psP = connection.prepareStatement(queryMadre);
            psP.setInt(1, p.getIdProducto());
            psP.setString(2, p.getNombre());
            psP.setFloat(3, p.getPrecioCompra());
            psP.setInt(4, p.getStockActual());
            psP.setInt(5, p.getStockMinimo());
            filasMadre = psP.executeUpdate();
            psP.close();

            //Insertar Datos Hijas
            if (p.mostrarTipo().equals("Insumo")) {
                String queryInsumo = "INSERT INTO productos_insumo (id_producto) VALUES (?)";
                PreparedStatement psI = connection.prepareStatement(queryInsumo);
                psI.setInt(1, p.getIdProducto());
                filasHija = psI.executeUpdate();
                psI.close();
                
            } else if (p.mostrarTipo().equals("Producto de venta")) {
                String queryVenta = "INSERT INTO productos_venta (id_producto, precio_venta) VALUES (?, ?)";
                PreparedStatement psV = connection.prepareStatement(queryVenta);
                psV.setInt(1, p.getIdProducto());
                psV.setFloat(2, ((ProductoVenta) p).getPrecioVenta());
                filasHija = psV.executeUpdate();
                psV.close();
                
            } else if (p.mostrarTipo().equals("producto mixto")) {
                String queryMixto = "INSERT INTO productos_mixtos (id_producto, precio_venta) VALUES (?, ?)";
                PreparedStatement psM = connection.prepareStatement(queryMixto);
                psM.setInt(1, p.getIdProducto());
                psM.setFloat(2, ((ProductoMixto) p).getPrecioVenta());
                filasHija = psM.executeUpdate();
                psM.close();
            }

            if (filasMadre > 0 && filasHija > 0) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error en INSERT de producto compuesto: " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Error al ejecutar rollback: " + ex.getMessage());
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Error al reestablecer autoCommit: " + e.getMessage());
            }
        }
    }

    // 2. LISTAR 
    public List<Producto> listarProductos() {
    List<Producto> lista = new ArrayList<>();
        String query = "SELECT p.*, pv.precio_venta AS pv_precio, pm.precio_venta AS pm_precio, pi.id_producto AS is_insumo " +
                    "FROM productos p " +
                    "LEFT JOIN productos_venta pv ON p.id_producto = pv.id_producto " +
                    "LEFT JOIN productos_mixtos pm ON p.id_producto = pm.id_producto " +
                    "LEFT JOIN productos_insumo pi ON p.id_producto = pi.id_producto";

        try (Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id_producto");
                String nombre = rs.getString("nombre");
                float precioCompra = rs.getFloat("precio_compra");
                int stockActual = rs.getInt("stock_actual");
                int stockMinimo = rs.getInt("stock_minimo");

                if (rs.getObject("pv_precio") != null) {
                    lista.add(new ProductoVenta(id, nombre, precioCompra, stockActual, stockMinimo, rs.getFloat("pv_precio")));
                } else if (rs.getObject("pm_precio") != null) {
                    lista.add(new ProductoMixto(id, nombre, precioCompra, stockActual, stockMinimo, rs.getFloat("pm_precio")));
                } else {
                    lista.add(new Insumo(id, nombre, precioCompra, stockActual, stockMinimo));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }
        return lista;
    }
    // BUSCAR POR ID 
    public Producto buscarPorId(int idBuscar) {
        String query = "SELECT p.*, pv.precio_venta AS pv_precio, pm.precio_venta AS pm_precio " +
                    "FROM productos p " +
                    "LEFT JOIN productos_venta pv ON p.id_producto = pv.id_producto " +
                    "LEFT JOIN productos_mixtos pm ON p.id_producto = pm.id_producto " +
                    "WHERE p.id_producto = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idBuscar);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    float precioCompra = rs.getFloat("precio_compra");
                    int stockActual = rs.getInt("stock_actual");
                    int stockMinimo = rs.getInt("stock_minimo");

                    if (rs.getObject("pv_precio") != null) {
                        return new ProductoVenta(idBuscar, nombre, precioCompra, stockActual, stockMinimo, rs.getFloat("pv_precio"));
                    } else if (rs.getObject("pm_precio") != null) {
                        return new ProductoMixto(idBuscar, nombre, precioCompra, stockActual, stockMinimo, rs.getFloat("pm_precio"));
                    } else {
                        return new Insumo(idBuscar, nombre, precioCompra, stockActual, stockMinimo);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar producto: " + e.getMessage());
        }
        return null;
    }

    // ACTUALIZAR
    public boolean actualizar(Producto p) {
        String queryMadre = "UPDATE productos SET nombre = ?, precio_compra = ?, stock_actual = ?, stock_minimo = ? WHERE id_producto = ?";
        int filasMadre = 0;
        int filasHija = 0;
        
        try {
            connection.setAutoCommit(false);

            PreparedStatement psP = connection.prepareStatement(queryMadre);
            psP.setString(1, p.getNombre());
            psP.setFloat(2, p.getPrecioCompra());
            psP.setInt(3, p.getStockActual());
            psP.setInt(4, p.getStockMinimo());
            psP.setInt(5, p.getIdProducto());
            filasMadre = psP.executeUpdate();
            psP.close();

            if (p.mostrarTipo().equals("Producto de venta")) {
                String queryVenta = "UPDATE productos_venta SET precio_venta = ? WHERE id_producto = ?";
                PreparedStatement psV = connection.prepareStatement(queryVenta);
                psV.setFloat(1, ((ProductoVenta) p).getPrecioVenta());
                psV.setInt(2, p.getIdProducto());
                filasHija = psV.executeUpdate();
                psV.close();
            } else if (p.mostrarTipo().equals("producto mixto")) {
                String queryMixto = "UPDATE productos_mixtos SET precio_venta = ? WHERE id_producto = ?";
                PreparedStatement psM = connection.prepareStatement(queryMixto);
                psM.setFloat(1, ((ProductoMixto) p).getPrecioVenta());
                psM.setInt(2, p.getIdProducto());
                filasHija = psM.executeUpdate();
                psM.close();
            } else {
                filasHija = 1; 
            }

            if (filasMadre > 0 && filasHija > 0) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
            try { connection.rollback(); } catch (SQLException ex) { System.out.println(ex.getMessage()); }
            return false;
        } finally {
            try { connection.setAutoCommit(true); } catch (SQLException e) { System.out.println(e.getMessage()); }
        }
    }

    // ELIMINAR
    public boolean eliminar(int idEliminar) {
        String query = "DELETE FROM productos WHERE id_producto = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, idEliminar);
            int filas = ps.executeUpdate();
            ps.close();
            return filas > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }
}