package com.cafe.app;

import com.cafe.dao.jdbc.ProductoJdbcDAO;

public class Main {
    public static void main(String[] args) {
        ProductoJdbcDAO dao = new ProductoJdbcDAO();
        dao.findAll().forEach(p -> System.out.println(p.getId()+" - "+p.getNombre()+" $"+p.getPrecioBase()));
    }
}
