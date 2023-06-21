package model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
	private int id;
	private String usuario;
	private String contrasena;
	private String nombre;
	private String direccion;
	private String telefono;

	public Cliente() {
	}

	public Cliente(String usuario, String contraseña, String nombre, String direccion, String telefono) {
		this.usuario = usuario;
		this.contrasena = contraseña;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
	}

	public Cliente(int id, String usuario, String contraseña, String nombre, String direccion, String telefono) {
		this.id = id;
		this.usuario = usuario;
		this.contrasena = contraseña;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contraseña) {
		this.contrasena = contraseña;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", usuario=" + usuario + ", contrasena=" + contrasena + ", nombre=" + nombre
				+ ", direccion=" + direccion + ", telefono=" + telefono + "]";
	}

	
}
