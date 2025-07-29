package model;

public class Move {

    private String moveName;
    private String moveDescription;
    private String moveClassification; // TM or HM
    private String moveType1;
    private String moveType2;

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
