package sample;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;

import javafx.event.*;
import javafx.fxml.*;

import javafx.scene.control.*;

import javafx.stage.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

/**
 *
 * @author Julian Javaruski
 */

/**
 * For this class, I had trouble creating error statements for incorrect input. The way I could make this cleaner is by
 * making a new class or method with a switch statement that I can use to search for errors in input, therefore making
 * it cleaner and easier to read.
 */

public class addWindowController implements Initializable {
    boolean isOutSource;
    boolean isInHouse;
    Random randomNum = new Random();
    @FXML
    private RadioButton outsourcedButton;
    @FXML
    private RadioButton inhouseButton;
    @FXML
    private Label textField;
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField inventorytext;
    @FXML
    private TextField pricecost;
    @FXML
    private TextField machineid;
    @FXML
    private TextField max;
    @FXML
    private TextField min;
    @FXML
    private Button saveButton;
    @FXML
    private Button canelButton;
    @FXML
    private ToggleGroup toggleGroup1;
    //change labels
    @FXML
    void changeText(ActionEvent event) {
        isOutSource = true;
        textField.setText("Company Name");
    }

    @FXML
    void changeTextTwo(ActionEvent event) {
        isInHouse = true;
        textField.setText("Machine ID");
    }
    //cancel button
    @FXML
    void cancelButtonAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("Click ok to continue");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            mainScreen(event);
        } else {
            alert.close();
        }
    }
    //save button
    @FXML
    void saveButtonAction(ActionEvent event) throws IOException {
        generatePartID();
        try {
            if (name.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error adding part");
                alert.setHeaderText("Cannot add part");
                alert.setContentText("Name is invalid");
                alert.showAndWait();
                return;
            }
            if (Integer.parseInt(min.getText().trim()) > Integer.parseInt(max.getText().trim())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error adding part");
                alert.setHeaderText("Cannot add part");
                alert.setContentText("Minimum is higher than maximum");
                alert.showAndWait();
                return;
            }
            if (!pricecost.getText().trim().matches("[0-9]*")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error adding part");
                alert.setHeaderText("Cannot add part");
                alert.setContentText("Pricecost must be a number");
                alert.showAndWait();
                return;
            }
            if (Double.parseDouble(pricecost.getText().trim()) < 0.0 || Double.parseDouble(inventorytext.getText().trim()) < 0 || Double.parseDouble(min.getText().trim()) < 0 || Double.parseDouble(max.getText().trim()) < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error adding part");
                alert.setHeaderText("Cannot add part");
                alert.setContentText("Parts cannot be negative");
                alert.showAndWait();
                return;
            }
            if (Integer.parseInt(inventorytext.getText().trim()) > Integer.parseInt(max.getText().trim())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error adding part");
                alert.setHeaderText("Cannot add part");
                alert.setContentText("Inventory cannot be greater than maximum");
                alert.showAndWait();
                return;
            }
            if (Integer.parseInt(inventorytext.getText().trim()) < Integer.parseInt(min.getText().trim())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error adding part");
                alert.setHeaderText("Cannot add part");
                alert.setContentText("Inventory cannot be lower than minimum");
                alert.showAndWait();
                return;
            }
            if (inhouseButton.isSelected() && !machineid.getText().trim().matches("[0-9]*")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error adding part");
                alert.setHeaderText("Cannot add part");
                alert.setContentText("Machine ID must be a number");
                alert.showAndWait();
                return;
            }
            if (machineid.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error adding part");
                alert.setHeaderText("Cannot add part");
                alert.setContentText("Invalid format");
                alert.showAndWait();
                return;
            }
            if (!inhouseButton.isSelected() && !outsourcedButton.isSelected()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error adding part");
                alert.setHeaderText("Cannot add part");
                alert.setContentText("Select In-House or Outsourced");
                alert.showAndWait();
                return;
            } else {
                if (inhouseButton.isSelected()) {
                    Inventory.addPart(new InHouse(Integer.parseInt(id.getText().trim()), name.getText().trim(),
                            Double.parseDouble(pricecost.getText().trim()), Integer.parseInt(inventorytext.getText().trim()),
                            Integer.parseInt(min.getText().trim()), Integer.parseInt(max.getText().trim()), (Integer.parseInt(machineid.getText().trim()))));
                } else {
                    Inventory.addPart(new Outsourced(Integer.parseInt(id.getText().trim()), name.getText().trim(),
                            Double.parseDouble(pricecost.getText().trim()), Integer.parseInt(inventorytext.getText().trim()),
                            Integer.parseInt(min.getText().trim()), Integer.parseInt(max.getText().trim()), machineid.getText().trim()));
                }

            }

        } catch (Exception e) {
            e.printStackTrace(System.out);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Part");
            alert.setContentText("Form contains blank fields or other errors");
            alert.showAndWait();
            return;
        }
        mainScreen(event);
    }
    //generating unique id
    private void generatePartID() {
        Integer num = randomNum.nextInt(10000);
        if (!verifyIfTaken(num)) {
            id.setText(num.toString());
        }
    }
    //id verification
    private boolean verifyIfTaken(Integer num) {
        Part match = Inventory.lookupPart(num);
        return match != null;
    }
    //main screen method
    private void mainScreen(Event event) {
        Stage stage2 = (Stage) saveButton.getScene().getWindow();
        stage2.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
