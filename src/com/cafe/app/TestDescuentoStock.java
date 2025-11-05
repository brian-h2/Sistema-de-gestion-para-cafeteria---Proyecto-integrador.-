package com.cafe.app;

import com.cafe.service.InventarioService;

public class TestDescuentoStock {
    public static void main(String[] args) {
        InventarioService service = new InventarioService();
        service.descontarStockPorBebida(1);
    }
}
