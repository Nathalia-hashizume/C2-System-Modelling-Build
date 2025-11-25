package CA_2;

//Represents an Applicant in the organization
//Basic identity from the Emplloyee class.

public class Applicant extends Employee {
    
    //Specific attributes for the Applicant
    private String department;
    private String jobTitle;
    
    public Applicant(String firstName, String lastName, String department, String jobTitle) {
        //Passes the full name to the parent Employee class
        super(firstName + "" + lastName);
        this.department = department;
        this.jobTitle = jobTitle;
    }
    
    public String getFullName() { return super.getName(); }
    public String getDepartment() { return department; }
    public String getJobTitle() { return jobTitle; }
    @Override public String toString() { return getFullName(); }
}