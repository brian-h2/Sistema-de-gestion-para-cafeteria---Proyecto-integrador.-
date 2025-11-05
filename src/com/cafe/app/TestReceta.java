package com.cafe.app;

import com.cafe.dao.jdbc.*;
import com.cafe.domain.producto.*;

public class TestReceta {
    public static void main(String[] args) {
        RecetaJdbcDAO dao = new RecetaJdbcDAO();
        Receta receta = dao.obtenerPorBebida(1); // Latte

        System.out.println("=== Receta del Latte ===");
        for (Receta.IngredienteCantidad ic : receta.getIngredientes()) {
            System.out.println("- " + ic.getIngrediente().getNombre() + ": " + ic.getCantidad() + " " + ic.getIngrediente().getUnidad());
        }
    }
}
