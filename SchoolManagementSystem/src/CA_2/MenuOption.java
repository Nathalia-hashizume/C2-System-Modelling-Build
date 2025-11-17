package CA_2;

public enum MenuOption {
    SORT_APPLICANTS(1, "Sort a Dummy List of People"),
    SEARCH_APPLICANTS(2, "Search in the List"),
    ADD_USER(3, "Add New User"),
    BINARY_TREE(4, "Create Employee Hierarchy (Binary Tree)"),
    EXIT(5, "Exit");

    private final int id;
    private final String description;

    MenuOption(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
    
    public static MenuOption fromId(int id) {
        for (MenuOption option : values()) {
            if (option.getId() == id) {
                return option;
            }
        }
        return null;
    }
}