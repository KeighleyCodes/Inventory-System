package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Inventory class - creates and stores part and product observable lists. */
public class Inventory {

    /** Creates part and product observable lists. */
    public static ObservableList<Part> allParts = FXCollections.observableArrayList();

    public static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Add part method.
     @param newPart Method for adding parts. */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /** Add product method.
     @param newProduct Method for adding products. */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /** Update product method.
     @param index
     @param product Method for updating product. */
    public static void updateProduct(int index, Product product) {
        allProducts.set(index, product);
    }

    /** Update part method.
     @param index
     @param part Method for updating parts. */
    public static void updatePart(int index, Part part) {
        allParts.set(index, part);
    }


    /** Get all parts method.
     @return the parts observable list. */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /** Get all products method.
     @return products observable list. */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /** Lookup part method.
     * @param partId Looks up part by ID
     * @return */
    // I chose to use a dynamic search bar to lookup part
    public static Part lookupPart(int partId) {
        Part part = null;
        for (Part p: getAllParts()) {
            if (p.getId() == partId) {
                part = p;
            }
        }
        return part;
    }

    /** Lookup product method.
     * @param productId Looks up product by ID
     * @return */
    // I chose to use a dynamic search bar to lookup part
    public static Product lookupProduct(int productId) {
        Product product = null;
        for (Product p: getAllProducts()) {
            if (p.getId() == productId) {
                product = p;
            }
        }
        return product;
    }

    /** Lookup part method.
     * @param name Looks up part by name
     * @return */
    // I chose to use a dynamic search bar to lookup part
    public static ObservableList lookupPart(String name) {
        ObservableList<Part> allParts = FXCollections.observableArrayList();
        for (Part p: getAllParts()) {
            if (p.getName() == name) {
                allParts.add(p);
            }
        }
        return allParts;
    }

    /** Lookup product method.
     * @param name Looks up product by name
     * @return */
    // I chose to use a dynamic search bar to lookup product
    public static ObservableList lookupProduct(String name) {
        ObservableList<Product> allProducts = FXCollections.observableArrayList();
        for (Product p: getAllProducts()) {
            if (p.getName() == name) {
                allProducts.add(p);
            }
        }
        return allProducts;
    }

    /** Deleted part method.
     @param selectedPart Deletes selected part. */
    public static boolean deletePart (Part selectedPart) {

        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /** Delete product method.
     @param selectedProduct Deletes product method. */
    public static boolean deleteProduct(Product selectedProduct) {

        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }

        else {
            return false;
        }
    }
}
