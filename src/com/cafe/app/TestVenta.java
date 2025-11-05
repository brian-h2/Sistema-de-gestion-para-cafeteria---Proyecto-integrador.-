package com.cafe.app;

import com.cafe.dao.jdbc.*;
import com.cafe.domain.Ventas.*;
import com.cafe.domain.producto.*;

public class TestVenta {
    public static void main(String[] args) {
        ProductoJdbcDAO prodDao = new ProductoJdbcDAO();
        VentaJdbcDAO ventaDao = new VentaJdbcDAO();

        // Obtenemos productos de la BD
        Producto latte = prodDao.findById(1).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        Producto brownie = prodDao.findById(2).orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Armamos la venta
        Venta v = new Venta();
        v.agregarLinea(new LineaVenta(latte, 2));   // 2 Lattes
        v.agregarLinea(new LineaVenta(brownie, 1)); // 1 Brownie

        System.out.println("Registrando venta total: $" + v.getTotal());
        ventaDao.registrarVenta(v);
    }
}
