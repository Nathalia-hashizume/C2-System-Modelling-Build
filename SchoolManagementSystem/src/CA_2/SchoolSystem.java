package CA_2;

// IMPORTS
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays; // Added for cleaner printing in validation
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class SchoolSystem {

    // GLOBAL LIST: Stores applicants so all methods can access it
    private static List<Applicant> applicantList = new ArrayList<>();

    // VALIDATION LISTS 
    private static final String[] VALID_DEPTS = {"Mathematics", "English", "Science", "History", "Administration", "Sports", "Library"};
    // Allowed Job Titles (Manager Types)
    private static final String[] VALID_JOBS = {"Director", "Vice-Director", "Head of Year", "Teacher", "Admin Staff", "Coordinator"};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to the School Management System");

        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            for (MenuOption option : MenuOption.values()) {
                System.out.println(option.getId() + ". " + option.getDescription());
            }
            System.out.print("Please select an option: ");

            int choice = -1;
            try {
                String input = scanner.nextLine();
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            MenuOption selectedOption = MenuOption.fromId(choice);

            if (selectedOption == null) {
                System.out.println("Invalid option. Please try again.");
                continue;
            }
            
            switch (selectedOption) {
                case SORT_APPLICANTS:
                    // [OPTION 1] Read File and Sort
                    System.out.println("Reading and sorting the applicant list...");
                    applicantList = readApplicantsFile("Applicants_Form - Sample data file for read (1).txt");
                    
                    if (!applicantList.isEmpty()) {
                        mergeSort(applicantList);
                        System.out.println("\n--- Top 20 Applicants (Sorted Alphabetically) ---");
                        int count = 0;
                        for (Applicant app : applicantList) {
                            if (count >= 20) break;
                            System.out.println((count + 1) + ". " + app.getFullName());
                            count++;
                        }
                    } else {
                        System.out.println("No applicants loaded. Check file name.");
                    }
                    break;

                case SEARCH_APPLICANTS:
                    // [OPTION 2] Binary Search
                    System.out.println("You selected: SEARCH");
                    if (applicantList.isEmpty()) {
                        System.out.println("ERROR: List is empty. Run Option 1 first.");
                        break;
                    }
                    System.out.print("Enter full name to search (Case Sensitive): ");
                    String nameToSearch = scanner.nextLine().trim();
                    Applicant foundApplicant = binarySearch(applicantList, nameToSearch, 0, applicantList.size() - 1);

                    if (foundApplicant != null) {
                        System.out.println("\n*** APPLICANT FOUND ***");
                        System.out.println("Name: " + foundApplicant.getFullName());
                        System.out.println("Department: " + foundApplicant.getDepartment());
                        System.out.println("Function Type: " + foundApplicant.getJobTitle());
                    } else {
                        System.out.println("Applicant '" + nameToSearch + "' not found.");
                    }
                    break;

                case ADD_USER:
                    // [OPTION 3] Add New User
                    addNewUser(scanner);
                    break;
                    
                case BINARY_TREE:
                    //Binary Tree Structure
                    if (applicantList.isEmpty()) {
                    System.out.println("ERROR: List Empty. Run Option 1 first to load data.");
                    break;
                    }
                    System.out.println("Building Employee Hierarchy Tree (Level-Order)...");
                    
                    //1. Build Tree with first 20 records (requirement)
                    Node root = buildLevelOrderTree(applicantList, 20);
                    
                    //2. Display Level Order Traversal
                    System.out.println("\n--- Level Order Traversal (Name and Role)---");
                    printLevelOrder(root);
                    
                    //3. Display Stats
                    System.out.println("\n\n--- Tree Statistics---");
                    System.out.println("Total Nodes: " + countNodes(root));
                    System.out.println("Tree Height: " + getHeight(root));
                    break;              
                    
                case EXIT:
                    System.out.println("Exiting system...");
                    running = false;
                    break;
            }
        }
        scanner.close();
    } // END OF MAIN METHOD

    // HELPER METHODS
    
    // 1. Read File
    private static List<Applicant> readApplicantsFile(String fileName) {
        List<Applicant> applicants = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 8) {
                    applicants.add(new Applicant(values[0], values[1], values[5], values[7]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        System.out.println("File loaded: " + applicants.size() + " records.");
        return applicants;
    }

    // 2. Merge Sort
    private static void mergeSort(List<Applicant> list) {
        int n = list.size();
        if (n < 2) return;
        int mid = n / 2;
        List<Applicant> left = new ArrayList<>(list.subList(0, mid));
        List<Applicant> right = new ArrayList<>(list.subList(mid, n));
        mergeSort(left);
        mergeSort(right);
        merge(list, left, right);
    }

    private static void merge(List<Applicant> list, List<Applicant> left, List<Applicant> right) {
        int i = 0, j = 0, k = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i).getFullName().compareTo(right.get(j).getFullName()) <= 0) {
                list.set(k++, left.get(i++));
            } else {
                list.set(k++, right.get(j++));
            }
        }
        while (i < left.size()) list.set(k++, left.get(i++));
        while (j < right.size()) list.set(k++, right.get(j++));
    }

    // 3. Binary Search
    private static Applicant binarySearch(List<Applicant> list, String targetName, int left, int right) {
        if (left > right) return null;
        int mid = left + (right - left) / 2;
        String midName = list.get(mid).getFullName();
        int comparison = targetName.compareTo(midName);
        if (comparison == 0) return list.get(mid);
        if (comparison < 0) return binarySearch(list, targetName, left, mid - 1);
        else return binarySearch(list, targetName, mid + 1, right);
    }

    // 4. Add New User (Option 3)
    private static void addNewUser(Scanner scanner) {
        System.out.println("\n--- Add New Applicant ---");
        System.out.print("Enter First Name: ");
        String fName = scanner.nextLine().trim();
        System.out.print("Enter Last Name: ");
        String lName = scanner.nextLine().trim();
        
        String selectedDept = "";
        boolean validDept = false;
        while (!validDept) {
            System.out.println("Available Departments: [IT Development, HR, Finance, Sales, Marketing, Operations]");
            System.out.print("Enter Department: ");
            String inputDept = scanner.nextLine().trim();
            for (String dept : VALID_DEPTS) {
                if (dept.equalsIgnoreCase(inputDept)) {
                    selectedDept = dept;
                    validDept = true;
                    break;
                }
            }
            if (!validDept) System.out.println("Invalid Department! Try again.");
        }
        
        String selectedJob = "";
        boolean validJob = false;
        while (!validJob) {
            System.out.println("Available Positions: [Manager, Senior Manager, Team Lead, Assistant Manager, Clerk, Intern]");
            System.out.print("Enter Position: ");
            String inputJob = scanner.nextLine().trim();
            for (String job : VALID_JOBS) {
                if (job.equalsIgnoreCase(inputJob)) {
                    selectedJob = job;
                    validJob = true;
                    break;
                }
            }
            if (!validJob) System.out.println("Invalid Position! Try again.");
        }
        
        Applicant newApp = new Applicant(fName, lName, selectedDept, selectedJob);
        applicantList.add(newApp);
        mergeSort(applicantList); // Re-sort automatically
        System.out.println("\nSUCCESS: User " + newApp.getFullName() + " added and list re-sorted!");
    }
    
    //5. BINARY TREE METHODS (OPTION 4) - NOVOS MÉTODOS
    
    // Insert first 'limit' records into tree using Level Order
    private static Node buildLevelOrderTree(List<Applicant> list, int limit) {
        if (list.isEmpty()) return null;
       
        Node root = new Node(list.get(0));
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        
        int i = 1;
        while (i < list.size() && i < limit) {
            Node current = queue.peek(); 
            
            if (current.left == null) {
                current.left = new Node(list.get(i));
                queue.add(current.left);
                i++;
            } else if (current.right == null) {
                current.right = new Node(list.get(i));
                queue.add(current.right);
                i++;
                queue.poll(); // Current node is full, remove from queue
            }
        }
        return root;
    }

    // Display Tree (Level Order Traversal)
    private static void printLevelOrder(Node root) {
        if (root == null) return;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node temp = queue.poll();
            // Print format: Name (Role)
            System.out.print(temp.data.getFullName() + " (" + temp.data.getJobTitle() + ") | ");
            
            if (temp.left != null) queue.add(temp.left);
            if (temp.right != null) queue.add(temp.right);
        }
    }

    // Calculate Height (Recursive)
    private static int getHeight(Node node) {
        if (node == null) return 0;
        int leftH = getHeight(node.left);
        int rightH = getHeight(node.right);
        return Math.max(leftH, rightH) + 1;
    }

    //Count Nodes (Recursive)
    private static int countNodes(Node node) {
        if (node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }
}
