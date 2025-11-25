package CA_2;

//Represents a Manager class

public class Manager extends Employee {
    protected String managerType;
    public Manager(String name, String managerType) {
        super(name);
        this.managerType = managerType;
    }
    public String getManagerType() { return managerType; }
}
    