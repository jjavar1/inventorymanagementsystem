package sample;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 * @author Julian Javaruski
 */

public class modifyPartController implements Initializable {

    @FXML
    private RadioButton outsourcedButton;

    @FXML
    private ToggleGroup toggleGroup1;

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
    private Button cancelButton;


    //get parts
    public void getParts (Part part) {

        if (part instanceof InHouse) {
            InHouse part1 = (InHouse) part;
            inhouseButton.setSelected(true);
            textField.setText("Machine ID");
            id.setText(String.valueOf(part.getId()));
            name.setText(part.getName());
            inventorytext.setText(String.valueOf(part.getInv()));
            pricecost.setText(String.valueOf(part.getPrice()));
            max.setText(String.valueOf(part.getMax()));
            min.setText(String.valueOf(part.getMin()));
            machineid.setText(String.valueOf(part1.getMachineId()));
        }
        if (part instanceof Outsourced) {
            Outsourced part2 = (Outsourced) part;
            outsourcedButton.setSelected(true);
            textField.setText("Company Name");
            id.setText(String.valueOf(part.getId()));
            name.setText(part.getName());
            inventorytext.setText(String.valueOf(part.getInv()));
            pricecost.setText(String.valueOf(part.getPrice()));
            max.setText(String.valueOf(part.getMax()));
            min.setText(String.valueOf(part.getMin()));
            machineid.setText(String.valueOf(part2.getOutsourceCompanyName()));
        }
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

    @FXML
    void changeText(ActionEvent event) {
        textField.setText("Company Name");
    }

    @FXML
    void changeTextTwo(ActionEvent event) {
        textField.setText("Machine ID");
    }
    //save button
    @FXML
    void saveButtonAction(ActionEvent event) {
        try {
            if (name.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error adding part");
                alert.setHeaderText("Cannot add part");
                alert.setContentText("Name is invalid or contains blank fields");
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
                    updatePart(new InHouse(Integer.parseInt(id.getText().trim()), name.getText().trim(),
                            Double.parseDouble(pricecost.getText().trim()), Integer.parseInt(inventorytext.getText().trim()),
                            Integer.parseInt(min.getText().trim()), Integer.parseInt(max.getText().trim()), (Integer.parseInt(machineid.getText().trim()))));
                } else {
                    updatePart(new Outsourced(Integer.parseInt(id.getText().trim()), name.getText().trim(),
                            Double.parseDouble(pricecost.getText().trim()), Integer.parseInt(inventorytext.getText().trim()),
                            Integer.parseInt(min.getText().trim()), Integer.parseInt(max.getText().trim()), machineid.getText().trim()));
                }

            }
        } catch (NumberFormatException e) {
            e.printStackTrace(System.out);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Part");
            alert.setContentText("Form contains blank fields.");
            alert.showAndWait();
        }
        mainScreen(event);
    }
    //main screen method
    private void mainScreen(Event event) {
        Stage stage2 = (Stage) saveButton.getScene().getWindow();
        stage2.close();
    }
    //update part method
    public void updatePart (Part selectedPart) {
        for (int index = 0; index < Inventory.getAllParts().size(); index++) {
            if (Inventory.getAllParts().get(index).getId() == selectedPart.getId()) {
                Inventory.getAllParts().set(index, selectedPart);
                break;
            }
        }
        return;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

