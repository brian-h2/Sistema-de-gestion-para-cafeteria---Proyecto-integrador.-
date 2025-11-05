package com.cafe.domain.producto;

import java.util.ArrayList;
import java.util.List;

public class Receta {
    private int idBebida;
    private final List<IngredienteCantidad> ingredientes = new ArrayList<>();

    public Receta(int idBebida) {
        this.idBebida = idBebida;
    }

    public int getIdBebida() { return idBebida; }
    public List<IngredienteCantidad> getIngredientes() { return ingredientes; }

    public void agregarIngrediente(Ingrediente i, double cantidad) {
        ingredientes.add(new IngredienteCantidad(i, cantidad));
    }

    public static class IngredienteCantidad {
        private Ingrediente ingrediente;
        private double cantidad;

        public IngredienteCantidad(Ingrediente ingrediente, double cantidad) {
            this.ingrediente = ingrediente;
            this.cantidad = cantidad;
        }

        public Ingrediente getIngrediente() { return ingrediente; }
        public double getCantidad() { return cantidad; }
    }
}
