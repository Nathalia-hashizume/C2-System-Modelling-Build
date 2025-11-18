package CA_2;

// --- A. IMPORTS ---
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SchoolSystem {

    // GLOBAL LIST: Stores applicants so both Sort and Search options can access it
    private static List<Applicant> applicantList = new ArrayList<>();

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
                // Read entire line to prevent buffer errors
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
                    
                    // 1. Load data into the global list
                    applicantList = readApplicantsFile("Applicants_Form - Sample data file for read (1).txt");
                    
                    if (!applicantList.isEmpty()) {
                        // 2. Sort using Recursive Merge Sort
                        mergeSort(applicantList);
                        
                        // 3. Display Top 20
                        System.out.println("\n--- Top 20 Applicants (Sorted Alphabetically) ---");
                        int count = 0;
                        for (Applicant app : applicantList) {
                            if (count >= 20) break;
                            System.out.println((count + 1) + ". " + app.getFullName());
                            count++;
                        }
                    } else {
                        System.out.println("No applicants loaded. Please check the file name.");
                    }
                    break;

                case SEARCH_APPLICANTS:
                    // [OPTION 2] Binary Search
                    System.out.println("You selected: SEARCH");
                    
                    // Validation: List must be loaded and sorted first
                    if (applicantList.isEmpty()) {
                        System.out.println("ERROR: The list is empty. Please run Option 1 (Sort) first to load the data.");
                        break;
                    }

                    System.out.print("Enter the full name to search (Case Sensitive): ");
                    String nameToSearch = scanner.nextLine().trim(); // trim removes extra spaces

                    // Call Recursive Binary Search
                    // We search from index 0 to the last index
                    Applicant foundApplicant = binarySearch(applicantList, nameToSearch, 0, applicantList.size() - 1);

                    if (foundApplicant != null) {
                        System.out.println("\n*** APPLICANT FOUND ***");
                        System.out.println("Name: " + foundApplicant.getFullName());
                        System.out.println("Department: " + foundApplicant.getDepartment());
                        System.out.println("Manager Type (Job Title): " + foundApplicant.getJobTitle());
                    } else {
                        System.out.println("Applicant '" + nameToSearch + "' was not found in the list.");
                    }
                    break;

                case ADD_USER:
                    System.out.println("You selected: ADD USER (Coming soon...)");
                    break;
                    
                case BINARY_TREE:
                    System.out.println("You selected: BINARY TREE (Coming soon...)");
                    break;
                    
                case EXIT:
                    System.out.println("Exiting system...");
                    running = false;
                    break;
            }
        }
        scanner.close();
        
    } // END OF MAIN METHOD

    
    // HELPER METHODS (File I/O, Sorting, Searching)
    /**
     * Reads the CSV file and returns a list of Applicant objects.
     */
    private static List<Applicant> readApplicantsFile(String fileName) {
        List<Applicant> applicants = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 8) {
                    // 0=FirstName, 1=LastName, 5=Department, 7=JobTitle
                    applicants.add(new Applicant(values[0], values[1], values[5], values[7]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        System.out.println("File loaded: " + applicants.size() + " records.");
        return applicants;
    }

    // RECURSIVE MERGE SORT
    private static void mergeSort(List<Applicant> list) {
        int n = list.size();
        if (n < 2) return; // Base case
        
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

    // RECURSIVE BINARY SEARCH
    /**
     * Recursive Binary Search to find an applicant by name.
     * REQUIREMENT: The list MUST be sorted first.
     */
    private static Applicant binarySearch(List<Applicant> list, String targetName, int left, int right) {
        // Base Case: If left index is greater than right, element is not present
        if (left > right) {
            return null;
        }

        // 1. Calculate Middle Index
        int mid = left + (right - left) / 2;
        String midName = list.get(mid).getFullName();

        // 2. Compare names (Case sensitive as strings usually are)
        int comparison = targetName.compareTo(midName);

        if (comparison == 0) {
            // Target found at mid
            return list.get(mid);
        }

        // 3. Recursive calls
        if (comparison < 0) {
            // Target is in the left half
            return binarySearch(list, targetName, left, mid - 1);
        } else {
            // Target is in the right half
            return binarySearch(list, targetName, mid + 1, right);
        }
    }
    
}