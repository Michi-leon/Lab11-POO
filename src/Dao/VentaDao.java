package Dao;

import Model.Venta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDao {
    private Connection con;

    public VentaDao(Connection con) {
        this.con = con;
    }

    public void registrar(Venta v) {
        String sql = "INSERT INTO venta (idCliente, fecha, total) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, v.getIdCliente());
            ps.setDate(2, v.getFecha());
            ps.setDouble(3, v.getTotal());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al registrar venta: " + e.getMessage());
        }
    }

    public List<Venta> listarVentas() {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT idVenta, idCliente, fecha, total FROM venta";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Venta(rs.getInt("idVenta"), rs.getInt("idCliente"),
                        rs.getDate("fecha"), rs.getDouble("total")));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar ventas: " + e.getMessage());
        }
        return lista;
    }

    public Venta buscarPorId(int id) {
        String sql = "SELECT idVenta, idCliente, fecha, total FROM venta WHERE idVenta = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Venta(rs.getInt("idVenta"), rs.getInt("idCliente"),
                            rs.getDate("fecha"), rs.getDouble("total"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar venta: " + e.getMessage());
        }
        return null;
    }

    public void actualizar(Venta v) {
        String sql = "UPDATE venta SET idCliente = ?, fecha = ?, total = ? WHERE idVenta = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, v.getIdCliente());
            ps.setDate(2, v.getFecha());
            ps.setDouble(3, v.getTotal());
            ps.setInt(4, v.getIdVenta());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar venta: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM venta WHERE idVenta = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar venta: " + e.getMessage());
        }
    }
}
