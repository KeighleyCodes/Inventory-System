package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.util.Objects;

/** Main class - this initializes an inventory management application and adds sample data.  */
public class Main extends Application {

    /** Loads Main Form on initialization.
     @param stage Loads main stage. */
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainForm.fxml")));
        stage.setTitle("Main Inventory");
        stage.setScene(new Scene(root, 868.0, 380.0));
        stage.show();
    }

    /** Add test data method - adds test data. */
    private static void addTestData() {

        InHouse brakeBlade = new InHouse(23159, "brake blade", 90.99, 12, 8, 24, 93725);
        Inventory.addPart(brakeBlade);

        InHouse shifterExpander = new InHouse(23161, "shifter switch magnet", 28.99, 8, 4, 20, 52799);
        Inventory.addPart(shifterExpander);

        OutSourced boltKit = new OutSourced(23163, "shock bolt kit", 79.19, 18, 10, 30, "Mikes Bikes");
        Inventory.addPart(boltKit);

        OutSourced mountHardware = new OutSourced(23165, "shock mount kit", 28.99, 10, 5, 30, "Mikes Bikes");
        Inventory.addPart(mountHardware);

        Product singleShifter = new Product(23162, "shifter", 77.89, 5, 2, 6);
        Inventory.addProduct(singleShifter);

        Product frameShocks = new Product(23164, "air shock", 550.89, 4, 3, 6);
        Inventory.addProduct(frameShocks);

    }

    /** Main method.
     @param args Launches program and adds test data.
     Javadocs are in a directory in project zip file. */
    public static void main(String[] args){

        addTestData();
        launch(args);
    }

}
