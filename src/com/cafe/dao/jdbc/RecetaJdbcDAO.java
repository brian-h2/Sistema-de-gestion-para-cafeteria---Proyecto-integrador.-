package com.cafe.dao.jdbc;

import com.cafe.config.DB;
import com.cafe.dao.RecetaDAO;
import com.cafe.domain.producto.*;

import java.sql.*;

public class RecetaJdbcDAO implements RecetaDAO {

    @Override
    public Receta obtenerPorBebida(int idBebida) {
        Receta receta = new Receta(idBebida);
        String sql = "SELECT r.cantidad, i.id, i.nombre, i.stock, i.unidad "
                + "FROM receta_bebida r "
                + "JOIN ingredientes i ON i.id = r.id_ingrediente "
                + "WHERE r.id_bebida = ?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idBebida);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ingrediente ing = new Ingrediente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("stock"),
                        rs.getString("unidad")
                    );
                    receta.agregarIngrediente(ing, rs.getDouble("cantidad"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return receta;
    }

    @Override
    public boolean guardarReceta(Receta receta) {
        String del = "DELETE FROM receta_bebida WHERE id_bebida=?";
        String ins = "INSERT INTO receta_bebida (id_bebida, id_ingrediente, cantidad) VALUES (?, ?, ?)";
        try (Connection con = DB.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement psDel = con.prepareStatement(del);
                 PreparedStatement psIns = con.prepareStatement(ins)) {
                psDel.setInt(1, receta.getIdBebida());
                psDel.executeUpdate();

                for (Receta.IngredienteCantidad ic : receta.getIngredientes()) {
                    psIns.setInt(1, receta.getIdBebida());
                    psIns.setInt(2, ic.getIngrediente().getId());
                    psIns.setDouble(3, ic.getCantidad());
                    psIns.addBatch();
                }
                psIns.executeBatch();
                con.commit();
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

    @Override
    public boolean eliminarReceta(int idBebida) {
        String sql = "DELETE FROM receta_bebida WHERE id_bebida=?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idBebida);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}
