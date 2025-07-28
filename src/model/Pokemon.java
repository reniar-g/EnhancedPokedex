package model;

import java.util.ArrayList;
import java.util.List;

// Pokémon class represents a Pokémon with various attributes and methods to manage its state and actions.
public class Pokemon {

    private int pokedexNumber;
    private String pokemonName;
    private String pokemonType1;
    private String pokemonType2; // Optional, nullable
    private int baseLevel;
    private Integer evolvesFrom; // Pokédex Number, nullable
    private Integer evolvesTo;   // Pokédex Number, nullable
    private Integer evolutionLevel; // Nullable
    private double hp;
    private double attack;
    private double defense;
    private double speed;
    private List<Move> moveSet;
    private Item heldItem;

    // Constructor for Pokémon class
    public Pokemon(int pokedexNumber, String name, String type1, String type2, int baseLevel,
            Integer evolvesFrom, Integer evolvesTo, Integer evolutionLevel,
            int hp, int attack, int defense, int speed) {
        this.pokedexNumber = pokedexNumber;
        this.pokemonName = name;
        this.pokemonType1 = type1;
        this.pokemonType2 = type2;
        this.baseLevel = baseLevel;
        this.evolvesFrom = evolvesFrom;
        this.evolvesTo = evolvesTo;
        this.evolutionLevel = evolutionLevel;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.moveSet = new ArrayList<>();
        this.heldItem = null;
    }

    /**
     * Adds default moves to this Pokémon. Should be called after a Pokémon is
     * created.
     */
    public void addDefaultMoves(ArrayList<Move> globalMoveList) {
        // Check if the move list has the default moves
        if (globalMoveList.size() >= 2) {
            moveSet.add(globalMoveList.get(0)); // Add Tackle
            moveSet.add(globalMoveList.get(1)); // Add Defend
        }
    }

    // Getters for Pokémon attributes
    public int getPokedexNumber() {
        return pokedexNumber;
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public String getPokemonType1() {
        return pokemonType1;
    }

    public String getPokemonType2() {
        return pokemonType2;
    }

    public int getBaseLevel() {
        return baseLevel;
    }

    public Integer getEvolvesFrom() {
        return evolvesFrom;
    }

    public Integer getEvolvesTo() {
        return evolvesTo;
    }

    public Integer getEvolutionLevel() {
        return evolutionLevel;
    }

    public double getHp() {
        return hp;
    }

    public double getAttack() {
        return attack;
    }

    public double getDefense() {
        return defense;
    }

    public double getSpeed() {
        return speed;
    }

    public List<Move> getMoveSet() {
        return moveSet;
    }

    public Item getHeldItem() {
        return heldItem;
    }

    //Setters for Pokemon Attributes
    public void setPokedexNumber(int pokedexNumber) {
        this.pokedexNumber = pokedexNumber;
    }

    public void setPokemonName(String pokemonName) {
        this.pokemonName = pokemonName;
    }

    public void setPokemonType1(String pokemonType1) {
        this.pokemonType1 = pokemonType1;
    }

    public void setPokemonType2(String pokemonType2) {
        this.pokemonType2 = pokemonType2;
    }

    public void setBaseLevel(int baseLevel) {
        this.baseLevel = baseLevel;
    }

    public void setEvolvesFrom(Integer evolvesFrom) {
        this.evolvesFrom = evolvesFrom;
    }

    public void setEvolvesTo(Integer evolvesTo) {
        this.evolvesTo = evolvesTo;
    }

    public void setEvolutionLevel(Integer evolutionLevel) {
        this.evolutionLevel = evolutionLevel;
    }

    public void setHP(double hp) {
        this.hp = hp;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setMoveSet(List<Move> moveSet) {
        this.moveSet = moveSet;
    }

    /**
     * Sets the Pokémon's held item.
     */
    public void setHeldItem(Item heldItem) {
        this.heldItem = heldItem;
    }

    public Item removeHeldItem() {
        Item removed = this.heldItem;
        this.heldItem = null;
        return removed;
    }

    public boolean hasHeldItem() {
        return this.heldItem != null;
    }

    public void addHealth(int healAmount) {
        hp += healAmount;
    }

    public void addAttack(int attackAmount) {
        attack += attackAmount;
    }

    public void addDefense(int defenseAmount) {
        defense += defenseAmount;
    }

    public void addSpeed(int speedAmount) {
        speed += speedAmount;
    }

    public void levelUp(int levelAmount) {
        baseLevel += levelAmount;
    }

    /**
     * Helper method to get formatted string representation of null Integer
     * values
     */
    private String formatNullableInteger(Integer value) {
        return value == null ? "N/A" : value.toString();
    }

    /**
     * Gets the type string in format "Type1/Type2" or just "Type1" if Type2 is
     * null
     */
    public String getTypeString() {
        return pokemonType2 != null && !pokemonType2.isEmpty()
                ? pokemonType1 + "/" + pokemonType2 : pokemonType1;
    }

    /**
     * Gets the evolution info string
     */
    public String getEvolutionString() {
        return "From:" + formatNullableInteger(evolvesFrom)
                + " To:" + formatNullableInteger(evolvesTo)
                + " EvoLv:" + formatNullableInteger(evolutionLevel);
    }

    public boolean hasMove(String moveName) {
        for (Move m : moveSet) {
            if (m.getMoveName().equalsIgnoreCase(moveName)) {
                return true;
            }
        }
        return false;
    }

    public boolean learnMove(Move moveToLearn) {
        if (hasMove(moveToLearn.getMoveName())) {
            return false;
        }

        Move newMove = new Move(
                moveToLearn.getMoveName(),
                moveToLearn.getMoveDescription(),
                moveToLearn.getMoveClassification(),
                moveToLearn.getMoveType1(),
                moveToLearn.getMoveType2()
        );

        moveSet.add(newMove);
        return true;
    }

    public boolean canLearnMove(Move move) {
        return (move.getMoveType1().equalsIgnoreCase(pokemonType1)
                || (pokemonType2 != null && move.getMoveType1().equalsIgnoreCase(pokemonType2))
                || (move.getMoveType2() != null && move.getMoveType2().equalsIgnoreCase(pokemonType1))
                || (pokemonType2 != null && move.getMoveType2() != null
                && move.getMoveType2().equalsIgnoreCase(pokemonType2)));
    }
}
