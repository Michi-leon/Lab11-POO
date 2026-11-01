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
        String query = "SELECT id_producto, nombre, precio_compra, stock_actual, stock_minimo FROM productos";
        
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id_producto");
                String nombre = rs.getString("nombre");
                float precioCompra = rs.getFloat("precio_compra");
                int stockActual = rs.getInt("stock_actual");
                int stockMinimo = rs.getInt("stock_minimo");

                // Productos_venta
                String queryVenta = "SELECT precio_venta FROM productos_venta WHERE id_producto = ?";
                PreparedStatement psV = connection.prepareStatement(queryVenta);
                psV.setInt(1, id);
                ResultSet rsV = psV.executeQuery();
                if (rsV.next()) {
                    lista.add(new ProductoVenta(id, nombre, precioCompra, stockActual, stockMinimo, rsV.getFloat("precio_venta")));
                    rsV.close(); psV.close();
                    continue; 
                }
                rsV.close(); psV.close();

                // Productos_mixtos
                String queryMixto = "SELECT precio_venta FROM productos_mixtos WHERE id_producto = ?";
                PreparedStatement psM = connection.prepareStatement(queryMixto);
                psM.setInt(1, id);
                ResultSet rsM = psM.executeQuery();
                if (rsM.next()) {
                    lista.add(new ProductoMixto(id, nombre, precioCompra, stockActual, stockMinimo, rsM.getFloat("precio_venta")));
                    rsM.close(); psM.close();
                    continue;
                }
                rsM.close(); psM.close();

                lista.add(new Insumo(id, nombre, precioCompra, stockActual, stockMinimo));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }
        return lista;
    }
    // 3. BUSCAR POR ID 
    public Producto buscarPorId(int idBuscar) {
        String queryPadre = "SELECT id_producto, nombre, precio_compra, stock_actual, stock_minimo FROM productos WHERE id_producto = ?";
        Producto p = null;
        
        try {
            PreparedStatement ps = connection.prepareStatement(queryPadre);
            ps.setInt(1, idBuscar);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id_producto");
                String nombre = rs.getString("nombre");
                float precioCompra = rs.getFloat("precio_compra");
                int stockActual = rs.getInt("stock_actual");
                int stockMinimo = rs.getInt("stock_minimo");

                // Productos_venta
                String queryVenta = "SELECT precio_venta FROM productos_venta WHERE id_producto = ?";
                PreparedStatement psV = connection.prepareStatement(queryVenta);
                psV.setInt(1, idBuscar);
                ResultSet rsV = psV.executeQuery();
                if (rsV.next()) {
                    p = new ProductoVenta(id, nombre, precioCompra, stockActual, stockMinimo, rsV.getFloat("precio_venta"));
                    rsV.close(); psV.close(); rs.close(); ps.close();
                    return p;
                }
                rsV.close(); psV.close();

                //Productos_mixtos
                String queryMixto = "SELECT precio_venta FROM productos_mixtos WHERE id_producto = ?";
                PreparedStatement psM = connection.prepareStatement(queryMixto);
                psM.setInt(1, idBuscar);
                ResultSet rsM = psM.executeQuery();
                if (rsM.next()) {
                    p = new ProductoMixto(id, nombre, precioCompra, stockActual, stockMinimo, rsM.getFloat("precio_venta"));
                    rsM.close(); psM.close(); rs.close(); ps.close();
                    return p;
                }
                rsM.close(); psM.close();

                //Insumo
                p = new Insumo(id, nombre, precioCompra, stockActual, stockMinimo);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al buscar producto: " + e.getMessage());
        }
        return p;
    }

    // 4. ACTUALIZAR
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

    // 5. ELIMINAR
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