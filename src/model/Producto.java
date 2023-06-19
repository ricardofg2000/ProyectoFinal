package model;

import java.util.concurrent.atomic.AtomicInteger;

public class Producto {

    private int id;
    private String nombre;
    private double precio;
    private String descripcion;
    private String codigoBarras;
    private int cantidad;

    public Producto() {
    }

    public Producto(String nombre, double precio, String descripcion, String codigoBarras) {
        this();
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.codigoBarras = codigoBarras;
        this.cantidad = 0;
    }
    
    public Producto(int id, String nombre, double precio, String descripcion, String codigoBarras) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.codigoBarras = codigoBarras;
        this.cantidad = 0;
    }
    
    public Producto(String nombre, double precio, String descripcion, String codigoBarras, int cantidad) {
        this();
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.codigoBarras = codigoBarras;
    }
    
    public Producto(int id, String nombre, double precio, String descripcion, String codigoBarras, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.codigoBarras = codigoBarras;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	

	public void setId(int id) {
		this.id = id;
	}

	@Override
    public String toString() {
        return "Producto [id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", descripcion=" + descripcion
                + ", codigoBarras=" + codigoBarras + ", stock" + cantidad + "]";
    }
}
