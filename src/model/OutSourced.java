package model;

/** Model creates class for outsourced parts.  */
public class OutSourced extends Part {

    /** Allows for int machineId when in house button selected. */
    private String companyName;

    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {

        super(id, name, price, stock, min, max);
        this.companyName = companyName;

    }

    /** @return companyName. */
    public String getCompanyName() {

        return companyName;

    }

    /** @param companyName Sets company name. */
    // I chose to set up company name differently in the Add Parts / Modify Parts controllers
    public void setCompanyName(String companyName) {

        this.companyName = companyName;

    }

}
