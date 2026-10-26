package Dao;

import Model.Trabajadora;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrabajadoraDao {
    private Connection connection;

    public TrabajadoraDao(Connection connection) {
        this.connection = connection;
    }

    // 1. REGISTRAR
    public void registrar(Trabajadora t) {
        String queryPersona = "INSERT INTO persona (id_persona, nombre, dni, telefono, correo) VALUES (?, ?, ?, ?, ?)";
        String queryTrabajadora = "INSERT INTO trabajadora (id_trabajadora, especialidad) VALUES (?, ?)";
        
        try {
            // INSERTAR PERSONA
            PreparedStatement psP = connection.prepareStatement(queryPersona);
            psP.setInt(1, t.getIdPersona());
            psP.setString(2, t.getNombre());
            psP.setString(3, t.getDni());
            psP.setString(4, t.getTelefono());
            psP.setString(5, t.getCorreo());
            psP.executeUpdate();
            psP.close();

            // insertar Trabajadora
            PreparedStatement psT = connection.prepareStatement(queryTrabajadora);
            psT.setInt(1, t.getIdPersona()); // Comparte exactamente el mismo ID heredado
            psT.setString(2, t.getEspecialidad());
            psT.executeUpdate();
            psT.close();

            System.out.println("Trabajadora registrada");
            System.out.println(" Filas insertadas correctamente en ambas tablas.");
        } catch (SQLException e) {
            System.out.println("Error en INSERT de trabajadora compuesta: " + e.getMessage());
        }
    }

    // 2. LISTAR
    public List<Trabajadora> listarTrabajadoras() {
        List<Trabajadora> lista = new ArrayList<>();
        String query = "SELECT p.id_persona, p.nombre, p.dni, p.telefono, p.correo, t.especialidad " +
                       "FROM trabajadora t INNER JOIN persona p ON t.id_trabajadora = p.id_persona";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int idPersona = resultSet.getInt("id_persona");
                String nombre = resultSet.getString("nombre");
                String dni = resultSet.getString("dni");
                String telefono = resultSet.getString("telefono");
                String correo = resultSet.getString("correo");
                String especialidad = resultSet.getString("especialidad");

                Trabajadora t = new Trabajadora(idPersona, nombre, dni, telefono, correo, especialidad);
                lista.add(t);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error al listar trabajadoras: " + e.getMessage());
        }
        return lista;
    }

    // 3. BUSCAR POR ID
    public Trabajadora buscarPorId(int idBuscar) {
        String query = "SELECT p.id_persona, p.nombre, p.dni, p.telefono, p.correo, t.especialidad " +
                       "FROM trabajadora t INNER JOIN persona p ON t.id_trabajadora = p.id_persona WHERE t.id_trabajadora = ?";
        Trabajadora t = null;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, idBuscar);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                int idPersona = resultSet.getInt("id_persona");
                String nombre = resultSet.getString("nombre");
                String dni = resultSet.getString("dni");
                String telefono = resultSet.getString("telefono");
                String correo = resultSet.getString("correo");
                String especialidad = resultSet.getString("especialidad");

                t = new Trabajadora(idPersona, nombre, dni, telefono, correo, especialidad);
            }
            resultSet.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al buscar trabajadora: " + e.getMessage());
        }
        return t;
    }

    // 4. ACTUALIZAR
    public void actualizar(Trabajadora t) {
        String queryPersona = "UPDATE persona SET nombre = ?, telefono = ?, correo = ? WHERE id_persona = ?";
        String queryTrabajadora = "UPDATE trabajadora SET especialidad = ? WHERE id_trabajadora = ?";
        try {
            // persona
            PreparedStatement psP = connection.prepareStatement(queryPersona);
            psP.setString(1, t.getNombre());
            psP.setString(2, t.getTelefono());
            psP.setString(3, t.getCorreo());
            psP.setInt(4, t.getIdPersona());
            psP.executeUpdate();
            psP.close();

            // trabajadora
            PreparedStatement psT = connection.prepareStatement(queryTrabajadora);
            psT.setString(1, t.getEspecialidad());
            psT.setInt(2, t.getIdPersona());
            psT.executeUpdate();
            psT.close();
        } catch (SQLException e) {
            System.out.println("Error al actualizar trabajadora en BD: " + e.getMessage());
        }
    }

    // 5. ELIMINAR
    public void eliminar(int idEliminar) {
        String query = "DELETE FROM trabajadora WHERE id_trabajadora = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, idEliminar);

            int filas = ps.executeUpdate();
            System.out.println("Trabajadora eliminada");
            System.out.println(" Filas eliminadas: " + filas);
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error en DELETE de trabajadora: " + e.getMessage());
        }
    }
}
