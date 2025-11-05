package com.cafe.dao;
import com.cafe.config.DB;
import com.cafe.domain.Usuario.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioDAO {
    public Usuario validar(String username, String password) {
        String sql = "SELECT * FROM usuarios WHERE username = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String passDB = rs.getString("password");
                if (org.mindrot.jbcrypt.BCrypt.checkpw(password, passDB)) {
                    Usuario u = new Usuario(0, null, null, null);
                    u.setId(rs.getInt("id"));
                    u.setUsername(rs.getString("username"));
                    u.setRol(rs.getString("rol"));
                    return u;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
    
    public boolean insertar(Usuario u) {
    String sql = "INSERT INTO usuarios (username, password, rol) VALUES (?, ?, ?)";
    try (Connection conn = DB.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        String hashed = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());
        ps.setString(1, u.getUsername());
        ps.setString(2, hashed);
        ps.setString(3, u.getRol());

        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
    }

    
    public boolean actualizar(Usuario u) {
        String sql;
        boolean tienePassword = u.getPassword() != null && !u.getPassword().isEmpty();

        if (tienePassword)
            sql = "UPDATE usuarios SET username=?, password=?, rol=? WHERE id=?";
        else
            sql = "UPDATE usuarios SET username=?, rol=? WHERE id=?";

        try (Connection conn = DB.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            if (tienePassword) {
                String hashed = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());
                ps.setString(2, hashed);
                ps.setString(3, u.getRol());
                ps.setInt(4, u.getId());
            } else {
                ps.setString(2, u.getRol());
                ps.setInt(3, u.getId());
            }

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE id=?";
        try (Connection conn = DB.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, username, rol FROM usuarios ORDER BY id";
        try (Connection conn = DB.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuario u = new Usuario(0, null, null, null);
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setRol(rs.getString("rol"));
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Usuario obtenerPorUsername(String username) {
    String sql = "SELECT * FROM usuarios WHERE username = ?";
    try (Connection conn = DB.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Usuario u = new Usuario(0, null, null, null);
            u.setId(rs.getInt("id"));
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));
            u.setRol(rs.getString("rol"));
            return u;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

}
