package model;

public class Move {

    private final String moveName;
    private final String moveDescription;
    private final String moveClassification; // TM or HM
    private final String moveType1;
    private final String moveType2;

    public Move(String name, String description, String classification, String type1,
            String type2) {
        this.moveName = name;
        this.moveDescription = description;
        this.moveClassification = classification;
        this.moveType1 = type1;
        this.moveType2 = type2;
    }

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
}
