package com.cafe.dao.jdbc;

import com.cafe.config.DB;
import com.cafe.dao.IngredienteDAO;
import com.cafe.domain.producto.Ingrediente;
import java.sql.*;
import java.util.*;

public class IngredienteJdbcDAO implements IngredienteDAO {
    @Override
    public List<Ingrediente> findAll() {
        List<Ingrediente> lista = new ArrayList<>();
        try (Connection con = DB.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM ingredientes")) {
            while (rs.next()) {
                lista.add(new Ingrediente(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getDouble("stock"),
                    rs.getString("unidad")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    @Override
    public boolean insert(Ingrediente i) {
        String sql = "INSERT INTO ingredientes(nombre,stock,unidad) VALUES (?,?,?)";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, i.getNombre());
            ps.setDouble(2, i.getStock());
            ps.setString(3, i.getUnidad());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean update(Ingrediente i) {
        String sql = "UPDATE ingredientes SET nombre=?,stock=?,unidad=? WHERE id=?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, i.getNombre());
            ps.setDouble(2, i.getStock());
            ps.setString(3, i.getUnidad());
            ps.setInt(4, i.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM ingredientes WHERE id=?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}
