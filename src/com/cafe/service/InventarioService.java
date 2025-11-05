package com.cafe.service;

import com.cafe.dao.jdbc.*;
import com.cafe.domain.producto.*;

public class InventarioService {
    private final RecetaJdbcDAO recetaDAO = new RecetaJdbcDAO();
    private final IngredienteJdbcDAO ingDAO = new IngredienteJdbcDAO();

    public void descontarStockPorBebida(int idBebida) {
        Receta receta = recetaDAO.obtenerPorBebida(idBebida);
        for (Receta.IngredienteCantidad ic : receta.getIngredientes()) {
            Ingrediente ing = ic.getIngrediente();
            double nuevoStock = ing.getStock() - ic.getCantidad();
            if (nuevoStock < 0) nuevoStock = 0;
            ingDAO.update(new Ingrediente(ing.getId(), ing.getNombre(), nuevoStock, ing.getUnidad()));
        }
        System.out.println("Stock actualizado según receta de bebida ID " + idBebida);
    }
}
