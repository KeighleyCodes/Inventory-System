package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Product class - provides product objects and associated parts, set up per UML documentation. */
public class Product {

    /** @return associatedParts. */
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    /** @param assocPart
     */
    public void addAssociatedParts(Part assocPart) {
        associatedParts.add(assocPart);
    }

    /** @param assocPart
     */
    public void deleteAssociatedParts(Part assocPart) {
        associatedParts.remove(assocPart);
    }

    /** Set associated parts method.
     @param associatedParts Sets associated parts observable list. */
    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }

    /** Initializes associated parts observable list. */
    public ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private double price;
    private int stock;
    private int max;
    private int min;

    public Product(int id, String name, double price, int stock, int max, int min) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.max = max;
        this.min = min;
    }

    /** @return id */
    public int getId() {
        return id;
    }

    /** @param id */
    public void setId(int id) {
        this.id = id;
    }

    /** @return name */
    public String getName() {
        return name;
    }

    /** @param name */
    public void setName(String name) {
        this.name = name;
    }

    /** @return price */
    public double getPrice() {
        return price;
    }

    /** @param price */
    public void setPrice(double price) {
        this.price = price;
    }

    /** @return stock */
    public int getStock() {
        return stock;
    }

    /** @param stock */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** @return max */
    public int getMax() {
        return max;
    }

    /** @param max */
    public void setMax(int max) {
        this.max = max;
    }

    /** @return min */
    public int getMin() {
        return min;
    }

    /** @param min */
    public void setMin(int min) {
        this.min = min;
    }
}


