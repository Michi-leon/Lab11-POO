import Controller.GestorCliente;
import Controller.GestorProducto;
import Controller.GestorServicio;
import Controller.GestorVenta;
import Dao.ClienteDao;
import Dao.ProductoDao;
import Dao.TrabajadoraDao;
import Dao.ServicioDao;
import Dao.VentaDao;
import Dao.DetalleDao;
import Service.Conexion;
import Model.*;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    //MENU GENERAL
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Conexion conexionDB = new Conexion();
        Connection conexion = conexionDB.conectarBaseDatos();

        if (conexion == null) {
            System.out.println("NO SE PUEDE CONECTAR CON LA BASE DE DATOS");
            return;
        }

        ClienteDao clienteDao = new ClienteDao(conexion);
        GestorCliente gestorCliente = new GestorCliente(clienteDao);

        ProductoDao productoDao = new ProductoDao(conexion);
        GestorProducto gestorProducto = new GestorProducto(productoDao);

        TrabajadoraDao trabajadoraDao = new TrabajadoraDao(conexion);

        ServicioDao servicioDao = new ServicioDao(conexion);
        GestorServicio gestorServicio = new GestorServicio(servicioDao);

        VentaDao ventaDao = new VentaDao(conexion);
        DetalleDao detalleDao = new DetalleDao(conexion);
        GestorVenta gestorVenta = new GestorVenta(conexion, ventaDao, detalleDao);

        int opcionPrincipal = 0;

        do {
            System.out.println("SALON DE BELLEZA ");
            System.out.println("1. GESTION DE PRODUCTOS");
            System.out.println("2. GESTION DE CLIENTES");
            System.out.println("3. GESTION DE TRABAJADORAS");
            System.out.println("4. GESTION DE SERVICIOS");
            System.out.println("5. MODULO DE VENTAS");
            System.out.println("6. SALIR DEL PROGRAMA");
            System.out.println("INGRESE UNA OPCION: ");

            if (scanner.hasNextInt()) {
                opcionPrincipal = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("INGRESE UN NUMERO VALIDO :)");
                scanner.nextLine();
                continue;
            }

            switch (opcionPrincipal) {
                case 1:
                    menuProductos(scanner, gestorProducto);
                    break;
                case 2:
                    menuClientes(scanner, gestorCliente);
                    break;
                case 3:
                    menuTrabajadoras(scanner, trabajadoraDao);
                    break;
                case 4:
                    menuServicios(scanner, gestorServicio);
                    break;
                case 5:
                    menuVentasConsola(scanner, gestorVenta, gestorCliente, gestorProducto, gestorServicio);
                    break;
                case 6:
                    System.out.println("APAGANDO SISTEMA");
                    conexionDB.cerrarConexion();
                    break;
                default:
                    System.out.println("ESTO NO ES VALIDO");
            }
        } while (opcionPrincipal != 6);

        scanner.close();
    }
    //MENU PRODUCTOS
    private static void menuProductos(Scanner scanner, GestorProducto gestorProducto) {
        int op = 0;
        do {
            System.out.println("GESTION DE PRODUCTOS");
            System.out.println("1. REGISTRAR PRODUCTO");
            System.out.println("2. LISTAR PRODUCTOS");
            System.out.println("3. BUSCAR PRODUCTO POR ID");
            System.out.println("4. ACTUALIZAR PRODUCTO");
            System.out.println("5. ELIMINAR PRODUCTO");
            System.out.println("6. LISTAR STOCK BAJO");
            System.out.println("7. VOLVER AL MENU PRINCIPAL");
            System.out.println();
            System.out.println("INGRESE UNA OPCION: ");
            
            if (scanner.hasNextInt()) {
                op = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("INGRESE UN NUMERO VALIDO :)");
                scanner.nextLine();
                continue;
            }

            switch (op) {
                case 1:
                    System.out.println("REGISTRAR PRODUCTO");
                    System.out.println("ID del producto: ");
                    int idp = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Nombre: ");
                    String nom = scanner.nextLine();
                    System.out.println("Precio Compra: ");
                    float pCom = scanner.nextFloat();
                    System.out.println("Stock Actual: ");
                    int sAct = scanner.nextInt();
                    System.out.println("Stock Minimo: ");
                    int sMin = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Tipo de Producto (1. Insumo, 2. Producto de venta, 3. Producto mixto): ");
                    int tipo = scanner.nextInt();
                    scanner.nextLine();

                    Producto p = null;
                    if (tipo == 1) {
                        p = new Insumo(idp, nom, pCom, sAct, sMin);
                    } else if (tipo == 2) {
                        System.out.println("Precio Venta: ");
                        float pVen = scanner.nextFloat();
                        scanner.nextLine();
                        p = new ProductoVenta(idp, nom, pCom, sAct, sMin, pVen);
                    } else if (tipo == 3) {
                        System.out.println("Precio Venta Mixto: ");
                        float pVenM = scanner.nextFloat();
                        scanner.nextLine();
                        p = new ProductoMixto(idp, nom, pCom, sAct, sMin, pVenM);
                    }

                    if (p != null) {
                        gestorProducto.registrarProducto(p);
                    } else {
                        System.out.println("EL PRODUCTO NO ES VALIDO");
                    }
                    break;
                case 2:
                    System.out.println("LISTA DE PRODUCTOS");
                    gestorProducto.listarProductos();
                    break;
                case 3:
                    System.out.println("BUSCAR PRODUCTO");
                    System.out.println("Ingrese ID del producto: ");
                    int idB = scanner.nextInt();
                    scanner.nextLine();
                    Producto prod = gestorProducto.buscarProducto(idB);
                    if (prod != null) prod.imprimirInformacion();
                    break;
                case 4:
                    System.out.println("ACTUALIZAR PRODUCTO");
                    System.out.println("Ingrese ID del producto a actualizar: ");
                    int idAct = scanner.nextInt();
                    scanner.nextLine();
                    Producto prodEx = gestorProducto.buscarProducto(idAct);
                    if (prodEx != null) {
                        System.out.println("Nuevo Nombre: ");
                        String nNom = scanner.nextLine();
                        gestorProducto.modificarNombre(idAct, nNom);
                    }
                    break;
                case 5:
                    System.out.println("ELIMINAR PRODUCTO");
                    System.out.println("Ingrese ID del producto a eliminar: ");
                    int idDel = scanner.nextInt();
                    scanner.nextLine();
                    gestorProducto.eliminarProducto(idDel);
                    break;
                case 6:
                    System.out.println("PRODUCTOS CON STOCK BAJO");
                    gestorProducto.listarProductosStockBajo();
                    break;
                case 7:
                    System.out.println("REGRESANDO AL MENU");
                    break;
                default:
                    System.out.println("NO ES VALIDO");
            }
        } while (op != 7);
    }
    //MENU CLIENTES
    private static void menuClientes(Scanner scanner, GestorCliente gestorCliente) {
        int op = 0;
        do {
            System.out.println("SUBMENU GESTION DE CLIENTES");
            System.out.println("1. REGISTRAR CLIENTE");
            System.out.println("2. LISTAR CLIENTES");
            System.out.println("3. BUSCAR CLIENTE");
            System.out.println("4. ACTUALIZAR CLIENTE");
            System.out.println("5. ELIMINAR CLIENTE");
            System.out.println("6. VOLVER AL MENU PRINCIPAL");
            System.out.println("INGRESE UNA OPCION: ");
            
            if (scanner.hasNextInt()) {
                op = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("INGRESE UN NUMERO VALIDO :)");
                scanner.nextLine();
                continue;
            }

            switch (op) {
                case 1:
                    System.out.println("REGISTRAR CLIENTE");
                    System.out.println("ID Persona / ID Cliente: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Nombre completo: ");
                    String nombre = scanner.nextLine();
                    System.out.println("DNI");
                    String dni = scanner.nextLine();
                    System.out.println("Telefono");
                    String tel = scanner.nextLine();
                    System.out.println("Correo electrónico: ");
                    String correo = scanner.nextLine();
                    System.out.println("Observacion: ");
                    String obs = scanner.nextLine();

                    Cliente nuevoCliente = new Cliente(id, nombre, dni, tel, correo, id, obs);
                    gestorCliente.registrarCliente(nuevoCliente);
                    break;
                case 2:
                    System.out.println("LISTA DE CLIENTES");
                    gestorCliente.listarClientes();
                    break;
                case 3:
                    System.out.println("BUSCAR CLIENTE");
                    System.out.println("Ingrese ID del cliente: ");
                    int idC = scanner.nextInt();
                    scanner.nextLine();
                    Cliente cli = gestorCliente.buscarCliente(idC);
                    if (cli != null) cli.imprimirInformacion();
                    break;
                case 4:
                    System.out.println("ACTUALIZAR CLIENTE");
                    System.out.println("Ingrese ID del cliente a modificar: ");
                    int idM = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Nuevo Telefono");
                    String nTel = scanner.nextLine();
                    gestorCliente.modificarTelefono(idM, nTel);
                    break;
                case 5:
                    System.out.println("ELIMINAR CLIENTE");
                    System.out.println("Ingrese ID del cliente a eliminar: ");
                    int idEl = scanner.nextInt();
                    scanner.nextLine();
                    gestorCliente.eliminarCliente(idEl);
                    break;
                case 6:
                    System.out.println("REGRESANDO AL MENU");
                    break;
                default:
                    System.out.println("OPCION NO VALIDA");
            }
        } while (op != 6);
    }
    //MENU TTRABAJADORAS
    private static void menuTrabajadoras(Scanner scanner, TrabajadoraDao trabajadoraDao) {
        int op = 0;
        do {
            System.out.println("SUBMENU GESTION DE TRABAJADORAS");
            System.out.println("1. REGISTRAR TRABAJADORA");
            System.out.println("2. LISTAR TRABAJADORAS");
            System.out.println("3. BUSCAR TRABAJADORA");
            System.out.println("4. ACTUALIZAR TRABAJADORA");
            System.out.println("5. ELIMINAR TRABAJADORA");
            System.out.println("6. VOLVER AL MENU PRINCIPAL");
            System.out.println("INGRESE UNA OPCION: ");
            
            if (scanner.hasNextInt()) {
                op = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("INGRESE UN NUMERO VALIDO :)");
                scanner.nextLine();
                continue;
            }

            switch (op) {
                case 1:
                    System.out.println("REGISTRAR TRABAJADORA");
                    System.out.println("ID Persona / ID Trabajadora: ");
                    int idT = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Nombre completo: ");
                    String nomT = scanner.nextLine();
                    System.out.println("DNI");
                    String dniT = scanner.nextLine();
                    System.out.println("Telefono");
                    String telT = scanner.nextLine();
                    System.out.println("Correo electronico");
                    String corT = scanner.nextLine();
                    System.out.println("Especialidad");
                    String esp = scanner.nextLine();

                    Trabajadora t = new Trabajadora(idT, nomT, dniT, telT, corT, esp);
                    trabajadoraDao.registrar(t);
                    break;
                case 2:
                    System.out.println("LISTA DE TRABAJADORAS");
                    List<Trabajadora> listaT = trabajadoraDao.listarTrabajadoras();
                    if (listaT.isEmpty()) {
                        System.out.println("NO EXISTEN TRABAJADORES REGISTRADOS");
                    } else {
                        for (Trabajadora tr : listaT) {
                            tr.imprimirInformacion();
                            System.out.println();
                        }
                    }
                    break;
                case 3:
                    System.out.println("BUSCAR TRABAJADORA");
                    System.out.println("Ingrese ID de la trabajadora: ");
                    int idBusT = scanner.nextInt();
                    scanner.nextLine();
                    Trabajadora trEncontrada = trabajadoraDao.buscarPorId(idBusT);
                    if (trEncontrada != null) {
                        trEncontrada.imprimirInformacion();
                    } else {
                        System.out.println("NO SE ENCONTRO A LA TRABAJADORA");
                    }
                    break;
                case 4:
                    System.out.println("ACTUALIZAR TRABAJADORA");
                    System.out.println("Ingrese ID de la trabajadora a actualizar: ");
                    int idUpT = scanner.nextInt();
                    scanner.nextLine();
                    Trabajadora trEx = trabajadoraDao.buscarPorId(idUpT);
                    if (trEx != null) {
                        System.out.println("Nueva Especialidad: ");
                        String nEsp = scanner.nextLine();
                        trEx.setEspecialidad(nEsp);
                        trabajadoraDao.actualizar(trEx);
                    } else {
                        System.out.println("TRABAJADORA NO ENCONTRADA");
                    }
                    break;
                case 5:
                    System.out.println("ELIMINAR TRABAJADORA");
                    System.out.println("Ingrese ID de la trabajadora a eliminar: ");
                    int idDelT = scanner.nextInt();
                    scanner.nextLine();
                    trabajadoraDao.eliminar(idDelT);
                    break;
                case 6:
                    System.out.println("REGRESANDO AL MENU");
                    break;
                default:
                    System.out.println("NO ES VALIDO");
            }
        } while (op != 6);
    }
    //MENU SERVICIOS
    private static void menuServicios(Scanner scanner, GestorServicio gestorServicio) {
        int op = 0;
        do {
            System.out.println("SUBMENU GESTION DE SERVICIOS");
            System.out.println("1. REGISTRAR SERVICIO");
            System.out.println("2. LISTAR SERVICIOS");
            System.out.println("3. BUSCAR SERVICIO POR ID");
            System.out.println("4. ELIMINAR SERVICIO");
            System.out.println("5. VOLVER AL MENU PRINCIPAL");
            System.out.println("INGRESE UNA OPCION: ");
            
            if (scanner.hasNextInt()) {
                op = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("INGRESE UN NUMERO VALIDO :)");
                scanner.nextLine();
                continue;
            }

            switch (op) {
                case 1:
                    System.out.println("REGISTRAR SERVICIO");
                    System.out.println("ID del servicio: ");
                    int idS = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Nombre: ");
                    String nom = scanner.nextLine();
                    System.out.println("Descripcion: ");
                    String desc = scanner.nextLine();
                    System.out.println("Precio: ");
                    double precio = scanner.nextDouble();
                    System.out.println("Duracion (minutos): ");
                    int dur = scanner.nextInt();
                    scanner.nextLine();

                    Servicio s = new Servicio(idS, nom, desc, precio, dur);
                    gestorServicio.registrarServicio(s);
                    break;
                case 2:
                    System.out.println("LISTA DE SERVICIOS");
                    gestorServicio.listarServicios();
                    break;
                case 3:
                    System.out.println("BUSCAR SERVICIO");
                    System.out.println("Ingrese ID del servicio: ");
                    int idB = scanner.nextInt();
                    scanner.nextLine();
                    Servicio serv = gestorServicio.buscarServicio(idB);
                    if (serv != null) serv.imprimirInformacion();
                    break;
                case 4:
                    System.out.println("ELIMINAR SERVICIO");
                    System.out.println("Ingrese ID del servicio a eliminar: ");
                    int idDel = scanner.nextInt();
                    scanner.nextLine();
                    gestorServicio.eliminarServicio(idDel);
                    break;
                case 5:
                    System.out.println("REGRESANDO AL MENU");
                    break;
                default:
                    System.out.println("NO ES VALIDO");
            }
        } while (op != 5);
    }
    //MENU VENTAS
    private static void menuVentasConsola(Scanner scanner, GestorVenta gestorVenta, GestorCliente gestorCliente, GestorProducto gestorProducto, GestorServicio gestorServicio) {
        System.out.println("REGISTRAR NUEVA VENTA");
        System.out.println("ID unico para esta Venta: ");
        int idVenta = scanner.nextInt();
        System.out.println("ID del Cliente: ");
        int idCliente = scanner.nextInt();
        scanner.nextLine();

        if (gestorCliente.buscarCliente(idCliente) == null) {
            System.out.println("No se puede proceder: El cliente no existe.");
            return;
        }

        List<DetalleVenta> listaDetalles = new ArrayList<>();
        int subOp = 0;
        double totalVenta = 0;

        do {
            System.out.println("¿Que desea añadir a la venta?");
            System.out.println("1. Añadir Producto");
            System.out.println("2. Añadir Servicio");
            System.out.println("3. Confirmar y Procesar Venta");
            
            if (scanner.hasNextInt()) {
                subOp = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("INGRESE UN NUMERO VALIDO :)");
                scanner.nextLine();
                continue;
            }

            if (subOp == 1) {
                System.out.println("ID del Producto: ");
                int idP = scanner.nextInt();
                System.out.println("Cantidad: ");
                int cant = scanner.nextInt();
                System.out.println("Descuento: ");
                double desc = scanner.nextDouble();
                scanner.nextLine();

                Producto prod = gestorProducto.buscarProducto(idP);
                if (prod != null) {
                    if (prod.mostrarTipo().equals("Insumo")) {
                        System.out.println("EL INSUMO ES DE USO INTERNO, NO SE PUEDE VENDER");
                        continue;
                    }

                    if (prod.getStockActual() < cant) {
                        System.out.println("Stock insuficiente. Disponible actual: " + prod.getStockActual());
                        continue;
                    }

                    double precio = 0;
                    if (prod.mostrarTipo().equals("Producto de venta")) {
                        precio = ((ProductoVenta) prod).getPrecioVenta();
                    } else if (prod.mostrarTipo().equals("producto mixto")) {
                        precio = ((ProductoMixto) prod).getPrecioVenta();
                    }

                    DetalleVentaProducto detProd = new DetalleVentaProducto(0, cant, precio, desc, prod);
                    listaDetalles.add(detProd);
                    totalVenta += detProd.calcularSubtotal();
                    System.out.println("PRODUCTO AGREGADO");
                }
            } else if (subOp == 2) {
                System.out.println("ID del Servicio: ");
                int idSer = scanner.nextInt();
                System.out.println("Cantidad: ");
                int cant = scanner.nextInt();
                System.out.println("Descuento: ");
                double desc = scanner.nextDouble();
                scanner.nextLine();

                Servicio serv = gestorServicio.buscarServicio(idSer);
                if (serv != null) {
                    DetalleVentaServicio detServ = new DetalleVentaServicio(0, cant, serv.getPrecio(), desc, serv);
                    listaDetalles.add(detServ);
                    totalVenta += detServ.calcularSubtotal();
                    System.out.println("SERVICIO AGREGADO");
                }
            }
        } while (subOp != 3);

        if (!listaDetalles.isEmpty()) {
            Venta nuevaVenta = new Venta(idVenta, idCliente, new Date(System.currentTimeMillis()), totalVenta);
            boolean exitoTransaccion = gestorVenta.procesarVenta(nuevaVenta, listaDetalles);
            
            if (exitoTransaccion) {
                for (DetalleVenta d : listaDetalles) {
                    if (d.mostrarTipo().equals("PRODUCTO")) {
                        DetalleVentaProducto dp = (DetalleVentaProducto) d;
                        gestorProducto.disminuirStock(dp.getProducto().getIdProducto(), dp.getCantidad());
                    }
                }
            }
        } else {
            System.out.println("Operacion cancelada. No se agregaron items.");
        }
    }
}
