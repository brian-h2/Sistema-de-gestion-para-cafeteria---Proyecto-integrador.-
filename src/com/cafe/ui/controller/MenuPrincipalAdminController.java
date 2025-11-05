package com.cafe.ui.controller;

import com.cafe.ui.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;

public class MenuPrincipalAdminController {

    @FXML private Button btnCerrar;


    @FXML
    private void abrirProductos() {
        abrirVentana("/com/cafe/ui/view/Productos.fxml", "Gestión de Productos");
    }

    @FXML
    private void abrirClientes() {
        abrirVentana("/com/cafe/ui/view/Clientes.fxml", "Gestión de Clientes");
    }

    @FXML
    private void abrirReportes() {
        abrirVentana("/com/cafe/ui/view/Reportes.fxml", "Reportes de Ventas");
    }

    @FXML
    private void abrirUsuarios() {
        abrirVentana("/com/cafe/ui/view/Usuarios.fxml", "Gestión de Usuarios");
    }

    @FXML
       private void abrirVentas() {
        abrirVentana("/com/cafe/ui/view/Carrito.fxml", "Gestión de Ventas");
    }
    


    @FXML
    private void cerrarSesion() {
        try {
            Stage actual = (Stage) btnCerrar.getScene().getWindow();
            actual.close();
            new MainApp().start(new Stage()); // vuelve al login
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirVentana(String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(titulo);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
