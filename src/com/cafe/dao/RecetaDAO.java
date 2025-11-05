package com.cafe.dao;

import com.cafe.domain.producto.Receta;

public interface RecetaDAO {
    Receta obtenerPorBebida(int idBebida);
    boolean guardarReceta(Receta receta);
    boolean eliminarReceta(int idBebida);
}
