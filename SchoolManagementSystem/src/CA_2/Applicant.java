package CA_2;

public class Applicant {
    
    String firstName, lastName, department, jobTitle;
    public Applicant(String f, String l, String d, String j) {
        firstName=f; lastName=l; department=d; jobTitle=j;
    }
    
    public String getFullName() { return firstName + " " + lastName; }
    public String getDepartment() { return department; }
    public String getJobTitle() { return jobTitle; }
    @Override public String toString() { return getFullName(); }
}