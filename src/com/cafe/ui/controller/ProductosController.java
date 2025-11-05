package com.cafe.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import com.cafe.dao.jdbc.*;
import com.cafe.domain.producto.*;
import com.cafe.domain.Ventas.*;


import java.util.*;
import javafx.beans.property.*;


public class ProductosController {

    @FXML private TableView<Producto> tablaProductos;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, Double> colPrecio;
    @FXML private TableColumn<Producto, Integer> colStock;

    @FXML private TableView<LineaVenta> tablaCarrito;
    @FXML private TableColumn<LineaVenta, String> colCarritoNombre;
    @FXML private TableColumn<LineaVenta, Integer> colCarritoCant;
    @FXML private TableColumn<LineaVenta, Double> colCarritoSubtotal;

    @FXML private Button btnAgregar, btnRegistrarVenta, btnReportes;
    @FXML private Label lblTotal;
    @FXML private Button btnCerrar;

    @FXML private ComboBox<String> cmbClientes;
    @FXML private Button btnRefrescarClientes;

    @FXML private Button btnUsuarios;

    private final ProductoJdbcDAO dao = new ProductoJdbcDAO();
    private final VentaJdbcDAO ventaDao = new VentaJdbcDAO();

    private final List<LineaVenta> carrito = new ArrayList<>();

    private final ClienteJdbcDAO clienteDao = new ClienteJdbcDAO();



    @FXML
    public void initialize() {
        // Configurar columnas productos
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colPrecio.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getPrecioBase()));
        colStock.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getStock()));

        // Configurar columnas carrito
        colCarritoNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getProducto().getNombre()));
        colCarritoCant.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getCantidad()));
        colCarritoSubtotal.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getSubtotal()));

        cargarProductos();

        cargarClientesCombo();
        btnRefrescarClientes.setOnAction(e -> cargarClientesCombo());

        btnAgregar.setOnAction(e -> agregarAlCarrito());
        btnRegistrarVenta.setOnAction(e -> registrarVenta());
    }

    private void cargarClientesCombo() {
    cmbClientes.getItems().clear();
    clienteDao.findAll().forEach(c -> cmbClientes.getItems().add(c.getId() + " - " + c.getNombre()));
    }


    private void cargarProductos() {
        tablaProductos.getItems().setAll(dao.findAll());
    }

    private void agregarAlCarrito() {
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            alert("Seleccioná un producto primero");
            return;
        }

        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setHeaderText("Cantidad de " + seleccionado.getNombre());
        dialog.setContentText("Ingrese cantidad:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(cantStr -> {
            try {
                int cant = Integer.parseInt(cantStr);
                if (cant <= 0 || cant > seleccionado.getStock()) {
                    alert("Cantidad inválida o mayor al stock disponible");
                    return;
                }
                carrito.add(new LineaVenta(seleccionado, cant));
                tablaCarrito.getItems().setAll(carrito);
                actualizarTotal();
            } catch (NumberFormatException ex) {
                alert("Ingrese un número válido");
            }
        });
    }

    private void actualizarTotal() {
        double total = carrito.stream().mapToDouble(LineaVenta::getSubtotal).sum();
        lblTotal.setText(String.format("%.2f", total));
    }

    private void registrarVenta() {
        if (carrito.isEmpty()) {
            alert("El carrito está vacío");
            return;
        }

        Venta venta = new Venta();
        carrito.forEach(venta::agregarLinea);

        String clienteSel = cmbClientes.getValue();
        Integer idCliente = null;
        if (clienteSel != null && clienteSel.contains(" - ")) {
            idCliente = Integer.parseInt(clienteSel.split(" - ")[0]);
        }

        ventaDao.registrarVentaConCliente(venta, idCliente);

        if (ventaDao.registrarVenta(venta)) {
            alert("Venta registrada con éxito!");
            carrito.clear();
            tablaCarrito.getItems().clear();
            lblTotal.setText("0.00");
            cargarProductos();
        } else {
            alert("Error registrando la venta.");
        }
    }

    private void alert(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }

    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow(); 
        stage.close();
    }

}
