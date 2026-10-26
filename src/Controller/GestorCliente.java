package Controller;

import Model.Cliente;
import Dao.ClienteDao;
import java.util.List;

public class GestorCliente {
    private ClienteDao clienteDao;

    public GestorCliente(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    // REGISTRAR CLIENTE
    public boolean registrarCliente(Cliente cliente) {
        if (cliente == null) {
            System.out.println("El cliente no puede ser nulo.");
            return false;
        }
        if (clienteDao.buscarPorId(cliente.getIdCliente()) != null) {
            System.out.println("El ID de cliente ya está registrado.");
            return false;
        }
        clienteDao.registrar(cliente);
        return true;
    }

    // LISTAR CLIENTES
    public void listarClientes() {
        List<Cliente> clientes = clienteDao.listarClientes();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados en el sistema.");
        } else {
            for (Cliente c : clientes) {
                c.imprimirInformacion();
                System.out.println();
            }
        }
    }

    // BUSCAR CLIENTE
    public Cliente buscarCliente(int id) {
        Cliente cliente = clienteDao.buscarPorId(id);
        if (cliente == null) {
            System.out.println("Cliente con ID " + id + " no encontrado.");
        }
        return cliente;
    }

    // ELIMINAR CLIENTE
    public boolean eliminarCliente(int id) {
        Cliente cliente = buscarCliente(id);
        if (cliente == null){
            return false;
        }
        clienteDao.eliminar(id);
        return true;
    }

    // Modificar Nombre
    public boolean modificarNombre(int id, String nuevoNombre) {
        Cliente cliente = buscarCliente(id);
        if (cliente == null){return false; }

       if (nuevoNombre == null || nuevoNombre.equals("")) {
            System.out.println("El nombre no puede estar vacío.");
            return false;
        }

        cliente.setNombre(nuevoNombre); 
        clienteDao.actualizar(cliente);
        System.out.println("El nombre se ha modificado con éxito");
        return true;
    }

    // Modificar Teléfono
    public boolean modificarTelefono(int id, String nuevoTelefono) {
        Cliente cliente = buscarCliente(id);
        if (cliente == null){return false; }

        if (nuevoTelefono == null || nuevoTelefono.length() != 9) {
            System.out.println("El teléfono en Perú debe tener exactamente 9 dígitos.");
            return false;
        }

        cliente.setTelefono(nuevoTelefono);
        clienteDao.actualizar(cliente);
        System.out.println("El número de teléfono se ha  modificado con éxito");
        return true;
    }

    // Modificar Correo
    public boolean modificarCorreo(int id, String nuevoCorreo) {
        Cliente cliente = buscarCliente(id);
        if (cliente == null){ return false; }

        cliente.setCorreo(nuevoCorreo);
        clienteDao.actualizar(cliente);
        System.out.println("El correo electrónico se ha modificado con éxito");
        return true;
    }

    // Modificar Observación
    public boolean modificarObservacion(int id, String nuevaObservacion) {
        Cliente cliente = buscarCliente(id);
        if (cliente == null){ return false; }

        cliente.setObservacion(nuevaObservacion);
        clienteDao.actualizar(cliente);
        System.out.println("La observación sobre el cliente se ha actualizada");
        return true;
    }
}