package Model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Julian Javaruski
 */

public class Product {

    //array
    private  ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    //vars
    private int id;
    private String name;
    private Double price;
    private int inv;
    private int min;
    private int max;

    public Product(int id, String name, Double price, int inv, int min, int max, ObservableList associatedParts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.inv = inv;
        this.min = min;
        this.max = max;
        this.associatedParts.addAll(associatedParts);
    }
    public Product(int id, String name, Double price, int inv, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.inv = inv;
        this.min = min;
        this.max = max;
    }


    public Product() {
    }

    //Get All Associated Parts Method
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    //Add Associated Part Method
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    //Delete Associated Part Method
    public boolean deleteAssociatedPart(int partID) {

        for (Part p : associatedParts) {
            if(p.getId() == partID) {
                associatedParts.remove(p);
                return true;
            }
        }
        return false;
    }

    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getInv() {
        return inv;
    }

    public void setInv(int inv) {
        this.inv = inv;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
