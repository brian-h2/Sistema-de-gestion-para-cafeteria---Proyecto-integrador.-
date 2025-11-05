package com.cafe.dao;
import com.cafe.domain.producto.Producto;
import java.util.*;

public interface ProductoDAO {
    List<Producto> findAll();
    public boolean delete(int id);
    public boolean update(Producto producto);
    public boolean insert(Producto producto);
}