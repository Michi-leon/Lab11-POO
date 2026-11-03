package Dao;

import Model.DetalleVenta;
import Model.DetalleVentaProducto;
import Model.DetalleVentaServicio;
import Model.Producto;
import Model.Servicio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleDao {
    private Connection con;

    public DetalleDao(Connection con) {
        this.con = con;
    }

    //REGISTRAR
    public boolean registrar(int idVenta, DetalleVenta d) {
        String sql = "INSERT INTO detalle_venta (idVenta, idProducto, idServicio, cantidad, precioUnitario, descuento) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVenta);
            
            if (d.mostrarTipo().equals("PRODUCTO")) {
                DetalleVentaProducto dp = (DetalleVentaProducto) d;
                ps.setInt(2, dp.getProducto().getIdProducto());
                ps.setInt(3, 0); 
            } else if (d.mostrarTipo().equals("SERVICIO")) {
                DetalleVentaServicio ds = (DetalleVentaServicio) d;
                ps.setInt(2, 0); 
                ps.setInt(3, ds.getServicio().getIdServicio());
            }
            
            ps.setInt(4, d.getCantidad());
            ps.setDouble(5, d.getPrecioUnitario());
            ps.setDouble(6, d.getDescuento());
            
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Se registro el detalle de la venta correctamente.");
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar el detalle: " + e.getMessage());
            return false;
        }
    }

    //LISTAR
    public List<DetalleVenta> listarPorVenta(int idVenta, ProductoDao productoDao, ServicioDao servicioDao) {
        List<DetalleVenta> lista = new ArrayList<>();
        String sql = "SELECT idDetalle, idProducto, idServicio, cantidad, precioUnitario, descuento FROM detalle_venta WHERE idVenta = ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVenta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idDetalle = rs.getInt("idDetalle");
                    int idProducto = rs.getInt("idProducto");
                    int idServicio = rs.getInt("idServicio");
                    int cantidad = rs.getInt("cantidad");
                    double precioUnitario = rs.getDouble("precioUnitario");
                    double descuento = rs.getDouble("descuento");

                    if (idProducto > 0) {
                        Producto prod = productoDao.buscarPorId(idProducto);
                        lista.add(new DetalleVentaProducto(idDetalle, cantidad, precioUnitario, descuento, prod));
                    } else if (idServicio > 0) {
                        Servicio serv = servicioDao.buscarPorId(idServicio);
                        lista.add(new DetalleVentaServicio(idDetalle, cantidad, precioUnitario, descuento, serv));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar detalles: " + e.getMessage());
        }
        return lista;
    }

    // ACTUALIZAR
    public boolean actualizar(DetalleVenta d) {
        String sql = "UPDATE detalle_venta SET idProducto = ?, idServicio = ?, cantidad = ?, precioUnitario = ?, descuento = ? WHERE idDetalle = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            
            if (d.mostrarTipo().equals("PRODUCTO")) {
                DetalleVentaProducto dp = (DetalleVentaProducto) d;
                ps.setInt(1, dp.getProducto().getIdProducto());
                ps.setInt(2, 0); 
            } else if (d.mostrarTipo().equals("SERVICIO")) {
                DetalleVentaServicio ds = (DetalleVentaServicio) d;
                ps.setInt(1, 0); 
                ps.setInt(2, ds.getServicio().getIdServicio());
            }
            
            ps.setInt(3, d.getCantidad());
            ps.setDouble(4, d.getPrecioUnitario());
            ps.setDouble(5, d.getDescuento());
            ps.setInt(6, d.getIdDetalle()); 
            
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Se actualizo el detalle de la venta correctamente.");
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar el detalle: " + e.getMessage());
            return false;
        }
    }

    //ELIMINAR
    public boolean eliminarPorVenta(int idVenta) {
        String sql = "DELETE FROM detalle_venta WHERE idVenta = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVenta);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar detalles: " + e.getMessage());
            return false;
        }
    }
}