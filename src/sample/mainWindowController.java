package sample;
import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 * @author Julian Javaruski
 */

public class mainWindowController implements Initializable {

    Stage stage;
    Product product;

    @FXML
    private Button addButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button deletePart;
    @FXML
    private Button deleteProduct;
    @FXML
    private Button addProductButton;
    @FXML
    private Button modifyPartButton;
    @FXML
    private Button modifyProductButton;
    @FXML
    private TextField partsSearch;
    @FXML
    private TextField productsSearch;
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> partidCol;
    @FXML
    private TableColumn<Part, String> partnameCol;
    @FXML
    private TableColumn<Part, Integer> invCol;
    @FXML
    private TableColumn<Part, Double> priceCol;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, Integer> partidproCol;
    @FXML
    private TableColumn<Product, String> partnameprodCol;
    @FXML
    private TableColumn<Product, Integer> invproCol;
    @FXML
    private TableColumn<Product, Double> priceproCol;
    private ObservableList<Part> partsInventorySearch = FXCollections.observableArrayList();
    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private ObservableList<Product> productInventorySearch = FXCollections.observableArrayList();
    private ObservableList<Product> productInventory = FXCollections.observableArrayList();
    //search part
    @FXML
    void partSearch(ActionEvent event) {
        {
            if (!partsSearch.getText().trim().isEmpty()) {
                partsInventorySearch.clear();
                for (Part p : Inventory.getAllParts()) {
                    if (p.getName().contains(partsSearch.getText().trim())) {
                        partsInventorySearch.add(p);
                        partInventory.add(p);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Incorrect part");
                        alert.setContentText("Enter a valid part");
                        alert.showAndWait();
                    }

                }
                partsTable.setItems(partsInventorySearch);
                partsTable.refresh();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No parts entered");
                alert.setContentText("Enter a valid part");
                alert.showAndWait();
            }

        }
    }

    //search product
    @FXML
    void productSearch(ActionEvent event) {
        {
            if (!productsSearch.getText().trim().isEmpty()) {
                productInventorySearch.clear();
                for (Product p : Inventory.getAllProducts()) {
                    if (p.getName().contains(productsSearch.getText().trim())) {
                        productInventorySearch.add(p);
                        productInventory.add(p);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Incorrect product");
                        alert.setContentText("Enter a valid product");
                        alert.showAndWait();
                    }
                    productsTable.setItems(productInventorySearch);
                    productsTable.refresh();
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
    //delete methods
    public boolean deletePart(int id) {
        for(Part part : Inventory.getAllParts()) {
            if(part.getId() == id)
                return Inventory.getAllParts().remove(part);
        }
        return false;
    }

    @FXML
    void deletePartAction(ActionEvent event) {
        Part removePart = partsTable.getSelectionModel().getSelectedItem();
        if (partInventory.isEmpty() && removePart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid");
            alert.setContentText("Please select a valid part");
            alert.showAndWait();
            return;
        }
        if (!partInventory.isEmpty() && removePart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid");
            alert.setContentText("You must select a part");
            alert.showAndWait();
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete part");
            alert.setHeaderText("Are you sure you want to delete: " + partsSearch.getText());
            alert.setContentText("Click ok to confirm");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                deletePart(removePart.getId());
                partsTable.refresh();
            } else {
                alert.close();
            }

            }
        }

    @FXML
    void deleteProductAction(ActionEvent event) {
        Product removeProduct = productsTable.getSelectionModel().getSelectedItem();
        if (productInventory.isEmpty() && removeProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid");
            alert.setContentText("Please select a valid part");
            alert.showAndWait();
            return;
        }
        if (!productInventory.isEmpty() && removeProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid");
            alert.setContentText("You must select a part");
            alert.showAndWait();
            return;
        } if (Inventory.valdeleteProduct(removeProduct)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid");
            alert.setContentText("Product cannot have an associated part");
            alert.showAndWait();
            return;
        }
        else if (!Inventory.valdeleteProduct(removeProduct)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete part");
            alert.setHeaderText("Are you sure you want to delete: " + partsSearch.getText());
            alert.setContentText("Click ok to confirm");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Inventory.deleteProduct(removeProduct);
                productsTable.refresh();
            } else {
                alert.close();
            }

        }
    }

    //exit button
    @FXML
    void exitButtonAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("This will exit the program, are you sure?");

        Optional<ButtonType> click = alert.showAndWait();
        if (click.get() == ButtonType.OK){
            System.exit(0);
        }

    }
    //add button
    @FXML
    void addButtonWindow(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/addwindow.fxml"));
            Parent root1 = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add Part");
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @FXML
    void addProductButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/addwindowproduct.fxml"));
            Parent root1 = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add Product");
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //modify button
    @FXML
    void modifyPartButtonAction(ActionEvent event) throws IOException {
        try {
            Part part = partsTable.getSelectionModel().getSelectedItem();
            if(part == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid");
                alert.setContentText("Please select a valid part");
                alert.showAndWait();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/modifypart.fxml"));
                Parent root1 = (Parent) loader.load();


                modifyPartController PartController = loader.getController();
                PartController.getParts(part);

                Stage stage = new Stage();
                stage.setTitle("Modify Part");
                stage.setScene(new Scene(root1));
                stage.show();
                stage.setResizable(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void modifyProductButtonAction(ActionEvent event) {
        try {
            Product product = productsTable.getSelectionModel().getSelectedItem();
            if(product == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid");
                alert.setContentText("Please select a valid part");
                alert.showAndWait();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/modifyproduct.fxml"));
                Parent root1 = (Parent) loader.load();


                modifyProductController ProductController = loader.getController();
                ProductController.getProducts(product);

                Stage stage = new Stage();
                stage.setTitle("Modify Product");
                stage.setScene(new Scene(root1));
                stage.show();
                stage.setResizable(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //update tableviews
    public void updatePartsTableView() {
        partsTable.setItems(Inventory.getAllParts());
        partsTable.refresh();
    }

    public void updateProductsTableView() {
        productsTable.setItems(Inventory.getAllProducts());
        partsTable.refresh();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //populating both TableViews
        partidCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partnameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invCol.setCellValueFactory(new PropertyValueFactory<>("inv"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        partidproCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partnameprodCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invproCol.setCellValueFactory(new PropertyValueFactory<>("inv"));
        priceproCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        updatePartsTableView();
        updateProductsTableView();

    }
}
