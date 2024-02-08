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

/** Add products controller - allows for adding and saving products and adding and deleting associated parts.  */
public class AddProductController implements Initializable {
    Stage stage;
    Parent scene;
    @FXML
    private Button addButton;
    @FXML
    private TextField addProductIdText;
    @FXML
    private TextField addProductStockText;
    @FXML
    private TextField addProductMaxText;
    @FXML
    private TextField addProductMinText;
    @FXML
    private TextField addProductNameText;
    @FXML
    private TextField addProductPriceText;
    @FXML
    private TextField addProductSearchText;
    @FXML
    private TableView<Part> addPartTable;
    @FXML
    private TableView<Part> addAssociatedPartsTable;
    @FXML
    private Button cancelButton;
    @FXML
    private TableColumn<Part, Integer> partStockColumn;
    @FXML
    private TableColumn<Product, Integer> productStockColumn;
    @FXML
    private TableColumn<Part, Integer> partIdColumn;
    @FXML
    private TableColumn<Product, Integer> productIdColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Part, Double> partPriceColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;
    @FXML
    private Button removeAssociatedPartButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button searchButton;

    /** Sets product ID to unique even number. */
    static int uniqueProductId = 23164;

    /** Imports Associated Parts Observable List. */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** Add product method. Adds associated part to bottom table. */
    @FXML
    void OnActionAddProduct() {
        Part selectedPart = (Part)this.addPartTable.getSelectionModel().getSelectedItem();
        associatedParts.add(selectedPart);
    }

    /** Close window method.
     @param event Closes window and returns to Main Form. */
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

    /** Save product method.
     @param event Saves modified part info and returns to Main Form.
     RUNTIME ERROR - If the name field was left empty or the min was greater than max the dialog box would pop up, however
     once accepted the program would return to the main form and save the incorrect information. Added if statements in order to fix. */
    @FXML
    void OnActionSaveProduct(ActionEvent event) throws IOException {
        try {
            int id = uniqueProductId;
            String name = this.addProductNameText.getText();
            int stock = Integer.parseInt(this.addProductStockText.getText());
            double price = Double.parseDouble(this.addProductPriceText.getText());
            int min = Integer.parseInt(this.addProductMinText.getText());
            int max = Integer.parseInt(this.addProductMaxText.getText());

            uniqueProductId += 2;
            Alert alert;

            if (min > max) {
                alert = new Alert(AlertType.ERROR, "Minimum must be less than maximum", new ButtonType[0]);
                alert.showAndWait();
            }

            if (stock < min || stock > max) {
                alert = new Alert(AlertType.ERROR, "Inventory outside of minimum and maximum range", new ButtonType[0]);
                alert.showAndWait();
            }

            if (name.isBlank()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please ensure all fields are filled in and contain correct data types");
                alert.showAndWait();
            }

            else if (min < max && stock > min && stock < max && !name.isBlank()) {
                Product product = new Product(uniqueProductId, name, price, stock, min, max);
                product.setAssociatedParts(associatedParts);
                Inventory.addProduct(product);

                uniqueProductId = uniqueProductId += 2;
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

    /** Search method -dynamically searches for product after clicking search button.
     FUTURE ENHANCEMENT: Have dynamic searching occur without needing to click search button first. */
    @FXML
    void OnActionSearch() {
        this.addProductSearchText.setVisible(true);
        FilteredList<Part> filteredParts = new FilteredList(Inventory.allParts, (part) -> {
            return true;
        });
        this.addProductSearchText.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredParts.setPredicate((part) -> {
                if (newValue != null && !newValue.isEmpty()) {
                    if (!part.getName().toLowerCase().contains(newValue.toLowerCase()) && !String.valueOf(part.getId()).contains(newValue.toLowerCase())) {
                        this.addPartTable.setPlaceholder(new Label("Part not found"));
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
        partSortedList.comparatorProperty().bind(this.addPartTable.comparatorProperty());
        this.addPartTable.setItems(partSortedList);
    }

    /**Remove associated part method -removes associated part. */
    @FXML
    void OnActionRemoveAssociatedPart() {
        Part selectedPart = (Part)this.addAssociatedPartsTable.getSelectionModel().getSelectedItem();
        Alert alert;
        Optional result;
        if (selectedPart == null) {
            alert = new Alert(AlertType.WARNING, "Select part to delete", new ButtonType[0]);
            result = alert.showAndWait();
        }
        else if (associatedParts.contains(selectedPart)) {
            alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this part?", new ButtonType[0]);
            result = alert.showAndWait();
            associatedParts.remove(selectedPart);
            this.addAssociatedPartsTable.setItems(associatedParts);
        }

    }

    /** Initialize method - initializes AddProduct controller. */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.addProductSearchText.setVisible(false);
        this.partIdColumn.setCellValueFactory(new PropertyValueFactory("id"));
        this.partNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        this.partStockColumn.setCellValueFactory(new PropertyValueFactory("stock"));
        this.partPriceColumn.setCellValueFactory(new PropertyValueFactory("price"));
        this.addAssociatedPartsTable.setItems(associatedParts);
        this.productIdColumn.setCellValueFactory(new PropertyValueFactory("id"));
        this.productNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        this.productStockColumn.setCellValueFactory(new PropertyValueFactory("stock"));
        this.productPriceColumn.setCellValueFactory(new PropertyValueFactory("price"));
    }

    /** Send parts method.
     @param items Sets parts in the parts table (top). */
    public void sendParts(ObservableList<Part> items) {
        this.addPartTable.setItems(Inventory.getAllParts());
    }
}



