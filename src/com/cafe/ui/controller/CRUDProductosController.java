package com.cafe.ui.controller;

import com.cafe.dao.ProductoDAO;
import com.cafe.dao.jdbc.ProductoJdbcDAO;
import com.cafe.domain.producto.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CRUDProductosController{

    @FXML private TableView<Producto> tablaProductos;
    @FXML private TableColumn<Producto, Integer> colId;
    @FXML private TableColumn<Producto, String> colNombre, colTipo;
    @FXML private TableColumn<Producto, Double> colPrecio;
    @FXML private TableColumn<Producto, Integer> colStock;
    @FXML private TextField txtNombre, txtPrecio, txtStock;
    @FXML private ComboBox<String> cbTipo;
    @FXML private Label lblMensaje;

    private ProductoDAO dao = new ProductoJdbcDAO();
    private Producto seleccionado;

    @FXML private Button NuevoProducto, GuardarProducto, EliminarProducto, ActualizarTabla;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getId()).asObject());
        colNombre.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNombre()));
        colTipo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getCategoria()));
        colPrecio.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(c.getValue().getPrecioBase()).asObject());
        colStock.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getStock()).asObject());

        cbTipo.getItems().addAll("bebidas calientes", "bebidas frías", "snacks", "postres");
        actualizarTabla();  

        NuevoProducto.setOnAction(e -> nuevoProducto());
        GuardarProducto.setOnAction(e -> guardarProducto());
        EliminarProducto.setOnAction(e -> eliminarProducto());
        ActualizarTabla.setOnAction(e -> actualizarTabla());

        tablaProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                seleccionado = newSel;
                txtNombre.setText(newSel.getNombre());
                cbTipo.setValue(newSel.getCategoria());
                txtPrecio.setText(String.valueOf(newSel.getPrecioBase()));
                txtStock.setText(String.valueOf(newSel.getStock()));
            }
        });
    }

    @FXML
    private void nuevoProducto() {
        tablaProductos.getSelectionModel().clearSelection();
        txtNombre.clear();
        txtPrecio.clear();
        txtStock.clear();
        cbTipo.setValue(null);
        seleccionado = null;
    }

    @FXML
    private void guardarProducto() {
        try {
            String nombre = txtNombre.getText();
            String tipo = cbTipo.getValue();
            double precio = Double.parseDouble(txtPrecio.getText());
            int stock = Integer.parseInt(txtStock.getText());

            if (nombre.isEmpty() || tipo == null) {
                lblMensaje.setText("⚠️ Complete todos los campos");
                lblMensaje.setTextFill(javafx.scene.paint.Color.RED);
                return;
            }

            Producto p = new Producto(0, nombre, precio, stock, tipo);
            p.setNombre(nombre);
            p.setCategoria(tipo);
            p.setPrecioBase(precio);
            p.setStock(stock);

            boolean exito;
            if (seleccionado == null) { //Consultar si aca deberia ir un insert o un update
                exito = dao.insert(p);
            } else {
                p.setId(seleccionado.getId());
                exito = dao.update(p);
            }

            if (exito) {
                lblMensaje.setText("✅ Guardado correctamente");
                lblMensaje.setTextFill(javafx.scene.paint.Color.GREEN);
                actualizarTabla();
                nuevoProducto();
            } else {
                lblMensaje.setText("❌ Error al guardar");
                lblMensaje.setTextFill(javafx.scene.paint.Color.RED);
            }
        } catch (NumberFormatException e) {
            lblMensaje.setText("⚠️ Precio o stock inválido");
            lblMensaje.setTextFill(javafx.scene.paint.Color.RED);
        }
    }

    @FXML
    private void eliminarProducto() {
        if (seleccionado != null) {
            if (dao.delete(seleccionado.getId())) {
                lblMensaje.setText("🗑️ Producto eliminado");
                actualizarTabla();
                nuevoProducto();
            }
        }
    }

    @FXML
    private void actualizarTabla() {
        ObservableList<Producto> lista = FXCollections.observableArrayList(dao.findAll());
        tablaProductos.setItems(lista);
    }
}
