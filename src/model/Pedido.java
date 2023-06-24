package model;

import java.util.Date;
import java.util.List;

/**
 * La clase Pedido representa un pedido realizado por un cliente en una fecha específica.
 */
public class Pedido {
	
    /** 
     * El cliente que realizó el pedido.
     */
    private Cliente cliente;
    
    /** 
     * La fecha en la que se realizó el pedido.
     */
    private Date fechaPedido;
    
    /**
     * Crea un nuevo pedido vacío.
     */
    public Pedido() {
    	
    }
    
	/**
	 * Crea un nuevo pedido con el cliente, los productos y la fecha especificados.
	 *
	 * @param cliente el cliente que realizó el pedido
	 * @param productos la lista de productos del pedido
	 * @param fechaPedido la fecha en la que se realizó el pedido
	 */
	public Pedido(Cliente cliente, List<Producto> productos, Date fechaPedido) {
		super();
		this.cliente = cliente;
		this.fechaPedido = fechaPedido;
	}

	/**
	 * Obtiene el cliente que realizó el pedido.
	 *
	 * @return el cliente del pedido
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * Establece el cliente del pedido.
	 *
	 * @param cliente el nuevo cliente del pedido
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * Obtiene la fecha en la que se realizó el pedido.
	 *
	 * @return la fecha del pedido
	 */
	public Date getFechaPedido() {
		return fechaPedido;
	}

	/**
	 * Establece la fecha del pedido.
	 *
	 * @param fechaPedido la nueva fecha del pedido
	 */
	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
	}
}
