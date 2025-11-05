package com.cafe.ui.controller;

import com.cafe.dao.UsuarioDAO;
import com.cafe.domain.Usuario.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblMensaje;

    private UsuarioDAO dao = new UsuarioDAO();

    @FXML
    private void iniciarSesion() {
        String user = txtUsername.getText();
        String pass = txtPassword.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            lblMensaje.setText("⚠️ Complete todos los campos");
            return;
        }

        Usuario u = dao.obtenerPorUsername(user);
        if (u == null) {
            lblMensaje.setText("❌ Usuario no encontrado");
            return;
        }

        if (BCrypt.checkpw(pass, u.getPassword())) {
            lblMensaje.setText("✅ Bienvenido " + u.getUsername());
            abrirInterfazPorRol(u.getRol());
        } else {
            lblMensaje.setText("❌ Contraseña incorrecta");
        }
    }

    private void abrirInterfazPorRol(String rol) {
        try {
            Stage actual = (Stage) txtUsername.getScene().getWindow();
            actual.close();

            if ("Administrador".equalsIgnoreCase(rol)) {
                new com.cafe.ui.MainApp().abrirPrincipalAdmin();
            } else {
                new com.cafe.ui.MainApp().abrirPrincipalEmpleado();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
