package com.cafe.dao.jdbc;

import com.cafe.config.DB;
import com.cafe.dao.VentaDAO;
import com.cafe.domain.Ventas.*;
import com.cafe.service.InventarioService;
import java.sql.*;

public class VentaJdbcDAO implements VentaDAO {
    private final InventarioService inventario = new InventarioService();

    @Override
    public boolean registrarVenta(Venta venta) {
        String sqlVenta = "INSERT INTO ventas(total) VALUES(?)";
        String sqlLinea = "INSERT INTO lineas_venta(id_venta, id_producto, cantidad, subtotal) VALUES (?, ?, ?, ?)";
        try (Connection con = DB.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement psVenta = con.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS)) {
                psVenta.setDouble(1, venta.getTotal());
                psVenta.executeUpdate();

                ResultSet rs = psVenta.getGeneratedKeys();
                if (rs.next()) venta.setId(rs.getInt(1));

                try (PreparedStatement psLinea = con.prepareStatement(sqlLinea)) {
                    for (LineaVenta lv : venta.getLineas()) {
                        psLinea.setInt(1, venta.getId());
                        psLinea.setInt(2, lv.getProducto().getId());
                        psLinea.setInt(3, lv.getCantidad());
                        psLinea.setDouble(4, lv.getSubtotal());
                        psLinea.addBatch();

                        // Actualizar stock de producto
                        int nuevoStock = lv.getProducto().getStock() - lv.getCantidad();
                        if (nuevoStock < 0) nuevoStock = 0;
                        try (PreparedStatement psUpd = con.prepareStatement("UPDATE productos SET stock=? WHERE id=?")) {
                            psUpd.setInt(1, nuevoStock);
                            psUpd.setInt(2, lv.getProducto().getId());
                            psUpd.executeUpdate();
                        }

                        // Actualizar stock de ingredientes (si es bebida)
                        inventario.descontarStockPorBebida(lv.getProducto().getId());
                    }
                    psLinea.executeBatch();
                }
                con.commit();
                System.out.println("✅ Venta registrada correctamente (ID " + venta.getId() + ")");
                return true;
            } catch (SQLException e) {
                con.rollback();
                System.err.println("❌ Error registrando venta: " + e.getMessage());
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean registrarVentaConCliente(Venta venta, Integer idCliente) {
            String sqlVenta = "INSERT INTO ventas(total, id_cliente) VALUES(?, ?)";
            String sqlLinea = "INSERT INTO lineas_venta(id_venta, id_producto, cantidad, subtotal) VALUES (?, ?, ?, ?)";
            try (Connection con = DB.getConnection()) {
                con.setAutoCommit(false);
                try (PreparedStatement psVenta = con.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS)) {
                    psVenta.setDouble(1, venta.getTotal());
                    if (idCliente != null) psVenta.setInt(2, idCliente);
                    else psVenta.setNull(2, Types.INTEGER);
                    psVenta.executeUpdate();

                    ResultSet rs = psVenta.getGeneratedKeys();
                    if (rs.next()) venta.setId(rs.getInt(1));

                    try (PreparedStatement psLinea = con.prepareStatement(sqlLinea)) {
                        for (LineaVenta lv : venta.getLineas()) {
                            psLinea.setInt(1, venta.getId());
                            psLinea.setInt(2, lv.getProducto().getId());
                            psLinea.setInt(3, lv.getCantidad());
                            psLinea.setDouble(4, lv.getSubtotal());
                            psLinea.addBatch();
                        }
                        psLinea.executeBatch();
                    }

                    // Bonificación de puntos
                    if (idCliente != null) {
                        int puntos = (int)(venta.getTotal() / 100); // 1 punto cada $100
                        try (PreparedStatement psUpd = con.prepareStatement("UPDATE clientes SET puntos = puntos + ? WHERE id=?")) {
                            psUpd.setInt(1, puntos);
                            psUpd.setInt(2, idCliente);
                            psUpd.executeUpdate();
                        }
                    }

                    con.commit();
                    System.out.println("✅ Venta registrada correctamente (Cliente " + idCliente + ")");
                    return true;
                } catch (SQLException e) {
                    con.rollback();
                    e.printStackTrace();
                } finally {
                    con.setAutoCommit(true);
                }
            } catch (SQLException e) { e.printStackTrace(); }
            return false;
        }

}
