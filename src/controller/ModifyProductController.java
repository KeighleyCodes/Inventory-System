//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

/** Modify products controller - allows for modifying products and adding and deleting associated parts.  */
public class ModifyProductController implements Initializable {
    Stage stage;
    Parent scene;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TableColumn<?, ?> partStockColumn;
    @FXML
    private TableColumn<?, ?> productStockColumn;
    @FXML
    private TableView<Part> productsTable;
    @FXML
    private TableView<Part> associatedPartsTable;
    @FXML
    private TableColumn<?, ?> productIdColumn;
    @FXML
    private TableColumn<?, ?> associatedPartsIdColumn;
    @FXML
    private TableColumn<?, ?> productNameColumn;
    @FXML
    private TableColumn<?, ?> associatedPartsNameColumn;
    @FXML
    private TableColumn<?, ?> productPriceColumn;
    @FXML
    private TableColumn<?, ?> associatedPartsPriceColumn;
    @FXML
    private Button removeAssociatedPartsButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button searchButton;
    @FXML
    private TextField productIdText;
    @FXML
    private TextField productNameText;
    @FXML
    private TextField productStockText;
    @FXML
    private TextField productPriceText;
    @FXML
    private TextField productMaxText;
    @FXML
    private TextField productMinText;
    @FXML
    private TextField searchProductText;

    Product productToModify = null;

    /** Imports associated parts observable list. */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    int index;

    /** Add associated part method. Adds associated part from top table to bottom table. */
    @FXML
    void OnActionAddAssociatedPart() {
        Part selectedPart = this.productsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to add");
            alert.show();
        }
        else {
            productToModify.associatedParts.add(selectedPart);
        }
    }

    /** Close window method.
     @param event Closes window and returns to the main form. */
    @FXML
    void OnActionCloseWindow(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to cancel?", new ButtonType[0]);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            this.stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            this.scene = (Parent)FXMLLoader.load((URL)Objects.requireNonNull(this.getClass().getResource("/view/MainForm.fxml")));
            this.stage.setScene(new Scene(this.scene));
            this.stage.setTitle("Main Inventory");
            this.stage.show();
        }
    }

    /** Delete part method - deletes associated part. */
    @FXML
    void OnActionDeletePart() {
        Part selectedPart = this.associatedPartsTable.getSelectionModel().getSelectedItem();
        Alert alert;
        Optional result;

        if (selectedPart == null) {
            alert = new Alert(AlertType.ERROR, "Select part to delete", new ButtonType[0]);
            result = alert.showAndWait();
        }

        else {
            alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this part?", new ButtonType[0]);
            result = alert.showAndWait();

            productToModify.getAssociatedParts().remove(selectedPart);
        }

    }

    /** Send product method.
     @param product Receives data from the main form. */
    public void sendProduct(Product product, int index) {
        this.index = index;
        this.productToModify = product;
        this.productIdText.setText(String.valueOf(product.getId()));
        this.productNameText.setText(String.valueOf(product.getName()));
        this.productStockText.setText(String.valueOf(product.getStock()));
        this.productPriceText.setText(String.valueOf(product.getPrice()));
        this.productMaxText.setText(String.valueOf(product.getMin()));
        this.productMinText.setText(String.valueOf(product.getMax()));

        // Adds parts to associated parts list (bottom table)
        this.associatedPartsTable.setItems(product.getAssociatedParts());
        this.associatedPartsIdColumn.setCellValueFactory(new PropertyValueFactory("id"));
        this.associatedPartsNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        this.productStockColumn.setCellValueFactory(new PropertyValueFactory("stock"));
        this.associatedPartsPriceColumn.setCellValueFactory(new PropertyValueFactory("price"));
    }

    /** Save product method.
     @param event Saves modified part info and returns to the main form.
     RUNTIME ERROR - If the name field was left empty or the min was greater than max the dialog box would pop up, however
     once accepted the program would return to the main form and save the incorrect information. Added if statements in order to fix. */
    @FXML
    void OnActionSaveModifyProduct(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(this.productIdText.getText());
            String name = this.productNameText.getText();
            int stock = Integer.parseInt(this.productStockText.getText());
            double price = Double.parseDouble(this.productPriceText.getText());
            int min = Integer.parseInt(this.productMinText.getText());
            int max = Integer.parseInt(this.productMaxText.getText());

            Alert alert;

            if (min > max) {
                alert = new Alert(AlertType.ERROR, "Minimum must be less than maximum", new ButtonType[0]);
                alert.showAndWait();
            }

            if (stock <= min || stock >= max) {
                alert = new Alert(AlertType.ERROR, "Inventory outside of minimum and maximum range", new ButtonType[0]);
                alert.showAndWait();
            }

            if (name.isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please ensure all fields are filled in and contain correct data types");
                alert.showAndWait();
            }

            else if (min < max && stock > min && stock < max) {
                Product p = new Product(id, name, price, stock, min, max);
                p.getAssociatedParts().addAll(productToModify.getAssociatedParts());
                Inventory.updateProduct(index, p);

                this.stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                this.scene = (Parent)FXMLLoader.load((URL)Objects.requireNonNull(this.getClass().getResource("/view/MainForm.fxml")));
                this.stage.setScene(new Scene(this.scene));
                this.stage.setTitle("Main Inventory");
                this.stage.show();
            }

        } catch (NumberFormatException var10) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please ensure all fields are filled in and contain correct data types");
            alert.showAndWait();
        }
    }

    /** Search method.
     *@param event Dynamically searches for product after clicking search button.
    FUTURE ENHANCEMENT: Have dynamic searching occur without needing to click search button. */
    @FXML
    void OnActionSearch(ActionEvent event) {
        this.searchProductText.setVisible(true);
        FilteredList<Part> filteredParts = new FilteredList(Inventory.allParts, (part) -> {
            return true;
        });
        this.searchProductText.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredParts.setPredicate((part) -> {
                if (newValue != null && !newValue.isEmpty()) {
                    if (!part.getName().toLowerCase().contains(newValue.toLowerCase()) && !String.valueOf(part.getId()).contains(newValue.toLowerCase())) {
                        this.productsTable.setPlaceholder(new Label("Part not found"));
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    return true;
                }
            });
        });
        SortedList<Part> partSortedList = new SortedList(filteredParts);
        partSortedList.comparatorProperty().bind(this.productsTable.comparatorProperty());
        this.productsTable.setItems(partSortedList);
    }

    /** Initialize method.
     @param resourceBundle Initializes ModifyPart Controller. */
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Sets search text to invisible upon initialization
        this.searchProductText.setVisible(false);

        // Adds products to parts list (top table)
        this.productsTable.setItems(Inventory.getAllParts());
        this.productIdColumn.setCellValueFactory(new PropertyValueFactory("id"));
        this.productNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        this.partStockColumn.setCellValueFactory(new PropertyValueFactory("stock"));
        this.productPriceColumn.setCellValueFactory(new PropertyValueFactory("price"));
    }

    /** Send part method.
     @param items Adds all parts to top table. */
    public void sendParts(ObservableList<Part> items) {
        this.productsTable.setItems(Inventory.getAllParts());
    }
}
