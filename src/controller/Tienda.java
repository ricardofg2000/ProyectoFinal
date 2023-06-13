package controller;

import java.util.List;
import java.util.ArrayList;
import model.*;

public class Tienda {
	private Inventario inventario;
	private List<Cliente> clientes;
	private List<Pedido> pedidos;

	public Tienda() {
		inventario = new Inventario();
		clientes = new ArrayList<>();
		pedidos = new ArrayList<>();
	}

	public void agregarCliente(Cliente cliente) {
		clientes.add(cliente);
	}

	public void realizarPedido(Pedido pedido) {
		// Validar el inventario y actualizar las cantidades
		// Agregar el pedido a la lista de pedidos
	}

	//public List<Pedido> obtenerPedidosPorCliente(Cliente cliente) {
		// Obtener la lista de pedidos realizados por un cliente específico
	//}

	//public Factura generarFactura(Pedido pedido) {
		// Generar una factura para un pedido dado
	//}
}