package Model; /**
* Supplied class Part.java 
 */

import javafx.beans.property.*;

/**
 *
 * @author Julian Javaruski
 */
public class Part {

    private int id;
    private String name;
    private Double price;
    private int inv;
    private int min;
    private int max;

    // Construct

    //Part Constructor
    public Part(int id, String name, Double price, int inv, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.inv = inv;
        this.min = min;
        this.max = max;
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
        this.inv= inv;
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