package model;

import java.util.Date;
import java.util.List;

public class Pedido {
	
    private Cliente cliente;
    private List<Producto> productos;
    private Date fechaPedido;
    
    public Pedido() {
    	
    }
    
	public Pedido(Cliente cliente, List<Producto> productos, Date fechaPedido) {
		super();
		this.cliente = cliente;
		this.productos = productos;
		this.fechaPedido = fechaPedido;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public Date getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

    
}
