package Dao;

import Model.Servicio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioDao{
    private Connection connection;
    public ServicioDao(Connection connection){
        this.connection = connection;
    }

    public void registrar(Servicio servicio){

        String sql = "INSERT INTO servicios "
                   + "(id_servicio, nombre, descripcion, precio, duracion) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, servicio.getIdServicio());
            ps.setString(2, servicio.getNombre());
            ps.setString(3, servicio.getDescripcion());
            ps.setDouble(4, servicio.getPrecio());
            ps.setInt(5, servicio.getDuracion());

            ps.executeUpdate();
            ps.close();

            System.out.println("Servicio registrado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al registrar servicio: " + e.getMessage());
        }
    }

    public List<Servicio> listarServicios() {

        List<Servicio> servicios = new ArrayList<>();

        String sql = "SELECT * FROM servicios";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                Servicio servicio = new Servicio(
                    rs.getInt("id_servicio"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getInt("duracion")
                );

                servicios.add(servicio);
            }

            rs.close();
            st.close();

        } catch (SQLException e) {
            System.out.println("Error al listar servicios: " + e.getMessage());
        }

        return servicios;
    }

    public Servicio buscarPorId(int id) {

        Servicio servicio = null;

        String sql = "SELECT * FROM servicios WHERE id_servicio = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                servicio = new Servicio(
                    rs.getInt("id_servicio"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getInt("duracion")
                );
            }

            rs.close();
            ps.close();

        } catch (SQLException e){
            System.out.println("Error al buscar servicio: " + e.getMessage());
        }

        return servicio;
    }

    public void actualizar(Servicio servicio){

        String sql = "UPDATE servicios SET nombre = ?, descripcion = ?, "
                   + "precio = ?, duracion = ? WHERE id_servicio = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, servicio.getNombre());
            ps.setString(2, servicio.getDescripcion());
            ps.setDouble(3, servicio.getPrecio());
            ps.setInt(4, servicio.getDuracion());
            ps.setInt(5, servicio.getIdServicio());

            ps.executeUpdate();
            ps.close();

            System.out.println("Servicio actualizado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al actualizar servicio: " + e.getMessage());
        }
    }

    public void eliminar(int id) {

        String sql = "DELETE FROM servicios WHERE id_servicio = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();
            ps.close();

            System.out.println("Servicio eliminado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al eliminar servicio: " + e.getMessage());
        }
    }
}

