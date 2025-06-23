package model;

public class Moves {
    private String name;
    private String type1;
    private String classification;
    private String desc;
    //private String type2;

    public Moves(String name, String type1, String classification, String desc) {
        this.name = name;
        this.desc = desc;
        this.classification = classification;
        this.type1 = type1;
        // this.type2 = type2;
    }

    // Getters
    public String getMoveName() { return name; }
    public String getMoveType1() { return type1; }
    public String getMoveClassification() { return classification; }
    public String getMoveDesc() { return desc; }
    //public String getMoveType2() { return type2; }

    // Setters
    public void setMoveName(String name) { this.name = name; }
    public void setMoveType1(String type1) { this.type1 = type1; }
    public void setMoveClassification(String classification) { this.classification = classification; }
    public void setMoveDesc(String desc) { this.desc = desc; }




    // public void setMoveType2(String type2) { this.type2 = type2; }


    // Methods


}
