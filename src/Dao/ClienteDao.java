package Dao;

import Model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {
    private Connection connection;

    public ClienteDao(Connection connection) {
        this.connection = connection;
    }

    // 1. REGISTRAR:
    public void registrar(Cliente cliente) {
        String queryPersona = "INSERT INTO persona (id_persona, nombre, dni, telefono, correo) VALUES (?, ?, ?, ?, ?)";
        String queryCliente = "INSERT INTO cliente (id_cliente, observacion, fecha_registro) VALUES (?, ?, ?)";
        
        try {
            // Insertar PERSONA
            PreparedStatement psP = connection.prepareStatement(queryPersona);
            psP.setInt(1, cliente.getIdPersona());
            psP.setString(2, cliente.getNombre());
            psP.setString(3, cliente.getDni());
            psP.setString(4, cliente.getTelefono());
            psP.setString(5, cliente.getCorreo());
            psP.executeUpdate();
            psP.close();

            // Insertar CLIENTE
            PreparedStatement psC = connection.prepareStatement(queryCliente);
            psC.setInt(1, cliente.getIdCliente()); // Comparte exactamente el mismo ID
            psC.setString(2, cliente.getObservacion());
            psC.setDate(3, Date.valueOf(cliente.getFechaRegistro()));
            psC.executeUpdate();
            psC.close();

            System.out.println("Cliente registrado");
            System.out.println(" Filas insertadas correctamente en ambas tablas");
        } catch (SQLException e) {
            System.out.println("Error en INSERT de cliente compuesto: " + e.getMessage());
        }
    }
    //2.Listar:
    public List<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        String query = "SELECT p.id_persona, p.nombre, p.dni, p.telefono, p.correo, c.id_cliente, c.observacion, c.fecha_registro FROM cliente c INNER JOIN persona p ON c.id_cliente = p.id_persona";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int idPersona = resultSet.getInt("id_persona");
                String nombre = resultSet.getString("nombre");
                String dni = resultSet.getString("dni");
                String telefono = resultSet.getString("telefono");
                String correo = resultSet.getString("correo");
                int idCliente = resultSet.getInt("id_cliente");
                String observacion = resultSet.getString("observacion");
                String fechaBonita = resultSet.getString("fecha_registro");
                
                Cliente cliente = new Cliente(idPersona, nombre, dni, telefono, correo, idCliente, observacion, fechaBonita);
                lista.add(cliente);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }
        return lista;
    }

    // 3. BUSCAR POR ID:
    public Cliente buscarPorId(int idClienteBuscar) {
        String query = "SELECT p.id_persona, p.nombre, p.dni, p.telefono, p.correo, c.id_cliente, c.observacion, c.fecha_registro FROM cliente c INNER JOIN persona p ON c.id_cliente = p.id_persona WHERE c.id_cliente = ?";
        Cliente cliente = null;

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, idClienteBuscar);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                int idPersona = resultSet.getInt("id_persona");
                String nombre = resultSet.getString("nombre");
                String dni = resultSet.getString("dni");
                String telefono = resultSet.getString("telefono");
                String correo = resultSet.getString("correo");
                int idCliente = resultSet.getInt("id_cliente");
                String observacion = resultSet.getString("observacion");
                String fechaBonita = resultSet.getString("fecha_registro");

                cliente = new Cliente(idPersona, nombre, dni, telefono, correo, idCliente, observacion, fechaBonita);
            }
            resultSet.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al buscar cliente: " + e.getMessage());
        }
        return cliente;
    }

    // 4. ACTUALIZAR:
    public void actualizar(Cliente cliente) {
        String queryPersona = "UPDATE persona SET nombre = ?, telefono = ?, correo = ? WHERE id_persona = ?";
        String queryCliente = "UPDATE cliente SET observacion = ? WHERE id_cliente = ?";
        try {
            // PERSONA
            PreparedStatement psP = connection.prepareStatement(queryPersona);
            psP.setString(1, cliente.getNombre());
            psP.setString(2, cliente.getTelefono());
            psP.setString(3, cliente.getCorreo());
            psP.setInt(4, cliente.getIdPersona());
            psP.executeUpdate();
            psP.close();

            // CLIENTE
            PreparedStatement psC = connection.prepareStatement(queryCliente);
            psC.setString(1, cliente.getObservacion());
            psC.setInt(2, cliente.getIdCliente());
            psC.executeUpdate();
            psC.close();
        } catch (SQLException e) {
            System.out.println("Error al actualizar cliente en BD: " + e.getMessage());
        }
    }

    // 5. ELIMINAR: 
    public void eliminar(int idClienteEliminar) {
        String query = "DELETE FROM cliente WHERE id_cliente = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, idClienteEliminar);

            int filas = ps.executeUpdate();
            System.out.println("Cliente eliminado");
            System.out.println(" Filas eliminadas: " + filas);
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error en DELETE de cliente: " + e.getMessage());
        }
    }
}
