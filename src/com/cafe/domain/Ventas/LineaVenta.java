package com.cafe.domain.Ventas;

import com.cafe.domain.producto.Producto;

public class LineaVenta {
    //Final no permite modificar el valor de las variables una vez asignadas
    private final Producto producto;
    private final int cantidad;

    public LineaVenta(Producto producto, int cantidad) {
        this.producto = producto; this.cantidad = cantidad;
    }

    public double getSubtotal() { return producto.precioFinal() * cantidad; }
    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    
}
