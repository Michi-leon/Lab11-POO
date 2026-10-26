package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private String url = "jdbc:mysql://localhost:3306/salon_belleza?useSSL=false&serverTimezone=UTC";
    private String usuario = "root";
    private String contraseña = "";
    private Connection connection;

    public Connection conectarBaseDatos() {
        try {
            connection = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("¡Conexión exitosa a la base de datos salon_belleza!");
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
        return connection;
    }
    public void cerrarConexion() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión cerrada exitosamente.");
            } catch (SQLException e) {
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
}