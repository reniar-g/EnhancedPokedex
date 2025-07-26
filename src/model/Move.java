package model;

// Move class represents a Pokémon move with its attributes and methods.
public class Move {

    private String moveName;
    private String moveDescription;
    private String moveClassification; // TM or HM
    private String moveType1;
    private String moveType2;

    // Constructor for Move class
    public Move(String name, String description, String classification, String type1,
            String type2) {
        this.moveName = name;
        this.moveDescription = description;
        this.moveClassification = classification;
        this.moveType1 = type1;
        this.moveType2 = type2;
    }

    // Getters for Move attributes
    public String getMoveName() {
        return moveName;
    }

    public String getMoveType1() {
        return moveType1;
    }

    public String getMoveType2() {
        return moveType2;
    }

    public String getMoveClassification() {
        return moveClassification;
    }

    public String getMoveDescription() {
        return moveDescription;
    }

    /**
     * Displays the header for Move information table
     */
    public static void displayMoveHeader() {
        System.out.printf("%-3s %-15s %-16s %-10s %-10s %-60s%n", "#",
                "Move Name", "Classification", "Type 1", "Type 2", "Description");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * Displays move details in a formatted table style using printf
     */
    public void displayMove() {
        // Format type2 to display "None" instead of null
        String type2Display = (moveType2 == null || moveType2.isEmpty()) ? "N/A" : moveType2;

        System.out.printf("%-15s %-16s %-10s %-10s %-60s%n",
                moveName,
                moveClassification,
                moveType1,
                type2Display,
                moveDescription);
    }
}
