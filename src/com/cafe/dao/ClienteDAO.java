package com.cafe.dao;

import java.util.List;

import com.cafe.domain.Cliente.Cliente;


public interface ClienteDAO {

    // Metodos para gestionar clientes (CRUD)
    List<Cliente> findAll();
    Cliente findById(int id);
    boolean insert(Cliente c);
    boolean update(Cliente c);
    boolean delete(int id);

}
