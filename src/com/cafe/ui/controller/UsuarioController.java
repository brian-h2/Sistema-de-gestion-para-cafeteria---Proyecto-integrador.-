package com.cafe.ui.controller;

import com.cafe.dao.UsuarioDAO;
import com.cafe.domain.Usuario.*;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UsuarioController {

    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, Integer> colId;
    @FXML private TableColumn<Usuario, String> colUsername;
    @FXML private TableColumn<Usuario, String> colRol;

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private ComboBox<String> cbRol;
    @FXML private Label lblMensaje;

    private UsuarioDAO dao = new UsuarioDAO();
    private ObservableList<Usuario> listaUsuarios;
    private Usuario usuarioSeleccionado = null;

    @FXML
    public void initialize() {
        cbRol.getItems().addAll("Administrador", "Empleado");

        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        colUsername.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getUsername()));
        colRol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getRol()));

        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSel, newSel) -> cargarSeleccion(newSel)
        );

        actualizarTabla();
    }

    private void cargarSeleccion(Usuario u) {
        if (u != null) {
            usuarioSeleccionado = u;
            txtUsername.setText(u.getUsername());
            cbRol.setValue(u.getRol());
        }
    }

    @FXML
    private void nuevoUsuario() {
        usuarioSeleccionado = null;
        txtUsername.clear();
        txtPassword.clear();
        cbRol.setValue(null);
        lblMensaje.setText("");
    }

    @FXML
    private void guardarUsuario() {
        String user = txtUsername.getText();
        String pass = txtPassword.getText();
        String rol = cbRol.getValue();

        if (user.isEmpty() || rol == null) {
            lblMensaje.setText("⚠️ Complete los campos requeridos");
            lblMensaje.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        if (usuarioSeleccionado == null) {
            // Nuevo usuario
            Usuario nuevo = new Usuario(0, null, null, null);
            nuevo.setUsername(user);
            nuevo.setPassword(pass);
            nuevo.setRol(rol);
            if (dao.insertar(nuevo)) {
                lblMensaje.setText("✅ Usuario creado");
                lblMensaje.setTextFill(javafx.scene.paint.Color.GREEN);
            } else {
                lblMensaje.setText("❌ Error al guardar");
                lblMensaje.setTextFill(javafx.scene.paint.Color.RED);
            }
        } else {
            // Modificar existente
            usuarioSeleccionado.setUsername(user);
            if (!pass.isEmpty()) usuarioSeleccionado.setPassword(pass);
            usuarioSeleccionado.setRol(rol);
            if (dao.actualizar(usuarioSeleccionado)) {
                lblMensaje.setText("✅ Usuario actualizado");
                lblMensaje.setTextFill(javafx.scene.paint.Color.GREEN);
            } else {
                lblMensaje.setText("❌ Error al actualizar");
                lblMensaje.setTextFill(javafx.scene.paint.Color.RED);
            }
        }
        actualizarTabla();
        nuevoUsuario();
    }

    @FXML
    private void eliminarUsuario() {
        Usuario u = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (u == null) {
            lblMensaje.setText("⚠️ Seleccione un usuario");
            lblMensaje.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }
        if (dao.eliminar(u.getId())) {
            lblMensaje.setText("🗑️ Usuario eliminado");
            lblMensaje.setTextFill(javafx.scene.paint.Color.GREEN);
            actualizarTabla();
        } else {
            lblMensaje.setText("❌ No se pudo eliminar");
            lblMensaje.setTextFill(javafx.scene.paint.Color.RED);
        }
    }

    @FXML
    private void actualizarTabla() {
        listaUsuarios = FXCollections.observableArrayList(dao.listar());
        tablaUsuarios.setItems(listaUsuarios);
    }
}
