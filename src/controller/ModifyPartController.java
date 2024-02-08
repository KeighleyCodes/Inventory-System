package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;
import model.Part;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** Modify parts controller - allows for modifying parts.  */
public class ModifyPartController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField partIdText;

    @FXML
    private RadioButton inHouseButton;

    @FXML
    private TextField partStockText;

    @FXML
    private TextField machineIdText;

    @FXML
    private TextField partMaxText;

    @FXML
    private TextField partMinTxt;

    @FXML
    private TextField partNameText;

    @FXML
    private RadioButton outsourcedButton;

    @FXML
    private TextField partPriceText;

    @FXML
    private Button saveButton;

    @FXML
    private Label variableLabel;


    Part partToModify = null;

    int index;

    /** Close window method.
     @param event Closes window and returns to Main Form. */
    @FXML
    void OnActionCloseWindow(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainForm.fxml")));
            stage.setScene(new Scene(scene));
            stage.setTitle("Main Inventory");
            stage.show();
        }
    }

    /** Send part method.
     @param part Receives data from Main Form. */
    public void sendPart(Part part, int index) {

        // Allows toggling between Machine ID and Company Name
        partToModify = part;
        this.index = index;

        if (part instanceof InHouse) {
            inHouseButton.setSelected(true);
            machineIdText.setText(String.valueOf(((InHouse) part).getMachineId()));
            variableLabel.setText("Machine ID");
        }

        if (part instanceof OutSourced) {
            outsourcedButton.setSelected(true);
            machineIdText.setText(String.valueOf(((OutSourced) part).getCompanyName()));
            variableLabel.setText("Company Name");
        }

        partIdText.setText(String.valueOf(part.getId()));
        partNameText.setText(String.valueOf(part.getName()));
        partStockText.setText(String.valueOf(part.getStock()));
        partPriceText.setText(String.valueOf(part.getPrice()));
        partMinTxt.setText(String.valueOf(part.getMin()));
        partMaxText.setText(String.valueOf(part.getMax()));
    }

    /** Save modified part method.
     @param event Saves modified part info and returns to Main Form.
     RUNTIME ERROR - If the name field was left empty or the min was greater than max the dialog box would pop up, however
     once accepted the program would return to the main form and save the incorrect information. Added if statement in order to fix. */
    @FXML
    void OnActionSaveModify(ActionEvent event) {

        try {
            int id = Integer.parseInt(partIdText.getText());
            String name = partNameText.getText();
            int stock = Integer.parseInt(partStockText.getText());
            double price = Double.parseDouble(partPriceText.getText());
            int min = Integer.parseInt(partMinTxt.getText());
            int max = Integer.parseInt(partMaxText.getText());

            // Alerts if minimum greater than maximum
            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Minimum must be less than maximum");
                alert.showAndWait();
                return;
            }

            // Alerts if inventory level (stock) is less than minimum or greater than maximum
            if (stock < min || stock > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory outside of minimum and maximum range");
                alert.showAndWait();
                return;
            }

            //The try/catch doesn't work for the string name. This alerts if the name field is empty
            if (name.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please ensure all fields are filled in and contain correct data types");
                alert.showAndWait();
                return;
            }

            //Allows use of Machine ID and Company name based on use of In House and Outsourced toggles
            if (inHouseButton.isSelected()) {

                int machineId = Integer.parseInt(machineIdText.getText());
                Inventory.updatePart (index, new InHouse(id, name, price, stock, min, max, machineId));
            }

            else {
                String companyName = machineIdText.getText();

                // Checks if company name is empty - try catch doesn't check for empty string
                if (companyName.isBlank()) {
                    throw new Exception("Please ensure all fields are filled in and contain correct data types");
                }
                else {
                    Inventory.updatePart (index, new OutSourced(id, name, price, stock, min, max, companyName));
                }
            }
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainForm.fxml")));
            stage.setScene(new Scene(scene));
            stage.setTitle("Main Inventory");
            stage.show();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please ensure all fields are filled in and contain correct data types");
            alert.showAndWait();
        }
    }

    /** Initialize method.
     @param resourceBundle Initializes ModifyPart Controller. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /** In-house button method.
     @param event Keeps Machine ID when In House button clicked. */
    public void ModifyPartInHouse(ActionEvent event) {
        variableLabel.setText("Machine ID");
    }

    /** Outsourced button method.
     @param event Changes Machine ID to Company Name when outsourced button clicked. */
    public void ModifyPartOutsourced(ActionEvent event) {
        variableLabel.setText("Company Name");
    }
}


