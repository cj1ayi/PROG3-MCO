package model;

/**
 * The {@code Moves} class is part of MODEL.
 *
 * It represents a Pokemon move with its properties such as name, type, classification, and description.
 * This class encapsulates all the information about a Pokemon move and provides
 * getter and setter methods for accessing and modifying the move's attributes.
 * 
 * This module contains the core data structure for Pokemon moves in the system.
 */
public class Moves {
    /** The name of the move */
    private String name;
    
    /** The primary type of the move (e.g., Fire, Water, Electric) */
    private String type1;
    
    /** The classification of the move (e.g., Physical, Special, Status) */
    private String classification;
    
    /** A detailed description of what the move does */
    private String desc;
    
    //private String type2;

    /**
     * Constructs a {@code Moves} object with the specified properties.
     * 
     * @param name the name of the move
     * @param type1 the primary type of the move
     * @param classification the classification of the move (Physical, Special, or Status)
     * @param desc a detailed description of the move's effect
     */
    public Moves(String name, String type1, String classification, String desc) {
        this.name = name;
        this.desc = desc;
        this.classification = classification;
        this.type1 = type1;
		  //	optional type2 will be implemented for mco2
        // this.type2 = type2;
    }

    ////////// Getters
    
    /**
     * Gets the name of the move.
     * 
     * @return the name of the move
     */
    public String getMoveName() { return name; }
    
    /**
     * Gets the primary type of the move.
     * 
     * @return the primary type of the move
     */
    public String getMoveType1() { return type1; }
    
    /**
     * Gets the classification of the move.
     * 
     * @return the classification of the move (Physical, Special, or Status)
     */
    public String getMoveClassification() { return classification; }
    
    /**
     * Gets the description of the move.
     * 
     * @return a detailed description of the move's effect
     */
    public String getMoveDesc() { return desc; }
    //optional type2 will be implemented for mco2
	 //public String getMoveType2() { return type2; }

    ////////// Setters
    /**
     * Sets the name of the move.
     * 
     * @param name the new name for the move
     */
    public void setMoveName(String name) { this.name = name; }
    
    /**
     * Sets the primary type of the move.
     * 
     * @param type1 the new primary type for the move
     */
    public void setMoveType1(String type1) { this.type1 = type1; }
    
    /**
     * Sets the classification of the move.
     * 
     * @param classification the new classification for the move (Physical, Special, or Status)
     */
    public void setMoveClassification(String classification) { this.classification = classification; }
    
    /**
     * Sets the description of the move.
     * 
     * @param desc the new description for the move
     */
    public void setMoveDesc(String desc) { this.desc = desc; }
}
