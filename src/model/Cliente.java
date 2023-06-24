package model;
/**
 * La clase Cliente representa un cliente con información personal y de cuenta.
 */
public class Cliente {
	
	/** 
	 * El ID del cliente.
	 */
	private int id;
	
	/** 
	 * El nombre de usuario del cliente.
	 */
	private String usuario;
	
	/** 
	 * La contraseña del cliente.
	 */
	private String contrasena;
	
	/** 
	 * El nombre del cliente.
	 */
	private String nombre;
	
	/** 
	 * La dirección del cliente.
	 */
	private String direccion;
	
	/** 
	 * El número de teléfono del cliente.
	 */
	private String telefono;
	
	/** 
	 * El rol del cliente.
	 */
	private String rol;

	/**
	 * Crea un nuevo cliente vacío.
	 */
	public Cliente() {
	}

	/**
	 * Crea un nuevo cliente con los datos especificados.
	 *
	 * @param usuario el nombre de usuario del cliente
	 * @param contraseña la contraseña del cliente
	 * @param nombre el nombre del cliente
	 * @param direccion la dirección del cliente
	 * @param telefono el número de teléfono del cliente
	 * @param rol el rol del cliente
	 */
	public Cliente(String usuario, String contraseña, String nombre, String direccion, String telefono, String rol) {
		this.usuario = usuario;
		this.contrasena = contraseña;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.rol = rol;
	}

	/**
	 * Crea un nuevo cliente con los datos especificados.
	 *
	 * @param id el ID del cliente
	 * @param usuario el nombre de usuario del cliente
	 * @param contraseña la contraseña del cliente
	 * @param nombre el nombre del cliente
	 * @param direccion la dirección del cliente
	 * @param telefono el número de teléfono del cliente
	 * @param rol el rol del cliente
	 */
	public Cliente(int id, String usuario, String contraseña, String nombre, String direccion, String telefono, String rol) {
		this.id = id;
		this.usuario = usuario;
		this.contrasena = contraseña;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.rol = rol;
	}

	/**
	 * Obtiene el nombre de usuario del cliente.
	 *
	 * @return el nombre de usuario del cliente
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Establece el nombre de usuario del cliente.
	 *
	 * @param usuario el nuevo nombre de usuario
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * Obtiene la contraseña del cliente.
	 *
	 * @return la contraseña del cliente
	 */
	public String getContrasena() {
		return contrasena;
	}

	/**
	 * Establece la contraseña del cliente.
	 *
	 * @param contraseña la nueva contraseña
	 */
	public void setContrasena(String contraseña) {
		this.contrasena = contraseña;
	}

	/**
	 * Obtiene el nombre del cliente.
	 *
	 * @return el nombre del cliente
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del cliente.
	 *
	 * @param nombre el nuevo nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene la dirección del cliente.
	 *
	 * @return la dirección del cliente
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Establece la dirección del cliente.
	 *
	 * @param direccion la nueva dirección
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * Obtiene el número de teléfono del cliente.
	 *
	 * @return el número de teléfono del cliente
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Establece el número de teléfono del cliente.
	 *
	 * @param telefono el nuevo número de teléfono
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * Obtiene el ID del cliente.
	 *
	 * @return el ID del cliente
	 */
	public int getId() {
		return id;
	}

	/**
	 * Establece el ID del cliente.
	 *
	 * @param id el nuevo ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtiene el rol del cliente.
	 *
	 * @return el rol del cliente
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * Establece el rol del cliente.
	 *
	 * @param rol el nuevo rol
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * Devuelve una representación en forma de cadena del objeto Cliente.
	 *
	 * @return una representación en forma de cadena del objeto Cliente
	 */
	@Override
	public String toString() {
		return "Cliente [id=" + id + ", usuario=" + usuario + ", contrasena=" + contrasena + ", nombre=" + nombre
				+ ", direccion=" + direccion + ", telefono=" + telefono + ", rol=" + rol + "]";
	}
}
