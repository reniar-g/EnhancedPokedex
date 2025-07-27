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

    /**
     * Sets the Pokémon's held item.
     */
    public void setHeldItem(Item heldItem)
    {
        if(this.heldItem != null && heldItem != null)
        {
            System.out.println(this.pokemonName + " discarded " + this.heldItem.getItemName());
        }

        this.heldItem = heldItem;
    }

    public Item removeHeldItem()
    {
        Item removed = this.heldItem;
        this.heldItem = null;
        return removed;
    }

    public boolean hasHeldItem()
    {
        return this.heldItem != null;
    }

    /**
     * Simulates the Pokémon's cry.
     */
    public void cry() {
        System.out.println(pokemonName + " cries: " + pokemonName.toUpperCase() + "!");
    }

    /**
     * Helper method to convert null Integer to "N/A" string
     */
    private String formatNullableInteger(Integer value) {
        return value == null ? "N/A" : value.toString();
    }

    /**
     * Displays the header for Pokemon information table
     */
    public static void displayPokemonHeader() {
        System.out.printf("%-4s %-14s %-15s %-6s %-7s %-8s %-8s %-8s %-15s %-13s %-15s%n",
                "ID", "Name", "Type", "Level", "HP", "Atk", "Def", "Spd", "Evolves From", "Evolves To", "Evo Level");
        System.out.println("---------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * Displays Pokemon information with N/A for null values in a formatted
     * table style with labels using printf
     */
    public void displayPokemon() {
        String type = pokemonType1 + (pokemonType2 != null && !pokemonType2.isEmpty() ? "/" + pokemonType2 : "");
        System.out.printf("%-4s %-14s %-15s %-6s %-7s %-8s %-8s %-8s %-15s %-13s %-15s%n",
                "#" + pokedexNumber,
                pokemonName,
                "(" + type + ")",
                "Lv." + baseLevel,
                "HP:" + hp,
                "Atk:" + attack,
                "Def:" + defense,
                "Spd:" + speed,
                "From:" + formatNullableInteger(evolvesFrom),
                "To:" + formatNullableInteger(evolvesTo),
                "EvoLv:" + formatNullableInteger(evolutionLevel));
    }

    public boolean hasMove(String moveName) {
        for (Move m : moveSet) {
            if (m.getMoveName().equalsIgnoreCase(moveName)) {
                return true;
            }
        }
        return false;
    }

    public boolean learnMove(Move moveToLearn)
    {
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

    public boolean canLearnMove(Move move)
    {
        return (move.getMoveType1().equalsIgnoreCase(pokemonType1) ||
                (pokemonType2 != null && move.getMoveType1().equalsIgnoreCase(pokemonType2)) ||
                (move.getMoveType2() != null && move.getMoveType2().equalsIgnoreCase(pokemonType1)) ||
                (pokemonType2 != null && move.getMoveType2() != null &&
                        move.getMoveType2().equalsIgnoreCase(pokemonType2)));
    }
}
