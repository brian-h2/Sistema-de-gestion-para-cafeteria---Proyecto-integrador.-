package com.cafe.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.cafe.dao.jdbc.ClienteJdbcDAO;
import com.cafe.domain.Cliente.Cliente;
import java.util.List;

public class ClientesController {
    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colTelefono;
    @FXML private TableColumn<Cliente, String> colEmail;
    @FXML private TableColumn<Cliente, Integer> colPuntos;
    @FXML private TextField txtNombre, txtTelefono, txtEmail;
    @FXML private Button btnAgregar, btnEliminar, btnEditar, btnVolver;

    private final ClienteJdbcDAO dao = new ClienteJdbcDAO();

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNombre()));
        colTelefono.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTelefono()));
        colEmail.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getEmail()));
        colPuntos.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getPuntos()));

        cargarClientes();

        btnAgregar.setOnAction(e -> agregarCliente());
        btnEliminar.setOnAction(e -> eliminarCliente());
        btnEditar.setOnAction(e -> editarCliente());
        btnVolver.setOnAction(e -> ((Stage)btnVolver.getScene().getWindow()).close());
    }

    private void cargarClientes() {
        List<Cliente> lista = dao.findAll();
        tablaClientes.getItems().setAll(lista);
    }

    private void agregarCliente() {
        if (txtNombre.getText().isEmpty()) {
            alert("Ingrese el nombre del cliente");
            return;
        }
        Cliente nuevo = new Cliente(0, txtNombre.getText(), txtTelefono.getText(), txtEmail.getText(), 0);
        if (dao.insert(nuevo)) {
            alert("Cliente agregado");
            limpiarCampos();
            cargarClientes();
        } else alert("Error al agregar cliente");
    }

    private void eliminarCliente() {
        Cliente sel = tablaClientes.getSelectionModel().getSelectedItem();
        if (sel == null) { alert("Seleccione un cliente"); return; }
        dao.delete(sel.getId());
        cargarClientes();
    }

    private void editarCliente() {
        Cliente sel = tablaClientes.getSelectionModel().getSelectedItem();
        if (sel == null) { alert("Seleccione un cliente"); return; }

        TextInputDialog dialog = new TextInputDialog(sel.getNombre());
        dialog.setHeaderText("Editar nombre");
        dialog.setContentText("Nuevo nombre:");
        dialog.showAndWait().ifPresent(nuevoNombre -> {
            Cliente actualizado = new Cliente(sel.getId(), nuevoNombre, sel.getTelefono(), sel.getEmail(), sel.getPuntos());
            dao.update(actualizado);
            cargarClientes();
        });
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtTelefono.clear();
        txtEmail.clear();
    }

    private void alert(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }
}
