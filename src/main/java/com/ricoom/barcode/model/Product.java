package com.ricoom.barcode.model;

import javafx.beans.property.*;

public class Product {

    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty barcode = new SimpleStringProperty("");
    private final IntegerProperty quantity = new SimpleIntegerProperty(1);
    private final DoubleProperty price = new SimpleDoubleProperty(0.0);
    private final DoubleProperty buyingPrice = new SimpleDoubleProperty(0.0);
    private final DoubleProperty wholesalePrice = new SimpleDoubleProperty(0.0);
    private final IntegerProperty stock = new SimpleIntegerProperty(0);
    private final StringProperty categoryName = new SimpleStringProperty("");
    private final LongProperty categoryId = new SimpleLongProperty(0);

    public Product() {}

    public Product(String name, String barcode) {
        this.name.set(name);
        this.barcode.set(barcode);
    }

    // --- Name ---
    public String getName() { return name.get(); }
    public void setName(String value) { name.set(value); }
    public StringProperty nameProperty() { return name; }

    // --- Barcode ---
    public String getBarcode() { return barcode.get(); }
    public void setBarcode(String value) { barcode.set(value); }
    public StringProperty barcodeProperty() { return barcode; }

    // --- Quantity ---
    public int getQuantity() { return quantity.get(); }
    public void setQuantity(int value) { quantity.set(value); }
    public IntegerProperty quantityProperty() { return quantity; }

    // --- Price ---
    public double getPrice() { return price.get(); }
    public void setPrice(double value) { price.set(value); }
    public DoubleProperty priceProperty() { return price; }

    // --- Buying Price ---
    public double getBuyingPrice() { return buyingPrice.get(); }
    public void setBuyingPrice(double value) { buyingPrice.set(value); }
    public DoubleProperty buyingPriceProperty() { return buyingPrice; }

    // --- Wholesale Price ---
    public double getWholesalePrice() { return wholesalePrice.get(); }
    public void setWholesalePrice(double value) { wholesalePrice.set(value); }
    public DoubleProperty wholesalePriceProperty() { return wholesalePrice; }

    // --- Stock ---
    public int getStock() { return stock.get(); }
    public void setStock(int value) { stock.set(value); }
    public IntegerProperty stockProperty() { return stock; }

    // --- Category Name ---
    public String getCategoryName() { return categoryName.get(); }
    public void setCategoryName(String value) { categoryName.set(value); }
    public StringProperty categoryNameProperty() { return categoryName; }

    // --- Category ID ---
    public long getCategoryId() { return categoryId.get(); }
    public void setCategoryId(long value) { categoryId.set(value); }
    public LongProperty categoryIdProperty() { return categoryId; }
}
