package com.cafe.dao;
import com.cafe.domain.producto.Ingrediente;
import java.util.*;

public interface IngredienteDAO {
    List<Ingrediente> findAll();
    boolean insert(Ingrediente i);
    boolean update(Ingrediente i);
    boolean delete(int id);
}
