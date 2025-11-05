package com.cafe.domain.Ventas;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    private int id;
    private LocalDateTime fecha = LocalDateTime.now();
    private final List<LineaVenta> lineas = new ArrayList<>();

    //Constructor
    //Metodo agregarLinea para agregar una linea de venta a la lista
    public void agregarLinea(LineaVenta l) { lineas.add(l); }
    public List<LineaVenta> getLineas() { return lineas; }

    //Metodo para calcular el total de la venta sumando los subtotales de cada linea
    public double getTotal() { return lineas.stream().mapToDouble(LineaVenta::getSubtotal).sum(); }
    public LocalDateTime getFecha() { return fecha; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
}