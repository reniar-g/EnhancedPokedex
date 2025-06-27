// Move class represents a Pokémon move with its attributes and methods.

public class Move {

    private String moveName;
    private String moveDescription;
    private String moveClassification; // TM or HM
    private String moveType1;

    // Constructor for Move class
    public Move(String name, String description, String classification, String type1) {
        this.moveName = name;
        this.moveDescription = description;
        this.moveClassification = classification;
        this.moveType1 = type1;
    }

    // Getters for Move attributes
    public String getMoveName() {
        return moveName;
    }

    public String getMoveType1() {
        return moveType1;
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
        System.out.printf("%-15s %-16s %-10s %-60s%n",
                "Move Name", "Classification", "Type", "Description");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * Displays move details in a formatted table style using printf
     */
    public void displayMove() {
        System.out.printf("%-15s %-16s %-10s %-60s%n",
                moveName,
                moveClassification,
                moveType1,
                moveDescription);
    }
}
