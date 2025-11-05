package com.cafe.domain.producto;

public class Ingrediente {
    private int id;
    private String nombre;
    private double stock;
    private String unidad;

    public Ingrediente(int id, String nombre, double stock, String unidad) {
        this.id = id; this.nombre = nombre; this.stock = stock; this.unidad = unidad;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getStock() { return stock; }
    public String getUnidad() { return unidad; }

    public void restarStock(double cantidad) { stock -= cantidad; }
}
