package com.ricoom.barcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.ricoom.barcode.model.Product;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;

public class BarcodePdfGridGenerator extends Application {

    private final ObservableList<Product> productList = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Batch new Barcode Generator");

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        TableView<Product> tableView = new TableView<>(productList);

        TableColumn<Product, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> data.getValue().nameProperty());
        nameCol.setPrefWidth(200);

        TableColumn<Product, String> barcodeCol = new TableColumn<>("Barcode");
        barcodeCol.setCellValueFactory(data -> data.getValue().barcodeProperty());
        barcodeCol.setPrefWidth(150);

        // Add price column
        TableColumn<Product, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(data -> data.getValue().priceProperty().asObject());
        priceCol.setPrefWidth(80);
        // Format price display with 2 decimal places
        priceCol.setCellFactory(column -> new TableCell<Product, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText(String.format("Kes%.2f", price));
                }
            }
        });

        TableColumn<Product, Integer> quantityCol = new TableColumn<>("Qty");
        quantityCol.setCellValueFactory(data -> data.getValue().quantityProperty().asObject());
        quantityCol.setPrefWidth(60);

        tableView.getColumns().addAll(nameCol, barcodeCol, priceCol, quantityCol);

        // Buttons
        Button addBtn = new Button("+ Add Product");
        addBtn.setOnAction(e -> addProductDialog(stage));

        Button generateBtn = new Button("Generate PDF");
        generateBtn.setOnAction(e -> {
            if (productList.isEmpty()) {
                showAlert("No products", "Please add at least one product.");
                return;
            }
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                try {
                    generateA4BarcodeGridPdf(productList, file.getAbsolutePath());
                    showAlert("Success", "PDF generated: " + file.getAbsolutePath());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    showAlert("Error", "Failed to generate PDF: " + ex.getMessage());
                }
            }
        });
        
        Button exportCsvBtn = new Button("Export CSV");
        exportCsvBtn.setOnAction(e -> exportToCSV(stage));

        Button exportExcelBtn = new Button("Export Excel");
        exportExcelBtn.setOnAction(e -> exportToExcel(stage));

        HBox buttons = new HBox(10, addBtn, generateBtn, exportCsvBtn, exportExcelBtn);
        buttons.setAlignment(Pos.CENTER);

        root.getChildren().addAll(tableView, buttons);

        Scene scene = new Scene(root, 550, 400); // Increased width for price column
        stage.setScene(scene);
        stage.show();
    }
    
    private void exportToCSV(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        fileChooser.setInitialFileName("products.csv");

        File file = fileChooser.showSaveDialog(stage);
        if (file == null) return;

        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("Name,Barcode,Price,Quantity"); // Added Price header

            for (Product p : productList) {
                writer.println(
                    p.getName() + "," +
                    p.getBarcode() + "," +
                    String.format("%.2f", p.getPrice()) + "," + // Format price with 2 decimals
                    p.getQuantity()
                );
            }

            showAlert("Export Successful", "CSV saved to:\n" + file.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Export Error", "Could not write CSV:\n" + e.getMessage());
        }
    }

    
    private void exportToExcel(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel File");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Excel Files", "*.xlsx")
        );
        fileChooser.setInitialFileName("products.xlsx");

        File file = fileChooser.showSaveDialog(stage);
        if (file == null) return;

        try (org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {

            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Products");

            // Header - Added Price
            org.apache.poi.ss.usermodel.Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Name");
            header.createCell(1).setCellValue("Barcode");
            header.createCell(2).setCellValue("Price");
            header.createCell(3).setCellValue("Quantity");

            // Rows
            int rowIndex = 1;
            for (Product p : productList) {
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(p.getName());
                row.createCell(1).setCellValue(p.getBarcode());
                row.createCell(2).setCellValue(p.getPrice()); // Add price
                row.createCell(3).setCellValue(p.getQuantity());
            }

            // Autosize
            for (int i = 0; i < 4; i++) { // Changed to 4 columns
                sheet.autoSizeColumn(i);
            }

            // Write file
            try (java.io.FileOutputStream fos = new java.io.FileOutputStream(file)) {
                workbook.write(fos);
            }

            showAlert("Export Successful", "Excel saved to:\n" + file.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Export Error", "Failed to write Excel:\n" + e.getMessage());
        }
    }

    private void addProductDialog(Stage owner) {
        Dialog<Product> dialog = new Dialog<>();
        dialog.setTitle("Add Product");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        TextField nameField = new TextField();
        nameField.setPromptText("Product Name");

        TextField barcodeField = new TextField();
        barcodeField.setPromptText("Barcode");

        Button genBarcodeBtn = new Button("Generate");
        genBarcodeBtn.setOnAction(e -> barcodeField.setText(generateRandomBarcode()));

        HBox barcodeBox = new HBox(5, barcodeField, genBarcodeBtn);

        // Add price field
        TextField priceField = new TextField();
        priceField.setPromptText("0.00");
        
        // Add validation for price
        priceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d{0,2})?")) {
                priceField.setText(oldValue);
            }
        });

        Spinner<Integer> quantitySpinner = new Spinner<>(1, 1000, 1);

        vbox.getChildren().addAll(
                new Label("Name:"), nameField,
                new Label("Barcode:"), barcodeBox,
                new Label("Price (Kes):"), priceField,
                new Label("Quantity:"), quantitySpinner
        );

        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Enable OK button only when required fields are filled
        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);

        // Validate inputs
        nameField.textProperty().addListener((obs, oldVal, newVal) -> validateInputs(nameField, priceField, okButton));
        priceField.textProperty().addListener((obs, oldVal, newVal) -> validateInputs(nameField, priceField, okButton));

        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                Product p = new Product();
                p.setName(nameField.getText());
                p.setBarcode(barcodeField.getText());
                try {
                    p.setPrice(Double.parseDouble(priceField.getText()));
                } catch (NumberFormatException e) {
                    p.setPrice(0.0); // Default to 0 if invalid
                }
                p.setQuantity(quantitySpinner.getValue());
                return p;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(productList::add);
    }

    private void validateInputs(TextField nameField, TextField priceField, Button okButton) {
        boolean isValid = !nameField.getText().trim().isEmpty() && 
                         !priceField.getText().trim().isEmpty();
        okButton.setDisable(!isValid);
    }

    private String generateRandomBarcode() {
        long randomCode = (long) (Math.random() * 1_000_000_000_000L) + 890000000000L;
        return String.valueOf(randomCode);
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    // -------------------- PDF Generation --------------------
    public static void generateA4BarcodeGridPdf(List<Product> products, String pdfPath) throws IOException, WriterException {
        PdfWriter writer = new PdfWriter(pdfPath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);
        document.setMargins(20, 20, 20, 20);

        Table table = createNewTable();
        int rowCounter = 0;

        for (Product product : products) {
            for (int i = 0; i < product.getQuantity(); i++) {
                Cell cell = createProductCell(product);
                table.addCell(cell);
                rowCounter++;
                if (rowCounter % 30 == 0) {
                    document.add(table);
                    document.add(new AreaBreak());
                    table = createNewTable();
                }
            }
        }

        if (rowCounter % 30 != 0) {
            document.add(table);
        }

        document.close();
    }

    private static Table createNewTable() {
        float[] columnWidths = {1, 1, 1};
        return new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();
    }

    private static Cell createProductCell(Product product) throws WriterException, IOException {
        BufferedImage barcodeImage = generateBarcode(product.getBarcode(), 350, 100);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(barcodeImage, "PNG", baos);
        Image pdfImage = new Image(ImageDataFactory.create(baos.toByteArray()));
        pdfImage.setAutoScale(true).setHorizontalAlignment(HorizontalAlignment.CENTER);

        String displayName = product.getName();
        if (displayName != null && displayName.length() > 30) {
            displayName = displayName.substring(0, 27) + "...";
        }

        // Format price with 2 decimal places
        String formattedPrice = String.format("Kes%.2f", product.getPrice());
        
        // Create paragraphs for name and price
        Paragraph nameParagraph = new Paragraph(displayName)
                .setFontSize(7f)
                .setTextAlignment(TextAlignment.CENTER)
                .setMultipliedLeading(1.0f);
        
        Paragraph priceParagraph = new Paragraph(formattedPrice)
                .setFontSize(10f)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setMultipliedLeading(1.2f);

        return new Cell()
                .add(pdfImage)
                .add(nameParagraph)
                .add(priceParagraph)
                .setPadding(8)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
    }

    private static BufferedImage generateBarcode(String text, int width, int height) throws WriterException {
        Code128Writer writer = new Code128Writer();
        BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.CODE_128, width, height);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public static void main(String[] args) {
        launch();
    }
}