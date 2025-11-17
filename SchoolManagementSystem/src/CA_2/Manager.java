package CA_2;

public class Manager extends Employee {
    protected String managerType;
    public Manager(String name, String managerType) {
        super(name);
        this.managerType = managerType;
    }
    public String getManagerType() { return managerType; }
}
    