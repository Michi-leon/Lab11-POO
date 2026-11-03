package Controller;

import Model.Servicio;
import Dao.ServicioDao;
import java.util.List;

public class GestorServicio {

    private ServicioDao servicioDao;

    public GestorServicio(ServicioDao servicioDao) {
        this.servicioDao = servicioDao;
    }

    // Registrar
    public boolean registrarServicio(Servicio servicio) {

        if (servicio == null) {
            System.out.println("El servicio no puede ser nulo");
            return false;
        }

        if (servicio.getIdServicio() <= 0) {
            System.out.println("El ID debe ser mayor que cero");
            return false;
        }

        if (servicio.getNombre() == null ||
            servicio.getNombre().equals("")) {
            System.out.println("El nombre no puede estar vacio");
            return false;
        }

        if (servicio.getPrecio() < 0) {
            System.out.println("El precio no puede ser negativo");
            return false;
        }

        if (servicio.getDuracion() <= 0) {
            System.out.println("La duracion debe ser mayor que cero");
            return false;
        }

        if (servicioDao.buscarPorId(servicio.getIdServicio()) != null) {
            System.out.println("El ID del servicio ya esta registrado");
            return false;
        }

        servicioDao.registrar(servicio);
        return true;
    }

    public void listarServicios() {

        List<Servicio> servicios = servicioDao.listarServicios();

        if (servicios.isEmpty()) {
            System.out.println("No hay servicios registrados");
        } else {

            for (Servicio s : servicios) {
                s.imprimirInformacion();
                System.out.println();
            }
        }
    }

    public Servicio buscarServicio(int id) {

        Servicio servicio = servicioDao.buscarPorId(id);

        if (servicio == null) {
            System.out.println("Servicio con ID " + id + " no encontrado.");
        }

        return servicio;
    }

    public boolean eliminarServicio(int id) {

        Servicio servicio = buscarServicio(id);

        if (servicio == null) {
            return false;
        }

        servicioDao.eliminar(id);
        return true;
    }

    public boolean modificarNombre(int id, String nuevoNombre) {

        Servicio servicio = buscarServicio(id);

        if (servicio == null) {
            return false;
        }

        if (nuevoNombre == null || nuevoNombre.equals("")) {
            System.out.println("El nombre no puede estar vacio.");
            return false;
        }

        servicio.setNombre(nuevoNombre);
        servicioDao.actualizar(servicio);

        System.out.println("Nombre modificado correctamente");
        return true;
    }

    public boolean modificarDescripcion(int id, String nuevaDescripcion) {

        Servicio servicio = buscarServicio(id);

        if (servicio == null) {
            return false;
        }

        servicio.setDescripcion(nuevaDescripcion);
        servicioDao.actualizar(servicio);

        System.out.println("Descripcion modificada correctamente");
        return true;
    }

    public boolean modificarPrecio(int id, double nuevoPrecio) {

        Servicio servicio = buscarServicio(id);

        if (servicio == null) {
            return false;
        }

        if (nuevoPrecio < 0) {
            System.out.println("El precio no puede ser negativo.");
            return false;
        }

        servicio.setPrecio(nuevoPrecio);
        servicioDao.actualizar(servicio);

        System.out.println("Precio modificado correctamente");
        return true;
    }

    public boolean modificarDuracion(int id, int nuevaDuracion) {

        Servicio servicio = buscarServicio(id);

        if (servicio == null) {
            return false;
        }

        if (nuevaDuracion <= 0) {
            System.out.println("La duracion debe ser mayor que cero");
            return false;
        }

        servicio.setDuracion(nuevaDuracion);
        servicioDao.actualizar(servicio);

        System.out.println("Duracion modificada correctamente");
        return true;
    }
}

