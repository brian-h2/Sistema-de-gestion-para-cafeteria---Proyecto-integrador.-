package com.cafe.dao.jdbc;

import com.cafe.config.DB;
import com.cafe.domain.producto.*;
import com.cafe.dao.ProductoDAO;

import java.sql.*;
import java.util.*;


//En esta clase se implementan los metodos definidos en la interfaz ProductoDAO
public class ProductoJdbcDAO implements ProductoDAO {
    @Override
    public List<Producto> findAll() {
        List<Producto> out = new ArrayList<>();
        String sql = "SELECT p.*, c.nombre AS cat FROM productos p JOIN categorias c ON c.id=p.categoria_id ORDER BY p.nombre";
        try (Connection con = DB.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String tipo = rs.getString("tipo");
                if ("BEBIDA".equalsIgnoreCase(tipo)) {
                    out.add(new Bebida(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("precio_base"),
                        rs.getInt("stock"),
                        rs.getString("cat"),
                        rs.getString("tamano"),
                        rs.getString("tipo_leche"),
                        rs.getBoolean("es_caliente")
                    ));
                } else {
                    out.add(new Alimento(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("precio_base"),
                        rs.getInt("stock"),
                        rs.getString("cat"),
                        rs.getBoolean("es_sin_tacc"),
                        rs.getBoolean("es_vegano")
                    ));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return out;
    }

    public Optional<Producto> findById(int id) {
        String sql = "SELECT * FROM productos WHERE id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new Producto(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getDouble("precio_base"),
                    rs.getInt("stock"),
                    rs.getString("categoria")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private int getCategoriaId(String categoriaNombre) throws SQLException {
        String sql = "SELECT id FROM categorias WHERE LOWER(nombre) = LOWER(?)";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, categoriaNombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    throw new SQLException("Categoría no encontrada: " + categoriaNombre);
                }
            }
        }
    }

   public boolean insert(Producto producto) {
    String sql = "INSERT INTO productos (nombre, precio_base, stock, categoria_id) " +
                 "VALUES (?, ?, ?, ?)";
        try (Connection con = DB.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecioBase());
            ps.setInt(3, producto.getStock());
            ps.setInt(4, getCategoriaId(producto.getCategoria()));
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean update(Producto p) {
        String sql = "UPDATE productos SET nombre=?, precio_base=?, stock=? WHERE id=?";
        try (Connection con = DB.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecioBase());
            ps.setInt(3, p.getStock());
            ps.setInt(4, p.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("Error actualizando producto: " + e.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
    String sql = "DELETE FROM productos WHERE id=?";
        try (Connection con = DB.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("Error eliminando producto: " + e.getMessage());
        }
    return false;
    }
}

