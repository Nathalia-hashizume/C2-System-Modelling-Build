package CA_2;

// --- A. IMPORTS ---
// Added for reading files and using lists
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SchoolSystem {

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
                // Read the whole line to avoid buffer issues
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            MenuOption selectedOption = MenuOption.fromId(choice);

            if (selectedOption == null) {
                System.out.println("Invalid option. Please try again.");
                continue;
            }

            // --- C. UPDATED SWITCH ---
            // The 'case SORT_APPLICANTS' was modified
            switch (selectedOption) {
                case SORT_APPLICANTS:
                    System.out.println("Reading and sorting the applicant list...");
                    
                    // 1. Read the file 
                    List<Applicant> applicants = readApplicantsFile("Applicants_Form - Sample data file for read (1).txt");
                    
                    if (!applicants.isEmpty()) {
                        // 2. Sort the list using Merge Sort
                        mergeSort(applicants);
                        
                        // 3. Display the first 20 names 
                        System.out.println("\n--- Top 20 Applicants (Sorted Alphabetically) ---");
                        int count = 0;
                        for (Applicant app : applicants) {
                            if (count >= 20) break;
                            System.out.println((count + 1) + ". " + app.getFullName());
                            count++;
                        }
                    } else {
                        System.out.println("No applicants were loaded. Check the file.");
                    }
                    break;
                case SEARCH_APPLICANTS:
                    System.out.println("You selected: SEARCH");
                  
                    break;
                case ADD_USER:
                    System.out.println("You selected: ADD USER");
                  
                    break;
                case BINARY_TREE:
                    System.out.println("You selected: BINARY TREE");
                   
                    break;
                case EXIT:
                    System.out.println("Exiting system...");
                    running = false;
                    break;
            }
        }
        scanner.close();
        
    } // END OF 'main' METHOD 

    // B. NEW METHODS (Reading and Sorting)
    // Pasted here, AFTER 'main' and BEFORE the class closes
    
    /**
     * Reads the applicant CSV file and returns a list of Applicant objects.
     * @param fileName The name of the file to read.
     * @return A list of applicants.
     */
    private static List<Applicant> readApplicantsFile(String fileName) {
        List<Applicant> applicants = new ArrayList<>();
        // "try-with-resources" ensures the file is closed automatically
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            
            // Skip header line
            br.readLine(); 

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 8) {
                    // We only get the fields we need: 0=First, 1=Last, 5=Dept, 7=JobTitle
                    applicants.add(new Applicant(values[0], values[1], values[5], values[7]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        System.out.println("File read successfully. " + applicants.size() + " records loaded.");
        return applicants;
    }

    // --- RECURSIVE SORTING ALGORITHM (Merge Sort) ---
    
    /**
     * Main recursive Merge Sort method.
     * @param list The list of applicants to be sorted.
     */
    private static void mergeSort(List<Applicant> list) {
        int n = list.size();
        if (n < 2) {
            return; // List is already sorted (recursion base case)
        }
        
        // 1. Divide
        int mid = n / 2;
        List<Applicant> left = new ArrayList<>(list.subList(0, mid));
        List<Applicant> right = new ArrayList<>(list.subList(mid, n));
        
        // 2. Conquer (call itself recursively)
        mergeSort(left);
        mergeSort(right);
        
        // 3. Combine
        merge(list, left, right);
    }

    /**
     * Helper method to merge two sorted lists.
     * @param list The original list to be modified.
     * @param left The sorted left half.
     * @param right The sorted right half.
     */
    private static void merge(List<Applicant> list, List<Applicant> left, List<Applicant> right) {
        int i = 0, j = 0, k = 0;
        int leftSize = left.size();
        int rightSize = right.size();

        while (i < leftSize && j < rightSize) {
            // Compares full names to sort alphabetically
            if (left.get(i).getFullName().compareTo(right.get(j).getFullName()) <= 0) {
                list.set(k++, left.get(i++));
            } else {
                list.set(k++, right.get(j++));
            }
        }
        
        // Copy remaining elements from left list (if any)
        while (i < leftSize) {
            list.set(k++, left.get(i++));
        }
        // Copy remaining elements from right list (if any)
        while (j < rightSize) {
            list.set(k++, right.get(j++));
        }
    }
    
} 