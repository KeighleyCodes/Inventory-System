package model;

/** Model creates class for in-house parts.  */
public class InHouse extends Part {

    /** Allows for int machineId when in house button selected. */
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {

        super(id, name, price, stock, min, max);
        this.machineId = machineId;

    }

    /** @return machineId */
    public int getMachineId() {

        return machineId;

    }

    /** @param machineId Sets machine ID. */
    // I chose to set up machine ID differently in the Add Parts / Modify Parts controllers
    public void setMachineId(int machineId) {

        this.machineId = machineId;

    }

}
