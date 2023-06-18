package model;

import java.util.Date;
import java.util.List;

public class Pedido {
	
    private Cliente cliente;
    private Date fechaPedido;
    
    public Pedido() {
    	
    }
    
	public Pedido(Cliente cliente, List<Producto> productos, Date fechaPedido) {
		super();
		this.cliente = cliente;
		this.fechaPedido = fechaPedido;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

    
}
