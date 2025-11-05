package com.cafe.domain.producto;

public class Producto {

    // Propiedades comunes para todos los productos
    protected int id;
    protected String nombre;
    protected double precioBase;
    protected int stock;
    protected String categoria; 

    // Constructor
    public Producto(int id, String nombre, double precioBase, int stock, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.stock = stock;
        this.categoria = categoria;
    }

    // Getters y Setters
    // Encapsulamiento
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public double getPrecioBase() { return precioBase; }
    public void setPrecioBase(double precioBase) { this.precioBase = precioBase; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    // Polimorfismo: subclases pueden modificar el precio base
    public double precioFinal() { return precioBase; }  
}