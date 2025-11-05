package com.cafe.app;

import com.cafe.dao.jdbc.ProductoJdbcDAO;
import com.cafe.domain.producto.*;

public class TestCRUDProductos {
    public static void main(String[] args) {
        ProductoJdbcDAO dao = new ProductoJdbcDAO();

        System.out.println("=== LISTA INICIAL ===");
        dao.findAll().forEach(p -> System.out.println(p.getId() + " - " + p.getNombre()));

        System.out.println("\n=== INSERTANDO NUEVO PRODUCTO ===");
        Bebida nueva = new Bebida(0, "Flat White", 1900, 25, "bebidas calientes", "M", "almendra", true);
        dao.insert(nueva);

        System.out.println("\n=== PRODUCTOS TRAS INSERT ===");
        dao.findAll().forEach(p -> System.out.println(p.getId() + " - " + p.getNombre()));

        // System.out.println("\n=== ACTUALIZANDO PRODUCTO ID 1 ===");
        // dao.findById(1).ifPresent(p -> {
        //     p.setPrecioBase(p.getPrecioBase() + 100);
        //     dao.update(p);
        // });

        System.out.println("\n=== ELIMINANDO PRODUCTO ID 3 ===");
        dao.delete(3);

        System.out.println("\n=== PRODUCTOS FINALES ===");
        dao.findAll().forEach(p -> System.out.println(p.getId() + " - " + p.getNombre()));
    }
}
