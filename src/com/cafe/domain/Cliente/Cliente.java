package com.cafe.domain.Cliente;

public class Cliente {
    private int id;
    private String nombre;
    private String telefono;
    private String email;
    private int puntos;

    public Cliente(int id, String nombre, String telefono, String email, int puntos) {
        this.id = id; this.nombre = nombre; this.telefono = telefono; this.email = email; this.puntos = puntos;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public int getPuntos() { return puntos; }
    public void setPuntos(int puntos) { this.puntos = puntos; }

}
