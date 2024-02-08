package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;


/** Add parts controller - allows for adding and saving parts.  */
public class AddPartsController implements Initializable {

    public ToggleGroup addPartToggle;
    Stage stage;
    Parent scene;

    @FXML
    private TextField addPartIdText;

    @FXML
    private RadioButton inHouseButton;

    @FXML
    private TextField inventoryLevelText;

    @FXML
    private TextField machineIdText;

    @FXML
    private TextField partMaxText;

    @FXML
    private TextField partMinText;

    @FXML
    private TextField partNameText;

    @FXML
    private RadioButton outsourcedButton;

    @FXML
    private TextField priceText;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    @FXML
    private Label variableLabel;

    /** Sets Part ID to unique odd number. */
    static int uniqueId = 23165;

    /** Close window method.
     @param event Returns to Main Form when Cancel button clicked. */
    @FXML
    void OnActionCloseWindow(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainForm.fxml")));
            stage.setScene(new Scene(scene));
            stage.setTitle("Main Inventory");
            stage.show();
        }
    }

    /** Save part method.
     @param event Saves modified part info and returns to Main Form.
     RUNTIME ERROR - If the name field was left empty or the min was greater than max the dialog box would pop up,
     however once accepted the program would return to the main form and save the incorrect information. Added else
     statement and else-if statements in order to fix. */
    @FXML
    void OnActionSaveAddPart(ActionEvent event) throws IOException {

        try {
            int id = uniqueId;
            String name = partNameText.getText();
            int stock = Integer.parseInt(inventoryLevelText.getText());
            double price = Double.parseDouble(priceText.getText());
            int min = Integer.parseInt(partMinText.getText());
            int max = Integer.parseInt(partMaxText.getText());

            //Increments Unique ID to new unique odd number
            uniqueId +=2;

            // Alerts if minimum greater than maximum
            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Minimum must be less than maximum");
                alert.showAndWait();
            }

            // Alerts if inventory level (stock) is less than minimum or greater than maximum
            if (stock < min || stock > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory outside of minimum and maximum range");
                alert.showAndWait();
            }

            // The try/catch doesn't work for the string name. This alerts if the name field is empty
            if (name.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please ensure all fields are filled in and contain correct data types");
                alert.showAndWait();
            }

            // Returns to main form only if all conditions met
            else if (min < max && stock > min && stock < max && !name.isBlank()) {

                //Allows use of Machine ID and Company name based on use of In House and Outsourced toggles
                if (inHouseButton.isSelected()) {
                    variableLabel.setText("Machine ID");

                    int machineId = Integer.parseInt(machineIdText.getText());
                    Inventory.addPart(new InHouse(uniqueId, name, price, stock, min, max, machineId));
                }

                if (outsourcedButton.isSelected()) {
                    variableLabel.setText("Company Name");
                    String companyName = machineIdText.getText();

                    // Checks if company name is empty - try catch doesn't check for empty string
                    if(companyName.isEmpty()) {
                        throw new Exception("Please ensure all fields are filled in and contain correct data types");
                    }

                    else {
                        Inventory.addPart(new OutSourced(uniqueId, name, price, stock, min, max, companyName));
                    }

                }

                uniqueId = uniqueId += 2;
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainForm.fxml")));
                stage.setScene(new Scene(scene));
                stage.setTitle("Main Inventory");
                stage.show();
            }
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please ensure all fields are filled in and contain correct data types");
            alert.showAndWait();
        }
    }

    /** Initialize method.
     @param resourceBundle Initializes Add Parts Controller. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Sets in-house button to selected on initialize
        addPartToggle.selectToggle(inHouseButton);
    }

    /** In-house button method.
     @param event Keeps Machine ID when In House button clicked. */
    public void AddPartInHouse(ActionEvent event) {
        variableLabel.setText("Machine ID");
    }

    /** Outsourced button method.
     @param event Changes Machine ID to Company Name when outsourced button clicked. */
    public void AddPartOutSourced(ActionEvent event) {
        variableLabel.setText("Company Name");
    }
}

