package controller;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.fxml.FXMLLoader.load;

/** Main form controller - initializes the main form UI where the program begins for the user. */
public class MainFormController implements Initializable {



    @FXML
    private Button addPartButton;

    @FXML
    private Button addProductButton;

    @FXML
    private TableColumn<Part, Double> partPriceColumn;

    @FXML
    private TableColumn<Product, Double> productPriceColumn;

    @FXML
    private Button deletePartButton;

    @FXML
    private Button deleteProductButton;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<Part, Integer> partStockColumn;

    @FXML
    private TableColumn<Product, Integer> productStockColumn;

    @FXML
    private TableView<Part> mainPartsTable;

    @FXML
    private TableView<Product> mainProductsTable;

    @FXML
    private Button modifyPartButton;

    @FXML
    private Button modifyProductButton;

    @FXML
    private TableColumn<Part, Integer> partIdColumn;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Product, Integer> productIdColumn;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private Button searchPartsButton;

    @FXML
    private Button searchProductsButton;

    @FXML
    private TextField searchPartsText;

    @FXML
    private TextField searchProductsText;

    /**
     * Add part method.
     *
     * @param event Adds part when add part button clicked.
     */
    @FXML
    void OnActionAddPart(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = load(Objects.requireNonNull(getClass().getResource("/view/AddParts.fxml")));
        stage.setScene(new Scene(scene));
        stage.setTitle("Add Parts");
        stage.show();
    }

    /**
     * Add product method.
     *
     * @param event Opens AddProduct Screen when add button clicked.
     */
    @FXML
    void OnActionAddProduct(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/AddProduct.fxml"));
        loader.load();

        AddProductController APController = loader.getController();
        APController.sendParts(mainPartsTable.getItems());

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.setTitle("Add Product");
        stage.show();
    }

    /**
     * Delete part method. Deletes part data when delete button clicked.
     */
    @FXML
    void OnActionDeletePart() {

        Part selectedPart = mainPartsTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deletePart(selectedPart);
        }
    }

    /**
     * Delete product method. Deletes product data when delete button clicked.
     */
    @FXML
    void OnActionDeleteProduct() {

        Product selectedProduct = mainProductsTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (selectedProduct.getAssociatedParts().size() > 0) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR, "Please remove associated parts before deleting");
                alert1.showAndWait();
            } else {
                Inventory.deleteProduct(selectedProduct);
            }
        }
    }

    /**
     * Exit method. Displays confirmation box and exits program when exit button clicked.
     */
    @FXML
    void OnActionExit() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }

    }

    /**
     * Modify part method.
     *
     * @param event Opens modify part screen when modify button clicked.
     */
    @FXML
    void OnActionModifyParts(ActionEvent event) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
            loader.load();

            ModifyPartController MPController = loader.getController();
            MPController.sendPart(mainPartsTable.getSelectionModel().getSelectedItem(), mainPartsTable.getSelectionModel().getSelectedIndex());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.setTitle("Modify Part");
            stage.show();
        } catch (NullPointerException n) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a part to modify");
            alert.show();
        }
    }

    /**
     * Modify product method.
     *
     * @param event Opens modify product screen when modify button clicked.
     */
    @FXML
    void OnActionModifyProducts(ActionEvent event) throws IOException {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
            loader.load();

            ModifyProductController MPSController = loader.getController();
            MPSController.sendProduct(mainProductsTable.getSelectionModel().getSelectedItem(), mainProductsTable.getSelectionModel().getSelectedIndex());

            mainProductsTable.getItems();

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.setTitle("Modify Product");
            stage.show();
        } catch (NullPointerException n) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a product to modify");
            alert.show();
        }
    }

    /**
     * Search parts method.
     * Dynamically searches for part after clicking search button.
     * FUTURE ENHANCEMENT: Have dynamic searching occur without needing to click search button.
     */
    @FXML
    void OnActionSearchParts() {

        // Sets search bar to visible
        searchPartsText.setVisible(true);

        // Wraps Observable List in Filtered List
        FilteredList<Part> filteredParts = new FilteredList<>(Inventory.allParts, part -> true);

        // Sets filter predicate when filter changes
        searchPartsText.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredParts.setPredicate(part -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                } else if (part.getName().toLowerCase().contains(newValue.toLowerCase()) ||
                        String.valueOf(part.getId()).contains(newValue.toLowerCase())) {
                    return true;
                } else {
                    mainPartsTable.setPlaceholder(new Label("Part not found"));
                }
                return false;

            });
        });

        // Wraps filtered list in Sorted List
        SortedList<Part> partSortedList = new SortedList<>(filteredParts);

        // Binds Sorted List to Table View
        partSortedList.comparatorProperty().bind(mainPartsTable.comparatorProperty());

        // Adds filtered and sorted data to table
        mainPartsTable.setItems(partSortedList);
    }


    /**
     * Search products method.
     * Dynamically searches for product after clicking search button.
     * FUTURE ENHANCEMENT: Have dynamic searching occur without needing to click search button first.
     */
    @FXML
    void OnActionSearchProducts() {

        // Sets search bar to visible
        searchProductsText.setVisible(true);

        // Wraps Observable List in Filtered List
        FilteredList<Product> filteredProducts = new FilteredList<>(Inventory.allProducts, part -> true);

        // Sets filter predicate when filter changes
        searchProductsText.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredProducts.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                } else if (product.getName().toLowerCase().contains(newValue.toLowerCase()) ||
                        String.valueOf(product.getId()).contains(newValue.toLowerCase())) {
                    return true;
                } else {
                    mainProductsTable.setPlaceholder(new Label("Part not found"));
                }
                return false;

            });
        });

        // Wraps filtered list in Sorted List
        SortedList<Product> productSortedList = new SortedList<>(filteredProducts);

        // Binds Sorted List to Table View
        productSortedList.comparatorProperty().bind(mainProductsTable.comparatorProperty());

        // Adds filtered and sorted data to table
        mainProductsTable.setItems(productSortedList);
    }


    /**
     * Initialize method.
     *
     * @param resourceBundle Initializes MainForm Controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Sets search bars to invisible
        searchPartsText.setVisible(false);
        searchProductsText.setVisible(false);

        // Initializes part table
        mainPartsTable.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Initializes product table
        mainProductsTable.setItems(Inventory.getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
