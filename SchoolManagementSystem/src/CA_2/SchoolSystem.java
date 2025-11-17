package CA_2;

import java.util.Scanner;

public class SchoolSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to the School Management System");

        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            
            // Show the options using the Enum 
            for (MenuOption option : MenuOption.values()) {
                System.out.println(option.getId() + ". " + option.getDescription());
            }
            System.out.print("Please select an option: ");

            int choice = -1;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clean the buffer
            } else {
                scanner.nextLine(); // Clear invalid entry
            }

            MenuOption selectedOption = MenuOption.fromId(choice);

            if (selectedOption == null) {
                System.out.println("Invalid option. Please try again.");
                continue;
            }

            switch (selectedOption) {
                case SORT_APPLICANTS:
                    System.out.println("You selected: SORT");
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
    }
}
