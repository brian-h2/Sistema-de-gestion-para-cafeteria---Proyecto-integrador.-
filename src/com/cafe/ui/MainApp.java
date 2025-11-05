package com.cafe.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // 👇 Arranca directamente desde la pantalla de Login
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cafe/ui/view/Login.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("☕ Login - Cafetería");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // 🔹 Abre el panel para administrador
    public void abrirPrincipalAdmin() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cafe/ui/view/MenuPrincipalAdmin.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Panel Administrador");
        stage.show();
    }

    // 🔹 Abre el panel para empleado
    public void abrirPrincipalEmpleado() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cafe/ui/view/MenuPrincipalEmpleado.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Panel Empleado");
        stage.show();
    }
}
