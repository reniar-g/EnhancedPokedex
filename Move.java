
public class Move {

    private String name;
    private String description;
    private String classification; // TM or HM
    private String type1;
    private String type2;

// constructor
    public Move(String name, String description, String classification, String type1, String type2) {
        this.name = name;
        this.description = description;
        this.classification = classification;
        this.type1 = type1;
        this.type2 = type2;
    }

    public String getName() {
        return name;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

    public String getClassification() {
        return classification;
    }

    public String displayMove() {
        return name + " (" + classification + ") - Type: " + type1
                + (type2 != null ? "/" + type2 : "") + " | " + description;
    }
}
