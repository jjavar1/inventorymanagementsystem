package Model;
/**
 *
 * @author Julian Javaruski
 */

public class Outsourced extends Part {

    private String outsourceCompanyName;
    //getters and setters
    public Outsourced(int id, String name, double price, int stock, int min, int max, String company){
        super(id, name, price, stock, min, max);
        this.outsourceCompanyName = company;
    }

    public String getOutsourceCompanyName(){
        return outsourceCompanyName;
    }

    public void setOutsourceCompanyName(String outsourceCompanyName){
        this.outsourceCompanyName = outsourceCompanyName;
    }
}
