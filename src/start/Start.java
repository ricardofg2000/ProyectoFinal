package start;

import java.util.List;

import controller.JsonController;
import model.Producto;

public class Start {

	public static void main(String[] args) {
		Log log = new Log(Log.Tipo.ERROR, "Este es un mensaje de error de prueba");
		
		JsonController gest = new JsonController();
		final String FILE_NAME = "importProductos.json";
		
		List<Producto> productos = gest.leerListProductos(FILE_NAME);
		if (productos != null) {
		    for (Producto producto : productos) {
		        System.out.println("Nombre: " + producto.getNombre());
		        System.out.println("Precio: " + producto.getPrecio());
		        System.out.println("Descripción: " + producto.getDescripcion());
		        System.out.println("Código de Barras: " + producto.getCodigoBarras());
		        System.out.println("-------------------------");
		    }
		} else {
		    System.out.println("No se encontraron productos en el archivo.");
		}

		
		new view.FrmLogin();
	}

}