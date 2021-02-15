package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Julian Javaruski
 */

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    //add
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    //lookup
    public static Part lookupPart(int partID) {
        if (!allParts.isEmpty()) {
            for (Part parts : allParts) {
                if (parts.getId() == partID) {
                    return parts;
                }
            }
        }
        return null;
    }
    //lookup product
    public static Product lookupProduct(int productID) {
        if (!allProducts.isEmpty()) {
            for (Product products : allProducts) {
                if (products.getId() == productID) {
                    return products;
                }
            }
        }
        return null;
    }

    //get
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }

    //update
    public void updatePart (Part selectedPart) {
        for (int index = 0; index < allParts.size(); index++) {
            if (allParts.get(index).getId() == selectedPart.getId()) {
                allParts.set(index, selectedPart);
                break;
            }
        }
        return;
    }
    //update product
    public void updateProduct (int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    //delete
    public boolean deletePart (Part selectedPart) {
        allParts.remove(selectedPart);
        return true;
    }

    public static void deleteProduct (Product selectedProduct) {
        allProducts.remove(selectedProduct);
    }

    /**
     *
     * @param product
     * @return This method was added to validate products to make sure they do not
     * have associated parts before they are deleted. I ran into a lot of issues
     * with validating the products before they are deleted.
     */
    public static boolean valdeleteProduct(Product product) {
        boolean isFound = false;
        int proID = product.getId();
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getId() == proID)
                if (!allProducts.get(i).getAllAssociatedParts().isEmpty()) {
                    isFound = true;
                }
        }
        return isFound;
    }
}
