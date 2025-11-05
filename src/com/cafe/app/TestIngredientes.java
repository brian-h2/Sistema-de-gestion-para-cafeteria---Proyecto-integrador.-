package com.cafe.app;
import com.cafe.dao.jdbc.IngredienteJdbcDAO;
import com.cafe.domain.producto.Ingrediente;

public class TestIngredientes {
    public static void main(String[] args) {
        IngredienteJdbcDAO dao = new IngredienteJdbcDAO();

        dao.insert(new Ingrediente(0, "Leche", 5000, "ml"));
        dao.insert(new Ingrediente(0, "Café molido", 2000, "g"));
        dao.insert(new Ingrediente(0, "Azúcar", 1000, "g"));

        dao.findAll().forEach(i ->
            System.out.println(i.getId()+" - "+i.getNombre()+" ("+i.getStock()+" "+i.getUnidad()+")")
        );
    }
}
