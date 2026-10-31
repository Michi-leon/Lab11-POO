package Dao;

import Model.Detalle;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleDao {
    private Connection con;

    public DetalleDao(Connection con) {
        this.con = con;
    }

    public void registrar(Detalle d) {
        String sql = "INSERT INTO detalle_venta (idVenta, idProducto, cantidad, precioUnitario, descuento) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, d.getIdVenta());
            ps.setInt(2, d.getIdProducto());
            ps.setInt(3, d.getCantidad());
            ps.setDouble(4, d.getPrecioUnitario());
            ps.setDouble(5, d.getDescuento());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al registrar detalle: " + e.getMessage());
        }
    }

    public List<Detalle> listarPorVenta(int idVenta) {
        List<Detalle> lista = new ArrayList<>();
        String sql = "SELECT idDetalle, idVenta, idProducto, cantidad, precioUnitario, descuento "
                + "FROM detalle_venta WHERE idVenta = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVenta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Detalle(rs.getInt("idDetalle"), rs.getInt("idVenta"),
                            rs.getInt("idProducto"), rs.getInt("cantidad"),
                            rs.getDouble("precioUnitario"), rs.getDouble("descuento")));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar detalles: " + e.getMessage());
        }
        return lista;
    }

    public Detalle buscarPorId(int id) {
        String sql = "SELECT idDetalle, idVenta, idProducto, cantidad, precioUnitario, descuento "
                + "FROM detalle_venta WHERE idDetalle = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Detalle(rs.getInt("idDetalle"), rs.getInt("idVenta"),
                            rs.getInt("idProducto"), rs.getInt("cantidad"),
                            rs.getDouble("precioUnitario"), rs.getDouble("descuento"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar detalle: " + e.getMessage());
        }
        return null;
    }

    public void actualizar(Detalle d) {
        String sql = "UPDATE detalle_venta SET idVenta = ?, idProducto = ?, cantidad = ?, "
                + "precioUnitario = ?, descuento = ? WHERE idDetalle = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, d.getIdVenta());
            ps.setInt(2, d.getIdProducto());
            ps.setInt(3, d.getCantidad());
            ps.setDouble(4, d.getPrecioUnitario());
            ps.setDouble(5, d.getDescuento());
            ps.setInt(6, d.getIdDetalle());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar detalle: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM detalle_venta WHERE idDetalle = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar detalle: " + e.getMessage());
        }
    }
}
