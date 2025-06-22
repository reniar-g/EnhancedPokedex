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

    // Method to display move details
    public String displayMove() {
        return moveName + " (" + moveClassification + ") - Type: " + moveType1
                + " | " + moveDescription;
    }
}
