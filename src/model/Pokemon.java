package model;

import java.util.ArrayList;
import java.util.List;

// Pokémon class represents a Pokémon with various attributes and methods to manage its state and actions.
public class Pokemon {

    private int pokedexNumber;
    private String pokemonName;
    private String pokemonType1;
    private String pokemonType2; // nullable
    private int baseLevel;
    private Integer evolvesFrom; //  nullable
    private Integer evolvesTo;   // nullable
    private Integer evolutionLevel; // nullable
    private double hp;
    private double attack;
    private double defense;
    private double speed;
    private List<Move> moveSet;
    private Item heldItem;

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

    // adds default moves to the Pokémon's move set
    public void addDefaultMoves(ArrayList<Move> globalMoveList) {
        // if the global move list has at least 2 moves, add Tackle and Defend
        if (globalMoveList.size() >= 2) {
            moveSet.add(globalMoveList.get(0)); // Add Tackle
            moveSet.add(globalMoveList.get(1)); // Add Defend
        }
    }

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

    // checks if the pokémon has a specific move by name
    public boolean hasMove(String moveName) {
        for (Move m : moveSet) {
            if (m.getMoveName().equalsIgnoreCase(moveName)) {
                return true;
            }
        }
        return false;
    }
}
