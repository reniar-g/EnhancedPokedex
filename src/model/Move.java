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
     * Gets a formatted string representation of the move's type2
     */
    public String getType2Display() {
        return (moveType2 == null || moveType2.isEmpty()) ? "N/A" : moveType2;
    }
}
