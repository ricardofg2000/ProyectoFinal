package model;

/**
 * La clase Producto representa un producto con su información básica, como el nombre, precio, descripción y código de barras.
 */
public class Producto {

    /** 
     * El identificador único del producto.
     */
    private int id;
    
    /** 
     * El nombre del producto.
     */
    private String nombre;
    
    /** 
     * El precio del producto.
     */
    private double precio;
    
    /** 
     * La descripción del producto.
     */
    private String descripcion;
    
    /** 
     * El código de barras del producto.
     */
    private String codigoBarras;

    /**
     * Crea un nuevo producto vacío.
     */
    public Producto() {
    }

    /**
     * Crea un nuevo producto con el nombre, precio, descripción y código de barras especificados.
     *
     * @param nombre el nombre del producto
     * @param precio el precio del producto
     * @param descripcion la descripción del producto
     * @param codigoBarras el código de barras del producto
     */
    public Producto(String nombre, double precio, String descripcion, String codigoBarras) {
        this();
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.codigoBarras = codigoBarras;
    }
    
    /**
     * Crea un nuevo producto con el identificador, nombre, precio, descripción y código de barras especificados.
     *
     * @param id el identificador único del producto
     * @param nombre el nombre del producto
     * @param precio el precio del producto
     * @param descripcion la descripción del producto
     * @param codigoBarras el código de barras del producto
     */
    public Producto(int id, String nombre, double precio, String descripcion, String codigoBarras) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.codigoBarras = codigoBarras;
    }

    /**
     * Obtiene el identificador único del producto.
     *
     * @return el identificador del producto
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return el nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param nombre el nuevo nombre del producto
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el precio del producto.
     *
     * @return el precio del producto
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del producto.
     *
     * @param precio el nuevo precio del producto
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene la descripción del producto.
     *
     * @return la descripción del producto
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del producto.
     *
     * @param descripcion la nueva descripción del producto
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el código de barras del producto.
     *
     * @return el código de barras del producto
     */
    public String getCodigoBarras() {
        return codigoBarras;
    }

    /**
     * Establece el código de barras del producto.
     *
     * @param codigoBarras el nuevo código de barras del producto
     */
    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

	/**
	 * Establece el identificador del producto.
	 *
	 * @param id el nuevo identificador del producto
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Devuelve una representación en forma de cadena del producto.
	 *
	 * @return una cadena que representa el producto
	 */
	@Override
    public String toString() {
        return "Producto [id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", descripcion=" + descripcion
                + ", codigoBarras=" + codigoBarras + "]";
    }
}
