package Model;

/**
 *
 * @author Julian Javaruski
 */

public class InHouse extends Part {
    //Variables
    private int machineId;

    //In House Constructor
    public InHouse(int id, String name, Double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    //get and set
    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
