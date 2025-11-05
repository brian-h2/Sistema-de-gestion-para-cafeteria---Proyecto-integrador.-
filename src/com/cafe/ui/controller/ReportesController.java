package com.cafe.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.cafe.config.DB;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;


public class ReportesController {
    @FXML private DatePicker dpDesde, dpHasta;
    @FXML private Button btnBuscar, btnExportar;
    @FXML private TableView<VentaRow> tablaVentas;
    @FXML private TableColumn<VentaRow, Integer> colId;
    @FXML private TableColumn<VentaRow, String> colFecha, colCliente;
    @FXML private TableColumn<VentaRow, Double> colTotal;
    @FXML private Label lblTotalGeneral;

    private final List<VentaRow> listaVentas = new ArrayList<>();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().id));
        colFecha.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().fecha));
        colCliente.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().cliente));
        colTotal.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().total));

        btnBuscar.setOnAction(e -> buscarVentas());
        btnExportar.setOnAction(e -> exportarPDF());
    }

    private void buscarVentas() {
        LocalDate desde = dpDesde.getValue();
        LocalDate hasta = dpHasta.getValue();
        if (desde == null || hasta == null) {
            alert("Seleccione ambas fechas");
            return;
        }

        listaVentas.clear();
        String sql = """
            SELECT v.id, v.fecha, IFNULL(c.nombre,'-') AS cliente, v.total
            FROM ventas v
            LEFT JOIN clientes c ON c.id = v.id_cliente
            WHERE DATE(v.fecha) BETWEEN ? AND ?
            ORDER BY v.fecha
        """;

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(desde));
            ps.setDate(2, java.sql.Date.valueOf(hasta));
            ResultSet rs = ps.executeQuery();
            double totalGeneral = 0;
            while (rs.next()) {
                VentaRow v = new VentaRow(
                    rs.getInt("id"),
                    rs.getString("fecha"),
                    rs.getString("cliente"),
                    rs.getDouble("total")
                );
                listaVentas.add(v);
                totalGeneral += v.total;
            }
            tablaVentas.getItems().setAll(listaVentas);
            lblTotalGeneral.setText(String.format("Total general: $%.2f", totalGeneral));
        } catch (SQLException e) {
            e.printStackTrace();
            alert("Error al buscar ventas");
        }
    }

    private void exportarPDF() {
        if (listaVentas.isEmpty()) {
            alert("No hay datos para exportar");
            return;
        }

        try {
            String path = "Reporte_Ventas.pdf";

            // Crear PDF moderno
            PdfWriter writer = new PdfWriter(path);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document doc = new Document(pdfDoc);

            // Fuente del título
            PdfFont font = PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA_BOLD);
            Paragraph titulo = new Paragraph("Reporte de Ventas")
                    .setFont(font)
                    .setFontSize(18);
                    // .setTextAlignment(TextAlignment.CENTER);
            doc.add(titulo);
            doc.add(new Paragraph("\n"));

            // Crear tabla
            Table table = new Table(4).useAllAvailableWidth();
            table.addHeaderCell("ID");
            table.addHeaderCell("Fecha");
            table.addHeaderCell("Cliente");
            table.addHeaderCell("Total");

            double totalGeneral = 0;
            for (VentaRow v : listaVentas) {
                table.addCell(String.valueOf(v.id));
                table.addCell(v.fecha);
                table.addCell(v.cliente);
                table.addCell(String.format("$%.2f", v.total));
                totalGeneral += v.total;
            }

            doc.add(table);
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph(String.format("Total general: $%.2f", totalGeneral)));

            doc.close();
            alert("✅ PDF generado: " + path);

        } catch (Exception e) {
            e.printStackTrace();
            alert("Error generando PDF: " + e.getMessage());
        }

    }

    private void alert(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }

    public static class VentaRow {
        int id; String fecha; String cliente; double total;
        public VentaRow(int id, String fecha, String cliente, double total) {
            this.id = id; this.fecha = fecha; this.cliente = cliente; this.total = total;
        }
    }
}
