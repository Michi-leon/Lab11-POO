package Controller;

import Dao.VentaDao;
import Dao.DetalleDao;
import Model.Venta;
import Model.DetalleVenta;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class GestorVenta {
    private Connection connection;
    private VentaDao ventaDao;
    private DetalleDao detalleDao;

    public GestorVenta(Connection connection, VentaDao ventaDao, DetalleDao detalleDao) {
        this.connection = connection;
        this.ventaDao = ventaDao;
        this.detalleDao = detalleDao;
    }

    // Relacionar y registrar
    public boolean procesarVenta(Venta v, List<DetalleVenta> detalles) {
        if (v == null || detalles == null || detalles.isEmpty()) {
            System.out.println("Datos de venta o detalles no validos.");
            return false;
        }
        
        if (ventaDao.buscarPorId(v.getIdVenta()) != null) {
            System.out.println("El ID de la venta ya esta registrado.");
            return false;
        }

        try {
            connection.setAutoCommit(false);

            boolean cabeceraOK = ventaDao.registrar(v);
            if (!cabeceraOK) {
                connection.rollback();
                return false;
            }

            for (DetalleVenta d : detalles) {
                boolean detalleOK = detalleDao.registrar(v.getIdVenta(), d);
                if (!detalleOK) {
                    System.out.println("Error al procesar los articulos. Deshaciendo cambios...");
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            System.out.println("Se guardo correctamente los datos de la Venta en el sistema.");
            return true;

        } catch (SQLException e) {
            System.out.println("Error critico en la base de datos: " + e.getMessage());
            try { connection.rollback(); } catch (SQLException ex) { System.out.println(ex.getMessage()); }
            return false;
        } finally {
            try { connection.setAutoCommit(true); } catch (SQLException e) { System.out.println(e.getMessage()); }
        }
    }
    //listar
    public void listarVentas() {
        List<Venta> ventas = ventaDao.listarVentas();
        if (ventas.isEmpty()) {
            System.out.println("No se registran ventas.");
        } else {
            for (Venta v : ventas) {
                v.imprimirInformacion();
            }
        }
    }
    //buscar
    public Venta buscarVenta(int id) {
        return ventaDao.buscarPorId(id);
    }

    // 4. ACTUALIZAR Venta
    public boolean modificarVenta(int id, int nuevoIdCliente, java.sql.Date nuevaFecha, double nuevoTotal) {
        Venta v = buscarVenta(id);
        if (v == null) {
            System.out.println("No se encontro la venta seleccionada.");
            return false;
        }
        v.setIdCliente(nuevoIdCliente);
        v.setFecha(nuevaFecha);
        v.setTotal(nuevoTotal);

        boolean exito = ventaDao.actualizar(v);
        return exito;
    }

    //Actualidar Detalle
    public boolean modificarDetalleVenta(DetalleVenta d) {
        if (d == null || d.getIdDetalle() <= 0) {
            System.out.println("Los datos del detalle no son validos.");
            return false;
        }
        
        boolean exito = detalleDao.actualizar(d);
        if (exito) {
            System.out.println("Se actualizo el detalle de la venta correctamente.");
        }
        return exito;
    }

    //ELIMINAR
    public boolean eliminarVenta(int id) {
        Venta v = buscarVenta(id);
        if (v == null) {
            System.out.println("No se encontro la venta seleccionada.");
            return false;
        }
        //Primero detalle por la relacion asi no nos saldra error
        detalleDao.eliminarPorVenta(id);
        
        //Ahora si venta
        boolean exito = ventaDao.eliminar(id);
        return exito;
    }
}