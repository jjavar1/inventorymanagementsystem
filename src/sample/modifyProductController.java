package sample;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Julian Javaruski
 */

public class modifyProductController implements Initializable {

    Product product;
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField inventorytext;
    @FXML
    private TextField pricecost;
    @FXML
    private TextField max;
    @FXML
    private TextField min;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> partidCol;
    @FXML
    private TableColumn<Part, String> partnameCol;
    @FXML
    private TableColumn<Part, Integer> invlevelCol;
    @FXML
    private TableColumn<Part, Double> pricecostCol;
    @FXML
    private TableColumn<Part, Integer> partidCol2;
    @FXML
    private TableColumn<Part, String> partnameCol2;
    @FXML
    private TableColumn<Part, Integer> invlevelCol2;
    @FXML
    private TableColumn<Part, Double> pricecostCol2;
    @FXML
    private TableView<Part> associatedPartTable;
    @FXML
    private TextField searchIDField;
    @FXML
    private Button addButton;
    @FXML
    private Button removeAssociatedButton;
    private ObservableList<Part> partsInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partsInventorySearch = FXCollections.observableArrayList();
    private ObservableList<Part> assocPartList = FXCollections.observableArrayList();
    //get products
    public void getProducts(Product products) {

        id.setText(String.valueOf(products.getId()));
        name.setText(products.getName());
        inventorytext.setText(String.valueOf(products.getInv()));
        pricecost.setText(String.valueOf(products.getPrice()));
        max.setText(String.valueOf(products.getMax()));
        min.setText(String.valueOf(products.getMin()));

        product.getAllAssociatedParts().addAll(products.getAllAssociatedParts());

    }
    //search field
    @FXML
    void SearchIDFieldAction(ActionEvent event) {
        {
            if (!searchIDField.getText().trim().isEmpty()) {
                partsInventorySearch.clear();
                for (Part p : Inventory.getAllParts()) {
                    if (p.getName().contains(searchIDField.getText().trim())) {
                        partsInventorySearch.add(p);
                        partsInventory.add(p);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Incorrect product");
                        alert.setContentText("Enter a valid product");
                        alert.showAndWait();
                    }
                    partsTable.setItems(partsInventorySearch);
                    partsTable.refresh();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No products entered");
                alert.setContentText("Enter a valid product");
                alert.showAndWait();
            }
        }
    }
    //add button
    @FXML
    void addButtonAction(ActionEvent event) {
        Part part = partsTable.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No products selected");
            alert.setContentText("Please select a valid product");
            alert.showAndWait();
        } else {
            product.getAllAssociatedParts().add(part);
            assocPartList.add(part);

        }
    }

    @FXML
    void associatedPartTableAction(ActionEvent event) {

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
    void partsTableAction(ActionEvent event) {

    }
    //remove associated product
    @FXML
    void removeAssociatedButtonAction(ActionEvent event) {
        Part removePart = associatedPartTable.getSelectionModel().getSelectedItem();
        if (!partsInventory.isEmpty() && removePart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid");
            alert.setContentText("You must select a part");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete part");
            alert.setHeaderText("Are you sure you want to delete");
            alert.setContentText("Click ok to confirm");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                product.deleteAssociatedPart(removePart.getId());
                partsTable.refresh();
            } else {
                alert.close();
            }
        }
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
            if (!pricecost.getText().trim().matches("[0.0-9.0]*")) {
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
            else {
                updateProduct(new Product(Integer.parseInt(id.getText().trim()), name.getText().trim(),
                        Double.parseDouble(pricecost.getText().trim()), Integer.parseInt(inventorytext.getText().trim()),
                        Integer.parseInt(min.getText().trim()), Integer.parseInt(max.getText().trim()), product.getAllAssociatedParts()));
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error modifying product");
            alert.setContentText("Form contains blank fields");
            alert.showAndWait();
            return;
        }
        mainScreen(event);
    }
    //update product method
    public void updateProduct(Product selectedProduct) {
        for (int index = 0; index < Inventory.getAllProducts().size(); index++) {
            if (Inventory.getAllProducts().get(index).getId() == selectedProduct.getId()) {
                Inventory.getAllProducts().set(index, selectedProduct);
                break;
            }
        }
        return;
    }
    //generate tableviews
    public void updatePartsTableView() {
        partsTable.setItems(Inventory.getAllParts());
        partsTable.refresh();
    }

    public void updateProductsTableView() {
        product = new Product();
        associatedPartTable.setItems(product.getAllAssociatedParts());
        partsTable.refresh();
    }
    //main screen method
    private void mainScreen(Event event) {
        Stage stage2 = (Stage) saveButton.getScene().getWindow();
        stage2.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partidCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partnameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invlevelCol.setCellValueFactory(new PropertyValueFactory<>("inv"));
        pricecostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        partidCol2.setCellValueFactory(new PropertyValueFactory<>("id"));
        partnameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
        invlevelCol2.setCellValueFactory(new PropertyValueFactory<>("inv"));
        pricecostCol2.setCellValueFactory(new PropertyValueFactory<>("price"));
        updatePartsTableView();
        updateProductsTableView();
    }
}
