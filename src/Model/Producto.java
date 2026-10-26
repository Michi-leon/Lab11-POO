public abstract class Producto {
    private  int idProducto;
    private String nombre;
    private float precioCompra;
    private int stockActual;
    private int stockMinimo;

    public Producto(int idProducto, String nombre, float precioCompra, int stockActual, int stockMinimo) {
        setIdProducto(idProducto);
        setNombre(nombre);
        setPrecioCompra(precioCompra);
        setStockActual(stockActual);
        setStockMinimo(stockMinimo);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.equals("")) {
            this.nombre = nombre;
        } else {
            System.out.println("El nombre no puede estar vacio.");
        }
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        if (idProducto > 0) {
            this.idProducto = idProducto;
        } else {
            System.out.println("El ID debe ser mayor que cero.");
        }
    }

    public float getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(float precioCompra) {
        if (precioCompra >= 0) {
            this.precioCompra = precioCompra;
        } else {
            System.out.println("El precio de compra no puede ser negativo.");
        }
    }

    public int getStockActual() {
        return stockActual;
    }

    public void setStockActual(int stockActual) {
        if (stockActual >= 0) {
            this.stockActual = stockActual;
        } else {
            System.out.println("El stock no puede ser negativo.");
        }
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        if (stockMinimo >= 0) {
            this.stockMinimo = stockMinimo;
        } else {
            System.out.println("El stock minimo no puede ser negativo.");
        }
    }
    
    public boolean tieneStockBajo() {
        return stockActual <= stockMinimo;
    }
    public abstract String mostrarTipo();

    public void imprimirInformacion(){
        System.out.println("ID: "+idProducto);
        System.out.println("Nombre: "+nombre);
        System.out.println("Precio compra: "+precioCompra);
        System.out.println("Stock actual: "+stockActual);
        System.out.println("Stock minimo: "+stockMinimo);
    }
}