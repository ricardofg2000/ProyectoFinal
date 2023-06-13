package model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String usuario;
    private String contraseña;
    private String nombre;
    private String direccion;
    private String telefono;

    public Cliente(String usuario, String contraseña, String nombre, String direccion, String telefono) {
        this.usuario = usuario;
        this.contraseña = contraseña;
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

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
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
}
